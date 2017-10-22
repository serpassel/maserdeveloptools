package es.marser.backgroundtools.recyclerviews.simple.adpaters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.TouchableViewHandler;
import es.marser.backgroundtools.handlers.ViewItemHandler;
import es.marser.backgroundtools.recyclerviews.listeners.OnItemChangedListener;
import es.marser.backgroundtools.recyclerviews.simple.controllers.ArrayListController;
import es.marser.backgroundtools.recyclerviews.simple.controllers.ExpandController;
import es.marser.backgroundtools.recyclerviews.simple.controllers.SelectionController;
import es.marser.backgroundtools.recyclerviews.simple.controllers.ViewHolderController;
import es.marser.backgroundtools.recyclerviews.simple.holder.ViewHolderBinding;

/**
 * @author sergio
 *         Created by Sergio on 09/03/2017.
 *         Clase generica para recycler view Adapter
 *         <ul>
 *         <il>Eventos de acción</il>
 *         <il>Sobreescritura de métodos de superclase</il>
 *          <il>Sobre escritura de métodos de ineterface</il>
 *         <p>
 *         <il>Oyentes de modificación de elementos</il>
 *         <il>Acceso a variables</il>
 *         <p>
 *         [EN]  Generic class for recycler view Adapter
 *         <ul>
 *         <il>Methods of overwriting</il>
 *         <il>Action Events</il>
 *         <p>
 *         <il>Element modification listeners</il>
 *         <il>Access to variables</il>
 *         <p>
 *         </ul>
 */

@SuppressWarnings({"SameReturnValue", "unused"})
public abstract class BaseBindAdapterList<T>
        extends RecyclerView.Adapter<ViewHolderBinding<T>>
        implements OnItemChangedListener, ViewHolderController<T> {

    /*Variables de control [EN]  Control variables*/
    public SelectionController<T> selectionController;
    public ArrayListController<T> arrayListController;
    public ExpandController expandController;

    //CONSTRUCTORS____________________________________________________________________________________________
    public BaseBindAdapterList() {

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
    public TouchableViewHandler<T> getTouchableViewHandler() {
        return null;
    }

    /*Oyente de enlace e la*/
    public ViewItemHandler<T> getItemHandler() {
        return null;
    }

    /**
     * Indicar la R.layout.xxxx-xxxxx
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
       /*Recuperar inflador de vistas [EN]  Recover view inflator*/
        LayoutInflater layoutInflater = LayoutInflater.from((parent.getContext()));
        /*Inflar elemento de vinculación de datos [EN]  Inflate Data Link Element*/
        ViewDataBinding dataBinding = DataBindingUtil.inflate(layoutInflater, getHolderLayout(), parent, false);
        /*Nueva instancia de la vista [EN]  New view instance*/
        return new ViewHolderBinding<>(dataBinding, this);
    }

    @Override
    public void onBindViewHolder(ViewHolderBinding<T> holder, int position) {
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
    /*Sobreescritura de OnItemChangedListener [EN]  OnItemChangedListener Overwrite*/
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
        notifyDataSetChanged();
    }

    /*Sobreescritura de ViewHolderController [EN]  Overwrite ViewHolderController*/
    @Override
    public boolean isExpaned(int position) {
        return expandController.get(position);
    }

    @Override
    public boolean isSelected(int position){
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
    public T getItemAt(int position){
        return arrayListController.getItemAt(position);
    }
}
