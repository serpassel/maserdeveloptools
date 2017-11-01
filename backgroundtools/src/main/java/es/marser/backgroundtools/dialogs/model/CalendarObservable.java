package es.marser.backgroundtools.dialogs.model;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.GregorianCalendar;

import es.marser.backgroundtools.BR;
import es.marser.tools.DateTools;


@SuppressWarnings("unused")
public class CalendarObservable extends BaseObservable implements Parcelable {

    private GregorianCalendar calendar;

    public CalendarObservable() {
        this.calendar = new GregorianCalendar();
    }

    public CalendarObservable setYear(int year) {
        calendar.set(Calendar.YEAR, year);
        notifyPropertyChanged(BR.year);
        notifyPropertyChanged(BR.calendar);
        return this;
    }

    @Bindable
    public int getYear() {
        return calendar.get(Calendar.YEAR);
    }

    public CalendarObservable setMonth(int month) {
        calendar.set(Calendar.MONTH, month);
        notifyPropertyChanged(BR.month);
        notifyPropertyChanged(BR.calendar);
        return this;
    }

    @Bindable
    public int getMonth() {
        return calendar.get(Calendar.MONTH);
    }

    public CalendarObservable setDay(int day) {
        calendar.set(Calendar.DAY_OF_MONTH, day);
        notifyPropertyChanged(BR.day);
        notifyPropertyChanged(BR.calendar);
        return this;
    }

    @Bindable
    public int getDay() {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public CalendarObservable setHour(int hour) {
       calendar.set(Calendar.HOUR_OF_DAY, hour);
        notifyPropertyChanged(BR.hour);
        notifyPropertyChanged(BR.calendar);
        return this;
    }

    @Bindable
    public int getHour() {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public CalendarObservable setMinute(int minute) {
        calendar.set(Calendar.MINUTE, minute);
        notifyPropertyChanged(BR.minute);
        notifyPropertyChanged(BR.calendar);
        return this;
    }

    @Bindable
    public int getMinute() {
        return calendar.get(Calendar.MINUTE);
    }

    public CalendarObservable setSecond(int second) {
        calendar.set(Calendar.SECOND, second);
        notifyPropertyChanged(BR.second);
        notifyPropertyChanged(BR.calendar);
        return this;
    }

    @Bindable
    public int getSecond() {
        return calendar.get(Calendar.SECOND);
    }

    public CalendarObservable setCalendar(GregorianCalendar calendar) {
        this.calendar = calendar;
        notifyPropertyChanged(BR.calendar);
        notifyPropertyChanged(BR.calendar);
        return this;
    }

    @Bindable
    public GregorianCalendar getCalendar() {
        return this.calendar;
    }

    @Override
    public String toString() {
        return DateTools.formatLongDate(calendar);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(calendar.getTimeInMillis());

    }

    protected CalendarObservable(Parcel in) {
        calendar = new GregorianCalendar();
        calendar.setTimeInMillis(in.readLong());
    }

    public static final Parcelable.Creator<CalendarObservable> CREATOR = new Parcelable.Creator<CalendarObservable>() {
        @Override
        public CalendarObservable createFromParcel(Parcel in) {
            return new CalendarObservable(in);
        }
        @Override
        public CalendarObservable[] newArray ( int size){
            return new CalendarObservable[size];
        }
    };

}