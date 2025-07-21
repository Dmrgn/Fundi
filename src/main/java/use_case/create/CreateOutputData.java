package use_case.create;

/**
 * Output Data for the Create Use Case.
 */
public class CreateOutputData {

    private final boolean useCaseFailed;

    public CreateOutputData(boolean useCaseFailed) {
        this.useCaseFailed = useCaseFailed;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }

}