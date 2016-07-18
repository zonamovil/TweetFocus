package cl.zonamovil.tweetfocus.search.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.twitter.sdk.android.core.services.params.Geocode;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cl.zonamovil.tweetfocus.R;
import cl.zonamovil.tweetfocus.TweetFocusApp;
import cl.zonamovil.tweetfocus.entities.TweetSearch;
import cl.zonamovil.tweetfocus.search.SearchTweetPresenter;
import cl.zonamovil.tweetfocus.search.adapters.OnItemClickListener;
import cl.zonamovil.tweetfocus.search.adapters.SearchTweetAdapter;
import cl.zonamovil.tweetfocus.search.di.SearchTweetComponent;

/**
 * Created by cgall on 17-07-2016.
 */
public class SearchTweetFragment extends Fragment implements SearchTweetView, OnItemClickListener {


    @BindView(R.id.search_recycler)
    RecyclerView searchRecycler;
    @BindView(R.id.search_progress)
    ProgressBar searchProgress;

    @Inject
    SearchTweetPresenter searchTweetPresenter;
    @Inject
    SearchTweetAdapter searchTweetAdapter;
    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.search_input_tag)
    EditText searchInputTag;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.search_ly_form)
    RelativeLayout searchLyForm;


    String str_input_query;


    public static String SRT_TAG_ACCIDENTE = "colision OR emergencia -filter:retweets filter:media";
    public static String SRT_TAG_INCENDIO = "incendio OR #fuego OR #incendio OR #humo -filter:retweets filter:media";
    public static String SRT_TAG_EMERGENCIA = "sismo OR temblor  OR terremoto OR tsunami -filter:retweets filter:media";
    public static String SRT_TAG_ALERTA = "alerta OR preemergencia OR urgente -filter:retweets filter:media";

    final static int TWEET_COUNT = 20;
    private boolean is_form=false;


    public SearchTweetFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, rootView);
        setupInjection();
        setupRecyclerView();
        //accidente OR #fuego OR #incendio OR #humo -RT -filter:retweets filter:media
        //searchInputTag.setText("#peligro OR #emergencia OR (#alerta OR #urgente OR #accidente OR #incendio)");

        return rootView;


    }


    private void setupInjection() {

        TweetFocusApp tweetFocusApp = (TweetFocusApp) getActivity().getApplication();
        SearchTweetComponent searchTweetComponent = tweetFocusApp.getSearchTweetComponent(this, this, this);

        searchTweetComponent.inject(this);

    }

    private void setupRecyclerView() {
        searchRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        searchRecycler.setAdapter(searchTweetAdapter);

    }


    @Override
    public void showImages() {

        searchRecycler.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideImages() {

        searchRecycler.setVisibility(View.GONE);

    }

    @Override
    public void showProgress() {

        searchProgress.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        searchProgress.setVisibility(View.GONE);
    }

    @Override
    public void onError(String error) {
        Snackbar.make(container, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setContent(List<TweetSearch> items) {
        searchTweetAdapter.setItems(items);

    }

    @Override
    public void onItemClick(TweetSearch tweetSearch) {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tweetSearch.getBaseTweetUrl()));
        startActivity(intent);

    }

    @Override
    public void onResume() {
        searchTweetPresenter.onResumen();
        super.onResume();

    }

    @Override
    public void onPause() {
        searchTweetPresenter.onPause();
        super.onPause();

    }

    @Override
    public void onDestroy() {
        searchTweetPresenter.onDestroy();
        super.onDestroy();

    }

    @OnClick(R.id.btnSubmit)
    public void onClick() {

        str_input_query = searchInputTag.getText().toString();

        if (!str_input_query.isEmpty()) {

            if (str_input_query.length() < 4) {

                Snackbar.make(container, getString(R.string.search_msg_input_short), Snackbar.LENGTH_SHORT).show();

            } else {

                hideKeyboard();
                str_input_query=searchInputTag.getText().toString()+" -filter:retweets filter:media";
                loadTweets();

            }


        } else {

            Snackbar.make(container, getString(R.string.search_msg_empty), Snackbar.LENGTH_SHORT).show();

        }


    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        is_form= getArguments().getBoolean("is_form");


        if(is_form){

            searchLyForm.setVisibility(View.VISIBLE);
            str_input_query ="";
            searchInputTag.setText(str_input_query);

        }else{

            str_input_query = getArguments().getString("base_query");
            loadTweets();

        }


    }

    /**
     * Se cargan los tweet en base a la consulta pasada como parametro en el Fragment
     */
    void loadTweets(){

        //TODO: rescatar location
        Geocode geocode = new Geocode(-33.45, -70.6667, 100, Geocode.Distance.KILOMETERS);

        /**
         * Para rescatar los tweet recientes . fecha actual
         */
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        String fch_since = df1.format(c.getTime());


        searchTweetPresenter.getImageTweets(TWEET_COUNT, geocode, str_input_query, fch_since);
    }


    private void hideKeyboard() {

        InputMethodManager inputManager=(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        try{

            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            Log.e(getActivity().getLocalClassName(),"Click en submit");


        }catch(NullPointerException npe){

            Log.e(getActivity().getLocalClassName(),Log.getStackTraceString(npe));

        }
    }



}
