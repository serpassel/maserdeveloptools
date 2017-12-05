package es.marser.backgroundtools.listables.table.presenter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import es.marser.backgroundtools.bindingadapters.BinderContainer;
import es.marser.backgroundtools.definition.Selectable;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.TouchableViewHandler;
import es.marser.backgroundtools.handlers.ViewItemHandler;
import es.marser.backgroundtools.listables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.listables.base.presenter.BasePresenter;
import es.marser.backgroundtools.listables.table.model.TableAdapterModel;

/**
 * @author sergio
 *         Created by sergio on 29/11/17.
 *         Presentador para tablas
 *         <p>
 *         [EN]  Presenter for tables
 */

@SuppressWarnings("unused")
public abstract class TableListPresenter<H extends Parcelable,
        B extends Parcelable,
        TAM extends TableAdapterModel<H, B>>
        extends BasePresenter<TAM> {

    //CONSTRUCTORS__________________________________________________________
    public TableListPresenter(@NonNull Context context) {
        super(context);
    }

    public TableListPresenter(@NonNull Context context, @NonNull TAM listModel) {
        super(context, listModel);
    }

    //OVERRIDE BASE__________________________________________________________
    @Override
    public void setListModel(@NonNull TAM listModel) {
        listModel.setTitleTouchableViewHandler(getTitleTouchableViewHandler());
        listModel.setHeadTouchableViewHandler(getHeadTouchableViewHandler());
        listModel.setBodyTouchableViewHandler(getBodyTouchableViewHandler());

        listModel.setTitleViewItemHandler(getTitleItemHandler());
        listModel.setHeadViewItemHandler(getHeadItemHandler());
        listModel.setBodyViewItemHandler(getBodyItemHandler());
        super.setListModel(listModel);

    }

    /**
     * Indicador del conmienzo de la vinculación de vistas {@link ViewDataBinding}
     * <p>
     * [EN]  Join linking view indicator
     *
     * @param binderContainer Objeto de enlace de vistas [EN]  View link object
     */
    @Override
    public void onBindObjects(@NonNull BinderContainer binderContainer) {

    }

    //PULSERS______________________________________________________
    /* @{link ViewItemHandler*/
     /*Controlador de pulsaciones*/
    //TITLE
    private ViewItemHandler<Selectable> getTitleItemHandler() {
        return new ViewItemHandler<Selectable>() {
            @Override
            public void onClickItem(BaseViewHolder<Selectable> holder, Selectable item, int position, ListExtra mode) {
                TableListPresenter.this.onClickTitleItem(holder, item, position, mode);
            }

            @Override
            public boolean onLongClickItem(BaseViewHolder<Selectable> holder, Selectable item, int position, ListExtra mode) {
                return TableListPresenter.this.onLongClickTitleItem(holder, item, position, mode);
            }
        };
    }

    @SuppressWarnings("EmptyMethod")
    public void onClickTitleItem(BaseViewHolder<Selectable> holder, Selectable item, int position, ListExtra mode) {
    }

    public boolean onLongClickTitleItem(BaseViewHolder<Selectable> holder, Selectable item, int position, ListExtra mode) {
        return true;
    }

    //HEAD
    private ViewItemHandler<H> getHeadItemHandler() {
        return new ViewItemHandler<H>() {
            @Override
            public void onClickItem(BaseViewHolder<H> holder, H item, int position, ListExtra mode) {
                TableListPresenter.this.onClickHeadItem(holder, item, position, mode);
            }

            @Override
            public boolean onLongClickItem(BaseViewHolder<H> holder, H item, int position, ListExtra mode) {
                return TableListPresenter.this.onLongClickHeadItem(holder, item, position, mode);
            }
        };
    }

    @SuppressWarnings("EmptyMethod")
    public void onClickHeadItem(BaseViewHolder<H> holder, H item, int position, ListExtra mode) {
    }

    public boolean onLongClickHeadItem(BaseViewHolder<H> holder, H item, int position, ListExtra mode) {
        return true;
    }

    //BODY
    private ViewItemHandler<B> getBodyItemHandler() {
        return new ViewItemHandler<B>() {
            @Override
            public void onClickItem(BaseViewHolder<B> holder, B item, int position, ListExtra mode) {
                TableListPresenter.this.onClickBodyItem(holder, item, position, mode);
            }

            @Override
            public boolean onLongClickItem(BaseViewHolder<B> holder, B item, int position, ListExtra mode) {
                return TableListPresenter.this.onLongClickBodyItem(holder, item, position, mode);
            }
        };
    }

    @SuppressWarnings("EmptyMethod")
    public void onClickBodyItem(BaseViewHolder<B> holder, B item, int position, ListExtra mode) {
    }

    public boolean onLongClickBodyItem(BaseViewHolder<B> holder, B item, int position, ListExtra mode) {
        return true;
    }

    /*@{link TouchableViewHandler}*/

        /*Control de pulsaciones en las vistas inferiores [EN]  Pulse control in the bottom views*/

    //TITLE
    private TouchableViewHandler<Selectable> getTitleTouchableViewHandler() {
        return new TouchableViewHandler<Selectable>() {
            @Override
            public void onClick(View view, int position, Selectable item, View root) {
                TableListPresenter.this.onClickTitle(view, position, item, root);
            }

            @Override
            public boolean onLongClick(View view, int position, Selectable item, View root) {
                return TableListPresenter.this.onLongClickTitle(view, position, item, root);
            }
        };
    }

    @SuppressWarnings("EmptyMethod")
    public void onClickTitle(View view, int position, Selectable item, View root) {

    }

    public boolean onLongClickTitle(View view, int position, Selectable item, View root) {
        return true;
    }

    //HEAD
    private TouchableViewHandler<H> getHeadTouchableViewHandler() {
        return new TouchableViewHandler<H>() {
            @Override
            public void onClick(View view, int position, H item, View root) {
                TableListPresenter.this.onClickHead(view, position, item, root);
            }

            @Override
            public boolean onLongClick(View view, int position, H item, View root) {
                return TableListPresenter.this.onLongClickHead(view, position, item, root);
            }
        };
    }

    @SuppressWarnings("EmptyMethod")
    public void onClickHead(View view, int position, H item, View root) {

    }

    public boolean onLongClickHead(View view, int position, H item, View root) {
        return true;
    }

    //BODY
    private TouchableViewHandler<B> getBodyTouchableViewHandler() {
        return new TouchableViewHandler<B>() {
            @Override
            public void onClick(View view, int position, B item, View root) {
                TableListPresenter.this.onClickBody(view, position, item, root);
            }

            @Override
            public boolean onLongClick(View view, int position, B item, View root) {
                return TableListPresenter.this.onLongClickBody(view, position, item, root);
            }
        };
    }

    @SuppressWarnings("EmptyMethod")
    public void onClickBody(View view, int position, B item, View root) {

    }

    public boolean onLongClickBody(View view, int position, B item, View root) {
        return true;
    }

    //SAVED AND RESTORE____________________________________________________
    @Override
    public void onSaveInstanceState(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {

    }
}
