package cl.zonamovil.tweetfocus.search;

import com.twitter.sdk.android.core.services.params.Geocode;

import cl.zonamovil.tweetfocus.search.events.SearchTweetEvent;

/**
 * Created by cgall on 17-07-2016.
 */
public interface SearchTweetPresenter {

    void onResumen();
    void onPause();
    void onDestroy();
    void getImageTweets(int count, Geocode geocode, String query, String since);
    void onEventMainThread(SearchTweetEvent event);



}
