package es.marser.backgroundtools.objectslistables.table.adapter;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import es.marser.backgroundtools.handlers.TouchableViewHandler;
import es.marser.backgroundtools.handlers.ViewItemHandler;
import es.marser.backgroundtools.objectslistables.base.adapter.BaseListAdapter;
import es.marser.backgroundtools.objectslistables.base.controller.GlobalController;
import es.marser.backgroundtools.objectslistables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.objectslistables.base.holder.ViewHolderType;
import es.marser.backgroundtools.objectslistables.table.holder.BodyViewHolderBinding;
import es.marser.backgroundtools.objectslistables.table.holder.HeaderViewHolderBinding;
import es.marser.backgroundtools.objectslistables.simple.holder.ViewHolderBinding;

/**
 * @author sergio
 *         Created by sergio on 1/11/17.
 *         Base para construcción de listas con varias vistas
 *         <p>
 *         [EN]  Base for building lists with several views
 */

@SuppressWarnings({"SameReturnValue", "unused"})
public abstract class TableListAdapter<H extends Parcelable, B extends Parcelable>
        extends BaseListAdapter {

    /*Controlador de cabecera [EN]  Header controller*/
    public GlobalController<H> hGlobalController;
    /*Controlador de cuerpo [EN]  Body controller*/
    public GlobalController<B> bGlobalController;

    protected List<Integer> types;

    /*Controlador de pulsaciones*/
    public ViewItemHandler<H> getHeadItemHandler() {
        return null;
    }

    public ViewItemHandler<B> getBodyItemHandler() {
        return null;
    }

    /*Control de pulsaciones en las vistas inferiores [EN]  Pulse control in the bottom views*/
    public TouchableViewHandler<H> getHeadTouchableViewHandler() {
        return null;
    }

    public TouchableViewHandler<B> getBodyTouchableViewHandler() {
        return null;
    }

    public TableListAdapter() {

        hGlobalController = new GlobalController<>(ViewHolderType.HEAD.ordinal());
        hGlobalController.setChangedListener(this);
        hGlobalController.setViewItemHandler(getHeadItemHandler());

        bGlobalController = new GlobalController<>(ViewHolderType.BODY.ordinal());
        bGlobalController.setChangedListener(this);
        bGlobalController.setViewItemHandler(getBodyItemHandler());


    }

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
        if (hGlobalController != null) {
            hGlobalController.onSaveInstanceState(savedInstanceState);
        }

        if (bGlobalController != null) {
            bGlobalController.onSaveInstanceState(savedInstanceState);
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
        if (hGlobalController != null) {
            if (savedInstanceState != null) {
                hGlobalController.onRestoreInstanceState(savedInstanceState);
            }
        }

        if (bGlobalController != null) {
            if (savedInstanceState != null) {
                bGlobalController.onRestoreInstanceState(savedInstanceState);
            }
        }

        notifyDataSetChanged();
    }




    //METHODS FOR OVERWRITING__________________________________________________________________________
    public abstract int getHeadHolderLayout();

    public abstract int getBodyHolderLayout();

    //OVERRRIDE ADAPTER_________________________________________________________________________________
    @Override
    protected SparseIntArray sparseHolderLayout() {
        SparseIntArray intArray = new SparseIntArray();
        intArray.put(ViewHolderType.HEAD.ordinal(), getHeadHolderLayout());
        intArray.put(ViewHolderType.BODY.ordinal(), getBodyHolderLayout());
        return intArray;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindVH(BaseViewHolder holder, int position) {
        ViewHolderType type = ViewHolderType.values()[holder.getIndexTypeView()];

        switch (type) {
            case HEAD:
                holder.bind(hGlobalController.getItemAt(position));
                ((ViewHolderBinding<H>)holder).attachTouchableViewHandler(getHeadTouchableViewHandler());
                break;
            case BODY:
                holder.bind(bGlobalController.getItemAt(position));
                ((ViewHolderBinding<B>)holder).attachTouchableViewHandler(getBodyTouchableViewHandler());
                break;
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewDataBinding dataBinding, int viewType) {
        ViewHolderType type = ViewHolderType.values()[viewType];
        switch (type) {
            case HEAD:
                return new HeaderViewHolderBinding<>(dataBinding, hGlobalController);
            case BODY:
                return new BodyViewHolderBinding<>(dataBinding, bGlobalController);
            default:
                throw new ClassCastException("Undefined view es");
        }
    }

    @Override
    public int getItemCount() {
        return hGlobalController.getItemCount() + bGlobalController.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
     if(position < hGlobalController.getItemCount()){
         return ViewHolderType.HEAD.ordinal();
     }
        return ViewHolderType.BODY.ordinal();
    }

    @Override
    public int flatPos(int indexPos, int viewType) {
        ViewHolderType type = ViewHolderType.values()[viewType];
        switch (type) {
            case HEAD:
                return indexPos;
            case BODY:
                return indexPos + hGlobalController.getItemCount();
            default:
                throw new ClassCastException("Type of view does not match");
        }
    }

    @Override
    public int indexPos(int flaPos, int viewType) {
        ViewHolderType type = ViewHolderType.values()[viewType];
        switch (type) {
            case HEAD:
                return flaPos;
            case BODY:
                return flaPos - hGlobalController.getItemCount();
            default:
                throw new ClassCastException("Type of view does not match");
        }
    }
}
