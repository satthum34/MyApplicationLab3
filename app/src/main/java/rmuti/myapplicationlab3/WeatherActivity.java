package rmuti.myapplicationlab3;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Scanner;


public class WeatherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final Button search = (Button) findViewById(R.id.search_button);

        search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView country = (TextView) findViewById(R.id.country_show);
                TextView city = (TextView) findViewById(R.id.city_show);
                TextView press = (TextView) findViewById(R.id.press_show);
                TextView humi = (TextView) findViewById(R.id.humi_show);
                TextView temp = (TextView) findViewById(R.id.temp_show);
                TextView wind = (TextView) findViewById(R.id.wind_show);

                EditText search_key = (EditText) findViewById(R.id.search_key);

                String search_key_String = search_key.getText().toString();
                try {

                    String api_call = new String("http://api.openweathermap.org/data/2.5/weather?q=" + search_key_String);
                    URL url = new URL(api_call);
                    Scanner sc = new Scanner(url.openStream());
                    StringBuffer buf = new StringBuffer();
                    while (sc.hasNext()) {
                        buf.append(sc.next());
                    }

                    JSONObject jsonOBJ = new JSONObject(buf.toString());

                    JSONObject sysObj = jsonOBJ.getJSONObject("sys");
                    JSONObject mainOBJ = jsonOBJ.getJSONObject("main");
                    JSONObject windOBJ = jsonOBJ.getJSONObject("wind");

                    String country_ = sysObj.getString("country");
                    String pressure_ = mainOBJ.getString("pressure");
                    String humidity_ = mainOBJ.getString("humidity");

                    Double temp_ = (mainOBJ.getDouble("temp")) - 273.15;

                    DecimalFormat df = new DecimalFormat("0.00");

                    String city_ = jsonOBJ.getString("name");
                    String wind_s = windOBJ.getString("speed");

                    country.setText("Country : (" + country_ + ")");
                    city.setText("Station name : " + city_);
                    press.setText("Pressure : " + pressure_);
                    humi.setText("Humidity : " + humidity_);
                    temp.setText("Temp : " + df.format(temp_) + " Celsius");
                    wind.setText("Wind speed : " + wind_s + " Km/h");


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}