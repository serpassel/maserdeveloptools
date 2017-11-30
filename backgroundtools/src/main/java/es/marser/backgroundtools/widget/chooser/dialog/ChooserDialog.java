package es.marser.backgroundtools.widget.chooser.dialog;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.definition.Selectable;
import es.marser.backgroundtools.containers.dialogs.bases.BaseDialogBinList;
import es.marser.backgroundtools.containers.dialogs.task.OnResult;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.objectslistables.simple.model.SimpleListModel;
import es.marser.backgroundtools.widget.chooser.presenter.ChooserPresenter;
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
        extends BaseDialogBinList<T,SimpleListModel<T>,  ChooserPresenter<T>> {

    protected OnResult<List<T>> result;

    public static <T extends Selectable> ChooserDialog<T> newInstance(
            @NonNull Context context,
            @NonNull Bundle bundle,
            @NonNull ChooserPresenter<T> presenter,
            @Nullable OnResult<List<T>> result
    ) {

        ChooserDialog<T> instace = new ChooserDialog<>();
        instace.setContext(context);
        instace.setArguments(bundle);
        instace.setResult(result);
        instace.setPresenter(presenter);
        instace.setSimpleListModel(new SimpleListModel<T>(context, R.layout.mvp_item_object_chooser));
        return instace;
    }

    public static <T extends Selectable> ChooserDialog<T> newInstance(
            @NonNull Context context,
            @NonNull Bundle bundle,
            @NonNull ChooserPresenter<T> presenter,
            @NonNull SimpleListModel<T> simpleListModel,
            @Nullable OnResult<List<T>> result
    ) {

        ChooserDialog<T> instace = ChooserDialog.newInstance(context, bundle, presenter, result);
        instace.setSimpleListModel(simpleListModel);
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
        super.preBuild();

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
        ListExtra out = (ListExtra) getArguments().getSerializable(ListExtra.LIST_EXTRA.name());
        out = out != null ? out : ListExtra.ONLY_SINGLE_SELECTION_MODE;
        setSelectionmode(out);
        presenter.load(getArguments());
    }

    @Override
    protected void bindObject() {
        super.bindObject();
        /*Presentador [EN]  Presenter*/
        viewDataBinding.setVariable(BR.handler, presenter);
        viewDataBinding.executePendingBindings();

        /*Multiselección [EN]  Multiselection*/
        viewDataBinding.setVariable(BR.multiselect, ChooserPresenter.multiselect);
        viewDataBinding.executePendingBindings();
    }

    @Override
    protected int getDialogLayout() {
        return R.layout.mvp_dialog_object_chooser;
    }

    /* {@link es.marser.backgroundtools.handlers.WindowAction}*/
    @Override
    public void onOk(View view) {
        if (result != null) {
            result.onResult(DialogExtras.OK_EXTRA, simpleListModel.getSelectds());
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

    /*{@link OnResult}*/
    public OnResult<List<T>> getResult() {
        return result;
    }

    public void setResult(OnResult<List<T>> result) {
        this.result = result;
    }


    /**
     * Filjar el modo de selección de la lista
     * <p>
     * [EN]  Filtering the mode selection mode of the list
     *
     * @param selectionmode Modo de slección de la lista
     */
    @Override
    public void setSelectionmode(@NonNull ListExtra selectionmode) {
        if (simpleListModel != null) {
            simpleListModel.setSelectionmode(selectionmode);
        }
    }

    @Override
    public void setPresenter(@NonNull ChooserPresenter<T> presenter) {
        super.setPresenter(presenter);
        this.presenter.setWindowAction(this);
    }
}