# Search Feature Implementation

## Overview
This document describes the implementation of a stock search feature for the Fundi application using the Alpha Vantage API.

## Architecture

The search feature follows Clean Architecture principles with clear separation of concerns:

### Entities
- **SearchResult**: Represents a single search result from the stock API

### Use Cases
- **SearchInputData**: Input data transfer object
- **SearchOutputData**: Output data transfer object
- **SearchInputBoundary**: Interface for the search use case
- **SearchOutputBoundary**: Interface for presenting search results
- **SearchDataAccessInterface**: Interface for external API access
- **GetMatches**: Main search interactor that orchestrates the search operation

### Interface Adapters
- **SearchController**: Handles search requests from the UI
- **SearchPresenter**: Formats search results for the UI
- **SearchViewModel**: Manages search state and notifies view of changes
- **SearchState**: Holds current search state (results, loading, errors)

### Data Access
- **APISearchDataAccessObject**: Implementation that calls Alpha Vantage API

### View
- **MainView**: Updated to display search results and handle user interactions

## API Integration

The search feature uses the Alpha Vantage API with the `SYMBOL_SEARCH` function:
- API Key: Read from `data/alpha_key.txt`
- Endpoint: `https://www.alphavantage.co/query`
- Function: `SYMBOL_SEARCH`

## User Experience

1. User enters a search query in the search bar
2. On submit (Enter or Search button), the query is sent to the search controller
3. Results are displayed in the main panel, replacing the default buttons
4. User can return to the main view using the "Back to Main" button
5. Error states are handled gracefully with appropriate messaging

## Testing

Unit tests are provided for the search use case (`GetMatchesTest.java`) covering:
- Successful search scenarios
- Empty query validation
- API failure handling

## Error Handling

The implementation handles several error scenarios:
- Empty or null search queries
- API failures (network, rate limits, invalid responses)
- JSON parsing errors
- Invalid API responses

## Dependencies

- **OkHttp**: For HTTP requests to the Alpha Vantage API
- **org.json**: For parsing JSON responses
- **JUnit 5**: For unit testing

## Future Enhancements

1. **Caching**: Implement local caching to reduce API calls
2. **Pagination**: Handle large result sets with pagination
3. **Advanced Filtering**: Add filters for asset type, region, etc.
4. **Search History**: Keep track of recent searches
5. **Autocomplete**: Implement search suggestions as user types
6. **Result Actions**: Add actions like "Add to Watchlist" or "View Details"
