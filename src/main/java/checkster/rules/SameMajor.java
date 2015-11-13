package checkster.rules;

import checkster.CheckResult;
import checkster.CompatibilityRules;
import checkster.Reason;
import checkster.Version;

public class SameMajor implements CompatibilityRules {

    public CheckResult isCompatibleService(Version clientVersion, Version serviceVersion) {

        Integer cm = clientVersion.getMajor();
        Integer sm = serviceVersion.getMajor();

        if (cm != sm) {
            return CheckResult
                    .failure(new Reason(
                            String.format(
                                    "El número mayor de versión del servicio (\"%d\") no coincide con el esperado por el cliente (\"%d\")",
                                    sm, cm)));
        }

        return CheckResult.success(new Reason(
                "El número mayor de versión del servicio coincide con el esperado por el cliente"));

    }

}
