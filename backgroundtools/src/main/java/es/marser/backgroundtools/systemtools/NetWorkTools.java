package es.marser.backgroundtools.systemtools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;

import java.io.IOException;

/**
 * @author sergio
 *         Created by Sergio on 07/04/2017.
 *         Comprueba si el dispositivo tiene conexión a internent
 *         <p>
 *         [EN]  Check if the device has an internent connection
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public class NetWorkTools extends AppCompatActivity {
    private OnNetworkAirListener onNetworkAirListener;
    private Context context;

    public NetWorkTools() {
     }

    /**
     * Estado de la red. Método principal de la clase
     * <p>
     * [EN]  Status of the network.  Main class method
     *
     * @param context contexto de la aplicación [EN]  application context
     * @param onNetworkAirListener [EN]  Connection test result listener
     */
    public static void netStatus(Context context, OnNetworkAirListener onNetworkAirListener) {
        //Comprobar conexión [EN]  Check connection
        if (onNetworkAirListener != null) {
            Pair<Boolean, String> p = okPermission(context);
            onNetworkAirListener.isOnlineResult(p.first, p.second);
        }
    }


    /**
     * Comprueba si está activado el servicio de conexión de red
     * <p>
     * [EN]  Checks whether the network connection service is enabled
     *
     * @param context contexto de aplicación [EN]  application context
     * @return verdadero si está disponible el servicio [EN]  true if the service is available
     */
    public static boolean isNetDisponible(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo actNetInfo = connectivityManager.getActiveNetworkInfo();

        return (actNetInfo != null && actNetInfo.isConnected());
    }

    /**
     * Comprueba si el dispositivo tiene conexión hacia el exterior
     * <p>
     * [EN]  Check whether the device is connected to the outside
     *
     * @return Verdadero si tiene conexión [EN]  True if you have a connection
     */
    public static Boolean isOnlineNet() {

        Process p;
        try {
            p = Runtime.getRuntime().exec("ping -c 1 www.google.es");
            int val = p.waitFor();
            return val == 0;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Resultado de la comprobación
     * <p>
     * [EN]  Result of the test
     *
     * @param context contexto de la aplicación [EN]  application context
     * @return Pair con los valores del resultado del test [EN]  Pair with test result values
     */
    @SuppressWarnings("unchecked")
    private static Pair<Boolean, String> okPermission(Context context) {
        if (!isNetDisponible(context)) {
            return new Pair(false, "Compruebe su conexión a internet");
        } else if (!isOnlineNet()) {
            return new Pair(false, "Buscando red, espere y vuelva a intentarlo");
        } else {
            return new Pair(true, "Conectado");
        }
    }

    /**
     * Oyente de resultado del test de conexión
     * <p>
     *     [EN]  Connection test result listener
     */
    public interface OnNetworkAirListener {
        void isOnlineResult(boolean isconected, String msg);
    }
}

