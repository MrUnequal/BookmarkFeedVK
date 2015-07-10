package ua.southwall.vkbookmarksfeed.JsonModels;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mac on 3/11/15.
 */
public class UsersBookmarks {

    private static String TAG = "UsersBookmarks";
    private String last_name;
    private int id;
    private String first_name;
    private static JSONArray items;
    private String avatar;
    private static ArrayList<UsersBookmarks> list;//TODO 2 may be used everywhere?
    private WallPost posts;

    public WallPost  getPosts(){
        return this.posts;
    }
    public void setPosts(WallPost posts){
        this.posts=posts;

    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
        //Log.e(TAG, "avatar set to " + avatar);
    }

    public String getAvatar(){
        return this.avatar;
    }


    public static String getFullNameByid(int id) {
        for (int i = 0; i < list.size(); i++)
            if (id == list.get(i).getId()) {
                return list.get(i).getFullName();
            }
        return "";//TODO null check
    }

    public static String getPhotoById(int id) {

        for (int i = 0; i < list.size(); i++) {
            Log.e(TAG,list.get(i).getPhoto()  + "    "+ id );
            if (id == list.get(i).getId()) {
                Log.e(TAG, list.get(i).getPhoto());
                return list.get(i).getPhoto();
            }
        }
        return "";//TODO null check
    }


    public static void setAvatars (ArrayList<JSONObject> input) {
        for (int i = 0; i < input.size(); i++)
        try{
            list.get(i).setAvatar( input.get(i).getJSONObject("response").getJSONArray("items").getJSONObject(0).getString("photo_130"));//TODO horrible wall of text
        }
         catch (Exception e) {
        Log.e(TAG, "Error processing JSONArray  Avatars " + input.get(i).toString());//TODO if happens - no avatar
             list.get(i).setAvatar("");
        e.printStackTrace();
        }
    }

    public String getPhoto() {
        return this.avatar;
    }


    public String getLastName() {
        return this.last_name;
    }

    public String getFullName() {
        return this.first_name + " " + this.last_name;
    }

    public int getId() {
        return this.id;
    }
    public String getFirstName() {
        return this.first_name;
    }
    public static ArrayList<UsersBookmarks> getList() {
        return list;
    }


    public static int[] getIds(){
        int[] result = new int[list.size()];
        for (int i=0; i < result.length; i++) {
            result[i] = list.get(i).id;
        }
        Log.e(TAG, "list size  " + list.size());
        Log.e(TAG, "result size  " + result.length);
        return result;
    }

    public static UsersBookmarks fromJsonSingle(JSONObject jsonObject) {
        UsersBookmarks b = new UsersBookmarks();
        // Deserialize json into object fields
        try {
            b.last_name = jsonObject.getString("last_name");
            b.id = jsonObject.getInt("id");
            b.first_name = jsonObject.getString("first_name");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Error processing JSONObject  " + jsonObject.toString());
            return null;
        }

        // Return new object
        return b;
    }


    public static UsersBookmarks fromJsonSingleAdd(JSONObject jsonObject) {
        UsersBookmarks b = new UsersBookmarks();
        // Deserialize json into object fields
        try {
            for(int i = 0 ; i<list.size();i++)
                if(jsonObject.getInt("id")==list.get(i).getId()){
                  //  b.photo = jsonObject.getInt("id");
                    b.first_name = jsonObject.getString("first_name");
                }


        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "ERROR processing JSONObject Add  " + jsonObject.toString());
            return null;
        }
        return b;
    }

    public static ArrayList<UsersBookmarks> fromJson(JSONObject jsonArray) {
        ArrayList<UsersBookmarks> response = new ArrayList<>(jsonArray.length());
        // Process each result in json array, decode and convert to CommunitiesResponseModel object
        try {

            items = jsonArray.getJSONObject("response").getJSONArray("items");

        for (int i=0; i < items.length(); i++) {
            JSONObject jsonObject = null;
            jsonObject = items.getJSONObject(i);
            UsersBookmarks business = UsersBookmarks.fromJsonSingle(jsonObject);
            if (business != null) {
                response.add(business);
            }
        }
        } catch (Exception e) {
            Log.e(TAG, "ERROR processing JSONArray  " + items.toString());
            e.printStackTrace();
        }
        //Log.e(TAG, "SUCCESS with JSONArray  " + response.size());
        list = response;
        return response;
    }
}
