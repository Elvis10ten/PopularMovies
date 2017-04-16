package mobymagic.com.popularmovies.ui.movies;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.ImageViewTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobymagic.com.popularmovies.R;
import mobymagic.com.popularmovies.data.remote.response.Movie;
import mobymagic.com.popularmovies.glide.PaletteBitmap;
import mobymagic.com.popularmovies.glide.PaletteBitmapTranscoder;

class MoviesAdapter  extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private List<Movie> mMovies = new ArrayList<>();
    private BitmapRequestBuilder<String, PaletteBitmap> mGlideRequest;

    private OnMovieClickListener mMovieClickListener;

    MoviesAdapter(@NonNull Context context, @NonNull OnMovieClickListener movieClickListener) {
        mGlideRequest = Glide.with(context)
                .fromString()
                .asBitmap()
                .transcode(new PaletteBitmapTranscoder(context), PaletteBitmap.class)
                .fitCenter()
                .placeholder(R.drawable.default_thumbnail)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        mMovieClickListener = movieClickListener;
    }

    void addMovies(@NonNull List<Movie> movies) {
        mMovies.addAll(movies);
        notifyItemRangeInserted(0, mMovies.size()-1);
    }

    void clear() {
        mMovies.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie smartCondition = mMovies.get(position);
        Context context = holder.itemView.getContext();

        holder.bind(context, smartCondition);
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        holder.unbind();
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_iv)
        ImageView movieImageView;
        @BindView(R.id.movie_name_tv)
        TextView movieNameTextView;
        @BindView(R.id.vote_tv)
        TextView voteTextView;
        @BindView(R.id.movie_text_ll)
        View textsContainerView;

        ViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }

        void bind(@NonNull Context context, @NonNull final Movie movie) {
            movieNameTextView.setText(movie.getTitle());

            String voteAverage = String.format(Locale.US, "%.2f", movie.getVoteAverage());
            voteTextView.setText(context.getString(R.string.msg_average_vote, voteAverage));

            textsContainerView.setBackgroundColor(Color.DKGRAY);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMovieClickListener.onMovieClicked(getAdapterPosition(), movie);
                }
            });

            mGlideRequest
                    .load(movie.getPosterUrl())
                    .into(new ImageViewTarget<PaletteBitmap>(movieImageView) {

                        @Override protected void setResource(PaletteBitmap resource) {
                            super.view.setImageBitmap(resource.bitmap);

                            Palette.Swatch swatch = resource.palette.getVibrantSwatch();

                            //If there is no vibrant swatch, try getting dominant swatch
                            if(swatch == null) {
                                swatch = resource.palette.getDominantSwatch();
                            }

                            // If there is no dominant swatch try getting muted swatch
                            if(swatch == null) {
                                swatch = resource.palette.getMutedSwatch();
                            }

                            if(swatch == null) {
                                return;
                            }

                            textsContainerView.setBackgroundColor(swatch.getRgb());
                            movieNameTextView.setTextColor(swatch.getBodyTextColor());
                            voteTextView.setTextColor(swatch.getTitleTextColor());
                        }

                    });
        }

        void unbind() {
            // optional, but recommended way to clear up the resources used by Glide
            movieNameTextView.setText(null);
            voteTextView.setText(null);

            Glide.clear(movieImageView);
        }

    }

    interface OnMovieClickListener {

        void onMovieClicked(int position, @NonNull Movie movie);

    }
}
