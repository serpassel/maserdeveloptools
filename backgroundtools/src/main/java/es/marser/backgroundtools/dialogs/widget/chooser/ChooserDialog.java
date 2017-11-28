package es.marser.backgroundtools.dialogs.widget.chooser;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.definition.Selectable;
import es.marser.backgroundtools.dialogs.bases.BaseDialogBinList;
import es.marser.backgroundtools.dialogs.task.OnResult;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.ViewHandler;
import es.marser.backgroundtools.objectslistables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.objectslistables.decorator.DividerDecoration;
import es.marser.tools.TextTools;


/**
 * @author sergio
 *         Created by Sergio on 09/03/2017.
 *         Dialogo de selección de archivos
 *         <p>
 *         [EN]  File selection dialog
 */

@SuppressWarnings("unused")
public class ChooserDialog<T extends Selectable>
        extends BaseDialogBinList<T> implements ViewHandler<Boolean> {

    protected OnResult<List<T>> result;

    public static ObservableBoolean multiselect;
    protected boolean multiselect_flag;

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

    @Override
    protected void preBuild() {

        multiselect_flag = true;
        multiselect = new ObservableBoolean();

        model.title.set(getArguments().getString(DialogExtras.TITLE_EXTRA.name(), ""));
        statusModel.blockAction.set(getArguments().getInt(DialogExtras.STATE_EXTRA.name(), 0) == 1);

        String ok_name = getArguments().getString(DialogExtras.OK_EXTRA.name());

        if (ok_name != null) {
            buttonsSetModel.ok_name.set(ok_name);
        }
        buttonsSetModel.cancel_name.set(getArguments().getString(DialogExtras.CANCEL_EXTRA.name()));
    }

    @Override
    protected void postBuild() {
        super.postBuild();
        load();
    }

    @Override
    protected void bindAdapter() {
        super.bindAdapter();
        recyclerView.addItemDecoration(new DividerDecoration(getContext()));
    }

    @Override
    protected void bindObject() {
        super.bindObject();
        /*Presentador [EN]  Presenter*/
        viewDataBinding.setVariable(BR.handler, this);
        viewDataBinding.executePendingBindings();

        /*Multiselección [EN]  Multiselection*/
        viewDataBinding.setVariable(BR.multiselect, multiselect);
        viewDataBinding.executePendingBindings();
    }

    /*{@link BaseDialogBinList}*/
    @Override
    protected int getHolderLayout() {
        return R.layout.mvp_item_object_chooser;
    }

    @Override
    protected int getDialogLayout() {
        return R.layout.mvp_dialog_object_chooser;
    }

    @Override
    protected ListExtra getInitialSelectionMode() {
        ListExtra out = (ListExtra) getArguments().getSerializable(ListExtra.LIST_EXTRA.name());
        return out != null ? out : ListExtra.ONLY_SINGLE_SELECTION_MODE;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void load() {
        final List<T> values = getArguments().getParcelableArrayList(ListExtra.VALUES_EXTRA.name());
        final String preselect = getArguments().getString(DialogExtras.FILTER_EXTRAS.name(), null);
        if (values != null) {
            if (preselect == null) {
                adapter.globalController.addAllItems(values);
                return;
            }

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    for (T t : values) {
                        addItem(t);
                        inputSelected(getItemCount()-1, preselect.contains(t.preSelectValue()));
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
        multiselect.set(getItemCount() == adapter.globalController.selectionController.getIdSelecteds().size());

        multiselect_flag = (old == multiselect.get());
    }

    /* {@link es.marser.backgroundtools.handlers.WindowAction}*/
    @Override
    public void onOk(View view) {
        if (result != null) {
            result.onResult(DialogExtras.OK_EXTRA, adapter.globalController.selectionController.getSelectds());
        }
       close();
    }

    @Override
    public void onCancel(View view) {
        if (result != null) {
            result.onResult(DialogExtras.CANCEL_EXTRA, new ArrayList<T>());
        }
        close();
    }

    /* {@link es.marser.backgroundtools.handlers.ViewItemHandler}*/
    @Override
    public void onClickItem(BaseViewHolder<T> holder, T item, int position, ListExtra mode) {
        super.onClickItem(holder, item, position, mode);

        if (getInitialSelectionMode() == ListExtra.ONLY_SINGLE_SELECTION_MODE) {
            onOk(holder.getItemView());
        } else {
            adjustMultiSelection();
        }
    }

    /*{@link OnResult}*/
    public OnResult<List<T>> getResult() {
        return result;
    }

    public void setResult(OnResult<List<T>> result) {
        this.result = result;
    }

    @Override
    public void onClick(View view, Boolean isChecked) {
        if (multiselect_flag) {
            if (isChecked) {
                adapter.globalController.selectionController.selectedAll();
            } else {
                adapter.globalController.selectionController.deselectedAll();
            }
        }
        multiselect_flag = true;
    }

    @Override
    public boolean onLongClick(View view, Boolean item) {
        return false;
    }

}