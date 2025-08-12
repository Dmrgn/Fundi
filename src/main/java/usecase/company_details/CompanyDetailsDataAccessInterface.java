package usecase.company_details;

import entity.CompanyDetails;

/**
 * DAO for the Company Details Use Case.
 */
public interface CompanyDetailsDataAccessInterface {

    /**
     * Retrieves detailed company information by symbol.
     * 
     * @param symbol the company symbol
     * @return the company details
     * @throws Exception if the retrieval fails
     */
    CompanyDetails getCompanyDetails(String symbol) throws Exception;
}
