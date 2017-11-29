package es.marser.backgroundtools.objectslistables.simple.adapter;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.handlers.TouchableViewHandler;
import es.marser.backgroundtools.handlers.ViewItemHandler;
import es.marser.backgroundtools.objectslistables.base.adapter.BaseListAdapter;
import es.marser.backgroundtools.objectslistables.base.controller.AdapterController;
import es.marser.backgroundtools.objectslistables.base.holder.ViewHolderType;
import es.marser.backgroundtools.objectslistables.base.model.ExpandItemsController;
import es.marser.backgroundtools.objectslistables.base.model.ExpandItemsManager;
import es.marser.backgroundtools.objectslistables.base.model.SelectionItemsController;
import es.marser.backgroundtools.objectslistables.base.model.SelectionItemsManager;
import es.marser.backgroundtools.objectslistables.simple.holder.ViewHolderBinding;
import es.marser.backgroundtools.objectslistables.base.model.AdapterItemsController;
import es.marser.backgroundtools.objectslistables.base.model.AdapterItemsManager;

/**
 * @author sergio
 *         Created by sergio on 1/11/17.
 */

@SuppressWarnings("unused")
public class SimpleListAdapter<T extends Parcelable>
        extends BaseListAdapter<T, ViewHolderBinding<T>>
        implements AdapterItemsManager<T>, SelectionItemsManager, ExpandItemsManager {

    private int holderLayout;
    private TouchableViewHandler<T> touchableViewHandler;
    /*Variables de control [EN]  Control variables*/
    private AdapterController<T> adapterController;

    //CONSTRUCTORS_______________________________________________________________________________
    public SimpleListAdapter() {
        this(R.layout.mvp_item_object_chooser);
    }

    public SimpleListAdapter(int holderLayout) {
        this.holderLayout = holderLayout;
        this.touchableViewHandler = null;

        adapterController = new AdapterController<>();
        adapterController.setChangedListener(this);
    }

    //OVERRIDE SUPERCLASS_____________________________________________________________________________
        /*Sobreescritura de  RecyclerView.Adapter [EN]  RecyclerView.Adapter Overwrite*/
    @Override
    public int getItemCount() {
        return adapterController.size();
    }


    //VARIABLE ACCESS___________________________________________________________________________________

    public int getHolderLayout() {
        return holderLayout;
    }

    public void setHolderLayout(int holderLayout) {
        this.holderLayout = holderLayout;
    }

    public void setTouchableViewHandler(TouchableViewHandler<T> touchableViewHandler) {
        this.touchableViewHandler = touchableViewHandler;
    }

    /**
     * Variable de oyente para pulsaciones de las vistas anidadas a la vista raiz del elemento
     * <p>
     * [EN]  Listener variable for clicking nested views to the root view of the item
     *
     * @return Variable de oyente de tipo {@link TouchableViewHandler}
     */
    public TouchableViewHandler<T> getTouchableViewHandler() {
        return touchableViewHandler;
    }

    public void removeTouchableViewHandler() {
        setTouchableViewHandler(null);
    }

    public void setViewItemHandler(ViewItemHandler<T> viewItemHandler) {
        if (adapterController != null) {
            adapterController.setViewItemHandler(viewItemHandler);
        }
    }

    public void removeViewItemHandler() {
        if (adapterController != null) {
            adapterController.removeViewItemHandler();
        }
    }

    public AdapterController<T> getAdapterController() {
        return adapterController;
    }

    public void setAdapterController(AdapterController<T> adapterController) {
        this.adapterController = adapterController;
    }

    //OVERRIDE SUPERCLASS______________________________________________________________________________
    @Override
    protected SparseIntArray sparseHolderLayout() {
        SparseIntArray intArray = new SparseIntArray();
        intArray.put(ViewHolderType.SIMPLE.ordinal(), holderLayout);
        return intArray;
    }

    @Override
    public void onBindVH(ViewHolderBinding<T> holder, int position) {
        /*Introducir la variable de modelo de datos [EN]  Enter the data model variable*/
        holder.bind(adapterController.getItemAt(position));
        /*Manejador de eventos de las subvistas  [EN]  Event manager of subviews*/
        holder.attachTouchableViewHandler(getTouchableViewHandler());
    }

    @Override
    public ViewHolderBinding<T> onCreateViewHolder(ViewDataBinding dataBinding, int viewType) {
        return new ViewHolderBinding<>(dataBinding, adapterController);
    }

    //SAVED AND RESTORE_____________________________________________________________

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
     */
    public void onSaveInstanceState(@Nullable Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        if (adapterController != null) {
            adapterController.onSaveInstanceState(savedInstanceState);
        }
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
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (adapterController != null) {
                adapterController.onRestoreInstanceState(savedInstanceState);
            }
        }

        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * @return devuelve el gestor de lectura y escritura asignado al manejador
     * <p>
     * [EN]  returns the read and write manager assigned to the handler
     */
    @Nullable
    @Override
    public AdapterItemsController<T> getAdapterItemsController() {
        return getAdapterController();
    }

    /**
     * @return Devuelve la controladora de selección [EN]  Returns the selection controller
     */
    @Nullable
    @Override
    public SelectionItemsController getSelectionItemsController() {
        return adapterController != null ? adapterController.getSelectionItemsController() : null;
    }

    /**
     * @return Devuelve el objeto de control de expansión de objetos [EN]  Returns the object expansion control object
     */
    @Nullable
    @Override
    public ExpandItemsController getExpandItemsController() {
        return adapterController != null ? adapterController.getExpandItemsController() : null;
    }
}
