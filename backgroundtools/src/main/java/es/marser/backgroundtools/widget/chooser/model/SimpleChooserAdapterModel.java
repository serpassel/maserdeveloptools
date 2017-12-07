package es.marser.backgroundtools.widget.chooser.model;

import android.content.Context;
import android.support.annotation.NonNull;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.definition.Selectable;
import es.marser.backgroundtools.listables.simple.model.SimpleAdapterModel;

/**
 * @author sergio
 *         Created by sergio on 7/12/17.
 *         Modelo de adaptador de listas para selección
 *         <p>
 *         [EN]  List adapter model for selection
 */

public class SimpleChooserAdapterModel<T extends Selectable>  extends SimpleAdapterModel<T> {

    //CONSTRUCTORS________________________________________________________
    public SimpleChooserAdapterModel(@NonNull Context context){
        this(context, R.layout.mvp_item_object_chooser);
    }

    public SimpleChooserAdapterModel(@NonNull Context context, int holderLayout){
        this(context, 1, holderLayout);
    }

    public SimpleChooserAdapterModel(@NonNull Context context, int rows, int holderLayout) {
        super(context, rows, holderLayout);
    }
}
