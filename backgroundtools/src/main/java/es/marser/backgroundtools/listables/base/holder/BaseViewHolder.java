package es.marser.backgroundtools.listables.base.holder;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.CompoundButton;

import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.R;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

/**
 * @author sergio
 *         Created by sergio on 24/10/17.
 */

@SuppressWarnings("unused")
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder
        implements View.OnClickListener, View.OnLongClickListener, CompoundButton.OnCheckedChangeListener {

    /*Variables de vistas [EN]  View Variables*/
    /*Vista vinculada [EN]  Linked view*/
    protected ViewDataBinding itemViewBindable;
    /*Vista raíz [EN]  Root view*/
    protected View itemView;

    /*Vista comprimida [EN]  Collapsed view*/
    protected View collapsresumView;
    /*Vista expandida [EN]  Expanded view*/
    protected View expandAreaView;
    /*Activador de expansión [EN]  Expansion Trigger*/
    protected View expandtigger;
    /*Activador de selección [EN]  Selection activator*/
    protected CompoundButton selecttigger;


    public BaseViewHolder(ViewDataBinding itemViewBindable) {
        super(itemViewBindable.getRoot());
        this.itemViewBindable = itemViewBindable;
        this.itemView = itemViewBindable.getRoot();

         /*Ajuste de eventos de pulsación [EN]  Adjusting pulse events*/
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);

        /*Definición de áreas expandibles [EN]  Definition of expandable areas*/
        /*Expandida [EN]  Expanded*/
        expandAreaView = itemView.findViewWithTag(
                itemView
                        .getResources()
                        .getString(R.string.SECONDARY_EXPANDABLE_VIEW));

        /*Colapsada [EN]  Collapsed*/
        collapsresumView = itemView.findViewWithTag(
                itemView
                        .getResources()
                        .getString(R.string.PRIMARY_EXPANDABLE_VIEW));

        /*Actuador de expansión [EN]  Expansion actuator*/
        expandtigger = itemView.findViewWithTag(
                itemView
                        .getResources()
                        .getString(R.string.EXPANDABLE_VIEW_TIGGER));

        /*Activador de selección [EN]  Selection activator*/
        try {
            selecttigger = itemView.findViewWithTag(itemView.getResources().getString(R.string.SELECTOR_VIEW_TIGGER));

            if (selecttigger != null) {
                selecttigger.setOnCheckedChangeListener(this);
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    //VIEW_______________________________________________________________________________________________

    /**
     * Devuelve la vista vinculada
     * <p>
     * [EN]  Returns the linked view
     *
     * @return vista vinculada [EN]  linked view
     */
    public ViewDataBinding getItemViewBindable() {
        return itemViewBindable;
    }

    /**
     * Devuelve la vista raiz
     * <p>
     * [EN]  Returns the root view
     *
     * @return la vista raiz [EN]  the root view
     */
    public View getItemView() {
        return itemView;
    }

    //ADJUNTAR MODELOS Y MANEJADORES_______________________________________________________________________

    /**
     * Tipo de vista
     * <p>
     * [EN]  Type of view
     *
     * @return Índice de la vista [EN]  View index
     */
    public abstract int getIndexTypeView();

    /**
     * Vinculación del modelo de datos con la vista
     * +++++++++++IMPORTANTE [EN]  IMPORTANT---------------------------------------/
     * La variable de datos en el modelo se debe nombrar como 'item'
     * <p>
     * [EN]  Linking the Data Model to the View
     * The data variable in the model must be named as an item
     *
     * @param item modelo de datos [EN]  data model
     */
    public void bind(T item) {
        //+++++++++++IMPORTANTE [EN]  IMPORTANT---------------------------------------/
        /*La variable de datos en el modelo se debe nombrar como 'item'
        [EN]  The data variable in the model must be named as an item*/
        itemViewBindable.setVariable(BR.item, item);
        itemViewBindable.executePendingBindings();
    }

    /**
     * Fija el estado de selección
     * <p>
     * [EN]  Set the selection status
     */
    public abstract void setSelected();

    /**
     * Fija el estado de expansión
     * <p>
     * [EN]  Set the expansion state
     */
    public abstract void setExpanded();

    /**
     * Evento de pusalción en una subvista chequeadora
     * <p>
     * [EN]  Preaching event in a checking subview
     *
     * @param view      Vista chequeadora [EN]  Checking view
     * @param b Valor del estado de chequeo [EN]  Check status value
     */
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        onClick(itemView);

        if (b) {
            select();
        } else {
            unselect();
        }
    }

//ACTIONS_______________________________________________________________________________________________________

    /**
     * Acción de expandir
     * <p>
     * [EN]  Action to expand
     */
    public void expand() {
        if (expandtigger != null) {
            RotateAnimation rotate =
                    new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(300);
            rotate.setFillAfter(true);
            expandtigger.setAnimation(rotate);
        }
    }

    /**
     * Acción de colapsar
     * <p>
     * [EN]  Collapse action
     */
    public void collapse() {
        if (expandtigger != null) {
            RotateAnimation rotate =
                    new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(300);
            rotate.setFillAfter(true);
            expandtigger.setAnimation(rotate);
        }
    }

    /**
     * Acción de selección
     * <p>
     * [EN]  Selection action
     */
    public void select() {
        if (selecttigger != null) {
            Animation animation = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.slide_left_end);
            selecttigger.setAnimation(animation);
        }
    }

    /**
     * Acción de deselección
     * <p>
     * [EN]  Deselection action
     */
    public void unselect() {
        if (selecttigger != null) {
            Animation animation = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.slide_right_end);
            selecttigger.setAnimation(animation);
        }
    }

}
