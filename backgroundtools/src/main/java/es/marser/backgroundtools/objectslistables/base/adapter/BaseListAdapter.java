package es.marser.backgroundtools.objectslistables.base.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.ViewItemHandler;
import es.marser.backgroundtools.objectslistables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.objectslistables.base.holder.ViewHolderType;
import es.marser.backgroundtools.objectslistables.simple.controller.ArrayListController;
import es.marser.backgroundtools.objectslistables.simple.controller.ExpandController;
import es.marser.backgroundtools.objectslistables.simple.controller.SelectionController;
import es.marser.backgroundtools.objectslistables.simple.controller.ViewHolderController;
import es.marser.backgroundtools.objectslistables.simple.listeners.OnItemChangedListener;


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
 * @see SelectionController
 * @see ArrayListController
 * @see ExpandController
 * @see es.marser.backgroundtools.recyclerviews.simple.holder.ViewHolderBinding
 * @see ViewHolderController
 * @see OnItemChangedListener
 */

@SuppressWarnings({"SameReturnValue", "unused"})
public abstract class BaseListAdapter<T, VH extends BaseViewHolder<T>>
        extends
        RecyclerView.Adapter<BaseViewHolder<T>>
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
     * Selector de vista.
     * La clave conicidente con el el viewType y el valor la vista la layout del ViewHolder
     * <p>
     * [EN]  View selector
     * The key conicidente with the the viewType and the value the view the layout of the ViewHolder
     *
     * @return key, R.layout.XXXXX
     */
    protected abstract SparseIntArray sparseHolderLayout();

    //OVERRIDE SUPERCLASS__________________________________________________________________________

    /*Sobreescritura de  RecyclerView.Adapter [EN]  RecyclerView.Adapter Overwrite*/
    @Override
    public int getItemCount() {
        return arrayListController.size();
    }

    @Override
    public BaseViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        //  Log.d(LOG_TAG.TAG, "CREATE HOLDER");
       /*Recuperar inflador de vistas [EN]  Recover view inflator*/
        LayoutInflater layoutInflater = LayoutInflater.from((parent.getContext()));
        /*Inflar elemento de vinculación de datos [EN]  Inflate Data Link Element*/
        ViewDataBinding dataBinding = DataBindingUtil.inflate(layoutInflater, sparseHolderLayout().get(viewType), parent, false);
        /*Nueva instancia de la vista [EN]  New view instance*/
        return onCreateViewHolder(dataBinding, viewType);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(BaseViewHolder<T> holder, int position) {
        //Log.d(LOG_TAG.TAG, "BIN HOLDER");
        /*Ajustar selección [EN]  Adjust selection*/
        holder.setSelected();
        /*Ajustar expansión [EN]  Adjust expansion*/
        holder.setExpanded();
        /*Introducir la variable de modelo de datos [EN]  Enter the data model variable*/
        holder.bind(getItemAt(position));
        /*Adjuntar el  manejador de eventos de elementos de la vista [EN]  Attach event handler to view items*/
        onBindVH((VH) holder, position);
    }

    @Override
    public int getItemViewType(int position) {
        return ViewHolderType.SIMPLE.ordinal();
    }

    /**
     * Devuelve el elemento de una posición determinada
     * <p>
     * [EN]  Returns the element of a given position
     *
     * @param position posición [EN]  position
     * @return elemento genérico [EN]  generic element
     */
    public T getItemAtPosition(int position) {
        return arrayListController.getItemAt(position);
    }

    /**
     * Añadir enlaces adicionales de la vista con el modelo de datos
     * <p>
     * [EN]  Add additional links of the view with the data model
     *
     * @param holder   Holder de tipo generíco [EN]  Generic type holder
     * @param position posición de datos [EN]  data position
     */
    public abstract void onBindVH(VH holder, int position);

    /**
     * Creación del MVP de la vista definida
     * <p>
     * [EN]  Creation of the MVP of the defined view
     *
     * @param dataBinding Objeto de enlace [EN]  Link object
     * @param viewType    Tipo de vista por defecto {@link ViewHolderType#SIMPLE}
     * @return Modelo de vista {@link ViewDataBinding}
     */
    public abstract VH onCreateViewHolder(ViewDataBinding dataBinding, int viewType);


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
