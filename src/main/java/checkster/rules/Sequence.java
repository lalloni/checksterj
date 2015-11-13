package checkster.rules;

import checkster.CheckResult;
import checkster.CompatibilityRules;
import checkster.Reason;
import checkster.Version;

public class Sequence implements CompatibilityRules {

    private CompatibilityRules[] rules;

    private String successReasonMessage;

    public Sequence(String successReasonMessage, CompatibilityRules... rules) {
        this.successReasonMessage = successReasonMessage;
        this.rules = rules;
    }

    public CheckResult isCompatibleService(Version clientVersion, Version serviceVersion) {
        for (CompatibilityRules rules : this.rules) {
            CheckResult result = rules.isCompatibleService(clientVersion, serviceVersion);
            if (!result.isSuccess()) {
                return result;
            }
        }
        return CheckResult.success(new Reason(successReasonMessage), serviceVersion);
    }

}
