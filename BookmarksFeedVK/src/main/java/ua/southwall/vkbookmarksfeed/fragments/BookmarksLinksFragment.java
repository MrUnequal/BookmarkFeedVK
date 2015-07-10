package ua.southwall.vkbookmarksfeed.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.util.ArrayList;

import ua.southwall.vkbookmarksfeed.JsonModels.LinkBookmarks;
import ua.southwall.vkbookmarksfeed.JsonModels.UsersBookmarks;
import ua.southwall.vkbookmarksfeed.R;
import ua.southwall.vkbookmarksfeed.adapters.BookmarksLinksAdapter;
import ua.southwall.vkbookmarksfeed.util.ApiRequests;

/**
 * Created by mac on 3/17/15.
 */
public class BookmarksLinksFragment extends Fragment {

    private SwipeRefreshLayout swipeContainer;
    private static ArrayList<LinkBookmarks> list;
    private BookmarksLinksAdapter adapter;
    final static String TAG ="BookmarksLinksFragment";
    private View view;


    public static BookmarksLinksFragment newInstance(ArrayList<LinkBookmarks> links){
        list = links;//first run
        return new BookmarksLinksFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bookmarks_links, container, false);
        setUpListView(view);
        return view;
    }

    private void setUpListView(View view) {//TODO move setup to adapter
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainerLinks);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e(TAG, "Pull to refresh activated");
                getBookmarksList();
                //TODO 3 images not loaded!!! sick
                swipeContainer.setRefreshing(false);
            }
        });
        if (list == null)
            getBookmarksList();
        //ArrayList<LinkBookmarks> arrayOfLinks = new ArrayList<>();
        adapter = new BookmarksLinksAdapter(getActivity(), list);
        ListView listView = (ListView) view.findViewById(R.id.linksList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LinkBookmarks item = (LinkBookmarks) adapterView.getItemAtPosition(i);
                showSnackBar(item, i);
            }
        });
    }
        private void showSnackBar(final LinkBookmarks item, final int position){

            final VKRequest test = new VKRequest("fave.removeLink");
            test.addExtraParameter("link_id", item.getId());
            test.executeWithListener(new VKRequest.VKRequestListener() {
                @Override
                public void onComplete(VKResponse response) {
                    Log.e(TAG, "SUCCESS, link removed " + response.json.toString());
                    //TODO update data to remove old data
                    updateData(removeItemFromData(position));
                }

                @Override
                public void onError(VKError error) {
                    Log.e(TAG, "ERROR, link not removed  " + error.toString());
                }
            });
            Snackbar.make(view, item.getTitle() + " " + getResources().getString(R.string.removed), Snackbar.LENGTH_LONG).setAction(getResources().getString(R.string.undo), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final VKRequest test = new VKRequest("fave.addLink");
                    test.addExtraParameter("link", item.getUrl());
                    test.executeWithListener(new VKRequest.VKRequestListener() {
                        @Override
                        public void onComplete(VKResponse response) {
                            Log.e(TAG, "SUCCESS, link added " + response.json.toString());
                            //TODO update data
                            updateData(restoreItemToData(item, position));
                            //updateData(list);

                        }

                        @Override
                        public void onError(VKError error) {
                        Log.e(TAG, "ERROR, link not added  " + error.toString());
                    }
                });


            }
        }).show();








    }

    private ArrayList<LinkBookmarks> removeItemFromData(int position){
        list.remove(position);
        return list;

    }

    private ArrayList<LinkBookmarks> restoreItemToData(LinkBookmarks data, int position ){
        list.add(position,data);
        return list;
    }

    private void updateData(ArrayList<LinkBookmarks> data){
        //adapter.clear();
        list = data;
        //adapter.addAll(list);
        adapter.notifyDataSetChanged();
    }

    private void getBookmarksList() {
        final VKRequest test = new VKRequest("fave.getLinks");
        test.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                Log.e(TAG, "SUCCESS, links loaded " + response.json.toString());
                list = LinkBookmarks.fromJson(response.json);
                updateData(list);
            }

            @Override
            public void onError(VKError error) {
                Log.e(TAG, "ERROR, links not loaded  " + error.toString());
            }
        });
    }
}
