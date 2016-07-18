package cl.zonamovil.tweetfocus.lib.di;

import android.support.v4.app.Fragment;


import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import javax.inject.Singleton;

import cl.zonamovil.tweetfocus.lib.GlideImageLoader;
import cl.zonamovil.tweetfocus.lib.GreenRobotEventBus;
import cl.zonamovil.tweetfocus.lib.base.EventBus;
import cl.zonamovil.tweetfocus.lib.base.ImageLoader;
import dagger.Module;
import dagger.Provides;

/**
 * Created by cgall on 17-07-2016.
 */
@Module
public class LibsModule {

    private Fragment fragment;

    public LibsModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @Singleton
    EventBus providesEventBus(org.greenrobot.eventbus.EventBus eventBus){

        return new GreenRobotEventBus(eventBus);

    }

    @Provides
    @Singleton
    org.greenrobot.eventbus.EventBus providesLibraryEventBus(){

        return org.greenrobot.eventbus.EventBus.getDefault();
    }


    @Provides
    @Singleton
    ImageLoader provideImageLoader(RequestManager requestManager) {
        return new GlideImageLoader(requestManager);
    }

    @Provides
    @Singleton
    RequestManager  providesRequestManager(Fragment fragment) {
        return  Glide.with(fragment);
    }

    @Provides
    @Singleton
    Fragment providesFragment(){

        return this.fragment;
    }


}
