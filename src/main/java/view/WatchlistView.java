package view;

import view.ui.PanelFactory;
import view.ui.ButtonFactory;
import view.ui.FieldFactory;
import data_access.DBUserDataAccessObject;
import data_access.TickerCache;
import interface_adapter.main.MainViewModel;
import interface_adapter.main.MainState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class WatchlistView extends BaseView implements PropertyChangeListener {
    private final DBUserDataAccessObject userDao;
    private final MainViewModel mainViewModel;
    private JPanel watchlistPanel;
    private JTextField tickerField;

    public WatchlistView(MainViewModel mainViewModel, DBUserDataAccessObject userDao) {
        super("watchlist");
        this.userDao = userDao;
        this.mainViewModel = mainViewModel;

        this.mainViewModel.addPropertyChangeListener(this);

        JPanel contentPanel = createGradientContentPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        this.add(contentPanel, BorderLayout.CENTER);

        // Title
        contentPanel.add(PanelFactory.createTitlePanel("Watchlist"));
        contentPanel.add(Box.createVerticalStrut(30));

        JLabel descriptionLabel = new JLabel("Track your favorite stocks and monitor their performance.");
        descriptionLabel.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        descriptionLabel.setForeground(new Color(200, 200, 200));
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(descriptionLabel);
        contentPanel.add(Box.createVerticalStrut(20));

        // Add ticker input section
        JPanel addTickerPanel = createAddTickerPanel();
        contentPanel.add(addTickerPanel);
        contentPanel.add(Box.createVerticalStrut(20));

        // Watchlist display panel
        watchlistPanel = new JPanel();
        watchlistPanel.setLayout(new BoxLayout(watchlistPanel, BoxLayout.Y_AXIS));
        watchlistPanel.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(watchlistPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        contentPanel.add(scrollPane);

        contentPanel.add(Box.createVerticalGlue());

        // Load initial watchlist
        refreshWatchlist();
    }

    private JPanel createAddTickerPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setOpaque(false);

        tickerField = FieldFactory.createTextField();
        tickerField.setPreferredSize(new Dimension(200, 30));

        // Set placeholder text behavior
        tickerField.setText("Enter ticker symbol (e.g., AAPL)");
        tickerField.setForeground(Color.GRAY);
        tickerField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (tickerField.getText().equals("Enter ticker symbol (e.g., AAPL)")) {
                    tickerField.setText("");
                    tickerField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (tickerField.getText().isEmpty()) {
                    tickerField.setText("Enter ticker symbol (e.g., AAPL)");
                    tickerField.setForeground(Color.GRAY);
                }
            }
        });

        JButton addButton = ButtonFactory.createStyledButton("Add to Watchlist");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTicker();
            }
        });

        panel.add(tickerField);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(addButton);

        return panel;
    }

    private void addTicker() {
        String ticker = tickerField.getText().trim().toUpperCase();
        if (ticker.isEmpty() || ticker.equals("ENTER TICKER SYMBOL (E.G., AAPL)")) {
            JOptionPane.showMessageDialog(this, "Please enter a ticker symbol.", "Invalid Input",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Regex validation for ticker symbol
        if (!ticker.matches("^[A-Z]{1,5}$")) {
            JOptionPane.showMessageDialog(this, "Invalid ticker symbol. Please enter a valid symbol (1-5 letters).",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Finnhub API validation using cached ticker list
        boolean isValidTicker = validateTickerWithFinnhubAPI(ticker);
        if (!isValidTicker) {
            JOptionPane.showMessageDialog(this, ticker + " is not a real stock ticker.", "Invalid Ticker",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        MainState mainState = mainViewModel.getState();
        String username = mainState.getUsername();

        try {
            userDao.addToWatchlist(username, ticker);
            tickerField.setText("Enter ticker symbol (e.g., AAPL)");
            tickerField.setForeground(Color.GRAY);
            refreshWatchlist();
            JOptionPane.showMessageDialog(this, ticker + " added to watchlist", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding ticker to watchlist: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Ticker validation using Finnhub cached ticker list
    private boolean validateTickerWithFinnhubAPI(String ticker) {
        return TickerCache.isValidTicker(ticker);
    }

    private void removeTicker(String ticker) {
        MainState mainState = mainViewModel.getState();
        String username = mainState.getUsername();

        try {
            userDao.removeFromWatchlist(username, ticker);
            refreshWatchlist();
            JOptionPane.showMessageDialog(this, ticker + " removed from watchlist successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error removing ticker from watchlist: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshWatchlist() {
        watchlistPanel.removeAll();

        MainState mainState = mainViewModel.getState();
        if (mainState == null || mainState.getUsername() == null || mainState.getUsername().isEmpty()) {
            JLabel noUserLabel = new JLabel("Please log in to view your watchlist.");
            noUserLabel.setFont(new Font("Sans Serif", Font.ITALIC, 14));
            noUserLabel.setForeground(new Color(150, 150, 150));
            noUserLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            watchlistPanel.add(noUserLabel);
            watchlistPanel.revalidate();
            watchlistPanel.repaint();
            return;
        }

        String username = mainState.getUsername();

        try {
            List<String> watchlist = userDao.getWatchlist(username);

            if (watchlist.isEmpty()) {
                JLabel emptyLabel = new JLabel("Your watchlist is empty. Add some tickers above!");
                emptyLabel.setFont(new Font("Sans Serif", Font.ITALIC, 14));
                emptyLabel.setForeground(new Color(150, 150, 150));
                emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                watchlistPanel.add(emptyLabel);
            } else {
                for (String ticker : watchlist) {
                    JPanel tickerPanel = createTickerPanel(ticker);
                    watchlistPanel.add(tickerPanel);
                    watchlistPanel.add(Box.createVerticalStrut(5));
                }
            }
        } catch (Exception e) {
            JLabel errorLabel = new JLabel("Error loading watchlist: " + e.getMessage());
            errorLabel.setFont(new Font("Sans Serif", Font.PLAIN, 14));
            errorLabel.setForeground(Color.RED);
            errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            watchlistPanel.add(errorLabel);
        }

        watchlistPanel.revalidate();
        watchlistPanel.repaint();
    }

    private JPanel createTickerPanel(String ticker) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel tickerLabel = new JLabel(ticker);
        tickerLabel.setFont(new Font("Sans Serif", Font.BOLD, 16));
        tickerLabel.setForeground(Color.WHITE);

        JButton removeButton = ButtonFactory.createStyledButton("Remove");
        removeButton.addActionListener(e -> removeTicker(ticker));

        panel.add(tickerLabel, BorderLayout.WEST);
        panel.add(removeButton, BorderLayout.EAST);

        // Add a subtle border
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 100), 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));

        return panel;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Refresh watchlist when main state changes (e.g., user logs in/out)
        refreshWatchlist();
    }
}
