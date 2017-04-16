package mobymagic.com.popularmovies.ui.movies;

public class PopularMoviesFragment extends BaseMoviesFragment {

    // region Factory Methods
    public static BaseMoviesFragment newInstance() {
        return new PopularMoviesFragment();
    }
    // endregion

    @Override
    protected void loadData() {
        mMoviesPresenter.onLoadPopularMovies(1);
    }

}
