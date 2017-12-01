package es.marser.backgroundtools.objectslistables.simple.adapter;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.SparseIntArray;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.handlers.TouchableViewHandler;
import es.marser.backgroundtools.handlers.ViewItemHandler;
import es.marser.backgroundtools.objectslistables.base.adapter.BaseListAdapter;
import es.marser.backgroundtools.objectslistables.base.controller.AdapterController;
import es.marser.backgroundtools.objectslistables.base.holder.ViewHolderType;
import es.marser.backgroundtools.objectslistables.base.model.AdapterItemsController;
import es.marser.backgroundtools.objectslistables.base.model.AdapterItemsManager;
import es.marser.backgroundtools.objectslistables.base.model.ExpandItemsController;
import es.marser.backgroundtools.objectslistables.base.model.ExpandItemsManager;
import es.marser.backgroundtools.objectslistables.base.model.SelectedsModel;
import es.marser.backgroundtools.objectslistables.base.model.SelectedsModelManager;
import es.marser.backgroundtools.objectslistables.base.model.SelectionItemsController;
import es.marser.backgroundtools.objectslistables.base.model.SelectionItemsManager;
import es.marser.backgroundtools.objectslistables.base.model.Selectionable;
import es.marser.backgroundtools.objectslistables.simple.holder.ViewHolderBinding;

/**
 * @author sergio
 *         Created by sergio on 1/11/17.
 */

@SuppressWarnings("unused")
public class SimpleListAdapter<T extends Parcelable>
        extends BaseListAdapter<T, ViewHolderBinding<T>>
        implements AdapterItemsManager<T>, SelectionItemsManager, ExpandItemsManager, SelectedsModelManager<T> {

    /*Variable vista de los elementos [EN]  Variable view of the elements*/
    private int holderLayout;

    /*Variables de Eventos de pulsación en vistas menores [EN]  Pulse events variables in child views*/
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

    //SAVED AND RESTORE____________________________________________________________
   @Override
    public void onSaveInstanceState(@Nullable Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        if (adapterController != null) {
            adapterController.onSaveInstanceState(savedInstanceState);
        }
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (adapterController != null) {
                adapterController.onRestoreInstanceState(savedInstanceState);
            }
        }

        super.onRestoreInstanceState(savedInstanceState);
    }

    //MANAGERS_______________________________________________________________________
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

    @Nullable
    @Override
    public SelectionItemsController getSelectionItemsController() {
        return adapterController != null ? adapterController.getSelectionItemsController() : null;
    }

    @Nullable
    @Override
    public ExpandItemsController getExpandItemsController() {
        return adapterController != null ? adapterController.getExpandItemsController() : null;
    }
    @Override
    @Nullable
    public SelectedsModel<T> getSelectedsModel() {
        return this.adapterController;
    }

    @Override
    @Nullable
    public Selectionable getSelectionable(@Nullable Class cls) {
        return this.adapterController;
    }
}
