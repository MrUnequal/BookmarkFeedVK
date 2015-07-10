package ua.southwall.vkbookmarksfeed.JsonModels;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mac on 3/11/15.
 */
public class Communities {

    private static String TAG = "Communities";
    private int id;//TODO review types to match data types from responses
    private String title;
    private String photo_100;
    private String photo_50;
    private String description;
    private String url;

    public int getId() {
        return this.id;
    }
    public String getTitle() {
        return this.title;
    }
    public String getPhoto_100() {
        return this.photo_100;
    }
    public String getPhoto_50() {
        return this.photo_50;
    }
    public String getDescription() {
        return this.description;
    }
    public String getUrl() {
        return this.url;
    }



    public static Communities fromJson(JSONObject jsonObject) {
        Communities b = new Communities();
        // Deserialize json into object fields
        try {
            b.id = jsonObject.getInt("id");
            b.title = jsonObject.getString("title");
            b.photo_100 = jsonObject.getString("photo_100");
            b.photo_50 = jsonObject.getString("photo_50");
            b.description = jsonObject.getString("description");
            b.url = jsonObject.getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Error processing JSONObject  " + jsonObject.toString());
            return null;
        }
        // Return new object
        return b;
    }

    public static ArrayList<Communities> fromJson(JSONArray jsonArray) {
        ArrayList<Communities> response = new ArrayList<Communities>(jsonArray.length());
        // Process each result in json array, decode and convert to CommunitiesResponseModel object
        for (int i=0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = null;
            try {
                jsonObject = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                Log.e(TAG, "Error processing JSONArray  " + jsonObject.toString());
                e.printStackTrace();
                continue;
            }

            Communities business = Communities.fromJson(jsonObject);
            if (business != null) {
                response.add(business);
            }
        }
        Log.e(TAG, "Success with JSONArray  " + response.get(0).getTitle().toString());

        return response;
    }

}
