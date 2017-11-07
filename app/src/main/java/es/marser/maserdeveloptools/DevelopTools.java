package es.marser.maserdeveloptools;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import es.marser.LOG_TAG;
import es.marser.async.Result;
import es.marser.backgroundtools.containers.activitys.base.BaseActivity;
import es.marser.backgroundtools.dialogs.bases.BaseDialog;

@SuppressWarnings({"EmptyMethod", "unused"})
public class DevelopTools extends BaseActivity {
    private BaseDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main_frag_drawer);
    }

    @Override
    protected void onStart() {
        super.onStart();
        launch();
    }

    @Override
    public void onBackPressed() {
        Log.i(LOG_TAG.TAG, "Back pulsado");
        dialog.close();
    }

    @Override
    protected boolean actionMenu(MenuItem item) {
        super.actionMenu(item);
        switch (item.getItemId()) {
            case R.id.item_1:
                dialog = DialogExample.indeterminateBox(this);
                break;
            case R.id.item_2:
                dialog = DialogExample.indeterminateSpinner(this);
                break;
            case R.id.item_3:
                dialog = DialogExample.progressIndeterminateBox(this);
                break;
            case R.id.item_4:
                dialog = DialogExample.progressBox(this);
                break;
            case R.id.item_5:
                dialog = DialogExample.editGeneric(this);
                break;

            case R.id.item_6:
                dialog = DialogExample.notificationInformation(this);
                break;
            case R.id.item_7:
                dialog = DialogExample.notificationError(this);
                break;
            case R.id.item_8:
                dialog = DialogExample.notificationWarning(this);
                break;
            case R.id.item_9:
                dialog = DialogExample.notificationHelp(this);
                break;
            case R.id.item_10:
                dialog = DialogExample.notificationConfirmation(this);
                break;
            case R.id.item_11:
                dialog = DialogExample.notificationOkCancelError(this);
                break;
            case R.id.item_12:
                dialog = DialogExample.notificationYesNoCancelConfirmation(this);
                break;
            case R.id.item_13:
                dialog = DialogExample.notificationDeleteRecords(this);
                break;
            case R.id.item_14:
                dialog = DialogExample.notificationConfirmationKey(this);
                break;

            case R.id.item_15:
                checkReadExternalStorage(new Result<Boolean>() {
                    @Override
                    public void onResult(Boolean result) {

                        DialogExample.fileSelector(DevelopTools.this, result);
                    }
                });
                break;
            case R.id.item_16:
                dialog = DialogExample.calendarChooser(this);
                break;
        }
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
        //DialogExample.calendarChooser(this);

        //DialogExample.provincieChooser(this);

        DialogExample.provincieMultiChooser(this);
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
