package cl.zonamovil.tweetfocus.search.events;

import java.util.List;

import cl.zonamovil.tweetfocus.entities.TweetSearch;

/**
 * Created by cgall on 17-07-2016.
 */
public class SearchTweetEvent {

    private String error;
    private List<TweetSearch> tweetSearches;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<TweetSearch> getTweetSearches() {
        return tweetSearches;
    }

    public void setTweetSearches(List<TweetSearch> tweetSearches) {
        this.tweetSearches = tweetSearches;
    }
}
