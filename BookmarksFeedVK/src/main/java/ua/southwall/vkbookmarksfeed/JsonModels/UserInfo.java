package ua.southwall.vkbookmarksfeed.JsonModels;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mac on 6/29/15.
 */
public class UserInfo {

    //TODO 2 get all possible data


    private static String TAG = "UserInfo";
    private String last_name;
    private int id;
    private String first_name;
    private String photo_200;

    public int getId() {
        return this.id;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public String getFullName() {
        return first_name + " " + last_name;
    }

    public String getPhoto_200() {
        return photo_200;
    }

    public static UserInfo fromJson(JSONObject jsonObject) {
        UserInfo response = new UserInfo();
        try {
            jsonObject = jsonObject.getJSONArray("response").getJSONObject(0);//TODO 2 SICK MANIPULATIONS TO GET TO THE POINT
            response.last_name = jsonObject.getString("last_name");
            response.id = jsonObject.getInt("id");
            response.first_name = jsonObject.getString("first_name");
            response.photo_200=jsonObject.getString("photo_200");
        } catch (JSONException e) {
            Log.e(TAG, "Error processing UserInfo JSONObject  " + jsonObject.toString());
            e.printStackTrace();
            return null;
        }
        //Log.e(TAG, "New UserInfo object success  " + response.getFullName());
        return response;
    }
}
