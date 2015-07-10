package ua.southwall.vkbookmarksfeed.JsonModels;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mac on 7/10/15.
 */
public class Profile {
    private static String TAG = "Profile";

    private int id;
    private String first_name;
    private String last_name;
    private int sex;
    private String screen_name;
    private String photo_50;
    private String photo_100;
    private int online;


    public int getId(){
        return this.id;
    }

    public String getPhoto_100() {
        return this.photo_100;
    }

    public String getFullName() {
        return this.first_name + " " + this.last_name;
    }


    public static Profile fromJson(JSONObject jsonObject) {
        Profile b = new Profile();
        try {
            b.id = jsonObject.getInt("id");
            b.first_name = jsonObject.getString("first_name");
            b.last_name = jsonObject.getString("last_name");
            b.photo_50 = jsonObject.getString("photo_50");
            b.photo_100 = jsonObject.getString("photo_100");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Error processing Profile JSONObject  " + jsonObject.toString());
            return null;
        }
        return b;
    }

}
