package ua.southwall.vkbookmarksfeed.JsonModels;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mac on 3/11/15.
 */
public class LinkBookmarks {

    private static String TAG = "LinkBookmarks";
    private String id;
    private String title;
    private String url;
    private String description;
    private String photo_50;
    private String photo_100;


    private static JSONArray items;
    private static String[] avatars;
    private static ArrayList<LinkBookmarks> list;


    public String getTitle() {
        return this.title;
    }

    public String getUrl() {
        return this.url;
    }
    public String getDescription() {
        return this.description;
    }
    public String getPhoto_50() {
        return this.photo_50;
    }
    public String getPhoto_100() {
        return this.photo_100;
    }

    public String getId() {
        return this.id;
    }

    public static LinkBookmarks fromJsonSingle(JSONObject jsonObject) {
        LinkBookmarks b = new LinkBookmarks();
        // Deserialize json into object fields
        try {
            b.id = jsonObject.getString("id");
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
        //Log.e(TAG, "Success processing JSONObject  " + jsonObject.toString());

        // Return new object
        return b;
    }

    public static ArrayList<LinkBookmarks> fromJson(JSONObject jsonArray) {
        ArrayList<LinkBookmarks> response = new ArrayList<>(jsonArray.length());
        // Process each result in json array, decode and convert to CommunitiesResponseModel object
        try {

            items = jsonArray.getJSONObject("response").getJSONArray("items");

            for (int i=0; i < items.length(); i++) {
                JSONObject jsonObject = null;
                jsonObject = items.getJSONObject(i);
                LinkBookmarks business = LinkBookmarks.fromJsonSingle(jsonObject);
                if (business != null) {
                    response.add(business);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error processing JSONArray  " + items.toString());
            e.printStackTrace();
        }
        //Log.e(TAG, "SUCCESS processing JSONArray  " + response.size());
        list = response;
        return response;
    }

}
