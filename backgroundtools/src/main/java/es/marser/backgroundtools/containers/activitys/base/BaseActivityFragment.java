package es.marser.backgroundtools.containers.activitys.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.fragments.base.BaseFragment;

/**
 * @author sergio
 *         Created by Sergio on 08/04/2017.
 */

public abstract class BaseActivityFragment extends BaseActivity {

    protected BaseFragment baseFragment;

    protected static String baseFragmentTag = "basePager";

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

    /**
     * Called to process touch screen events.  You can override this to
     * intercept all touch screen events before they are dispatched to the
     * window.  Be sure to call this implementation for touch screen events
     * that should be handled normally.
     *
     * @param ev The touch screen event.
     * @return boolean Return true if this event was consumed.
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
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
