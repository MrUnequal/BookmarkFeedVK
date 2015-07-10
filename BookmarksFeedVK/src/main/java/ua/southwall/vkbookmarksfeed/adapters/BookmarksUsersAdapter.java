package ua.southwall.vkbookmarksfeed.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ua.southwall.vkbookmarksfeed.JsonModels.UsersBookmarks;
import ua.southwall.vkbookmarksfeed.R;

/**
 * Created by mac on 5/16/15.
 */
public class BookmarksUsersAdapter extends ArrayAdapter<UsersBookmarks> {

    private ArrayList<UsersBookmarks> users;
    private static class ViewHolder {
        ImageView avatar;
        TextView userName;
        ImageView remove;
    }    public BookmarksUsersAdapter(Context context, ArrayList<UsersBookmarks> users) {
        super(context, 0, users);
        this.users = users;
    }
    public UsersBookmarks getItem(int position){
        return users.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UsersBookmarks user = users.get(position);
        ViewHolder viewHolder; // view lookup cache stored in tag
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_bookmarks_users, parent, false);
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.bookmarks_user_avatar);
            viewHolder.userName = (TextView) convertView.findViewById(R.id.bookmarks_user_name);
            viewHolder.remove = (ImageView) convertView.findViewById(R.id.icRemove);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.userName.setText(user.getFirstName() + " " + user.getLastName());
        viewHolder.avatar.setImageResource(R.drawable.ic_launcher);
        if (!(user.getAvatar()==null || user.getAvatar().equals(""))) {//TODO due to recylcling this block is broken, new elements appear
            Picasso.with(getContext()).cancelRequest(viewHolder.avatar);
            Picasso.with(getContext()).load(user.getAvatar()).into(viewHolder.avatar);//TODO we refresh all the view, what if we call new method and change only images
        }
        return convertView;
    }
}