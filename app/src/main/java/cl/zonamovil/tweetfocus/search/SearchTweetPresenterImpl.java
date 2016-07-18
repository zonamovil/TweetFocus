package cl.zonamovil.tweetfocus.search;

import com.twitter.sdk.android.core.services.params.Geocode;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import cl.zonamovil.tweetfocus.entities.TweetSearch;
import cl.zonamovil.tweetfocus.lib.base.EventBus;
import cl.zonamovil.tweetfocus.search.events.SearchTweetEvent;
import cl.zonamovil.tweetfocus.search.ui.SearchTweetView;

/**
 * Created by cgall on 17-07-2016.
 */
public class SearchTweetPresenterImpl implements  SearchTweetPresenter{

    private EventBus eventBus;
    private SearchTweetView view;
    private SearchTweetInteractor interactor;


    public SearchTweetPresenterImpl(EventBus eventBus, SearchTweetInteractor interactor, SearchTweetView view) {
        this.eventBus = eventBus;
        this.interactor = interactor;
        this.view = view;
    }

    @Override
    public void onResumen() {

        eventBus.register(this);

    }

    @Override
    public void onPause() {

        eventBus.unregister(this);

    }

    @Override
    public void onDestroy() {

        view=null;

    }

    @Override
    public void getImageTweets(int count, Geocode geocode, String query, String since) {

        if(view!=null)
        {
            view.hideImages();
            view.showProgress();
        }

        interactor.execute(count,geocode,  query,  since);


    }

    @Override
    @Subscribe
    public void onEventMainThread(SearchTweetEvent event) {

        String errorMsj=event.getError();
        if(view!=null){

            view.showImages();
            view.hideProgress();

            if(errorMsj!=null){
                this.view.onError(errorMsj);

            }else{

                List<TweetSearch> items =event.getTweetSearches();

                if(items!=null && !items.isEmpty()){

                    this.view.setContent(items);

                }
            }

        }


    }
}
