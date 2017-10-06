package es.marser.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * @author sergio
 *         Created by Sergio on 31/03/2016.
 *         [ES]
 *         Herramientas básicas para el manejo de fechas
 *         1.- Marca temporal. Creación de marcas temporales con diferentes formatos
 *         2.- Formateo especial. Formateo para Bases de datos y archivos específicos
 *         3.- Formateo estandar.
 *         4.- Cálculos con fechas.
 *         5.- Rangos predefinidos
 *         <p>
 *         [EN]
 *         Basic tools for handling dates
 *         1.- Time Stamp. Creation of temporary marks with different formats
 *         2.- Special formatting.  Formatting for specific databases and files
 *         3.- Standard formatting.
 *         4.- Calculations with dates.
 *         5.- Predefined ranges
 */
@SuppressWarnings({"unused", "WeakerAccess", "SameParameterValue"})
public abstract class DateTools {

    public static final String comparativeFormatting = "ddMMyyyy"; //Variable de formateo comparativo [EN]  Comparative Format Variable
    public static final String separatorMark = "|"; //Marca separadora para registros en cadena [EN]  [EN]  Separator mark for string registers

    //1.- MARCAS TEMPORALES______________________________________________________________________
    //1..-[EN] TEMPORARY BRANDS_________________________________________________________________________

    /**
     * Utilidad para ID de Bases de datos
     * [EN] Database ID Utility
     *
     * @return String de los milisegundos del instante actual [EN] String of the milliseconds of the current instant
     */
    public static String getTimeStamp() {
        return new GregorianCalendar().getTimeInMillis() + "";
    }

    /**
     * Uso en vinculación de vistas
     * [EN] Use in view binding
     *
     * @param timeStamp Fecha hora en String de los milisegundos [EN] Date time in milliseconds String
     * @return String con una referencia para registros con la fecha actual en milisegundos (Id) ID: 000000000000 y la marca de fecha formateada
     * [EN] String with a reference for records with the current date in milliseconds (Id) ID: 000000000000 and the formatted date stamp
     */
    public static String getRef(String timeStamp) {
        GregorianCalendar gregorianCalendar = setTimeMilis(timeStamp);
        return "ID: " +
                TextTools.nc(timeStamp) +
                "  " +
                new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(gregorianCalendar.getTime());
    }

    /**
     * @param timeStamp String tiempo en milisegundos [EN] String time in milliseconds
     * @return GregorianCalendar con los milisegundos seteados o el día de hoy [EN] GregorianCalendar with the milliseconds set or today
     */
    public static GregorianCalendar setTimeMilis(String timeStamp) {
        GregorianCalendar gc = new GregorianCalendar();
        if (TextTools.isNumeric(timeStamp)) {
            gc.setTimeInMillis(Long.parseLong(timeStamp));
        }
        return gc;
    }

    /**
     * Utilidad para id de registros, y campos de marcas temporales, tipo creación, modificación
     * [EN] Utility for records id, and fields of temporary marks, type creation, modification
     *
     * @param timeStamp String temporal en milisegundos (Id) [EN] String in milliseconds (Id)
     * @return String con la fecha formateada dd-MMM-yyyy HH:mm:ss [EN] String with date formatted dd-MMM-yyyy HH: mm: ss
     */
    public static String getDateTime(String timeStamp) {
        GregorianCalendar gregorianCalendar = setTimeMilis(timeStamp);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        return sdf.format(gregorianCalendar.getTime());
    }

    /**
     * Referencia de registros
     * [EN] Records reference
     *
     * @param timeStamp String con la fecha en milisegundos [EN] String with date in milliseconds
     * @return Fecha formateada dd-MMM-yyyy [EN] Date formatted dd-MMM-yyyy
     */
    public static String getDate(String timeStamp) {
        GregorianCalendar gregorianCalendar = setTimeMilis(timeStamp);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        return sdf.format(gregorianCalendar.getTime());
    }

