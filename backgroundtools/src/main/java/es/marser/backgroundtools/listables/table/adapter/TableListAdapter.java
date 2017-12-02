package es.marser.backgroundtools.listables.table.adapter;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.SparseIntArray;

import java.util.ArrayList;

import es.marser.backgroundtools.definition.Selectable;
import es.marser.backgroundtools.handlers.TouchableViewHandler;
import es.marser.backgroundtools.handlers.ViewItemHandler;
import es.marser.backgroundtools.listables.base.adapter.BaseListAdapterDecrep;
import es.marser.backgroundtools.listables.base.controller.AdapterController;
import es.marser.backgroundtools.listables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.listables.base.holder.ViewHolderType;
import es.marser.backgroundtools.listables.simple.holder.ViewHolderBinding;
import es.marser.backgroundtools.listables.table.holder.BodyViewHolderBinding;
import es.marser.backgroundtools.listables.table.holder.HeaderViewHolderBinding;
import es.marser.backgroundtools.listables.table.holder.TitleViewHolderBinding;

/**
 * @author sergio
 *         Created by sergio on 1/11/17.
 *         Base para construcción de listas con varias vistas
 *         <p>
 *         [EN]  Base for building lists with several views
 */

@SuppressWarnings({"SameReturnValue", "unused"})
public abstract class TableListAdapter<H extends Parcelable, B extends Parcelable>
        extends BaseListAdapterDecrep {

    /*Controlador de cabecera [EN]  Header controller*/
    public AdapterController<Selectable> tAdapterController;
    /*Controlador de cabecera [EN]  Header controller*/
    public AdapterController<H> hAdapterController;
    /*Controlador de cuerpo [EN]  Body controller*/
    public AdapterController<B> bAdapterController;

    /*Controlador de tipos de vista [EN]  View type controller*/
    protected ArrayList<Integer> types;

    private static String[] extras = new String[]{"types_extras"};

    /*Controlador de pulsaciones*/
    public ViewItemHandler<H> getHeadItemHandler() {
        return null;
    }

    public ViewItemHandler<B> getBodyItemHandler() {
        return null;
    }

    public ViewItemHandler<Selectable> getTitleItemHandler() {
        return null;
    }

    /*Control de pulsaciones en las vistas inferiores [EN]  Pulse control in the bottom views*/
    public TouchableViewHandler<H> getHeadTouchableViewHandler() {
        return null;
    }

    public TouchableViewHandler<B> getBodyTouchableViewHandler() {
        return null;
    }

    public TouchableViewHandler<Selectable> getTitleTouchableViewHandler() {
        return null;
    }

    public TableListAdapter() {


        tAdapterController = new AdapterController<>(ViewHolderType.TITLE.ordinal());
        tAdapterController.setChangedListener(this);
        tAdapterController.setViewItemHandler(getTitleItemHandler());

        hAdapterController = new AdapterController<>(ViewHolderType.HEAD.ordinal());
        hAdapterController.setChangedListener(this);
        hAdapterController.setViewItemHandler(getHeadItemHandler());

        bAdapterController = new AdapterController<>(ViewHolderType.BODY.ordinal());
        bAdapterController.setChangedListener(this);
        bAdapterController.setViewItemHandler(getBodyItemHandler());

        types = new ArrayList<>();

    }

    //SAVED AND RESTORE_____________________________________________________________

    @Override
    public void onSaveInstanceState(@Nullable Bundle savedInstanceState) {

        if(tAdapterController != null){
            tAdapterController.onSaveInstanceState(savedInstanceState);
        }

        if (hAdapterController != null) {
            hAdapterController.onSaveInstanceState(savedInstanceState);
        }

        if (bAdapterController != null) {
            bAdapterController.onSaveInstanceState(savedInstanceState);
        }

        if (savedInstanceState != null) {
            savedInstanceState.putIntegerArrayList(extras[0], types);
        }
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        if (tAdapterController != null) {
            if (savedInstanceState != null) {
                tAdapterController.onRestoreInstanceState(savedInstanceState);
            }
        }
        if (hAdapterController != null) {
            if (savedInstanceState != null) {
                hAdapterController.onRestoreInstanceState(savedInstanceState);
            }
        }

        if (bAdapterController != null) {
            if (savedInstanceState != null) {
                bAdapterController.onRestoreInstanceState(savedInstanceState);
            }
        }

        if (savedInstanceState != null) {
            types = savedInstanceState.getIntegerArrayList(extras[0]) != null
                    ? savedInstanceState.getIntegerArrayList(extras[0])
                    : new ArrayList<Integer>();
        }

        notifyDataSetChanged();
    }

    //METHODS FOR OVERWRITING__________________________________________________________________________
    public abstract int getHeadHolderLayout();

    public abstract int getBodyHolderLayout();

    public int getTitleHolderLayout(){
        return -1;
    }

    //OVERRRIDE ADAPTER_________________________________________________________________________________
    @Override
    protected SparseIntArray sparseHolderLayout() {
        SparseIntArray intArray = new SparseIntArray();
        intArray.put(ViewHolderType.TITLE.ordinal(), getTitleHolderLayout());
        intArray.put(ViewHolderType.HEAD.ordinal(), getHeadHolderLayout());
        intArray.put(ViewHolderType.BODY.ordinal(), getBodyHolderLayout());
        return intArray;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindVH(BaseViewHolder holder, int position) {
        ViewHolderType type = ViewHolderType.values()[holder.getIndexTypeView()];

        switch (type) {
            case TITLE:
                holder.bind(tAdapterController.getItemAt(position));
                ((ViewHolderBinding<Selectable>) holder).attachTouchableViewHandler(getTitleTouchableViewHandler());
                break;
            case HEAD:
                holder.bind(hAdapterController.getItemAt(position));
                ((ViewHolderBinding<H>) holder).attachTouchableViewHandler(getHeadTouchableViewHandler());
                break;
            case BODY:
                holder.bind(bAdapterController.getItemAt(position));
                ((ViewHolderBinding<B>) holder).attachTouchableViewHandler(getBodyTouchableViewHandler());
                break;
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewDataBinding dataBinding, int viewType) {
        ViewHolderType type = ViewHolderType.values()[viewType];
        switch (type) {
            case TITLE:
                return new TitleViewHolderBinding(dataBinding, tAdapterController);
            case HEAD:
                return new HeaderViewHolderBinding<>(dataBinding, hAdapterController);
            case BODY:
                return new BodyViewHolderBinding<>(dataBinding, bAdapterController);
            default:
                throw new ClassCastException("Undefined view es");
        }
    }

    @Override
    public int getItemCount() {
        return types.size();
    }

    @Override
    public int getItemViewType(int position) {
        return types.get(position);
    }

    @Override
    public int flatPos(int indexPos, int viewType) {
        int count = -1;

        for (int i = 0; i <= types.lastIndexOf(viewType); ++i) {

            if (types.get(i) == viewType) {
                ++count;
            }
            if (count == indexPos && count > -1) {
                return i;
            }
        }
        /*Lanzar un error si no se localiza una vista para la posición*/
        throw new ClassCastException("Type of view does not match");
    }

    @Override
    public int indexPos(int flaPos, int viewType) {

        int count = -1;
        for(int i = 0; i<= flaPos;++i){
            if(types.get(i) == viewType){
                ++count;
            }
        }

        if(count > -1){
            return count;
        }
          /*Lanzar un error si no se localiza una vista para la posición*/
        throw new ClassCastException("Type of view does not match");
    }

    //NOTIFICATION_________________________________________________________________________

    @Override
    public void notifyItemRemoved(int index, int viewType) {
        super.notifyItemRemoved(index, viewType);
        types.remove(flatPos(index, viewType));
    }

    @Override
    public void notifyItemInserted(int index, int count, int viewType) {
        int pos = index == count - 1 ? getItemCount() : flatPos(index, viewType);
        types.add(pos, viewType);
          /*Notificar al adapter después de la inserción [EN]  Notify the adapter after insertion*/
        super.notifyItemInserted(index, count, viewType);
    }

    @Override
    public void notifyDataAdd(int count, int viewType) {

        /*Añadir n veces el tipo de vista [EN]  Add n times the type of view*/
        for (int i = 0; i < count; ++i) {
            types.add(viewType);
        }

       /*Notificar al adapter después de la inserción [EN]  Notify the adapter after insertion*/
        super.notifyDataAdd(count, viewType);
    }

    @Override
    public void notifyDataRemoved(int count, int viewType) {
        /*Notificar al adapter previa eliminación [EN]  Notify adapter after elimination*/
        super.notifyDataRemoved(count, viewType);

        /*Eliminar de la lista de tipos [EN]  Remove from the list of types*/
        for (int i = 0; i <= types.lastIndexOf(viewType); ++i) {
            if (types.get(i) == viewType) {
                types.remove(i);
            }
        }
    }
}
