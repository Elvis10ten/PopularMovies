package mobymagic.com.popularmovies.data.source.movies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import mobymagic.com.popularmovies.data.remote.AuthorizedNetworkInterceptor;
import mobymagic.com.popularmovies.data.remote.MoviesService;
import mobymagic.com.popularmovies.data.remote.RemoteCallback;
import mobymagic.com.popularmovies.data.remote.ServiceGenerator;
import mobymagic.com.popularmovies.data.remote.response.MoviesEnvelope;
import mobymagic.com.popularmovies.utils.ValidateUtils;
import timber.log.Timber;

public class MoviesRemoteDataSource implements MoviesDataSourceContract.RemoteDateSource {

    // region Member Variables
    private MoviesService mMovieService;
    // endregion

    // region Constructors
    public MoviesRemoteDataSource(@NonNull Context context) {
        mMovieService = ServiceGenerator.createService(
                MoviesService.class,
                MoviesService.BASE_URL,
                new AuthorizedNetworkInterceptor(context));
    }
    // endregion

    // region MoviesDataSourceContract.RemoteDateSource Methods
    @Override
    public void getPopularMovies(int currentPage,
                                 @NonNull final MoviesDataSourceContract.MoviesDataCallback moviesDataCallback) {
        moviesDataCallback.onMoviesLoadStarted();

        mMovieService.getPopularMovies(currentPage).enqueue(new RemoteCallback<MoviesEnvelope>() {

            @Override
            public void onSuccess(@Nullable MoviesEnvelope response) {
                Timber.d("Popular movies successfully fetched. Data %s", response);

                if(response == null) {
                    onFailed(new Throwable("Popular movies is null"));
                    return;
                }

                if(ValidateUtils.isEmpty(response.getMovies())) {
                    Timber.d("Popular movies is empty");
                    moviesDataCallback.onMoviesLoadEmpty();
                    return;
                }

                moviesDataCallback.onMoviesLoaded(response.getMovies());
            }

            @Override
            public void onFailed(int responseCode, @Nullable String message) {
                onFailed(new Throwable("Response code: " + responseCode + ", Message: " + message));
            }

            @Override
            public void onFailed(Throwable throwable) {
                Timber.e("Error fetching popular movies: %s", throwable);

                moviesDataCallback.onMoviesLoadFailed();
            }
        });
    }

    @Override
    public void getTopRatedMovies(int currentPage,
                                  @NonNull final MoviesDataSourceContract.MoviesDataCallback moviesDataCallback) {
        moviesDataCallback.onMoviesLoadStarted();

        mMovieService.getTopRatedMovies(currentPage).enqueue(new RemoteCallback<MoviesEnvelope>() {

            @Override
            public void onSuccess(@Nullable MoviesEnvelope response) {
                Timber.d("Top rated movies successfully fetched. Data %s", response);

                if(response == null) {
                    onFailed(new Throwable("Top rated movies is null"));
                    return;
                }

                if(ValidateUtils.isEmpty(response.getMovies())) {
                    Timber.d("Top rated movies is empty");
                    moviesDataCallback.onMoviesLoadEmpty();
                    return;
                }

                moviesDataCallback.onMoviesLoaded(response.getMovies());
            }

            @Override
            public void onFailed(int responseCode, @Nullable String message) {
                onFailed(new Throwable("Response code: " + responseCode + ", Message: " + message));
            }

            @Override
            public void onFailed(Throwable throwable) {
                Timber.e("Error fetching top rated movies: %s", throwable);

                moviesDataCallback.onMoviesLoadFailed();
            }
        });
    }
    // endregion

}
