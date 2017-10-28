package es.marser.backgroundtools.fragments.base;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import es.marser.async.Result;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.activitys.base.BaseActivity;
import es.marser.backgroundtools.enums.EventsExtras;
import es.marser.backgroundtools.fragments.listeners.FragmentAction;

/**
 * @author sergio
 *         Created by Sergio on 18/03/2017.
 *         Base de construcción de Fragments
 *         <ul>
 *         <il>Argumentos</il>
 *         <il>Eventos de actividad</il>
 *         <il>Métodos para fragments adjuntos en instancias de BaseActivity</il>
 *         </ul>
 *         <p>
 *         [EN]  Fragments building base
 *         <ul>
 *         <il>Arguments</il>
 *         <il>Events of activity</il>
 *         <il>Methods for Attachment Fragments on BaseActivity Instances</il>
 *         </ul>
 */

@SuppressWarnings("unused")
public abstract class BaseFragment extends Fragment {

    protected FragmentAction fragmentAction;


    //ARGUMENTS________________________________________________________________________________________
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        resolveArgs();
        instaceVariables();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * Traslado de argumentos a variables
     * <p>
     * [EN]  Moving arguments to variables
     */
    @SuppressWarnings("EmptyMethod")
    protected void resolveArgs() {
    }

    /**
     * Instanciar variables
     * <p>
     * [EN]  Instanciar variables
     */
    protected abstract void instaceVariables();

    /**
     * Utilizar para configurar datos. Se aplica cuando el fragment ha sido cargado por la actividad
     * <p>
     * [EN]  Use to configure data.  Applies when the fragment has been loaded by the activity
     */
    protected abstract void initActivityCreated();

    /**
     * Definición de la vista del fragment. Valor por defecto {@link R.layout#mvc_frag_simple_list}
     * <p>
     * [EN]  Fragment view definition
     *
     * @return R.layout.XXXXX Vista del fragment [EN]  View of the fragment
     */
    protected  abstract int getFragmentLayout();

    //EVENTS OF ACTIVITY___________________________________________________________________________________

    /**
     * Llamado cuando se lanza una actividad en espera de su resultado
     * <p>
     * [EN]  Called when an activity is launched waiting for its result
     *
     * @param requestCode Código identificador del envío [EN]  Shipping ID
     * @param resultCode  Código del resultado [EN]  Result code
     * @param data        datos de resultado [EN]  result data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    //METHODS FOR ATTACHMENT FRAGMENTS ON BASEACTIVITY INSTANCES______________________________________

    protected BaseActivity getBaseActivity() {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            return (BaseActivity) getActivity();
        }
        return null;
    }


    /**
     * Chequear un permiso o solicitarlo
     * <p>
     * [EN]  Check a permission or request it
     *
     * @param permit      Permiso [EN]  Permission
     * @param checkresult Variable de resultado [EN]  Result variable
     */
    public void checkPermission(@NonNull String permit, @NonNull Result<Boolean> checkresult) {
        if (getBaseActivity() != null) {
            getBaseActivity().checkPermission(permit, checkresult);
        } else {
            throw new ClassCastException("The activity on which the fragment is attached must be a instance of BaseActivity");
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
        } else {
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

    /**
     * Muestra un cuadro de dialogo de carga de datos
     * <p>
     * [EN]  Displays a data load dialog box
     */
    public void showProgressDialog() {
        if (getBaseActivity() != null) {
            getBaseActivity().showProgressDialog();
        }
    }

    /**
     * Oculta el cuadro de dialogo de carga de datos
     * <p>
     * [EN]  Hide the data load dialog box
     */
    public void hideProgressDialog() {
        if (getBaseActivity() != null) {
            getBaseActivity().hideProgressDialog();
        }
    }


    /**
     * Animación de movimiento para fragment
     * <p>
     * [EN]  Motion animation for fragment
     *
     * @param events tipo de desplazamiento según {@link EventsExtras}
     *               [EN]  displacement type according to {@link EventsExtras}
     */
    public void animLayout(EventsExtras events) {
        View v = this.getView();
        Animation animation;
        switch (events) {
            case SLIDE_RIGHT:
                animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_right);
                break;
            case SLIDE_LEFT:
                animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_left);
                break;
            case SLIDE_RIGHT_END:
                animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_right_end);
                break;
            case SLIDE_LEFT_END:
                animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_left_end);
                break;
            default:
                animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_right);
                break;
        }
        if (v != null) {
            v.startAnimation(animation);
        }
    }

    //CHANGE LISTENERS IN FRAGMENTS_____________________________________________________________________

    /**
     * Establecer el oyente de tipo {@link FragmentAction}
     * <p>
     * [EN]  Set type listener {@link FragmentAction}
     *
     * @param fragmentAction Oyente de acciones de fragments [EN]  Fragments actions listener
     */
    public void setFragmentAction(FragmentAction fragmentAction) {
        this.fragmentAction = fragmentAction;
    }

    /**
     * Elimina el oyente de acciones de fragment
     * <p>
     * [EN]  Delete the listener of fragment actions
     *
     * @param fragmentAction Oyente de acciones de fragments [EN]  Fragments actions listener
     */
    public void removeFragmentAction(FragmentAction fragmentAction) {
        this.fragmentAction = fragmentAction;
    }
}
