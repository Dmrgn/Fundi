package dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Centralized, idempotent database schema initializer.
 * Ensures all required tables exist before DAOs run queries.
 */
public final class DatabaseInitializer {
    private static volatile boolean initialized = false;

    private DatabaseInitializer() {}

    public static void ensureInitialized() {
        if (initialized) return;
        synchronized (DatabaseInitializer.class) {
            if (initialized) return;
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:data/fundi.sqlite");
                 Statement statement = connection.createStatement()) {
                // Enforce foreign keys
                statement.execute("PRAGMA foreign_keys = ON");

                String schema = """
                        CREATE TABLE IF NOT EXISTS users (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            username TEXT,
                            password TEXT
                        );

                        CREATE TABLE IF NOT EXISTS portfolios (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            name TEXT,
                            user_id INTEGER REFERENCES users,
                            balance REAL NOT NULL DEFAULT 10000
                        );

                        CREATE TABLE IF NOT EXISTS stocks (
                            name TEXT,
                            price REAL,
                            date DATE,
                            CONSTRAINT key PRIMARY KEY (name, date)
                        );

                        CREATE TABLE IF NOT EXISTS transactions (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            portfolio_id INTEGER REFERENCES portfolios,
                            stock_name TEXT,
                            amount INTEGER,
                            date DATE,
                            price REAL
                        );

                        CREATE TABLE IF NOT EXISTS holdings (
                            portfolio_id INTEGER NOT NULL REFERENCES portfolios(id),
                            ticker TEXT NOT NULL,
                            quantity INTEGER NOT NULL DEFAULT 0,
                            PRIMARY KEY (portfolio_id, ticker),
                            CHECK (quantity >= 0)
                        );

                        CREATE TABLE IF NOT EXISTS watchlist (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            user_id INTEGER NOT NULL REFERENCES users(id),
                            ticker TEXT NOT NULL,
                            UNIQUE(user_id, ticker)
                        );

                        CREATE TABLE IF NOT EXISTS user_settings (
                            user_id INTEGER NOT NULL REFERENCES users(id),
                            key TEXT NOT NULL,
                            value TEXT NOT NULL,
                            PRIMARY KEY (user_id, key)
                        );
                        """;

                for (String stmt : schema.split(";")) {
                    String trimmed = stmt.trim();
                    if (!trimmed.isEmpty()) {
                        statement.execute(trimmed);
                    }
                }

                initialized = true;
            } catch (SQLException e) {
                System.out.println("Schema initialization error: " + e.getMessage());
            }
        }
    }
}
