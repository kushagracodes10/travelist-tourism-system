package frontend;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TransportationUtil {

    /**
     * Creates a transportation information card for the specified city
     * @param city Name of the city (jaipur, goa, varanasi, darjeeling)
     * @param cardColor Background color for the card in hex format (e.g. "#f0ab51")
     * @return JPanel containing the transportation information with visually appealing layout
     */
    public static JPanel createTransportationCard(String city, String cardColor) {
        JPanel card = new JPanel();
        card.setBackground(Color.decode(cardColor));
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Create content panel with rounded corners and white background
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

        // Add title with transportation emoji
        JLabel titleLabel = new JLabel("Transportation Guide 🚆 🚌 🚕");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22)); // Increased font size
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Get transportation data for the selected city
        TransportationData data = getTransportationData(city);

        // Main attraction transportation section
        JPanel mainAttractionPanel = new JPanel();
        mainAttractionPanel.setLayout(new BorderLayout(10, 0));
        mainAttractionPanel.setOpaque(false);
        mainAttractionPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 200));

        // Icon panel on the left
        JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        iconPanel.setOpaque(false);

        JLabel transportIcon = new JLabel(data.icon);
        transportIcon.setFont(new Font("SansSerif", Font.PLAIN, 42)); // Increased icon size
        iconPanel.add(transportIcon);

        // Information panel on the right
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        JLabel nameLabel = new JLabel(data.name);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 18)); // Increased font size
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel categoryLabel = new JLabel(data.category);
        categoryLabel.setFont(new Font("SansSerif", Font.ITALIC, 14)); // Increased font size
        categoryLabel.setForeground(Color.GRAY);
        categoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel descriptionLabel = new JLabel("<html>" + data.description + "</html>");
        descriptionLabel.setFont(new Font("SansSerif", Font.PLAIN, 14)); // Increased font size
        descriptionLabel.setForeground(Color.BLACK);
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoPanel.add(nameLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        infoPanel.add(categoryLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(descriptionLabel);

        // Add to main attraction panel
        mainAttractionPanel.add(iconPanel, BorderLayout.WEST);
        mainAttractionPanel.add(infoPanel, BorderLayout.CENTER);

        // Add to content panel
        contentPanel.add(mainAttractionPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Add price and duration info
        JPanel priceTimePanel = new JPanel(new GridLayout(1, 2, 10, 0));
        priceTimePanel.setOpaque(false);

        // Price panel
        JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pricePanel.setOpaque(false);

        JLabel priceIcon = new JLabel("💰");
        JLabel priceLabel = new JLabel(data.price);
        priceLabel.setFont(new Font("SansSerif", Font.BOLD, 16)); // Increased font size
        priceLabel.setForeground(new Color(76, 175, 80));

        pricePanel.add(priceIcon);
        pricePanel.add(priceLabel);

        // Duration panel
        JPanel durationPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        durationPanel.setOpaque(false);

        JLabel durationIcon = new JLabel("⏱");
        JLabel durationLabel = new JLabel(data.duration);
        durationLabel.setFont(new Font("SansSerif", Font.BOLD, 16)); // Increased font size
        durationLabel.setForeground(Color.ORANGE);

        durationPanel.add(durationIcon);
        durationPanel.add(durationLabel);

        priceTimePanel.add(pricePanel);
        priceTimePanel.add(durationPanel);

        contentPanel.add(priceTimePanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Add separator
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(Short.MAX_VALUE, 2)); // Thicker separator
        contentPanel.add(separator);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Additional information section (availability, frequency, routes)
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setOpaque(false);

        // Availability
        JPanel availabilityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        availabilityPanel.setOpaque(false);

        JLabel availabilityTitle = new JLabel("⏰ Availability:");
        availabilityTitle.setFont(new Font("SansSerif", Font.BOLD, 14)); // Increased font size
        availabilityTitle.setForeground(Color.BLACK);

        JLabel availabilityValue = new JLabel(data.availability);
        availabilityValue.setFont(new Font("SansSerif", Font.PLAIN, 14)); // Increased font size
        availabilityValue.setForeground(Color.BLACK);

        availabilityPanel.add(availabilityTitle);
        availabilityPanel.add(availabilityValue);

        // Frequency
        JPanel frequencyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        frequencyPanel.setOpaque(false);

        JLabel frequencyTitle = new JLabel("🔄 Frequency:");
        frequencyTitle.setFont(new Font("SansSerif", Font.BOLD, 14)); // Increased font size
        frequencyTitle.setForeground(Color.BLACK);

        JLabel frequencyValue = new JLabel(data.frequency);
        frequencyValue.setFont(new Font("SansSerif", Font.PLAIN, 14)); // Increased font size
        frequencyValue.setForeground(Color.BLACK);

        frequencyPanel.add(frequencyTitle);
        frequencyPanel.add(frequencyValue);

        // Routes
        JPanel routesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        routesPanel.setOpaque(false);

        JLabel routesTitle = new JLabel("🗺 Routes:");
        routesTitle.setFont(new Font("SansSerif", Font.BOLD, 14)); // Increased font size
        routesTitle.setForeground(Color.BLACK);

        JLabel routesValue = new JLabel(data.routes);
        routesValue.setFont(new Font("SansSerif", Font.PLAIN, 14)); // Increased font size
        routesValue.setForeground(Color.BLACK);

        routesPanel.add(routesTitle);
        routesPanel.add(routesValue);

        detailsPanel.add(availabilityPanel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        detailsPanel.add(frequencyPanel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        detailsPanel.add(routesPanel);

        contentPanel.add(detailsPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Travel tips section with improved alignment
        JPanel tipsHeaderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tipsHeaderPanel.setOpaque(false);
        tipsHeaderPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel tipsTitle = new JLabel("✨ Travel Tips:");
        tipsTitle.setFont(new Font("SansSerif", Font.BOLD, 16)); // Increased font size
        tipsTitle.setForeground(new Color(33, 150, 243)); // Blue color for emphasis

        tipsHeaderPanel.add(tipsTitle);
        contentPanel.add(tipsHeaderPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        // Add bullet points for tips with better alignment
        JPanel tipsContainer = new JPanel();
        tipsContainer.setLayout(new BoxLayout(tipsContainer, BoxLayout.Y_AXIS));
        tipsContainer.setOpaque(false);
        tipsContainer.setAlignmentX(Component.LEFT_ALIGNMENT);

        for (String tip : data.tips) {
            JPanel tipRow = new JPanel();
            tipRow.setLayout(new BoxLayout(tipRow, BoxLayout.X_AXIS));
            tipRow.setOpaque(false);
            tipRow.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel bulletPoint = new JLabel(" • ");
            bulletPoint.setFont(new Font("SansSerif", Font.BOLD, 16));
            bulletPoint.setForeground(new Color(33, 150, 243)); // Blue bullet points

            JLabel tipText = new JLabel("<html>" + tip + "</html>");
            tipText.setFont(new Font("SansSerif", Font.PLAIN, 14)); // Increased font size
            tipText.setForeground(Color.BLACK);

            tipRow.add(bulletPoint);
            tipRow.add(tipText);

            tipsContainer.add(tipRow);
            tipsContainer.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        contentPanel.add(tipsContainer);

        // Add content panel to card
        card.add(contentPanel, BorderLayout.CENTER);

        return card;
    }

    /**
     * Helper class to store transportation data
     */
    private static class TransportationData {
        String icon;
        String name;
        String category;
        String description;
        String price;
        String duration;
        String availability;
        String frequency;
        String routes;
        String[] tips;

        public TransportationData(String icon, String name, String category, String description,
                                  String price, String duration, String availability,
                                  String frequency, String routes, String[] tips) {
            this.icon = icon;
            this.name = name;
            this.category = category;
            this.description = description;
            this.price = price;
            this.duration = duration;
            this.availability = availability;
            this.frequency = frequency;
            this.routes = routes;
            this.tips = tips;
        }
    }

    /**
     * Returns transportation data for the specified city
     */
    private static TransportationData getTransportationData(String city) {
        switch (city.toLowerCase()) {
            case "darjeeling":
                return new TransportationData(
                        "🚂",
                        "Darjeeling Himalayan Railway (Toy Train)",
                        "Heritage Transport 🏆",
                        "UNESCO World Heritage narrow-gauge railway offering scenic mountain views.",
                        "₹1300.00",
                        "2 hours (joy ride)",
                        "8:00 AM - 4:00 PM",
                        "Multiple departures daily",
                        "Darjeeling to Ghum, Darjeeling to NJP",
                        new String[] {
                                "Book tickets in advance during peak season",
                                "Sit on the right side for best views",
                                "Joy rides are shorter but offer similar experience"
                        }
                );
            case "jaipur":
                return new TransportationData(
                        "🚕",
                        "Auto Rickshaw & Taxi Services",
                        "Local Transport 🏙",
                        "Convenient and affordable way to explore the Pink City and nearby attractions.",
                        "₹200-600",
                        "Varies by destination",
                        "24/7",
                        "Available throughout the city",
                        "All major attractions and local areas",
                        new String[] {
                                "Always negotiate fares before starting your journey",
                                "Use ride-sharing apps like Ola or Uber for better rates",
                                "Consider hiring for full day (₹1000-1500) to visit multiple attractions"
                        }
                );
            case "goa":
                return new TransportationData(
                        "🛵",
                        "Scooter/Bike Rental",
                        "Self-drive 🏖",
                        "Most popular and flexible way to explore beaches and hidden spots in Goa.",
                        "₹300-600 per day",
                        "Rentals available",
                        "24/7",
                        "Multiple rental shops across tourist areas",
                        "All beaches and local attractions",
                        new String[] {
                                "International driving license recommended",
                                "Always wear a helmet to avoid fines (₹500)",
                                "Check the vehicle condition before renting"
                        }
                );
            case "varanasi":
                return new TransportationData(
                        "🚣",
                        "Ganges River Boat Ride",
                        "Water Transport 🌊",
                        "Traditional wooden boats offering stunning views of the ghats and ceremonies.",
                        "₹500-1500",
                        "1-3 hours",
                        "Dawn to dusk (sunrise rides most popular)",
                        "Available throughout the day",
                        "Assi Ghat to Manikarnika Ghat and back",
                        new String[] {
                                "Sunrise (5:30 AM) and sunset (6:00 PM) rides offer the best experience",
                                "Negotiate the price before boarding",
                                "Group boats are more economical than private ones"
                        }
                );
            default:
                // Default placeholder data
                return new TransportationData(
                        "🚌",
                        "Local Transport",
                        "Various 🗺",
                        "Multiple transportation options available for exploring the city.",
                        "Varies",
                        "Depends on distance",
                        "Generally available throughout the day",
                        "Regular service",
                        "Major tourist attractions",
                        new String[] {
                                "Research local transportation options before your trip",
                                "Consider guided tours for convenience",
                                "Public transport is often most economical"
                        }
                );
        }
    }
}