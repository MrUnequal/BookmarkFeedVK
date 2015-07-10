package ua.southwall.vkbookmarksfeed.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vk.sdk.api.VKError;

import java.util.ArrayList;

import ua.southwall.vkbookmarksfeed.JsonModels.LinkBookmarks;
import ua.southwall.vkbookmarksfeed.JsonModels.UserInfo;
import ua.southwall.vkbookmarksfeed.JsonModels.UsersBookmarks;
import ua.southwall.vkbookmarksfeed.R;
import ua.southwall.vkbookmarksfeed.util.BlurTransformation;
import ua.southwall.vkbookmarksfeed.util.BookmarksPresenter;
import ua.southwall.vkbookmarksfeed.util.UserInfoPresenter;

public class BookmarksFragment extends Fragment {
    private static String TAG = "BookmarksFragment";
    TabLayout mTabLayout;
    ViewPager mViewPager;
    public static BookmarksPresenter presenter;
    private BookmarksLinksFragment fragmentLinks;
    private BookmarksUsersFragment fragmentUsers;
    private static ArrayList<UsersBookmarks> usersBookmarks;
    private static ArrayList<LinkBookmarks> linkBookmarks;

    public static BookmarksFragment newInstance(ArrayList<UsersBookmarks> usersBookmark, ArrayList<LinkBookmarks> linkBookmark) {
        usersBookmarks =usersBookmark;
        linkBookmarks =linkBookmark;
        return new BookmarksFragment();//TODO 3 oncreate() method is closer to ending point in lifecycle and onpause() to commit changes before getting out of view
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookmarks, container, false);
        mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        initViewPager();
        return view;
    }

    private void initViewPager() {
        mViewPager.setAdapter(new BookmarksPagerAdapter(getChildFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private  class BookmarksPagerAdapter extends FragmentPagerAdapter {
        private int NUM_ITEMS;
        String tabNames[];

        public BookmarksPagerAdapter(FragmentManager fm) {
            super(fm);
            tabNames = getResources().getStringArray(R.array.bookmarks_tabs_names);
            NUM_ITEMS = tabNames.length;
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 1:
                    fragmentLinks = BookmarksLinksFragment.newInstance(linkBookmarks);
                    return fragmentLinks;
                case 0:
                    fragmentUsers = BookmarksUsersFragment.newInstance(usersBookmarks);
                    return fragmentUsers;
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabNames[position];
        }

    }


}
