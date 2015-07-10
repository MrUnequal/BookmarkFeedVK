package ua.southwall.vkbookmarksfeed.JsonModels;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mac on 7/10/15.
 */
public class Comment {//TODO 3 only text first, no attachment
    private static String TAG = "Comment";
    private int id;
    private int from_id;
    private int date;
    private String text;
    private Attachment attachment;//TODO not done

    public int getId() {
        return this.id;
    }
    public int getFromId() {
        return this.from_id;
    }


    public String getText() {
        return this.text;
    }

    public int getDate() {
        return this.date;
    }

    public static Comment fromJson(JSONObject jsonObject) {
        Comment b = new Comment();
        // Deserialize json into object fields
        try {
            b.id = jsonObject.getInt("id");
            b.from_id = jsonObject.getInt("from_id");
            b.date = jsonObject.getInt("date");
            b.text = jsonObject.getString("text");
           // b.attachment = jsonObject.getString("attachment");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Error processing Comment JSONObject  " + jsonObject.toString());
            return null;
        }
        return b;
    }
}
