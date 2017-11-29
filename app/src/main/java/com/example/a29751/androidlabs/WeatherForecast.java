package com.example.a29751.androidlabs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.Thread.sleep;

public class WeatherForecast extends AppCompatActivity {
    public ProgressBar pb;
    public TextView tvCurrent;
    public TextView tvMin;
    public TextView tvMax;
    public ImageView weatherImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        pb = (ProgressBar)findViewById(R.id.pB_weather);
        pb.setVisibility(View.VISIBLE);

        tvCurrent = (TextView)findViewById(R.id.tv_currentT) ;
        tvMin = (TextView)findViewById(R.id.tv_minT) ;
        tvMax = (TextView)findViewById(R.id.tv_maxT) ;
        weatherImage = (ImageView)findViewById(R.id.weather_iv);
        ForecastQuery fq = new ForecastQuery();
       // fq.execute(new String[]{"http://www.google.ca",",","b","a"});
        fq.execute();

    }

    private class ForecastQuery extends AsyncTask<String,Integer, String>{
        public String minT;
        public String maxT;
        public String currentT;
        public Bitmap btWeather;

        @Override
        protected String doInBackground(String... strings) {//...means an array
            HttpURLConnection conn = null;
            URL url = null;
            InputStream input = null;
            try {
                url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");

                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                input = conn.getInputStream();

                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(input, null);
                parser.nextTag();
                parser.require(XmlPullParser.START_TAG,null,"current");
                while (parser.next() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                    String name = parser.getName();

                    if (name.equals("temperature")) {

                        currentT = getResources().getString(R.string.current_t)+": "+ parser.getAttributeValue(null, "value")+" °C";
                        publishProgress(25);
                        SystemClock.sleep(300);
                        minT = getResources().getString(R.string.min_t)+": "+parser.getAttributeValue(null, "min")+" °C";
                        publishProgress(50);
                        SystemClock.sleep(300);
                        maxT = getResources().getString(R.string.max_t)+": "+parser.getAttributeValue(null, "max")+" °C";
                        publishProgress(75);
                        SystemClock.sleep(300);


                    }else if(name.equals("weather")) {
                        String iconName = parser.getAttributeValue(null, "icon");

                        Log.i("filename: " ,iconName+".png");
                        if(fileExistance(iconName+".png")) {
                            FileInputStream fis = null;
                            try {
                                fis = openFileInput(iconName+".png");
                            }
                            catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            btWeather = BitmapFactory.decodeStream(fis);
                            Log.i("Existed:","Find a local image");
                        }
                        else{
                            String urlString = "http://openweathermap.org/img/w/" + iconName + ".png";
                            btWeather = getImage(urlString);
                            SaveImage(btWeather, iconName);
                            Log.i("Non-Existed:","Download from website");
                        }
                        publishProgress(100);
                    }
                    else
                        skip(parser);

                }


            } catch (XmlPullParserException ex){
                    Log.i("XmlPullParserException:",ex.getMessage());
            } catch (MalformedURLException mu){
                Log.i("MalformedURLException: ",mu.getLocalizedMessage());
            } catch (IOException ex){
                Log.i("IOException: " ,ex.getMessage());
            } finally {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;

        }

        @Override
        protected void onPostExecute(String s) {

            tvCurrent.setText(currentT);
            tvCurrent.setTextSize(20);
            tvMin.setText(minT);
            tvMin.setTextSize(20);
            tvMax.setText(maxT);
            tvMax.setTextSize(20);
            weatherImage.setImageBitmap(btWeather);
            pb.setVisibility(View.INVISIBLE);

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            pb.setVisibility(View.VISIBLE);


            pb.setProgress(values[0]);
        }


    }



    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    public boolean fileExistance(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }




    private void SaveImage(Bitmap bmp, String iName){
        try {
            FileOutputStream outputStream = openFileOutput(iName + ".png", Context.MODE_PRIVATE);
            bmp.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
            outputStream.flush();
            outputStream.close();
        }catch (FileNotFoundException ex){
            Log.i("FileNotFoundException",ex.getMessage());
        }catch(IOException ex){
            Log.i("IOException",ex.getMessage());
        }
    }

    public static Bitmap getImage(URL url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                return BitmapFactory.decodeStream(connection.getInputStream());
            } else
                return null;
        } catch (Exception e) {
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    public static Bitmap getImage(String urlString) {
        try {
            URL url = new URL(urlString);
            return getImage(url);
        } catch (MalformedURLException e) {
            return null;
        }
    }
}

