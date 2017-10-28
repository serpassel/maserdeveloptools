package es.marser.backgroundtools.activitys.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sergio
 *         Created by Sergio on 12/09/2017.
 *         Adaptador de transición de fragmentos en una actividad
 *         <p>
 *         [EN]  Adapter of transition of fragments in an activity
 *         <p>
 *         <ul>
 *         <il>Constructors</il>
 *         </ul>
 */

@SuppressWarnings("unused")
public abstract class ScreenSlidePagerAdapter extends FragmentPagerAdapter {

    public Fragment fragment;
    protected List<Fragment> fragments;

    protected abstract String[] tabs();

    //CONSTRUCTORS_____________________________________________________________________________________
    public ScreenSlidePagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
    }

    public ScreenSlidePagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    /**
     * Agregar un fragmento
     * <p>
     * [EN]  Add a fragment
     *
     * @param fragment fragmento tipo {@link  android.support.v4.app.Fragment}
     *                 [EN]  fragment tipo {@link android.support.v4.app.Fragment}
     */
    public void addFragment(Fragment fragment) {
        fragments.add(fragment);
    }

    /**
     * Devuelve un fragmento según su posición en la lista
     * <p>
     * [EN]  Returns a fragment according to its position in the list
     *
     * @param position posición del fragmento a recuperar en la vista
     *                 [EN]  position of the fragment to be retrieved in the view
     * @return fragmento tipo {@link  android.support.v4.app.Fragment}
     * [EN]  fragment tipo {@link android.support.v4.app.Fragment}
     */
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    /**
     * Número de fragmentos en la lista
     * <p>
     * [EN]  Number of fragments in the list
     *
     * @return cantidad total de fragmentos en la lista [EN]  total number of fragments in the list
     */
    @Override
    public int getCount() {
        return fragments.size();
    }

    /**
     * Título de las tabs en caso de existir, para la posición indicada
     * <p>
     *   [EN]  Title of the tabs, if any, for the position indicated
     * @param position posición del título del fragment [EN]  fragment heading position
     * @return Título del fragment [EN]  Fragment title
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return tabs()[position];
    }
}

