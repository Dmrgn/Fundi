package view;

import javax.swing.*;
import java.awt.*;
import interface_adapter.news.NewsViewModel;
import interface_adapter.news.NewsController;
import interface_adapter.news.NewsState;

public class NewsView extends JPanel {
    private final String viewName = "news";
    private final NewsViewModel newsViewModel;
    private final JPanel newsPanel;
    private NewsController newsController;

    public NewsView(NewsViewModel newsViewModel) {
        this.newsViewModel = newsViewModel;
        setPreferredSize(new Dimension(900, 600));
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Stock News");
        titleLabel.setFont(new Font("Sans Serif", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // News content panel
        newsPanel = new JPanel();
        newsPanel.setLayout(new BoxLayout(newsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(newsPanel);

        // Layout
        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Listen for news updates
        newsViewModel.addPropertyChangeListener(evt -> {
            NewsState state = newsViewModel.getState();
            updateNewsPanel(state);
        });
    }

    private void updateNewsPanel(NewsState state) {
        newsPanel.removeAll();
        for (String[] newsItem : state.getNewsItems()) {
            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
            itemPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            itemPanel.setMaximumSize(new Dimension(850, 150));

            JLabel titleLabel = new JLabel(newsItem[0]);
            titleLabel.setFont(new Font("Sans Serif", Font.BOLD, 14));
            JTextArea descArea = new JTextArea(newsItem[1]);
            descArea.setWrapStyleWord(true);
            descArea.setLineWrap(true);
            descArea.setEditable(false);
            descArea.setBackground(null);

            itemPanel.add(titleLabel);
            itemPanel.add(Box.createVerticalStrut(5));
            itemPanel.add(descArea);
            
            newsPanel.add(Box.createVerticalStrut(10));
            newsPanel.add(itemPanel);
        }
        newsPanel.revalidate();
        newsPanel.repaint();
    }

    public String getViewName() {
        return viewName;
    }

    public void setNewsController(NewsController newsController) {
        this.newsController = newsController;
    }
}
