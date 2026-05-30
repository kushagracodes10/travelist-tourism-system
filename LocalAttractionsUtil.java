package frontend;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocalAttractionsUtil {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/travelist";
    private static final String USER = "root";
    private static final String PASS = "Kushagr29569014@";

    public static class Attraction {
        String name, description, openTimings, location;

        public Attraction(String name, String description, String openTimings, String location) {
            this.name = name;
            this.description = description;
            this.openTimings = openTimings;
            this.location = location;
        }
    }

    private static List<Attraction> getAttractionsForCity(String city) {
        List<Attraction> attractions = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT name, description, open_timings, location FROM local_attractions WHERE city = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, city.toLowerCase());

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                attractions.add(new Attraction(
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("open_timings"),
                        rs.getString("location")
                ));
            }
        } catch (SQLException e) {
            attractions.add(new Attraction("Database Error", "Could not connect to database.", "N/A", "N/A"));
        }
        return attractions;
    }

    public static JPanel createAttractionsCard(String city, String cardColor) {
        JPanel card = new JPanel();
        card.setBackground(Color.decode(cardColor)); // Outer card color
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Inner content panel (rounded white box)
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2d.dispose();
            }
        };
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Title
        JLabel titleLabel = new JLabel("Local Attractions");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(10));

        // Data
        List<Attraction> attractions = getAttractionsForCity(city);
        if (attractions.isEmpty()) {
            JLabel noData = new JLabel("No attractions found for this city.");
            noData.setForeground(Color.GRAY);
            noData.setFont(new Font("SansSerif", Font.ITALIC, 14));
            contentPanel.add(noData);
        } else {
            for (Attraction a : attractions) {
                JPanel aPanel = new JPanel();
                aPanel.setLayout(new BoxLayout(aPanel, BoxLayout.Y_AXIS));
                aPanel.setOpaque(false);
                aPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));

                JLabel nameLabel = new JLabel("• " + a.name);
                nameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
                nameLabel.setForeground(Color.BLACK);

                JLabel descLabel = new JLabel("<html>" + a.description + "</html>");
                descLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
                descLabel.setForeground(Color.DARK_GRAY);

                JLabel timeLabel = new JLabel("Hours: " + a.openTimings);
                timeLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
                timeLabel.setForeground(Color.GRAY);

                JLabel locLabel = new JLabel("Location: " + a.location);
                locLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
                locLabel.setForeground(Color.GRAY);

                aPanel.add(nameLabel);
                aPanel.add(descLabel);
                aPanel.add(timeLabel);
                aPanel.add(locLabel);

                contentPanel.add(aPanel);

                if (attractions.indexOf(a) < attractions.size() - 1) {
                    JSeparator sep = new JSeparator();
                    sep.setForeground(new Color(230, 230, 230));
                    contentPanel.add(sep);
                }
            }
        }

        card.add(contentPanel, BorderLayout.CENTER);
        return card;
    }
}
