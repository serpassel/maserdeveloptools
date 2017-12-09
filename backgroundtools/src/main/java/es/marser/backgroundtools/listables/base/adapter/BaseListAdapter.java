package es.marser.backgroundtools.listables.base.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import es.marser.LOG_TAG;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.definition.Restorable;
import es.marser.backgroundtools.listables.base.controller.ExpandController;
import es.marser.backgroundtools.listables.base.controller.ViewHolderController;
import es.marser.backgroundtools.listables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.listables.base.holder.ViewHolderType;
import es.marser.backgroundtools.listables.base.listeners.AdapterNotifier;
import es.marser.backgroundtools.listables.base.listeners.OnItemChangedListener;
import es.marser.backgroundtools.listables.base.model.Selectionable;


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
 * @see ExpandController
 * @see es.marser.backgroundtools.recyclerviews.simple.holder.ViewHolderBinding
 * @see ViewHolderController
 * @see OnItemChangedListener
 */

@SuppressWarnings({"SameReturnValue", "unused", "EmptyMethod"})
public abstract class BaseListAdapter<VH extends BaseViewHolder>
        extends
        RecyclerView.Adapter<VH>
        implements
        AdapterNotifier, Restorable, Selectionable {

    public boolean enabledAttachedAnimHolders;
    public boolean enabledDetachedAnimHolders;
    private int attachedAnim;
    private int detachedAnim;

    protected  SparseIntArray sparseHolderLayout;


    private static String[] baselistadapterKey = {
            "enabledAttachedAnimHolders_key",
            "enabledDetachedAnimHolders_key",
            "attachedAnim_key",
            "detachedAnim_key"};


//SAVED AND RESTORE_____________________________________________________________

    @Override
    public void onSaveInstanceState(@Nullable Bundle savedInstanceState) {
        // Log.e(LOG_TAG.TAG, "VISTA GRABADA");
        if (savedInstanceState != null) {
            savedInstanceState.putBoolean(baselistadapterKey[0], enabledAttachedAnimHolders);
            savedInstanceState.putBoolean(baselistadapterKey[1], enabledDetachedAnimHolders);
            savedInstanceState.putInt(baselistadapterKey[2], attachedAnim);
            savedInstanceState.putInt(baselistadapterKey[3], detachedAnim);
        }
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            enabledAttachedAnimHolders = savedInstanceState.getBoolean(baselistadapterKey[0], false);
            enabledDetachedAnimHolders = savedInstanceState.getBoolean(baselistadapterKey[1], false);
            attachedAnim = savedInstanceState.getInt(baselistadapterKey[2], R.anim.slide_left_end);
            detachedAnim = savedInstanceState.getInt(baselistadapterKey[3], R.anim.slide_right_end);

        }
        notifyDataSetChanged();
        //Log.e(LOG_TAG.TAG, "RESTAURADA VISTA");
    }


    //CONSTRUCTORS____________________________________________________________________________________________
    public BaseListAdapter() {
        enabledAttachedAnimHolders = false;
        enabledDetachedAnimHolders = false;
        attachedAnim = R.anim.slide_left_end;
        detachedAnim = R.anim.slide_right_end;
        sparseHolderLayout = new SparseIntArray();
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
        if (enabledAttachedAnimHolders) {
            Animation animation = AnimationUtils.loadAnimation(holder.getItemView().getContext(), attachedAnim);
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
        if (enabledDetachedAnimHolders) {
            Animation animation = AnimationUtils.loadAnimation(holder.getItemView().getContext(), detachedAnim);
            holder.getItemView().setAnimation(animation);
        }
    }


    //ACTION EVENTS_______________________________________________________________________________________________
    public void setHolderLayout(ViewHolderType type, int holderLayout){
        sparseHolderLayout.put(type.ordinal(), holderLayout);
    }

    //OVERRIDE SUPERCLASS__________________________________________________________________________
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
    /*Recuperar inflador de vistas [EN]  Recover view inflator*/
        LayoutInflater layoutInflater = LayoutInflater.from((parent.getContext()));
    /*Inflar elemento de vinculación de datos [EN]  Inflate Data Link Element*/
        ViewDataBinding dataBinding = DataBindingUtil.inflate(layoutInflater, sparseHolderLayout.get(viewType), parent, false);
       LOG_TAG.assertNotNull(dataBinding);
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
        int pos = index == count - 1 ? getItemCount() - 1 : flatPos(index, viewType);
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

    /**
     * @return Verdadero si está activada la animación de viewholder al adjuntarse a la ventana
     * [EN]  True if the viewholder animation is activated when attached to the window
     * @see se utiliza en {@link #onViewAttachedToWindow(BaseViewHolder)}
     * Para modficiar el tipo de animación {@link #setAttachedAnim(int)}
     */
    public boolean isEnabledAttachedAnimHolders() {
        return enabledAttachedAnimHolders;
    }

    /**
     * Establece la animación de viewsholder al adjutarse a la ventana
     * <p>
     * [EN]  Sets the viewholder animation when attached to the window
     *
     * @param enabledAttachedAnimHolders Verdadero para activar [EN]  True to activate
     */
    public void setEnabledAttachedAnimHolders(boolean enabledAttachedAnimHolders) {
        this.enabledAttachedAnimHolders = enabledAttachedAnimHolders;
    }

    /**
     * @return Verdadero si está activada la animación de viewholder al separarse de la ventana
     * [EN]  True if the viewholder animation is activated when detached from the window
     * @see se utiliza en {@link #onViewAttachedToWindow(BaseViewHolder)}
     * Para modficiar el tipo de animación {@link #setAttachedAnim(int)}
     */
    public boolean isEnabledDetachedAnimHolders() {
        return enabledDetachedAnimHolders;
    }

    /**
     * Establece la animación de viewsholder al separarse dela ventana
     * <p>
     * [EN]  Sets the viewholder animation when detached to the window
     *
     * @param enabledAttachedAnimHolders Verdadero para activar [EN]  True to activate
     */
    public void setEnabledDetachedAnimHolders(boolean enabledDetachedAnimHolders) {
        this.enabledDetachedAnimHolders = enabledDetachedAnimHolders;
    }

    /**
     * Establece el recurso de animación al adjuntarse a la ventana
     * <p>
     * [EN]  Set the animation resource when attached to the window
     *
     * @param attachedAnim recurso de animación R.anim.XXXX
     */
    public void setAttachedAnim(int attachedAnim) {
        this.attachedAnim = attachedAnim;
    }

    /**
     * Establece el recurso de animación al separarse de la ventana
     * <p>
     * [EN]  Set the animation resource when detached from the window
     *
     * @param detachedAnim recurso de animación R.anim.XXXX
     */
    public void setDetachedAnim(int detachedAnim) {
        this.detachedAnim = detachedAnim;
    }
}
