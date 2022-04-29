/**
 * @Author: Xin Zheng (Modified from Professor Mertz's InteretingPicture Lab
 * @AndrewID: xinzhen2
 */


package edu.cmu.androidApplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class BreweryInfo extends AppCompatActivity {

    BreweryInfo me = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
         * The click listener will need a reference to this object, so that upon successfully finding a picture from Flickr, it
         * can callback to this object with the resulting picture Bitmap.  The "this" of the OnClick will be the OnClickListener, not
         * this InterestingPicture.
         */
        final BreweryInfo ma = this;

        /*
         * Find the "submit" button, and add a listener to it
         */
        Button submitButton = (Button)findViewById(R.id.submit);


        // Add a listener to the send button
        submitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View viewParam) {
                String searchTerm = ((EditText)findViewById(R.id.searchTerm)).getText().toString();
                edu.cmu.project4task1.GetBreweryInfo gbi = new edu.cmu.project4task1.GetBreweryInfo();
                gbi.search(searchTerm, me, ma); // Done asynchronously in another thread.  It calls ip.pictureReady() in this thread when complete.
            }
        });
    }

    /*
     * This is called by the GetPicture object when the picture is ready.  This allows for passing back the Bitmap picture for updating the ImageView
     */
    public void infoReady(String breweryInfo) throws JSONException {
        String searchTerm = ((EditText)findViewById(R.id.searchTerm)).getText().toString();
        String found = "Here is the information of the brewery containing the text '" + searchTerm + "'. ";
        String notFOUND = "Sorry, I could not find the information of the brewery containing the text '" + searchTerm + "'. ";
        String connectionError = "Sorry, there is a connection error to the open brewery db API. ";
        TextView searchView = (EditText)findViewById(R.id.searchTerm);
        TextView responseView = (TextView) findViewById(R.id.responseText);
        TextView nameTextView = (TextView) findViewById(R.id.nameText);
        TextView cityTextView = (TextView) findViewById(R.id.cityText);
        TextView stateTextView = (TextView) findViewById(R.id.stateText);
        TextView countryTextView = (TextView) findViewById(R.id.countryText);
        TextView typeTextView = (TextView) findViewById(R.id.breweryTypeText);
        TextView webTextView = (TextView) findViewById(R.id.websiteText);
        if (searchTerm.equals("")) {
            responseView.setText("Please enter something. ");
            nameTextView.setText("");
            cityTextView.setText("");
            stateTextView.setText("");
            countryTextView.setText("");
            typeTextView.setText("");
            webTextView.setText("");
        }
        else if (breweryInfo == null || breweryInfo.contains("connection")) {
            responseView.setText(connectionError);
            nameTextView.setText("");
            cityTextView.setText("");
            stateTextView.setText("");
            countryTextView.setText("");
            typeTextView.setText("");
            webTextView.setText("");
        }
        else if (breweryInfo.contains("no")) {
            responseView.setText(notFOUND);
            nameTextView.setText("");
            cityTextView.setText("");
            stateTextView.setText("");
            countryTextView.setText("");
            typeTextView.setText("");
            webTextView.setText("");
        }
        else {
            responseView.setText(found);
            JSONObject breweryJSON = new JSONObject(breweryInfo);
            nameTextView.setText("Brewery Name: " + breweryJSON.get("name").toString());
            cityTextView.setText("City: " + breweryJSON.get("city").toString());
            stateTextView.setText("State: " + breweryJSON.get("state").toString());
            countryTextView.setText("Country: "+breweryJSON.get("country").toString());
            typeTextView.setText("Brewery type: " + breweryJSON.get("brewery_type").toString());
            webTextView.setText("Website: " + breweryJSON.get("website_url").toString());
        }
        searchView.setText("");
    }
}
