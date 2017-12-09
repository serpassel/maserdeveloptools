package es.marser.backgroundtools.widget.auth.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.bindingadapters.BinderContainer;
import es.marser.backgroundtools.containers.dialogs.presenter.DialogBasePresenter;
import es.marser.backgroundtools.containers.dialogs.task.OnResult;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.backgroundtools.events.ViewHandler;

/**
 * @author sergio
 *         Created by sergio on 6/12/17.
 *         Presentador para la obtención de credenciales de autentificación
 *         <p>
 *         [EN]  Presenter for obtaining authentication credentials
 */

@SuppressWarnings("unused")
public class CredentialPresenter
        extends DialogBasePresenter
        implements ViewHandler<DialogIcon> {

    /*RESULT*/
    private OnResult<DialogIcon> result;

    //CONSTRUCTORS_________________________________________
    public CredentialPresenter(@NonNull Context context, int viewLayout) {
        super(context, viewLayout);
    }

    public CredentialPresenter(@NonNull Context context){
        this(context, R.layout.mvp_dialog_credential);
    }

    //OVERRIDE______________________________________________
    @Override
    public void onBindObjects(@NonNull BinderContainer binderContainer) {
        super.onBindObjects(binderContainer);
        binderContainer.bindObject(BR.handler, this);
    }

    //VIEW ITEM ACTION_______________________________________
    @Override
    public void onClick(View view, DialogIcon item) {
        if (result != null) {
            result.onResult(DialogExtras.OK_EXTRA, item);
            close();
        }
    }

    @Override
    public boolean onLongClick(View view, DialogIcon item) {
        return false;
    }


    //WINACTION___________________________________________________________________
    @Override
    public void onCancel(View v) {
        if (result != null) {
            result.onResult(DialogExtras.CANCEL_EXTRA, DialogIcon.DEFAULT_ICON);
            close();
        }

    }

    //PROPERTIES______________________________________________
    @SuppressWarnings("unused")
    public OnResult<DialogIcon> getResult() {
        return result;
    }

    public void setResult(OnResult<DialogIcon> result) {
        this.result = result;
    }
}
