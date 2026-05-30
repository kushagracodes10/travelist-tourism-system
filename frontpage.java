package frontend;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.HashMap;
import java.util.Map;

public class frontpage {
    // Destinations with descriptions
    private static final String[] DESTINATIONS = {"Jaipur", "Goa", "Varanasi", "Darjeeling"};
    private static final Map<String, String> CITY_DESCRIPTIONS = new HashMap<String, String>() {{
        put("jaipur", "The Pink City - Royal palaces and vibrant culture");
        put("goa", "Pristine beaches and laid-back coastal vibes");
        put("varanasi", "The spiritual capital on the banks of the Ganges");
        put("darjeeling", "Misty mountains and world-famous tea gardens");
    }};

    // Color scheme
    private static final Color ACCENT_COLOR = new Color(255, 165, 0);
    private static final Color PRIMARY_COLOR = new Color(30, 41, 59);
    private static final Color TEXT_LIGHT = new Color(240, 240, 240);

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Travelist- Made by - Vaibhav Sharma | Reyan Ahmad| Vedant Singh");
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(createMainPanel(frame));
            frame.setVisible(true);
        });
    }

    private static JPanel createMainPanel(JFrame frame) {
        // Enhanced background panel with parallax effect
        JPanel backgroundPanel = new JPanel() {
            Image backgroundImage = new ImageIcon("C:\\Users\\Kush\\Desktop\\NewPJ\\bg1.jpg").getImage();
            int offsetY = 0;
            Timer parallaxTimer;

            {
                // Subtle parallax animation
                parallaxTimer = new Timer(50, e -> {
                    offsetY = (offsetY + 1) % 100;
                    repaint();
                });
                parallaxTimer.start();
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();

                // Enable antialiasing
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                // Draw background with subtle parallax effect
                g2d.drawImage(backgroundImage, 0, -offsetY/10, getWidth(), getHeight() + offsetY/5, this);

                // Premium gradient overlay
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(0, 0, 0, 160),
                        0, getHeight(), new Color(0, 0, 30, 200)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.dispose();
            }
        };
        backgroundPanel.setLayout(new BorderLayout());

        // Add header with modern navigation
        backgroundPanel.add(createHeaderPanel(), BorderLayout.NORTH);

        // Main content area
        JPanel centerContent = new JPanel(new GridBagLayout());
        centerContent.setOpaque(false);
        centerContent.add(createHeroPanel(frame));

        backgroundPanel.add(centerContent, BorderLayout.CENTER);
        backgroundPanel.add(createFooterPanel(), BorderLayout.SOUTH);

        return backgroundPanel;
    }

    private static JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0, 0, 0, 120));
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(255, 255, 255, 40)));
        headerPanel.setPreferredSize(new Dimension(0, 70));

        // Logo panel
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        logoPanel.setOpaque(false);

        JLabel logoLabel = new JLabel("TRAVELIST");
        logoLabel.setFont(new Font("Quicksand", Font.BOLD, 28));
        logoLabel.setForeground(TEXT_LIGHT);
        logoPanel.add(logoLabel);

        // Navigation items
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 25, 20));
        navPanel.setOpaque(false);

        String[] navItems = {"Home", "Destinations", "Gallery", "About Us", "Contact"};
        for (String item : navItems) {
            JLabel navItem = new JLabel(item);
            navItem.setFont(new Font("Open Sans", Font.PLAIN, 16));
            navItem.setForeground(item.equals("Home") ? ACCENT_COLOR : TEXT_LIGHT);
            navItem.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Hover effects
            navItem.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    navItem.setForeground(ACCENT_COLOR);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    navItem.setForeground(item.equals("Home") ? ACCENT_COLOR : TEXT_LIGHT);
                }
            });

            navPanel.add(navItem);
        }

        headerPanel.add(logoPanel, BorderLayout.WEST);
        headerPanel.add(navPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private static JPanel createHeroPanel(JFrame frame) {
        JPanel heroPanel = new JPanel();
        heroPanel.setLayout(new BoxLayout(heroPanel, BoxLayout.Y_AXIS));
        heroPanel.setOpaque(false);
        heroPanel.setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20));

        // Hero title with modern typography
        JLabel titleLabel = new JLabel("EXPLORE INCREDIBLE INDIA");
        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 62));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Decorative divider with animation
        JPanel divider = new JPanel() {
            private final Timer animator = new Timer(30, e -> repaint());
            private int width = 0;
            private final int targetWidth = 350;

            {
                animator.start();
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Animated divider
                if (width < targetWidth) {
                    width += 10;
                }

                g2d.setColor(ACCENT_COLOR);
                g2d.fillRoundRect((getWidth() - width) / 2, 0, width, 3, 3, 3);
                g2d.dispose();
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(targetWidth, 3);
            }
        };
        divider.setOpaque(false);
        divider.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Inspirational tagline
        JLabel taglineLabel = new JLabel("Tour the Best – Where Every City Tells a Story!");
        taglineLabel.setFont(new Font("Open Sans", Font.ITALIC , 22));
        taglineLabel.setForeground(new Color(220, 220, 220));
        taglineLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components with spacing
        heroPanel.add(Box.createVerticalGlue());
        heroPanel.add(titleLabel);
        heroPanel.add(Box.createVerticalStrut(20));
        heroPanel.add(divider);
        heroPanel.add(Box.createVerticalStrut(20));
        heroPanel.add(taglineLabel);
        heroPanel.add(Box.createVerticalStrut(60));

        // Modern search panel
        heroPanel.add(createSearchPanel(frame));
        heroPanel.add(Box.createVerticalGlue());

        // Inspirational quote
        JPanel quotePanel = new JPanel();
        quotePanel.setLayout(new BoxLayout(quotePanel, BoxLayout.Y_AXIS));
        quotePanel.setOpaque(false);
        quotePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        quotePanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

        JLabel quoteLabel = new JLabel("The world is a book and those who do not travel read only one page.");
        quoteLabel.setFont(new Font("Georgia", Font.ITALIC, 18));
        quoteLabel.setForeground(new Color(220, 220, 220));
        quoteLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel authorLabel = new JLabel("— Saint Augustine");
        authorLabel.setFont(new Font("Georgia", Font.PLAIN, 16));
        authorLabel.setForeground(new Color(180, 180, 180));
        authorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        quotePanel.add(quoteLabel);
        quotePanel.add(Box.createVerticalStrut(10));
        quotePanel.add(authorLabel);

        heroPanel.add(quotePanel);

        return heroPanel;
    }

    private static JPanel createSearchPanel(JFrame frame) {
        // Modern floating search panel
        JPanel searchWrapper = new JPanel();
        searchWrapper.setLayout(new BoxLayout(searchWrapper, BoxLayout.Y_AXIS));
        searchWrapper.setOpaque(true);
        searchWrapper.setBackground(new Color(15, 23, 42, 200));
        searchWrapper.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 30), 1),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        searchWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchWrapper.setMaximumSize(new Dimension(600, 300));

        // Modern search title
        JLabel searchTitle = new JLabel("Where would you like to explore?");
        searchTitle.setFont(new Font("Montserrat", Font.BOLD, 24));
        searchTitle.setForeground(Color.WHITE);
        searchTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Search options
        JPanel searchInputPanel = new JPanel();
        searchInputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
        searchInputPanel.setOpaque(false);
        searchInputPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Custom text field with rounded corners
        JTextField searchField = new JTextField(15);
        searchField.setFont(new Font("Open Sans", Font.PLAIN, 16));
        searchField.setPreferredSize(new Dimension(280, 45));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                new RoundBorder(8, new Color(255, 255, 255, 60)),
                BorderFactory.createEmptyBorder(0, 15, 0, 15)
        ));

        // Custom search button
        JButton searchButton = new JButton("SEARCH");
        searchButton.setFont(new Font("Montserrat", Font.BOLD, 16));
        searchButton.setForeground(Color.WHITE);
        searchButton.setBackground(ACCENT_COLOR);
        searchButton.setPreferredSize(new Dimension(140, 45));
        searchButton.setBorder(new RoundBorder(8, ACCENT_COLOR));
        searchButton.setFocusPainted(false);
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchButton.setContentAreaFilled(false);
        searchButton.setOpaque(true);

        // Button hover effects
        searchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                searchButton.setBackground(new Color(255, 140, 0));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                searchButton.setBackground(ACCENT_COLOR);
            }
        });

        searchInputPanel.add(searchField);
        searchInputPanel.add(searchButton);

        // Popular destinations
        JPanel destinationsPanel = new JPanel();
        destinationsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
        destinationsPanel.setOpaque(false);

        JLabel popularLabel = new JLabel("POPULAR DESTINATIONS:");
        popularLabel.setFont(new Font("Open Sans", Font.BOLD, 14));
        popularLabel.setForeground(new Color(180, 180, 180));
        destinationsPanel.add(popularLabel);

        // Destination pills
        for (String city : DESTINATIONS) {
            JLabel cityLabel = new JLabel(city);
            cityLabel.setFont(new Font("Open Sans", Font.PLAIN, 14));
            cityLabel.setForeground(TEXT_LIGHT);
            cityLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            cityLabel.setBorder(BorderFactory.createCompoundBorder(
                    new RoundBorder(12, new Color(255, 255, 255, 40)),
                    BorderFactory.createEmptyBorder(5, 15, 5, 15)
            ));

            // Hover and click effects
            cityLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    cityLabel.setForeground(ACCENT_COLOR);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    cityLabel.setForeground(TEXT_LIGHT);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    navigateToDestination(frame, city.toLowerCase());
                }
            });

            destinationsPanel.add(cityLabel);
        }

        // Action listeners
        searchButton.addActionListener(e -> {
            String destination = searchField.getText().trim().toLowerCase();
            if (!destination.isEmpty()) {
                navigateToDestination(frame, destination);
            }
        });

        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchButton.doClick();
                }
            }
        });

        // Add all components
        searchWrapper.add(searchTitle);
        searchWrapper.add(Box.createVerticalStrut(20));
        searchWrapper.add(searchInputPanel);
        searchWrapper.add(Box.createVerticalStrut(25));
        searchWrapper.add(destinationsPanel);

        return searchWrapper;
    }

    private static void navigateToDestination(JFrame frame, String destination) {
        JPanel nextPanel = null;

        switch (destination) {
            case "jaipur":
                nextPanel = new jaipurpage().getPanel();
                break;
            case "goa":
                nextPanel = new goapage().getPanel();
                break;
            case "varanasi":
                nextPanel = new varanasipage().getPanel();
                break;
            case "darjeeling":
                nextPanel = new darjeelingpage().getPanel();
                break;
            default:
                JOptionPane.showMessageDialog(frame,
                        "Sorry, we don't have information about \"" + destination + "\" yet.",
                        "Destination Not Available",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
        }

        if (nextPanel != null) {
            frame.setContentPane(nextPanel);
            frame.revalidate();
            frame.repaint();
        }
    }

    private static JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(15, 23, 42, 200));
        footerPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(70, 70, 70)));
        footerPanel.setPreferredSize(new Dimension(0, 60));

        JPanel leftFooter = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        leftFooter.setOpaque(false);
        JLabel copyrightLabel = new JLabel("© 2025 Travelist | All Rights Reserved");
        copyrightLabel.setFont(new Font("Open Sans", Font.PLAIN, 14));
        copyrightLabel.setForeground(new Color(180, 180, 180));
        leftFooter.add(copyrightLabel);

        JPanel centerFooter = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        centerFooter.setOpaque(false);
        String[] socialIcons = {"Facebook", "Instagram", "Twitter", "Pinterest"};
        for (String icon : socialIcons) {
            JLabel socialLabel = new JLabel(icon);
            socialLabel.setFont(new Font("Open Sans", Font.PLAIN, 14));
            socialLabel.setForeground(TEXT_LIGHT);
            socialLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            centerFooter.add(socialLabel);
        }

        footerPanel.add(leftFooter, BorderLayout.WEST);
        footerPanel.add(centerFooter, BorderLayout.CENTER);

        return footerPanel;
    }

    // Custom rounded border for modern UI components
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
            g2.setColor(color);
            g2.draw(new RoundRectangle2D.Double(x, y, width-1, height-1, radius, radius));
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius/2, this.radius/2, this.radius/2, this.radius/2);
        }
    }
}