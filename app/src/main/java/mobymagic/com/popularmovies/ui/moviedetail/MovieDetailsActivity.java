package mobymagic.com.popularmovies.ui.moviedetail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;

import butterknife.ButterKnife;
import mobymagic.com.popularmovies.R;
import mobymagic.com.popularmovies.data.remote.response.Movie;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String ARG_MOVIE = "ARG_MOVIE";

    // region Lifecycle Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

        showMovieDetailFragment();
    }
    // endregion

    private void showMovieDetailFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(android.R.id.content);

        if (fragment == null) {
            Movie movie = getIntent().getParcelableExtra(ARG_MOVIE);
            fragment = MovieDetailsFragment.newInstance(movie);

            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(android.R.id.content, fragment, "")
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .attach(fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
