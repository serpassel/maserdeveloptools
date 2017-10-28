package es.marser.backgroundtools.activitys.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.fragments.base.BaseFragment;

/**
 * @author sergio
 *         Created by Sergio on 08/04/2017.
 */

public abstract class BaseActivityFragment extends BaseActivity {

    protected BaseFragment baseFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityLayout());
    }

    @Override
    protected void instaceVariables() {
        baseFragment = instanceFragment();
        if (baseFragment != null) {
            insertFragment(baseFragment);
        } else {
            baseFragment = (BaseFragment) getSupportFragmentManager()
                    .findFragmentByTag(getResources()
                            .getString(R.string.FRAGMENT_PAGER));
        }
}

    protected int getActivityLayout() {
        return R.layout.ac_frag;
    }

    @SuppressWarnings("SameReturnValue")
    protected BaseFragment instanceFragment() {
        return null;
    }

}