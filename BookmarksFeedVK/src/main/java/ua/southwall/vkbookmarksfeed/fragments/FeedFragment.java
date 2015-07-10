package ua.southwall.vkbookmarksfeed.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.util.ArrayList;
import java.util.List;

import ua.southwall.vkbookmarksfeed.ContactInfo;
import ua.southwall.vkbookmarksfeed.JsonModels.UsersBookmarks;
import ua.southwall.vkbookmarksfeed.JsonModels.WallPost;
import ua.southwall.vkbookmarksfeed.R;
import ua.southwall.vkbookmarksfeed.adapters.FeedAdapter;
import ua.southwall.vkbookmarksfeed.util.UnixTimeConverter;

/**
 * Created by mac on 5/19/15.
 */
public class FeedFragment extends Fragment {


    private final String TAG = "FeedFragment";
    private ArrayList<WallPost> wallPosts;
    private SwipeRefreshLayout swipeContainer;
    private FeedAdapter adapter;
    private RecyclerView recList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        recList = (RecyclerView) view.findViewById(R.id.cardList);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainerFeed);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        setUpRecyclerView();
        WallPost.setContext(getActivity());//TODO 3 so dumb we parse context to data class  just to access resources!!
        //UnixTimeConverter.ConvertUnixToDate(((System.currentTimeMillis() / 1000L)-60*60*4-10*60)
        //, getActivity());

        getFeedItems(1,20);//durov feed TODO fetch all from UserBookmarks
        return view;
    }

    private void setUpRecyclerView(){
        adapter = new FeedAdapter(getActivity());
        recList.setAdapter(adapter);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e(TAG, "Pull to refresh activated");
                //getBookmarksList();
                //TODO 3 images not loaded!!! sick
                swipeContainer.setRefreshing(false);
            }
        });

    }





    private void getFeedItems(int ownerId, int count) {
        final VKRequest test = new VKRequest("wall.get");//TODO batch request
        test.addExtraParameter("owner_id", ownerId);//TODO communities have negative ids
        //test.addExtraParameter("filter", "owner");
        test.addExtraParameter("count", count);

        //test.attempts = 10;//TODO find optimal number of request

        test.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                Log.e(TAG, "SUCCESS, wall loaded " + response.json.toString());
                wallPosts = WallPost.fromJson(response.json);
                adapter.updateData(wallPosts);
                Log.e(TAG, "SUCCESS, changing data");

                adapter.notifyDataSetChanged();
                //update the list
                //TODO add spinner if no info
                //adapter.
                //adapter.addAll(wallPosts);//TODO crate inner method onDataChanged
                //getBookmarksAvatars(UsersBookmarks.getIds());
            }
            @Override
            public void onError(VKError error) {
                Log.e(TAG, "ERROR, wall not loaded  " + error.toString());
            }
        });
    }
}


