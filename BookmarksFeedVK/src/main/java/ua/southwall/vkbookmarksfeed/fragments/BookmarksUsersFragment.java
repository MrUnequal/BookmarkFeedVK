package ua.southwall.vkbookmarksfeed.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vk.sdk.api.VKBatchRequest;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import ua.southwall.vkbookmarksfeed.JsonModels.LinkBookmarks;
import ua.southwall.vkbookmarksfeed.JsonModels.UsersBookmarks;
import ua.southwall.vkbookmarksfeed.R;
import ua.southwall.vkbookmarksfeed.adapters.BookmarksUsersAdapter;

/**
 * Created by mac on 3/17/15.
 */
public class BookmarksUsersFragment extends Fragment {

    private SwipeRefreshLayout swipeContainer;
    private static ArrayList<UsersBookmarks> list;
    private BookmarksUsersAdapter adapter;
    final static String TAG ="BookmarksUsersFragment";
    private View view;


    public static BookmarksUsersFragment newInstance(ArrayList<UsersBookmarks> usersBookmarks) {
        list = usersBookmarks;
        return new BookmarksUsersFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bookmarks_users, container, false);
        setUpListView(view);
        return view;
    }

    private void setUpListView(View view){//TODO move setup to adapter
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e(TAG, "Pull to refresh activated");
                getBookmarksList();
                getBookmarksAvatars(UsersBookmarks.getIds());//TODO 3 too many requests error may occur! handle it
                swipeContainer.setRefreshing(false);
            }
        });
        if(list==null)
            getBookmarksList();//TODO 3 wtf we change adapter when its null, inside we call updateData();
        else
            getBookmarksAvatars(UsersBookmarks.getIds());
        adapter = new BookmarksUsersAdapter(getActivity(), list);
        ListView listView = (ListView) view.findViewById(R.id.testList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                UsersBookmarks item = (UsersBookmarks) adapterView.getItemAtPosition(i);
                showSnackBar(item, i);
            }
        });

    }

    private void showSnackBar(final UsersBookmarks item, final int position){

        final VKRequest test = new VKRequest("fave.removeUser");//TODO 3 if we got error in removing, restoring adds a duplicate!!
        test.addExtraParameter("user_id", item.getId());//TODO 3 add restore all option!!! for safety of course
        test.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                Log.e(TAG, "SUCCESS, user removed " + response.json.toString());
                updateData(removeItemFromData(position));
            }
            @Override
            public void onError(VKError error) {
                Log.e(TAG, "ERROR, user not removed  " + error.toString());
            }
        });
        Snackbar.make(view, item.getId() + " " + getResources().getString(R.string.removed), Snackbar.LENGTH_LONG).setAction(getResources().getString(R.string.undo), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final VKRequest test = new VKRequest("fave.addUser");
                test.addExtraParameter("user_id", item.getId());
                test.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        Log.e(TAG, "SUCCESS, user added " + response.json.toString());
                        updateData(restoreItemToData(item, position));
                        //updateData(list);

                    }

                    @Override
                    public void onError(VKError error) {
                        Log.e(TAG, "ERROR, user not added  " + error.toString());
                    }
                });


            }
        }).show();


    }
    private ArrayList<UsersBookmarks> removeItemFromData(int position){
        list.remove(position);
        return list;

    }

    private ArrayList<UsersBookmarks> restoreItemToData(UsersBookmarks data, int position ){
        list.add(position, data);
        return list;
    }

    private void updateData(ArrayList<UsersBookmarks> data){
        //adapter.clear();
        //adapter.addAll(data);
        list = data;
        adapter.notifyDataSetChanged();
    }


    private void getBookmarksList() {
        final VKRequest test = new VKRequest("fave.getUsers");
        test.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                Log.e(TAG, "SUCCESS, users loaded " + response.json.toString());
                list = UsersBookmarks.fromJson(response.json);
                //update the list
                //TODO add spinner if no info
                adapter.clear();
                adapter.addAll(list);

            }

            @Override
            public void onError(VKError error) {
                Log.e(TAG, "ERROR, users not loaded  " + error.toString());
            }
        });
        }

    private void getBookmarksAvatars(int[] ids) {
        VKRequest[] requests  = new VKRequest[ids.length];
        Log.e(TAG, "Not loaded from presenter");
        for (int i =0;i<ids.length;i++) {
            VKRequest request = new VKRequest("photos.getProfile");
            //request.attempts=10;
            request.addExtraParameter("count", 1);
            request.addExtraParameter("owner_id", ids[i]);
            Log.e(TAG, "SUCCESS, avatars   " + ids[i]);
            request.addExtraParameter("rev", 1);
            requests[i] = request;

        }
        VKBatchRequest batch = new VKBatchRequest(requests);
        batch.executeWithListener(new VKBatchRequest.VKBatchRequestListener() {
            @Override
            public void onComplete(VKResponse[] responses) {
                super.onComplete(responses);
                ArrayList<JSONObject> response = new ArrayList<JSONObject>(responses.length);
                for (int i = 0; i < responses.length; i++) {
                    Log.e(TAG, "SUCCESS, avatars loaded  " + responses[i].json.toString());
                    response.add(responses[i].json);
                }
                UsersBookmarks.setAvatars(response);
                adapter.notifyDataSetChanged();//update data
            }

            @Override
            public void onError(VKError error) {
                Log.e(TAG, "ERROR, avatars no loaded  " + error.toString());

            }
        });
    }
}
