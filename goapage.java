package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.border.*;

public class goapage {
    private JPanel panel;
    private static final Color ACCENT_COLOR = new Color(0, 164, 215);  // Ocean blue for Goa theme

    public goapage() {
        // Professional background panel with enhanced parallax
        panel = new JPanel() {
            Image backgroundImage = new ImageIcon("C:\\Users\\Kush\\Desktop\\NewPJ\\g2.jpg").getImage();
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
                        0, 0, new Color(5, 25, 45, 185),
                        0, getHeight(), new Color(0, 10, 25, 195)
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

        // Elegant title section with drop shadow effect
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);

        JLabel titleLabel = new JLabel("GOA");
        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 72));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Elegant subtitle with modern font
        JLabel subtitleLabel = new JLabel("Pristine Beaches & Coastal Paradise");
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

        // MODIFIED: Reduced vertical spacing
        titlePanel.add(Box.createVerticalStrut(20));  // Reduced from 40
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(15));
        titlePanel.add(accentLine);
        titlePanel.add(Box.createVerticalStrut(20));
        titlePanel.add(subtitleLabel);
        titlePanel.add(Box.createVerticalStrut(30));  // Reduced from 50

        // Main content with sleek cards
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.add(titlePanel, BorderLayout.NORTH);

        // Cards with refined spacing - MODIFIED: reduced vertical padding
        JPanel cardsPanel = new JPanel(new GridLayout(1, 3, 30, 10));
        cardsPanel.setOpaque(false);
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(0, 80, 30, 80));  // Reduced top padding

        cardsPanel.add(frontend.SimpleWeatherUtil.createWeatherCard("goa", "#00A4D7"));
        cardsPanel.add(frontend.TransportationUtil.createTransportationCard("goa", "#00A4D7"));
        cardsPanel.add(frontend.LocalAttractionsUtil.createAttractionsCard("goa", "#00A4D7"));

        contentPanel.add(cardsPanel, BorderLayout.CENTER);

        // Center content with refined spacing
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        centerWrapper.add(contentPanel);

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