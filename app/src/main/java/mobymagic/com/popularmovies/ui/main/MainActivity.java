package mobymagic.com.popularmovies.ui.main;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobymagic.com.popularmovies.R;
import mobymagic.com.popularmovies.ui.movies.BaseMoviesFragment;
import mobymagic.com.popularmovies.ui.movies.FavoriteMoviesFragment;
import mobymagic.com.popularmovies.ui.movies.PopularMoviesFragment;
import mobymagic.com.popularmovies.ui.movies.TopRatedMoviesFragment;

public class MainActivity extends AppCompatActivity {

    // region Views
    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigationView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    // endregion

    // region Lifecycle Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        showFirstFragment();
        setBottomNavListener();
    }
    // endregion

    private void showFirstFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_fl);
        if (fragment == null) {
            fragment = PopularMoviesFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.content_fl, fragment, null)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .attach(fragment)
                    .commit();
        }
    }

    private void setBottomNavListener() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        if(!item.isChecked()){
                            item.setChecked(true);
                            switch (item.getItemId()) {
                                case R.id.tab_popular_movies:
                                    getSupportFragmentManager()
                                            .beginTransaction()
                                            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                            .replace(R.id.content_fl, PopularMoviesFragment.newInstance(), null)
                                            .commit();
                                    break;
                                case R.id.tab_top_rated_movies:
                                    getSupportFragmentManager()
                                            .beginTransaction()
                                            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                            .replace(R.id.content_fl, TopRatedMoviesFragment.newInstance(), null)
                                            .commit();
                                    break;
                                case R.id.tab_favorite_movies:
                                    getSupportFragmentManager()
                                            .beginTransaction()
                                            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                            .replace(R.id.content_fl, FavoriteMoviesFragment.newInstance(), null)
                                            .commit();
                                    break;
                            }
                        }
                        else {
                            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_fl);

                            if(fragment instanceof BaseMoviesFragment){
                                ((BaseMoviesFragment)fragment).scrollToTop();
                            }
                        }
                        return false;
                    }
                });
    }
}
