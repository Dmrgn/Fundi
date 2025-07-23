package use_case.sell;

public class SellOutputData {
    boolean success;
    String message;

    public SellOutputData(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
