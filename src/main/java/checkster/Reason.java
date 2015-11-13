package checkster;

public class Reason {

    private String message;

    private Exception cause;

    public Reason(String message) {
        this(message, null);
    }

    public Reason(String message, Exception cause) {
        super();
        this.message = message;
        this.cause = cause;
    }

    public String getMessage() {
        return message;
    }

    public Exception getCause() {
        return cause;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(message);
        if (cause != null) {
            builder.append(" (causado por \"");
            builder.append(cause.getMessage());
            builder.append("\")");
        }
        return builder.toString();
    }

}
