/**
 * @Author: Xin Zheng (Modified from Professor Mertz's InteretingPicture Lab
 * @AndrewID: xinzhen2
 */


package edu.cmu.WebApplication;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONObject;

@WebServlet(name = "fetchServer", value = "/fetch-server")
public class FetchBreweryServerServlet extends HttpServlet {
    private FetchBreweryModel fbm;

    public void init() {
        fbm = new FetchBreweryModel();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String nameSearch = request.getParameter("inputText");
        String result;
        result = fbm.doBreweryNameSearch(nameSearch);
        PrintWriter out = response.getWriter();
        if (result.equals("")) {
            response.setIntHeader("OK", 202);
            out.println("There is no brewery based on the information you entered. ");
        }
        else if(result.contains("Connection")) {
            response.setIntHeader("Error", 404);
            out.println("There is a connection error to the API. ");
        }
        else {
            response.setIntHeader("OK", 202);
            JSONObject breweryJson = new JSONObject(result);
            JSONObject neededInfoJSON = new JSONObject();
            neededInfoJSON.put("country",breweryJson.get("country"));
            neededInfoJSON.put("brewery_type",breweryJson.get("brewery_type"));
            neededInfoJSON.put("city",breweryJson.get("city"));
            neededInfoJSON.put("name",breweryJson.get("name"));
            neededInfoJSON.put("state",breweryJson.get("state"));
            neededInfoJSON.put("website_url",breweryJson.get("website_url"));
            out.println(neededInfoJSON.toString());
        }

    }

    public void destroy() {
    }
}