    /**
     * Referencia de registros
     * [EN] Records reference
     *
     * @param timeStamp String con la fecha en milisegundos [EN] String with date in milliseconds
     * @return Hora formateada en HH:mm:ss [EN] Time formatted in HH: mm: ss
     */
    public static String getTime(String timeStamp) {
        GregorianCalendar gregorianCalendar = setTimeMilis(timeStamp);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(gregorianCalendar.getTime());
    }

    /**
     * Fija la fecha y hora para los registros de una db
     * [EN] Set the date and time for the records of a db
     *@param time tiempo en milisegundos [EN]  time in milliseconds
     * @return String con el formato de BD [EN] String with DB format dd-MM-yyyy HH:mm:ss
     */
    public static String timeInMili(Integer time) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        if (time != null) {
            gregorianCalendar.setTimeInMillis(time);
        }
        return time != null ? sdf.format(gregorianCalendar.getTime()) : "";
    }

    //2.- FORMATEO ESPECIAL____________________________________________________________________________
    //2.- [EN] SPECIAL FORMAT____________________________________________________________________________

    //------------------------------SQLITE-----------------------------------------

    /**
     * @param gc Entrada de fecha con GregorianCalendar [EN] Date input with GregorianCalendar
     * @return Fecha formateada para uso en SQLite [EN] Formatted date for use in SQLite
     */
    public static String SQLiteDate(GregorianCalendar gc) {
        return gc != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(gc.getTime()) : "";
    }

    /**
     * @return Marca temporal actual para registros SQLite [EN] Current time stamp for SQLite records
     */
    public static String SQLiteDateNow() {
        return SQLiteDate(new GregorianCalendar());
    }

    /**
     * Utilidad para marcar momentos de creación y modificación en bases de datos
     * [EN] Useful to mark moments of creation and modification in databases
     *
     * @return Devuelve un String con la fecha actual formateada para SQLite [EN] Returns a String with the current date formatted for SQLite
     */
    public static String getTimeRefStamp() {
        return SQLiteDate(new GregorianCalendar());
    }

//------------------------------BC3-----------------------------------------

    /**
     * @param calendar Fecha de entrada [EN] Entry date
     * @return Fecha formateado con convenio BC3 [EN] Date formatted with BC3 agreement
     */
    public static String formatBC3Date(GregorianCalendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        return calendar != null ? sdf.format(calendar.getTime()) : "";
    }

    /**
     * @return Fecha actual formateada con convenio BC3 [EN] Current date formatted with BC3 agreement
     */
    public static String formatBC3DateNow() {
        return formatBC3Date(new GregorianCalendar());
    }

    /**
     * Función inversa de fecha en formato BC3
     * [EN] Reverse date function in format BC3
     *
     * @param ddmmyyyy String con la fecha en formato BC3 [EN] String with date in BC3 format
     * @return GregorianCalendar con la fecha parseada [EN] GregorianCalendar with parse date
     */
    public static GregorianCalendar parseShortDate(String ddmmyyyy) {
        if (TextTools.isEmpty(ddmmyyyy) || ddmmyyyy.length() < 6 || ddmmyyyy.length() > 8) {
            return new GregorianCalendar();
        }
        String day = ddmmyyyy.substring(0, 2);
        String month = ddmmyyyy.substring(2, 4);
        String year = ddmmyyyy.substring(4);
        Integer dayi, monthi, yeari;
        if (TextTools.isNumeric(day)) {
            dayi = Integer.parseInt(day);
        } else {
            return new GregorianCalendar();
        }
        if (TextTools.isNumeric(month)) {
            monthi = Integer.parseInt(month) - 1;
        } else {
            return new GregorianCalendar();
        }
        if (TextTools.isNumeric(year)) {
            yeari = Integer.parseInt(year);
            if (year.length() == 2) {
                yeari = yeari + 2000;
            }
        } else {
            return new GregorianCalendar();
        }
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.set(yeari, monthi, dayi, 0, 0, 0);
        return gregorianCalendar;
    }

