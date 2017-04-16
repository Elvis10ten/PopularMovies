package mobymagic.com.popularmovies.data.source.movies;

import android.support.annotation.NonNull;

import java.util.List;

import mobymagic.com.popularmovies.data.remote.response.Movie;

public interface MoviesDataSourceContract {

    interface Repository {

        //Restful VERB is the first part of method name GET , POST , DELETE, PUT
        void getPopularMovies(int currentPage,
                              @NonNull final MoviesDataSourceContract.MoviesDataCallback moviesDataCallback);
        void getTopRatedMovies(int currentPage,
                              @NonNull final MoviesDataSourceContract.MoviesDataCallback moviesDataCallback);

    }

    interface LocalDateSource {

        void getPopularMovies(int currentPage,
                              @NonNull final MoviesDataSourceContract.MoviesDataCallback moviesDataCallback);
        void getTopRatedMovies(int currentPage,
                               @NonNull final MoviesDataSourceContract.MoviesDataCallback moviesDataCallback);

        void savePopularMovies(List<Movie> popularMovies);
        void saveTopRatedMovies(List<Movie> topRatedMovies);

    }

    interface RemoteDateSource {

        void getPopularMovies(int currentPage,
                              @NonNull final MoviesDataSourceContract.MoviesDataCallback moviesDataCallback);
        void getTopRatedMovies(int currentPage,
                               @NonNull final MoviesDataSourceContract.MoviesDataCallback moviesDataCallback);

    }

    interface MoviesDataCallback {

        void onMoviesLoadStarted();
        void onMoviesLoadFailed();
        void onMoviesLoadEmpty();
        void onMoviesLoaded(@NonNull List<Movie> movies);

    }
}
