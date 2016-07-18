package cl.zonamovil.tweetfocus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import cl.zonamovil.tweetfocus.search.ui.SearchTweetFragment;

/**
 * Created by cgall on 17-07-2016.
 */
public class LoginFragment extends Fragment {


    @BindView(R.id.twitterLoginButton)
    TwitterLoginButton twitterLoginButton;
    @BindView(R.id.loginContainer)
    RelativeLayout loginContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_main, container, false);
        ButterKnife.bind(this, rootView);

        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {


                navigateToMainScreen();
            }

            @Override
            public void failure(TwitterException exception) {
                String msgError = String.format(getString(R.string.login_error_message), exception.getMessage());
                Snackbar.make(loginContainer, msgError, Snackbar.LENGTH_SHORT).show();
            }
        });



        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

twitterLoginButton.onActivityResult(requestCode,resultCode,data);

    }




    public void navigateToMainScreen() {

        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction();

        SearchTweetFragment searchTweetFragment=new SearchTweetFragment();

        transaction.replace(R.id.content_frame, searchTweetFragment, "1");
        transaction.addToBackStack(searchTweetFragment.getTag());
        transaction.commit();

    }
}
