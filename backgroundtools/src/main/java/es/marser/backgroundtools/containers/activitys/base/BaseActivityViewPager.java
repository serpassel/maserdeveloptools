package es.marser.backgroundtools.containers.activitys.base;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.activitys.adapters.ScreenSlidePagerAdapter;
import es.marser.backgroundtools.containers.activitys.animations.DepthPageTransformer;

/**
 * @author sergio
 *         Created by Sergio on 12/09/2017.
 *         Base de actividad con adaptador de páginas de fragments
 *         <p>
 *         [EN]  Activity base with fragments page adapter
 */

@SuppressWarnings("ALL")
public abstract class BaseActivityViewPager extends BaseActivity {
    protected ViewPager mPager;
    protected TabLayout tabLayout;

    @Override
    protected void instaceVariables() {
        mPager = findViewById(R.id.pager);
        ScreenSlidePagerAdapter mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager()) {
            @Override
            protected String[] tabs() {
                return tabsName();
            }
        };
        addFragments(mPagerAdapter);
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true, new DepthPageTransformer());

        tabLayout = findViewById(R.id.appbartabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(mPager);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.ac_view_pager;
    }

    protected void preInit() {
    }

    protected void postInit() {
    }

    protected abstract String[] tabsName();

    protected abstract void addFragments(ScreenSlidePagerAdapter mPagerAdapter);
}
