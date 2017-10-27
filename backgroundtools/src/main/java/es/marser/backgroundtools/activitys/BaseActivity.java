package es.marser.backgroundtools.activitys;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import es.marser.async.Result;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.dialogs.progress.CustomInterminateBinDialog;
import es.marser.backgroundtools.enums.DialogIcon;

/**
 * @author sergio
 *         Base de construcción de Actividades
 *         <ul>
 *         <il>Dialogos de carga</il>
 *         <il>Estado de teclado</il>
 *         <il>Soporte para fragments</il>
 *         <il>Chequeador de permisos</il>
 *         </ul>
 *         <p>
 *         [EN]  Activities building base
 *         <ul>
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
public class BaseActivity extends AppCompatActivity {

    protected Result<Boolean> checkresult;
    @VisibleForTesting
    public CustomInterminateBinDialog mProgressDialog;


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

    //LOADING DIALOGS_____________________________________________________________________________________

    /**
     * Muestra un cuadro de dialogo de carga de datos
     * <p>
     * [EN]  Displays a data load dialog box
     */
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = CustomInterminateBinDialog
                    .newInstace(this, CustomInterminateBinDialog
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

    /**
     * Chequear un permiso o solicitarlo
     * <p>
     * [EN]  Check a permission or request it
     *
     * @param permit      Permiso [EN]  Permission
     * @param checkresult Variable de resultado [EN]  Result variable
     */
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

    /**
     * Permite que una aplicación lea los datos del calendario de los usuarios
     * <p>
     * [EN]  Allows an application to read the user's calendar data
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    public void checkReadCalendar(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.READ_CALENDAR, checkresult);
    }

    /**
     * Permite que una aplicación escriba los datos del calendario de los usuarios
     * <p>
     * [EN]  Allows an application to write the user's calendar data.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    public void checkWriteCalendar(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.WRITE_CALENDAR, checkresult);
    }

    /*CAMERA*/

    /**
     * Requerido para poder acceder al dispositivo de la cámara
     * <p>
     * [EN]  Required to be able to access the camera device
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    public void checkCamera(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.CAMERA, checkresult);
    }

    /*CONTACTS*/

    /**
     * Permite que una aplicación lea los datos de los contactos de los usuarios.
     * <p>
     * [EN]  Allows an application to read the user's contacts data.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    public void checkReadContacts(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.READ_CONTACTS, checkresult);
    }

    /**
     * Permite que una aplicación escriba los datos de los contactos de los usuarios.
     * <p>
     * [EN]  Allows an application to write the user's contacts data.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    public void checkWriteContacts(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.WRITE_CONTACTS, checkresult);
    }

    /**
     * Permite el acceso a la lista de cuentas en el Servicio de cuentas.
     * <p>
     * [EN]  Allows access to the list of accounts in the Accounts Service.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    public void checkGetAccounts(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.GET_ACCOUNTS, checkresult);
    }

    /*LOCATIONS*/

    /**
     * Permite que una aplicación acceda a una ubicación precisa.  Alternativamente, es posible que desee ACCESS_COARSE_LOCATION
     * <p>
     * [EN]  Allows an app to access precise location. Alternatively, you might want ACCESS_COARSE_LOCATION.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    public void checkAccessFineLocation(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, checkresult);
    }

    /**
     * Permite que una aplicación acceda a la ubicación aproximada.  Alternativamente, es posible que desee ACCESS_FINE_LOCATION
     * <p>
     * [EN]  Allows an app to access approximate location. Alternatively, you might want ACCESS_FINE_LOCATION.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    public void checkAccessCoarseLocation(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION, checkresult);
    }

 /*MICROPHONE*/

    /**
     * Permite que una aplicación grabe audio.
     * <p>
     * [EN]  Allows an application to record audio.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    public void checkRecordAudio(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.RECORD_AUDIO, checkresult);
    }


/*PHONE*/

    /**
     * Permite el acceso de solo lectura al estado del teléfono,
     * incluido el número de teléfono del dispositivo,
     * la información de la red celular actual,
     * el estado de las llamadas en curso y una lista de todas las cuentas telefónicas registradas en el dispositivo.
     * <p>
     * [EN]  Allows read only access to phone state,
     * including the phone number of the device,
     * current cellular network information,
     * the status of any ongoing calls,
     * and a list of any PhoneAccounts registered on the device.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    public void checkReadPhoneState(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.READ_PHONE_STATE, checkresult);
    }

    /**
     * Permite que una aplicación inicie una llamada telefónica sin pasar por la interfaz de usuario del Marcador para que el usuario confirme la llamada.
     * <p>
     * [EN]  Allows an application to initiate a phone call without going through the Dialer user interface for the user to confirm the call.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    public void checkCallPhone(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.CALL_PHONE, checkresult);
    }

    /**
     * Permite que una aplicación lea el registro de llamadas de los usuarios.
     * <p>
     * [EN]  Allows an application to read the user's call log.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void checkReadCallLog(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.READ_CALL_LOG, checkresult);
    }

    /**
     * Permite que una aplicación escriba (pero no lea) los datos de registro de llamadas de los usuarios.
     * <p>
     * [EN]  Allows an application to write (but not read) the user's call log data.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void checkWriteCallLog(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.WRITE_CALL_LOG, checkresult);
    }

    /**
     * Permite que una aplicación agregue correos de voz en el sistema.
     * <p>
     * [EN]  Allows an application to add voicemails into the system.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    public void checkAddVoicemail(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.ADD_VOICEMAIL, checkresult);
    }


    /**
     * Permite que una aplicación use el servicio SIP.
     * <p>
     * [EN]  Allows an application to use SIP service.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    public void checkUseSip(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.USE_SIP, checkresult);
    }

    /**
     * Permite que una aplicación vea el número marcado durante una llamada saliente
     * con la opción de redirigir la llamada a un número diferente o cancelar la llamada por completo.
     * <p>
     * [EN]  Allows an application to see the number being dialed during an outgoing call with the option to redirect the call to a different number or abort the call altogether.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    public void checkProcessOutgoingCalls(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.PROCESS_OUTGOING_CALLS, checkresult);
    }

/*SENSORS*/
    /**
     * Permite que una aplicación acceda a los datos de los sensores
     * que usa el usuario para medir lo que está sucediendo dentro de su cuerpo,
     * como la frecuencia cardíaca.
     * <p>
     * [EN]  Allows an application to access data from sensors that the user uses
     * to measure what is happening inside his/her body, such as heart rate.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    public void checkBodySensors(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.BODY_SENSORS, checkresult);
    }


/*SMS*/
    /**
     * Permite que una aplicación envíe mensajes SMS.
     * <p>
     * [EN]  Allows an application to send SMS messages.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    public void checkSendSms(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.SEND_SMS, checkresult);
    }

    /**
     * Allows an application to receive SMS messages
     * <p>
     * [EN]  Allows an application to receive SMS messages.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    public void checkReceiveSms(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.RECEIVE_SMS, checkresult);
    }

    /**
     * Permite que una aplicación lea mensajes SMS.
     * <p>
     * [EN]  Allows an application to read SMS messages.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    public void checkReadSms(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.READ_SMS, checkresult);
    }
    /**
     * Permite que una aplicación reciba mensajes WAP push.
     * <p>
     * [EN]  Allows an application to receive WAP push messages.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    public void checkReceiveWapPush(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.RECEIVE_WAP_PUSH, checkresult);
    }
    /**
     * Permite que una aplicación monitoree los mensajes MMS entrantes.
     * <p>
     * [EN]  Allows an application to monitor incoming MMS messages.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    public void checkReceiveMms(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.RECEIVE_MMS, checkresult);
    }

    /*STORAGE*/
    /**
     * Permite que una aplicación lea desde el almacenamiento externo.
     * Cualquier aplicación que declare el permiso WRITE_EXTERNAL_STORAGE recibe implícitamente este permiso.
     * <p>
     * [EN]  Allows an application to read from external storage.
     * Any app that declares the WRITE_EXTERNAL_STORAGE permission is implicitly granted this permission.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    public void checkReadExternalStorage(@NonNull Result<Boolean> checkresult) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, checkresult);
        }else{
            checkresult.onResult(true);
        }
    }

    /**
     * Permite que una aplicación escriba en el almacenamiento externo.
     * <p>
     * [EN]  Allows an application to write to external storage.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    public void checkWriteExternalStorage(@NonNull Result<Boolean> checkresult) {
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, checkresult);
    }

    /*NETWORK*/
    /**
     * Permite que las aplicaciones accedan a información sobre redes.
     * <p>
     * [EN]  Allows applications to access information about networks.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
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
