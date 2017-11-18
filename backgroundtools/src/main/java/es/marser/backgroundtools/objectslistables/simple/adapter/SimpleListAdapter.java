package es.marser.backgroundtools.objectslistables.simple.adapter;

import android.databinding.ViewDataBinding;
import android.os.Parcelable;
import android.util.SparseIntArray;

import es.marser.backgroundtools.handlers.TouchableViewHandler;
import es.marser.backgroundtools.objectslistables.base.adapter.BaseListAdapter;
import es.marser.backgroundtools.objectslistables.base.holder.ViewHolderType;
import es.marser.backgroundtools.objectslistables.simple.holder.ViewHolderBinding;

/**
 * @author sergio
 *         Created by sergio on 1/11/17.
 */

public abstract class SimpleListAdapter<T extends Parcelable> extends BaseListAdapter<T, ViewHolderBinding<T>> {

    /**
     * Vista del item
     * <p>
     * [EN]  Item view
     *
     * @return variable de vista de los elementos [EN]  variable view of the elements
     */
    protected abstract int getHolderLayout();

    /**
     * Variable de oyente para pulsaciones de las vistas anidadas a la vista raiz del elemento
     * <p>
     * [EN]  Listener variable for clicking nested views to the root view of the item
     *
     * @return Variable de oyente de tipo {@link TouchableViewHandler}
     */
    public TouchableViewHandler<T> getTouchableViewHandler() {
        return null;
    }

    //OVERRIDE SUPERCLASS______________________________________________________________________________
    @Override
    protected SparseIntArray sparseHolderLayout() {
        SparseIntArray intArray = new SparseIntArray();
        intArray.put(ViewHolderType.SIMPLE.ordinal(), getHolderLayout());
        return intArray;
    }

    @Override
    public void onBindVH(ViewHolderBinding<T> holder, int position) {
        /*Introducir la variable de modelo de datos [EN]  Enter the data model variable*/
        holder.bind(globalController.getItemAt(position));
        /*Manejador de eventos de las subvistas  [EN]  Event manager of subviews*/
        holder.attachTouchableViewHandler(getTouchableViewHandler());
    }

    @Override
    public ViewHolderBinding<T> onCreateViewHolder(ViewDataBinding dataBinding, int viewType) {
        return new ViewHolderBinding<>(dataBinding, globalController);
    }
}
