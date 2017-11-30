package es.marser.backgroundtools.widget.files.model;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.objectslistables.simple.model.SimpleListModel;

/**
 * @author sergio
 *         Created by sergio on 30/11/17.
 *         Modelo de control de listas de archivos y carpetas
 *         <p>
 *         [EN]  Model control of file and folder lists
 */

public class SimpleFileListModel extends SimpleListModel<FileModel> {
    //CONTRUCTORS______________________________________________________________
    public SimpleFileListModel(Context context) {
        super(context, R.layout.mvp_item_file_model);
    }

    public SimpleFileListModel(Context context, int holderLayout) {
        super(context, holderLayout);
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
            return new GridLayoutManager(getContext(), 3);
    }
}
