package ua.southwall.vkbookmarksfeed.util;

import android.util.Log;

import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.util.ArrayList;

import ua.southwall.vkbookmarksfeed.JsonModels.LinkBookmarks;

/**
 * Created by mac on 6/7/15.
 */
public class ApiRequests {
    private static String TAG = "ApiRequests";
    private static ArrayList<LinkBookmarks> list;


    public static ArrayList<LinkBookmarks> getBookmarksList() {
        final VKRequest test = new VKRequest("fave.getLinks");
        //test.attempts = 10;//TODO find optimal number of requests and delay between them
        test.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                Log.e(TAG, "SUCCESS, links loaded " + response.json.toString());
                list = LinkBookmarks.fromJson(response.json);
                //update the list
                //TODO add spinner if no info
                //adapter.addAll(list);
            }

            @Override
            public void onError(VKError error) {
                Log.e(TAG, "ERROR, links not loaded  " + error.toString());
                list = null;
            }
        });
        return list;
    }



}
