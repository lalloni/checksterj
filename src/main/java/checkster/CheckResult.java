package checkster;

public class CheckResult {

    private Boolean success;

    private Reason reason;

    public CheckResult(Boolean success, Reason reason) {
        super();
        this.success = success;
        this.reason = reason;
    }

    public Boolean isSuccess() {
        return success;
    }

    public Reason getReason() {
        return reason;
    }

    public static CheckResult failure(Reason reason) {
        return new CheckResult(false, reason);
    }

    public static CheckResult success(Reason reason) {
        return new CheckResult(true, reason);
    }

}
