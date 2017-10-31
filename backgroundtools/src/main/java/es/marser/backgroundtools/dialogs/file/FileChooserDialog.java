package es.marser.backgroundtools.dialogs.file;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import es.marser.async.DataUploaderTask;
import es.marser.async.Result;
import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.dialogs.bases.BaseDialogBinList;
import es.marser.backgroundtools.dialogs.model.FileModel;
import es.marser.backgroundtools.dialogs.task.OnResult;
import es.marser.backgroundtools.dialogs.toast.Launch_toast;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.ViewHandler;
import es.marser.backgroundtools.systemtools.FilePathUtil;
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
        extends BaseDialogBinList<FileModel>
        implements ViewHandler<Void> {

    protected OnResult<FileModel> result;
    protected boolean readablepermission;
    protected FileModel headmodel;

    /**
     * Nueva instancia {@link FileChooserDialog}
     *
     * @param context contexto de la aplicación [EN]  application context
     * @param bundle  Argumentos de inicio [EN]  Start arguments
     * @param result  Variable de resultados [EN]  Variable of results
     * @return nueva instancia del dialogo [EN]  new instance of dialogue
     */
    @SuppressWarnings("NullableProblems")
    public static FileChooserDialog newInstance(
            @NonNull Context context,
            @NonNull Bundle bundle,
            @NonNull boolean readablepermission,
            @Nullable OnResult<FileModel> result
    ) {

        FileChooserDialog instace = new FileChooserDialog();
        instace.setContext(context);
        instace.setArguments(bundle);
        instace.setResult(null);
        instace.setReadablepermission(readablepermission);
        return instace;
    }

    /**
     * Creador de argumentos del cuadro de dialogo
     * <p>
     * [EN]  Dialog Box Argument Creator
     *
     * @param icon   Icono para la barra de título [EN]  Icon for the title bar
     * @param title  Título de la barra [EN]  Title of the bar
     * @param body   Cuerpo del mensaje [EN]  Message body
     * @param ok     Texto de botón aceptar [EN]  Accept button text
     * @param cancel Texto de botón cancelar [EN]  Cancel button text
     * @return Bundle argumentado [EN]  Bundle argued
     */
    @SuppressWarnings("NullableProblems")
    public static Bundle createBundle(DialogIcon icon, String title, String path, String filter, String ok, String cancel) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DialogIcon.ICON_EXTRA.name(), icon);
        bundle.putString(DialogExtras.TITLE_EXTRA.name(), TextTools.nc(title));
        bundle.putString(DialogExtras.BODY_EXTRA.name(), TextTools.nc(path));
        bundle.putString(DialogExtras.FILTER_EXTRAS.name(), TextTools.nc(filter));
        bundle.putString(DialogExtras.CANCEL_EXTRA.name(), TextTools.nc(cancel));
        bundle.putString(DialogExtras.OK_EXTRA.name(), TextTools.nc(ok));
        return bundle;
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
        return createBundle(
                DialogIcon.SEARCH_ICON,
                context.getResources().getString(R.string.bt_dialog_select_title),
                FilePathUtil.getDownloadPath().getAbsolutePath(),
                null,
                context.getResources().getString(R.string.bt_ACTION_OPEN),
                context.getResources().getString(R.string.bt_ACTION_CANCEL));
    }

    @Override
    public void show() {
        if(readablepermission){
            super.show();
        }
    }

    @Override
    protected void preBuild() {
        model.body.set(getArguments().getString(DialogExtras.BODY_EXTRA.name(), ""));
        model.title.set(getArguments().getString(DialogExtras.TITLE_EXTRA.name(), ""));

        buttonsSetModel.ok_name.set(getArguments().getString(DialogExtras.OK_EXTRA.name()));
        buttonsSetModel.cancel_name.set(getArguments().getString(DialogExtras.CANCEL_EXTRA.name()));
    }

    @Override
    protected void bindObject() {
        super.bindObject();
        viewDataBinding.setVariable(BR.headmodel, headmodel);
        viewDataBinding.executePendingBindings();

        viewDataBinding.setVariable(BR.handler, this);
        viewDataBinding.executePendingBindings();
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
        return R.layout.mvp_dialog_file_chooser;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return new GridLayoutManager(getContext(), 3);
        } else {
            return new GridLayoutManager(getActivity(), 3);
        }
    }

    @Override
    protected ListExtra getInitialSelectionMode() {
        return ListExtra.NOT_SELECTION_MODE;
    }

    @Override
    protected void load() {
        final List<FileModel> directory = new ArrayList<>();
        final List<FileModel> file = new ArrayList<>();
        final Comparator<FileModel> comparator = new FileModelOrderByName();
        String filter = getArguments().getString(DialogExtras.FILTER_EXTRAS.name(), "");

        File path = model.body.get() != null ? new File(model.body.get()) : null;

        FilePathUtil.getAsyncFiles(path, filter, new DataUploaderTask<Void, File, Void>() {
            @Override
            public void onStart(Void start) {
                clear();
            }

            @Override
            public void onUpdate(File update) {
                // addItem(new FileModel(update));

                FileModel FileModel = new FileModel(update);
                if (FileModel.isDirectory()) {
                    directory.add(FileModel);
                } else {
                    file.add(FileModel);
                }
            }

            @Override
            public void onFinish(Void finish) {
                Collections.sort(directory, comparator);
                Collections.sort(file, comparator);
                adapter.arrayListController.addAllItems(directory);
                adapter.arrayListController.addAllItems(file);
            }

            @Override
            public void onFailure(Throwable e) {
                Launch_toast.errorToast(context, e.getMessage());
            }
        });
    }

    /* {@link es.marser.backgroundtools.handlers.WindowAction}*/
    @Override
    public void onOk(View v) {
        if (result != null) {
            result.onResult(DialogExtras.OK_EXTRA, headmodel);
        }
        close();
    }

    @Override
    public void onCancel(View v) {
        if (result != null) {
            result.onResult(DialogExtras.CANCEL_EXTRA, headmodel);
        }
        close();
    }

    /*{@link OnResult}*/
    public OnResult<FileModel> getResult() {
        return result;
    }

    public void setResult(OnResult<FileModel> result) {
        this.result = result;
    }

    /* {@link ViewHandler}*/
    @Override
    public void onClick(View v, Void item) {
        if (v.getId() == R.id.path_up) {
            upPath();
        }
    }

    @Override
    public boolean onLongClick(View v, Void item) {
        return true;
    }


    /* {@link es.marser.backgroundtools.handlers.ViewItemHandler}*/
    @Override
    public void onClickItem(View v, FileModel item, int position, ListExtra mode) {
        super.onClickItem(v, item, position, mode);
        downPath(item);
    }

    /**
     * Subir un nivel de directorio
     * <p>
     * [EN]  Upload a directory level
     */
    private void upPath() {
        File path = model.body.get() != null ? new File(model.body.get()) : null;

        if (path != null && !path.equals(FilePathUtil.getRootPath())) {
            model.body.set(path.getParent());
            load();
        }
    }

    /**
     * Bajar un nivel de directorio
     * <p>
     * [EN]  Download a directory level
     *
     * @param item item pulsado [EN]  item pulsed
     */
    private void downPath(FileModel item) {
        if (item != null && item.getFile() != null) {
            File path = item.getFile();

            /*Bajar si es directorio [EN]  Download if it is directory*/
            if (path.isDirectory()) {

                model.body.set(path.getAbsolutePath());
                load();

            } else {
                /*Fijar archivo selecionado [EN]  Set selected file*/
                if (item.isFile() && result != null) {
                    headmodel.setFile(item.getFile());
                }
            }
        }

    }

    //PROPERTIES_______________________________________________________________________________

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
     * <p>
     * [EN]  Set the flag reading permission
     *
     * @param readablepermission indicar si se dispone del permiso de lectura
     *                           [EN]  indicate if reading permission is available
     *                           {@link es.marser.backgroundtools.containers.PermissionChecker#checkReadExternalStorage(Result)}
     */
    public void setReadablepermission(boolean readablepermission) {
        this.readablepermission = readablepermission;
    }
}