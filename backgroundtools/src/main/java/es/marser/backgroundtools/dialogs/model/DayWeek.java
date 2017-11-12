package es.marser.backgroundtools.dialogs.model;

import android.databinding.ObservableField;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author sergio
 *         Created by sergio on 1/11/17.
 *         Días de la semana
 *         <p>
 *         [EN]  Weekdays
 */

@SuppressWarnings("unused")
public class DayWeek implements Parcelable{
    public final ObservableField<String> day = new ObservableField<>();//Título de carga [EN]  Loading title

    public DayWeek() {
    }

    protected DayWeek(Parcel in) {
        day.set(in.readString());
    }

    public static final Creator<DayWeek> CREATOR = new Creator<DayWeek>() {
        @Override
        public DayWeek createFromParcel(Parcel in) {
            return new DayWeek(in);
        }

        @Override
        public DayWeek[] newArray(int size) {
            return new DayWeek[size];
        }
    };

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(day.get());
    }
}