//3.- FORMATEO ESTANDAR____________________________________________________________________________________
    //3.- STANDARD FORMAT______________________________________________________________________________________

    /**
     * @return Fecha actual con formateo corto [EN] Current date with short format
     */
    public static String formatShortDateNow() {
        return formatShortDate(new GregorianCalendar());
    }

    /**
     * Formateo de fecha corto
     * [EN] Short date formatting
     *
     * @param calendar Objeto de fecha [EN] Date Object
     * @return String con la fecha en formato dd-MM-yyyy [EN] String with date in dd-MM-yyyy format
     **/
    public static String formatShortDate(GregorianCalendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return calendar != null ? sdf.format(calendar.getTime()) : "";
    }

    /**
     * Fecha para firmas de documentos
     * [EN] Date for document signatures
     *
     * @param calendar Objecto de fecha [EN] Date Object
     * @return String con la fecha en formato largo [EN] Date for document signatures "dd 'de' MMMM 'de' yyyy"
     */
    public static String formatLongDate(GregorianCalendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");
        return calendar != null ? sdf.format(calendar.getTime()) : "";
    }

    /**
     * @param gc Fecha entrada [EN]  Input date
     * @return Fecha con formato comparativo [EN]  Date with comparative format
     */
    public static String formatComparative(GregorianCalendar gc) {
        SimpleDateFormat sdf = new SimpleDateFormat(comparativeFormatting);
        return sdf.format(nc(gc).getTime());
    }

    //4.- CALCULOS CON FECHAS____________________________________________________________________________________
    // 4.- CALCULATIONS WITH DATES_______________________________________________________________________________

    /**
     * @param in Fecha entrada [EN]  Input date
     * @return día 0 si la entrada es nula. En caso contrario devuelve entrada [EN]  [EN]  day 0 if input is null.  Otherwise returns input
     */
    public static GregorianCalendar nc(GregorianCalendar in) {
        return in != null ? in : new GregorianCalendar(0, 0, 0, 0, 0, 0);
    }

    /**
     * @param in Fecha entrada [EN]  Entry date
     * @return true si es el día 0 [EN]  true if it's day 0
     */
    public static boolean isEmpty(GregorianCalendar in) {
        return sameDay(nc(in), nc(null));
    }

    /**
     * Comprueba si dos fechas son el mismo día
     * [EN] Check if two dates are the same day
     *
     * @param calendar1 input1
     * @param calendar2 input2
     * @return true si son el mismo día y no son nulos [EN] true if they are the same day and are not null
     */
    public static boolean sameDay(GregorianCalendar calendar1, GregorianCalendar calendar2) {
        return calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR)
                && calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR);
    }
    //Funciones

    /**
     * @param in Fecha de análisis [EN] Date of analysis
     * @return primer día del mes sobre la fecha de entrada [EN] first day of the month on the date of entry
     */
    public static GregorianCalendar firstDaysOfTheMonth(GregorianCalendar in) {
        GregorianCalendar out = new GregorianCalendar();
        if (in != null) {
            out.set(Calendar.YEAR, in.get(Calendar.YEAR));
            out.set(Calendar.MONTH, in.get(Calendar.MONTH));
        }
        out.set(Calendar.DAY_OF_MONTH, 1);
        return out;
    }

    /**
     * @param in Fecha de análisis [EN] Date of analysis
     * @return último del día del mes sobre la fecha de entrada [EN] last day of the month on the date of entry
     */
    public static GregorianCalendar lastDayOfTheMonth(GregorianCalendar in) {
       /*Posicionamos el primer día del mes [EN] We place the first day of the month*/
        GregorianCalendar out = firstDaysOfTheMonth(in);
        /*Añadir un mes [EN] Add a month*/
        out.add(Calendar.MONTH, 1);
        /* Restar un día [EN] Subtract one day */
        out.add(Calendar.DAY_OF_MONTH, -1);
        return out;
    }

    /**
     * Reseteo de horas
     * [EN]  Weather Reset
     *
     * @param in Fecha a resetear [EN]  Date to reset
     * @return Fecha con hora 0 [EN]  Date with time 0
     */
    public static GregorianCalendar resetTime(GregorianCalendar in) {
        if (in != null) {
            in.set(Calendar.HOUR_OF_DAY, 0);
            in.set(Calendar.MINUTE, 0);
            in.set(Calendar.SECOND, 0);
            in.set(Calendar.MILLISECOND, 0);
        }
        return in;
    }

    /**
     * @param in Fecha de entrada [EN] Input date
     * @return Clona la fecha resetando las horas [EN] Clone the date resetting hours
     */
    public static GregorianCalendar cloneDayResetTime(GregorianCalendar in) {
        GregorianCalendar out = new GregorianCalendar();
        if (in != null) {
            out.set(Calendar.YEAR, in.get(Calendar.YEAR));
            out.set(Calendar.MONTH, in.get(Calendar.MONTH));
            out.set(Calendar.DAY_OF_MONTH, in.get(Calendar.DAY_OF_MONTH));
        }
        return resetTime(out);
    }

    /**
     * @param start  Fecha del valor a comprobar [EN]  Date of value to be checked
     * @param filter filtro de días festivos [EN]  Public holiday filter
     * @return true si es día hábil [EN]  [EN]  true if business day
     */
    public static boolean isBusinessDay(GregorianCalendar start, String filter) {
        return start != null && (
                start.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY
                        && start.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY)
                && !TextTools.nc(filter).contains(formatComparative(start));
    }

    /**
     * @param start Fecha del valor a comprobar [EN]  Date of value to be checked
     * @return true si el día no es ni sábado ni domingo [EN]  true if the day is neither Saturday nor Sunday
     */
    public static boolean isBusinessDay(GregorianCalendar start) {
        return isBusinessDay(start, null);
    }

    /**
     * Cuenta los días hábiles entre dos fechas excluyendo el primer día
     * Count the business days between two dates excluding the first day
     *
     * @param start Fecha de inicio [EN] Start date
     * @param end   Fecha de finalizacion [EN] End date
     * @return días hábiles entre ambas fechas. Excluye sábados y domingos [EN] business days between the two dates.  Excludes Saturdays and Sundays
     */
    public static int businessDays(GregorianCalendar start, GregorianCalendar end) {
        int counter = 0;
        /*Si es el mismo día devolvemos 0
       * [EN] If it is the same day we return 0*/
        if (sameDay(start, end)) {
            return 0;
        }
        Pair customizepair = sortDateRangeAndResetTime(start, end);
        /*Recorrer todos los días entre ambas fechas [EN] Ride every day between both dates*/
        while (!sameDay(customizepair.start, customizepair.end)) {
            customizepair.start.add(Calendar.DAY_OF_MONTH, 1);
            if (isBusinessDay(customizepair.start)) {
                ++counter;
            }
        }
        return counter;
    }

    /**
     * @param in     Fecha inicial [EN]  Start date
     * @param days   Número de días hábiles a sumar [EN]  Number of working days to add
     * @param filter Días festivos [EN] Public Holidays
     * @return Fecha calculada [EN]  Estimated date
     */
    public static GregorianCalendar addBusinessDays(GregorianCalendar in, int days, String filter) {
        int counter = 0;
        while (counter < days) {
            /*añadir un día a fecha entrada [EN]  add a day to date entry */
            in.add(Calendar.DAY_OF_MONTH, 1);
            /*Comprobar si es día hábil [EN]  [EN]  Check if it is a business day*/
            if (isBusinessDay(in, filter)) {
                ++counter;
            }
        }
        return in;
    }

    /**
     * @param in   Fecha inicial [EN]  Start date
     * @param days Número de días hábiles a sumar [EN]  Number of working days to add
     * @return Fecha calculada [EN]  Estimated date
     */
    public static GregorianCalendar addBusinessDays(GregorianCalendar in, int days) {
        return addBusinessDays(in, days, null);
    }

    /**
     * @param start fecha desde excluida [EN]  date from excluded
     * @param end fecha hasta incluida [EN]  date up included
     * @param holidays Lista de días festivos [EN]  Holiday List
     * @return Número de días hábiles [EN]  Holiday List
     */
    public static int businessDays(GregorianCalendar start, GregorianCalendar end, GregorianCalendar[] holidays) {
        int counter = 0;
        /*Si es el mismo día devolvemos 0
       * [EN] If it is the same day we return 0*/
        if (sameDay(start, end)) {
            return 0;
        }
        Pair pair = sortDateRangeAndResetTime(start, end);

          /*Generamos el filtro de fechas festivas [EN] We generate the holiday filter*/
        String holidayfilter = filterBusinessListBetween(pair, holidays);

        /*Recorrer todos los días entre ambas fechas [EN] Ride every day between both dates*/
        while (!sameDay(pair.start, pair.end)) {
            pair.start.add(Calendar.DAY_OF_MONTH, 1);
            if (!isBusinessDay(pair.start, holidayfilter)) {
                /*Comprobar si */
                ++counter;
            }
        }
        return counter;
    }

    /**
     * Cuenta cuantas veces aparece un día de la semana entre dos fechas excluyendo el primer día
     * [EN] Count how many times a day of the week appears between two dates excluding the first day
     *
     * @param start        Fecha de inicio [EN] Start date
     * @param end          Fecha de finalizacion [EN] End date
     * @param dayoftheweek día de la semana 1 domingo 7 lunes [EN]  weekday 1 Sunday 7 Monday (1-7)
     * @return Contar el día de la semana. Comienzo excluido [EN] Count the day of the week. Excluded start
     */
    public static int countTheDayOfTheWeek(GregorianCalendar start, GregorianCalendar end, int dayoftheweek) {
        int counter = 0;
        /*Si es el mismo día devolvemos 0
       * [EN] If it is the same day we return 0*/
        if (sameDay(start, end) || dayoftheweek < 1 || dayoftheweek > 7) {
            return 0;
        }
        Pair pair = sortDateRangeAndResetTime(start, end);

        /*Recorrer todos los días entre ambas fechas [EN] Ride every day between both dates*/
        while (!sameDay(pair.start, pair.end)) {
            pair.start.add(Calendar.DAY_OF_MONTH, 1);
            if (pair.start.get(Calendar.DAY_OF_WEEK) == dayoftheweek) {
                ++counter;
            }
        }
        return counter;
    }

    /**
     * Entre dos fechas [EN]  Between two date
     *
     * @param start Fecha inicial para comparar [EN]  Initial date to compare
     * @param end   Fecha final para comparar [EN]  End date to compare
     * @param in    Fecha a comparar [EN]  Date to compare
     * @return true si in está entre ambas fechas [EN]  if in is between both dates
     */

    public static boolean betweenTwoDate(GregorianCalendar start, GregorianCalendar end, GregorianCalendar in) {
        /*Comprobar nulos [EN]  Check for nulls*/
        if (start == null || end == null || in == null) {
            return false;
        }
        /*Si es el mismo día devolvemos true [EN] If it is the same day we return true*/
        if (sameDay(start, end) && sameDay(start, in)) {
            return true;
        }

        /*Ordenar fechas y resetar horas [EN]  Sort dates and reset hours es*/
        Pair customizePair = sortDateRangeAndResetTime(start, end);
        GregorianCalendar ini = cloneDayResetTime(in);
        return customizePair.start.before(ini) && (customizePair.end.after(ini) || sameDay(customizePair.end, ini));
    }

    /**
     * @param start Fecha inicial [EN]  Start date
     * @param end   Fecha final [EN] End date
     * @return Par de fechas ordenadas [EN]  Pair of ordered dates
     */
    private static Pair sortDateRangeAndResetTime(GregorianCalendar start, GregorianCalendar end) {
        GregorianCalendar starti;
        GregorianCalendar endi;

        /*Comprobar que la fechas están ordenadas [EN] Check that the dates are sorted*/
        if (start.before(end)) {
            starti = cloneDayResetTime(nc(start));
            endi = cloneDayResetTime(nc(end));
        } else {
            /*Ordenar fechas [EN] Sort dates*/
            starti = cloneDayResetTime(nc(end));
            endi = cloneDayResetTime(nc(start));
        }
        return new Pair(starti, endi);
    }

    /**
     * @param pair Rango de fechas. Se excluye a primera. [EN]  Date range.  The first is excluded.
     * @param list Lista de fechas para filtrar [EN]  List of dates to filter
     * @return Lista filtrada en formato comparativo y separadas por la marca de la clase [EN]  List filtered in comparative format and separated by the class mark
     */
    public static String filterListBetween(
            Pair pair,
            GregorianCalendar[] list) {

        String out = "";
        /*Ordenar fechas y resetar horas [EN]  Sort dates and reset hours es*/
        for (GregorianCalendar gc : list) {
            if (betweenTwoDate(pair.start, pair.end, gc)) {
                out += formatComparative(gc) + DateTools.separatorMark;
            }
        }
        return out;
    }

    /**
     * @param pair Rango de fechas [EN]  Date range
     * @param list Lista de fechas [EN]  List of dates
     * @return Lista filtrada con días hábiles en formato comparativo y separadas por la marca de la clase
     * [EN]  List filtered with business days in comparative format and separated by the mark of the class
     */
    public static String filterBusinessListBetween(
            Pair pair,
            GregorianCalendar[] list) {

        String out = "";
        /*Ordenar fechas y resetar horas [EN]  Sort dates and reset hours es*/
        for (GregorianCalendar gc : list) {
            if (betweenTwoDate(pair.start, pair.end, gc) && isBusinessDay(gc)) {
                out += formatComparative(gc) + DateTools.separatorMark;
            }
        }
        return out;
    }


