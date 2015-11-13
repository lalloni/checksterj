package checkster;

public interface CompatibilityRules {

    CheckResult isCompatibleService(Version clientVersion, Version serviceVersion);

}
