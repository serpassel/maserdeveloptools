package es.marser.backgroundtools.listables.base.holder;

import android.databinding.ViewDataBinding;
import android.os.Parcelable;
import android.view.View;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.handlers.ComplexTouchabeViewHandler;
import es.marser.backgroundtools.listables.complex.controller.ComplexViewHolderController;
import es.marser.backgroundtools.listables.complex.models.ExpandableGroup;

/**
 * @author sergio
 *         Created by sergio on 24/10/17.
 */

@SuppressWarnings("unused")
public abstract class BaseComplexViewHolder<X extends ExpandableGroup<T>, T extends Parcelable>
        extends BaseViewHolder<T> {

    /*Variables de control [EN]  Control variables*/
    protected ComplexViewHolderController<X, T> viewHolderController;

    public BaseComplexViewHolder(ViewDataBinding itemViewBindable, ComplexViewHolderController<X,T> viewHolderController) {
        super(itemViewBindable);
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
}
