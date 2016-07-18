package cl.zonamovil.tweetfocus.search;

import android.util.Log;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.MediaEntity;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.params.Geocode;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TimelineCursor;
import com.twitter.sdk.android.tweetui.TimelineResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cl.zonamovil.tweetfocus.api.CustomTwitterApiClient;
import cl.zonamovil.tweetfocus.entities.SearchList;
import cl.zonamovil.tweetfocus.entities.TweetSearch;
import cl.zonamovil.tweetfocus.lib.base.EventBus;
import cl.zonamovil.tweetfocus.search.events.SearchTweetEvent;

/**
 * Created by cgall on 17-07-2016.
 */
public class SearchTweetRepositoryImpl implements SearchTweetRepository {

    private EventBus eventBus;
    private CustomTwitterApiClient client;
    private String query;
    private Geocode geocode;
    private String since;
    private int count;

    public enum ResultType {
        RECENT("recent"),
        POPULAR("popular"),
        MIXED("mixed"),
        FILTERED("filtered");

        final String type;

        ResultType(String type) {
            this.type = type;
        }
    }


    public SearchTweetRepositoryImpl(CustomTwitterApiClient client, EventBus eventBus) {
        this.client = client;
        this.eventBus = eventBus;

    }

    @Override
    public void getSearchTweet(int count ,Geocode geocode, String query, String since) {


        this.geocode = geocode;
        this.query = query;
        this.since = since;
        this.count=count;



       Callback<Search> callback=new Callback<Search>() {
           @Override
           public void success(Result<Search> result) {
               List<TweetSearch> items=new ArrayList<TweetSearch>();

               for (Tweet tweet:result.data.tweets) {

                   if(containsImages(tweet)) {

                       TweetSearch tweetSearch = new TweetSearch();

                       tweetSearch.setId(tweet.idStr);
                       tweetSearch.setFavoriteCount(tweet.favoriteCount);
                       String tweetText = tweet.text;
                       int index = tweetText.indexOf("http");
                       if (index > 0) {
                           tweetText = tweetText.substring(0, index);
                       }
                       tweetSearch.setTweetText(tweetText);

                       MediaEntity currentPhoto = tweet.entities.media.get(0);
                       String imageURL = currentPhoto.mediaUrl;
                       tweetSearch.setImageURL(imageURL);

                       items.add(tweetSearch);

                       Log.d("Search--: ",tweetSearch.getTweetText()+" "+tweetSearch.getFavoriteCount());

                   }


               }

               /*Collections.sort(items, new Comparator<TweetSearch>() {
                   public int compare(TweetSearch t1, TweetSearch t2) {
                       return t2.getFavoriteCount() - t1.getFavoriteCount();
                   }
               });*/

               post(items);

           }

           @Override
           public void failure(TwitterException exception) {
               post(exception.getLocalizedMessage());
           }
       };




        client.getSearchTweetsService().searchTweets(query,geocode,since,count,true, ResultType.RECENT.type,callback);


    }

    private boolean containsImages(Tweet tweet){
        return  tweet.entities!=null && tweet.entities.media!=null ;

    }

    private void  post( String error){

        post(null,error);

    }

    private void  post(List<TweetSearch> items) {

        post(items,null);
    }


    private void  post(List<TweetSearch> items , String error){

        SearchTweetEvent event=new SearchTweetEvent();
        event.setError(error);
        event.setTweetSearches(items);

        eventBus.post(event);


    }


}
