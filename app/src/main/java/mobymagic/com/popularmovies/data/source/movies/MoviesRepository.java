package mobymagic.com.popularmovies.data.source.movies;

import android.support.annotation.NonNull;

public class MoviesRepository implements MoviesDataSourceContract.Repository {

    // region Constants
    private static final int PAGE_SIZE = 20;
    // endregion

    // region Member Variables
    private MoviesDataSourceContract.LocalDateSource mMoviesLocalDataSource;
    private MoviesDataSourceContract.RemoteDateSource mMoviesRemoteDataSource;
    // endregion

    // region Constructors
    public MoviesRepository(@NonNull MoviesDataSourceContract.LocalDateSource moviesLocalDataSource,
                            @NonNull MoviesDataSourceContract.RemoteDateSource moviesRemoteDataSource) {
        this.mMoviesLocalDataSource = moviesLocalDataSource;
        this.mMoviesRemoteDataSource = moviesRemoteDataSource;
    }
    // endregion

    // region MoviesDataSourceContract.Repository Methods
    @Override
    public void getPopularMovies(int currentPage,
                                 @NonNull MoviesDataSourceContract.MoviesDataCallback moviesDataCallback) {
        mMoviesRemoteDataSource.getPopularMovies(currentPage, moviesDataCallback);
    }

    @Override
    public void getTopRatedMovies(int currentPage,
                                  @NonNull MoviesDataSourceContract.MoviesDataCallback moviesDataCallback) {
        mMoviesRemoteDataSource.getTopRatedMovies(currentPage, moviesDataCallback);
    }
    // endregion
}
