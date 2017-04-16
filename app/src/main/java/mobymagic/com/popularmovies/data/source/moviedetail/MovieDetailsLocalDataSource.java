package mobymagic.com.popularmovies.data.source.moviedetail;

import android.content.Context;
import android.support.annotation.NonNull;

import mobymagic.com.popularmovies.data.remote.response.Movie;

public class MovieDetailsLocalDataSource implements MovieDetailsDataSourceContract.LocalDateSource {

    // region Constructors
    public MovieDetailsLocalDataSource(@NonNull Context context) {}

    // endregion

    // region MovieDetailsDataSourceContract.LocalDateSource Methods
    @Override
    public void getMovieDetails(int movieId,
                                @NonNull MovieDetailsDataSourceContract.MovieDataDataCallback movieDataDataCallback) {
        // TODO Fetch movie details from database
    }

    @Override
    public void saveMovieDetails(@NonNull Movie movie) {
        // TODO save movie details to database
    }
    // endregion
}
