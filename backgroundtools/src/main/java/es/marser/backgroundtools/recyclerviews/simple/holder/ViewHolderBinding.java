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
    private View expandchevron;


    public ViewHolderBinding(
            ViewDataBinding itemViewBindable, ViewHolderController<T> viewHolderController) {
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
                        .getString(R.string.EXPANDABLE_VIEW));

        /*Colapsada [EN]  Collapsed*/
        collapsresumView = itemView.findViewWithTag(
                itemView
                        .getResources()
                        .getString(R.string.RESUM_EXPANDABLE_VIEW));

        /*Actuador de expansión [EN]  Expansion actuator*/
        expandchevron = itemView.findViewWithTag(
                itemView
                        .getResources()
                        .getString(R.string.EXPAN_VIEW_CHEVERON));
    }

    public ViewHolderBinding bind(T item) {
        //R se tiene que llamar item la variable
        itemViewBindable.setVariable(BR.item, item);
        itemViewBindable.executePendingBindings();
        //Si no es seleccionable quitamos la selección
        return this;
    }

    public void attachTouchableViewHandler(final TouchableViewHandler<T> handler) {
        if (handler == null) {
            return;
        }

        for (View v : itemView.getTouchables()) {

            if (v.getTag() != null && v.getTag().equals(v.getContext().getResources().getString(R.string.INCLUDE_ITEM_ACTIONS))) {

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
            if (expandchevron != null) {
                expandchevron.setOnClickListener(new View.OnClickListener() {
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
                expandchevron.setOnLongClickListener(new View.OnLongClickListener() {
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


    public void setSelected() {
        itemView.setSelected(viewHolderController.isSelected(getAdapterPosition()));
    }

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

    public void expand() {
        if (expandchevron != null) {
            RotateAnimation rotate =
                    new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(300);
            rotate.setFillAfter(true);
            expandchevron.setAnimation(rotate);
        }
    }

    public void collapse() {
        if (expandAreaView != null) {
            RotateAnimation rotate =
                    new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(300);
            rotate.setFillAfter(true);
            expandchevron.setAnimation(rotate);
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