package mobymagic.com.popularmovies.data.source.moviedetail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import mobymagic.com.popularmovies.data.remote.AuthorizedNetworkInterceptor;
import mobymagic.com.popularmovies.data.remote.MoviesService;
import mobymagic.com.popularmovies.data.remote.RemoteCallback;
import mobymagic.com.popularmovies.data.remote.ServiceGenerator;
import mobymagic.com.popularmovies.data.remote.response.Movie;
import timber.log.Timber;

public class MovieDetailsRemoteDataSource implements MovieDetailsDataSourceContract.RemoteDateSource {

    // region Member Variables
    private MoviesService mMovieService;
    // endregion

    // region Constructors
    public MovieDetailsRemoteDataSource(@NonNull Context context) {
        mMovieService = ServiceGenerator.createService(
                MoviesService.class,
                MoviesService.BASE_URL,
                new AuthorizedNetworkInterceptor(context));
    }
    // endregion

    // region MovieDetailsDataSourceContract.RemoteDateSource Methods
    @Override
    public void getMovieDetails(int movieId,
                                @NonNull final MovieDetailsDataSourceContract.MovieDataDataCallback movieDataDataCallback) {
        movieDataDataCallback.onMovieDetailLoadStarted();

        mMovieService.getMovieDetails(movieId).enqueue(new RemoteCallback<Movie>() {

            @Override
            public void onSuccess(@Nullable Movie movie) {
                Timber.d("MovieDetail successfully fetched. Data %s", movie);

                if(movie == null) {
                    onFailed(new Throwable("MovieDetail is null"));
                    return;
                }

                movieDataDataCallback.onMovieDetailLoaded(movie);
            }

            @Override
            public void onFailed(int responseCode, @Nullable String message) {
                onFailed(new Throwable("Response code: " + responseCode + ", Message: " + message));
            }

            @Override
            public void onFailed(Throwable throwable) {
                Timber.e("Error fetching MovieDetails: %s", throwable);

                movieDataDataCallback.onMovieDetailLoadFailed();
            }
        });
    }
    // endregion
}
