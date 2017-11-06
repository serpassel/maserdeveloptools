package es.marser.backgroundtools.objectslistables.complex.holder;

import android.databinding.ViewDataBinding;
import android.os.Parcelable;
import android.view.View;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.handlers.ComplexTouchabeViewHandler;
import es.marser.backgroundtools.objectslistables.base.holder.ViewHolderType;
import es.marser.backgroundtools.objectslistables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.objectslistables.complex.controller.ComplexViewHolderController;
import es.marser.backgroundtools.objectslistables.complex.models.ExpandableGroup;

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
public class ChildViewHolderBinding<X extends ExpandableGroup<T>, T extends Parcelable>
        extends BaseViewHolder<T> {


    /*Variables de control [EN]  Control variables*/
    protected ComplexViewHolderController<X, T> viewHolderController;

    @Override
    public int getIndexTypeView() {
        return ViewHolderType.CHILD.ordinal();
    }

    //CONSTRUCTORS____________________________________________________________________________________________
    public ChildViewHolderBinding(
            ViewDataBinding itemViewBindable,
            ComplexViewHolderController<X, T> viewHolderController) {
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
    public void attachTouchableViewHandler(final ComplexTouchabeViewHandler<X, T> handler) {


        for (View v : itemView.getTouchables()) {
            if (v.getTag() != null && v.getTag().equals(v.getContext().getResources().getString(R.string.TOUCHABLE_VIEW_ACTIONABLE))) {

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handler.onChildClick(
                                v,
                                getAdapterPosition(),
                                itemView,
                                viewHolderController.getGroupIndexAt(getAdapterPosition()),
                                viewHolderController.getGroupItemAt(getAdapterPosition()),
                                viewHolderController.getChildIndexAt(getAdapterPosition()),
                                viewHolderController.getChildItemAt(getAdapterPosition())
                        );
                    }
                });
                v.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        handler.onChildLongClick(
                                v,
                                getAdapterPosition(),
                                itemView,
                                viewHolderController.getGroupIndexAt(getAdapterPosition()),
                                viewHolderController.getGroupItemAt(getAdapterPosition()),
                                viewHolderController.getChildIndexAt(getAdapterPosition()),
                                viewHolderController.getChildItemAt(getAdapterPosition()));
                        return true;
                    }
                });
            }
        }
    }

    //ACTIONS_______________________________________________________________________________________________________

   @Override
    public void setSelected() {
        if (viewHolderController != null) {
            itemView.setSelected(viewHolderController.isChildSelected(getAdapterPosition()));
        }

        /*Modificar los selectores [EN]  We modify the selectors */
       if(selecttigger != null){
           selecttigger.setOnCheckedChangeListener(null);
           selecttigger.setChecked(itemView.isSelected());
           selecttigger.setOnCheckedChangeListener(this);
       }
    }

    @Override
    public void setExpanded() {

    }


    @Override
    public void onClick(View view) {
        if (viewHolderController != null) {
            viewHolderController.onChildClick(this, getAdapterPosition());
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
            viewHolderController.onChildLongClick(this, getAdapterPosition());
        }
        return true;
    }
}
