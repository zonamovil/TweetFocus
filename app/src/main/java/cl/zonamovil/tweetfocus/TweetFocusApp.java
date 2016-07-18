package cl.zonamovil.tweetfocus;

import android.app.Application;
import android.support.v4.app.Fragment;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import cl.zonamovil.tweetfocus.lib.di.LibsModule;
import cl.zonamovil.tweetfocus.search.adapters.OnItemClickListener;
import cl.zonamovil.tweetfocus.search.di.DaggerSearchTweetComponent;
import cl.zonamovil.tweetfocus.search.di.SearchTweetComponent;
import cl.zonamovil.tweetfocus.search.di.SearchTweetModule;
import cl.zonamovil.tweetfocus.search.ui.SearchTweetView;
import io.fabric.sdk.android.Fabric;

/**
 * Created by cgall on 17-07-2016.
 */
public class TweetFocusApp extends Application {

    public SearchTweetComponent searchTweetComponent;

    public SearchTweetComponent getAppComponent() {
        return searchTweetComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        TwitterAuthConfig authConfig = new TwitterAuthConfig(BuildConfig.TWITTER_KEY,BuildConfig.TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

    }


    public SearchTweetComponent getSearchTweetComponent(Fragment fragment, SearchTweetView view, OnItemClickListener clickListener){




       searchTweetComponent= DaggerSearchTweetComponent.builder()
                .libsModule(new LibsModule(fragment))
                .searchTweetModule(new SearchTweetModule(clickListener,view)).build();

        return searchTweetComponent;

    }
}
