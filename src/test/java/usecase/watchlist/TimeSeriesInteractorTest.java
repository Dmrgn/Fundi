package usecase.watchlist;

import entity.TimeSeriesPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TimeSeriesInteractorTest {

    @Mock
    private TimeSeriesDataAccess dataAccess;

    @Mock
    private TimeSeriesOutputBoundary presenter;

    private TimeSeriesInteractor interactor;

    @BeforeEach
    void setUp() {
        interactor = new TimeSeriesInteractor(dataAccess, presenter);
    }

    @Test
    void execute_WithValidRequest_ShouldPresentSuccessResponse() throws Exception {
        // Arrange
        String symbol = "AAPL";
        String range = "1M";
        TimeSeriesRequest request = new TimeSeriesRequest(symbol, range);

        List<TimeSeriesPoint> mockPoints = Arrays.asList(
                new TimeSeriesPoint(1640995200000L, 150.0), // Jan 1, 2022
                new TimeSeriesPoint(1641081600000L, 152.0), // Jan 2, 2022
                new TimeSeriesPoint(1641168000000L, 148.0) // Jan 3, 2022
        );

        when(dataAccess.getTimeSeries(symbol, range)).thenReturn(mockPoints);

        // Act
        interactor.execute(request);

        // Assert
        ArgumentCaptor<TimeSeriesResponse> responseCaptor = ArgumentCaptor.forClass(TimeSeriesResponse.class);
        verify(presenter).present(responseCaptor.capture());

        TimeSeriesResponse response = responseCaptor.getValue();
        assertEquals(symbol, response.symbol);
        assertEquals(mockPoints, response.points);
        assertNull(response.error);
        assertEquals(range, response.range);
    }

    @Test
    void execute_WithEmptyDataResponse_ShouldPresentEmptySuccessResponse() throws Exception {
        // Arrange
        String symbol = "TEST";
        String range = "1W";
        TimeSeriesRequest request = new TimeSeriesRequest(symbol, range);

        List<TimeSeriesPoint> emptyPoints = Arrays.asList();
        when(dataAccess.getTimeSeries(symbol, range)).thenReturn(emptyPoints);

        // Act
        interactor.execute(request);

        // Assert
        ArgumentCaptor<TimeSeriesResponse> responseCaptor = ArgumentCaptor.forClass(TimeSeriesResponse.class);
        verify(presenter).present(responseCaptor.capture());

        TimeSeriesResponse response = responseCaptor.getValue();
        assertEquals(symbol, response.symbol);
        assertEquals(emptyPoints, response.points);
        assertNull(response.error);
        assertEquals(range, response.range);
    }

    @Test
    void execute_WithDataAccessException_ShouldPresentErrorResponse() throws Exception {
        // Arrange
        String symbol = "INVALID";
        String range = "3M";
        String errorMessage = "API rate limit exceeded";
        TimeSeriesRequest request = new TimeSeriesRequest(symbol, range);

        when(dataAccess.getTimeSeries(symbol, range)).thenThrow(new Exception(errorMessage));

        // Act
        interactor.execute(request);

        // Assert
        ArgumentCaptor<TimeSeriesResponse> responseCaptor = ArgumentCaptor.forClass(TimeSeriesResponse.class);
        verify(presenter).present(responseCaptor.capture());

        TimeSeriesResponse response = responseCaptor.getValue();
        assertEquals(symbol, response.symbol);
        assertEquals(List.of(), response.points);
        assertEquals(errorMessage, response.error);
        assertEquals(range, response.range);
    }

    @Test
    void execute_WithNullPointerException_ShouldPresentErrorResponse() throws Exception {
        // Arrange
        String symbol = "NULL";
        String range = "1Y";
        TimeSeriesRequest request = new TimeSeriesRequest(symbol, range);

        when(dataAccess.getTimeSeries(symbol, range)).thenThrow(new NullPointerException("Null pointer error"));

        // Act
        interactor.execute(request);

        // Assert
        ArgumentCaptor<TimeSeriesResponse> responseCaptor = ArgumentCaptor.forClass(TimeSeriesResponse.class);
        verify(presenter).present(responseCaptor.capture());

        TimeSeriesResponse response = responseCaptor.getValue();
        assertEquals(symbol, response.symbol);
        assertEquals(List.of(), response.points);
        assertEquals("Null pointer error", response.error);
        assertEquals(range, response.range);
    }

    @Test
    void execute_WithDifferentRanges_ShouldHandleAllRangeTypes() throws Exception {
        // Test all supported ranges
        String[] ranges = { "1W", "1M", "3M", "6M", "1Y" };
        String symbol = "MSFT";

        for (String range : ranges) {
            // Arrange
            TimeSeriesRequest request = new TimeSeriesRequest(symbol, range);
            List<TimeSeriesPoint> mockPoints = Arrays.asList(
                    new TimeSeriesPoint(System.currentTimeMillis(), 200.0));
            when(dataAccess.getTimeSeries(symbol, range)).thenReturn(mockPoints);

            // Act
            interactor.execute(request);

            // Assert
            ArgumentCaptor<TimeSeriesResponse> responseCaptor = ArgumentCaptor.forClass(TimeSeriesResponse.class);
            verify(presenter, atLeastOnce()).present(responseCaptor.capture());

            TimeSeriesResponse response = responseCaptor.getValue();
            assertEquals(range, response.range);
        }
    }

    @Test
    void execute_VerifyDataAccessMethodCalled() throws Exception {
        // Arrange
        String symbol = "GOOG";
        String range = "1M";
        TimeSeriesRequest request = new TimeSeriesRequest(symbol, range);

        when(dataAccess.getTimeSeries(symbol, range)).thenReturn(List.of());

        // Act
        interactor.execute(request);

        // Assert
        verify(dataAccess, times(1)).getTimeSeries(symbol, range);
        verify(presenter, times(1)).present(any(TimeSeriesResponse.class));
    }
}
