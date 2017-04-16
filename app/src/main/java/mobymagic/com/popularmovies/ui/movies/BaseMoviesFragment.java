package mobymagic.com.popularmovies.ui.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;
import mobymagic.com.popularmovies.R;
import mobymagic.com.popularmovies.data.remote.response.Movie;
import mobymagic.com.popularmovies.data.source.movies.MoviesLocalDataSource;
import mobymagic.com.popularmovies.data.source.movies.MoviesRemoteDataSource;
import mobymagic.com.popularmovies.data.source.movies.MoviesRepository;
import mobymagic.com.popularmovies.ui.base.BaseFragment;
import mobymagic.com.popularmovies.ui.moviedetail.MovieDetailsActivity;
import mobymagic.com.popularmovies.utils.DisplayUtils;
import mobymagic.com.popularmovies.views.ItemOffsetDecoration;
import timber.log.Timber;

public abstract class BaseMoviesFragment extends BaseFragment implements MoviesUiContract.View, MoviesAdapter.OnMovieClickListener {

    // region Views
    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.error_ll)
    LinearLayout mErrorLinearLayout;
    @BindView(R.id.error_tv)
    TextView mErrorTextView;
    @BindView(R.id.pb)
    ProgressBar mProgressBar;
    @BindView(android.R.id.empty)
    LinearLayout mEmptyLinearLayout;

    // region Member Variables
    private MoviesAdapter mMoviesAdapter;
    private Unbinder mUnbinder;
    protected MoviesUiContract.Presenter mMoviesPresenter;
    // endregion

    // region Constructors
    public BaseMoviesFragment() {}
    // endregion

    // region Lifecycle Methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMoviesPresenter = new MoviesPresenter(
                this,
                new MoviesRepository(
                        new MoviesLocalDataSource(getContext()),
                        new MoviesRemoteDataSource(getContext())
                )
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(getActivity(),
                R.dimen.grid_spacing_small);
        mRecyclerView.addItemDecoration(itemOffsetDecoration);

        mRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        mRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        Timber.d("Setting real grid span count");

                        gridLayoutManager.setSpanCount(DisplayUtils
                                .getGridSpanCount(mRecyclerView.getMeasuredWidth(),
                                        getResources().getDimensionPixelSize(R.dimen.regular_grid_width)));
                        gridLayoutManager.requestLayout();
                    }
                });

        mMoviesAdapter = new MoviesAdapter(getContext(), this);
        mRecyclerView.setItemAnimator(new ScaleInAnimator(new FastOutSlowInInterpolator()));
        mRecyclerView.setAdapter(mMoviesAdapter);

        loadData();
    }

    @Override
    public void onDestroyView() {
        mMoviesPresenter.onDestroyView();

        mUnbinder.unbind();
        super.onDestroyView();
    }
    // endregion

    protected abstract void loadData();

    // region MoviesUiContract.View Methods

    @Override
    public void showEmptyView() {
        mEmptyLinearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyView() {
        mEmptyLinearLayout.setVisibility(View.GONE);
    }

    @Override
    public void showErrorView() {
        mErrorLinearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideErrorView() {
        mErrorLinearLayout.setVisibility(View.GONE);
    }

    @Override
    public void setErrorText(String errorText) {
        mErrorTextView.setText(errorText);
    }

    @Override
    public void showLoadingView() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMovies(@NonNull List<Movie> movies) {
        mMoviesAdapter.addMovies(movies);
    }

    @Override
    public void openMovieDetails(@NonNull Movie movie) {

    }

    public void scrollToTop(){
        mRecyclerView.scrollToPosition(0);
    }
    // endregion

    @OnClick(R.id.retry_btn)
    void onRetryClicked() {
        loadData();
    }

    @Override
    public void onMovieClicked(int position, @NonNull Movie movie) {
        Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
        intent.putExtra(MovieDetailsActivity.ARG_MOVIE, movie);
        startActivity(intent);
    }
}
