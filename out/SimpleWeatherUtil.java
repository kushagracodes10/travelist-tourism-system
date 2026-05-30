package frontend;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

public class SimpleWeatherUtil {
    private static final String API_KEY = "e29156dd0a37f66e9caba31f3fcfad3a";

    /**
     * Creates an enhanced weather card panel for the specified city
     * @param city Name of the city (jaipur, goa, varanasi, darjeeling)
     * @param cardColor Background color for the card in hex format (e.g. "#f0ab51")
     * @return JPanel containing the weather information with improved visuals
     */
    public static JPanel createWeatherCard(String city, String cardColor) {
        JPanel card = new JPanel();
        card.setBackground(Color.decode(cardColor));
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Create weather content panel with rounded corners and white background
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

        // Add title with emoji
        JLabel titleLabel = new JLabel("Weather Forecast");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Add loading indicator
        JLabel loadingLabel = new JLabel("Loading weather data...");
        loadingLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
        loadingLabel.setForeground(Color.GRAY);
        loadingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(loadingLabel);

        card.add(contentPanel, BorderLayout.CENTER);

        // Fetch weather data in background thread
        new Thread(() -> {
            try {
                // Get city coordinates
                String lat = "0", lon = "0";
                switch(city.toLowerCase()) {
                    case "jaipur":
                        lat = "26.9124";
                        lon = "75.7873";
                        break;
                    case "goa":
                        lat = "15.2993";
                        lon = "74.1240";
                        break;
                    case "varanasi":
                        lat = "25.3176";
                        lon = "83.0062";
                        break;
                    case "darjeeling":
                        lat = "27.0410";
                        lon = "88.2663";
                        break;
                }

                // Build API request URL
                String apiUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat +
                        "&lon=" + lon + "&units=metric&appid=" + API_KEY;
                URL url = new URL(apiUrl);

                // Make API call
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // Read response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Parse JSON response - converts text to java object (humidity,etc)
                JSONParser parser = new JSONParser();
                JSONObject jsonResponse = (JSONObject) parser.parse(response.toString());

                // Extract weather info
                JSONObject main = (JSONObject) jsonResponse.get("main");
                double temperature = Double.parseDouble(main.get("temp").toString());
                double humidity = Double.parseDouble(main.get("humidity").toString());

                JSONArray weatherArray = (JSONArray) jsonResponse.get("weather");
                JSONObject weather = (JSONObject) weatherArray.get(0);
                String description = weather.get("description").toString();
                String iconCode = weather.get("icon").toString();

                // Get wind speed
                JSONObject wind = (JSONObject) jsonResponse.get("wind");
                double windSpeed = Double.parseDouble(wind.get("speed").toString());

                // Get weather condition for emoji selection
                String condition = weather.get("main").toString().toLowerCase();
                String weatherEmoji = getWeatherEmoji(condition);

                // Update UI on EDT
                SwingUtilities.invokeLater(() -> {
                    // Clear the panel
                    contentPanel.removeAll();

                    // Add title
                    contentPanel.add(titleLabel);
                    contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

                    // Current weather section
                    JPanel currentWeatherPanel = new JPanel(new BorderLayout());
                    currentWeatherPanel.setOpaque(false);

                    // Left side - condition and icon
                    JPanel leftPanel = new JPanel();
                    leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
                    leftPanel.setOpaque(false);

                    JLabel currentLabel = new JLabel("Current");
                    currentLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
                    currentLabel.setForeground(Color.GRAY);

                    JLabel conditionLabel = new JLabel(capitalizeWords(description));
                    conditionLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
                    conditionLabel.setForeground(Color.BLACK);

                    leftPanel.add(currentLabel);
                    leftPanel.add(conditionLabel);

                    // Create icon panel
                    JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    iconPanel.setOpaque(false);

                    // Try to load weather icon or use emoji
                    JLabel iconLabel = new JLabel(weatherEmoji);
                    iconLabel.setFont(new Font("SansSerif", Font.PLAIN, 40));
                    iconPanel.add(iconLabel);

                    // Right side - temperature and details
                    JPanel rightPanel = new JPanel();
                    rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
                    rightPanel.setOpaque(false);
                    rightPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);

                    JLabel tempLabel = new JLabel(String.format("%.0f°C", temperature));
                    tempLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
                    tempLabel.setForeground(Color.BLACK);
                    tempLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

                    JLabel detailsLabel = new JLabel(String.format("Humidity: %.0f%% | Wind: %.0f km/h", humidity, windSpeed * 3.6));
                    detailsLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
                    detailsLabel.setForeground(Color.GRAY);
                    detailsLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

                    rightPanel.add(tempLabel);
                    rightPanel.add(detailsLabel);

                    // Add components to current weather panel
                    currentWeatherPanel.add(iconPanel, BorderLayout.WEST);
                    currentWeatherPanel.add(leftPanel, BorderLayout.CENTER);
                    currentWeatherPanel.add(rightPanel, BorderLayout.EAST);

                    contentPanel.add(currentWeatherPanel);
                    contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));

                    // Add separator
                    JSeparator separator = new JSeparator();
                    separator.setMaximumSize(new Dimension(Short.MAX_VALUE, 1));
                    contentPanel.add(separator);
                    contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));

                    // Forecast section
                    JPanel forecastPanel = new JPanel(new GridLayout(1, 5, 10, 0));
                    forecastPanel.setOpaque(false);

                    // Sample 5-day forecast data (in a real app, you'd fetch this from API)
                    String[] days = {"Today", "Tomorrow", "Wednesday", "Thursday", "Friday"};
                    int[] highTemps = {30, 31, 29, 30, 31};
                    int[] lowTemps = {24, 25, 24, 25, 25};
                    String[] conditions = {"cloudy", "sunny", "rainy", "cloudy", "sunny"};

                    for (int i = 0; i < 5; i++) {
                        JPanel dayPanel = new JPanel();
                        dayPanel.setLayout(new BoxLayout(dayPanel, BoxLayout.Y_AXIS));
                        dayPanel.setOpaque(false);

                        JLabel dayLabel = new JLabel(days[i]);
                        dayLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
                        dayLabel.setForeground(Color.BLACK);
                        dayLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                        JLabel dayIconLabel = new JLabel(getWeatherEmoji(conditions[i]));
                        dayIconLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));
                        dayIconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                        JPanel tempRangePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
                        tempRangePanel.setOpaque(false);

