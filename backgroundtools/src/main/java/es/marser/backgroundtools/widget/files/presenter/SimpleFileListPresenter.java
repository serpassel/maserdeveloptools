package es.marser.backgroundtools.widget.files.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import es.marser.async.DataUploaderTask;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.toast.Launch_toast;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.handlers.ViewHandler;
import es.marser.backgroundtools.listables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.listables.simple.presenter.SimpleListPresenter;
import es.marser.backgroundtools.systemtools.FilePathUtil;
import es.marser.backgroundtools.widget.files.OnPathChangedListener;
import es.marser.backgroundtools.widget.files.model.FileModel;
import es.marser.backgroundtools.widget.files.model.FileModelOrderByName;
import es.marser.backgroundtools.widget.files.model.SimpleFileAdapterModel;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 30/11/17.
 *         Presentador para acciones de archivos y carpetas sobre lista simple
 *         <p>
 *         [EN]  Presenter for file and folder actions on simple list
 */

@SuppressWarnings("unused")
public class SimpleFileListPresenter extends SimpleListPresenter<FileModel, SimpleFileAdapterModel>
        implements ViewHandler<FileModel> {

    private OnPathChangedListener listener;
    private File path;
    protected String[] filter;


    public SimpleFileListPresenter(@NonNull Context context) {
        super(context);
    }

    public SimpleFileListPresenter(@NonNull Context context, @NonNull SimpleFileAdapterModel listModel) {
        super(context, listModel);
    }

    @Override
    public void load(@Nullable Bundle bundle) {
        final List<FileModel> directory = new ArrayList<>();
        final List<FileModel> file = new ArrayList<>();
        final Comparator<FileModel> comparator = new FileModelOrderByName();
        //File path = model.body.get() != null ? new File(model.body.get()) : null;
        resetByBundle(bundle);

        FilePathUtil.getAsyncFiles(path, filter != null ? filter : new String[]{}, new DataUploaderTask<Void, File, Void>() {
            @Override
            public void onStart(Void start) {
                getListmodel().clear();
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
                getListmodel().addAll(directory);
                getListmodel().addAll(file);
            }

            @Override
            public void onFailure(Throwable e) {
                Launch_toast.errorToast(context, e.getMessage());
            }
        });
    }

    /**
     * Asigna las variables por argumentos
     * <p>
     * [EN]  Assign variables by arguments
     *
     * @param bundle argumentos [EN]  arguments
     */
    private void resetByBundle(@Nullable Bundle bundle) {
        if (bundle != null) {
            String pathname = bundle.getString(DialogExtras.BODY_EXTRA.name(), "");
            this.path = new File(pathname);
            this.filter = bundle.getStringArray(DialogExtras.FILTER_EXTRAS.name());
        }
    }

    @Override
    public void onClick(View view, FileModel item) {
        if (view.getId() == R.id.path_up) {
            upPath();
        }
    }

    @Override
    public boolean onLongClick(View view, FileModel item) {
        return false;
    }

    @Override
    public void onClickItem(BaseViewHolder<FileModel> holder, FileModel item, int position, ListExtra mode) {
        super.onClickItem(holder, item, position, mode);
        downPath(item);
    }

    //ACTIONS_____________________________________________________________________________
    /**
     * Subir un nivel de directorio
     * <p>
     * [EN]  Upload a directory level
     */
    private void upPath() {
        if (!path.equals(FilePathUtil.getRootPath())) {
            if (listener != null) {
                listener.onPathChanged(path, new File(path.getParent()));
            }
            path = new File(path.getParent());
            load(null);
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

            if (listener != null) {
                listener.onPathChanged(this.path, path);
            }

            if (path.isDirectory()) {
                this.path = path;
                load(null);
            }
        }
    }

    //WINDOW ACTIONS____________________________________________________________________
    public void setOnPathChangedListener(@NonNull OnPathChangedListener listener) {
        this.listener = listener;
    }

    public void removeOnPathChangedListener() {
        this.listener = null;
    }

    public static class BundleBuilder {
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
            bundle.putSerializable(DialogIcon.ICON_EXTRA.name(), icon);
            bundle.putString(DialogExtras.TITLE_EXTRA.name(), TextTools.nc(title));
            bundle.putString(DialogExtras.BODY_EXTRA.name(), TextTools.nc(path));
            bundle.putStringArray(DialogExtras.FILTER_EXTRAS.name(), filter);
            bundle.putString(DialogExtras.CANCEL_EXTRA.name(), TextTools.nc(cancel));
            bundle.putString(DialogExtras.OK_EXTRA.name(), TextTools.nc(ok));
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

    }
}
