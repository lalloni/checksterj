package checkster;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Checker {

    private Version clientVersion;
    private URL serviceURL;
    private SSLContext sslContext;

    public Checker(Version clientVersion, URL serviceURL, SSLContext sslContext) {
        this.clientVersion = clientVersion;
        this.serviceURL = serviceURL;
        this.sslContext = sslContext;
    }

    public CheckResult check(CompatibilityRules rules) {
        HttpURLConnection c = null;
        try {
            try {
                c = (HttpURLConnection) serviceURL.openConnection();
                if (sslContext != null && c instanceof HttpsURLConnection) {
                    ((HttpsURLConnection) c).setSSLSocketFactory(sslContext.getSocketFactory());
                }
                c.setConnectTimeout(5000);
                c.setReadTimeout(5000);
                c.setRequestMethod("GET");
                c.setInstanceFollowRedirects(true);
                c.setRequestProperty("Accept", "application/json");
                c.connect();
                if (c.getResponseCode() != 200) {
                    return CheckResult.failure(new Reason(
                            "El servicio respondió de forma inesperada (status HTTP no fue OK)"));
                }
            } catch (IOException e) {
                return CheckResult.failure(new Reason("Imposible conectar al servicio", e));
            }
            String charset = getCharsetFromContentType(c.getContentType());
            InputStream is = null;
            try {
                is = c.getInputStream();
            } catch (IOException e) {
                return CheckResult.failure(new Reason("Imposible obtener respuesta del servicio", e));
            }
            try {
                Object o = null;
                try {
                    o = new JSONParser().parse(new InputStreamReader(is, charset));
                } catch (Exception e) {
                    return CheckResult.failure(new Reason("Imposible interpretar la respuesta del servicio", e));
                }
                if (!(o instanceof JSONObject)) {
                    return CheckResult.failure(new Reason(
                            "La respuesta del servicio fue incorrecta (no es un objeto JSON)"));
                }
                JSONObject json = (JSONObject) o;

                String service = null;
                o = json.get("service");
                if (o != null) {
                    if (!(o instanceof String)) {
                        return CheckResult
                                .failure(new Reason(
                                        "La respuesta del servicio fue incorrecta (\"service\" no es una cadena de caracteres)"));
                    }
                    service = (String) o;
                }

                Integer major = null;
                o = json.get("major");
                if (o != null) {
                    if (!(o instanceof Long)) {
                        return CheckResult.failure(new Reason(
                                "La respuesta del servicio fue incorrecta (\"major\" no es un número)"));
                    }
                    major = ((Long) o).intValue();
                }

                Integer minor = null;
                o = json.get("minor");
                if (o != null) {
                    if (!(o instanceof Long)) {
                        return CheckResult.failure(new Reason(
                                "La respuesta del servicio fue incorrecta (\"minor\" no es un número)"));
                    }
                    minor = ((Long) o).intValue();
                }

                Integer patch = null;
                o = json.get("patch");
                if (o != null) {
                    if (!(o instanceof Long)) {
                        return CheckResult.failure(new Reason(
                                "La respuesta del servicio fue incorrecta (\"patch\" no es un número)"));
                    }
                    patch = ((Long) o).intValue();
                }

                String meta = null;
                o = json.get("meta");
                if (o != null) {
                    if (!(o instanceof String)) {
                        return CheckResult.failure(new Reason(
                                "La respuesta del servicio fue incorrecta (\"meta\" no es una cadena de caracteres)"));
                    }
                    meta = (String) o;
                }

                Version version = new Version(service, major, minor, patch, meta);

                return rules.isCompatibleService(clientVersion, version);

            } finally {
                if (is != null)
                    try {
                        is.close();
                    } catch (IOException e) {}
            }
        } finally {
            if (c != null)
                c.disconnect();
        }
    }

    private static final Pattern charsetPattern = Pattern.compile("(?i)\\bcharset=\\s*\"?([^\\s;\"]*)");

    private static String getCharsetFromContentType(String contentType) {
        if (contentType == null)
            return "UTF-8";
        Matcher m = charsetPattern.matcher(contentType);
        if (m.find()) {
            return m.group(1).trim().toUpperCase();
        }
        return "UTF-8";
    }

}
