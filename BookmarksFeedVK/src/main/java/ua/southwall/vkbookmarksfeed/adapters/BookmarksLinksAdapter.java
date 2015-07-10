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

import ua.southwall.vkbookmarksfeed.JsonModels.LinkBookmarks;
import ua.southwall.vkbookmarksfeed.R;

/**
 * Created by mac on 5/16/15.
 */
public class BookmarksLinksAdapter extends ArrayAdapter<LinkBookmarks> {

    private static final String TAG = "BookmarksLinksAdapter";
    private ArrayList<LinkBookmarks> links;
    private static class ViewHolder {
        ImageView avatar;
        TextView title;
        TextView linkType;
        ImageView remove;
    }    public BookmarksLinksAdapter(Context context, ArrayList<LinkBookmarks> links) {
        super(context, 0, links);
        this.links = links;
    }
    public LinkBookmarks getItem(int position){
        return links.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        LinkBookmarks link = links.get(position);
        ViewHolder viewHolder; // view lookup cache stored in tag

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_bookmarks_links, parent, false);
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.bookmarks_link_avatar);
            viewHolder.title = (TextView) convertView.findViewById(R.id.bookmarks_link_name);
            viewHolder.remove = (ImageView) convertView.findViewById(R.id.icRemoveLink);
            viewHolder.linkType = (TextView) convertView.findViewById(R.id.bookmarks_link_type);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(link.getTitle());
        viewHolder.linkType.setText(link.getDescription());
        viewHolder.avatar.setImageResource(R.drawable.ic_launcher);
        Picasso.with(getContext()).load(link.getPhoto_100()).into(viewHolder.avatar);//TODO we refresh all the view, what if we call new method and change only images
        //it's fine, nothing to load, instead
        // Populate the data into the template view using the data object
        // Return the completed view to render on screen
        return convertView;
    }
}