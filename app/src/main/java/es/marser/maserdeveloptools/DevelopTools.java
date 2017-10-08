package es.marser.maserdeveloptools;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import es.marser.backgroundtools.toast.Launch_toast;

@SuppressWarnings("EmptyMethod")
public class DevelopTools extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_develop_tools);
    }

    @Override
    protected void onStart() {
        super.onStart();
       // launch();
    }

    public void launch() {
       DialogExample.indeterminateBox(this);
       DialogExample.indeterminateSpinner(this);
       DialogExample.progressIndeterminateBox(this);
       DialogExample.progressBox(this);

       Launch_toast.warningToast(this, "Mensaje de advertencia");
       Launch_toast.errorToast(this, "Mensaje de error");
       Launch_toast.informationToast(this, "Mensaje de información");

        //DialogExample.editGeneric(this);
    }
}
