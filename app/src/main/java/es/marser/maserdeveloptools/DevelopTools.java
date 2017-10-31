package es.marser.maserdeveloptools;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

@SuppressWarnings({"EmptyMethod", "unused"})
public class DevelopTools extends AppCompatActivity {

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

    public void launch() {
      //DialogExample.indeterminateBox(this);
      //DialogExample.indeterminateSpinner(this);
      //DialogExample.progressIndeterminateBox(this);
      //DialogExample.progressBox(this);

       //es.marser.backgroundtools.dialogs.toast.Launch_toast.warningToast(this, "Mensaje de advertencia");
       //es.marser.backgroundtools.dialogs.toast.Launch_toast.errorToast(this, "Mensaje de error");
       //es.marser.backgroundtools.dialogs.toast.Launch_toast.informationToast(this, "Mensaje de información");

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

        DialogExample.fileSelector(this);
    }
}
