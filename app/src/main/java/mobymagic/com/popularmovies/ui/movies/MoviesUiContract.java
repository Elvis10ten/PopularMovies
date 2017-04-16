package mobymagic.com.popularmovies.ui.movies;

import android.support.annotation.NonNull;

import java.util.List;

import mobymagic.com.popularmovies.data.remote.response.Movie;
import mobymagic.com.popularmovies.ui.base.BasePresenter;

public interface MoviesUiContract {

    interface View {
        void showEmptyView();
        void hideEmptyView();

        void showErrorView();
        void hideErrorView();
        void setErrorText(String errorText);

        void showLoadingView();
        void hideLoadingView();

        void showMovies(@NonNull List<Movie> movies);

        // Navigation methods
        void openMovieDetails(@NonNull Movie movie);
    }

    interface Presenter extends BasePresenter {

        void onLoadPopularMovies(int currentPage);
        void onLoadTopRatedMovies(int currentPage);
        void onLoadFavoriteMovies(int currentPage);

        void onMovieClick(@NonNull Movie movie);

    }
}
