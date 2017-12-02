package es.marser.backgroundtools.widget.files.model;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.listables.simple.model.SimpleListModel;

/**
 * @author sergio
 *         Created by sergio on 30/11/17.
 *         Modelo de control de listas de archivos y carpetas
 *         <p>
 *         [EN]  Model control of file and folder lists
 */

@SuppressWarnings("unused")
public class SimpleFileListModel extends SimpleListModel<FileModel> {
    //CONTRUCTORS______________________________________________________________
    public SimpleFileListModel(Context context) {
        this(context, R.layout.mvp_item_file_model);
    }

    public SimpleFileListModel(Context context, int holderLayout) {
        super(context, 3, holderLayout);
    }

}
