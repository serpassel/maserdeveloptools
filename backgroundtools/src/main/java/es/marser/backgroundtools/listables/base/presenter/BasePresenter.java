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

    /*Contexto de la aplicación [EN]  Context of the application*/
    protected Context context;

    /*Variable de la vista principal [EN]  Variable of the main view*/
    protected int viewLayout;

    /*Variable de argumentos [EN]  Variable arguments*/
    private Bundle arguments;

    private static String[] extras = new String[]{"basePresenter_viewLayout_key"};

    //CONSTRUCTORS__________________________________________________________
    public BasePresenter(@NonNull Context context, int viewLayout) {
        this.context = context;
        this.viewLayout = viewLayout;
    }

    //VARIABLES_______________________________________________________________

    @Override
    public int getViewLayout() {
        return viewLayout;
    }

    public void setViewLayout(int viewLayout) {
        this.viewLayout = viewLayout;
    }

    public Bundle getArguments() {
        return arguments;
    }

    public void setArguments(Bundle arguments) {
        this.arguments = arguments;
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

    //OPERATIVE_____________________________________

    /**
     * Operativa para evitar entradas de datos duplicadas, sobretodo en presentadores de carga de datos
     * Utilizar en aquellos métodos que tengan una entrada de argumentos y puedan haber sido introducidos por vía de argumentos globales
     * <p>
     * [EN]  Replace bundle with arguments
     * [EN]  Use in those methods that have an input of arguments and may have been introduced by way of global arguments
     *
     * @param bundle Argumentos de entrada del método
     *               [EN]  Arguments for the input of the method
     * @return Argumentos del método o argumentos globales en caso de entrada nula.
     * [EN]  Arguments for the input of the method
     */
    @Nullable
    protected Bundle replaceNullBundleWithArguments(@Nullable Bundle bundle) {
        if (bundle == null) {
            bundle = getArguments();
        }
        return bundle;
    }

    //SAVED AND RESTORED_____________________________________________________
    @Override
    public void onSaveInstanceState(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null && viewLayout > -1) {
            savedInstanceState.putInt(extras[0], viewLayout);
        }
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null && viewLayout < 0) {
            viewLayout = savedInstanceState.getInt(extras[0], -1);
        }
    }
}
