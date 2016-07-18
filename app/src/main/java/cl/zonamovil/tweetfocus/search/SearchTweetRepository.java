package cl.zonamovil.tweetfocus.search;

import com.twitter.sdk.android.core.services.params.Geocode;

/**
 * Created by cgall on 17-07-2016.
 */
public interface SearchTweetRepository {

    void getSearchTweet(int count, Geocode geocode, String query, String since);
}
