package ua.southwall.vkbookmarksfeed.JsonModels;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mac on 7/10/15.
 */
public class Comments {//TODO 3 only call if comments count in wallpost is nonzero
    private static String TAG = "Comments";

    private ArrayList<Comment> comments;
    private ArrayList<Profile> profiles;

    public ArrayList<Comment> getComments(){
        if (comments!=null)
            return this.comments;
        return null;
    }


    public ArrayList<Profile> getProfiles() {
        if (profiles != null)
            return this.profiles;
        return null;
    }

    public String getPhotoById(int id){
        for (int i = 0; i<profiles.size();i++)
            if(id == profiles.get(i).getId())
                return profiles.get(i).getPhoto_100();
        return null;
    }
    public String getFullNameById(int id){
        for (int i = 0; i<profiles.size();i++) {
            if (id == profiles.get(i).getId())
                return profiles.get(i).getPhoto_100();
        }
        return null;
    }

    public static Comments fromJson(JSONObject jsonArray) {
        Comments response = new Comments();

        response.comments = new ArrayList<>();
        response.profiles = new ArrayList<>();
        try {
            JSONArray itemsComments = jsonArray.getJSONObject("response").getJSONArray("items");
            JSONArray itemsProfiles = jsonArray.getJSONObject("response").getJSONArray("profiles");
            for (int i=0; i < itemsComments.length(); i++) {
                Comment obj = Comment.fromJson(itemsComments.getJSONObject(i));
                if (obj != null) {
                    response.comments.add(obj);
                }
            }
            for (int i=0; i < itemsProfiles.length(); i++) {
                Profile obj = Profile.fromJson(itemsProfiles.getJSONObject(i));
                if (obj != null) {

                    response.profiles.add(obj);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "ERROR processing Comments  " + e.toString());
            e.printStackTrace();
        }
        Log.e(TAG, "processed Comments");

        return response;
    }
}
