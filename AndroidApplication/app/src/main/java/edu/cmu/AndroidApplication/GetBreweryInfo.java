/**
 * @Author: Xin Zheng (Modified from Professor Mertz's InteretingPicture Lab
 * @AndrewID: xinzhen2
 */


package edu.cmu.AndroidApplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/*
 * This class provides capabilities to search for breweries on Open Brewery DB given the input text from the user.
 * The app will show a brewery whose name contains the text entered by the user.
 * 
 * Method BackgroundTask.doInBackground( ) does the background work
 * Method BackgroundTask.onPostExecute( ) is called when the background work is
 *    done; it calls *back* to ip to report the results
 *
 */
public class GetBreweryInfo {
    BreweryInfo bi = null;   // for callback
    String searchTerm = null;       // search Flickr for this word
    String breweryInfo = null;

    // search( )
    // Parameters:
    // String searchTerm: the thing to search for on open brewery db
    // Activity activity: the UI thread activity
    // BreweryInfo bi: the callback method's class; here, it will be bi.infoReady( )
    public void search(String searchTerm, Activity activity, BreweryInfo bi) {
        this.bi = bi;
        this.searchTerm = searchTerm;
        new BackgroundTask(activity).execute();
    }

    // class BackgroundTask
    // Implements a background thread for a long running task that should not be
    //    performed on the UI thread. It creates a new Thread object, then calls doInBackground() to
    //    actually do the work. When done, it calls onPostExecute(), which runs
    //    on the UI thread to update some UI widget (***never*** update a UI
    //    widget from some other thread!)
    //
    // Adapted from one of the answers in
    // https://stackoverflow.com/questions/58767733/the-asynctask-api-is-deprecated-in-android-11-what-are-the-alternatives
    // Modified by Barrett
    //
    // Ideally, this class would be abstract and parameterized.
    // The class would be something like:
    //      private abstract class BackgroundTask<InValue, OutValue>
    // with two generic placeholders for the actual input value and output value.
    // It would be instantiated for this program as
    //      private class MyBackgroundTask extends BackgroundTask<String, Bitmap>
    // where the parameters are the String url and the Bitmap image.
    //    (Some other changes would be needed, so I kept it simple.)
    //    The first parameter is what the BackgroundTask looks up on Flickr and the latter
    //    is the image returned to the UI thread.
    // In addition, the methods doInBackground() and onPostExecute( ) could be
    //    absttract methods; would need to finesse the input and ouptut values.
    // The call to activity.runOnUiThread( ) is an Android Activity method that
    //    somehow "knows" to use the UI thread, even if it appears to create a
    //    new Runnable.

    private class BackgroundTask {

        private Activity activity; // The UI thread

        public BackgroundTask(Activity activity) {
            this.activity = activity;
        }

        private void startBackground() {
            new Thread(new Runnable() {
                public void run() {

                    try {
                        doInBackground();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // This is magic: activity should be set to MainActivity.this
                    //    then this method uses the UI thread
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                onPostExecute();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }).start();
        }

        private void execute(){
            // There could be more setup here, which is why
            //    startBackground is not called directly
            startBackground();
        }

        // doInBackground( ) implements whatever you need to do on
        //    the background thread.
        // Implement this method to suit your needs
        private void doInBackground() throws IOException {
            breweryInfo = search(searchTerm);
        }

        // onPostExecute( ) will run on the UI thread after the background
        //    thread completes.
        // Implement this method to suit your needs
        public void onPostExecute() throws JSONException {
            bi.infoReady(breweryInfo);
        }

        /*
         * create a connection to web server and return a string containing the information about the brewery
         */
        private String search(String searchTerm) throws IOException {

            // Code below is modified from https://medium.com/swlh/getting-json-data-from-a-restful-api-using-java-b327aafb3751

//          // Create a URL linked to the heroku application
            URL url = new URL("https://mighty-shelf-70931.herokuapp.com/fetch-server?inputText=" + searchTerm.toLowerCase());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            int responsecode = urlConnection.getResponseCode();
            String info = "";
            try {
                urlConnection.connect();
                if (responsecode != 200) {
                    info = "connection error";
                } else {
                    Scanner scanner = new Scanner(url.openStream());

                    //Write all the data into a string using a scanner
                    while (scanner.hasNext()) {
                        info += scanner.nextLine();
                    }

                    //Close the scanner
                    scanner.close();

                }
            } finally {
                urlConnection.disconnect(); // disconnect the url
            }
            return info;

        }
    }
}

