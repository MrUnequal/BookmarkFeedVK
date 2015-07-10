package ua.southwall.vkbookmarksfeed.JsonModels;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mac on 2/27/15.
 */
public class FaveUserModel {
    private static String TAG = "FaveUserModel";
    private String lastName;
    private String id;
    private String firstName;

    public String getLastName() {
        return this.lastName;
    }

    public String getId() {
        return this.id;
    }

    public String fetFirstName() {
        return this.firstName;
    }

    public static FaveUserModel fromJson(JSONObject recievedMessage) {
        FaveUserModel b = new FaveUserModel();
        try {
            JSONArray items = new  JSONArray (recievedMessage.getJSONObject("response").getJSONArray("items").toString());
            Log.e(TAG, items.toString());
            //items.getJSONObject("d");
            for (int i = 0; i < items.length(); ++i) {
            }
                //b.id = recievedMessage.getString("id");
            //b.name = recievedMessage.getString("name");
            //b.phone = recievedMessage.getString("display_phone");
            //b.imageUrl = recievedMessage.getString("image_url");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return b;
    }
}
