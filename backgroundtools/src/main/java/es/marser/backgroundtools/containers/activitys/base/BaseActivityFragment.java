package es.marser.backgroundtools.containers.activitys.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.fragments.base.BaseFragment;
import es.marser.backgroundtools.enums.EventsExtras;

/**
 * @author sergio
 *         Created by Sergio on 08/04/2017.
 */

public abstract class BaseActivityFragment extends BaseActivity {

    protected BaseFragment baseFragment;

    protected static String baseFragmentTag = "basePager";

    @Override
    public void onSwipe(EventsExtras eventsExtras) {
        baseFragment.onSwipe(eventsExtras);
        super.onSwipe(eventsExtras);
    }

    @Override
    public void onDoubleTap() {
        baseFragment.onDoubleTap();
        super.onDoubleTap();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityLayout());
    }

    @Override
    protected void instanceVariables(@Nullable Bundle savedInstanceState) {

        if (getSupportFragmentManager().findFragmentByTag(baseFragmentTag) == null) {

            if(baseFragment == null) {
                baseFragment = instanceFragment();
            }
            
            if (baseFragment != null) {
                if (!baseFragment.isAdded()) {
                    insertFragment(baseFragment, baseFragmentTag);
                }
            } else {
                baseFragment = (BaseFragment) getSupportFragmentManager()
                        .findFragmentByTag(getResources()
                                .getString(R.string.FRAGMENT_PAGER));
            }
        }
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.ac_frag;
    }

    @SuppressWarnings("SameReturnValue")
    protected BaseFragment instanceFragment() {
        return null;
    }

}
