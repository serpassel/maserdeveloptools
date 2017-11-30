package es.marser.backgroundtools.containers.dialogs.model;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author sergio
 *         Created by sergio on 20/10/17.
 *         Modelo de estado de las vistas
 *         <p>
 *         [EN]  View State Model
 */

@SuppressWarnings("unused")
public class StatusModel implements Parcelable {
    /**
     * Variable para bloqueo de accions
     * <p>
     * [EN]  Variable for stock blocking
     */
    public final ObservableBoolean blockAction = new ObservableBoolean(false);

    /**
     * Variable de definición de estado de la vista
     * <p>
     * [EN]  Event handler model in MVP views
     */
    public final ObservableInt state = new ObservableInt(0);

    public StatusModel() {
    }

    protected StatusModel(Parcel in) {
        blockAction.set(in.readByte() != 0);
        state.set(in.readInt());
    }

    public static final Creator<StatusModel> CREATOR = new Creator<StatusModel>() {
        @Override
        public StatusModel createFromParcel(Parcel in) {
            return new StatusModel(in);
        }

        @Override
        public StatusModel[] newArray(int size) {
            return new StatusModel[size];
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
        dest.writeByte((byte) (blockAction.get() ? 1 : 0));
        dest.writeInt(state.get());
    }
}
