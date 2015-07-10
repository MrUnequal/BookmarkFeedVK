package ua.southwall.vkbookmarksfeed.JsonModels;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mac on 3/13/15.
 */
public class Audio {
    private static String TAG = "Audio";
    private int id;
    private int owner_id;
    private String artist;
    private String title;
    private int duration;
    private String url;
    private int lyrics_id;
    private int genre_id;

    public int getId()       {return this.id;}
    public int getOwnerId()  {return this.owner_id;}
    public String getArtist(){return this.artist;}
    public String getTitle() {return this.title;}
    public int getDuration() {return this.duration;}
    public String getUrl()   {return this.url;}
    public int getLyricsId() {return this.lyrics_id;}
    public int getGenreId()  {return this.genre_id;}


    public static Audio fromJson(JSONObject jsonObject) {
        Audio b = new Audio();
        // Deserialize json into object fields
        try {
            b.id = jsonObject.getInt("id");
            b.owner_id = jsonObject.getInt("owner_id");
            b.artist = jsonObject.getString("artist");
            b.title = jsonObject.getString("title");
            b.duration = jsonObject.getInt("duration");
            b.url = jsonObject.getString("url");
            b.lyrics_id = jsonObject.getInt("lyrics_id");
            b.genre_id = jsonObject.getInt("genre_id");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Error processing JSONObject  " + jsonObject.toString());
            return null;
        }
        // Return new object
        return b;
    }

    public static ArrayList<Audio> fromJson(JSONArray jsonArray) {
        ArrayList<Audio> response = new ArrayList<Audio>(jsonArray.length());
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

            Audio business = Audio.fromJson(jsonObject);
            if (business != null) {
                response.add(business);
            }
        }
        //Log.e(TAG, "Success with JSONArray  " + response.get(0).getTitle().toString());

        return response;
    }
}
