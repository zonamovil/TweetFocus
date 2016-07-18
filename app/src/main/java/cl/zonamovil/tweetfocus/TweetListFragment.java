package cl.zonamovil.tweetfocus;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.internal.TwitterApiConstants;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.TwitterListTimeline;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TweetListFragment extends Fragment {

    View rootView;
    @BindView(R.id.tweet_list)
    ListView tweetList;
//#cannonballapp AND pic.twitter.com AND " + "(#adventure OR #nature OR #romance OR #mystery
    private static final String SEARCH_QUERY = "#DesarrolloAPPSAndroid -filter:retweets filter:media  since:2016-07-10 ";

    public TweetListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_tweet_list, container,
                false);

        ButterKnife.bind(this, rootView);


        return rootView;
    }


    private void setUpPopularList() {
        SearchTask searchTask = new SearchTask();
        searchTask.execute((Void) null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpPopularList();
    }


    public class SearchTask extends AsyncTask<Void,Integer,Object[]>{


        @Override
        protected void onPostExecute(Object[] objects) {
            super.onPostExecute(objects);

            if(objects[0]!=null) {

                tweetList.setAdapter((TweetTimelineListAdapter)objects[0]);


            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            tweetList.setEmptyView(rootView.findViewById(R.id.list_progress));
        }

        @Override
        protected Object[] doInBackground(Void... params) {

            Object resp[] = { null, null };

            final SearchTimeline searchTimeline = new SearchTimeline.Builder().query(SEARCH_QUERY).resultType(SearchTimeline.ResultType.RECENT).maxItemsPerRequest(20).build();
            final TweetTimelineListAdapter timelineAdapter;
            timelineAdapter = new TweetTimelineListAdapter(getActivity(), searchTimeline);




            resp[0] =timelineAdapter;



            return resp;
        }
    }


}
