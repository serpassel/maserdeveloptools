package es.marser.backgroundtools.systemtools;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by Sergio on 26/04/2017.
 *         Gestor de preferencias de usuario y variables globales
 *         <p>
 *         Simplifica el acceso a SharedPreferences
 *         <p>
 *         [EN]  User Preferences Manager and Global Variables
 *         <p>
 *         Simplifies access to SharedPreferences
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public class SharedPreferenceTools {
    /**
     * Recuperar variable booleana de tabla  por defecto
     * <p>
     * [EN]  Retrieve boolean table variable by default
     *
     * @param context      contexto de aplicación [EN]  application context
     * @param defaultValue Valor por defecto [EN]  Default value
     * @param key          clave de la varible [EN]  key of the variable
     * @return valor sin nulos [EN]  value without nulls
     */
    public static boolean getBoolean(Context context, boolean defaultValue, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(
                        key,
                        defaultValue);
    }

    /**
     * Recuperar variable booleana de tabla  personalizada
     * <p>
     * [EN]  Retrieve custom table Boolean variable
     *
     * @param context      contexto de aplicación [EN]  application context
     * @param defaultValue Valor por defecto [EN]  Default value
     * @param key          clave de la varible [EN]  key of the variable
     * @param preferences  tabla personalizada de preferencias [EN]  custom preferences table
     * @return valor sin nulos [EN]  value without nulls
     */
    public static boolean getBoolean(Context context, boolean defaultValue, String preferences, String key) {
        return context.getSharedPreferences(preferences, Context.MODE_PRIVATE)
                .getBoolean(
                        key,
                        defaultValue);
    }

    /**
     * Introducir variable booleana en tabla  personalizada
     * <p>
     * [EN]  Enter boolean variable in custom table
     *
     * @param context      contexto de aplicación [EN]  application context
     * @param defaultValue Valor por defecto [EN]  Default value
     * @param key          clave de la varible [EN]  key of the variable
     * @param preferences  tabla personalizada de preferencias [EN]  custom preferences table
     */
    public static void setBoolean(Context context, boolean defaultValue, String preferences, String key) {
        SharedPreferences prefs;
        if (TextTools.isEmpty(preferences)) {
            prefs =
                    PreferenceManager.getDefaultSharedPreferences(context);
        } else {
            prefs =
                    context.getSharedPreferences(preferences, Context.MODE_PRIVATE);

        }

        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, defaultValue);
        editor.apply();
    }

    /**
     * Recuperar variable de texto de tabla por defecto
     * <p>
     * [EN]  Retrieve default table text variable
     *
     * @param context      contexto de aplicación [EN]  application context
     * @param defaultValue Valor por defecto [EN]  Default value
     * @param key          clave de la varible [EN]  key of the variable
     * @return valor sin nulos [EN]  value without nulls
     */
    public static String getString(Context context, String defaultValue, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(
                        key,
                        defaultValue);
    }

    /**
     * Recuperar variable de texto de tabla por defecto. Cadena vacía como valor por defecto
     * <p>
     * [EN]  Retrieve default table text variable. [EN]  Empty string as default
     *
     * @param context contexto de aplicación [EN]  application context
     * @param key     clave de la varible [EN]  key of the variable
     * @return valor sin nulos. Cadena vacía por defecto [EN]  value without nulls, [EN]  Default empty string
     */
    public static String getString(Context context, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(
                        key,
                        "");
    }

    /**
     * Recuperar variable de texto de tabla personalizada
     * <p>
     * [EN]  Retrieve custom table text variable
     *
     * @param context      contexto de aplicación [EN]  application context
     * @param defaultValue Valor por defecto [EN]  Default value
     * @param key          clave de la varible [EN]  key of the variable
     * @param preferences  tabla personalizada de preferencias [EN]  custom preferences table
     * @return valor sin nulos [EN]  value without nulls
     */
    public static String getString(Context context, String defaultValue, String preferences, String key) {
        return context.getSharedPreferences(preferences, Context.MODE_PRIVATE)
                .getString(
                        key,
                        defaultValue);
    }

    /**
     * Introducir variable de texto en tabla  por defecto
     * <p>
     * [EN]  Enter text variable in default table
     *
     * @param context contexto de aplicación [EN]  application context
     * @param key     clave de la varible [EN]  key of the variable
     * @param value   Valor nuevo [EN]  New value
     */
    public static void setString(Context context, String value, String key) {
        SharedPreferences prefs;

        prefs =
                PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, TextTools.nc(value));
        editor.apply();
    }

    /**
     * Introducir variable de texto en tabla personalizada
     * <p>
     * [EN]  Enter Text Variable in Custom Table
     *
     * @param context     contexto de aplicación [EN]  application context
     * @param key         clave de la varible [EN]  key of the variable
     * @param value       Valor nuevo [EN]  New value
     * @param preferences tabla personalizada de preferencias [EN]  custom preferences table
     */
    public static void setString(Context context, String value, String preferences, String key) {
        SharedPreferences prefs;
        if (TextTools.isEmpty(preferences)) {
            prefs =
                    PreferenceManager.getDefaultSharedPreferences(context);
        } else {
            prefs =
                    context.getSharedPreferences(preferences, Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, TextTools.nc(value));
        editor.apply();
    }

    /**
     * Recuperar variable numérica de valor entero de tabla por defecto
     * <p>
     * [EN]  Recover numeric variable from default table integer value
     *
     * @param context      contexto de aplicación [EN]  application context
     * @param defaultValue Valor por defecto [EN]  Default value
     * @param key          clave de la varible [EN]  key of the variable
     * @return valor sin nulos [EN]  value without nulls
     */
    public static Integer getInteger(Context context, Integer defaultValue, String key) {
        return Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(context)
                .getString(
                        key,
                        String.valueOf(defaultValue)));
    }

    /**
     * Eliminar variable en tabla de preferencias por defecto
     * <p>
     * [EN]  Delete variable in default preference table
     *
     * @param context    contexto de aplicación [EN]  application context
     * @param key        clave de la varible [EN]  key of the variable
     * @param preference tabla personalizada de preferencias [EN]  custom preferences table
     */
    public static void remove(Context context, String preference, String key) {
        SharedPreferences preferences = context.getSharedPreferences(preference, 0);
        preferences.edit().remove(key).apply();
    }

    /**
     * Eliminar variable de tabla de preferencias personalizada
     * <p>
     * [EN]  Remove custom preference table variable
     *
     * @param context contexto de aplicación [EN]  application context
     * @param key     clave de la varible [EN]  key of the variable
     */
    public static void remove(Context context, String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().remove(key).apply();
    }

    /**
     * Eliminar tabla de preferencias personalizada
     * <p>
     * [EN]  Delete custom preference table
     *
     * @param context    contexto de aplicación [EN]  application context
     * @param preference tabla personalizada de preferencias [EN]  custom preferences table
     */
    public static void clear(Context context, String preference) {
        SharedPreferences preferences = context.getSharedPreferences(preference, 0);
        preferences.edit().clear().apply();
    }
}
