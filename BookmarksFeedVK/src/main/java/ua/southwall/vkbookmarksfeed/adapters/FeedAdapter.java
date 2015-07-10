package ua.southwall.vkbookmarksfeed.adapters;

/**
 * Created by mac on 5/19/15.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ua.southwall.vkbookmarksfeed.JsonModels.UsersBookmarks;
import ua.southwall.vkbookmarksfeed.JsonModels.WallPost;
import ua.southwall.vkbookmarksfeed.R;

/**
 * Created by mac on 2/23/15.
 */
import android.support.v7.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ContactViewHolder> {

    private static final String TAG = "FeedAdapter";
    private ArrayList<WallPost> wallPosts;
    private Context context;
    public void updateData(ArrayList<WallPost> wallPosts) {
        this.wallPosts = wallPosts;
    }

    public FeedAdapter(Context context) {//TODO dummy constructor
        this.context=context;

        return;
    }


    @Override
    public int getItemCount() {
        if (wallPosts==null)
            return 0;
        else
            return wallPosts.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        //TODO FIRST sort data
        WallPost ci = wallPosts.get(i);
        if(!(UsersBookmarks.getPhotoById(ci.getOwnerId())==null || UsersBookmarks.getPhotoById(ci.getOwnerId()).equals("")))
        Picasso.with(context).load(UsersBookmarks.getPhotoById(ci.getFromId())).into(contactViewHolder.photo);//TODO 2 works only if we went into manage bookmarks tab, so we need to download photo links, maybe not prematurly but by fact!!
        //Log.e(TAG,UsersBookmarks.getPhotoById(ci.getFromId()));
        contactViewHolder.name.setText(UsersBookmarks.getFullNameByid(ci.getFromId()));
        contactViewHolder.time.setText(ci.getDateString());
        contactViewHolder.content.setText(ci.getText());
        contactViewHolder.comments.setText(String.valueOf(ci.getCommentsCount()));
        contactViewHolder.shares.setText(String.valueOf(ci.getRepostCount()));
        contactViewHolder.likes.setText(String.valueOf(ci.getLikesCount()));

        //Log.e("Viewholder txtName", ci.getText());
        //Log.e("Viewholder vSurname", ci.getText());
        //contactViewHolder.vEmail.setText(ci.getText());
        //Log.e("Viewholder vEmail", ci.getText());
        //contactViewHolder.vTitle.setText(ci.getText());
        //Log.e("Viewholder vTitle", ci.getText());

    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_feed, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        protected ImageView photo;
        protected TextView name;
        protected TextView time;
        protected TextView content;
        protected TextView comments;
        protected TextView shares;
        protected TextView likes;

        public ContactViewHolder(View v) {
            super(v);
            photo =  (ImageView) v.findViewById(R.id.user_photo);
            name = (TextView)  v.findViewById(R.id.user_name);
            time = (TextView)  v.findViewById(R.id.time);
            //TODO 3dots button
            content = (TextView) v.findViewById(R.id.contentFragment);
            //TODO bottom buttons(comments likes shares)
            shares =  (TextView) v.findViewById(R.id.sharesCount);
            comments = (TextView)  v.findViewById(R.id.commentsCount);
            likes = (TextView)  v.findViewById(R.id.likesCount);

        }
    }
        //List<Object>
    //private
    //TODO first do sorting
    //TODO fisrt algorithm by bubble
    //maybe new array with current cursor same length as arraylist
    private ArrayList<WallPost> sortByTime(ArrayList<ArrayList<WallPost>> unsortedList){
        int listSize = unsortedList.size();
        int [] array = new int[listSize];
        ArrayList<WallPost> sortedList =  new ArrayList<WallPost>();
        //init
        sortedList.add(0, unsortedList.get(0).get(0));
        //DateComparator comparator = new DateComparator();
        for (int i =0; i<listSize;i++){
            unsortedList.get(i).get(i).getDate();
        }



        return null;

    }




}