                        JLabel highTempLabel = new JLabel(highTemps[i] + "°");
                        highTempLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
                        highTempLabel.setForeground(Color.BLACK);

                        JLabel lowTempLabel = new JLabel(lowTemps[i] + "°");
                        lowTempLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
                        lowTempLabel.setForeground(Color.GRAY);

                        tempRangePanel.add(highTempLabel);
                        tempRangePanel.add(lowTempLabel);

                        dayPanel.add(dayLabel);
                        dayPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                        dayPanel.add(dayIconLabel);
                        dayPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                        dayPanel.add(tempRangePanel);

                        forecastPanel.add(dayPanel);
                    }

                    contentPanel.add(forecastPanel);

                    // Refresh the card
                    card.revalidate();
                    card.repaint();
                });

            } catch (Exception e) {
                e.printStackTrace();
                SwingUtilities.invokeLater(() -> {
                    contentPanel.remove(loadingLabel);
                    JLabel errorLabel = new JLabel("❌ Weather data unavailable");
                    errorLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
                    errorLabel.setForeground(Color.RED);
                    errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    contentPanel.add(errorLabel);
                    card.revalidate();
                    card.repaint();
                });
            }
        }).start();

        return card;
    }

    /**
     * Returns appropriate weather emoji based on condition
     */
    private static String getWeatherEmoji(String condition) {
        switch (condition.toLowerCase()) {
            case "clear":
                return "☀️";
            case "clouds":
                return "⛅";
            case "rain":
                return "🌧️";
            case "drizzle":
                return "🌦️";
            case "thunderstorm":
                return "⛈️";
            case "snow":
                return "❄️";
            case "mist":
            case "fog":
            case "haze":
                return "🌫️";
            default:
                return "⛅";
        }
    }

    private static String capitalizeWords(String text) {
        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = true;

        for (char c : text.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                capitalizeNext = true;
            } else if (capitalizeNext) {
                c = Character.toUpperCase(c);
                capitalizeNext = false;
            }
            result.append(c);
        }

        return result.toString();
    }
}