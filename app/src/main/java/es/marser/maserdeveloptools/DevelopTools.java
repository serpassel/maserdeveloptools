package es.marser.maserdeveloptools;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
        launch();
    }

    private void launch() {
        //CustomProgressBarExample.indeterminateBox(this);
        //CustomProgressBarExample.indeterminateSpinner(this);
        //CustomProgressBarExample.progressIndeterminateBox(this);
        //CustomProgressBarExample.progressBox(this);

  //      Launch_toast.warningToast(this, "Mensaje de advertencia");
  //     Launch_toast.errorToast(this, "Mensaje de error");
//      Launch_toast.informationToast(this, "Mensaje de información");

    }
}
