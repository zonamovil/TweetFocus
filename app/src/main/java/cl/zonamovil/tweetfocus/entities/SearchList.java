package cl.zonamovil.tweetfocus.entities;

import com.google.gson.annotations.SerializedName;
import com.twitter.sdk.android.core.models.Tweet;

/**
 * Created by cgall on 17-07-2016.
 */
public class SearchList {

        @SerializedName("statuses")
        public Tweet[] tweets;

}
