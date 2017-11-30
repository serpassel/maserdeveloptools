package es.marser.backgroundtools.containers.dialogs.model;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Parcel;
import android.os.Parcelable;

import es.marser.backgroundtools.enums.DialogIcon;


/**
 * @author sergio
 *         Created by Sergio on 06/09/2017.
 *         Objeto Observable para manejo de dialogos de notificaciones
 *         <p>
 *         [EN]  Observable object for handling notifications dialogs
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public class DialogModel implements Parcelable {

    public final ObservableField<String> title = new ObservableField<>("NOTIFICACION");//Título de carga [EN]  Loading title
    public final ObservableField<String> body = new ObservableField<>("");//Texto del cuerpo [EN]  Body text
    public final ObservableField<DialogIcon> icon = new ObservableField<>();//Icono del título [EN]  Title Icon
    public final ObservableBoolean key = new ObservableBoolean(false); //Clave de reiteración [EN]  Repeat Key
    public final ObservableField<String> keyname = new ObservableField<>("");//Nombre de la clave de reiteración [EN]  Name of the reiteration key

    public DialogModel() {
    }

    protected DialogModel(Parcel in) {
        title.set(in.readString());
        body.set(in.readString());
        icon.set(DialogIcon.valueOf(in.readString()));
        key.set(in.readByte() != 0);
        keyname.set(in.readString());
    }

    public static final Creator<DialogModel> CREATOR = new Creator<DialogModel>() {
        @Override
        public DialogModel createFromParcel(Parcel in) {
            return new DialogModel(in);
        }

        @Override
        public DialogModel[] newArray(int size) {
            return new DialogModel[size];
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
        dest.writeString(title.get());
        dest.writeString(body.get());
        dest.writeString(icon.get().name());
        dest.writeByte((byte) (key.get() ? 1 : 0));
        dest.writeString(keyname.get());
    }
}
