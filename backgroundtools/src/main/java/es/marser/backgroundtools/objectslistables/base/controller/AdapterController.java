package es.marser.backgroundtools.objectslistables.base.controller;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.ViewItemHandler;
import es.marser.backgroundtools.objectslistables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.objectslistables.base.holder.ViewHolderType;
import es.marser.backgroundtools.objectslistables.base.listeners.AdapterNotifier;
import es.marser.backgroundtools.objectslistables.base.listeners.OnItemChangedListener;
import es.marser.backgroundtools.objectslistables.base.model.AdapterItemsController;
import es.marser.backgroundtools.objectslistables.base.model.ExpandItemsController;
import es.marser.backgroundtools.objectslistables.base.model.ExpandItemsManager;
import es.marser.backgroundtools.objectslistables.base.model.SelectedsModel;
import es.marser.backgroundtools.objectslistables.base.model.SelectionItemsController;
import es.marser.backgroundtools.objectslistables.base.model.SelectionItemsManager;
import es.marser.backgroundtools.objectslistables.base.model.Selectionable;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 1/11/17.
 *         Controlodador global de la lista
 *         <p>
 *         [EN]  Global controller from the list
 */

@SuppressWarnings("unused")
public class AdapterController<T extends Parcelable>
        implements ViewHolderController<T>,
        OnItemChangedListener,
        AdapterItemsController<T>,
        SelectedsModel<T>,
        SelectionItemsManager,
        ExpandItemsManager,
        Selectionable {


    /*Variables de control [EN]  Control variables*/
    protected SelectionController selectionController;
    public ExpandController expandController;

    /*Registro de elementos [EN]  Registration of elements*/
    protected ArrayList<T> items;

    /*Oyente de cambios en la lista [EN]  Listener of changes in the list*/
    protected AdapterNotifier adapterNotifier;

    /*Variable de modo de selección [EN]  Selection Mode Variable*/
    protected ListExtra selectionmode;

    /*Variable de captación de eventos de pulsación [EN]  Pulse Pickup Variable*/
    protected ViewItemHandler<T> viewItemHandler;

    /*EXTRAS*/
    public static String[] extras = new String[]{"items_key", "items_count_key"};

    public int viewHolderType;

    //CONTRUCTORS______________________________________________________________________________________
    public AdapterController() {
        this(ViewHolderType.SIMPLE.ordinal());
    }

    public AdapterController(int viewHolderType) {
        this.viewHolderType = viewHolderType;

         /*Nueva instancia de controladores [EN]  New controller instance*/
        items = new ArrayList<>();
        selectionController = new SelectionController();
        expandController = new ExpandController();

     /*Definición de oyentes a los controladores [EN]  Definition of listeners to the controllers*/
        selectionController.setOnSelectionChanged(this);
        expandController.setOnSelectionChanged(this);

        /*Oyentes [EN]  Listeners*/
        viewItemHandler = null;
        adapterNotifier = null;

        /*Variable de modo de selección [EN]  Variable of selection mode*/
        selectionmode = ListExtra.SINGLE_SELECTION_MODE;
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
        //Selection
        if (selectionController != null) {
            selectionController.onSaveInstanceState(savedInstanceState, String.valueOf(viewHolderType));
            //  selectionController.removedOnSelectionChanged();
        }

        //Expanded
        expandController.onSaveInstanceState(savedInstanceState, String.valueOf(viewHolderType));

        //ArrayList
        if (savedInstanceState != null) {
            savedInstanceState.putInt(TextTools.nc(viewHolderType) + extras[1], size());

            if (items != null && size() > 0) {
                savedInstanceState.putParcelableArrayList(TextTools.nc(viewHolderType) + extras[0], items);
            }
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
    @SuppressWarnings("unchecked")
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        //Selection
        if (selectionController != null) {
            selectionController.onRestoreInstanceState(savedInstanceState, String.valueOf(viewHolderType));
        }

        //Expanded
        if (expandController != null) {
            expandController.onRestoreInstanceState(savedInstanceState, String.valueOf(viewHolderType));
        }

        //ArrayList
        if (savedInstanceState != null
                && items != null) {

            try {
                items.addAll(savedInstanceState.getParcelableArrayList(TextTools.nc(viewHolderType) + extras[0]) != null
                        ? (ArrayList<T>) savedInstanceState.getParcelableArrayList(TextTools.nc(viewHolderType) + extras[0])
                        : new ArrayList<T>()
                );
            } catch (ClassCastException ignored) {
            }
        }
    }

    //CONTROLLERS MANAGER_______________________________________________________________________

    /**
     * Limpia los controladores
     * <p>
     * [EN]  Clean the drivers
     */
    private void clearControllers() {
        clearSelected();
        clearExpanded();
    }

    /**
     * Limpia la lista de expansión
     * <p>
     * [EN]  Clean the expansion list
     */
    private void clearExpanded() {
        if (expandController != null) {
            expandController.clear();
        }
    }

    /**
     * Limpia los valores de selección
     * <p>
     * [EN]  Clean the selection values
     */
    private void clearSelected() {
        if (selectionController != null) {
            selectionController.clear();
        }
    }

    //ITEMS CONTROLLER_____________________________________________________________________________

    @Override
    public int size() {
        return items != null ? items.size() : 0;
    }

    @Override
    public boolean isEmpty() {
        return items != null && items.isEmpty();
    }

    /**
     * Devuelve la lista de elementos
     * <p>
     * [EN]  Returns the list of elements
     *
     * @return Lista de elementos [EN]  List of items
     */
    @Override
    @Nullable
    public List<T> getItems() {
        return items;
    }

    /**
     * Devuelve el resgitro para la posición indicada
     * <p>
     * [EN]  Returns the resgitro for the indicated position
     *
     * @param index posición del elemento
     */
    @Override
    @Nullable
    public T get(int index) {
        return items != null && index > -1 && index < size() ? items.get(index) : null;
    }

    @Override
    public void addAll(@Nullable List<T> items) {
        // Log.d(LOG_TAG.TAG, "items nulos " + (items == null));
        if (items != null) {
            //   Log.d(LOG_TAG.TAG, "Añadidos " + items.size());
            /*Agregar la lista de registros [EN]  Add the list of records*/
            this.items.addAll(items);

         /*Notificar cambios de selección [EN]  Notify selection changes*/
            if (adapterNotifier != null) {
                adapterNotifier.notifyDataAdd(items.size(), viewHolderType);
            }
        }
    }

    @Override
    public void add(@Nullable T item) {
        if (item != null) {
        /*Agregar elemento [EN]  Add Item*/
            this.items.add(item);

        /*Notificar cambios de selección [EN]  Notify selection changes*/
            clearExpanded();

            if (adapterNotifier != null) {
                adapterNotifier.notifyItemInserted(size() - 1, size(), viewHolderType);
            }
        }
    }

    @Override
    public void add(int index, @Nullable T item) {
         /*Si la posición está fuera de rango terminamos el proceso [EN]  If the position is out of range we finish the process*/
        if ((index > -1 && index < size()) || item != null) {
        /*Agregar elemento [EN]  Add Item*/
            this.items.add(index, item);

        /*Notificar cambios de selección [EN]  Notify selection changes*/
            clearExpanded();

            if (adapterNotifier != null) {
                adapterNotifier.notifyItemInserted(index, size(), viewHolderType);
            }
        }
    }

    @Override
    public void remove(int index) {
         /*Si la posición está fuera de rango terminamos el proceso [EN]  If the position is out of range we finish the process*/
        if ((index > -1 && index < size())) {

         /*Notificar cambios de selección [EN]  Notify selection changes*/
            if (expandController != null) {
                expandController.delete(index);
            }
            if (selectionController != null) {
                selectionController.delete(index);
            }
            if (adapterNotifier != null) {
                adapterNotifier.notifyItemRemoved(index, viewHolderType);
            }
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (expandController != null) {
                            expandController.collapseAll();
                        }
                    } catch (Exception ignored) {
                    }
                }
            });



            /*Eliminar elemento [EN]  Delete item*/
            this.items.remove(index);
        }
    }

    @Override
    public void clear() {
          /*Notificar cambios de selección [EN]  Notify selection changes*/
        clearControllers();

        if (adapterNotifier != null) {
            adapterNotifier.notifyDataRemoved(size(), viewHolderType);
        }

          /*Limpiar lista [EN]  Clean list*/
        this.items.clear();
    }

    @Override
    public void set(@Nullable Integer index, @Nullable T item) {

   /*Validar entrada [EN]  Validate entry*/
        if (index != null && item != null && index > -1 && index < size()) {
            /*Actualizar registro [EN]  Update record*/
            this.items.set(index, item);
       /*Notificar cambios de selección [EN]  Notify selection changes*/
            onItemChaged(index);
        }
    }

    @Override
    public void replace(List<T> items) {
        clear();
        addAll(items);
    }

    //SELECTION ITEM MODEL____________________________________________________________________
    @Override
    @Nullable
    public List<T> removeSelectedItems() {
        List<T> toRemove = new ArrayList<>();
        List<T> toSave = new ArrayList<>();
        for (int i = 0; i < size(); i++) {

            if (selectionController.get(i)) {
                toRemove.add(getItemAt(i));
                remove(i);
            } else {
                toSave.add(getItemAt(i));
            }
        }

        clear();//Limpiar lista de elementos [EN]  Clean item list
        addAll(toSave);//Agregar los elementos no borrados [EN]  Add items not deleted

        selectionController.setLastposition(-1);
        selectionController.setPosition(-1);
        return toRemove;
    }

    @Override
    @Nullable
    public List<T> getSelectds() {
        List<T> selected = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            if (selectionController.get(i)) {
                selected.add(getItemAt(i));
            }
        }
        return selected;
    }

    @Override
    @Nullable
    public T getItemSelected() {
        if (selectionController.get(selectionController.getPosition())) {
            return getItemAt(selectionController.getPosition());
        }
        for (int i = 0; i <= size(); i++) {
            if (selectionController.get(i))
                return getItemAt(i);
        }
        return null;
    }

    //SET LISTENERS____________________________________________________________________________

    /*Oyentes de pulsación [EN]  Pulsation listeners*/
    public void removeViewItemHandler() {
        this.viewItemHandler = null;
    }

    public void setViewItemHandler(ViewItemHandler<T> viewItemHandler) {
        this.viewItemHandler = viewItemHandler;
    }

    /*Oyentes de cambio [EN]  Change listeners*/
    public void removeAdapterNotifier() {
        this.adapterNotifier = null;
    }

    public void setChangedListener(AdapterNotifier adapterNotifier) {
        this.adapterNotifier = adapterNotifier;
    }

    //VIEW HOLDER CONTROLLER____________________________________________________________________________
    @Override
    public boolean isExpaned(int posicion) {
        return expandController != null && adapterNotifier != null && expandController.get(adapterNotifier.indexPos(posicion, viewHolderType));
    }

    @Override
    public boolean isSelected(int posicion) {
        return selectionController != null && adapterNotifier != null && selectionController.get(adapterNotifier.indexPos(posicion, viewHolderType));
    }

    @Override
    public void onClick(BaseViewHolder<T> holder, int position) {
        if (selectionController != null) {

            int index = adapterNotifier != null ? adapterNotifier.indexPos(position, viewHolderType) : position;

            /*Actualizar las variables de posición [EN]  Update position variables*/
            selectionController.setLastposition(selectionController.getPosition());
            selectionController.setPosition(index);


            int lastposition = selectionController.getLastposition();
            int posicion = selectionController.getPosition();

            switch (selectionmode) {
                case NOT_SELECTION_MODE:
                    return;

                case ONLY_SINGLE_SELECTION_MODE:
                case SINGLE_SELECTION_MODE:
                    if (lastposition != position) {
                        //Comprobamos que no estamos en el mismo registro
                        selectionController.setSelected(lastposition, false);

                    /*Notificar cambios de selección [EN]  Notify selection changes*/
                        onItemChaged(lastposition);
                        onItemChaged(position);
                    }

                    selectionController.setSelected(position, !holder.getItemView().isSelected());

                /*Notificar cambios de selección [EN]  Notify selection changes*/
                    onItemChaged(position);
                    break;
                case ONLY_MULTIPLE_SELECTION_MODE:
                case MULTIPLE_SELECTION_MODE:

                    selectionController.setSelected(position, !holder.getItemView().isSelected());

                /*Notificar cambios de selección [EN]  Notify selection changes*/
                    onItemChaged(position);

                    //Si es el ultimo registro pasar a selección sencilla
                    //[EN]  If the last record goes to simple selection
                    if (selectionController.isEmptySelected() && selectionmode == ListExtra.MULTIPLE_SELECTION_MODE) {
                        //Si no hay elementos seleccionados pasar a modo singular
                        //[EN]   If there are no selected elements go to singular mode
                        selectionmode = ListExtra.SINGLE_SELECTION_MODE;
                    }
                    break;
            }

          /*Lanzar la pulsación sobre el elemento [EN]  Release the key on the element*/
            if (viewItemHandler != null) {
                viewItemHandler.onClickItem(holder, getItemAt(position), position, selectionmode);
            }
        }
    }

    @Override
    public boolean onLongClick(BaseViewHolder<T> holder, int position) {

        if (selectionController != null) {

            int index = adapterNotifier != null ? adapterNotifier.indexPos(position, viewHolderType) : position;

           /*Actualizar las variables de posición [EN]  Update position variables*/
            selectionController.setLastposition(selectionController.getPosition());
            selectionController.setPosition(index);


            int lastposition = selectionController.getLastposition();
            int posicion = selectionController.getPosition();


            switch (selectionmode) {
                case NOT_SELECTION_MODE:
                    return true;
                case ONLY_SINGLE_SELECTION_MODE:
                case SINGLE_SELECTION_MODE:
                /*Deseleccionar la posición anterior [EN]  Deselect previous position*/
                    if (lastposition != position) {

                    /*Comprobar si se ha cambiado el registro [EN]  To compute if the record has been changed*/
                        selectionController.setSelected(lastposition, false);

                    /*Notificar cambios de selección [EN]  Notify selection changes*/
                        onItemChaged(lastposition);
                    }

                    selectionController.setSelected(position, true);

                /*Notificar cambios de selección [EN]  Notify selection changes*/
                    onItemChaged(position);
                    if (selectionmode == ListExtra.SINGLE_SELECTION_MODE) {
                    /*Activar selección multiple [EN]  Enable multiple selection*/
                        selectionmode = ListExtra.MULTIPLE_SELECTION_MODE;
                    }
                    break;

                case MULTIPLE_SELECTION_MODE:
                /*Limpiar selección [EN]  Clear Selection*/
                    selectionController.deselectedAll();
                /*Activar selección simple [EN]  Enable simple selection*/
                    selectionmode = ListExtra.SINGLE_SELECTION_MODE;
                    break;
            }

        /*Lanzar la pulsación sobre el elemento [EN]  Release the key on the element*/
            if (viewItemHandler != null) {
                viewItemHandler.onLongClickItem(holder, getItemAt(position), position, selectionmode);
            }
        }
        return true;
    }

    @Override
    public void setSelected(int position, boolean value) {
        if (selectionController == null || adapterNotifier == null) {
            return;
        }
        selectionController.setSelected(adapterNotifier.indexPos(position, viewHolderType), value);
    }

    @Override
    public T getItemAt(int position) {
        if (items == null || adapterNotifier == null) {
            return null;
        }

        int index = adapterNotifier.indexPos(position, viewHolderType);

        if ((index > -1 && index < this.items.size())) {
            return this.items.get(index);
        }
        return null;
    }

    //ON CHANGED SELECTION LISTENER______________________________________________________________________

    /**
     * {@link OnItemChangedListener}
     */
    @Override
    public void onSelectionChanged() {
        if (adapterNotifier != null) {
            adapterNotifier.notifyDataSetChanged(viewHolderType);
        }
    }

    @Override
    public void onItemChaged(int index) {
        if (adapterNotifier != null) {
            adapterNotifier.notifyItemChanged(index, viewHolderType);
        }
    }

    //SETTER AND GETTER___________________________________________________________________________
    @Override
    @NonNull
    public ListExtra getSelectionmode() {
        return selectionmode;
    }

    @Override
    public void setSelectionmode(@NonNull ListExtra selectionmode) {
        this.selectionmode = selectionmode;
    }

    @Nullable
    @Override
    public SelectionItemsController getSelectionItemsController() {
        return this.selectionController;
    }

    @Nullable
    @Override
    public ExpandItemsController getExpandItemsController() {
        return this.expandController;
    }
}
