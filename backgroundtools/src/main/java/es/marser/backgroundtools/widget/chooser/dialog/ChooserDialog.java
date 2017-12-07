package es.marser.backgroundtools.widget.chooser.dialog;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.dialogs.bases.BaseDialogBinList;
import es.marser.backgroundtools.containers.dialogs.presenter.BundleBuilder;
import es.marser.backgroundtools.containers.dialogs.task.OnResult;
import es.marser.backgroundtools.definition.Selectable;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.backgroundtools.enums.ListExtra;
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
        extends BaseDialogBinList<ChooserPresenter<T>> {

    public static <T extends Selectable> ChooserDialog<T> newInstance(
            @NonNull Context context,
            @Nullable Bundle bundle,
            @NonNull OnResult<List<T>> result
    ) {

        /*PRESENTER*/
        ChooserPresenter<T> presenter = new ChooserPresenter<>(context);
        presenter.setArguments(bundle);
        presenter.setResult(result);

        /*DIALOG*/
        ChooserDialog<T> instance = new ChooserDialog<>();
        instance.setContext(context);
        instance.setPresenter(presenter);

        return instance;
    }

    //BUNDLE BUILDERS_______________________________

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
                                                             List<T> values) {
        Bundle bundle = new Bundle();

        /*DIALOG MODEL*/
        bundle.putAll(BundleBuilder.createDialogModelBundle(icon, TextTools.nc(title), null, null));

        /*BUTTON SET MODEL*/
        switch (listExtra) {
            case MULTIPLE_SELECTION_MODE:
            case ONLY_MULTIPLE_SELECTION_MODE:
                bundle.putString(DialogExtras.OK_EXTRA.name(), TextTools.nc(ok));
            default:
                bundle.putString(DialogExtras.CANCEL_EXTRA.name(), TextTools.nc(cancel));
                break;
        }

        /*LIST MODEL*/
        bundle.putAll(BundleBuilder.createListModeBundle(listExtra));

        /*LOAD BUNDLE*/
        bundle.putString(DialogExtras.FILTER_EXTRAS.name(), preselect);
        bundle.putParcelableArrayList(ListExtra.VALUES_EXTRA.name(), (ArrayList<? extends Parcelable>) values);
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
            List<T> values) {
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
            List<T> values) {
        return createBundle(context, ListExtra.ONLY_SINGLE_SELECTION_MODE, null, values);
    }
}