package es.marser.backgroundtools.objectslistables.base.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.objectslistables.base.controller.ExpandController;
import es.marser.backgroundtools.objectslistables.base.controller.SelectionControllerD;
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
 * @see SelectionControllerD
 * @see ExpandController
 * @see es.marser.backgroundtools.recyclerviews.simple.holder.ViewHolderBinding
 * @see ViewHolderController
 * @see OnItemChangedListener
 */

@SuppressWarnings({"SameReturnValue", "unused", "EmptyMethod"})
public abstract class BaseListAdapter<T extends Parcelable, VH extends BaseViewHolder<T>>
        extends
        RecyclerView.Adapter<VH>
        implements
        AdapterNotifier {

    public boolean animHolders;

    private static String[] animHoldersKey = {"anim_holders_key"};


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
        if (savedInstanceState != null) {
            savedInstanceState.putBoolean(animHoldersKey[0], animHolders);
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
            animHolders = savedInstanceState.getBoolean(animHoldersKey[0], false);
        }

        notifyDataSetChanged();
    }


    //CONSTRUCTORS____________________________________________________________________________________________
    public BaseListAdapter() {
        animHolders = false;
    }

    //ANIMATIONS_____________________________________________________________________

    /**
     * Called when a view created by this adapter has been attached to a window.
     * <p>
     * <p>This can be used as a reasonable signal that the view is about to be seen
     * by the user. If the adapter previously freed any resources in
     * {@link #onViewDetachedFromWindow(RecyclerView.ViewHolder) onViewDetachedFromWindow}
     * those resources should be restored here.</p>
     *
     * @param holder Holder of the view being attached
     */
    @Override
    public void onViewAttachedToWindow(VH holder) {
        super.onViewAttachedToWindow(holder);
        if (animHolders) {
            Animation animation = AnimationUtils.loadAnimation(holder.getItemView().getContext(), R.anim.slide_left_end);
            holder.getItemView().setAnimation(animation);
        }
    }

    /**
     * Called when a view created by this adapter has been detached from its window.
     * <p>
     * <p>Becoming detached from the window is not necessarily a permanent condition;
     * the consumer of an Adapter's views may choose to cache views offscreen while they
     * are not visible, attaching and detaching them as appropriate.</p>
     *
     * @param holder Holder of the view being detached
     */
    @Override
    public void onViewDetachedFromWindow(VH holder) {
        super.onViewDetachedFromWindow(holder);
        if (animHolders) {
            Animation animation = AnimationUtils.loadAnimation(holder.getItemView().getContext(), R.anim.slide_right_end);
            holder.getItemView().setAnimation(animation);
        }
    }


    //ACTION EVENTS_______________________________________________________________________________________________
    /*Sobreescritura para introducir de manejador de eventos [EN]  Overwrite to enter event handler*/

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
    public void notifyItemInserted(int index, int count, int viewType) {
        /*Comprobar si se agrega al final de la lista o es una insercción
        [EN]  Check if it is added to the end of the list or is an insert*/
       int pos = index == count -1 ? getItemCount() -1 : flatPos(index, viewType);
        notifyItemInserted(pos);
    }

    @Override
    public void notifyDataAdd(int count, int viewType) {
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataRemoved(int count, int viewType) {
        notifyDataSetChanged();
    }

    //PROPERTIES_________________________________________________________________________________________
    public boolean isAnimHolders() {
        return animHolders;
    }

    public void setAnimHolders(boolean animHolders) {
        this.animHolders = animHolders;
    }
}
