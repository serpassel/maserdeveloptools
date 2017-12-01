package es.marser.backgroundtools.widget.files.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.io.File;

import es.marser.async.Result;
import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.dialogs.bases.BaseDialogBinList;
import es.marser.backgroundtools.containers.dialogs.task.OnResult;
import es.marser.backgroundtools.definition.PermissionChecker;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.widget.files.OnPathChangedListener;
import es.marser.backgroundtools.widget.files.model.FileModel;
import es.marser.backgroundtools.widget.files.model.SimpleFileListModel;
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
        extends BaseDialogBinList<FileModel, SimpleFileListModel, SimpleFileListPresenter> implements OnPathChangedListener {

    protected OnResult<FileModel> result;
    protected boolean readablepermission;
    protected FileModel headmodel;

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
            @NonNull SimpleFileListPresenter presenter,
            @NonNull SimpleFileListModel model,
            @Nullable OnResult<FileModel> result
    ) {

        FileChooserDialog instace = new FileChooserDialog();
        instace.setContext(context);
        instace.setArguments(bundle);
        instace.setResult(result);
        instace.setReadablepermission(readablepermission);
        instace.setSimpleListModel(model);
        instace.setPresenter(presenter);
        return instace;
    }


    //PARTICULAR_______________________________________________________________________
    @Override
    public void show() {
        if (readablepermission) {
            super.show();
        }
    }

    @Override
    protected void preBuild() {
        headmodel = new FileModel();

        model.body.set(getArguments().getString(DialogExtras.BODY_EXTRA.name(), ""));
        model.title.set(getArguments().getString(DialogExtras.TITLE_EXTRA.name(), ""));

        buttonsSetModel.ok_name.set(getArguments().getString(DialogExtras.OK_EXTRA.name()));
        buttonsSetModel.cancel_name.set(getArguments().getString(DialogExtras.CANCEL_EXTRA.name()));

        String[] filter = getArguments().getStringArray(DialogExtras.FILTER_EXTRAS.name());

        StringBuilder builder = new StringBuilder("");

        if (filter != null) {
            for (String s : filter) {
                builder.append(s).append(", ");
            }
        }

        model.keyname.set(TextTools.deleteLastBrand(builder, ", "));
    }

    @Override
    protected void postBuild() {
        super.postBuild();
        presenter.load(getArguments());
    }

    @Override
    protected void bindObject() {
        super.bindObject();
        viewDataBinding.setVariable(BR.headmodel, headmodel);
        viewDataBinding.executePendingBindings();

        viewDataBinding.setVariable(BR.handler, presenter);
        viewDataBinding.executePendingBindings();
    }

    @Override
    protected int getDialogLayout() {
        return R.layout.mvp_dialog_file_chooser;
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

    /* {@link es.marser.backgroundtools.handlers.WindowAction}*/
    @Override
    public void onOk(View view) {
        if (result != null) {
            result.onResult(DialogExtras.OK_EXTRA, headmodel);
        }
        close();
    }

    @Override
    public void onCancel(View view) {
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

    //VARIABLES___________________________________________________
    @Override
    public void setPresenter(@NonNull SimpleFileListPresenter presenter) {
        super.setPresenter(presenter);
        presenter.setOnPathChangedListener(this);
    }

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

    //LISTENER____________________________________________________________________
    @Override
    public void onPathChanged(@Nullable File oldFile, @NonNull File newPath) {
        if (newPath.isDirectory()) {
            headmodel.setFile(new File(""));
            model.body.set(newPath.getAbsolutePath());
        }else{
            headmodel.setFile(newPath);
        }
    }

}