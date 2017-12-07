package es.marser.backgroundtools.widget.chooser.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import es.marser.backgroundtools.containers.dialogs.bases.BaseDialogBinList;
import es.marser.backgroundtools.containers.dialogs.task.OnResult;
import es.marser.backgroundtools.definition.Selectable;
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
        extends BaseDialogBinList<ChooserPresenter<T>> {

    public static <T extends Selectable> ChooserDialog<T> newInstance(
            @NonNull Context context,
            @Nullable Bundle bundle,
            @NonNull OnResult<List<T>> result
    ) {

        /*PRESENTER*/
        ChooserPresenter<T> presenter = new ChooserPresenter<>(context);
        presenter.setArguments(bundle);//Arguments to iniciate
        presenter.setResult(result);

        /*DIALOG*/
        ChooserDialog<T> instance = new ChooserDialog<>();
        instance.setContext(context);
        instance.setPresenter(presenter);
        instance.setArguments(bundle);//Arguments for load

        return instance;
    }

    public static <T extends Selectable, CP extends ChooserPresenter<T>> ChooserDialog<T> newInstance(
            @NonNull Context context,
            @Nullable Bundle bundle,
            @NonNull CP presenter,
            @NonNull OnResult<List<T>> result
    ) {

        /*PRESENTER*/
        presenter.setArguments(bundle);
        presenter.setResult(result);

        /*DIALOG*/
        ChooserDialog<T> instance = new ChooserDialog<>();
        instance.setContext(context);
        instance.setPresenter(presenter);
        instance.setArguments(bundle);

        return instance;
    }

}