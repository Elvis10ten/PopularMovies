package mobymagic.com.popularmovies.ui.moviedetail;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import mobymagic.com.popularmovies.R;
import mobymagic.com.popularmovies.data.remote.response.Movie;
import mobymagic.com.popularmovies.utils.BlurUtils;
import timber.log.Timber;

public class MovieDetailsFragment extends Fragment {

    private static final String ARG_MOVIE = "ARG_MOVIE";
    @BindView(R.id.movie_iv)
    ImageView mMovieImageView;
    @BindView(R.id.backdrop_iv)
    ImageView mBackDropImageView;
    @BindView(R.id.movie_name_tv)
    TextView mNameTextView;
    @BindView(R.id.release_date_tv)
    TextView mReleaseDateTextView;
    @BindView(R.id.vote_average_tv)
    TextView mVoteAverageTextView;
    @BindView(R.id.toolbar_title_tv)
    TextView mToolbarTitleTextView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.overview_tv)
    TextView mOverviewTextView;
    @BindView(R.id.adult_tv)
    TextView mAdultTextView;
    @BindView(R.id.original_language_tv)
    TextView mOriginalLanguageTextView;
    @BindView(R.id.favorite_fab)
    FloatingActionButton mFavoriteFab;
    Unbinder unbinder;

    private Movie mMovie;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    // region Factory Methods
    public static MovieDetailsFragment newInstance(@NonNull Movie movie) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE, movie);
        fragment.setArguments(args);

        return fragment;
    }
    // endregion

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMovie = getArguments().getParcelable(ARG_MOVIE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mCollapsingToolbar.setStatusBarScrimColor(Color.TRANSPARENT);
        mCollapsingToolbar.setTitle(" ");

        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                mToolbarTitleTextView.setAlpha(Math.abs(verticalOffset / (float)
                        appBarLayout.getTotalScrollRange()));
            }
        });

        mToolbarTitleTextView.setText(mMovie.getTitle());
        mNameTextView.setText(mMovie.getTitle());

        mReleaseDateTextView.setText(mMovie.getReleaseDate());
        mAdultTextView.setText(String.valueOf(mMovie.isAdult()));
        mOriginalLanguageTextView.setText(mMovie.getOriginalLanguage());
        mOverviewTextView.setText(mMovie.getOverview());

        Timber.d("Json" + new Gson().toJson(mMovie));

        String voteAverage = String.format(Locale.US, "%.2f", mMovie.getVoteAverage());
        mVoteAverageTextView.setText(getString(R.string.vote_average_over_vote_count,
                voteAverage, "10"));

        Glide.with(this)
                .load(mMovie.getPosterUrl())
                .crossFade()
                .placeholder(R.drawable.default_thumbnail)
                .centerCrop()
                .into(mMovieImageView);

        Glide.with(this)
                .load(Movie.SECURE_BASE_URL + Movie.POSTER_SIZE + mMovie.getBackdropPath())
                .asBitmap()
                .placeholder(R.drawable.default_thumbnail)
                .centerCrop()
                .into(mBackDropTarget);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    // endregion

    @OnClick(R.id.favorite_fab)
    public void onFavoriteClicked() {
        Toast.makeText(getActivity(), "Favorite feature coming soon", Toast.LENGTH_LONG).show();
    }

    private SimpleTarget<Bitmap> mBackDropTarget = new SimpleTarget<Bitmap>() {

        @Override
        public void onResourceReady(final Bitmap resource, GlideAnimation glideAnimation) {
            BlurUtils.blurAsync(getActivity(), resource, BlurUtils.DEFAULT_BITMAP_SCALE,
                    BlurUtils.DEFAULT_BLUR_RADIUS, new BlurUtils.BlurTaskCallback() {

                        @Override
                        public boolean isActive() {
                            return isAdded();
                        }

                        @Override
                        public void onBlurError() {
                            mBackDropImageView.setImageBitmap(resource);
                        }

                        @Override
                        public void onBlurSuccess(@NonNull Bitmap bitmap) {
                            mBackDropImageView.setImageBitmap(bitmap);
                        }
                    });
        }

    };
}
