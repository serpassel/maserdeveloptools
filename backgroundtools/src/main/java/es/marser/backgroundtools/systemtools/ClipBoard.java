package es.marser.backgroundtools.systemtools;

import android.content.Context;

import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by Sergio on 28/06/2017.
 *         Portapapeles de objetos interno para aplicaciones
 *         <p>
 *         [EN]  Internal object clipboard for applications
 */

@SuppressWarnings("unused")
public abstract class ClipBoard {

    /*Nombre de la clave de la variable [EN]  Name of the variable key*/
    public static final String CLIPBOARD_KEY = "clipboard";
    /*Separador de registros [EN]  Record separator*/
    public static final String CLIPBOARD_SEPARATOR = TextTools.CLIPBOARD_SEPARATOR;

    /**
     * Introducir valor en el portapapeles
     * <p>
     * [EN]  Enter value in clipboard
     *
     * @param context contexto de la aplicación [EN]  application context
     * @param value   Valor a introducir [EN]  Value to introduce
     */
    public static void setValue(Context context, String value) {
        try {
            SharedPreferenceTools.setString(context, TextTools.nc(value), CLIPBOARD_KEY);
        } catch (Exception ignored) {
        }
    }

    /**
     * Recuperar valor del portapapeles
     * <p>
     * [EN]  Retrieve clipboard value
     *
     * @param context contexto de la aplicación [EN]  application context
     * @return Cadena de texto con los datos del portapapeles [EN]  Text string with clipboard data
     */
    public static String getValue(Context context) {
        return SharedPreferenceTools.getString(context, "", CLIPBOARD_KEY);
    }

    /**
     * Vaciar el portapapeles
     * <p>
     * [EN]  Empty the clipboard
     *
     * @param context contexto de la aplicación [EN]  application context
     */
    public static void clear(Context context) {
        try {
            setValue(context, "");
        } catch (Exception ignored) {
        }
    }

    /**
     * Comprobar si el portapales está vacío
     * <p>
     * [EN]  Check if the carrier is empty
     *
     * @param context contexto de la aplicación [EN]  application context
     * @return verdadero si está vacío [EN]  true if empty
     */
    public static boolean isEmpty(Context context) {
        return TextTools.isEmpty(getValue(context));
    }

    /**
     * Recuperar el primer registro del portapapeles
     * <p>
     * [EN]  Recover the first record from the clipboard
     *
     * @param context contexto de la aplicación [EN]  application context
     * @return Cadena de texto con el primer registro del portapaleles [EN]  Text string with the first register of the portapaleles
     */
    public static String getFirst(Context context) {
        String reg = getValue(context);
        if (TextTools.isEmpty(reg)) {
            return "";
        }
        return reg.substring(0, reg.indexOf(CLIPBOARD_SEPARATOR));
    }
}
