package usecase.notifications;

import view.TabbedMainView;

import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Manages notifications for the application
 */
public class NotificationManager {
    private static NotificationManager instance;
    private TabbedMainView mainView;
    private final List<String> notifications;
    private final NewsNotificationService newsService;

    private NotificationManager() {
        this.notifications = new ArrayList<>();
        try {
            this.newsService = new NewsNotificationService();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize news service", e);
        }
    }

    public static NotificationManager getInstance() {
        if (instance == null) {
            instance = new NotificationManager();
        }
        return instance;
    }

    public void setMainView(TabbedMainView mainView) {
        this.mainView = mainView;
    }

    /**
     * Check for news after a stock purchase and notify if found
     * @param ticker The purchased stock ticker
     * @param quantity The quantity purchased
     */
    public void checkNewsAfterPurchase(String ticker, int quantity) {
        if (mainView == null) return;

        // Run news check in background to avoid blocking UI
        CompletableFuture.runAsync(() -> {
            try {
                boolean hasNews = newsService.hasRecentNews(ticker);
                if (hasNews) {
                    String summary = newsService.getNewsSummary(ticker);
                    String notificationText = summary != null ? 
                        summary : 
                        "Recent news available for " + ticker;
                    
                    // Add notification on UI thread
                    SwingUtilities.invokeLater(() -> {
                        notifications.add("📰 " + notificationText + " - Check News tab");
                        mainView.addNotification();
                        System.out.println("News notification added for " + ticker);
                    });
                }
            } catch (Exception e) {
                System.err.println("Error checking news after purchase: " + e.getMessage());
            }
        });
    }

    /**
     * Get all current notifications
     * @return List of notification messages
     */
    public List<String> getNotifications() {
        return new ArrayList<>(notifications);
    }

    /**
     * Clear all notifications
     */
    public void clearNotifications() {
        notifications.clear();
        if (mainView != null) {
            mainView.clearNotifications();
        }
    }

    /**
     * Add a custom notification
     * @param message The notification message
     */
    public void addNotification(String message) {
        notifications.add(message);
        if (mainView != null) {
            mainView.addNotification();
        }
    }
}
