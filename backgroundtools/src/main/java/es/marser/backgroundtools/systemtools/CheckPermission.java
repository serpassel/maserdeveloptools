package es.marser.backgroundtools.systemtools;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

/**
 * @author sergio
 *         Created by Sergio on 28/03/2017.
 *         Clase chequeadora de permisos disponibles
 *         <p>
 *         [EN]  Permissions checker class available
 */

@SuppressWarnings("unused")
public class CheckPermission extends Fragment {

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
     * Permiso de acceso a internet. El usuario no puede revocarlo
     * <p>
     * [EN]  Permission to access the internet.  [EN]  The user can not revoke it
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
