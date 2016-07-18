package cl.zonamovil.tweetfocus.search.ui;

import java.util.List;

import cl.zonamovil.tweetfocus.entities.TweetSearch;

/**
 * Created by cgall on 17-07-2016.
 */
public interface SearchTweetView {

    void showImages();
    void hideImages();
    void showProgress();
    void hideProgress();
    void onError(String error);
    void setContent(List<TweetSearch> items);




}
