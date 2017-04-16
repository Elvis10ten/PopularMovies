package mobymagic.com.popularmovies.ui.movies;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import mobymagic.com.popularmovies.data.remote.response.Movie;
import mobymagic.com.popularmovies.data.source.movies.MoviesDataSourceContract;
import timber.log.Timber;

class MoviesPresenter implements MoviesUiContract.Presenter, MoviesDataSourceContract.MoviesDataCallback {

    // region Member Variables
    private @Nullable MoviesUiContract.View mMoviesView;
    private final MoviesDataSourceContract.Repository mMoviesRepository;
    // endregion

    // region Constructors
    MoviesPresenter(@NonNull MoviesUiContract.View moviesView,
                           @NonNull MoviesDataSourceContract.Repository moviesRepository) {
        mMoviesView = moviesView;
        mMoviesRepository = moviesRepository;
    }
    // endregion

    // region MoviesUiContract.Presenter Methods
    @Override
    public void onDestroyView() {
        mMoviesView = null;
    }

    @Override
    public void onLoadPopularMovies(final int currentPage) {
        if(mMoviesView == null) {
            Timber.d("MoviesView is not attached");
            return;
        }

        mMoviesView.hideEmptyView();
        mMoviesView.hideErrorView();
        mMoviesView.showLoadingView();

        mMoviesRepository.getPopularMovies(currentPage, this);
    }

    @Override
    public void onLoadTopRatedMovies(int currentPage) {
        if(mMoviesView == null) {
            Timber.d("MoviesView is not attached");
            return;
        }

        mMoviesView.hideEmptyView();
        mMoviesView.hideErrorView();
        mMoviesView.showLoadingView();

        mMoviesRepository.getTopRatedMovies(currentPage, this);
    }

    @Override
    public void onLoadFavoriteMovies(int currentPage) {
        // TODO implement favorite feature in stage 2
    }

    @Override
    public void onMovieClick(@NonNull Movie movie) {
        if(mMoviesView != null) {
            mMoviesView.openMovieDetails(movie);
        }
    }


    @Override
    public void onMoviesLoadStarted() {}

    @Override
    public void onMoviesLoadFailed() {
        if (mMoviesView != null) {
            mMoviesView.hideLoadingView();
            mMoviesView.setErrorText("Can't load data.\nCheck your network connection.");
            mMoviesView.showErrorView();
        }
    }

    @Override
    public void onMoviesLoadEmpty() {
        if (mMoviesView != null) {
            mMoviesView.hideLoadingView();
            mMoviesView.showEmptyView();
        }
    }

    @Override
    public void onMoviesLoaded(@NonNull List<Movie> movies) {
        if(mMoviesView != null) {
            mMoviesView.hideLoadingView();
            mMoviesView.showMovies(movies);
        }
    }
    // endregion

}
