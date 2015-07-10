package ua.southwall.vkbookmarksfeed.JsonModels;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mac on 3/13/15.
 */
public class Polls {


    private static String TAG = "Polls";
    private int id;
    private int owner_id;
    private int created;
    private String question;
    private int votes;
    private int answer_id;
    private ArrayList <Answers> answers;
    private int anonymous;




    public static Polls fromJson(JSONObject jsonObject) {
        Polls b = new Polls();
        // Deserialize json into object fields
        try {
            b.id = jsonObject.getInt("id");
            b.owner_id = jsonObject.getInt("owner_id");
            b.created = jsonObject.getInt("created");
            b.question = jsonObject.getString("question");
            b.votes = jsonObject.getInt("votes");
            b.answer_id = jsonObject.getInt("answer_id");
            b.answers = Answers.fromJson(jsonObject.getJSONArray("answers"));
            b.anonymous = jsonObject.getInt("anonymous");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Error processing JSONObject  " + jsonObject.toString());
            return null;
        }
        // Return new object
        return b;
    }

    public static ArrayList<Polls> fromJson(JSONArray jsonArray) {
        ArrayList<Polls> response = new ArrayList<Polls>(jsonArray.length());
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

            Polls business = Polls.fromJson(jsonObject);
            if (business != null) {
                response.add(business);
            }
        }
        //Log.e(TAG, "Success with JSONArray  " + response.get(0).getTitle().toString());

        return response;
    }


    private static class Answers {

        private String TAG = "Answers";

        private int id;
        private String text;
        private int votes;
        private float rate;//TODO, after coma value

        public int getId() {return this.id;}
        public String getText() {return this.text;}
        public int getVotes() {return this.votes;}
        public float getRate() {return this.rate;}


    public static Answers fromJson(JSONObject jsonObject) {
        Answers b = new Answers();
        // Deserialize json into object fields
        try {
            b.id = jsonObject.getInt("id");
            b.text = jsonObject.getString("text");
            b.votes = jsonObject.getInt("votes");
           // b.rate = jsonObject.getFloat("rate");//TODO no getFlaot method

        } catch (JSONException e) {
            e.printStackTrace();
            //Log.e(TAG, "Error processing JSONObject  " + jsonObject.toString());
            return null;
        }
        // Return new object
        return b;
    }

    public static ArrayList<Answers> fromJson(JSONArray jsonArray) {
        ArrayList<Answers> response = new ArrayList<Answers>(jsonArray.length());
        // Process each result in json array, decode and convert to CommunitiesResponseModel object
        for (int i=0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = null;
            try {
                jsonObject = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                //Log.e(TAG, "Error processing JSONArray  " + jsonObject.toString());
                e.printStackTrace();
                continue;
            }

            Answers business = Answers.fromJson(jsonObject);
            if (business != null) {
                response.add(business);
            }
        }
        //Log.e(TAG, "Success with JSONArray  " + response.get(0).getTitle().toString());

        return response;
    }



    }

}
