package es.marser.backgroundtools.listables.simple.presenter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import es.marser.backgroundtools.bindingadapters.BinderContainer;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.TouchableViewHandler;
import es.marser.backgroundtools.handlers.ViewItemHandler;
import es.marser.backgroundtools.listables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.listables.base.presenter.AdapterPresenter;
import es.marser.backgroundtools.listables.simple.model.SimpleAdapterModel;

/**
 * @author sergio
 *         Created by sergio on 29/11/17.
 *         Presentador para listas sencillas
 *         <p>
 *         [EN]  Presenter for simple lists
 */

@SuppressWarnings("unused")
public abstract class SimpleListPresenter<T extends Parcelable, SLM extends SimpleAdapterModel<T>>
        implements AdapterPresenter, ViewItemHandler<T>, TouchableViewHandler<T> {

    protected Context context;
    protected SLM simpleListModel;

    //CONSTRUCTORS__________________________________________________________
    public SimpleListPresenter(@NonNull Context context) {
        this.context = context;
    }

    public SimpleListPresenter(@NonNull Context context, @NonNull SLM listModel) {
        this(context);
        setListModel(listModel);
    }

    //VARIABLES_______________________________________________________________
    public SLM getListModel() {
        return this.simpleListModel;
    }

    public void setListModel(@NonNull SLM listModel) {
        this.simpleListModel = listModel;
        this.simpleListModel.setViewItemHandler(this);
        this.simpleListModel.setTouchableViewHandler(this);
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

    //VIEWITEMHANDLER_______________________________________________________
    /**
     * Pulsación corta sobre vista del elemento
     * <p>
     * [EN]  Short press on element view
     *
     * @param holder   vista reciclable
     * @param item     Objeto de datos
     * @param position posición de datos
     * @param mode     modo de pulsación en el adapter
     */
    @Override
    public void onClickItem(BaseViewHolder<T> holder, T item, int position, ListExtra mode) {

    }

    /**
     * Pulsación larga sobre vista del elemento
     * <p>
     * [EN]  Long press on element view
     *
     * @param holder   vista reciclable
     * @param item     Objeto de datos
     * @param position posición de datos
     * @param mode     modo de pulsación en el adapter
     * @return devolver true si está activado
     */
    @Override
    public boolean onLongClickItem(BaseViewHolder<T> holder, T item, int position, ListExtra mode) {
        return false;
    }

    //TOUCHABLEVIEWHANDLER_________________________________________________
    /**
     * Manejador de eventos de pulsación sencilla en elementos pulsables
     * <p>
     * [EN]  Single-pulse event handler for push-button elements
     * TAG @string/INCLUDE_ITEM_ACTIONS
     *
     * @param view     Vista que inicia la acción [EN]  View that initiates the action
     * @param position posición en el adpater [EN]  position in the adpater
     * @param item     objeto de datos [EN]  data object
     * @param root     Vista grupal [EN]  Group view
     */
    @Override
    public void onClick(View view, int position, T item, View root) {

    }

    /**
     * Manejador de eventos de pulsación prolongada en elementos pulsables
     * <p>
     * [EN]  Long-pulsed event handler on pushbutton elements
     * <p>
     * TAG @string/INCLUDE_ITEM_ACTIONS
     *
     * @param view     Vista que inicia la acción [EN]  View that initiates the action
     * @param position posición en el adpater [EN]  position in the adpater
     * @param item     objeto de datos [EN]  data object
     * @param root     Vista grupal [EN]  Group view
     */
    @Override
    public boolean onLongClick(View view, int position, T item, View root) {
        return false;
    }

    //SAVED AND RESTORE______________________________________________________
    @Override
    public void onSaveInstanceState(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {

    }
}
