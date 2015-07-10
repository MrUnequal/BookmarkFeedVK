package ua.southwall.vkbookmarksfeed.JsonModels;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mac on 3/13/15.
 */

public class Files {
    private static String TAG = "Files";
    private String mp4_240;
    private String mp4_360;
    private String mp4_480;
    private String mp4_720;
    private String mp4_1080; //TODO 1080 and above resolution may or may not be possible;



    public String getMp4_420() {return this.mp4_240;}
    public String getMp4_360() {return this.mp4_360;}
    public String getMp4_480() {return this.mp4_480;}
    public String getMp4_720() {return this.mp4_720;}
    public String getMp4_1080() {return this.mp4_1080;}


    public static Files fromJson(JSONObject jsonObject) {
        Files b = new Files();
        // Deserialize json into object fields
        try {
            b.mp4_240 = jsonObject.getString("mp4_240");
            b.mp4_360 = jsonObject.getString("mp4_360");
            b.mp4_480 = jsonObject.getString("mp4_480");
            b.mp4_720 = jsonObject.getString("mp4_720");
            b.mp4_1080 = jsonObject.getString("mp4_1080");//TODO find a way to smartly process video resolutions

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Error processing JSONObject  " + jsonObject.toString());
            return null;
        }
        // Return new object
        return b;
    }

}
