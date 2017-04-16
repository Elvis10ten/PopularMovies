package mobymagic.com.popularmovies.data.source.moviedetail;

import android.support.annotation.NonNull;

public class MovieDetailsRepository implements MovieDetailsDataSourceContract.Repository {

    // region Member Variables
    private MovieDetailsDataSourceContract.LocalDateSource mMovieDetailsLocalDataSource;
    private MovieDetailsDataSourceContract.RemoteDateSource mMovieDetailsRemoteDataSource;
    // endregion

    // region Constructors
    public MovieDetailsRepository(@NonNull MovieDetailsDataSourceContract.LocalDateSource movieDetailsLocalDataSource,
                                  @NonNull MovieDetailsDataSourceContract.RemoteDateSource movieDetailsRemoteDataSource) {
        this.mMovieDetailsLocalDataSource = movieDetailsLocalDataSource;
        this.mMovieDetailsRemoteDataSource = movieDetailsRemoteDataSource;
    }
    // endregion

    // region MovieDetailsDataSourceContract.Repository Methods
    @Override
    public void getMovieDetails(int movieId, @NonNull MovieDetailsDataSourceContract.MovieDataDataCallback movieDataDataCallback) {
        mMovieDetailsRemoteDataSource.getMovieDetails(movieId, movieDataDataCallback);
    }
    // endregion
}
