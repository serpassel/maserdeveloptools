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
import es.marser.backgroundtools.definition.Selectable;
import es.marser.backgroundtools.dialogs.bases.BaseDialogBinList;
import es.marser.backgroundtools.dialogs.task.OnResult;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.objectslistables.base.holder.BaseViewHolder;
import es.marser.tools.TextTools;


/**
 * @author sergio
 *         Created by Sergio on 09/03/2017.
 *         Dialogo de selección de archivos
 *         <p>
 *         [EN]  File selection dialog
 */

@SuppressWarnings("unused")
public class ObjectChooserDialog<T extends Selectable>
        extends BaseDialogBinList<T> {

    protected OnResult<List<T>> result;
    protected String premarc;
    protected T[] values;

    /**
     * Nueva instancia {@link ObjectChooserDialog}
     *
     * @param context contexto de la aplicación [EN]  application context
     * @param bundle  Argumentos de inicio [EN]  Start arguments
     * @param result  Variable de resultados [EN]  Variable of results
     * @return nueva instancia del dialogo [EN]  new instance of dialogue
     */
    @SuppressWarnings("All")
    public static <T extends Selectable> ObjectChooserDialog newInstance(
            @NonNull Context context,
            @NonNull Bundle bundle,
            @Nullable OnResult<List<T>> result
    ) {

        ObjectChooserDialog instace = new ObjectChooserDialog();
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
    public static <T extends Selectable> Bundle createBundle(DialogIcon icon,
                                                             String title,
                                                             String ok,
                                                             String cancel,
                                                             String premarc,
                                                             ListExtra listExtra,
                                                             T[] values
    ) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DialogIcon.ICON_EXTRA.name(), icon);
        bundle.putString(DialogExtras.TITLE_EXTRA.name(), TextTools.nc(title));
        bundle.putString(DialogExtras.FILTER_EXTRAS.name(), premarc);
        bundle.putParcelableArray(ListExtra.VALUES_EXTRA.name(), values);

        switch (listExtra) {
            case MULTIPLE_SELECTION_MODE:
            case ONLY_MULTIPLE_SELECTION_MODE:
                bundle.putString(DialogExtras.OK_EXTRA.name(), TextTools.nc(ok));
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
    public static <T extends Selectable> Bundle createBundle(Context context, ListExtra listExtra, String premarc, T[] values) {
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
    public static <T extends Selectable> Bundle createBundle(Context context, T[] values) {
        return createBundle(context, ListExtra.ONLY_SINGLE_SELECTION_MODE, null, values);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void preBuild() {

        model.body.set(getArguments().getString(DialogExtras.BODY_EXTRA.name(), ""));
        model.title.set(getArguments().getString(DialogExtras.TITLE_EXTRA.name(), ""));

        buttonsSetModel.ok_name.set(getArguments().getString(DialogExtras.OK_EXTRA.name()));
        buttonsSetModel.cancel_name.set(getArguments().getString(DialogExtras.CANCEL_EXTRA.name()));

        premarc = getArguments().getString(DialogExtras.FILTER_EXTRAS.name(), "");

        values = (T[]) getArguments().getParcelableArray(ListExtra.VALUES_EXTRA.name());
    }

    @Override
    protected void postBuild() {
        super.postBuild();
        load();
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

    @Override
    protected void load() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                for (T t : values) {
                    setSelected(getItemCount(), premarc.contains(t.premarcValue()));
                    addItem(t);
                }
            }
        });
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
        }
    }

    /*{@link OnResult}*/
    public OnResult<List<T>> getResult() {
        return result;
    }

    public void setResult(OnResult<List<T>> result) {
        this.result = result;
    }
}