package ua.southwall.vkbookmarksfeed.JsonModels;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mac on 3/13/15.
 */
public class Photo {


    private static String TAG = "Photo";
    private int id;//TODO review types to match data types from responses
    private int album_id;
    private int  owner_id;
    private String photo_75;
    private String photo_130;//TODO analyse with different photo sizes
    private String photo_604;
    private String photo_807;

    private int width;
    private int height;
    private String text;
    private int date;
    private String access_key;

    private static JSONArray items;
    private static ArrayList<Photo> list;


    private String post_source_type;
    private int comments_count;
    private int likes_count;
    private int reposts_count;

    public int getId() {return this.id;}
    public int getAlbumId() {return this.album_id;}
    public int getOwnerId() {return this.owner_id;}
    public String getPhoto_75() {return this.photo_75;}
    public String getPhoto_130() {return this.photo_130;}
    public String getPhoto_604() {return this.photo_604;}
    public String get_Photo_807() {return this.photo_807;}
    public int getWidth() {return this.width;}
    public int getHeight() {return this.height;}
    public String getText() {return this.text;}
    public int getDate() {
        return this.date;
    }
    public String getAccessKey() {
        return this.access_key;
    }


    public String getPostSourceType() {return this.post_source_type;}
    public int getCommentsCount() {return this.comments_count;}
    public int getLikesCount() {return this.likes_count;}
    public int getRepostsCount() {
        return this.reposts_count;
    }



    public static Photo fromJsonSingle(JSONObject jsonObject) {
        Photo b = new Photo();
        // Deserialize json into object fields
        try {
            b.id = jsonObject.getInt("id");
            b.album_id = jsonObject.getInt("album_id");
            b.owner_id = jsonObject.getInt("owner_id");
            b.photo_75 = jsonObject.getString("photo_75");
            b.photo_130 = jsonObject.getString("photo_130");
            //b.photo_604 = jsonObject.getString("photo_604");
            //b.photo_807 = jsonObject.getString("photo_807");//TODO not sure about those photo sizes
            //b.width = jsonObject.getInt("width");
            //b.height = jsonObject.getInt("height");
            //b.text = jsonObject.getString("text");
            //b.access_key = jsonObject.getString("access_key");
            //b.post_source_type = jsonObject.getJSONObject("post_source").getString("type");
            //b.comments_count = jsonObject.getJSONObject("comments").getInt("count");
            //b.likes_count = jsonObject.getJSONObject("likes").getInt("count");
            //b.reposts_count = jsonObject.getJSONObject("reposts").getInt("count");

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Error processing JSONObject  " + jsonObject.toString());
            return null;
        }
        // Return new object
        return b;
    }

    public static ArrayList<Photo> fromJson(JSONObject jsonArray) {

        ArrayList<Photo> response = new ArrayList<Photo>(jsonArray.length());
        JSONObject jsonObject = null;
        try {
            items = jsonArray.getJSONObject("response").getJSONArray("items");
        // Process each result in json array, decode and convert to CommunitiesResponseModel object
        for (int i=0; i < jsonArray.length(); i++) {
            jsonObject = items.getJSONObject(i);

            Photo business = Photo.fromJsonSingle(jsonObject);
            if (business != null) {
                response.add(business);
            }
        }
        } catch (Exception e) {
            Log.e(TAG, "Error processing JSONArray  " + jsonObject.toString());
            e.printStackTrace();
        }
        //Log.e(TAG, "Success with JSONArray  " + response.get(0).getTitle().toString());

        return response;
    }

}
