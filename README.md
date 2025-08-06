# Fundi

CSC207 final Project.
By Aaron Avram, Daniel Morgan, Krish Patel, Abdallah Arham Wajid Mohammed, and Varak Tanashian

![Logo](resources/logo.png)

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

3. Run the following to run the program (Windows):

    ```powershell
    java -cp "target/fundi-1.0-SNAPSHOT.jar;target/dependency/*" app.Main
    ```

   For Mac/Linux, use a colon (:) instead of a semicolon (;):

    ```bash
    java -cp "target/fundi-1.0-SNAPSHOT.jar:target/dependency/*" app.Main
    ```

- Note: Only need to run steps 1 and 2 once.

- For now (developers), the following command will re-compile and run the app in one step (Windows):

```powershell
mvn package; mvn dependency:copy-dependencies; java -cp "target/fundi-1.0-SNAPSHOT.jar;target/dependency/*" app.Main;
```

For Mac/Linux:

```bash
mvn package && mvn dependency:copy-dependencies && java -cp "target/fundi-1.0-SNAPSHOT.jar:target/dependency/*" app.Main
```

---

**Troubleshooting:**

- If you see `Error: Could not find or load main class app.Main`, ensure:
  - The `Main.java` file is in the `app` package and the first line is `package app;`.
  - The compiled JAR contains `app/Main.class`.
  - You are using the correct classpath separator for your OS (see above).
