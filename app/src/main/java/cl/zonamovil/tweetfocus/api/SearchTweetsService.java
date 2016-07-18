package cl.zonamovil.tweetfocus.api;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.params.Geocode;

import java.util.List;

import retrofit.http.EncodedQuery;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by cgall on 17-07-2016.
 */
public interface SearchTweetsService {

@GET("/1.1/search/tweets.json")
    void searchTweets (@Query("q") String q,
                       @EncodedQuery("geocode") Geocode geocode,
                       @Query("since") String since,
                       @Query("count") Integer count,
                       @Query("include_entities") Boolean include_entities,
                       @Query("result_type") String resultType,
                       Callback<Search> callback);



}
