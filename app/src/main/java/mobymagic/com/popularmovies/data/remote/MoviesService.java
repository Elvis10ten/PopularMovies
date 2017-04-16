package mobymagic.com.popularmovies.data.remote;

import mobymagic.com.popularmovies.data.remote.response.Movie;
import mobymagic.com.popularmovies.data.remote.response.MoviesEnvelope;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesService {

    String BASE_URL = "https://api.themoviedb.org/3/";

    @GET("movie/popular")
    Call<MoviesEnvelope> getPopularMovies(@Query("page") int page);

    @GET("movie/top_rated")
    Call<MoviesEnvelope> getTopRatedMovies(@Query("page") int page);

    @GET("movie/{movieId}")
    Call<Movie> getMovieDetails(@Path("movieId") long movieId);

    @GET("movie/{movieId}/similar")
    Call<MoviesEnvelope> getSimilarMovies(@Path("movieId") long movieId);

    @GET("search/movie")
    Call<MoviesEnvelope> searchMovies(@Query("query") String query, @Query("page") int page);

}
