package use_case.main;

import java.util.Map;

/**
 * Output Data for the main Use Case.
 */
public class MainOutputData {

    private final String username;
    private final String useCase;
    private final Map<String, String> portfolios;


    public MainOutputData(String username, String useCase, Map<String, String> portfolios) {
        this.username = username;
        this.useCase = useCase;
        this.portfolios = portfolios;
    }

    public String getUsername() {
        return username;
    }

    public String getUseCase() {
        return useCase;
    }
    
    public Map<String, String> getPortfolios() {
        return portfolios;
    }
}