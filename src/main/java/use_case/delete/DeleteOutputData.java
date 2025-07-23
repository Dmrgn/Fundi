package use_case.delete;

public class DeleteOutputData {
    boolean success;
    String message;

    public DeleteOutputData(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
