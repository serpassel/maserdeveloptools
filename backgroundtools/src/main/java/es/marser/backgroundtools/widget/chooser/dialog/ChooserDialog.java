package es.marser.backgroundtools.widget.chooser.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.dialogs.bases.BaseDialogBinList;
import es.marser.backgroundtools.definition.Selectable;
import es.marser.backgroundtools.listables.simple.model.SimpleAdapterModel;
import es.marser.backgroundtools.widget.chooser.presenter.ChooserPresenter;


/**
 * @author sergio
 *         Created by Sergio on 09/03/2017.
 *         Dialogo de selección de archivos
 *         <p>
 *         [EN]  File selection dialog
 */

@SuppressWarnings("unused")
public class ChooserDialog<T extends Selectable>
        extends BaseDialogBinList<T, ChooserPresenter<T>> {

    public static <T extends Selectable> ChooserDialog<T> newInstance(
            @NonNull Context context,
            @Nullable Bundle bundle,
            @NonNull ChooserPresenter<T> presenter
    ) {

        ChooserDialog<T> instance = new ChooserDialog<>();
        
        instance.setContext(context);
        instance.setArguments(bundle);
       
        if (presenter.getListmodel() == null) {
            presenter.setListmodel(new SimpleAdapterModel<T>(context, R.layout.mvp_item_object_chooser));
        }
        
        if(presenter.getViewLayout() <0){
            presenter.setViewLayout(R.layout.mvp_dialog_object_chooser);
        }
       
        instance.setPresenter(presenter);
        return instance;
    }
}