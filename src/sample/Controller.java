package sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.json.JSONObject;


public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button checkTheWeather;

    @FXML
    private TextField enterTheCity;

    @FXML
    private Text feelsLike;

    @FXML
    private AnchorPane firstAnchor;

    @FXML
    private Text information;

    @FXML
    private Text mainText;

    @FXML
    private Text maximum;

    @FXML
    private Text minimum;

    @FXML
    private Text presure;

    @FXML
    private Text wind;

    @FXML
    private AnchorPane secondAnchor;

    @FXML
    private Text temperature;

    @FXML
    void initialize() {
        // get from button
        checkTheWeather.setOnAction(event -> {
            // get from text field
            String getUserCity = enterTheCity.getText().trim();
            if(!getUserCity.equals("")) { // If data not empty
                // get data from https://openweathermap.org/
                String output = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=" + getUserCity +
                        "&appid=5a04dec1e0a51a7aa5d8dcdbc449e051&units=metric");

                if (!output.isEmpty()) { // all right
                    JSONObject obj = new JSONObject(output);
                    // Rework JSON and install data in text fields
                    temperature.setText("Temperature: " + obj.getJSONObject("main").getDouble("temp"));
                    feelsLike.setText("Feels like: " + obj.getJSONObject("main").getDouble("feels_like"));
                    maximum.setText("Maximum: " + obj.getJSONObject("main").getDouble("temp_max"));
                    minimum.setText("Minimum: " + obj.getJSONObject("main").getDouble("temp_min"));
                    presure.setText("Pressure: " + obj.getJSONObject("main").getDouble("pressure"));
                    wind.setText("Wind speed: " + obj.getJSONObject("wind").getDouble("speed"));
                }
            }
        });
    }

    // Work with URL adress and getting dates from it
    private static String getUrlContent(String urlAdress) {
        StringBuffer content = new StringBuffer();

        try {
            URL url = new URL(urlAdress);
            URLConnection urlConn = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;

            while((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch(Exception e) {
            System.out.println("Such city was not found!");
        }
        return content.toString();
    }
}