package checkster;

public class CheckResult {

    private Boolean success;

    private Reason reason;

    private Version serviceVersion;

    public CheckResult(Boolean success, Reason reason) {
        super();
        this.success = success;
        this.reason = reason;
    }

    public CheckResult(Boolean success, Reason reason, Version serviceVersion) {
        super();
        this.success = success;
        this.reason = reason;
        this.serviceVersion = serviceVersion;
    }

    public Boolean isSuccess() {
        return success;
    }

    public Reason getReason() {
        return reason;
    }

    public Version getServiceVersion() {
        return serviceVersion;
    }

    public static CheckResult failure(Reason reason) {
        return new CheckResult(false, reason);
    }

    public static CheckResult failure(Reason reason, Version serviceVersion) {
        return new CheckResult(false, reason, serviceVersion);
    }

    public static CheckResult success(Reason reason) {
        return new CheckResult(true, reason);
    }

    public static CheckResult success(Reason reason, Version serviceVersion) {
        return new CheckResult(true, reason, serviceVersion);
    }

}
