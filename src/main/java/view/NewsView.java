package view;

import javax.swing.*;
import java.awt.*;
import interface_adapter.news.NewsViewModel;
import interface_adapter.news.NewsController;
import interface_adapter.news.NewsState;
import interface_adapter.navigation.NavigationController;
import view.ui.ButtonFactory;
import view.ui.LabelFactory;
import view.ui.FieldFactory;
import java.awt.Desktop;
import java.net.URI;

public class NewsView extends BaseView {
    private final NewsViewModel newsViewModel;
    private final JPanel newsPanel;
    private NewsController newsController;
    private final NavigationController navigationController;
    private final JLabel titleLabel = LabelFactory.createTitleLabel("Stock Market News");
    private final JLabel usernameLabel = LabelFactory.createLabel("");
    private final JTextField searchField;
    private final JButton searchButton;

    public NewsView(NewsViewModel newsViewModel, NavigationController navigationController) {
        super("news");
        this.newsViewModel = newsViewModel;
        this.navigationController = navigationController;

        // Create search field with placeholder
        this.searchField = FieldFactory.createTextField();
        this.searchField.setText("Enter stock ticker (e.g., AAPL, GOOGL)");
        this.searchField.setForeground(Color.GRAY);
        
        // Add focus listeners for placeholder behavior
        this.searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchField.getText().equals("Enter stock ticker (e.g., AAPL, GOOGL)")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Enter stock ticker (e.g., AAPL, GOOGL)");
                    searchField.setForeground(Color.GRAY);
                }
            }
        });

        this.searchButton = ButtonFactory.createStyledButton("Search");

        // Create main content panel with gradient
        JPanel contentPanel = createGradientContentPanel();
        contentPanel.setLayout(new BorderLayout(10, 10));

        // Create top container for title and search
        JPanel topContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
        topContainer.setOpaque(false);

        // Add title
        topContainer.add(titleLabel);
        topContainer.add(Box.createVerticalStrut(10));
        topContainer.add(usernameLabel);

        // Create search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setOpaque(false);

        // Add search components to search panel
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Add search panel to top container
        topContainer.add(Box.createVerticalStrut(10));
        topContainer.add(searchPanel);
        topContainer.add(Box.createVerticalStrut(15));

        // News content panel with styling
        newsPanel = new JPanel();
        newsPanel.setLayout(new BoxLayout(newsPanel, BoxLayout.Y_AXIS));
        newsPanel.setOpaque(false);

        // Create scroll pane
        JScrollPane scrollPane = new JScrollPane(newsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBackground(new Color(15, 25, 55));
        scrollPane.getViewport().setBackground(new Color(15, 25, 55));
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);

        // Style scroll bar
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setUnitIncrement(16);
        verticalBar.setBackground(new Color(30, 40, 80));
        verticalBar.setForeground(new Color(60, 90, 150));

        // Add components to main layout
        contentPanel.add(topContainer, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        this.add(contentPanel);

        // Wire up search functionality
        setupSearchListeners();

        // Listen for news updates
        newsViewModel.addPropertyChangeListener(evt -> {
            NewsState state = newsViewModel.getState();
            updateNewsPanel(state);
        });
    }

    private void setupSearchListeners() {
        // Handle search button click
        searchButton.addActionListener(e -> performSearch());

        // Handle Enter key in search field
        searchField.addActionListener(e -> performSearch());
    }

    private void performSearch() {
        String query = searchField.getText().trim();
        if (!query.isEmpty() && !query.equals("Enter stock ticker (e.g., AAPL, GOOGL)") && newsController != null) {
            newsController.executeSearch(query.toUpperCase());
            // Reset placeholder after search
            searchField.setText("Enter stock ticker (e.g., AAPL, GOOGL)");
            searchField.setForeground(Color.GRAY);
        }
    }

    private void updateNewsPanel(NewsState state) {
        newsPanel.removeAll();

        for (String[] newsItem : state.getNewsItems()) {
            String title = newsItem[0];
            String summary = newsItem.length > 1 ? newsItem[1] : "";
            String url = newsItem.length > 2 ? newsItem[2] : "";

            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
            itemPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(30, 60, 120), 1),
                    BorderFactory.createEmptyBorder(15, 15, 15, 15)));
            itemPanel.setBackground(new Color(20, 30, 70));
            itemPanel.setMaximumSize(new Dimension(800, 150));
            itemPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel titleLabel = new JLabel(title);
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setFont(new Font("Sans Serif", Font.BOLD, 14));
            titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JTextArea descArea = new JTextArea(summary);
            descArea.setWrapStyleWord(true);
            descArea.setLineWrap(true);
            descArea.setEditable(false);
            descArea.setForeground(new Color(200, 200, 200));
            descArea.setBackground(new Color(20, 30, 70));
            descArea.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
            descArea.setFont(new Font("Sans Serif", Font.PLAIN, 12));
            descArea.setAlignmentX(Component.LEFT_ALIGNMENT);

            if (url != null && !url.isBlank()) {
                // Make clickable
                itemPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                itemPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent e) {
                        itemPanel.setBackground(new Color(35, 50, 90));
                        itemPanel.repaint();
                    }

                    @Override
                    public void mouseExited(java.awt.event.MouseEvent e) {
                        itemPanel.setBackground(new Color(20, 30, 70));
                        itemPanel.repaint();
                    }

                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        try {
                            if (Desktop.isDesktopSupported()) {
                                Desktop.getDesktop().browse(new URI(url));
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(NewsView.this,
                                    "Could not open link: " + ex.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                titleLabel.setText("🔗 " + title);
            }

            itemPanel.add(titleLabel);
            itemPanel.add(Box.createVerticalStrut(8));
            itemPanel.add(descArea);

            newsPanel.add(Box.createVerticalStrut(15));
            newsPanel.add(itemPanel);
        }

        newsPanel.add(Box.createVerticalStrut(15));
        newsPanel.revalidate();
        newsPanel.repaint();
    }

    public String getViewName() {
        return "news";
    }

    public void setNewsController(NewsController newsController) {
        this.newsController = newsController;
    }
}
