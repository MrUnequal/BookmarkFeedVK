package ua.southwall.vkbookmarksfeed;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vk.sdk.VKUIHelper;
import com.vk.sdk.api.VKError;

import de.hdodenhof.circleimageview.CircleImageView;
import ua.southwall.vkbookmarksfeed.JsonModels.UserInfo;
import ua.southwall.vkbookmarksfeed.fragments.BookmarksFragment;
import ua.southwall.vkbookmarksfeed.fragments.BookmarksUsersFragment;
import ua.southwall.vkbookmarksfeed.fragments.FeedFragment;
import ua.southwall.vkbookmarksfeed.fragments.PostFragment;
import ua.southwall.vkbookmarksfeed.util.BlurTransformation;
import ua.southwall.vkbookmarksfeed.util.UserInfoPresenter;

public class MainActivity extends AppCompatActivity {


    public static UserInfoPresenter presenter;

    static final String TAG = "MainActivity";
    private ActionBarDrawerToggle dlDrawer;
    private DrawerLayout drawerLayout;
    public Context context;
    NavigationView navView;
    RelativeLayout header;
    CircleImageView avatar;
    ImageView background;

    TextView userName;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navView = (NavigationView) drawerLayout.findViewById(R.id.navigation);
        header = (RelativeLayout) navView.findViewById(R.id.header);
        avatar = (CircleImageView) header.findViewById(R.id.avatar);
        background = (ImageView) header.findViewById(R.id.avatarBackground);
        userName = (TextView) header.findViewById(R.id.userName);
        context = this;
        initInstances();//toolbar and drawer set up

        if(presenter==null)
            presenter = new UserInfoPresenter();
        presenter.onTakeView(this);

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        VKUIHelper.onDestroy(this);
        presenter.onTakeView(null);
        if (isFinishing())
            presenter = null;
    }


    public void onSuccess(UserInfo info) {
        userName.setText(info.getFullName());
        Picasso.with(context).load(info.getPhoto_200()).into(avatar);
        Picasso.with(context).load(info.getPhoto_200()).transform(new BlurTransformation()).into(background);
    }

    public void onError(VKError throwable) {
        Toast.makeText(this, throwable.toString(), Toast.LENGTH_LONG).show();
        Log.e(TAG, "Not loaded from presenter");

    }

    private void initInstances() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dlDrawer = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.hello_world, R.string.hello_world);
        drawerLayout.setDrawerListener(dlDrawer);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.navItemFeed:
                        startNewFragmentFromDrawer(new FeedFragment());
                        break;
                    case R.id.navItemFeedBookmarks:
                        startNewFragmentFromDrawer(new PostFragment());
                        break;
                    case R.id.navItemFeedUsers:
                        startNewFragmentFromDrawer(new PostFragment());
                        break;
                    case R.id.navItemManageBookmarks:
                        startNewFragmentFromDrawer(BookmarksFragment.newInstance(presenter.usersBookmarks, presenter.linkBookmarks));
                        break;
                }
                return false;
            }
        });
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void startNewFragmentFromDrawer(Fragment fragment) {
        drawerLayout.closeDrawer(Gravity.LEFT);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.container, fragment);
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        dlDrawer.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        dlDrawer.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            drawerLayout.openDrawer(Gravity.LEFT);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        VKUIHelper.onResume(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        VKUIHelper.onActivityResult(this, requestCode, resultCode, data);
    }

}

