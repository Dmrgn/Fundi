# Fundi

CSC207 final Project.
By Aaron [], Daniel Morgan, Krish [], Arham [] [] [], and Varak Tanashian

## Setup

### Requirements

The following are required to build and run:

- **Java Development Kit (JDK) 17 or newer**
  - Ensure that Java is installed.
  - Ensure `JAVA_HOME` environment variable is set. Sometimes done automatically sometimes not.
- **Maven 3.8 or higher**
  - For building the project.
- **SQLite JDBC driver**
  - Automatically downloaded via Maven (no manual installation required).
- **

### Running the Application

From the main directory,

1. Run the following to compile to a jar:

    ```powershell
    mvn package
    ```

2. Run the following to ensure dependencies are installed correctly:

    ```powershell
    mvn dependency:copy-dependencies
    ```

3. Run the following to run the program.

    ```powershell
    java -cp target/fundi-1.0-SNAPSHOT.jar app.Main
    ```

- Note: Only need to run steps 1 and 2 once.

- For now (developers), the following command will re-compile and run the app in one step.

```powershell
mvn package; mvn dependency:copy-dependencies; java -cp "target/fundi-1.0-SNAPSHOT.jar;target/dependency/*" app.Main;
```
# do this to build the app
mvn package
# do this to run the app
mvn -q exec:java -Dexec.mainClass="app.Main"
```
