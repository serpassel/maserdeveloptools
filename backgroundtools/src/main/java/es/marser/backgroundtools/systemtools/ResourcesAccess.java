package es.marser.backgroundtools.systemtools;

import android.content.Context;

import java.util.Arrays;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.enums.Resources;
import es.marser.tools.DateTools;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 3/11/17.
 *         Enumeración de recursos de android por tipología
 *         <p>
 *         [EN]  Enumeration of android resources by type
 */

public class ResourcesAccess {

    /**
     * lookup a resource id by field name in static R.class
     *
     * @param variableName - name of drawable, e.g R.drawable.<b>image</b>
     * @param с            - class of resource, e.g R.drawable.class or R.raw.class
     * @return integer id of resource
     * @author - ceph3us
     */
    public static int getResId(String variableName, Class<?> с)
            throws android.content.res.Resources.NotFoundException {
        try {
            // lookup field in class
            java.lang.reflect.Field field = с.getField(variableName);
            // always set access when using reflections
            // preventing IllegalAccessException
            field.setAccessible(true);
            // we can use here also Field.get() and do a cast
            // receiver reference is null as it's static field
            return field.getInt(null);
        } catch (Exception e) {
            // rethrow as not found ex
            throw new android.content.res.Resources.NotFoundException(e.getMessage());
        }
    }

    /**
     * Devuelve el Id de un recurso array
     * <p>
     * [EN]  Returns the ID of an array resource
     *
     * @param context Contexto de la aplicación [EN]  Context of the application
     * @param name    nombre de la variable [EN]  name of the variable
     * @return entero identificador de la variable [EN]  integer identifier of the variable
     * @throws Si el recurso no existe lanza un error [EN]  If the resource does not exist, it throws an error
     */
    public static int getResArrayId(Context context, String name) throws android.content.res.Resources.NotFoundException {
        if (TextTools.isEmpty(name)) {
            return 0;
        }
        return context.getResources().getIdentifier(name, Resources.array.name(), context.getPackageName());
    }

    /**
     * Devuelve un arreglo de un recurso {@link R.array}
     * <p>
     * [EN]  Returns an array of a resource {@link R.array}
     *
     * @param context contexto de la aplicación [EN]  context of the application
     * @param name    de la variable [EN]  variable
     * @return arreglo de un recurso {@link R.array}
     */
    private static String[] getStringArray(Context context, String name) {
        int id = getResArrayId(context, name);
        if (id <= 0) {
            return new String[]{};
        }
        try {
            return context.getResources().getStringArray(id);
        } catch (android.content.res.Resources.NotFoundException e) {
            return new String[]{};
        }
    }

    /**
     * Devuelve el filtro de registros separados por {@link es.marser.tools.TextTools#OBJECT_SEPARATOR}
     * <p>
     * [EN]  Returns the record filter separated by {@link es.marser.tools.TextTools
     *
     * @param in Arreglo con los datos [EN]  Arrangement with the data
     * @return Cadena de texto con el filtro [EN]  Text string with filter
     */
    private static String getFilterOfArray(String[] in) {
        StringBuilder builder = new StringBuilder();
        for (String s : in) {
            builder.append(s).append(DateTools.separatorMark);
        }
        return builder.toString();
    }

    /**
     * Devuelve el índice de posición en una lista de {@link R.array}, para un valor dado
     * <p>
     * [EN]  Return the position index in a list of {@link R.array}, for a given value
     *
     * @param context contexto de la aplicación [EN]  context of the application
     * @param arrayName Nombre del arreglo [EN]  Arrangement name
     * @param value valor buscado [EN]  sought value
     * @return índice de posición o -1 si no existe [EN]  position index or -1 if it does not exist
     */
    public static int getItemId(Context context, String arrayName, String value) {
        if (value == null) {
            return -1;
        }
        return Arrays.asList(getStringArray(context, arrayName)).indexOf(value);
    }

    /**
     * Devuelve los días festivos de un año
     * <p>
     * [EN]  Returns the one-year holidays
     *
     * @param context contexto de la aplicación [EN]  context of the application
     * @param year    año de búsqueda [EN]  year of search
     * @return Array con los días festivos o array vacío [EN]  Array with holidays or empty array
     */
    public static String[] getNationalHolidays(Context context, int year) {
        return getStringArray(context, "national_holidays_" + year);
    }

    /**
     * Devuelve el filtro de fechas para el periodo vacacional del año indicado
     * <p>
     * [EN]  Returns the filter of dates for the holiday period of the indicated year
     *
     * @param context contexto de la aplicación [EN]  context of the application
     * @param year    año de búsqueda [EN]  year of search
     * @return Cadena de texto con el filtro [EN]  Text string with filter
     */
    public static String getNatinoalHolidaysFilter(Context context, int year) {
        return getFilterOfArray(getNationalHolidays(context, year));
    }

    /**
     * Devuelve los días festivos autonómicos de un año
     * <p>
     * [EN]  Returns the one-year autonomous holidays
     *
     * @param context contexto de la aplicación [EN]  context of the application
     * @param year    año de búsqueda [EN]  year of search
     * @return Array con los días festivos o array vacío [EN]  Array with holidays or empty array
     */
    public static String[] getAutonomyHolidays(Context context, int year) {
        return getStringArray(context, "autonomy_holidays_" + year);
    }

    /**
     * Devuelve el filtro de fechas autonómicas para el periodo vacacional del año indicado
     * <p>
     * [EN]  Returns the filter of [EN]  autonomic dates for the holiday period of the indicated year
     *
     * @param context contexto de la aplicación [EN]  context of the application
     * @param year    año de búsqueda [EN]  year of search
     * @return Cadena de texto con el filtro [EN]  Text string with filter
     */
    public static String getAutonomyHolidaysFilter(Context context, int year) {
        return getFilterOfArray(getAutonomyHolidays(context, year));
    }


}
