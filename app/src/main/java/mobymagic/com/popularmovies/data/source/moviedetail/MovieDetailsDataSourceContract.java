package mobymagic.com.popularmovies.data.source.moviedetail;

import android.support.annotation.NonNull;

import mobymagic.com.popularmovies.data.remote.response.Movie;

public interface MovieDetailsDataSourceContract {

    interface Repository {
        void getMovieDetails(int movieId,
                             @NonNull final MovieDataDataCallback movieDataDataCallback);
    }

    interface LocalDateSource {
        void getMovieDetails(int movieId,
                             @NonNull final MovieDataDataCallback movieDataDataCallback);

        void saveMovieDetails(@NonNull Movie movie);
    }

    interface RemoteDateSource {
         void getMovieDetails(int movieId,
                              @NonNull final MovieDataDataCallback movieDataDataCallback);
    }

    interface MovieDataDataCallback {

        void onMovieDetailLoadStarted();
        void onMovieDetailLoadFailed();
        void onMovieDetailLoaded(@NonNull Movie movie);

    }
}
