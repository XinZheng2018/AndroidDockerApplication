/**
 * @Author: Xin Zheng (Modified from Professor Mertz's InteretingPicture Lab
 * @AndrewID: xinzhen2
 */


package edu.cmu.WebApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.json.JSONObject;
import org.json.JSONArray;

/***
 *  This is the Model class used to fetch the brewery information from openbrewerydb.org.
 *  Code below are modified from Professor Mertz's InterestingPicture lab.
 */
public class FetchBreweryModel {

    /***
     * This method gets the first brewery information from the list of breweries
     * getting from openbrewerydb.org with the brewery name indicated by user.
     *
     * @param searchTag user input of brewery name
     * @return a json string of the brewery's information
     * @throws UnsupportedEncodingException
     */
    public String doBreweryNameSearch(String searchTag) throws UnsupportedEncodingException{
        /*
         * URL encode the searchTag, e.g. to encode spaces as %20
         */
        searchTag = URLEncoder.encode(searchTag, "UTF-8");
        String response = "";

        // Create a URL for the api response
        String breweryApiURL = "https://api.openbrewerydb.org/breweries?by_name="+searchTag;

        // Fetch the page
        response = fetch(breweryApiURL);
        if (response.equals("[]")) {
            return "";
        }
        else if (response.contains("Sorry")) {
            return "API Connection Error";
        }
        // Create a JSONArray of the response getting from openbrewerydb.org
        JSONArray jsonResponse = new JSONArray(response);
        // Create a JSONObject of the first brewery in the list
        JSONObject firstBrewery = jsonResponse.getJSONObject(0);
        // Store the JSON object into a string
        String breweryInfo = firstBrewery.toString();

        // return the json string of the first brewery's information
        return breweryInfo;
    }




    // Code below is from Professor Mertz's InterestingPicture Lab Code

    /*
     * Make an HTTP request to a given URL
     *
     * @param urlString The URL of the request
     * @return A string of the response from the HTTP GET.
     */
    private String fetch(String urlString) {
        String response = "";
        try {
            URL url = new URL(urlString);

            //Create an HttpURLConnection.
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Read all the text returned by the server
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String str;
            // Read each line of "in" until done, adding each to "response"
            while ((str = in.readLine()) != null) {
                // str is one line of text readLine() strips newline characters
                response += str;
            }
            in.close();
        } catch (IOException e) {
            response = "Sorry, there is an error connecting to the API. ";
            System.out.println("Eeek, an exception");
        }
        return response;
    }
}
