package mobymagic.com.popularmovies.ui.base;

import android.support.v4.app.Fragment;

import com.squareup.leakcanary.RefWatcher;

import mobymagic.com.popularmovies.MoviesApplication;

public abstract class BaseFragment extends Fragment {

    // region Lifecycle Methods
    @Override
    public void onDestroy() {
        super.onDestroy();

        RefWatcher refWatcher = MoviesApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
    // region

}