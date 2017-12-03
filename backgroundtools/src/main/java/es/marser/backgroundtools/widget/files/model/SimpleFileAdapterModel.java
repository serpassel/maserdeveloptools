package es.marser.backgroundtools.widget.files.model;

import android.content.Context;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.listables.simple.model.SimpleAdapterModel;

/**
 * @author sergio
 *         Created by sergio on 30/11/17.
 *         Modelo de control de listas de archivos y carpetas
 *         <p>
 *         [EN]  Model control of file and folder lists
 */

@SuppressWarnings("unused")
public class SimpleFileAdapterModel extends SimpleAdapterModel<FileModel> {
    //CONTRUCTORS______________________________________________________________
    public SimpleFileAdapterModel(Context context) {
        this(context, R.layout.mvp_item_file_model);
    }

    public SimpleFileAdapterModel(Context context, int holderLayout) {
        super(context, 3, holderLayout);
    }

}
