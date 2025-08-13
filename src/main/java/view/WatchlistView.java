package view;

import interfaceadapter.main.MainViewModel;
import interfaceadapter.watchlist.WatchlistController;
import interfaceadapter.watchlist.WatchlistState;
import interfaceadapter.watchlist.WatchlistViewModel;
import view.ui.UiConstants;
import view.watchlist.AddTickerPanel;
import view.watchlist.WatchlistListPanel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class WatchlistView extends BaseView implements PropertyChangeListener {
    private final MainViewModel mainViewModel;
    private final WatchlistViewModel watchlistViewModel;
    private final WatchlistController watchlistController;
    private final AddTickerPanel addTickerPanel;
    private final WatchlistListPanel listPanel;

    public WatchlistView(MainViewModel mainViewModel, WatchlistViewModel watchlistViewModel,
            WatchlistController watchlistController) {
        super("watchlist");
        this.mainViewModel = mainViewModel;
        this.watchlistViewModel = watchlistViewModel;
        this.watchlistController = watchlistController;

        this.mainViewModel.addPropertyChangeListener(this);
        this.watchlistViewModel.addPropertyChangeListener(this);

        // Initialize components
        this.addTickerPanel = new AddTickerPanel(this.watchlistController);
        this.listPanel = new WatchlistListPanel(this.watchlistController);

        initializeUI();
        // Initialize username for input panel if already logged in
        if (this.mainViewModel.getState() != null && this.mainViewModel.getState().getUsername() != null) {
            String username = this.mainViewModel.getState().getUsername();
            this.addTickerPanel.setCurrentUsername(username);
        }

        // Load watchlist when view becomes visible
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                String username = mainViewModel.getState().getUsername();
                if (username != null && !username.isEmpty()) {
                    WatchlistView.this.watchlistController.refreshWatchlist(username);
                }
            }
        });
    }

    private void initializeUI() {

        // Header
        JLabel titleLabel = new JLabel("Watchlist");
        titleLabel.setFont(UiConstants.Fonts.TITLE);
        titleLabel.setForeground(UiConstants.Colors.ON_PRIMARY);
        JPanel headerLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, UiConstants.Spacing.LG, UiConstants.Spacing.SM));
        headerLeft.setOpaque(false);
        headerLeft.add(titleLabel);
        header.add(headerLeft, BorderLayout.WEST);

        // Main content panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(UiConstants.Colors.CANVAS_BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(
                UiConstants.Spacing.XL,
                UiConstants.Spacing.XL,
                UiConstants.Spacing.XL,
                UiConstants.Spacing.XL));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(UiConstants.Spacing.SM, UiConstants.Spacing.SM, UiConstants.Spacing.SM,
                UiConstants.Spacing.SM);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;

        // Description
        JLabel descriptionLabel = new JLabel("Track your favorite stocks and monitor their performance.");
        descriptionLabel.setFont(UiConstants.Fonts.BODY);
        descriptionLabel.setForeground(UiConstants.Colors.TEXT_PRIMARY);
        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Layout components
        int row = 0;
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 1;
        gbc.weighty = 0;
        mainPanel.add(descriptionLabel, gbc);

        gbc.gridy = row++;
        mainPanel.add(Box.createVerticalStrut(UiConstants.Spacing.XL), gbc);

        gbc.gridy = row++;
        mainPanel.add(addTickerPanel, gbc);

        gbc.gridy = row++;
        mainPanel.add(Box.createVerticalStrut(UiConstants.Spacing.LG), gbc);

        gbc.gridy = row++;
        gbc.weighty = 1.0;
        mainPanel.add(listPanel, gbc);

        // Add to BaseView content area
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBackground(UiConstants.Colors.CANVAS_BG);
        scrollPane.getViewport().setBackground(UiConstants.Colors.CANVAS_BG);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        content.add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object source = evt.getSource();
        if (source == watchlistViewModel) {
            WatchlistState state = watchlistViewModel.getState();
            // Keep input panel in sync with current username
            addTickerPanel.setCurrentUsername(state.getUsername());
            // Render list
            listPanel.refreshWatchlist(state);
            // Surface messages
            if (state.getErrorMessage() != null && !state.getErrorMessage().isEmpty()) {
                JOptionPane.showMessageDialog(this, state.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            if (state.getSuccessMessage() != null && !state.getSuccessMessage().isEmpty()) {
                JOptionPane.showMessageDialog(this, state.getSuccessMessage(), "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (source == mainViewModel) {
            // Update username and refresh list on login/logout
            String username = mainViewModel.getState() != null ? mainViewModel.getState().getUsername() : null;
            addTickerPanel.setCurrentUsername(username);
            if (username != null && !username.isEmpty()) {
                watchlistController.refreshWatchlist(username);
            } else {
                // Clear list if logged out
                WatchlistState empty = new WatchlistState();
                empty.setUsername("");
                listPanel.refreshWatchlist(empty);
            }
        }
    }
}
