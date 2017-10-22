package es.marser.backgroundtools.recyclerviews.simple.holder;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.RotateAnimation;

import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.handlers.TouchableViewHandler;
import es.marser.backgroundtools.recyclerviews.simple.controllers.ViewHolderController;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

/**
 * @author sergio
 *         Created by sergio on 22/10/17.
 *         Objeto de vinculación de datos reciclable  para adaptadores de vistas
 *         <p>
 *         [EN]  Recyclable Data Binding Object for View Adapters
 * @see es.marser.backgroundtools.recyclerviews.simple.controllers.ViewHolderController
 * @see es.marser.backgroundtools.recyclerviews.simple.adapters.BaseBindAdapterList
 * @see es.marser.backgroundtools.res tag_item_view.xml
 */

@SuppressWarnings({"UnusedReturnValue", "unused"})
public class ViewHolderBinding<T> extends RecyclerView.ViewHolder
        implements View.OnClickListener, View.OnLongClickListener {

    /*Variables de control [EN]  Control variables*/
    public ViewHolderController<T> viewHolderController;

    /*Variables de vistas [EN]  View Variables*/
    /*Vista vinculada [EN]  Linked view*/
    private ViewDataBinding itemViewBindable;
    /*Vista raíz [EN]  Root view*/
    private View itemView;

    /*Vista comprimida [EN]  Collapsed view*/
    private View collapsresumView;
    /*Vista expandida [EN]  Expanded view*/
    private View expandAreaView;
    /*Activador de expansión [EN]  Expansion Trigger*/
    private View expandtigger;

    //CONSTRUCTORS____________________________________________________________________________________________
    public ViewHolderBinding(
            ViewDataBinding itemViewBindable,
            ViewHolderController<T> viewHolderController) {

        super(itemViewBindable.getRoot());

        /*Variables de control [EN]  Control variables*/
        this.viewHolderController = viewHolderController;

        /*Variables de vistas [EN]  View Variables*/
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
    }

    //ADJUNTAR MODELOS Y MANEJADORES_______________________________________________________________________

    /**
     * Vinculación del modelo de datos con la vista
     * <p>
     * [EN]  Linking the Data Model to the View
     *
     * @param item modelo de datos [EN]  data model
     * @return Class principal para seteos conscutivos [EN]  Main class for conspicuous settings
     */
    public ViewHolderBinding bind(T item) {
        //+++++++++++IMPORTANTE [EN]  IMPORTANT---------------------------------------/
        /*La variable de datos en el modelo se debe nombrar como 'item' 
        [EN]  The data variable in the model must be named as an item*/
        itemViewBindable.setVariable(BR.item, item);
        itemViewBindable.executePendingBindings();
        return this;
    }

    /**
     * Adjuntar manejador de eventos de pulsación sobre las vistas secundarias
     * <p>
     * [EN]  Attach event handler over secondary views
     *
     * @param handler manejador [EN]  manager
     */
    public void attachTouchableViewHandler(final TouchableViewHandler<T> handler) {
        if (handler == null) {
            return;
        }

        for (View v : itemView.getTouchables()) {

            if (v.getTag() != null && v.getTag().equals(v.getContext().getResources().getString(R.string.TOUCHABLE_VIEW_ACTIONABLE))) {

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handler.onClick(
                                v,
                                getAdapterPosition(),
                                viewHolderController.getItemAt(getAdapterPosition()),
                                itemView
                        );
                    }
                });
                v.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        handler.onLongClick(
                                v,
                                getAdapterPosition(),
                                viewHolderController.getItemAt(getAdapterPosition()),
                                itemView
                        );
                        return true;
                    }
                });
            }
            if (expandtigger != null) {
                expandtigger.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handler.onClick(
                                v,
                                getAdapterPosition(),
                                viewHolderController.getItemAt(getAdapterPosition()),
                                itemView
                        );
                    }
                });
                expandtigger.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        handler.onLongClick(
                                v,
                                getAdapterPosition(),
                                viewHolderController.getItemAt(getAdapterPosition()),
                                itemView
                        );
                        return true;
                    }
                });
            }
        }
    }

//ACTIONS_______________________________________________________________________________________________________

    /**
     * Marcar selección
     * <p>
     * [EN]  Mark Selection
     */
    public void setSelected() {
        itemView.setSelected(viewHolderController.isSelected(getAdapterPosition()));
    }

    /**
     * Marcar expansión
     * <p>
     * [EN]  Mark Expansion
     */
    public void setExpanded() {
        if (expandAreaView != null) {
            if (viewHolderController.isExpaned(getAdapterPosition())) {
                expandAreaView.setVisibility(View.VISIBLE);
                expand();
                if (collapsresumView != null) {
                    collapsresumView.setVisibility(View.GONE);
                }
            } else {
                expandAreaView.setVisibility(View.GONE);
                collapse();
                if (collapsresumView != null) {
                    collapsresumView.setVisibility(View.VISIBLE);
                }
            }
        }
    }

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
        if (expandAreaView != null) {
            RotateAnimation rotate =
                    new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(300);
            rotate.setFillAfter(true);
            expandtigger.setAnimation(rotate);
        }
    }

    @Override
    public void onClick(View view) {
        viewHolderController.onClick(view, getAdapterPosition());
    }

    /**
     * Called when a view has been clicked and held.
     *
     * @param v The view that was clicked and held.
     * @return true if the callback consumed the long click, false otherwise.
     */
    @Override
    public boolean onLongClick(View v) {
        viewHolderController.onLongClick(v, getAdapterPosition());
        return true;
    }
}