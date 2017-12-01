package es.marser.backgroundtools.definition;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import es.marser.async.Result;

/**
 * @author sergio
 *         Created by sergio on 31/10/17.
 */

@SuppressWarnings("unused")
public interface PermissionChecker {

    /**
     * Chequear un permiso o solicitarlo
     * <p>
     * [EN]  Check a permission or request it
     *
     * @param permit      Permiso [EN]  Permission
     * @param checkresult Variable de resultado [EN]  Result variable
     */
    void checkPermission(@NonNull String permit, @NonNull Result<Boolean> checkresult);

    /*CALENDAR*/

    /**
     * Permite que una aplicación lea los datos del calendario de los usuarios
     * <p>
     * [EN]  Allows an application to read the user's calendar data
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    void checkReadCalendar(@NonNull Result<Boolean> checkresult);

    /**
     * Permite que una aplicación escriba los datos del calendario de los usuarios
     * <p>
     * [EN]  Allows an application to write the user's calendar data.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    void checkWriteCalendar(@NonNull Result<Boolean> checkresult);

    /*CAMERA*/

    /**
     * Requerido para poder acceder al dispositivo de la cámara
     * <p>
     * [EN]  Required to be able to access the camera device
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    void checkCamera(@NonNull Result<Boolean> checkresult);

    /*CONTACTS*/

    /**
     * Permite que una aplicación lea los datos de los contactos de los usuarios.
     * <p>
     * [EN]  Allows an application to read the user's contacts data.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    void checkReadContacts(@NonNull Result<Boolean> checkresult);

    /**
     * Permite que una aplicación escriba los datos de los contactos de los usuarios.
     * <p>
     * [EN]  Allows an application to write the user's contacts data.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    void checkWriteContacts(@NonNull Result<Boolean> checkresult);

    /**
     * Permite el acceso a la lista de cuentas en el Servicio de cuentas.
     * <p>
     * [EN]  Allows access to the list of accounts in the Accounts Service.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    void checkGetAccounts(@NonNull Result<Boolean> checkresult);

    /*LOCATIONS*/

    /**
     * Permite que una aplicación acceda a una ubicación precisa.  Alternativamente, es posible que desee ACCESS_COARSE_LOCATION
     * <p>
     * [EN]  Allows an app to access precise location. Alternatively, you might want ACCESS_COARSE_LOCATION.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    void checkAccessFineLocation(@NonNull Result<Boolean> checkresult);

    /**
     * Permite que una aplicación acceda a la ubicación aproximada.  Alternativamente, es posible que desee ACCESS_FINE_LOCATION
     * <p>
     * [EN]  Allows an app to access approximate location. Alternatively, you might want ACCESS_FINE_LOCATION.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    void checkAccessCoarseLocation(@NonNull Result<Boolean> checkresult);

 /*MICROPHONE*/

    /**
     * Permite que una aplicación grabe audio.
     * <p>
     * [EN]  Allows an application to record audio.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    void checkRecordAudio(@NonNull Result<Boolean> checkresult);
    
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
    void checkReadPhoneState(@NonNull Result<Boolean> checkresult);

    /**
     * Permite que una aplicación inicie una llamada telefónica sin pasar por la interfaz de usuario del Marcador para que el usuario confirme la llamada.
     * <p>
     * [EN]  Allows an application to initiate a phone call without going through the Dialer user interface for the user to confirm the call.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    void checkCallPhone(@NonNull Result<Boolean> checkresult);

    /**
     * Permite que una aplicación lea el registro de llamadas de los usuarios.
     * <p>
     * [EN]  Allows an application to read the user's call log.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    void checkReadCallLog(@NonNull Result<Boolean> checkresult);

    /**
     * Permite que una aplicación escriba (pero no lea) los datos de registro de llamadas de los usuarios.
     * <p>
     * [EN]  Allows an application to write (but not read) the user's call log data.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    void checkWriteCallLog(@NonNull Result<Boolean> checkresult);

    /**
     * Permite que una aplicación agregue correos de voz en el sistema.
     * <p>
     * [EN]  Allows an application to add voicemails into the system.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    void checkAddVoicemail(@NonNull Result<Boolean> checkresult);

    /**
     * Permite que una aplicación use el servicio SIP.
     * <p>
     * [EN]  Allows an application to use SIP service.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    void checkUseSip(@NonNull Result<Boolean> checkresult);

    /**
     * Permite que una aplicación vea el número marcado durante una llamada saliente
     * con la opción de redirigir la llamada a un número diferente o cancelar la llamada por completo.
     * <p>
     * [EN]  Allows an application to see the number being dialed during an outgoing call with the option to redirect the call to a different number or abort the call altogether.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    void checkProcessOutgoingCalls(@NonNull Result<Boolean> checkresult);

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
    void checkBodySensors(@NonNull Result<Boolean> checkresult);
    
/*SMS*/

    /**
     * Permite que una aplicación envíe mensajes SMS.
     * <p>
     * [EN]  Allows an application to send SMS messages.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    void checkSendSms(@NonNull Result<Boolean> checkresult);

    /**
     * Allows an application to receive SMS messages
     * <p>
     * [EN]  Allows an application to receive SMS messages.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    void checkReceiveSms(@NonNull Result<Boolean> checkresult);

    /**
     * Permite que una aplicación lea mensajes SMS.
     * <p>
     * [EN]  Allows an application to read SMS messages.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    void checkReadSms(@NonNull Result<Boolean> checkresult);

    /**
     * Permite que una aplicación reciba mensajes WAP push.
     * <p>
     * [EN]  Allows an application to receive WAP push messages.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    void checkReceiveWapPush(@NonNull Result<Boolean> checkresult);

    /**
     * Permite que una aplicación monitoree los mensajes MMS entrantes.
     * <p>
     * [EN]  Allows an application to monitor incoming MMS messages.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    void checkReceiveMms(@NonNull Result<Boolean> checkresult);

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
    void checkReadExternalStorage(@NonNull Result<Boolean> checkresult);

    /**
     * Permite que una aplicación escriba en el almacenamiento externo.
     * <p>
     * [EN]  Allows an application to write to external storage.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    void checkWriteExternalStorage(@NonNull Result<Boolean> checkresult);

    /*NETWORK*/

    /**
     * Permite que las aplicaciones accedan a información sobre redes.
     * <p>
     * [EN]  Allows applications to access information about networks.
     *
     * @param checkresult resultado booleano [EN]  boolean result{@link Result}
     */
    void checkAccessNetworkState(@NonNull Result<Boolean> checkresult);
}
