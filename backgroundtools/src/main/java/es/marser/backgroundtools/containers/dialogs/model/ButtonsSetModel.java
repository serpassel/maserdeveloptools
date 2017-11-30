package es.marser.backgroundtools.containers.dialogs.model;

import android.databinding.ObservableField;
import android.os.Parcel;
import android.os.Parcelable;


/**
 * @author sergio
 *         Created by Sergio on 06/09/2017.
 *         Objeto Observable para manejo de dialogos de notificaciones
 *         <p>
 *         [EN]  Observable object for handling notifications dialogs
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public class ButtonsSetModel implements Parcelable {
    public final ObservableField<String> ok_name = new ObservableField<>();//Título de carga [EN]  Loading title
    public final ObservableField<String> cancel_name = new ObservableField<>();//Texto del cuerpo [EN]  Body text
    public final ObservableField<String> option_name = new ObservableField<>();//Nombre de la clave de reiteración [EN]  Name of the reiteration key

    public ButtonsSetModel() {
    }

    protected ButtonsSetModel(Parcel in) {
        ok_name.set(in.readString());
        cancel_name.set(in.readString());
        option_name.set(in.readString());
    }

    public static final Creator<ButtonsSetModel> CREATOR = new Creator<ButtonsSetModel>() {
        @Override
        public ButtonsSetModel createFromParcel(Parcel in) {
            return new ButtonsSetModel(in);
        }

        @Override
        public ButtonsSetModel[] newArray(int size) {
            return new ButtonsSetModel[size];
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
        dest.writeString(ok_name.get());
        dest.writeString(cancel_name.get());
        dest.writeString(option_name.get());
    }
}

