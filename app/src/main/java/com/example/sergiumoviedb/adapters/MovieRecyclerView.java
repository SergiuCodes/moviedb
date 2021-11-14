package com.example.sergiumoviedb.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sergiumoviedb.R;
import com.example.sergiumoviedb.models.MovieModel;
import com.example.sergiumoviedb.utils.Credentials;

import java.util.List;

public class MovieRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MovieModel> mMovies;
    private OnMovieListener onMovieListener;

    private static final int DISPLAY_POP=1;
    private static final int DISPLAY_SEARCH=2;




    public MovieRecyclerView(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == DISPLAY_SEARCH) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);

            return new MovieViewHolder(view, onMovieListener);

        }
        else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_movies_layout, parent, false);

            return new Popular_View_Holder(view, onMovieListener);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        int itemViewType = getItemViewType(i);
        if (itemViewType == DISPLAY_SEARCH) {


            ((MovieViewHolder)holder).movie_title.setText(mMovies.get(i).getTitle());
            ((MovieViewHolder)holder).movie_releaseDate.setText(mMovies.get(i).getRelease_date());



            //Divided by 2 because moviedb uses 10 stars

            ((MovieViewHolder)holder).rating_bar.setRating((mMovies.get(i).getVote_average())/2);

            //Image glide

            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500/" +mMovies.get(i).getPoster_path())
                    .into((((MovieViewHolder)holder)
                            .movie_img));
        }else {

            //Divided by 2 because moviedb uses 10 stars

            ((Popular_View_Holder)holder).ratingBar2.setRating((mMovies.get(i).getVote_average())/2);

            //Image glide

            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500/" +mMovies.get(i).getPoster_path())
                    .into((((Popular_View_Holder)holder)
                            .imageView2));

        }



    }

    @Override
    public int getItemCount() {

        if(mMovies != null) {
            return mMovies.size();
        }
        return 0;
    }

    public void setmMovies(List<MovieModel> mMovies) {
        this.mMovies = mMovies;
        notifyDataSetChanged();
    }

    //Getting the id of the movie clicked to pass into moviedetails.java

    public MovieModel getSelectedMovie (int position) {
        if (mMovies != null) {
            if (mMovies.size() > 0) {
                return mMovies.get(position);
            }
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (Credentials.POPULAR) {
            return DISPLAY_POP;
        }
        else
            return DISPLAY_SEARCH;
    }
}
