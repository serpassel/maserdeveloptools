package es.marser.backgroundtools.widget.chooser.presenter;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.List;

import es.marser.backgroundtools.definition.Selectable;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.ViewHandler;
import es.marser.backgroundtools.handlers.WindowAction;
import es.marser.backgroundtools.objectslistables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.objectslistables.base.model.SelectionItemsController;
import es.marser.backgroundtools.objectslistables.simple.model.SimpleListModel;
import es.marser.backgroundtools.objectslistables.simple.presenter.SimpleListPresenter;

/**
 * @author sergio
 *         Created by sergio on 29/11/17.
 *         Presentador para selectores de Objetos
 *         <p>
 *         [EN]  Presenter for Object selectors
 */

public class ChooserPresenter<T extends Selectable>
        extends SimpleListPresenter<T, SimpleListModel<T>>
        implements ViewHandler<Boolean> {

    public static ObservableBoolean multiselect;
    protected boolean multiselect_flag;

    private WindowAction  windowAction;

    public ChooserPresenter(@NonNull Context context) {
        this(context, true);
    }

    public ChooserPresenter(@NonNull Context context, boolean multiselect_flag) {
        super(context);
        this.multiselect_flag = multiselect_flag;
        multiselect = new ObservableBoolean();
        windowAction = null;
    }

    public ChooserPresenter(@NonNull Context context, @NonNull SimpleListModel<T> listModel) {
        super(context, listModel);
        multiselect_flag = true;
        multiselect = new ObservableBoolean();
        windowAction = null;
    }



    public void setWindowAction(WindowAction windowAction) {
        this.windowAction = windowAction;
    }

    /**
     * Método para la carga de datos
     * <p>
     * [EN]  Method for data loading
     *
     * @param bundle Argumentos de carga de datos [EN]  Arguments of data loading
     */
    @Override
    public void load(@Nullable Bundle bundle) {

        if (bundle == null) {
            return;
        }

        final List<T> values = bundle.getParcelableArrayList(ListExtra.VALUES_EXTRA.name());
        final String preselect = bundle.getString(DialogExtras.FILTER_EXTRAS.name(), null);
        if (values != null) {
            if (preselect == null) {
                simpleListModel.addAll(values);
                return;
            }


            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    SelectionItemsController selectionItemsController = simpleListModel.getSelectionItemsController();
                    for (T t : values) {
                        simpleListModel.add(t);
                        if (selectionItemsController != null) {
                            selectionItemsController.inputSelected(simpleListModel.size() - 1, preselect.contains(t.preSelectValue()));
                        }
                    }
                    adjustMultiSelection();
                }
            });
        }
    }

    /**
     * Método para ajustar el estado del boón de multiselección
     * Anula los eventos de cambio de estado durante su ajuste
     * <p>
     * [EN]  Method to adjust the state of the multi-selection button
     * Cancels state change events during adjustment
     */
    protected void adjustMultiSelection() {
        //  Log.i(LOG_TAG.TAG, "Ajustado " + "/" + multiselect.get());
        multiselect_flag = false;
        boolean old = multiselect.get();
        SelectionItemsController selectionItemsController = simpleListModel.getSelectionItemsController();
        multiselect.set(selectionItemsController != null && simpleListModel.size() == selectionItemsController.getIdSelecteds().size());
        multiselect_flag = (old == multiselect.get());
    }


    @Override
    public void onClick(View view, Boolean isChecked) {
        if (multiselect_flag) {
            SelectionItemsController selectionItemsController = simpleListModel.getSelectionItemsController();
            if (isChecked) {
                if (selectionItemsController != null) {
                    selectionItemsController.selectedAll(simpleListModel.size());
                }
            } else {
                if (selectionItemsController != null) {
                    selectionItemsController.deselectedAll();
                }
            }
        }
        multiselect_flag = true;
    }

    @Override
    public boolean onLongClick(View view, Boolean item) {
        return false;
    }

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
        if (mode == ListExtra.ONLY_SINGLE_SELECTION_MODE) {
            if (windowAction != null) {
                windowAction.onOk(holder.getItemView());
            }
        } else {
            adjustMultiSelection();
        }
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

}
