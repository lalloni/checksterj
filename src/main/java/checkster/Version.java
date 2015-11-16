package checkster;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;

public class Version {

    private static final Pattern pattern = Pattern
            .compile("^(?:(?<service>[a-zA-Z0-9-_.]+)-)?(?<major>\\d+)\\.(?<minor>\\d+)\\.(?<patch>\\d+)(?:-(?<prerelease>[0-9a-zA-Z-]+(?:\\.[0-9a-zA-Z-]+)*))?(?:\\+(?<build>[0-9a-zA-Z-]+(?:\\.[0-9a-zA-Z-]+)*))?$");

    private String service;

    private int major;

    private int minor;

    private int patch;

    private String prerelease;

    private String build;

    public Version(int major, int minor, int patch) {
        this(null, major, minor, patch, null, null);
    }

    public Version(String service, int major, int minor, int patch) {
        this(service, major, minor, patch, null, null);
    }

    public Version(String service, int major, int minor, int patch, String prerelease, String build) {
        super();
        this.service = service;
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.prerelease = prerelease;
        this.build = build;
    }

    public String getService() {
        return service;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getPatch() {
        return patch;
    }

    public String getPrerelease() {
        return prerelease;
    }

    public String getBuild() {
        return build;
    }

    public String toVersionString() {
        StringBuilder s = new StringBuilder();
        appendVersion(s);
        return s.toString();
    }

    public String toServiceVersionString() {
        StringBuilder s = new StringBuilder();
        if (service == null) {
            throw new IllegalArgumentException("Identificador de servicio nulo");
        }
        s.append(service);
        s.append('-');
        appendVersion(s);
        return s.toString();
    }

    public JSONObject toVersionJSON() {
        JSONObject json = new JSONObject();
        json.put("major", major);
        json.put("minor", minor);
        json.put("patch", patch);
        if (prerelease != null) {
            json.put("prerelease", prerelease);
        }
        if (build != null) {
            json.put("build", build);
        }
        return json;
    }

    public JSONObject toServiceVersionJSON() {
        JSONObject json = toVersionJSON();
        if (service != null) {
            json.put("service", service);
        }
        return json;
    }

    private void appendVersion(StringBuilder s) {
        s.append(major);
        s.append('.');
        s.append(minor);
        s.append('.');
        s.append(patch);
        if (prerelease != null) {
            s.append('-');
            s.append(prerelease);
        }
        if (build != null) {
            s.append('+');
            s.append(build);
        }
    }

    public static Version fromServiceVersionJSON(JSONObject json) {
        String service = getString("service", json);
        int major = getInt("major", json);
        int minor = getInt("minor", json);
        int patch = getInt("patch", json);
        String prerelease = getString("prerelease", json);
        String build = getString("build", json);
        return new Version(service, major, minor, patch, prerelease, build);
    }

    private static int getInt(String name, JSONObject json) throws IllegalArgumentException {
        int n = 0;
        Object o = json.get(name);
        if (o != null) {
            if (!(o instanceof Long)) {
                throw new IllegalArgumentException(String.format("\"%s\" no es un número entero", name));
            }
            n = ((Long) o).intValue();
        }
        return n;
    }

    private static String getString(String name, JSONObject json) throws IllegalArgumentException {
        String s = null;
        Object o = json.get(name);
        if (o != null) {
            if (!(o instanceof String)) {
                throw new IllegalArgumentException(String.format("\"%s\" no es una cadena de caracteres", name));
            }
            s = (String) o;
        }
        return s;
    }

    public static Version fromServiceVersionString(String string) {
        Matcher m = pattern.matcher(string);
        if (m.matches()) {
            String service = m.group("service");
            int major = Integer.parseInt(m.group("major"));
            int minor = Integer.parseInt(m.group("minor"));
            int patch = Integer.parseInt(m.group("patch"));
            String prerelease = m.group("prerelease");
            String build = m.group("build");
            return new Version(service, major, minor, patch, prerelease, build);
        }
        throw new IllegalArgumentException(String.format("La cadena '%s' no se puede interpretar como una versión",
                string));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((build == null) ? 0 : build.hashCode());
        result = prime * result + major;
        result = prime * result + minor;
        result = prime * result + patch;
        result = prime * result + ((prerelease == null) ? 0 : prerelease.hashCode());
        result = prime * result + ((service == null) ? 0 : service.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Version other = (Version) obj;
        if (build == null) {
            if (other.build != null) {
                return false;
            }
        } else if (!build.equals(other.build)) {
            return false;
        }
        if (major != other.major) {
            return false;
        }
        if (minor != other.minor) {
            return false;
        }
        if (patch != other.patch) {
            return false;
        }
        if (prerelease == null) {
            if (other.prerelease != null) {
                return false;
            }
        } else if (!prerelease.equals(other.prerelease)) {
            return false;
        }
        if (service == null) {
            if (other.service != null) {
                return false;
            }
        } else if (!service.equals(other.service)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("Version [service=%s, major=%s, minor=%s, patch=%s, prerelease=%s, build=%s]", service,
                major, minor, patch, prerelease, build);
    }

}
