package view;

import javax.swing.*;
import java.awt.*;
import interface_adapter.news.NewsViewModel;
import interface_adapter.news.NewsController;
import interface_adapter.news.NewsState;
import interface_adapter.navigation.NavigationController;
import view.ui.ButtonFactory;
import view.ui.LabelFactory;

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

        // Create and style search field
        searchField = new JTextField(25);
        searchField.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(30, 60, 120), 1),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));

        // Create and style search button
        searchButton = ButtonFactory.createStyledButton("Search News");

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
        if (!query.isEmpty() && newsController != null) {
            // Convert query to uppercase for consistency with stock symbols
            newsController.executeSearch(query.toUpperCase());
            searchField.setText(""); // Clear the search field after searching
        }
    }

    private void updateNewsPanel(NewsState state) {
        newsPanel.removeAll();

        for (String[] newsItem : state.getNewsItems()) {
            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
            itemPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(30, 60, 120), 1),
                    BorderFactory.createEmptyBorder(15, 15, 15, 15)));
            itemPanel.setBackground(new Color(20, 30, 70));
            itemPanel.setMaximumSize(new Dimension(800, 150));
            itemPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Title with custom styling
            JLabel titleLabel = new JLabel(newsItem[0]);
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setFont(new Font("Sans Serif", Font.BOLD, 14));
            titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            // Description with custom styling
            JTextArea descArea = new JTextArea(newsItem[1]);
            descArea.setWrapStyleWord(true);
            descArea.setLineWrap(true);
            descArea.setEditable(false);
            descArea.setForeground(new Color(200, 200, 200));
            descArea.setBackground(new Color(20, 30, 70));
            descArea.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
            descArea.setFont(new Font("Sans Serif", Font.PLAIN, 12));
            descArea.setAlignmentX(Component.LEFT_ALIGNMENT);

            itemPanel.add(titleLabel);
            itemPanel.add(Box.createVerticalStrut(8));
            itemPanel.add(descArea);

            // Add padding between news items
            newsPanel.add(Box.createVerticalStrut(15));
            newsPanel.add(itemPanel);
        }

        // Add final padding at bottom
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
