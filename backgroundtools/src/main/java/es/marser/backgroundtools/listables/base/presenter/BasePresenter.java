package es.marser.backgroundtools.listables.base.presenter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import es.marser.backgroundtools.bindingadapters.BinderContainer;

/**
 * @author sergio
 *         Created by sergio on 29/11/17.
 *         Presentador para listas sencillas
 *         <p>
 *         [EN]  Presenter for simple lists
 */

@SuppressWarnings("unused")
public abstract class BasePresenter
        implements LinkedPresenter {

    protected Context context;

    //CONSTRUCTORS__________________________________________________________
    public BasePresenter(@NonNull Context context) {
        this.context = context;
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

    //SAVED AND RESTORED_____________________________________________________

    @Override
    public void onSaveInstanceState(@Nullable Bundle savedInstanceState) {

    }
 @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {

    }
}
