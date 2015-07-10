package ua.southwall.vkbookmarksfeed.JsonModels;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mac on 3/13/15.
 */
public class Video {
    private static String TAG = "Video";
    private int id;
    private int owner_id;
    private String title;
    private String description;
    private int date;
    private int views;
    private int comments;
    private String photo_130;
    private String photo_320;
    private Files files;//TODO replace ArrayLsit with key - value pairs, if I'm not mistaking it's HashMap
    //keys such as  mp_240, mp_360, mp_480, mp_720
    private String player;
    private int likes_count;


    public int getId() {return this.id;}
    public int getOwnerId() {return this.owner_id;}
    public String getTitle() {return this.title;}
    public String getDescription() {return this.description;}
    public int getDate() {return this.date;}
    public int getViews() {return this.views;}
    public int getComments() {return this.comments;}
    public String getPhoto130() {return this.photo_130;}
    public String getPhoto320() {return this.photo_320;}
    public Files getFiles() {return this.files;}
    public String getPlayer() {return this.player;}
    public int getLikesCount() {return this.likes_count;}



    public static Video fromJson(JSONObject jsonObject) {
        Video b = new Video();
        // Deserialize json into object fields
        try {


            b.id = jsonObject.getInt("id");
            b.owner_id = jsonObject.getInt("owner_id");
            b.title = jsonObject.getString("titel");
            b.description = jsonObject.getString("description");
            b.date = jsonObject.getInt("date");
            b.views = jsonObject.getInt("views");
            b.comments = jsonObject.getInt("comments");
            b.photo_130 = jsonObject.getString("photo_130");
            b.photo_320 = jsonObject.getString("photo_320");
            b.files = Files.fromJson(jsonObject.getJSONObject("files"));
            b.player = jsonObject.getString("player");
            b.likes_count = jsonObject.getJSONObject("likes").getInt("count");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Error processing JSONObject  " + jsonObject.toString());
            return null;
        }
        // Return new object
        return b;
    }

    public static ArrayList<Video> fromJson(JSONArray jsonArray) {
        ArrayList<Video> response = new ArrayList<Video>(jsonArray.length());
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

            Video business = Video.fromJson(jsonObject);
            if (business != null) {
                response.add(business);
            }
        }
        //Log.e(TAG, "Success with JSONArray  " + response.get(0).getTitle().toString());

        return response;
    }

}