//5.- Rangos predefinidos___________________________________________________________________________________________
    //5.- Predefined ranges___________________________________________________________________________________________

    /**
     * @param year año de cálculo [EN]  year of calculation
     * @return rango de fechas para el año indicado [EN]  range of dates for the indicated year
     */
    public static Pair yearRange(int year) {
        return new Pair(new GregorianCalendar(year - 1, 11, 31), new GregorianCalendar(year, 11, 31));
    }

    /**
     * @param year  año de cálculo [EN]  year of calculation
     * @param month mes de cálculo [EN]  month of calculation
     * @return rango de fechas para el año y mes indicados [EN]  range of dates for the indicated year and month
     */
    public static Pair monthRange(int year, int month) {
        GregorianCalendar start = resetTime(new GregorianCalendar());
        /*Posicionar primer día del mes [EN]  Position first day of the month*/
        start.set(Calendar.YEAR, year);
        start.set(Calendar.MONTH, month);
        start.set(Calendar.DAY_OF_MONTH, 1);
        /*Posicionar el último día del mes anterior [EN]  Position the last day of the previous month*/
        start.add(Calendar.DAY_OF_MONTH, -1);
        GregorianCalendar end = resetTime(new GregorianCalendar());
        end.set(Calendar.YEAR, year);
        end.set(Calendar.MONTH, month);
        /*Posicionar en el último día del mes [EN]  Position on the last day of the month*/
        end = lastDayOfTheMonth(end);
        return new Pair(start, end);
    }

    /**
     * Par de fechas [EN]  Pair of dates
     */
    @SuppressWarnings({"CanBeFinal", "unused"})
    public static class Pair {
        public GregorianCalendar start;
        public GregorianCalendar end;

        public Pair() {
            this.start = nc(null);
            this.end = nc(null);
        }

        public Pair(GregorianCalendar first, GregorianCalendar second) {
            this.start = nc(first);
            this.end = nc(second);
        }

        @Override
        public String toString() {
            return "Pair|" + DateTools.formatComparative(start) + "|" + DateTools.formatComparative(end);
        }
    }
}
