package es.marser.maserdeveloptools;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DevelopTools extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_develop_tools);
        launch();
    }

    private void launch() {
        //CustomProgressBarExample.indeterminateBox(this);
        //CustomProgressBarExample.indeterminateSpinner(this);
        //CustomProgressBarExample.progressIndeterminateBox(this);
        CustomProgressBarExample.progressBox(this);
    }
}
