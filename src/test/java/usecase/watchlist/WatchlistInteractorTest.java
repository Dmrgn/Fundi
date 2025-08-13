package usecase.watchlist;

import dataaccess.TickerCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WatchlistInteractorTest {

    @Mock
    private WatchlistDataAccessInterface dataAccess;

    @Mock
    private WatchlistOutputBoundary presenter;

    private WatchlistInteractor interactor;
    private final String username = "testuser";

    @BeforeEach
    void setUp() {
        interactor = new WatchlistInteractor(dataAccess, presenter);
    }

    @Test
    void addTicker_WithEmptyTicker_ShouldPresentError() throws Exception {
        // Arrange
        WatchlistInputData inputData = new WatchlistInputData(username, "");

        // Act
        interactor.addTicker(inputData);

        // Assert
        verify(presenter).presentError("Please enter a ticker symbol.");
        verify(dataAccess, never()).addToWatchlist(anyString(), anyString());
    }

    @Test
    void addTicker_WithInvalidFormat_ShouldPresentError() throws Exception {
        // Arrange
        WatchlistInputData inputData = new WatchlistInputData(username, "ABC123");

        // Act
        interactor.addTicker(inputData);

        // Assert
        verify(presenter).presentError("Invalid ticker symbol. Please enter a valid symbol (1-5 letters).");
        verify(dataAccess, never()).addToWatchlist(anyString(), anyString());
    }

    @Test
    void addTicker_WithNonExistentTicker_ShouldPresentError() throws Exception {
        // Arrange
        WatchlistInputData inputData = new WatchlistInputData(username, "XYZ");

        try (MockedStatic<TickerCache> tickerCache = mockStatic(TickerCache.class)) {
            // Mock the static method
            tickerCache.when(() -> TickerCache.isValidTicker("XYZ")).thenReturn(false);

            // Act
            interactor.addTicker(inputData);

            // Assert
            verify(presenter).presentError("XYZ is not a real stock ticker.");
            verify(dataAccess, never()).addToWatchlist(anyString(), anyString());
        }
    }

    @Test
    void addTicker_WithValidTicker_ShouldAddAndFetchWatchlist() throws Exception {
        // Arrange
        WatchlistInputData inputData = new WatchlistInputData(username, "AAPL");
        List<String> tickers = Arrays.asList("AAPL", "MSFT");

        try (MockedStatic<TickerCache> tickerCache = mockStatic(TickerCache.class)) {
            // Mock the static method
            tickerCache.when(() -> TickerCache.isValidTicker("AAPL")).thenReturn(true);

            // Mock data access responses
            when(dataAccess.getWatchlist(username)).thenReturn(tickers);

            // Act
            interactor.addTicker(inputData);

            // Assert
            verify(dataAccess).addToWatchlist(username, "AAPL");
            verify(presenter).presentSuccess("AAPL added to watchlist");

            // Verify fetchWatchlist was called and output data was created correctly
            ArgumentCaptor<WatchlistOutputData> outputDataCaptor = ArgumentCaptor.forClass(WatchlistOutputData.class);
            verify(presenter).presentWatchlist(outputDataCaptor.capture());

            WatchlistOutputData outputData = outputDataCaptor.getValue();
            assertEquals(username, outputData.getUsername());
            assertEquals(tickers, outputData.getTickers());
        }
    }

    @Test
    void addTicker_WithDataAccessException_ShouldPresentError() throws Exception {
        // Arrange
        WatchlistInputData inputData = new WatchlistInputData(username, "AAPL");
        String errorMessage = "Database connection error";
        Exception exception = new Exception(errorMessage);

        try (MockedStatic<TickerCache> tickerCache = mockStatic(TickerCache.class)) {
            // Mock the static method
            tickerCache.when(() -> TickerCache.isValidTicker("AAPL")).thenReturn(true);

            // Mock data access to throw exception
            doThrow(exception).when(dataAccess).addToWatchlist(username, "AAPL");

            // Act
            interactor.addTicker(inputData);

            // Assert
            verify(presenter).presentError("Error adding ticker to watchlist: " + errorMessage);
        }
    }

    @Test
    void removeTicker_Success_ShouldRemoveAndFetchWatchlist() throws Exception {
        // Arrange
        WatchlistInputData inputData = new WatchlistInputData(username, "AAPL");
        List<String> tickers = Arrays.asList("MSFT");

        // Mock data access responses
        when(dataAccess.getWatchlist(username)).thenReturn(tickers);

        // Act
        interactor.removeTicker(inputData);

        // Assert
        verify(dataAccess).removeFromWatchlist(username, "AAPL");
        verify(presenter).presentSuccess("AAPL removed from watchlist successfully!");

        // Verify fetchWatchlist was called
        ArgumentCaptor<WatchlistOutputData> outputDataCaptor = ArgumentCaptor.forClass(WatchlistOutputData.class);
        verify(presenter).presentWatchlist(outputDataCaptor.capture());

        WatchlistOutputData outputData = outputDataCaptor.getValue();
        assertEquals(username, outputData.getUsername());
        assertEquals(tickers, outputData.getTickers());
    }

    @Test
    void removeTicker_WithDataAccessException_ShouldPresentError() throws Exception {
        // Arrange
        WatchlistInputData inputData = new WatchlistInputData(username, "AAPL");
        String errorMessage = "Database connection error";
        Exception exception = new Exception(errorMessage);

        // Mock data access to throw exception
        doThrow(exception).when(dataAccess).removeFromWatchlist(username, "AAPL");

        // Act
        interactor.removeTicker(inputData);

        // Assert
        verify(presenter).presentError("Error removing ticker from watchlist: " + errorMessage);
    }

    @Test
    void fetchWatchlist_Success_ShouldPresentWatchlist() throws Exception {
        // Arrange
        List<String> tickers = Arrays.asList("AAPL", "MSFT", "GOOG");

        // Mock data access response
        when(dataAccess.getWatchlist(username)).thenReturn(tickers);

        // Act
        interactor.fetchWatchlist(username);

        // Assert
        ArgumentCaptor<WatchlistOutputData> outputDataCaptor = ArgumentCaptor.forClass(WatchlistOutputData.class);
        verify(presenter).presentWatchlist(outputDataCaptor.capture());

        WatchlistOutputData outputData = outputDataCaptor.getValue();
        assertEquals(username, outputData.getUsername());
        assertEquals(tickers, outputData.getTickers());
    }

    @Test
    void fetchWatchlist_WithDataAccessException_ShouldPresentError() throws Exception {
        // Arrange
        String errorMessage = "Database connection error";
        Exception exception = new Exception(errorMessage);

        // Mock data access to throw exception
        when(dataAccess.getWatchlist(username)).thenThrow(exception);

        // Act
        interactor.fetchWatchlist(username);

        // Assert
        verify(presenter).presentError("Error loading watchlist: " + errorMessage);
    }
}
