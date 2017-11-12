package es.marser.backgroundtools.objectslistables.table.adapter;

import android.databinding.ViewDataBinding;
import android.os.Parcelable;
import android.util.SparseIntArray;

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
