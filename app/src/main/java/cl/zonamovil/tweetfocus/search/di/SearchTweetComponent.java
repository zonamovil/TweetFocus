package cl.zonamovil.tweetfocus.search.di;

import javax.inject.Singleton;

import cl.zonamovil.tweetfocus.lib.di.LibsModule;
import cl.zonamovil.tweetfocus.search.SearchTweetPresenter;
import cl.zonamovil.tweetfocus.search.ui.SearchTweetFragment;
import dagger.Component;

/**
 * Created by cgall on 17-07-2016.
 */

@Singleton
@Component(modules = {LibsModule.class,SearchTweetModule.class})
public interface SearchTweetComponent {


    void inject(SearchTweetFragment fragment);
    SearchTweetPresenter getPresenter();






}
