package cl.zonamovil.tweetfocus.search.di;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterSession;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import cl.zonamovil.tweetfocus.api.CustomTwitterApiClient;
import cl.zonamovil.tweetfocus.entities.TweetSearch;
import cl.zonamovil.tweetfocus.lib.base.EventBus;
import cl.zonamovil.tweetfocus.lib.base.ImageLoader;
import cl.zonamovil.tweetfocus.search.SearchTweetInteractor;
import cl.zonamovil.tweetfocus.search.SearchTweetInteractorImpl;
import cl.zonamovil.tweetfocus.search.SearchTweetPresenter;
import cl.zonamovil.tweetfocus.search.SearchTweetPresenterImpl;
import cl.zonamovil.tweetfocus.search.SearchTweetRepository;
import cl.zonamovil.tweetfocus.search.SearchTweetRepositoryImpl;
import cl.zonamovil.tweetfocus.search.adapters.OnItemClickListener;
import cl.zonamovil.tweetfocus.search.adapters.SearchTweetAdapter;
import cl.zonamovil.tweetfocus.search.ui.SearchTweetView;
import dagger.Module;
import dagger.Provides;

/**
 * Created by cgall on 17-07-2016.
 */
@Module
public class SearchTweetModule {

    private SearchTweetView searchTweetView;
    private OnItemClickListener clickListener;

    public SearchTweetModule(OnItemClickListener clickListener, SearchTweetView searchTweetView) {
        this.clickListener = clickListener;
        this.searchTweetView = searchTweetView;
    }

    @Provides
    @Singleton
    SearchTweetAdapter providesAdapter(OnItemClickListener clickListener, List<TweetSearch> dataset, ImageLoader imageLoader){

        return new SearchTweetAdapter(clickListener,dataset,imageLoader);


    }

    @Provides
    @Singleton
    OnItemClickListener providesOnItemClickListener(){

        return this.clickListener;
    }


    @Provides
    @Singleton
    List<TweetSearch> providesItemsList(){
        return new ArrayList<TweetSearch>();
    }

    @Provides
    @Singleton
    SearchTweetView providesSearchTweetView(){
        return this.searchTweetView;
    }

    @Provides
    @Singleton
    SearchTweetPresenter providesSearchTweetPresenter(EventBus eventBus, SearchTweetInteractor interactor, SearchTweetView view){

        return new SearchTweetPresenterImpl( eventBus,  interactor,  view);
    }

    @Provides
    @Singleton
    SearchTweetInteractor providesSearchTweetInteractor(SearchTweetRepository searchTweetRepository){

        return new SearchTweetInteractorImpl(searchTweetRepository);
    }

    @Provides
    @Singleton
    SearchTweetRepository providesSearchTweetRepository(CustomTwitterApiClient client,EventBus eventBus){

        return new SearchTweetRepositoryImpl(client,  eventBus);
    }

    @Provides
    @Singleton
    CustomTwitterApiClient providesTwitterApiClient(TwitterSession session) {
        return new CustomTwitterApiClient(session);
    }

    @Provides
    @Singleton
    TwitterSession providesTwitterSession() {
        return Twitter.getSessionManager().getActiveSession();
    }





}
