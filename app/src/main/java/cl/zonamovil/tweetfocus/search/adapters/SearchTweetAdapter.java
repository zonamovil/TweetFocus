package cl.zonamovil.tweetfocus.search.adapters;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twitter.sdk.android.tweetui.CompactTweetView;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;
import com.twitter.sdk.android.tweetui.TweetViewFetchAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cl.zonamovil.tweetfocus.R;
import cl.zonamovil.tweetfocus.entities.TweetSearch;
import cl.zonamovil.tweetfocus.lib.base.ImageLoader;

/**
 * Created by cgall on 17-07-2016.
 */
public class SearchTweetAdapter extends RecyclerView.Adapter<SearchTweetAdapter.ViewHolder> {

    private List<TweetSearch> dataset;
    private ImageLoader imageLoader;
    private OnItemClickListener clickListener;

    public SearchTweetAdapter(OnItemClickListener clickListener, List<TweetSearch> dataset, ImageLoader imageLoader) {
        this.clickListener = clickListener;
        this.dataset = dataset;
        this.imageLoader = imageLoader;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {



        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.content_search,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TweetSearch tweetSearch=dataset.get(position);
        holder.setClickListener(tweetSearch,clickListener);
        holder.content_search_txt.setText(tweetSearch.getTweetText());
        imageLoader.load(holder.content_search_img,tweetSearch.getImageURL());


    }

    public void setItems(List<TweetSearch> items){

        dataset.addAll(items);
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount(){

        return dataset.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private View view;
        @BindView(R.id.content_search_img)
        ImageView content_search_img;

        @BindView(R.id.content_search_txt)
        TextView content_search_txt;




        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            this.view=itemView;
        }

        public void setClickListener(final TweetSearch tweet,
                                     final OnItemClickListener listener) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(tweet);
                }
            });

        }

    }
}
