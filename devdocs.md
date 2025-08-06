# Documentation for Developers

Follow these conventions!!

## View Management

The application uses a `CardLayout` to manage different views. A `ViewManager` is responsible for switching between views based on user actions and application state changes. Each view has a corresponding `ViewModel` that holds the data to be displayed.

## Data Flow

1. User Interaction: The user interacts with a `View` (e.g., clicks a button).
2. Controller: The `View` calls a method on a `Controller`.
3. Use Case Interactor: The `Controller` builds an `InputData` object and passes it to a `Use Case Interactor`.
4. Entity & Data Access: The `Interactor` processes the request, interacts with `Entities`, and uses a `DataAccessInterface` to retrieve or store data.
5. Presenter: The `Interactor` passes an `OutputData` object to a `Presenter`.
6. ViewModel: The `Presenter` updates a `ViewModel` with the new data.
7. View Update: The `View` listens for changes in the `ViewModel` and updates itself accordingly.
