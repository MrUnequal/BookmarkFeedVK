package ua.southwall.vkbookmarksfeed.util;

import java.util.Collections;
import java.util.Comparator;

import ua.southwall.vkbookmarksfeed.JsonModels.WallPost;

/**
 * Created by mac on 6/6/15.
 */
public class DateComparator {


    public int compare(WallPost post1, WallPost post2) {
        //TODO if it's the last object in class - initiate new request to server
        //TODO it should enough t ot just minus the values
        return post1.getDate()-post2.getDate();
    }
}

