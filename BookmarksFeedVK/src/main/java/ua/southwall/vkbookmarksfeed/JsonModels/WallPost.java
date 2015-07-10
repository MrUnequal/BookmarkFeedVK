package ua.southwall.vkbookmarksfeed.JsonModels;

/**
 * Created by mac on 3/12/15.
 */


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import ua.southwall.vkbookmarksfeed.util.UnixTimeConverter;

/**
 * Created by mac on 3/11/15.
 */
public class WallPost implements Comparable<WallPost>{

    private static String TAG = "WallPost";
    private static Context context;
    private int id;
    private int from_id;
    private int owner_id;
    private String name;
    private String photo;
    private int date;
    private String post_type;
    private String text;
    private ArrayList <Attachment> attachments;
    //next items not thought through
    private static JSONArray items;
    private String post_source_type;
    private int comments_count;
    private int likes_count;
    private int repost_count;
    private String dateString;

    public String getName(){return this.name;}

    public String getPhoto() {return  this.photo;}

    public void setName(String name){this.name = name;}
    public static void setContext(Context mContext){context = mContext;}


    public void setPhoto(String photo) {this.photo = photo;}

    public int getId() {return this.id;}

    public int getFromId() {return this.from_id;}

    public int getOwnerId() { return this.owner_id;}

    public String getText() { return this.text;}


    public int getDate() { return this.date;}

    public String getPostType() { return this.post_type;}

    public ArrayList <Attachment> getAttachments() { return this.attachments;}

    public int getCommentsCount() {return this.comments_count;}

    public int getLikesCount() {return this.likes_count;}

    public int getRepostCount() { return this.repost_count;}

    public String getDateString(){ return this.dateString;}

    private static String transformUnixTime(int date){
       return UnixTimeConverter.ConvertUnixToDate(date, context);
    }


    public static WallPost fromJsonSingle(JSONObject jsonObject) {
        WallPost b = new WallPost();
        // Deserialize json into object fields
        try {
            b.id = jsonObject.getInt("id");
            b.from_id = jsonObject.getInt("from_id");
            b.owner_id = jsonObject.getInt("owner_id");
            b.date = jsonObject.getInt("date");
            b.dateString = transformUnixTime(b.date);
            b.text = jsonObject.getString("text");
            b.post_type = jsonObject.getString("post_type");
            //b.attachments = jsonObject.getJSONArray("attachments").;//TODO attachment should be processed somewhere else
            b.comments_count = jsonObject.getJSONObject("comments").getInt("count");
            b.likes_count = jsonObject.getJSONObject("likes").getInt("count");
            b.repost_count = jsonObject.getJSONObject("reposts").getInt("count");


        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Error processing JSONObject  " + jsonObject.toString());
            return null;
        }
        //Log.e(TAG, "SUCCESS, WallPost object " + jsonObject.toString() );

        // Return new object
        return b;
    }

    public static ArrayList<WallPost> fromJson(JSONObject jsonArray) {
        ArrayList<WallPost> response = new ArrayList<WallPost>(jsonArray.length());
        // Process each result in json array, decode and convert to CommunitiesResponseModel object
        try {

        items = jsonArray.getJSONObject("response").getJSONArray("items");

        for (int i=0; i < items.length(); i++) {
            JSONObject jsonObject = null;
                jsonObject = items.getJSONObject(i);
            WallPost business = WallPost.fromJsonSingle(jsonObject);
            if (business != null) {
                response.add(business);
            }
        }
        } catch (Exception e) {
            Log.e(TAG, "Error processing JSONArray  " + items.toString());
            e.printStackTrace();//TODO try block outside of for means no continue on fail
        }
        //Log.e(TAG, "Success with JSONArray  " + response.get(0).getTitle().toString());
        Log.e(TAG, "SUCCESS, WallPost array " + response.size());

        return response;
    }

    @Override//TODO FIRST MORNING
    public int compareTo(WallPost wallPost) {//TODO cursor as private field?
        if (this.getDate()>wallPost.getDate())
            return 1;
        else
            return -1;
    }
}
