package es.marser.backgroundtools.objectslistables.simple.holder;

import android.databinding.ViewDataBinding;
import android.view.View;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.handlers.TouchableViewHandler;
import es.marser.backgroundtools.objectslistables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.objectslistables.base.holder.ViewHolderType;
import es.marser.backgroundtools.objectslistables.base.controller.ViewHolderController;

/**
 * @author sergio
 *         Created by sergio on 22/10/17.
 *         Objeto de vinculación de datos reciclable  para adaptadores de vistas
 *         <p>
 *         [EN]  Recyclable Data Binding Object for View Adapters
 * @see es.marser.backgroundtools.objectslistables.base.controller.ViewHolderController
 * @see es.marser.backgroundtools.objectslistables.simple.adapter.SimpleListAdapter
 * @see es.marser.backgroundtools.res tag_item_view.xml
 */

@SuppressWarnings({"UnusedReturnValue", "unused"})
public class ViewHolderBinding<T> extends BaseViewHolder<T> {

    /*Variables de control [EN]  Control variables*/
    protected ViewHolderController<T> viewHolderController;

    /**
     * Tipo de vista
     * <p>
     * [EN]  Type of view
     *
     * @return Valor entero del tipo de vista
     */
    @Override
    public int getIndexTypeView() {
        return ViewHolderType.SIMPLE.ordinal();
    }

    //CONSTRUCTORS____________________________________________________________________________________________
    public ViewHolderBinding(
            ViewDataBinding itemViewBindable,
            ViewHolderController<T> viewHolderController) {

        super(itemViewBindable);

        /*Variables de control [EN]  Control variables*/
        this.viewHolderController = viewHolderController;
    }


    //ADJUNTAR MODELOS Y MANEJADORES_______________________________________________________________________

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

    @Override
    public void setSelected() {
            itemView.setSelected(viewHolderController.isSelected(getAdapterPosition()));

            /*Modificar los selectores [EN]  We modify the selectors */
            if(selecttigger != null){
                selecttigger.setOnCheckedChangeListener(null);
                selecttigger.setChecked(itemView.isSelected());
                selecttigger.setOnCheckedChangeListener(this);
            }
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

    @Override
    public void onClick(View view) {
        if (viewHolderController != null) {
            viewHolderController.onClick(this, getAdapterPosition());
        }
    }

    /**
     * Called when a view has been clicked and held.
     *
     * @param v The view that was clicked and held.
     * @return true if the callback consumed the long click, false otherwise.
     */
    @Override
    public boolean onLongClick(View view) {
        if (viewHolderController != null) {
            viewHolderController.onLongClick(this, getAdapterPosition());
        }
        return true;
    }


}