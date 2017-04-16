package mobymagic.com.popularmovies.data.source.movies;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import mobymagic.com.popularmovies.data.remote.response.Movie;

public class MoviesLocalDataSource implements MoviesDataSourceContract.LocalDateSource {

    // region Constructors
    public MoviesLocalDataSource(@NonNull Context context) {}
    // endregion

    // region MoviesDataSourceContract.LocalDateSource Methods

    @Override
    public void getPopularMovies(int currentPage,
                                  @NonNull final MoviesDataSourceContract.MoviesDataCallback moviesDataCallback) {
        // TODO get popular movies from database
    }

    @Override
    public void getTopRatedMovies(int currentPage,
                             @NonNull final MoviesDataSourceContract.MoviesDataCallback moviesDataCallback) {
        // TODO get top rated movies from database
    }

    @Override
    public void savePopularMovies(List<Movie> movies) {
        // TODO Save the popular movies to database
    }

    @Override
    public void saveTopRatedMovies(List<Movie> topRatedMovies) {
        // TODO Save the top rated movies to database
    }

    // endregion
}