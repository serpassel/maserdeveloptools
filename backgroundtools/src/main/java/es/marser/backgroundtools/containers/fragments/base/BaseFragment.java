package es.marser.backgroundtools.containers.fragments.base;

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
import es.marser.backgroundtools.containers.PermissionChecker;
import es.marser.backgroundtools.containers.activitys.base.BaseActivity;
import es.marser.backgroundtools.enums.EventsExtras;
import es.marser.backgroundtools.containers.fragments.listeners.FragmentAction;
import es.marser.backgroundtools.systemtools.events.SimpleGestureFilter;

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
public abstract class BaseFragment
        extends Fragment
        implements PermissionChecker,
        SimpleGestureFilter.SimpleGestureListener {

    protected FragmentAction fragmentAction;

    public BaseFragment() {
        //setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        inflater.inflate(getFragmentLayout(), container, false);
        return inflater.inflate(getFragmentLayout(), container, false);
    }

    /**
     * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * Definición de la vista del fragment. Valor por defecto {@link R.layout#mvc_frag_simple_list}
     * <p>
     * [EN]  Fragment view definition
     *
     * @return R.layout.XXXXX Vista del fragment [EN]  View of the fragment
     */
    protected abstract int getFragmentLayout();

    /**
     * Método para carga datos en el adapter
     * <p>
     * [EN]  Method for loading data into the adapter
     *
     * @param bundle argumentos de carga de datos [EN]  data load arguments
     */
    protected abstract void load(Bundle bundle);

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

    /**
     * Recupera la actividad Base si fuese una instancia de {@link BaseActivity}
     * <p>
     * [EN]  Retrieve the Base activity if it were an instance of {@link BaseActivity}
     *
     * @return objeto nulo o {@link BaseActivity}
     */
    protected BaseActivity getBaseActivity() {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            return (BaseActivity) getActivity();
        }
        return null;
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

    //EVENTS_________________________________________________________________________________________________
    @Override
    public void onSwipe(EventsExtras eventsExtras) {

    }

    @Override
    public void onDoubleTap() {

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
