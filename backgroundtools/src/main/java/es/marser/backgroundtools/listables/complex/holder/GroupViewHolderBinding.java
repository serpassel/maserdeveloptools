package es.marser.backgroundtools.listables.complex.holder;

import android.databinding.ViewDataBinding;
import android.os.Parcelable;
import android.view.View;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.handlers.ComplexTouchabeViewHandler;
import es.marser.backgroundtools.listables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.listables.base.holder.ViewHolderType;
import es.marser.backgroundtools.listables.complex.controller.ComplexViewHolderController;
import es.marser.backgroundtools.listables.complex.models.ExpandableGroup;

/**
 * @author sergio
 *         Se utiliza en las listas.
 *         <p>
 *         Recoge las acciones de pulsación sobre elementos Touchables.
 *         <p>
 *         Tienen que tener el TAG @string/INCLUDE_ITEM_ACTIONS.
 *         <p>
 *         Sobre escribirá cualquier evento anterior de pulsación
 *         <p>
 *         [EN]  It is used in the lists.
 *         <p>
 *         [EN]  Collect touch actions on Touchable elements.
 *         <p>
 *         [EN]  They must have the TAG @ string / INCLUDE_ITEM_ACTIONS.
 *         <p>
 *         [EN]  Overwrite any previous pulse event
 *         <p>
 */

@SuppressWarnings("unused")
public class GroupViewHolderBinding<X extends ExpandableGroup<T>, T extends Parcelable>
        extends BaseViewHolder<X> {

    /*Variables de control [EN]  Control variables*/
    protected ComplexViewHolderController<X, T> viewHolderController;

    @Override
    public int getIndexTypeView() {
        return ViewHolderType.GROUP.ordinal();
    }

    @Override
    public void setSelected() {

    }

    @Override
    public void setExpanded() {

    }

    //CONSTRUCTORS____________________________________________________________________________________________
    public GroupViewHolderBinding(
            ViewDataBinding itemViewBindable,
            ComplexViewHolderController<X,T> viewHolderController) {
        super(itemViewBindable);
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
    public void attachTouchableViewHandler(final ComplexTouchabeViewHandler<X, T> handler) {


        for (View v : itemView.getTouchables()) {
            if (v.getTag() != null && v.getTag().equals(v.getContext().getResources().getString(R.string.TOUCHABLE_VIEW_ACTIONABLE))) {

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handler.onGroupClick(
                                v,
                                getAdapterPosition(),
                                itemView,
                                viewHolderController.getGroupIndexAt(getAdapterPosition()),
                                viewHolderController.getGroupItemAt(getAdapterPosition())
                        );
                    }
                });
                v.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        handler.onGroupLongClick(
                                v,
                                getAdapterPosition(),
                                itemView,
                                viewHolderController.getGroupIndexAt(getAdapterPosition()),
                                viewHolderController.getGroupItemAt(getAdapterPosition())
                        );
                        return true;
                    }
                });
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (viewHolderController != null) {
            viewHolderController.onGroupClick(this, getAdapterPosition());
        }
    }

    /**
     * Called when a view has been clicked and held.
     *
     * @param v The view that was clicked and held.
     * @return true if the callback consumed the long click, false otherwise.
     */
    @Override
    public boolean onLongClick(View v) {
        if (viewHolderController != null) {
            viewHolderController.onGroupLongClick(this, getAdapterPosition());
        }
        return true;
    }
}
