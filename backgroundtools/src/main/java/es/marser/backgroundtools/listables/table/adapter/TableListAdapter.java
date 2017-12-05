package es.marser.backgroundtools.listables.table.adapter;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import es.marser.backgroundtools.definition.Selectable;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.TouchableViewHandler;
import es.marser.backgroundtools.handlers.ViewItemHandler;
import es.marser.backgroundtools.listables.base.adapter.BaseListAdapter;
import es.marser.backgroundtools.listables.base.controller.AdapterController;
import es.marser.backgroundtools.listables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.listables.base.holder.ViewHolderType;
import es.marser.backgroundtools.listables.base.model.ExpandItemsController;
import es.marser.backgroundtools.listables.base.model.ExpandItemsManager;
import es.marser.backgroundtools.listables.base.model.SelectedsModel;
import es.marser.backgroundtools.listables.base.model.SelectedsModelManager;
import es.marser.backgroundtools.listables.base.model.SelectionItemsController;
import es.marser.backgroundtools.listables.base.model.SelectionItemsManager;
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
public class TableListAdapter<H extends Parcelable, B extends Parcelable>
        extends BaseListAdapter implements
        SelectionItemsManager,
        ExpandItemsManager,
        SelectedsModelManager<B> {

    /*Eventos sobre vistas menores [EN]  Events on minor views*/
    private TouchableViewHandler<Selectable> titleTouchableViewHandler;
    private TouchableViewHandler<H> headTouchableViewHandler;
    private TouchableViewHandler<B> bodyTouchableViewHandler;

    /*Controladores de selección y expansión [EN]  Selection and expansion controllers*/
    protected AdapterController<Selectable> titleAdapterController;
    protected AdapterController<H> headAdapterController;
    protected AdapterController<B> bodyAdapterController;

    /*Controlador de tipos de vista [EN]  View type controller*/
    protected ArrayList<Integer> types;

    private static String[] extras = new String[]{"types_extras", "tholderlayout_key", "hholderlayout_key", "bholderlayout_key"};

    public TableListAdapter() {
        titleAdapterController = new AdapterController<>(ViewHolderType.TITLE.ordinal());
        titleAdapterController.setChangedListener(this);

        headAdapterController = new AdapterController<>(ViewHolderType.HEAD.ordinal());
        headAdapterController.setChangedListener(this);

        bodyAdapterController = new AdapterController<>(ViewHolderType.BODY.ordinal());
        bodyAdapterController.setChangedListener(this);

        types = new ArrayList<>();
    }

    public TableListAdapter(int headHolderLayout, int bodyHolderLayout) {
        this(-1, headHolderLayout, bodyHolderLayout);
    }

    public TableListAdapter(int titleHolderLayout, int headHolderLayout, int bodyHolderLayout) {
        this();
        setHolderLayout(ViewHolderType.TITLE, titleHolderLayout);
        setHolderLayout(ViewHolderType.HEAD, headHolderLayout);
        setHolderLayout(ViewHolderType.BODY, bodyHolderLayout);
    }

    //PRESENTERS_________________________________________________________________________
    public TouchableViewHandler<Selectable> getTitleTouchableViewHandler() {
        return titleTouchableViewHandler;
    }

    public void setTitleTouchableViewHandler(TouchableViewHandler<Selectable> tTouchableViewHandler) {
        this.titleTouchableViewHandler = tTouchableViewHandler;
    }

    public void removedTitleTouchableViewHandler() {
        setTitleTouchableViewHandler(null);
    }

    public TouchableViewHandler<H> getHeadTouchableViewHandler() {
        return headTouchableViewHandler;
    }

    public void setHeadTouchableViewHandler(TouchableViewHandler<H> hTouchableViewHandler) {
        this.headTouchableViewHandler = hTouchableViewHandler;
    }

    public void removedHeadTouchableViewHandler() {
        setHeadTouchableViewHandler(null);
    }

    public TouchableViewHandler<B> getBodyTouchableViewHandler() {
        return bodyTouchableViewHandler;
    }

    public void setBodyTouchableViewHandler(TouchableViewHandler<B> bTouchableViewHandler) {
        this.bodyTouchableViewHandler = bTouchableViewHandler;
    }

    public void removedBodyTouchableViewHandler() {
        setBodyTouchableViewHandler(null);
    }

    public AdapterController<Selectable> getTitleAdapterController() {
        return titleAdapterController;
    }

    public void setTitleAdapterController(AdapterController<Selectable> titleAdapterController) {
        this.titleAdapterController = titleAdapterController;
    }

    public AdapterController<H> getHeadAdapterController() {
        return headAdapterController;
    }

    public void setHeadAdapterController(AdapterController<H> headAdapterController) {
        this.headAdapterController = headAdapterController;
    }

    public AdapterController<B> getBodyAdapterController() {
        return bodyAdapterController;
    }

    public void setBodyAdapterController(AdapterController<B> bodyAdapterController) {
        this.bodyAdapterController = bodyAdapterController;
    }

    public void setTitleViewItemHandler(ViewItemHandler<Selectable> viewItemHandler) {
        if (titleAdapterController != null) {
            titleAdapterController.setViewItemHandler(viewItemHandler);
        }
    }

    public void removeTitleViewItemHandler() {
        if (titleAdapterController != null) {
            titleAdapterController.removeViewItemHandler();
        }
    }

    public void setHeadViewItemHandler(ViewItemHandler<H> viewItemHandler) {
        if (headAdapterController != null) {
            headAdapterController.setViewItemHandler(viewItemHandler);
        }
    }

    public void removeHeadViewItemHandler() {
        if (headAdapterController != null) {
            headAdapterController.removeViewItemHandler();
        }
    }

    public void setBodyViewItemHandler(ViewItemHandler<B> viewItemHandler) {
        if (bodyAdapterController != null) {
            bodyAdapterController.setViewItemHandler(viewItemHandler);
        }
    }

    public void removeBodyViewItemHandler() {
        if (bodyAdapterController != null) {
            bodyAdapterController.removeViewItemHandler();
        }
    }

    //OVERRRIDE ADAPTER_________________________________________________________________________________
    @SuppressWarnings("unchecked")
    @Override
    public void onBindVH(BaseViewHolder holder, int position) {
        ViewHolderType type = ViewHolderType.values()[holder.getIndexTypeView()];

        switch (type) {
            case TITLE:
                holder.bind(titleAdapterController.getItemAt(position));
                ((ViewHolderBinding<Selectable>) holder).attachTouchableViewHandler(getTitleTouchableViewHandler());
                break;
            case HEAD:
                holder.bind(headAdapterController.getItemAt(position));
                ((ViewHolderBinding<H>) holder).attachTouchableViewHandler(getHeadTouchableViewHandler());
                break;
            case BODY:
                holder.bind(bodyAdapterController.getItemAt(position));
                ((ViewHolderBinding<B>) holder).attachTouchableViewHandler(getBodyTouchableViewHandler());
                break;
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewDataBinding dataBinding, int viewType) {
        ViewHolderType type = ViewHolderType.values()[viewType];
        switch (type) {
            case TITLE:
                return new TitleViewHolderBinding(dataBinding, titleAdapterController);
            case HEAD:
                return new HeaderViewHolderBinding<>(dataBinding, headAdapterController);
            case BODY:
                return new BodyViewHolderBinding<>(dataBinding, bodyAdapterController);
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
        for (int i = 0; i <= flaPos; ++i) {
            if (types.get(i) == viewType) {
                ++count;
            }
        }

        if (count > -1) {
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

    //SAVED AND RESTORE_____________________________________________________________
    @Override
    public void onSaveInstanceState(@Nullable Bundle savedInstanceState) {

        if (titleAdapterController != null) {
            titleAdapterController.onSaveInstanceState(savedInstanceState);
        }

        if (headAdapterController != null) {
            headAdapterController.onSaveInstanceState(savedInstanceState);
        }

        if (bodyAdapterController != null) {
            bodyAdapterController.onSaveInstanceState(savedInstanceState);
        }

        if (savedInstanceState != null) {
            savedInstanceState.putIntegerArrayList(extras[0], types);
            savedInstanceState.putInt(extras[1], sparseHolderLayout.get(ViewHolderType.TITLE.ordinal()));
            savedInstanceState.putInt(extras[2], sparseHolderLayout.get(ViewHolderType.HEAD.ordinal()));
            savedInstanceState.putInt(extras[3], sparseHolderLayout.get(ViewHolderType.BODY.ordinal()));
        }
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        if (titleAdapterController != null) {
            if (savedInstanceState != null) {
                titleAdapterController.onRestoreInstanceState(savedInstanceState);
            }
        }
        if (headAdapterController != null) {
            if (savedInstanceState != null) {
                headAdapterController.onRestoreInstanceState(savedInstanceState);
            }
        }

        if (bodyAdapterController != null) {
            if (savedInstanceState != null) {
                bodyAdapterController.onRestoreInstanceState(savedInstanceState);
            }
        }

        if (savedInstanceState != null) {
            types = savedInstanceState.getIntegerArrayList(extras[0]) != null
                    ? savedInstanceState.getIntegerArrayList(extras[0])
                    : new ArrayList<Integer>();

            setHolderLayout(ViewHolderType.TITLE, savedInstanceState.getInt(extras[1], -1));
            setHolderLayout(ViewHolderType.HEAD, savedInstanceState.getInt(extras[2], -1));
            setHolderLayout(ViewHolderType.BODY, savedInstanceState.getInt(extras[3], -1));
        }

        notifyDataSetChanged();
    }

    //SELECTIONABLE_____________________________________________________________
    @Nullable
    @Override
    public ListExtra getSelectionmode(@Nullable Integer viewtype) {
        if (viewtype == null) {
            return bodyAdapterController != null ? bodyAdapterController.getSelectionmode(null) : null;
        }

        ViewHolderType type = ViewHolderType.values()[viewtype];
        switch (type) {
            case TITLE:
                return titleAdapterController != null ? titleAdapterController.getSelectionmode(viewtype) : null;
            case HEAD:
                return headAdapterController != null ? headAdapterController.getSelectionmode(viewtype) : null;
            case BODY:
            default:
                return bodyAdapterController != null ? bodyAdapterController.getSelectionmode(viewtype) : null;

        }
    }

    @Override
    public void setSelectionmode(@Nullable Integer viewType, @NonNull ListExtra selectionmode) {

        if (viewType == null) {
            bodyAdapterController.setSelectionmode(null, selectionmode);
        } else {

            ViewHolderType type = ViewHolderType.values()[viewType];
            switch (type) {
                case TITLE:
                    if (titleAdapterController != null) {
                        titleAdapterController.setSelectionmode(viewType, selectionmode);
                    }
                    break;
                case HEAD:
                    if (headAdapterController != null) {
                        headAdapterController.setSelectionmode(viewType, selectionmode);
                    }
                    break;
                case BODY:
                default:
                    if (bodyAdapterController != null) {
                        bodyAdapterController.setSelectionmode(viewType, selectionmode);
                    }
                    break;
            }
        }
    }

    //MANAGERS___________________________________________________________________
    /**
     * @return Devuelve el objeto de control de expansión de objetos [EN]  Returns the object expansion control object
     */
    @Nullable
    @Override
    public ExpandItemsController getExpandItemsController() {
        return bodyAdapterController != null ? bodyAdapterController.getExpandItemsController() : null;
    }

    /**
     * @return Devuelve la controladora de selección [EN]  Returns the selection controller
     */
    @Nullable
    @Override
    public SelectionItemsController getSelectionItemsController() {
        return bodyAdapterController != null ? bodyAdapterController.getSelectionItemsController() : null;
    }

    //SELECTION BODY MODEL________________________________________________________
    /**
     * Devuelve el contralodor de elementos seleccionados
     * <p>
     * [EN]  Returns the selected elements controller
     *
     * @return Devuelve el contralodor de elementos seleccionados [EN]  Returns the selected elements controller
     */
    @Nullable
    @Override
    public SelectedsModel<B> getSelectedsModel() {
        return bodyAdapterController;
    }
}
