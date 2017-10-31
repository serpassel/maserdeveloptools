package es.marser.backgroundtools.containers.activitys.base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import es.marser.async.Result;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.PermissionChecker;
import es.marser.backgroundtools.dialogs.progress.BinIndeterminateDialog;
import es.marser.backgroundtools.enums.DialogIcon;

/**
 * @author sergio
 *         Base de construcción de Actividades
 *         <ul>
 *         <il>Soporte de Toolbar</il>
 *         <il>Dialogos de carga</il>
 *         <il>Estado de teclado</il>
 *         <il>Soporte para fragments</il>
 *         <il>Chequeador de permisos</il>
 *         </ul>
 *         <p>
 *         [EN]  Activities building base
 *         <ul>
 *         <il>Toolbar support</il>
 *         <il>Loading Dialogs</il>
 *         <il>Keyboard status</il>
 *         <il>Support for fragments</il>
 *         <il>Permit Checker</il>
 *         </ul>
 *         <p>
 *         <ul>
 *         <il>CALENDAR</il>
 *         <il>CAMERA</il>
 *         <il>CONTACTS</il>
 *         <il>LOCATION</il>
 *         <il>MICROPHONE</il>
 *         <il>PHONE</il>
 *         <il>SENSORS</il>
 *         <il>SMS</il>
 *         <il>STORAGE</il>
 *         <il>NETWORK</il>
 *         </ul>
 */

@SuppressWarnings("unused")
public abstract class BaseActivity extends AppCompatActivity implements PermissionChecker{

    protected Toolbar toolbar;

    @VisibleForTesting
    public BinIndeterminateDialog mProgressDialog;

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideInputMode();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        /*Instanciar variables [EN]  Instanciar variables*/
        instaceVariables();
        /*Activar toolbar [EN]  Activar toolbar*/
        if (activeToolbarSupport()) {
            initToolbar();
        }
    }

    /**
     * Métodos pre-inicio de variables
     * <p>
     * [EN]  Pre-start methods of variables
     */
    protected abstract void preinstaceVariables();

    /**
     * Instanciar variables
     * <p>
     * [EN]  Instanciar variables
     */
    protected abstract void instaceVariables();

    /**
     * Métodos post-inicio de variables
     * <p>
     * [EN]  Post-start methods of variables
     */
    protected abstract void postinstaceVariables();

    protected int getActivityLayout() {
        return R.layout.ac_frag_toolbar;
    }

    //TOOLBAR SUPPORT__________________________________________________________________________________

    /**
     * Método para inicio de la barra de herramientas
     * <p>
     * [EN]  Method for starting the toolbar
     */
    protected void initToolbar() {

        toolbar = findViewById(R.id.app_toolbar);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    /**
     * Activa el soporte de la barra de herramientas
     * <p>
     * [EN]  Enable toolbar support
     *
     * @return verdadero si se debe de incluir barra de herramientas
     * [EN]  true if toolbar should be included
     */
    public boolean activeToolbarSupport() {
        return true;
    }

    //LOADING DIALOGS_____________________________________________________________________________________

    /**
     * Muestra un cuadro de dialogo de carga de datos
     * <p>
     * [EN]  Displays a data load dialog box
     */
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = BinIndeterminateDialog
                    .newInstance(this, BinIndeterminateDialog
                            .createBundle(DialogIcon.LOADING_ICON));
            mProgressDialog.setBody("Espere...");
        }
        mProgressDialog.show();
    }

    /**
     * Oculta el cuadro de dialogo de carga de datos
     * <p>
     * [EN]  Hide the data load dialog box
     */
    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.close();
        }
    }


    //KEYBOARD STATUS____________________________________________________________________________

    /**
     * Mantiene el teclado oculto por defecto
     * <p>
     * [EN]  Keeps the keyboard hidden by default
     */
    public void hideInputMode() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /**
     * Mantiene el teclado visible por defecto
     * <p>
     * [EN]  Keeps the keyboard visible by default
     */
    public void showInputMode() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    /**
     * Oculta el teclado
     * <p>
     * [EN]  Hide keyboard
     *
     * @param context contexto de aplicación [EN]  application context
     */
    public static void forceHiddeKeyboard(Activity context) {
        if (context == null || context.isFinishing()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (context != null) {
                    context.isDestroyed();
                }
                return;
            }
            return;
        }
        View view = context.getCurrentFocus();
        if (view != null) {
            view.clearFocus();
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }


    //SUPPORT FOR FRAGMENTS______________________________________________________________________

    /**
     * Inserta un fragment
     * <p>
     * [EN]  Insert a fragment it
     *
     * @param fragment {@link android.support.v4.app.Fragment}
     */
    protected void insertFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragment != null && fragmentManager != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.pager, fragment);
            fragmentTransaction.commit();
        }
    }

    /**
     * Reemplaza un fragment
     * <p>
     * [EN]  Replaces a fragment
     *
     * @param fragment {@link android.support.v4.app.Fragment}
     */
    protected void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragment != null && fragmentManager != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.pager, fragment);
            fragmentTransaction.commit();
        }
    }

    //PERMISSION CHECKER____________________________________________________________________________________
    protected Result<Boolean> checkresult;

    @Override
    public void checkPermission(@NonNull String permit, @NonNull Result<Boolean> checkresult) {
        this.checkresult = checkresult;

        if (checkPermission(this, permit)) {
            checkresult.onResult(true);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]
                        {permit}, 1001);
            } else {
                checkresult.onResult(false);
            }
        }
    }

   /*CALENDAR*/

    @Override
    public void checkReadCalendar(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.READ_CALENDAR, checkresult);
    }

    @Override
    public void checkWriteCalendar(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.WRITE_CALENDAR, checkresult);
    }

    /*CAMERA*/

    @Override
    public void checkCamera(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.CAMERA, checkresult);
    }

    /*CONTACTS*/

    @Override
    public void checkReadContacts(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.READ_CONTACTS, checkresult);
    }

    @Override
    public void checkWriteContacts(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.WRITE_CONTACTS, checkresult);
    }

    @Override
    public void checkGetAccounts(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.GET_ACCOUNTS, checkresult);
    }

    /*LOCATIONS*/

    @Override
    public void checkAccessFineLocation(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, checkresult);
    }

    @Override
    public void checkAccessCoarseLocation(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION, checkresult);
    }

 /*MICROPHONE*/

    @Override
    public void checkRecordAudio(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.RECORD_AUDIO, checkresult);
    }

