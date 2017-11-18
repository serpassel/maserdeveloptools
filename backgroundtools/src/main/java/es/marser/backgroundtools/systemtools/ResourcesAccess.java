package es.marser.backgroundtools.systemtools;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import es.marser.LOG_TAG;
import es.marser.async.TaskLoadingResult;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.dialogs.model.HolidayModel;
import es.marser.backgroundtools.enums.Resources;
import es.marser.generic.GenericFactory;
import es.marser.tools.DateTools;
import es.marser.tools.MathTools;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 3/11/17.
 *         Recuperador de recursos de {@link R}
 *         <p>
 *         [EN]  Resource retrieval from {@link R}
 *         <ul>
 *         <il>General</il>
 *         <il>Holidays</il>
 *         <il>Territory</il>
 *         </ul>
 */

@SuppressWarnings("unused")
public class ResourcesAccess {

    //GENERAL_______________________________________________________________________________________

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
    public static String[] getStringArray(Context context, String name) {
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
    public static String getFilterOfArray(String[] in) {
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
     * @param context   contexto de la aplicación [EN]  context of the application
     * @param arrayName Nombre del arreglo [EN]  Arrangement name
     * @param value     valor buscado [EN]  sought value
     * @return índice de posición o -1 si no existe [EN]  position index or -1 if it does not exist
     */
    public static int getItemId(Context context, String arrayName, String value) {
        if (value == null) {
            return -1;
        }
        return Arrays.asList(getStringArray(context, arrayName)).indexOf(value);
    }

    //HOLIDAYS________________________________________________________________________________

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

    /**
     * Devuelve el texto asociado a una fecha festiva autonómica
     * <p>
     * [EN]  Returns the text associated with a regional festive date
     *
     * @param context  contexto de la aplicación [EN]  context of the application
     * @param calendar fecha de referencia [EN]  reference date
     * @return Listado de las comunidades autónomas donde la fecha es festiva
     * [EN]  List of autonomous communities where the date is festive
     */
    public static String getHolidayText(Context context, GregorianCalendar calendar) {
        GregorianCalendar gc = DateTools.nc(DateTools.resetTime(calendar));
        int year = gc.get(Calendar.YEAR);
        /*recuperar el índice del día en la lista festiva [EN]  retrieve the index of the day in the festive list*/
        int index = getItemId(context, "autonomy_holidays_" + year, DateTools.formatComparative(gc));

        if (index < 0) {
            return "";
        }
        /*recuperar el arreglo de textos [EN]  recover the arrangement of texts*/
        String[] texts = getStringArray(context, "text_holidays_" + year);

        if (index > texts.length) {
            return "";
        }
        return texts[index];
    }

    /**
     * Devuelve una lista de objetos modelo de días festivos para un año concreto
     * <p>
     * [EN]  Returns a list of model holiday objects for a specific year
     *
     * @param context contexto de la aplicación [EN]  context of the application
     * @param year    año de referencia [EN]  reference year
     * @param result Objeto para procedmiento asíncrono [EN]  Object for asynchronous procedure
     * @return Lista de valores de objeto [EN]  List of object values {@link HolidayModel}
     */
    public static List<HolidayModel> getHolidays(Context context, int year, TaskLoadingResult<HolidayModel> result) {
        List<HolidayModel> out = new ArrayList<>();

        if (result != null) {
            result.onStart(null);
        }

        for (String s : getStringArray(context, "holidays_" + year)) {

            Log.i(LOG_TAG.TAG, "Entrada: " + s);

            HolidayModel holidayModel = GenericFactory.BuildSingleObject(HolidayModel.class, s);
            if (holidayModel != null) {
                if (result != null) {
                    result.onUpdate(holidayModel);
                }
                out.add(holidayModel);
            } else {
                if (result != null) {
                    result.onFailure(new NullPointerException("Error: " + s));
                }
            }
        }

        if (result != null) {
            result.onFinish(null);
        }
        return out;
    }

    /**
     * Devuelve una lista de objetos modelo de días festivos para un año concreto
     * <p>
     * [EN]  Returns a list of model holiday objects for a specific year
     *
     * @param context contexto de la aplicación [EN]  context of the application
     * @param year    año de referencia [EN]  reference year
     * @return Lista de valores de objeto [EN]  List of object values {@link HolidayModel}
     */
    public static List<HolidayModel> getHolidays(Context context, int year){
        return getHolidays(context,year, null);
    }

    //TERRITORY_______________________________________________________________________________

    /**
     * Recupera el listado de las comunidades autónomas
     * <p>
     * [EN]  Retrieve the list of the autonomous communities
     *
     * @param context contexto de la aplicación [EN]  context of the application
     * @return Listado de comunidade autónomas [EN]  List of autonomous communities
     */
    public static String[] getListAutonomousCommunities(Context context) {
        return getStringArray(context, "spain_ccaa");
    }

    /**
     * Recupera el listado de provincias
     * <p>
     * [EN]  Retrieve the list of provinces
     *
     * @param context contexto de la aplicación [EN]  context of the application
     * @return Listado de provincias [EN]  List of provinces
     */
    public static String[] getListProvinces(Context context) {
        return getStringArray(context, "spain_province");
    }

    /**
     * Recupera el listado de provincias
     * <p>
     * [EN]  Retrieve the list of provinces
     *
     * @param context contexto de la aplicación [EN]  context of the application
     * @param ccaaId  código de la comunidad autónoma [EN]  code of the autonomous community
     * @return Listado de provincias [EN]  List of provinces
     */
    public static String[] getListProvinces(Context context, int ccaaId) {
        return getStringArray(context, "spain_province_" + MathTools.formatCifra(ccaaId, 2));
    }

    /**
     * Recupera la lista de municipios para una provincia determinada
     * <p>
     * [EN]  Retrieve the list of municipalities for a given province
     *
     * @param context    contexto de la aplicación [EN]  context of the application
     * @param provinceId Código de la provincia [EN]  Code of the province
     * @return Listado de pueblos de la provincia [EN]  List of towns in the province
     */
    public static String[] getListVillages(Context context, int provinceId) {
        return getStringArray(context, "spain_villages_" + MathTools.formatCifra(provinceId, 2));
    }
}
