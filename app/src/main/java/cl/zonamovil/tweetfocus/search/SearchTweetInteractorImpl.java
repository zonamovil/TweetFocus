package cl.zonamovil.tweetfocus.search;

import com.twitter.sdk.android.core.services.params.Geocode;

/**
 * Created by cgall on 17-07-2016.
 */
public class SearchTweetInteractorImpl implements SearchTweetInteractor {

    private SearchTweetRepository searchTweetRepository;

    public SearchTweetInteractorImpl(SearchTweetRepository searchTweetRepository) {
        this.searchTweetRepository = searchTweetRepository;
    }

    @Override
    public void execute(int count, Geocode geocode, String query, String since) {

        searchTweetRepository.getSearchTweet(count,geocode, query, since);
    }
}
