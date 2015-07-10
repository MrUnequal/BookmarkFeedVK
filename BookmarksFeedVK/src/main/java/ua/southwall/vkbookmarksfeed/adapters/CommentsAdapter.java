package ua.southwall.vkbookmarksfeed.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;

import ua.southwall.vkbookmarksfeed.JsonModels.Comment;
import ua.southwall.vkbookmarksfeed.JsonModels.Comments;
import ua.southwall.vkbookmarksfeed.JsonModels.UsersBookmarks;
import ua.southwall.vkbookmarksfeed.JsonModels.WallPost;
import ua.southwall.vkbookmarksfeed.R;
import ua.southwall.vkbookmarksfeed.util.UnixTimeConverter;

/**
 * Created by mac on 7/10/15.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {

    private static String TAG = "CommentsAdapter";
    private Comments comments;//TODO try to get rid of
    private Context context;

    public void updateData(Comments comments) {
        this.comments = comments;
    }

    public static class CommentsViewHolder extends RecyclerView.ViewHolder{
        protected ImageView avatar;
        protected TextView name;
        protected TextView text;
        protected TextView date;


        public CommentsViewHolder(View v) {
            super(v);
            avatar = (ImageView) v.findViewById(R.id.comments_avatar);
            name = (TextView) v.findViewById(R.id.comments_name);
            text = (TextView) v.findViewById(R.id.comments_text);
            date = (TextView) v.findViewById(R.id.comments_date);

        }
    }


    @Override
    public CommentsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_comments, viewGroup, false);

        return new CommentsViewHolder(itemView);
    }


    public CommentsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemCount() {
        if (comments==null)
            return 0;
        else
            return comments.getComments().size();
    }



    @Override
    public void onBindViewHolder(CommentsViewHolder viewHolder, int i) {
        //TODO FIRST sort data
        Comment comment = comments.getComments().get(i);
        //viewHolder.name.setText(comments.getFullNameById(comment.getFromId()));
        viewHolder.text.setText(comment.getText());
        viewHolder.date.setText(UnixTimeConverter.ConvertUnixToDate(comment.getDate(), context));
        viewHolder.avatar.setImageResource(R.drawable.ic_launcher);//TODO placeholder with picasso
        String avatar = comments.getPhotoById(comment.getId());
        if (!(avatar==null || avatar.equals(""))) {//TODO due to recylcling this block is broken, new elements appear
            Picasso.with(context).cancelRequest(viewHolder.avatar);
            Picasso.with(context).load(avatar).into(viewHolder.avatar);//TODO we refresh all the view, what if we call new method and change only images
        }

    }


}
