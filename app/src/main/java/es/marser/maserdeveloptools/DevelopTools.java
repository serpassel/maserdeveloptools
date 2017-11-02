package es.marser.maserdeveloptools;

import android.os.Bundle;

import es.marser.async.Result;
import es.marser.backgroundtools.containers.activitys.base.BaseActivity;

@SuppressWarnings({"EmptyMethod", "unused"})
public class DevelopTools extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_develop_tools);
    }

    @Override
    protected void onStart() {
        super.onStart();
        launch();
    }

    @Override
    public boolean activeToolbarSupport() {
        return false;
    }

    public void launch() {
        //DialogExample.indeterminateBox(this);
        //DialogExample.indeterminateSpinner(this);
        //DialogExample.progressIndeterminateBox(this);
        //DialogExample.progressBox(this);

        //es.marser.backgroundtools.dialogs.widget.toast.Launch_toast.warningToast(this, "Mensaje de advertencia");
        //es.marser.backgroundtools.dialogs.widget.toast.Launch_toast.errorToast(this, "Mensaje de error");
        //es.marser.backgroundtools.dialogs.widget.toast.Launch_toast.informationToast(this, "Mensaje de información");

        //DialogExample.editGeneric(this);
        //SQLExample.createDatabase(this);

        //DialogExample.notificationInformation(this);
        //DialogExample.notificationError(this);
        //DialogExample.notificationWarning(this);
        //DialogExample.notificationHelp(this);
        //DialogExample.notificationConfirmation(this);
        //DialogExample.notificationOkCancelError(this);
        //DialogExample.notificationYesNoCancelConfirmation(this);
        //DialogExample.notificationDeleteRecords(this);
        //DialogExample.notificationConfirmationKey(this);

        /*
        checkReadExternalStorage(new Result<Boolean>() {
            @Override
            public void onResult(Boolean result) {

                    DialogExample.fileSelector(DevelopTools.this, result);
            }
        });
*/
/*
        checkReadExternalStorage(new Result<Boolean>() {
            @Override
            public void onResult(Boolean result) {

                DialogExample.filefilterSelector(DevelopTools.this, result);
            }
        });
*/
        DialogExample.calendarChooser(this);
    }

    @Override
    protected void preinstaceVariables() {

    }

    @Override
    protected void instaceVariables() {

    }

    @Override
    protected void postinstaceVariables() {

    }
}
