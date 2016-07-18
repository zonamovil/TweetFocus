package cl.zonamovil.tweetfocus;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import cl.zonamovil.tweetfocus.search.ui.SearchTweetFragment;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(BuildConfig.TWITTER_KEY, BuildConfig.TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        if (Twitter.getSessionManager().getActiveSession() != null) {
            seleccionarItem(navigationView.getMenu().findItem(R.id.nav_camera));


        }else{

            seleccionarItem(navigationView.getMenu().findItem(R.id.nav_login));
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        seleccionarItem(item);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void seleccionarItem(MenuItem item) {

        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        String Tag="";
        Bundle data = new Bundle();



        switch (item.getItemId()) {

            case R.id.nav_login:
                fragmentoGenerico =  new LoginFragment();
                Tag="0";
                break;

            case R.id.nav_camera:

                fragmentoGenerico =  new SearchTweetFragment();
                Tag="1";
                data.putString("base_query", SearchTweetFragment.SRT_TAG_ACCIDENTE);
                data.putBoolean("is_form", true);


                break;

            case R.id.nav_tw_timeline:

                fragmentoGenerico =  new TweetListFragment();
                Tag="2";

                break;

            case R.id.nav_gallery:

                fragmentoGenerico =  new SearchTweetFragment();
                Tag="3";
                data.putString("base_query", SearchTweetFragment.SRT_TAG_INCENDIO);
                data.putBoolean("is_form", false);


                break;

            case R.id.nav_slideshow:
                fragmentoGenerico =  new SearchTweetFragment();
                Tag="4";
                data.putString("base_query", SearchTweetFragment.SRT_TAG_EMERGENCIA);
                data.putBoolean("is_form", false);

                break;

            case R.id.nav_manage:

                fragmentoGenerico =  new SearchTweetFragment();
                Tag="5";
                data.putString("base_query", SearchTweetFragment.SRT_TAG_ALERTA);
                data.putBoolean("is_form", false);

                break;

        }


        if (fragmentoGenerico != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.content_frame, fragmentoGenerico,Tag)
                    .commit();
            fragmentoGenerico.setArguments(data);

            item.setChecked(true);
            getSupportActionBar().setTitle(item.getTitle());
            //setTitle(item.getTitle());
        }



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the fragment, which will then pass the result to the login
        // button.
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("0");
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }



}
