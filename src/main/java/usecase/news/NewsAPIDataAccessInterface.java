package usecase.news;

import org.json.JSONArray;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Interface for accessing news data from an external API.
 */
public interface NewsAPIDataAccessInterface {
    /**
     * Fetches news articles for a given stock symbol within a date range.
     *
     * @param symbol   The stock symbol.
     * @param fromDate The start date for the news search.
     * @param toDate   The end date for the news search.
     * @return A JSONArray of news articles.
     * @throws IOException If an I/O error occurs.
     */
    JSONArray fetchNewsForSymbol(String symbol, LocalDate fromDate, LocalDate toDate) throws IOException;
}
