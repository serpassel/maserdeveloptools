package es.marser.backgroundtools.systemtools;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

/**
 * @author sergio
 *         Created by Sergio on 28/03/2017.
 *         Clase chequeadora de permisos disponibles
 *         <p>
 *         [EN]  Permissions checker class available
 *         <ul>
 *         <il>CALENDAR</il>
 *         </ul>
 */

@SuppressWarnings("unused")
@Deprecated
public class CheckPermission {

    //CALENDAR________________________________________________________________________________________

    public static boolean checkPermit(Context context, String permit) {
        if (permit == null) {
            return false;
        }

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
     * Permite que una aplicación lea los datos del calendario de los usuarios
     * <p>
     * [EN]  Allows an application to read the user's calendar data
     *
     * @param context contexto de aplicación [EN]  application context
     * @return verdadero para permisos habilitados [EN]  true for enabled permissions
     */
    public static boolean checkReadCalendar(Context context) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            return true;
        }
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR);
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
     * Permite que una aplicación escriba los datos del calendario de los usuarios
     * <p>
     * [EN]  Allows an application to write the user's calendar data.
     *
     * @param context contexto de aplicación [EN]  application context
     * @return verdadero para permisos habilitados [EN]  true for enabled permissions
     */
    public static boolean checkWriteCalendar(Context context) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            return true;
        }
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR);
        switch (result) {
            case PackageManager.PERMISSION_GRANTED:
                return true;
            case PackageManager.PERMISSION_DENIED:
                return false;
            default:
                return false;
        }
    }

    //CAMERA_______________________________________________________________________________________

    /**
     * Permiso de acceso a la camara
     * <p>
     * [EN]  Permission to access the camera
     *
     * @param context contexto de aplicación [EN]  application context
     * @return verdadero para permisos habilitados [EN]  true for enabled permissions
     */
    public static boolean checkCamera(Context context) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            return true;
        }
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
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
     * Comprobar si hay permisos de lectura  del disco
     * <p>
     * [EN]  Check for disk read permissions
     *
     * @param context contexto de aplicación [EN]  application context
     * @return verdadero para permisos habilitados [EN]  true for enabled permissions
     */
    public static boolean checkReadStoragedPermission(Context context) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            return true;
        }
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
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
     * Comprobar si hay permisos de escritura  en el disco
     * <p>
     * [EN]  Check for write permissions to disk
     *
     * @param context contexto de aplicación [EN]  application context
     * @return verdadero para permisos habilitados [EN]  true for enabled permissions
     */
    public static boolean checkWriteStoragedPermission(Context context) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            return true;
        }
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
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
     * Permiso de para consultar es estado del acceso a internet.
     * <p>
     * [EN]  Permission to consult is internet access status
     *
     * @param context contexto de aplicación [EN]  application context
     * @return verdadero para permisos habilitados [EN]  true for enabled permissions
     */
    public static boolean checkAccessNetWorkState(Context context) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            return true;
        }
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NETWORK_STATE);
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
     * Permiso para realizar llamadas de teléfono
     * <p>
     * [EN]  Permission to make phone calls
     *
     * @param context contexto de aplicación [EN]  application context
     * @return verdadero para permisos habilitados [EN]  true for enabled permissions
     */
    public static boolean checkCallPhone(Context context) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            return true;
        }
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE);
        switch (result) {
            case PackageManager.PERMISSION_GRANTED:
                return true;
            case PackageManager.PERMISSION_DENIED:
                return false;
            default:
                return false;
        }
    }
}
