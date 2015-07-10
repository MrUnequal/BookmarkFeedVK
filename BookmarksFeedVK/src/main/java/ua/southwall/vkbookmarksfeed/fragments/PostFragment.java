package ua.southwall.vkbookmarksfeed.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import ua.southwall.vkbookmarksfeed.JsonModels.Comment;
import ua.southwall.vkbookmarksfeed.JsonModels.Comments;
import ua.southwall.vkbookmarksfeed.JsonModels.LinkBookmarks;
import ua.southwall.vkbookmarksfeed.JsonModels.WallPost;
import ua.southwall.vkbookmarksfeed.R;
import ua.southwall.vkbookmarksfeed.adapters.CommentsAdapter;

/**
 * Created by mac on 7/9/15.
 */
public class PostFragment extends Fragment {//TODO 3 when fragemnt is recreated only the no parameter constructor is called, colution - use Bundle();

    private WallPost post;
    private SwipeRefreshLayout swipeContainer;
    private CommentsAdapter adapter;
    private Comments list = new Comments();
    private  View view;
    private RecyclerView recList;

    private static String TAG = "PostFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_post, container, false);

        recList = (RecyclerView) view.findViewById(R.id.post_comments);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainerComments);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        setUpListView(view);
        return view;
    }
    public static PostFragment newInstance(WallPost post){
        PostFragment fragment = new PostFragment();
        fragment.post = post;
        return fragment;
    }

    private void setUpListView(View view){//TODO move setup to adapter
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e(TAG, "Pull to refresh activated");
                //getBookmarksList();
                //getBookmarksAvatars(UsersBookmarks.getIds());//TODO 3 too many requests error may occur! handle it
                swipeContainer.setRefreshing(false);
            }
        });
        getComments();
        if(list==null)
            Log.e(TAG, "WTF");
        //else
        //    getBookmarksAvatars(UsersBookmarks.getIds());

        adapter = new CommentsAdapter(getActivity());
        if(recList==null)
            Log.e(TAG,"wtf2");
        recList.setAdapter(adapter);
        /*recList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Comment item = (Comment) adapterView.getItemAtPosition(i);
                showSnackBar(item, i);
            }
        });*/

    }


    private void getComments(){
        VKRequest request = new VKRequest("wall.getComments");
        request.addExtraParameter("post_id", post.getId());//post_id
        request.addExtraParameter("owner_id", post.getFromId());//id
        request.addExtraParameter("count", 100);//count max 100, problem here
        request.addExtraParameter("extended", 1);//needed to load profiles TODO check if other methods can do the same, seems like new feature
        request.addExtraParameter("sort", "desc");//descending order


        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                Log.e(TAG, "SUCCESS, comments added " + response.json.toString());
                list = Comments.fromJson(response.json);
                adapter.updateData(list);
                Log.e(TAG, "SUCCESS, changing data");

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(VKError error) {
                Log.e(TAG, "ERROR, user not added  " + error.toString());
            }
        });
        return;
    }

    private void showSnackBar(final Comment item, final int position) {
        Snackbar.make(view, "asd", Snackbar.LENGTH_LONG).setAction(getResources().getString(R.string.undo), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        }).show();
    }

/*
    private void updateData(Comments comments){
        adapter.clear();
        adapter.addAll(comments);

    }
*/
}