/*PHONE*/

    @Override
    public void checkReadPhoneState(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.READ_PHONE_STATE, checkresult);
    }

    @Override
    public void checkCallPhone(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.CALL_PHONE, checkresult);
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void checkReadCallLog(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.READ_CALL_LOG, checkresult);
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void checkWriteCallLog(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.WRITE_CALL_LOG, checkresult);
    }

    @Override
    public void checkAddVoicemail(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.ADD_VOICEMAIL, checkresult);
    }

    @Override
    public void checkUseSip(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.USE_SIP, checkresult);
    }

    @Override
    public void checkProcessOutgoingCalls(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.PROCESS_OUTGOING_CALLS, checkresult);
    }

/*SENSORS*/

    @Override
    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    public void checkBodySensors(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.BODY_SENSORS, checkresult);
    }

/*SMS*/

    @Override
    public void checkSendSms(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.SEND_SMS, checkresult);
    }

    @Override
    public void checkReceiveSms(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.RECEIVE_SMS, checkresult);
    }

    @Override
    public void checkReadSms(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.READ_SMS, checkresult);
    }

    @Override
    public void checkReceiveWapPush(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.RECEIVE_WAP_PUSH, checkresult);
    }

    @Override
    public void checkReceiveMms(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.RECEIVE_MMS, checkresult);
    }

    /*STORAGE*/

    @Override
    public void checkReadExternalStorage(@NonNull Result<Boolean> checkresult) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, checkresult);
        } else {
            checkresult.onResult(true);
        }
    }

    @Override
    public void checkWriteExternalStorage(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, checkresult);
    }

    /*NETWORK*/

    @Override
    public void checkAccessNetworkState(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.ACCESS_NETWORK_STATE, checkresult);
    }


    /**
     * Control de permisos para versiones inferiores a Lolliipop
     * <p>
     * [EN]  Permission control for versions lower than Lolliipop
     *
     * @param context Context de la apliación [EN]  Context of the application
     * @param permit  permiso a solicitar [EN]  permission to request
     * @return verdadero si se dispone del permiso [EN]  true if permission is available
     */
    private static boolean checkPermission(Context context, @NonNull String permit) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            return true;
        }
        int result = ContextCompat.checkSelfPermission(context, permit);
        switch (result) {
            case PackageManager.PERMISSION_GRANTED:
                return true;
            case PackageManager.PERMISSION_DENIED:
                return false;
            default:
                return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (this.checkresult != null) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                this.checkresult.onResult(true);
            } else {
                this.checkresult.onResult(false);
            }
        }
    }
}
