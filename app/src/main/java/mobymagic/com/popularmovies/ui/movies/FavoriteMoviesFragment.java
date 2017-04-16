package mobymagic.com.popularmovies.ui.movies;

import android.widget.Toast;

public class FavoriteMoviesFragment extends BaseMoviesFragment {

    // region Factory Methods
    public static BaseMoviesFragment newInstance() {
        return new FavoriteMoviesFragment();
    }
    // endregion

    @Override
    protected void loadData() {
        mMoviesPresenter.onLoadFavoriteMovies(0);

        // TODO load favorites
        Toast.makeText(getActivity(), "Not implemented yet. Wait for stage 2.", Toast.LENGTH_LONG).show();
    }

}
