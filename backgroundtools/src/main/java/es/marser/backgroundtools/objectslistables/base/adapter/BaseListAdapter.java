package es.marser.backgroundtools.objectslistables.base.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import es.marser.backgroundtools.handlers.ViewItemHandler;
import es.marser.backgroundtools.objectslistables.base.controller.ArrayListController;
import es.marser.backgroundtools.objectslistables.base.controller.ExpandController;
import es.marser.backgroundtools.objectslistables.base.controller.GlobalController;
import es.marser.backgroundtools.objectslistables.base.controller.SelectionController;
import es.marser.backgroundtools.objectslistables.base.controller.ViewHolderController;
import es.marser.backgroundtools.objectslistables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.objectslistables.base.holder.ViewHolderType;
import es.marser.backgroundtools.objectslistables.base.listeners.AdapterNotifier;
import es.marser.backgroundtools.objectslistables.base.listeners.OnItemChangedListener;


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
        RecyclerView.Adapter<VH>
        implements
        AdapterNotifier {

    /*Variables de control [EN]  Control variables*/

    public GlobalController<T> globalController;

    //CONSTRUCTORS____________________________________________________________________________________________
    public BaseListAdapter() {

        globalController = new GlobalController<>();

        globalController.setChangedListener(this);
        globalController.setViewItemHandler(getItemHandler());
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
        return globalController.getItemCount();
    }


    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
    /*Recuperar inflador de vistas [EN]  Recover view inflator*/
        LayoutInflater layoutInflater = LayoutInflater.from((parent.getContext()));
    /*Inflar elemento de vinculación de datos [EN]  Inflate Data Link Element*/
        ViewDataBinding dataBinding = DataBindingUtil.inflate(layoutInflater, sparseHolderLayout().get(viewType), parent, false);
        /*Nueva instancia de la vista [EN]  New view instance*/
        return onCreateViewHolder(dataBinding, viewType);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
     /*Ajustar selección [EN]  Adjust selection*/
        holder.setSelected();
        /*Ajustar expansión [EN]  Adjust expansion*/
        holder.setExpanded();
    /*Adjuntar el  manejador de eventos de elementos de la vista [EN]  Attach event handler to view items*/
        onBindVH(holder, position);
    }

    @Override
    public int getItemViewType(int position) {
        return ViewHolderType.SIMPLE.ordinal();
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

    /**
     * Utilizar para cuando existen varias listas
     * Recupera la posición la posición de un índice partiendo de la posción en la lista global
     * <p>
     * [EN]  Use for when there are several lists
     * [EN]  Retrieves the position the position of an index starting from the position in the global list
     *
     * @param indexPos índice en la vistas de referencia [EN]  index in the reference views
     * @param viewType Tipo de vista [EN]  Type of view
     * @return Posición plana en el adapter [EN]  Flat position on the adapter
     */
    @Override
    public int flatPos(int indexPos, int viewType) {
        return indexPos;
    }

    /**
     * Utilizar para cuando existen varias listas
     * Recupera la posición en una lista partiendo de la posición plana
     * <p>
     * [EN]  Use for when there are several lists
     * Retrieve the position in a list starting from the flat position
     *
     * @param flaPos   posición plana en el adapter [EN]  flat position on the adapter
     * @param viewType Tipo de vista [EN]  Type of view
     * @return Posición en la lista de referencia [EN]  Position in the reference list
     */
    @Override
    public int indexPos(int flaPos, int viewType) {
        return flaPos;
    }

    /*{@link AdapterNotifier}*/

    @Override
    public void notifyDataSetChanged(int viewType) {
        notifyDataSetChanged();
    }

    @Override
    public void notifyItemRemoved(int index, int viewType) {
        notifyItemRemoved(flatPos(index, viewType));
    }

    @Override
    public void notifyItemChanged(int index, int viewType) {
        notifyItemChanged(flatPos(index, viewType));
    }

    @Override
    public void notifyItemInserted(int index, int viewType) {
        notifyItemInserted(flatPos(index, viewType));
    }

}
