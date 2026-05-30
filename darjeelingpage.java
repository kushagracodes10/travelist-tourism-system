package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.border.*;

public class darjeelingpage {
    private JPanel panel;
    private static final Color ACCENT_COLOR = new Color(120, 165, 90);  // Tea garden green for Darjeeling theme

    public darjeelingpage() {
        // Professional background panel with enhanced parallax
        panel = new JPanel() {
            Image backgroundImage = new ImageIcon("C:\\Users\\Kush\\Desktop\\NewPJ\\d2.jpg").getImage();
            int offsetY = 0;
            Timer parallaxTimer;

            {
                parallaxTimer = new Timer(40, e -> {
                    offsetY = (offsetY + 1) % 120;
                    repaint();
                });
                parallaxTimer.start();
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

                // Enhanced parallax effect
                g2d.drawImage(backgroundImage, 0, -offsetY/12, getWidth(), getHeight() + offsetY/6, this);

                // Professional gradient overlay with depth
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(25, 45, 15, 185),
                        0, getHeight(), new Color(10, 30, 10, 195)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Add subtle light rays at the top for dimension
                AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f);
                g2d.setComposite(alphaComposite);
                g2d.setPaint(new RadialGradientPaint(
                        new Point(getWidth()/2, 0),
                        getWidth()/2,
                        new float[]{0.0f, 1.0f},
                        new Color[]{new Color(255, 255, 255, 120), new Color(255, 255, 255, 0)}
                ));
                g2d.fillRect(0, 0, getWidth(), getHeight()/2);

                g2d.dispose();
            }
        };
        panel.setLayout(new BorderLayout());

        // Sleek back button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 20));
        topPanel.setOpaque(false);

        JButton backButton = new JButton("← Back");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(0, 0, 0, 120));
        backButton.setBorder(BorderFactory.createCompoundBorder(
                new RoundBorder(20, new Color(255, 255, 255, 60)),
                BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        backButton.setFocusPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setOpaque(true);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                backButton.setBackground(new Color(ACCENT_COLOR.getRed(), ACCENT_COLOR.getGreen(),
                        ACCENT_COLOR.getBlue(), 160));
                backButton.setForeground(Color.WHITE);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                backButton.setBackground(new Color(0, 0, 0, 120));
                backButton.setForeground(Color.WHITE);
            }
        });

        backButton.addActionListener(e -> {
            JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(panel);
            currentFrame.dispose();
            String[] args = {};
            frontpage.main(args);
        });

        topPanel.add(backButton);
        panel.add(topPanel, BorderLayout.NORTH);

        // Main content panel with responsive layout
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);

        // Elegant title section with drop shadow effect
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);

        JLabel titleLabel = new JLabel("DARJEELING");
        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 72));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Elegant subtitle with modern font
        JLabel subtitleLabel = new JLabel("Queen of Hills & Tea Paradise");
        subtitleLabel.setFont(new Font("Segoe UI", Font.ITALIC, 22));
        subtitleLabel.setForeground(new Color(230, 230, 230));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Animated accent line
        JPanel accentLine = new JPanel() {
            private final Timer animator = new Timer(18, e -> repaint());
            private int width = 0;
            private final int targetWidth = 180;

            {
                animator.start();
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (width < targetWidth) {
                    width += 6;
                }

                g2d.setColor(ACCENT_COLOR);
                g2d.fillRoundRect((getWidth() - width) / 2, 0, width, 4, 4, 4);
                g2d.dispose();
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(targetWidth, 4);
            }
        };
        accentLine.setOpaque(false);
        accentLine.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Assemble title panel
        titlePanel.add(Box.createVerticalStrut(20));
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(15));
        titlePanel.add(accentLine);
        titlePanel.add(Box.createVerticalStrut(20));
        titlePanel.add(subtitleLabel);
        titlePanel.add(Box.createVerticalStrut(30));

        contentPanel.add(titlePanel, BorderLayout.NORTH);

        // RESPONSIVE CARDS PANEL - MODIFIED FOR BETTER DISPLAY ACROSS DEVICES
        // Create a panel to hold all three cards with proper scrolling support
        JPanel cardsContainer = new JPanel();
        cardsContainer.setOpaque(false);
        cardsContainer.setLayout(new BoxLayout(cardsContainer, BoxLayout.Y_AXIS));

        // Create a panel with wrap layout that will better handle different screen sizes
        JPanel cardsPanel = new JPanel();
        cardsPanel.setOpaque(false);

        // Use a responsive layout manager - FlowLayout with proper configuration
        cardsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        // Create cards with fixed size to ensure consistency
        JPanel weatherCard = SimpleWeatherUtil.createWeatherCard("darjeeling", "#78A55A");
        JPanel transportCard = TransportationUtil.createTransportationCard("darjeeling", "#78A55A");
        JPanel attractionsCard = LocalAttractionsUtil.createAttractionsCard("darjeeling", "#78A55A");

        // Set preferred size for each card to maintain consistency
        Dimension cardSize = new Dimension(380, 450);
        weatherCard.setPreferredSize(cardSize);
        weatherCard.setMinimumSize(cardSize);
        transportCard.setPreferredSize(cardSize);
        transportCard.setMinimumSize(cardSize);
        attractionsCard.setPreferredSize(cardSize);
        attractionsCard.setMinimumSize(cardSize);

        // Add cards to the panel
        cardsPanel.add(weatherCard);
        cardsPanel.add(transportCard);
        cardsPanel.add(attractionsCard);

        // Add the cards panel to the container
        cardsContainer.add(cardsPanel);

        // Create a scroll pane to handle overflow on smaller screens
        JScrollPane scrollPane = new JScrollPane(cardsContainer);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Add the scrollable container to the main content
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Create a wrapper with GridBagLayout for proper centering
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        centerWrapper.add(contentPanel, gbc);

        panel.add(centerWrapper, BorderLayout.CENTER);
    }

    // Enhanced rounded border with subtle glow
    private static class RoundBorder extends AbstractBorder {
        private final int radius;
        private final Color color;

        public RoundBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Add subtle outer glow effect
            for (int i = 0; i < 2; i++) {
                g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 30 - i*10));
                g2.draw(new RoundRectangle2D.Double(x-i, y-i, width+i*2-1, height+i*2-1, radius, radius));
            }

            g2.setColor(color);
            g2.draw(new RoundRectangle2D.Double(x, y, width-1, height-1, radius, radius));
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius/2, this.radius/2, this.radius/2, this.radius/2);
        }
    }

    public JPanel getPanel() {
        return panel;
    }
}