package checkster;

import java.net.URL;

import javax.net.ssl.SSLContext;

import checkster.rules.Rules;

public class Check {

    private Version clientVersion;
    private URL serviceURL;
    private CompatibilityRules rules;
    private SSLContext sslContext;

    private CheckResult result;
    private Boolean run = false;

    public Check(Version clientVersion, URL serviceURL) {
        this.clientVersion = clientVersion;
        this.serviceURL = serviceURL;
    }

    public Check(Version clientVersion, URL serviceURL, CompatibilityRules rules) {
        this.clientVersion = clientVersion;
        this.serviceURL = serviceURL;
        this.rules = rules;
    }

    public Check(Version clientVersion, URL serviceURL, SSLContext ssl) {
        this.clientVersion = clientVersion;
        this.serviceURL = serviceURL;
        this.sslContext = ssl;
    }

    public Check(Version clientVersion, URL serviceURL, CompatibilityRules rules, SSLContext ssl) {
        this.clientVersion = clientVersion;
        this.serviceURL = serviceURL;
        this.rules = rules;
        this.sslContext = ssl;
    }

    public void run() {
        CompatibilityRules effectiveRules = rules != null ? rules : Rules.SameServiceAndMajor;
        result = new Checker(clientVersion, serviceURL, sslContext).check(effectiveRules);
        run = true;
    }

    public CheckResult getResult() {
        if (!run)
            run();
        return result;
    }

    public void ensureSuccessful() throws CheckFailure {
        CheckResult result = getResult();
        if (!result.isSuccess()) {
            throw new CheckFailure(result);
        }
    }

}
