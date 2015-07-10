package ua.southwall.vkbookmarksfeed.util;

import android.util.Log;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKBatchRequest;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.util.ArrayList;

import ua.southwall.vkbookmarksfeed.JsonModels.LinkBookmarks;
import ua.southwall.vkbookmarksfeed.JsonModels.UserInfo;
import ua.southwall.vkbookmarksfeed.JsonModels.UsersBookmarks;
import ua.southwall.vkbookmarksfeed.MainActivity;

/**
 * Created by mac on 7/1/15.
 */
public class UserInfoPresenter {

    private UserInfo userInfo;
    public ArrayList <UsersBookmarks> usersBookmarks;//TODO 2 choose container (ArrayList, HashMap etc)
    public ArrayList <LinkBookmarks> linkBookmarks;
    private MainActivity view;
    private VKError error;
    private static String TAG = "UserInfoPresenter";

    public UserInfoPresenter () {

        //TODO 2 get info, links, users in one batch
        VKRequest requestInfo = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "photo_200"));
        VKRequest requestLinks = new VKRequest("fave.getLinks");
        VKRequest requestUsers = new VKRequest("fave.getUsers");
        VKBatchRequest batch = new VKBatchRequest(requestInfo, requestLinks, requestUsers);

        batch.executeWithListener(new VKBatchRequest.VKBatchRequestListener() {
            @Override
            public void onComplete(VKResponse[] responses) {
                super.onComplete(responses);
                Log.e(TAG, "SUCCESS, user initial loaded " + responses[2].json.toString());//TODO 2 even log messages should be in view not in presenter
                userInfo = UserInfo.fromJson(responses[0].json);
                linkBookmarks = LinkBookmarks.fromJson(responses[1].json);
                usersBookmarks = UsersBookmarks.fromJson(responses[2].json);
                publish();

            }

            @Override
            public void onError(VKError error) {
                Log.e(TAG, "ERROR, initial info not loaded  " + error.toString());
                UserInfoPresenter.this.error = error;
                publish();
            }
        });
    }
    public void onTakeView(MainActivity view) {//TODO 2 network check status, not possible in constructor due to the null object
        this.view = view;
        publish();
    }
    private void publish() {
        if (view != null) {
            if (userInfo != null)
                view.onSuccess(userInfo);
            else if (error != null)
                view.onError(error);
        }
    }
}
