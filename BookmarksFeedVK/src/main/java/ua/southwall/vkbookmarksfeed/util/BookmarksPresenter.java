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
import ua.southwall.vkbookmarksfeed.fragments.BookmarksFragment;

/**
 * Created by mac on 7/1/15.
 */
public class BookmarksPresenter {
    private ArrayList <UsersBookmarks> usersBookmarks;//TODO 2 choose container (ArrayList, HashMap etc)
    private ArrayList <LinkBookmarks> linkBookmarks;
    private BookmarksFragment view;
    private VKError error;
    private static String TAG = "UserInfoPresenter";

    public BookmarksPresenter (ArrayList<UsersBookmarks> usersBookmark, ArrayList<LinkBookmarks> linkBookmark) {
        this.linkBookmarks=linkBookmark;
        this.usersBookmarks=usersBookmark;

    }
    public void onTakeView(BookmarksFragment view) {//TODO 2 network check status, not possible in constructor due to the null object
        this.view = view;
        publish();
    }
    private void publish() {
        if (view != null) {
            if (linkBookmarks != null && usersBookmarks!=null)
                return;
                //view.onSuccess(usersBookmarks, linkBookmarks);
            else if (error != null)
                return;
                //view.onError(error);
        }
    }
}
