package checkster.rules;

import checkster.CheckResult;
import checkster.CompatibilityRules;
import checkster.Reason;
import checkster.Version;

public class SameService implements CompatibilityRules {

    public CheckResult isCompatibleService(Version clientVersion, Version serviceVersion) {

        String cs = clientVersion.getService();
        String ss = serviceVersion.getService();

        if (cs != null && !cs.equalsIgnoreCase(ss) || cs == null && ss != null) {
            Reason reason = new Reason(String.format(
                    "El identificador del servicio (\"%s\") no coincide con el esperado (\"%s\")", ss, cs));
            return CheckResult.failure(reason, serviceVersion);
        }

        return CheckResult.success(new Reason("El identificar del servicio coincide con el esperado"), serviceVersion);

    }

}
