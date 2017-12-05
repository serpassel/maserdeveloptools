package es.marser.backgroundtools.widget.chooser.presenter;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.definition.Selectable;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.ViewHandler;
import es.marser.backgroundtools.handlers.WindowAction;
import es.marser.backgroundtools.listables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.listables.base.model.SelectionItemsController;
import es.marser.backgroundtools.listables.simple.model.SimpleAdapterModel;
import es.marser.backgroundtools.listables.simple.presenter.SimpleListPresenter;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 29/11/17.
 *         Presentador para selectores de Objetos
 *         <p>
 *         [EN]  Presenter for Object selectors
 */

public class ChooserPresenter<T extends Selectable>
        extends SimpleListPresenter<T, SimpleAdapterModel<T>>
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

    public ChooserPresenter(@NonNull Context context, @NonNull SimpleAdapterModel<T> listModel) {
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

    @SuppressWarnings("unused")
    public static class BundleBuilder{
        /**
         * Creador de argumentos del cuadro de dialogo
         * <p>
         * [EN]  Dialog Box Argument Creator
         *
         * @param icon   Icono para la barra de título [EN]  Icon for the title bar
         * @param title  Título de la barra [EN]  Title of the bar
         * @param path   Directorio de búsqueda [EN]  Search directory
         * @param ok     Texto de botón aceptar [EN]  Accept button text
         * @param cancel Texto de botón cancelar [EN]  Cancel button text
         * @param filter Listado de extensiones válidas [EN]  List of valid extensions
         * @return Bundle argumentado [EN]  Bundle argued
         */
        @SuppressWarnings("All")
        public static <T extends Selectable> Bundle createBundle(DialogIcon icon,
                                                                 String title,
                                                                 String ok,
                                                                 String cancel,
                                                                 String preselect,
                                                                 ListExtra listExtra,
                                                                 List<T> values
        ) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(DialogIcon.ICON_EXTRA.name(), icon);
            bundle.putString(DialogExtras.TITLE_EXTRA.name(), TextTools.nc(title));
            bundle.putString(DialogExtras.FILTER_EXTRAS.name(), preselect);
            bundle.putParcelableArrayList(ListExtra.VALUES_EXTRA.name(), (ArrayList<? extends Parcelable>) values);
            bundle.putSerializable(ListExtra.LIST_EXTRA.name(), listExtra);

            switch (listExtra) {

                case MULTIPLE_SELECTION_MODE:
                case ONLY_MULTIPLE_SELECTION_MODE:
                    bundle.putString(DialogExtras.OK_EXTRA.name(), TextTools.nc(ok));
                    bundle.putInt(DialogExtras.STATE_EXTRA.name(), 1);
                    break;
                default:
                    bundle.putInt(DialogExtras.STATE_EXTRA.name(), 0);
                    break;
            }

            bundle.putString(DialogExtras.CANCEL_EXTRA.name(), TextTools.nc(cancel));
            return bundle;
        }

        /**
         * @param context contexto de la aplicación [EN]  context of the application
         * @param path    Directorio de búsqueda [EN]  Search directory
         * @param filter  Listado de extensiones válidas [EN]  List of valid extensions
         * @return Bundle argumentado [EN]  Bundle argued
         */
        public static <T extends Selectable> Bundle createBundle(
                Context context,
                ListExtra listExtra,
                String premarc,
                List<T> values
        ) {
            return createBundle(
                    DialogIcon.SEARCH_ICON,
                    context.getResources().getString(R.string.bt_dialog_select_title),
                    context.getResources().getString(R.string.bt_ACTION_OK),
                    context.getResources().getString(R.string.bt_ACTION_CANCEL),
                    premarc,
                    listExtra,
                    values
            );
        }

        /**
         * Valores de prueba por defecto
         * <p>
         * [EN]  Default test values
         *
         * @param context contexto de la aplicación [EN]  context of the application
         * @return Bundle argumentado [EN]  Bundle argued
         */
        public static <T extends Selectable> Bundle createBundle(
                Context context,
                List<T> values
        ) {
            return createBundle(context, ListExtra.ONLY_SINGLE_SELECTION_MODE, null, values);
        }
    }
}
