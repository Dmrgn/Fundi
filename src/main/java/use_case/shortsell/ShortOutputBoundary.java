package use_case.shortsell;

public interface ShortOutputBoundary {
    void prepareSuccessView(ShortOutputData outputData);
    void prepareFailView(String errorMessage);
}
