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
import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.bindingadapters.BinderContainer;
import es.marser.backgroundtools.containers.dialogs.presenter.SimpleDialogListPresenter;
import es.marser.backgroundtools.containers.dialogs.task.OnResult;
import es.marser.backgroundtools.containers.toast.Launch_toast;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.events.ViewHandler;
import es.marser.backgroundtools.listables.base.holder.BaseViewHolder;
import es.marser.backgroundtools.systemtools.FilePathUtil;
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
public class SimpleFileListPresenter extends SimpleDialogListPresenter<FileModel, SimpleFileAdapterModel>
        implements ViewHandler<FileModel> {

    private File path;
    protected String[] filter;

    protected OnResult<FileModel> result;
    protected FileModel headmodel;

    //CONSTRUCTORS_________________________________________________________________

    public SimpleFileListPresenter(@NonNull Context context) {
        this(context, R.layout.mvp_dialog_file_chooser);
    }

    public SimpleFileListPresenter(@NonNull Context context, int viewlayout) {
        this(context, viewlayout, new SimpleFileAdapterModel(context));
    }

    public SimpleFileListPresenter(@NonNull Context context, int viewlayout, @NonNull SimpleFileAdapterModel listmodel) {
        super(context, viewlayout, listmodel);
        this.headmodel = new FileModel();
    }

    //OVERRIDE____________________________________________________
    @Override
    public void onBindObjects(@NonNull BinderContainer binderContainer) {
        super.onBindObjects(binderContainer);
        binderContainer.bindObject(BR.headmodel, headmodel);
        binderContainer.bindObject(BR.handler, this);
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        if (args != null) {
            String pathname = args.getString(DialogExtras.BODY_EXTRA.name(), "");
            this.path = new File(pathname);
            this.filter = args.getStringArray(DialogExtras.FILTER_EXTRAS.name());

            StringBuilder builder = new StringBuilder("");

            if (this.filter != null) {
                for (String s : this.filter) {
                    builder.append(s).append(", ");
                }
            }
            model.keyname.set(TextTools.deleteLastBrand(builder, ", "));
        }
    }

    //LOAD ADAPTER________________________________________________
    @Override
    public void load(@Nullable Bundle bundle) {
        final List<FileModel> directory = new ArrayList<>();
        final List<FileModel> file = new ArrayList<>();
        final Comparator<FileModel> comparator = new FileModelOrderByName();
        //File path = model.body.get() != null ? new File(model.body.get()) : null;

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

    //EVENTS______________________________________________________________________
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

    //WINACTIONS____________________________________
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

    //ACTIONS_____________________________________________________________________________

    /**
     * Subir un nivel de directorio
     * <p>
     * [EN]  Upload a directory level
     */
    private void upPath() {
        if (!path.equals(FilePathUtil.getRootPath())) {
            onPathChanged(path, new File(path.getParent()));
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
            onPathChanged(this.path, path);
            if (path.isDirectory()) {
                this.path = path;
                load(null);
            }
        }
    }

    /**
     * Cambio de directorio
     * <p>
     * [EN]  Change of directory
     *
     * @param oldFile path old
     * @param newPath new path
     */
    public void onPathChanged(@Nullable File oldFile, @NonNull File newPath) {
        if (newPath.isDirectory()) {
            headmodel.setFile(new File(""));
            model.body.set(newPath.getAbsolutePath());
        } else {
            headmodel.setFile(newPath);
        }
    }

    //PROPERTIES__________________________________________________________________________

    public File getPath() {
        return path;
    }

    public void setPath(File path) {
        this.path = path;
    }

    public String[] getFilter() {
        return filter;
    }

    public void setFilter(String[] filter) {
        this.filter = filter;
    }

    public OnResult<FileModel> getResult() {
        return result;
    }

    public void setResult(OnResult<FileModel> result) {
        this.result = result;
    }

    public FileModel getHeadmodel() {
        return headmodel;
    }

    public void setHeadmodel(FileModel headmodel) {
        this.headmodel = headmodel;
    }
}
