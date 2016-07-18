package cl.zonamovil.tweetfocus.api;

import android.content.Context;

import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;

/**
 * Created by cgall on 17-07-2016.
 */
public class CustomTwitterApiClient extends TwitterApiClient {


    /**
     * Must be instantiated after {@link TwitterCore} has been
     * initialized via {@link Fabric#with(Context, Kit[])}.
     *
     * @param session Session to be used to create the API calls.
     * @throws IllegalArgumentException if TwitterSession argument is null
     */
    public CustomTwitterApiClient(Session session) {
        super(session);
    }


    public SearchTweetsService getSearchTweetsService(){

        return getService(SearchTweetsService.class);
    }
}
