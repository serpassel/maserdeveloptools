package es.marser.backgroundtools.dialogs.bases;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import es.marser.async.Result;
import es.marser.backgroundtools.containers.PermissionChecker;

/**
 * @author sergio
 *         Created by Sergio on 15/09/2017.
 *         Base para creación de dialogos personalizados.
 *         Mantener un constructor vacío para evitar problemas de compilación
 *         <p>
 *         [EN]  Basis for creating custom dialogs
 *         Keep an empty constructor to avoid compilation problems
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class BaseDialog extends DialogFragment implements PermissionChecker{
    protected Context context;
    protected Dialog dialog;
    protected View view;

    public BaseDialog(){
    }

    /**
     * Procedimiento para crear el dialog. método para sobreescribir
     * <p>
     * [EN]  Procedure to create the dialog.  method to overwrite
     */
    protected abstract void createDialog();

    /**
     * Procedimiento para mostrar el dialogo. Oculta el teclado por defecto
     * <p>
     * [EN]  Procedure to display the dialog.  Hides the default keyboard
     */
    public void show() {
        createDialog();

        if (view != null) {
            view.clearFocus();
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
        //Eliminamos los oyentes de las clases abstractas
        if (!isShowing()) {
            dialog.show();
        }
    }

    /**
     * Método de cierre del dialogo
     * <p>
     * [EN]  Method of closing the dialog
     */
    public void close() {
        if (dialog != null && isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * Indentificar si el dialogo está activado
     * <p>
     * [EN]  Indicate whether the dialog is activated
     *
     * @return verdadero si está activo [EN]  true if active
     */
    public boolean isShowing() {
        return dialog != null && dialog.isShowing();
    }

    /**
     * Getter del contexto que ha lanzado del dialogo
     * <p>
     * [EN]  Context getter that has released dialog
     *
     * @return Contexto vinculado al dialogo [EN]  Context linked to the dialogue
     */
    @Override
    public Context getContext() {
        return context;
    }

    /**
     * Setter de la variable de contexto
     * <p>
     * [EN]  Context Variable Setter es
     *
     * @param context Contexto sobre el que se vincula el dialogo [EN]  Context on which the dialogue is linked
     */
    public void setContext(Context context) {
        this.context = context;
    }

    //PERMISSION CHECKER____________________________________________________________________________________
    protected Result<Boolean> checkresult;

    @Override
    public void checkPermission(@NonNull String permit, @NonNull Result<Boolean> checkresult) {
        this.checkresult = checkresult;

        if (checkPermission(getContext(), permit)) {
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
