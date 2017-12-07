package es.marser.backgroundtools.widget.files.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import es.marser.async.Result;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.dialogs.bases.BaseDialogBinList;
import es.marser.backgroundtools.containers.dialogs.presenter.BundleBuilder;
import es.marser.backgroundtools.containers.dialogs.task.OnResult;
import es.marser.backgroundtools.definition.PermissionChecker;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.backgroundtools.systemtools.FilePathUtil;
import es.marser.backgroundtools.widget.files.model.FileModel;
import es.marser.backgroundtools.widget.files.presenter.SimpleFileListPresenter;
import es.marser.tools.TextTools;


/**
 * @author sergio
 *         Created by Sergio on 09/03/2017.
 *         Dialogo de selección de archivos
 *         <p>
 *         [EN]  File selection dialog
 */

@SuppressWarnings("unused")
public class FileChooserDialog
        extends BaseDialogBinList<SimpleFileListPresenter>{

    protected boolean readablepermission;

    /**
     * Nueva instancia {@link FileChooserDialog}
     *
     * @param context            contexto de la aplicación [EN]  application context
     * @param bundle             Argumentos de inicio [EN]  Start arguments
     * @param readablepermission estado de permisos de lectura del disco [EN]  disk read permissions state
     * @param result             Variable de resultados [EN]  Variable of results
     * @return nueva instancia del dialogo [EN]  new instance of dialogue
     */
    @SuppressWarnings("All")
    public static FileChooserDialog newInstance(
            @NonNull Context context,
            @NonNull Bundle bundle,
            @NonNull boolean readablepermission,
            @Nullable OnResult<FileModel> result
    ) {

        /*PRESENTER*/
        SimpleFileListPresenter presenter = new SimpleFileListPresenter(context);
        presenter.setArguments(bundle);
        presenter.setResult(result);

        /*DIALOG*/
        FileChooserDialog instace = new FileChooserDialog();
        instace.setContext(context);
        instace.setArguments(bundle);
        instace.setReadablepermission(readablepermission);
        instace.setPresenter(presenter);

        return instace;
    }

    public FileChooserDialog() {
        this.readablepermission = false;
    }

    //PARTICULAR_______________________________________________________________________
    @Override
    public void show() {
        if (readablepermission) {
            super.show();
        }
    }

    //BUNDLE_______________________________________________________________________________________
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
        public static Bundle createBundle(DialogIcon icon, String title, String path, String ok, String cancel, String[] filter) {
            Bundle bundle = new Bundle();
            /*DIALOG*/
            bundle.putAll(BundleBuilder.createDialogModelBundle(icon,TextTools.nc(title), null, null));
            /*BUTTONS SET MODEL*/
            bundle.putAll(BundleBuilder.createButtonSetModelBundle(TextTools.nc(ok),TextTools.nc(cancel),null));
            /*LOAD BUNDLE*/
            bundle.putString(DialogExtras.BODY_EXTRA.name(), TextTools.nc(path));
            bundle.putStringArray(DialogExtras.FILTER_EXTRAS.name(), filter);
            return bundle;
        }

        /**
         * @param context contexto de la aplicación [EN]  context of the application
         * @param path    Directorio de búsqueda [EN]  Search directory
         * @param filter  Listado de extensiones válidas [EN]  List of valid extensions
         * @return Bundle argumentado [EN]  Bundle argued
         */
        public static Bundle createBundle(Context context, String path, String[] filter) {
            return createBundle(
                    DialogIcon.SEARCH_ICON,
                    context.getResources().getString(R.string.bt_dialog_select_title),
                    path,
                    context.getResources().getString(R.string.bt_ACTION_OPEN),
                    context.getResources().getString(R.string.bt_ACTION_CANCEL), filter
            );
        }

        /**
         * Valores de prueba por defecto
         * <p>
         * [EN]  Default test values
         *
         * @param context contexto de la aplicación [EN]  context of the application
         * @param filter  Listado de extensiones válidas [EN]  List of valid extensions
         * @return Bundle argumentado [EN]  Bundle argued
         */
        public static Bundle createBundle(Context context, String[] filter) {
            return createBundle(context, FilePathUtil.getDownloadPath().getAbsolutePath(), filter);
        }

        /**
         * Valores de prueba por defecto
         * <p>
         * [EN]  Default test values
         *
         * @param context contexto de la aplicación [EN]  context of the application
         * @return Bundle argumentado [EN]  Bundle argued
         */
        public static Bundle createBundle(Context context) {
            return createBundle(context, new String[]{});
        }

    //USER PERMISSION_______________________________________
    /**
     * Indica si tenemos los permisos de lectura del disco
     * <p>
     * [EN]  Indicates whether we have the disk read permissions
     *
     * @return verdadero si disponemos de permisos de lectura [EN]  true if we have read permissions
     */
    public boolean isReadablepermission() {
        return readablepermission;
    }

    /**
     * Fijar la bandera indicadora del permiso de lectura
     * <p>tring[]{}
     * [EN]  Set the flag reading permission
     *
     * @param readablepermission indicar si se dispone del permiso de lectura
     *                           [EN]  indicate if reading permission is available
     *                           {@link PermissionChecker#checkReadExternalStorage(Result)}
     */
    public void setReadablepermission(boolean readablepermission) {
        this.readablepermission = readablepermission;
    }
}