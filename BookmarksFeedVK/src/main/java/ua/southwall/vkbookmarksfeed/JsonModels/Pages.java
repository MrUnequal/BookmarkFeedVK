package ua.southwall.vkbookmarksfeed.JsonModels;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mac on 3/13/15.
 */
public class Pages {


    private static String TAG = "Pages";
    private int id;
    private int group_id;
    private String title;
    private int edited;
    private int created;
    private int views;
    private int editor_id;
    private int creator_id;
    private String html;
    private String view_url;



    public int getId() {return this.id;}
    public int getGroupId() {return this.group_id;}
    public int getEdited() {
        return this.edited;
    }
    public int getCreated() {
        return this.created;
    }
    public int getViews() {
        return this.views;
    }
    public int getEditorId() {
        return this.editor_id;
    }
    public int getCreatorId() {
        return this.creator_id;
    }
    public String getViewUrl() {return this.view_url;}

    public static Pages fromJson(JSONObject jsonObject) {
        Pages b = new Pages();
        // Deserialize json into object fields
        try {
            b.id = jsonObject.getInt("id");
            b.group_id = jsonObject.getInt("group_id");
            b.edited = jsonObject.getInt("edited");
            b.created = jsonObject.getInt("created");
            b.views = jsonObject.getInt("views");
            b.editor_id = jsonObject.getInt("editor_id");
            b.creator_id = jsonObject.getInt("creator_id");
            b.html = jsonObject.getString("html");
            b.view_url = jsonObject.getString("view_url");

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Error processing JSONObject  " + jsonObject.toString());
            return null;
        }
        // Return new object
        return b;
    }

    public static ArrayList<Pages> fromJson(JSONArray jsonArray) {
        ArrayList<Pages> response = new ArrayList<Pages>(jsonArray.length());
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

            Pages business = Pages.fromJson(jsonObject);
            if (business != null) {
                response.add(business);
            }
        }
        //Log.e(TAG, "Success with JSONArray  " + response.get(0).getTitle().toString());

        return response;
    }




}
