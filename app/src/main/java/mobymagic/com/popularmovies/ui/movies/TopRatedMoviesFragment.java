package mobymagic.com.popularmovies.ui.movies;

public class TopRatedMoviesFragment extends BaseMoviesFragment {

    // region Factory Methods
    public static BaseMoviesFragment newInstance() {
        return new TopRatedMoviesFragment();
    }
    // endregion

    @Override
    protected void loadData() {
        mMoviesPresenter.onLoadTopRatedMovies(1);
    }

}
