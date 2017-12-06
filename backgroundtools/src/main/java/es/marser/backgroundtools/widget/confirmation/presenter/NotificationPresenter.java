package es.marser.backgroundtools.widget.confirmation.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.dialogs.presenter.DialogBasePresenter;
import es.marser.backgroundtools.containers.dialogs.task.OnResult;
import es.marser.backgroundtools.enums.DialogExtras;

/**
 * @author sergio
 *         Created by sergio on 6/12/17.
 */

public class NotificationPresenter extends DialogBasePresenter {

    /*Variable de resultados [EN]  Variable of results*/
    protected OnResult<Void> result;

    //CONTRUCTORS____________________________________________
    @SuppressWarnings("unused")
    public NotificationPresenter(@NonNull Context context, int viewLayout) {
        super(context, viewLayout);
    }

    public NotificationPresenter(@NonNull Context context) {
        super(context, R.layout.mvp_dialog_notification);
    }

    public NotificationPresenter(@NonNull Context context, @Nullable OnResult<Void> result) {
        this(context);
        this.result = result;
    }

    //RESULT___________________________________________________
    @SuppressWarnings("unused")
    public OnResult<Void> getResult() {
        return result;
    }

    @SuppressWarnings("unused")
    public void setResult(OnResult<Void> result) {
        this.result = result;
    }

    //WINACTION________________________________________________
    @Override
    public void onOk(View v) {
        if (result != null) {
            result.onResult(DialogExtras.OK_EXTRA, null);
        }
        savePreferences();
        close();
    }

    @Override
    public void onCancel(View v) {
        if (result != null) {
            result.onResult(DialogExtras.CANCEL_EXTRA, null);
        }
        savePreferences();
        close();
    }

    @Override
    public void onOption(View v) {
        if (result != null) {
            result.onResult(DialogExtras.OPTION_EXTRA, null);
        }
        savePreferences();
        close();
    }
}
