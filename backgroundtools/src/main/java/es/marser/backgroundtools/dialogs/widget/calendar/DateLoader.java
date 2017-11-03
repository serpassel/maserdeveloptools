package es.marser.backgroundtools.dialogs.widget.calendar;

import java.util.GregorianCalendar;

/**
 * Clase para configurar a carga de fechas asíncrona
 * <p>
 * [EN]  Class to configure asynchronous date loading
 */
@SuppressWarnings("unused")
public class DateLoader {

    private GregorianCalendar calendar;
    private String holidayFilter;
    private String otherHolidayFilter;


    public DateLoader(GregorianCalendar calendar, String holidayFilter, String otherHolidayFilter) {
        this.calendar = calendar;
        this.holidayFilter = holidayFilter;
        this.otherHolidayFilter = otherHolidayFilter;
    }

    public GregorianCalendar getCalendar() {
        return calendar;
    }

    public void setCalendar(GregorianCalendar calendar) {
        this.calendar = calendar;
    }

    public String getHolidayFilter() {
        return holidayFilter;
    }

    public void setHolidayFilter(String holidayFilter) {
        this.holidayFilter = holidayFilter;
    }

    public String getOtherHolidayFilter() {
        return otherHolidayFilter;
    }

    public void setOtherHolidayFilter(String otherHolidayFilter) {
        this.otherHolidayFilter = otherHolidayFilter;
    }
}