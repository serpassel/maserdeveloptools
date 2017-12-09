package es.marser.backgroundtools.listables.simple.adapter;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.events.TouchableViewHandler;
import es.marser.backgroundtools.events.ViewItemHandler;
import es.marser.backgroundtools.listables.base.adapter.BaseListAdapter;
import es.marser.backgroundtools.listables.base.controller.AdapterController;
import es.marser.backgroundtools.listables.base.holder.ViewHolderType;
import es.marser.backgroundtools.listables.simple.model.AdapterItems;
import es.marser.backgroundtools.listables.base.model.AdapterItemsManager;
import es.marser.backgroundtools.listables.base.model.ExpandItemsController;
import es.marser.backgroundtools.listables.base.model.ExpandItemsManager;
import es.marser.backgroundtools.listables.base.model.SelectedsModel;
import es.marser.backgroundtools.listables.base.model.SelectedsModelManager;
import es.marser.backgroundtools.listables.base.model.SelectionItemsController;
import es.marser.backgroundtools.listables.base.model.SelectionItemsManager;
import es.marser.backgroundtools.listables.base.model.Selectionable;
import es.marser.backgroundtools.listables.base.model.SelectionableManager;
import es.marser.backgroundtools.listables.simple.holder.ViewHolderBinding;

/**
 * @author sergio
 *         Created by sergio on 1/11/17.
 */

@SuppressWarnings("unused")
public class SimpleListAdapter<T extends Parcelable>
        extends BaseListAdapter<ViewHolderBinding<T>>
        implements AdapterItemsManager<T>,
        SelectionItemsManager,
        ExpandItemsManager,
        SelectedsModelManager<T>,
        SelectionableManager {

    /*Variables de Eventos de pulsación en vistas menores [EN]  Pulse events variables in child views*/
    private TouchableViewHandler<T> touchableViewHandler;
    /*Variables de control [EN]  Control variables*/
    private AdapterController<T> adapterController;

    //CONSTRUCTORS_______________________________________________________________________________
    public SimpleListAdapter() {
        this(R.layout.mvp_item_object_chooser);
    }

    public SimpleListAdapter(int holderLayout) {
        setHolderLayout(ViewHolderType.SIMPLE, holderLayout);
        this.touchableViewHandler = null;

        adapterController = new AdapterController<>();
        adapterController.setChangedListener(this);
    }

    //VARIABLE ACCESS______________________________________________________________________________

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

    //OVERRIDE SUPERCLASS_________________________________________________________________________
    @Override
    public int getItemCount() {
        return adapterController.size();
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
    //   Log.i(LOG_TAG.TAG, "GUARDANDO  ADAPTADOR");
       if (adapterController != null) {
            adapterController.onSaveInstanceState(savedInstanceState);
        }
       super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
      //  Log.w(LOG_TAG.TAG, "RESTAURANDO  ADAPTADOR");
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
    public AdapterItems<T> getAdapterItemsController() {
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
    public Selectionable getSelectionable(@Nullable Integer viewType) {
        return this.adapterController;
    }

    /**
     * @param viewtype tipo de vista en el holder, sólo para listas multiples [EN]  type of view in the holder, only for multiple lists
     * @return Modo de selección de la lista [EN]  Selection mode of the list
     */
    @Nullable
    @Override
    public ListExtra getSelectionmode(@Nullable Integer viewtype) {
        return adapterController != null ? adapterController.getSelectionmode(null) : null;
    }

    /**
     * Filjar el modo de selección de la lista
     * <p>
     * [EN]  Filtering the mode selection mode of the list
     *
     * @param viewtype tipo de vista en el holder, sólo para listas multiples [EN]  type of view in the holder, only for multiple lists
     * @param selectionmode Modo de slección de la lista
     */
    @Override
    public void setSelectionmode(@Nullable Integer viewType, @NonNull ListExtra selectionmode) {
        if(adapterController != null){
            adapterController.setSelectionmode(null, selectionmode);
        }
    }
}
