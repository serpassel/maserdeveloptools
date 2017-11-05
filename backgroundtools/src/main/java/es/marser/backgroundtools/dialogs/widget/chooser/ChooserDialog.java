package es.marser.backgroundtools.dialogs.widget.chooser;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.dialogs.bases.BaseDialogBinList;
import es.marser.backgroundtools.dialogs.task.OnResult;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.backgroundtools.enums.ListExtra;
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
public class ChooserDialog
        extends BaseDialogBinList<String> {

    protected OnResult<List<String>> result;
    protected String[] values;

    /**
     * Nueva instancia {@link ChooserDialog}
     *
     * @param context contexto de la aplicación [EN]  application context
     * @param bundle  Argumentos de inicio [EN]  Start arguments
     * @param result  Variable de resultados [EN]  Variable of results
     * @return nueva instancia del dialogo [EN]  new instance of dialogue
     */
    @SuppressWarnings("All")
    public static ChooserDialog newInstance(
            @NonNull Context context,
            @NonNull Bundle bundle,
            @Nullable OnResult<List<String>> result
    ) {

        ChooserDialog instace = new ChooserDialog();
        instace.setContext(context);
        instace.setArguments(bundle);
        instace.setResult(null);
        return instace;
    }

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
    public static Bundle createBundle(DialogIcon icon,
                                      String title,
                                      String ok,
                                      String cancel,
                                      String premarc,
                                      ListExtra listExtra,
                                      @NonNull String[] values
    ) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DialogIcon.ICON_EXTRA.name(), icon);
        bundle.putString(DialogExtras.TITLE_EXTRA.name(), TextTools.nc(title));
        bundle.putString(DialogExtras.FILTER_EXTRAS.name(), premarc);
        bundle.putStringArray(ListExtra.VALUES_EXTRA.name(), values);

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
    public static Bundle createBundle(
            Context context,
            ListExtra listExtra,
            String premarc,
            @NonNull String[] values
    ) {
        return createBundle(
                DialogIcon.SEARCH_ICON,
                context.getResources().getString(R.string.bt_dialog_select_title),
                context.getResources().getString(R.string.bt_ACTION_OPEN),
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
    public static Bundle createBundle(
            Context context,
            @NonNull String[] values
    ) {
        return createBundle(context, ListExtra.ONLY_SINGLE_SELECTION_MODE, null, values);
    }

    @Override
    protected void preBuild() {

        model.body.set(getArguments().getString(DialogExtras.BODY_EXTRA.name(), ""));
        model.title.set(getArguments().getString(DialogExtras.TITLE_EXTRA.name(), ""));

        statusModel.blockAction.set(getArguments().getInt(DialogExtras.STATE_EXTRA.name(), 0) == 1);

        String ok_name = getArguments().getString(DialogExtras.OK_EXTRA.name());

        values = getArguments().getStringArray(ListExtra.VALUES_EXTRA.name());

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

    /*{@link BaseDialogBinList}*/
    @Override
    protected int getHolderLayout() {
        return R.layout.mvp_item_file_model;
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
        if (values != null) {

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    for (String s : values) {
                        addItem(s);
                    }
                }
            });
        }
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
            result.onResult(DialogExtras.CANCEL_EXTRA, new ArrayList<String>());
        }
        close();
    }

    /* {@link es.marser.backgroundtools.handlers.ViewItemHandler}*/
    @Override
    public void onClickItem(BaseViewHolder<String> holder, String item, int position, ListExtra mode) {
        super.onClickItem(holder, item, position, mode);

        if (getInitialSelectionMode() == ListExtra.ONLY_SINGLE_SELECTION_MODE) {
            onOk(holder.getItemView());
        }
    }

    /*{@link OnResult}*/
    public OnResult<List<String>> getResult() {
        return result;
    }

    public void setResult(OnResult<List<String>> result) {
        this.result = result;
    }
}