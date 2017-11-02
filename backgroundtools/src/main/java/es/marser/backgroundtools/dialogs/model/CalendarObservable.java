package es.marser.backgroundtools.dialogs.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import es.marser.backgroundtools.BR;
import es.marser.tools.BooleanTools;
import es.marser.tools.DateTools;


@SuppressWarnings("unused")
public class CalendarObservable extends BaseObservable implements Parcelable {

    private GregorianCalendar calendar;
    private boolean holiday;
    private boolean othermonth;
    private boolean otherholiday;

    public CalendarObservable() {
        this(new GregorianCalendar());
    }

    public CalendarObservable(GregorianCalendar calendar) {
        this.calendar = DateTools.nc(calendar);
        this.holiday = false;
        this.othermonth = false;
        this.otherholiday = false;
    }

    public CalendarObservable setCalendar(GregorianCalendar calendar) {
        this.calendar = calendar;
        notifyPropertyChanged(BR.calendar);
        notifyPropertyChanged(BR.day);
        return this;
    }

    @Bindable
    public GregorianCalendar getCalendar() {
        return this.calendar;
    }

    public CalendarObservable setHoliday(boolean holiday) {
        this.holiday = holiday;
        notifyPropertyChanged(BR.holiday);
        return this;
    }

    @Bindable
    public boolean isHoliday() {
        return this.holiday;
    }

    public CalendarObservable setOthermonth(boolean othermonth) {
        this.othermonth = othermonth;
        notifyPropertyChanged(BR.othermonth);
        return this;
    }

    @Bindable
    public boolean isOthermonth() {
        return this.othermonth;
    }

    public CalendarObservable setOtherholiday(boolean otherholiday) {
        this.otherholiday = otherholiday;
        notifyPropertyChanged(BR.otherholiday);
        return this;
    }

    @Bindable
    public boolean getOtherholiday() {
        return this.otherholiday;
    }

    @Bindable
    public String getDay() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d");
        //Log.e(LOG_TAG.TAG, "Día " + dateFormat.format(calendar.getTime()));
        return dateFormat.format(calendar.getTime());
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
        dest.writeByte((byte) (BooleanTools.nc(holiday) ? 1 : 0));
        dest.writeByte((byte) (BooleanTools.nc(othermonth) ? 1 : 0));
        dest.writeByte((byte) (BooleanTools.nc(otherholiday) ? 1 : 0));
        dest.writeLong(calendar.getTimeInMillis());

    }

    protected CalendarObservable(Parcel in) {
        holiday = in.readByte() != 0;
        othermonth = in.readByte() != 0;
        otherholiday = in.readByte() != 0;
        calendar = new GregorianCalendar();
        calendar.setTimeInMillis(in.readLong());

    }

    public static final Parcelable.Creator<CalendarObservable> CREATOR = new Parcelable.Creator<CalendarObservable>() {
        @Override
        public CalendarObservable createFromParcel(Parcel in) {
            return new CalendarObservable(in);
        }

        @Override
        public CalendarObservable[] newArray(int size) {
            return new CalendarObservable[size];
        }
    };

}