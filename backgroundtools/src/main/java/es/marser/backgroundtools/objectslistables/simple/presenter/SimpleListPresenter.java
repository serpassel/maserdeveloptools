package es.marser.backgroundtools.objectslistables.simple.presenter;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.view.View;

import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.TouchableViewHandler;
import es.marser.backgroundtools.handlers.ViewItemHandler;
import es.marser.backgroundtools.objectslistables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.objectslistables.base.presenter.AdapterPresenter;
import es.marser.backgroundtools.objectslistables.simple.model.SimpleListModel;

/**
 * @author sergio
 *         Created by sergio on 29/11/17.
 *         Presentador para listas sencillas
 *         <p>
 *         [EN]  Presenter for simple lists
 */

@SuppressWarnings("unused")
public abstract class SimpleListPresenter<T extends Parcelable>
        implements AdapterPresenter, TouchableViewHandler<T>, ViewItemHandler<T> {

    protected Context context;
    protected SimpleListModel<T> simpleListModel;

    public SimpleListPresenter(@NonNull Context context, @NonNull SimpleListModel<T> listModel) {
        this.context = context;
        this.simpleListModel = listModel;
        this.simpleListModel.setTouchableViewHandler(this);
        this.simpleListModel.setViewItemHandler(this);
    }

    //VARIABLES_______________________________________________________________

    public SimpleListModel<T> getListModel() {
        return this.simpleListModel;
    }

    public void setListModel(@NonNull SimpleListModel<T> listModel) {
        this.simpleListModel = listModel;
        this.simpleListModel.setTouchableViewHandler(this);
        this.simpleListModel.setViewItemHandler(this);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    //CLICK EVENTS_____________________________________________________________

    @Override
    public void onClickItem(BaseViewHolder<T> holder, T item, int position, ListExtra mode) {

    }

    @Override
    public boolean onLongClickItem(BaseViewHolder<T> holder, T item, int position, ListExtra mode) {
        return false;
    }

    @Override
    public void onClick(View view, int position, T item, View root) {

    }

    @Override
    public boolean onLongClick(View view, int position, T item, View root) {
        return false;
    }
}
