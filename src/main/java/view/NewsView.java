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
import view.ui.UiConstants;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class NewsView extends BaseView {
    private static final String SEARCH_PLACEHOLDER = "Enter stock ticker (e.g., AAPL, GOOGL)";
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

        // Header
        header.add(createBackButtonPanel(evt -> this.navigationController.goBack()), BorderLayout.WEST);
        JPanel titleWrap = new JPanel();
        titleWrap.setOpaque(false);
        titleWrap.setLayout(new BoxLayout(titleWrap, BoxLayout.Y_AXIS));
        titleWrap.add(titleLabel);
        titleWrap.add(Box.createVerticalStrut(UiConstants.Spacing.SM));
        titleWrap.add(usernameLabel);
        header.add(titleWrap, BorderLayout.CENTER);

        // Create search field with placeholder (centralized)
        this.searchField = FieldFactory.createSearchField(SEARCH_PLACEHOLDER);
        this.searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals(SEARCH_PLACEHOLDER)) {
                    searchField.setText("");
                    searchField.setForeground(UiConstants.Colors.TEXT_PRIMARY);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setForeground(UiConstants.Colors.TEXT_MUTED);
                    searchField.setText(SEARCH_PLACEHOLDER);
                }
            }
        });
        this.searchButton = ButtonFactory.createPrimaryButton("Search");

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setOpaque(false);
        searchPanel.add(searchField);
        searchPanel.add(Box.createHorizontalStrut(UiConstants.Spacing.SM));
        searchPanel.add(searchButton);

        newsPanel = new JPanel();
        newsPanel.setLayout(new BoxLayout(newsPanel, BoxLayout.Y_AXIS));
        newsPanel.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(newsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBackground(UiConstants.Colors.CANVAS_BG);
        scrollPane.getViewport().setBackground(UiConstants.Colors.CANVAS_BG);

        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setOpaque(false);
        contentPanel.add(searchPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        content.add(contentPanel, BorderLayout.CENTER);

        setupSearchListeners();

        this.newsViewModel.addPropertyChangeListener(evt -> {
            NewsState state = this.newsViewModel.getState();
            updateNewsPanel(state);
        });
    }

    private void setupSearchListeners() {
        searchButton.addActionListener(e -> performSearch());
        searchField.addActionListener(e -> performSearch());
    }

    private void performSearch() {
        String query = searchField.getText().trim();
        if (!query.isEmpty() && !query.equals(SEARCH_PLACEHOLDER) && newsController != null) {
            newsController.executeSearch(query.toUpperCase());
            searchField.setText(""); // Clear field after search
            searchField.getTopLevelAncestor().requestFocus(); // Remove focus
        }
    }

    private void updateNewsPanel(NewsState state) {
        newsPanel.removeAll();

        for (String[] newsItem : state.getNewsItems()) {
            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
            itemPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(UiConstants.Colors.PRIMARY, 1),
                    BorderFactory.createEmptyBorder(15, 15, 15, 15)));
            itemPanel.setBackground(UiConstants.Colors.SURFACE_BG);
            itemPanel.setMaximumSize(new Dimension(800, 150));
            itemPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel titleLabel = new JLabel(newsItem[0]);
            titleLabel.setForeground(UiConstants.Colors.TEXT_PRIMARY);
            titleLabel.setFont(UiConstants.Fonts.HEADING);
            titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JTextArea descArea = new JTextArea(newsItem[1]);
            descArea.setWrapStyleWord(true);
            descArea.setLineWrap(true);
            descArea.setEditable(false);
            descArea.setForeground(UiConstants.Colors.TEXT_PRIMARY);
            descArea.setBackground(UiConstants.Colors.SURFACE_BG);
            descArea.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
            descArea.setFont(UiConstants.Fonts.BODY);
            descArea.setAlignmentX(Component.LEFT_ALIGNMENT);

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
