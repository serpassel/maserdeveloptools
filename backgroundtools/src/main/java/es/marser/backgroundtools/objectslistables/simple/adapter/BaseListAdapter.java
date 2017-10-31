package es.marser.backgroundtools.objectslistables.simple.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.marser.LOG_TAG;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.TouchableViewHandler;
import es.marser.backgroundtools.handlers.ViewItemHandler;
import es.marser.backgroundtools.objectslistables.simple.listeners.OnItemChangedListener;
import es.marser.backgroundtools.objectslistables.simple.controller.ArrayListController;
import es.marser.backgroundtools.objectslistables.simple.controller.ExpandController;
import es.marser.backgroundtools.objectslistables.simple.controller.SelectionController;
import es.marser.backgroundtools.objectslistables.simple.controller.ViewHolderController;
import es.marser.backgroundtools.objectslistables.simple.holder.ViewHolderBinding;


/**
 * @author sergio
 *         Created by Sergio on 09/03/2017.
 *         Clase generica para recycler view Adapter
 *         <ul>
 *         <il>Eventos de acción</il>
 *         <il>Sobreescritura de métodos de superclase</il>
 *         <il>Sobre escritura de métodos de interface</il>
 *         <il>Oyentes de modificación de elementos</il>
 *         <il>Acceso a variables</il>
 *         <p>
 *         [EN]  Generic class for recycler view Adapter
 *         <ul>
 *         <il>Action Events</il>
 *         <il>Superclass methods overwriting</il>
 *         <il>About Writing Interface Methods</il>
 *         <il>Element modification listeners</il>
 *         <il>Access to variables</il>
 *         <p>
 *         </ul>
 * @see es.marser.backgroundtools.objectslistables.simple.controller.SelectionController
 * @see es.marser.backgroundtools.objectslistables.simple.controller.ArrayListController
 * @see es.marser.backgroundtools.objectslistables.simple.controller.ExpandController
 * @see es.marser.backgroundtools.recyclerviews.simple.holder.ViewHolderBinding
 * @see es.marser.backgroundtools.objectslistables.simple.controller.ViewHolderController
 * @see es.marser.backgroundtools.objectslistables.simple.listeners.OnItemChangedListener
 */

@SuppressWarnings({"SameReturnValue", "unused"})
public abstract class BaseListAdapter<T>
        extends
        RecyclerView.Adapter<ViewHolderBinding<T>>
        implements
        OnItemChangedListener,
        ViewHolderController<T> {

    /*Variables de control [EN]  Control variables*/
    public SelectionController<T> selectionController;
    public ArrayListController<T> arrayListController;
    public ExpandController expandController;

    //CONSTRUCTORS____________________________________________________________________________________________
    public BaseListAdapter() {

        /*Nueva instancia de controladores [EN]  New controller instance*/
        arrayListController = new ArrayListController<>();
        selectionController = new SelectionController<>(
                arrayListController,
                ListExtra.SINGLE_SELECTION_MODE);

        expandController = new ExpandController(this);

        /*Definición de oyentes a los controladores [EN]  Definition of listeners to the controllers*/
        arrayListController.setOnChangedListListener(this);
        selectionController.setOnSelectionChanged(this);
        selectionController.setItemHandler(getItemHandler());
    }

    //ACTION EVENTS_______________________________________________________________________________________________
    /*Sobreescritura para introducir de manejador de eventos [EN]  Overwrite to enter event handler*/

    /**
     * Variable de oyente para pulsacione de las vistas anidadas a la vista raiz del item
     * <p>
     * [EN]  Listener variable for clicking nested views to the root view of the item
     *
     * @return Variable de oyente de tipo {@link TouchableViewHandler}
     */
    public TouchableViewHandler<T> getTouchableViewHandler() {
        return null;
    }

    /**
     * Variable de oyente para las pulsaciones sobre la vista raíz
     * <p>
     * [EN]  Listener variable for the keystrokes on the root view
     *
     * @return Variable de oyente de tipo {@link ViewItemHandler}
     */
    public ViewItemHandler<T> getItemHandler() {
        return null;
    }

    /**
     * Vista del item
     * <p>
     * [EN]  Item view
     *
     * @return variable de vista de los elementos [EN]  variable view of the elements
     */
    protected abstract int getHolderLayout();

    //OVERRIDE SUPERCLASS__________________________________________________________________________

    /*Sobreescritura de  RecyclerView.Adapter [EN]  RecyclerView.Adapter Overwrite*/
    @Override
    public int getItemCount() {
        return arrayListController.size();
    }

    @Override
    public ViewHolderBinding<T> onCreateViewHolder(ViewGroup parent, int viewType) {
      //  Log.d(LOG_TAG.TAG, "CREATE HOLDER");
       /*Recuperar inflador de vistas [EN]  Recover view inflator*/
        LayoutInflater layoutInflater = LayoutInflater.from((parent.getContext()));
        /*Inflar elemento de vinculación de datos [EN]  Inflate Data Link Element*/
        ViewDataBinding dataBinding = DataBindingUtil.inflate(layoutInflater, getHolderLayout(), parent, false);
        /*Nueva instancia de la vista [EN]  New view instance*/
        return new ViewHolderBinding<>(dataBinding, this);
    }

    @Override
    public void onBindViewHolder(ViewHolderBinding<T> holder, int position) {
        //Log.d(LOG_TAG.TAG, "BIN HOLDER");
        /*Ajustar selección [EN]  Adjust selection*/
        holder.setSelected();
        /*Ajustar expansión [EN]  Adjust expansion*/
        holder.setExpanded();
        /*Introducir la variable de modelo de datos [EN]  Enter the data model variable*/
        holder.bind(arrayListController.getItemAt(position));
        /*Adjuntar el  manejador de eventos de elementos de la vista [EN]  Attach event handler to view items*/
        holder.attachTouchableViewHandler(getTouchableViewHandler());
    }


    //ELEMENT MODIFICATION LISTENERS_______________________________________________________________

    /**
     * {@link OnItemChangedListener}
     */
    @Override
    public void onSelectionChanged() {
        notifyDataSetChanged();
    }

    @Override
    public void onItemChaged(int position) {
        notifyItemChanged(position);
    }

    @Override
    public void onAddItem(int position) {
        expandController.clear();
        notifyItemInserted(position);
    }

    @Override
    public void onRemoveItem(int position) {
        expandController.delete(position);
        selectionController.delete(position);
        notifyItemRemoved(position);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    expandController.collapseAll();
                } catch (Exception ignored) {
                }
            }
        });
    }

    @Override
    public void removeAllItems() {
        // Log.i(MainCRUD.TAG, "Eliminados todos los registros;");
        selectionController.clear();
        expandController.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onAllChanged() {
       // Log.i(LOG_TAG.TAG, "Notificar cambios totales");
        notifyDataSetChanged();
    }

    /**
     * {@link ViewHolderController}
     */
    @Override
    public boolean isExpaned(int position) {
        return expandController.get(position);
    }

    @Override
    public boolean isSelected(int position) {
        return selectionController.get(position);
    }

    @Override
    public void onClick(View view, int position) {
        selectionController.onClick(view, position);
    }

    @Override
    public boolean onLongClick(View view, int position) {
        return selectionController.onLongClick(view, position);
    }

    @Override
    public T getItemAt(int position) {
        return arrayListController.getItemAt(position);
    }
}
