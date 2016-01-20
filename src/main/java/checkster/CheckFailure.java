package checkster;

public class CheckFailure extends RuntimeException {

    private static final long serialVersionUID = 2773357608111411957L;

    private CheckResult checkResult;

    public CheckFailure(CheckResult checkResult) {
        super(checkResult.getReason().getMessage(), checkResult.getReason().getCause());
        this.checkResult = checkResult;
    }

    public CheckResult getCheckResult() {
        return checkResult;
    }

}
