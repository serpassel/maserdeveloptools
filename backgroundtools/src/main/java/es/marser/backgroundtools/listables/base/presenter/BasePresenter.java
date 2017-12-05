package es.marser.backgroundtools.listables.base.presenter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;

import es.marser.backgroundtools.bindingadapters.BinderContainer;
import es.marser.backgroundtools.listables.base.adapter.BaseListAdapter;
import es.marser.backgroundtools.listables.base.model.BaseAdapterModel;

/**
 * @author sergio
 *         Created by sergio on 29/11/17.
 *         Presentador para listas sencillas
 *         <p>
 *         [EN]  Presenter for simple lists
 */

@SuppressWarnings("unused")
public abstract class BasePresenter<LM extends BaseAdapterModel<? extends BaseListAdapter>>
        implements AdapterPresenter {

    protected Context context;
    protected LM listModel;

    //CONSTRUCTORS__________________________________________________________
    public BasePresenter(@NonNull Context context) {
        this.context = context;
    }

    public BasePresenter(@NonNull Context context, @NonNull LM listModel) {
        this(context);
        setListModel(listModel);
    }

    //VARIABLES_______________________________________________________________
    public LM getListModel() {
        return this.listModel;
    }

    public void setListModel(@NonNull LM listModel) {
        this.listModel = listModel;
    }

    @NonNull
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
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
}
