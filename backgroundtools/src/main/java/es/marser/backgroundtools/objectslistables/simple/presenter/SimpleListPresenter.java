package es.marser.backgroundtools.objectslistables.simple.presenter;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;

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
        implements AdapterPresenter {

    protected Context context;
    protected SimpleListModel<T> simpleListModel;

    public SimpleListPresenter(@NonNull Context context, @NonNull SimpleListModel<T> listModel) {
        this.context = context;
        this.simpleListModel = listModel;
    }

    //VARIABLES_______________________________________________________________

    public SimpleListModel<T> getListModel() {
        return this.simpleListModel;
    }

    public void setListModel(@NonNull SimpleListModel<T> listModel) {
        this.simpleListModel = listModel;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
