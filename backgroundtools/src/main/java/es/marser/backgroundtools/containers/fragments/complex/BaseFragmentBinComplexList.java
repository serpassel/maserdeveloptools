package es.marser.backgroundtools.containers.fragments.complex;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.fragments.base.BaseFragmentList;
import es.marser.backgroundtools.listables.complex.adapter.ComplexAdapter;
import es.marser.backgroundtools.listables.complex.models.ComplexAdapterModel;
import es.marser.backgroundtools.listables.complex.models.ExpandableGroup;
import es.marser.backgroundtools.listables.complex.presenter.ComplexListPresenter;

/**
 * @author sergio
 *         Created by Sergio on 28/04/2017.
 *         Base para listas expandibles con objetos enlazados
 *         <p>
 *         [EN]  Base for expandable lists with linked objects
 *         <p>
 *         <ul>
 *         <il>Variable start</il>
 *         <il>Abstract Methods of Configuration</il>
 *         <il>View event events</il>
 *         <il>Control of items</il>
 *         <il>Change listeners in fragments</il>
 *         </ul>
 */

@SuppressWarnings("unused")
public abstract class BaseFragmentBinComplexList<G extends ExpandableGroup<C>, C extends Parcelable>
        extends BaseFragmentList<ComplexListPresenter<G,C, ComplexAdapterModel<G,C>>> {

    protected ComplexAdapter<G, C> adapter;
    //SAVED AND RESTORE_________________________________________________________________________________

    /**
     * Called to ask the fragment to save its current dynamic state, so it
     * can later be reconstructed in a new instance of its process is
     * restarted.  If a new instance of the fragment later needs to be
     * created, the data you place in the Bundle here will be available
     * in the Bundle given to {@link #onCreate(Bundle)},
     * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}, and
     * {@link #onActivityCreated(Bundle)}.
     * <p>
     * <p>This corresponds to {@link Activity#onSaveInstanceState(Bundle)
     * Activity.onSaveInstanceState(Bundle)} and most of the discussion there
     * applies here as well.  Note however: <em>this method may be called
     * at any time before {@link #onDestroy()}</em>.  There are many situations
     * where a fragment may be mostly torn down (such as when placed on the
     * back stack with no UI showing), but its state will not be saved until
     * its owning activity actually needs to save its state.
     *
     * @param outState Bundle in which to place your saved state.
     */
    @Override
    public void onSaveInstanceState(@Nullable Bundle outState) {
        adapter.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    /**
     * Called when all saved state has been restored into the view hierarchy
     * of the fragment.  This can be used to do initialization based on saved
     * state that you are letting the view hierarchy track itself, such as
     * whether check box widgets are currently checked.  This is called
     * after {@link #onActivityCreated(Bundle)} and before
     * {@link #onStart()}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        adapter.onRestoreInstanceState(savedInstanceState);
    }

    //ABSTRACT METHODS OF CONFIGURATION_______________________________________________________________

    /*Vistas [EN] Views*/

    @Override
    protected int getFragmentLayout() {
        return R.layout.mvc_frag_simple_list;
    }
}
