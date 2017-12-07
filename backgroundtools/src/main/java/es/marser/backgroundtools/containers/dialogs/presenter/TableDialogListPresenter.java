package es.marser.backgroundtools.containers.dialogs.presenter;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.view.View;

import es.marser.backgroundtools.definition.Selectable;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.events.TouchableViewHandler;
import es.marser.backgroundtools.events.ViewItemHandler;
import es.marser.backgroundtools.listables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.listables.table.model.TableAdapterModel;

/**
 * @author sergio
 *         Created by sergio on 29/11/17.
 *         Presentador para tablas
 *         <p>
 *         [EN]  Presenter for tables
 */

@SuppressWarnings("unused")
public abstract class TableDialogListPresenter<H extends Parcelable,
        B extends Parcelable,
        TAM extends TableAdapterModel<H, B>>
        extends DialogListPresenter<TAM> {

    //CONSTRUCTORS__________________________________________________________
    public TableDialogListPresenter(@NonNull Context context, int viewLayout, TAM listmodel) {
        super(context, viewLayout, listmodel);
    }

    //OVERRIDE BASE__________________________________________________________
    public void setListModel(@NonNull TAM listmodel) {
        listmodel.setTitleTouchableViewHandler(getTitleTouchableViewHandler());
        listmodel.setHeadTouchableViewHandler(getHeadTouchableViewHandler());
        listmodel.setBodyTouchableViewHandler(getBodyTouchableViewHandler());
        listmodel.setTitleViewItemHandler(getTitleItemHandler());
        listmodel.setHeadViewItemHandler(getHeadItemHandler());
        listmodel.setBodyViewItemHandler(getBodyItemHandler());
        super.setListmodel(listmodel);
    }

    //PULSERS______________________________________________________
    /* @{link ViewItemHandler*/
     /*Controlador de pulsaciones*/
    //TITLE
    private ViewItemHandler<Selectable> getTitleItemHandler() {
        return new ViewItemHandler<Selectable>() {
            @Override
            public void onClickItem(BaseViewHolder<Selectable> holder, Selectable item, int position, ListExtra mode) {
                TableDialogListPresenter.this.onClickTitleItem(holder, item, position, mode);
            }

            @Override
            public boolean onLongClickItem(BaseViewHolder<Selectable> holder, Selectable item, int position, ListExtra mode) {
                return TableDialogListPresenter.this.onLongClickTitleItem(holder, item, position, mode);
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
                TableDialogListPresenter.this.onClickHeadItem(holder, item, position, mode);
            }

            @Override
            public boolean onLongClickItem(BaseViewHolder<H> holder, H item, int position, ListExtra mode) {
                return TableDialogListPresenter.this.onLongClickHeadItem(holder, item, position, mode);
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
                TableDialogListPresenter.this.onClickBodyItem(holder, item, position, mode);
            }

            @Override
            public boolean onLongClickItem(BaseViewHolder<B> holder, B item, int position, ListExtra mode) {
                return TableDialogListPresenter.this.onLongClickBodyItem(holder, item, position, mode);
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
                TableDialogListPresenter.this.onClickTitle(view, position, item, root);
            }

            @Override
            public boolean onLongClick(View view, int position, Selectable item, View root) {
                return TableDialogListPresenter.this.onLongClickTitle(view, position, item, root);
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
                TableDialogListPresenter.this.onClickHead(view, position, item, root);
            }

            @Override
            public boolean onLongClick(View view, int position, H item, View root) {
                return TableDialogListPresenter.this.onLongClickHead(view, position, item, root);
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
                TableDialogListPresenter.this.onClickBody(view, position, item, root);
            }

            @Override
            public boolean onLongClick(View view, int position, B item, View root) {
                return TableDialogListPresenter.this.onLongClickBody(view, position, item, root);
            }
        };
    }

    @SuppressWarnings("EmptyMethod")
    public void onClickBody(View view, int position, B item, View root) {

    }

    public boolean onLongClickBody(View view, int position, B item, View root) {
        return true;
    }

}
