package es.marser.backgroundtools.widget.chooser.presenter;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.bindingadapters.BinderContainer;
import es.marser.backgroundtools.containers.dialogs.presenter.SimpleDialogListPresenter;
import es.marser.backgroundtools.containers.dialogs.task.OnResult;
import es.marser.backgroundtools.definition.Selectable;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.ViewHandler;
import es.marser.backgroundtools.listables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.listables.base.model.SelectionItemsController;
import es.marser.backgroundtools.widget.chooser.model.SimpleChooserAdapterModel;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 29/11/17.
 *         Presentador para selectores de Objetos
 *         <p>
 *         [EN]  Presenter for Object selectors
 */

@SuppressWarnings("unused")
public class ChooserPresenter<T extends Selectable>
        extends SimpleDialogListPresenter<T, SimpleChooserAdapterModel<T>>
        implements ViewHandler<Boolean> {


    /*VARIABLE DE RESULTADO [EN]  VARIABLE RESULT*/
    protected OnResult<List<T>> result;

    /*MODELO DE SELECCIÓN MULTIPLE [EN]  MODEL OF MULTIPLE SELECTION*/
    public static ObservableBoolean multiselect = new ObservableBoolean();
    protected boolean multiselect_flag;

    //CONTRUCTORS______________________________________________________________________
    public ChooserPresenter(@NonNull Context context) {
        this(context, R.layout.mvp_dialog_object_chooser);
    }

    public ChooserPresenter(@NonNull Context context, int viewlayout) {
        this(context, viewlayout, new SimpleChooserAdapterModel<T>(context));
    }

    public ChooserPresenter(@NonNull Context context, int viewlayout, @NonNull SimpleChooserAdapterModel<T> listmodel) {
        super(context, viewlayout, listmodel);
        this.multiselect_flag = true;
    }

    //OVERRIDE_____________________________________________________________________

    /**
     * if (presenter.getViewLayout() < 0) {
     * presenter.setViewLayout(R.layout.mvp_frag_simple_list);
     * }
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
                getListmodel().addAll(values);
                return;
            }


            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    SelectionItemsController selectionItemsController = getListmodel().getSelectionItemsController();
                    for (T t : values) {
                        getListmodel().add(t);
                        if (selectionItemsController != null) {
                            selectionItemsController.inputSelected(getListmodel().size() - 1, preselect.contains(t.preSelectValue()));
                        }
                    }
                    adjustMultiSelection();
                }
            });
        }
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
        super.onBindObjects(binderContainer);
         /*Presentador [EN]  Presenter*/
        binderContainer.bindObject(BR.handler, this);
        /*Multiselección [EN]  Multiselection*/
        binderContainer.bindObject(BR.multiselect, multiselect);
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        if (args != null) {
            ListExtra li = (ListExtra) args.getSerializable(ListExtra.LIST_EXTRA.name());
            if (li != null) {
                setSelectionmode(null, li);
            }
        }
    }

    //OWN METHODS________________________________________________________________

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

        SelectionItemsController selectionItemsController = getListmodel().getSelectionItemsController();
        multiselect.set(selectionItemsController != null && getListmodel().size() == selectionItemsController.getIdSelecteds().size());
        multiselect_flag = (old == multiselect.get());
    }

    //EVENTS_________________________________________________________________________
    @Override
    public void onClick(View view, Boolean isChecked) {
        if (multiselect_flag) {
            SelectionItemsController selectionItemsController = getListmodel().getSelectionItemsController();
            if (isChecked) {
                if (selectionItemsController != null) {
                    selectionItemsController.selectedAll(getListmodel().size());
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
            onOk(holder.getItemView());
        } else {
            adjustMultiSelection();
        }
    }

    //WINACTION____________________________________________________________________
    /* {@link es.marser.backgroundtools.handlers.WindowAction}*/
    @Override
    public void onOk(View view) {
        if (result != null) {
            result.onResult(DialogExtras.OK_EXTRA, getListmodel().getSelectds());
        }
        if (closableView != null) {
            closableView.close();
        }
    }

    @Override
    public void onCancel(View view) {
        if (result != null) {
            result.onResult(DialogExtras.CANCEL_EXTRA, new ArrayList<T>());
        }
        if (closableView != null) {
            closableView.close();
        }
    }

    //PROPIERTIES___________________________________________________________________
    /*{@link OnResult}*/
    public OnResult<List<T>> getResult() {
        return result;
    }

    public void setResult(@Nullable OnResult<List<T>> result) {
        this.result = result;
    }

    public boolean isMultiselect_flag() {
        return multiselect_flag;
    }

    public void setMultiselect_flag(boolean multiselect_flag) {
        this.multiselect_flag = multiselect_flag;
    }

    //BUNDLE BUILDER________________________________
    public static <T extends Selectable> Bundle createLoadBundle(@Nullable String preselect, @Nullable ArrayList<T> values) {
        Bundle bundle = new Bundle();
          /*LOAD BUNDLE*/
        bundle.putString(DialogExtras.FILTER_EXTRAS.name(), TextTools.nc(preselect));
        if (values == null) {
            values = new ArrayList<>();
        }
        bundle.putParcelableArrayList(ListExtra.VALUES_EXTRA.name(), values);
        return bundle;
    }
}
