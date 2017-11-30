package es.marser.backgroundtools.containers.dialogs.model;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Parcel;
import android.os.Parcelable;


/**
 * @author sergio
 *         Created by Sergio on 06/09/2017.
 *         Objeto Observable para manejo de dialogos de progreso
 *         <p>
 *         [EN]  Observable Object for handling progress dialogs
 *
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public class DialogProgressModel extends DialogModel implements Parcelable{

    public final ObservableField<String> max = new ObservableField<>();//Longitud máxima de progreso [EN]  Maximum length of progress
    public final ObservableField<String> progress = new ObservableField<>();//progreso [EN]  progress
    public final ObservableBoolean indeterminate = new ObservableBoolean(true); //Barra indeterminada [EN]  Indeterminate bar
    public final ObservableField<String> progresstext = new ObservableField<>("");//Texto de progreso [EN]  Progress text
    public final ObservableField<String> error = new ObservableField<>("");//Mensaje de error [EN]  Error message

    public DialogProgressModel() {
        super();
    }

    protected DialogProgressModel(Parcel in) {
        super(in);
        max.set(in.readString());
        progress.set(in.readString());
        indeterminate.set(in.readByte() != 0);
        progresstext.set(in.readString());
        error.set(in.readString());
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
        super.writeToParcel(dest, flags);
        dest.writeString(max.get());
        dest.writeString(progress.get());
        dest.writeByte((byte) (indeterminate.get() ? 1 : 0));
        dest.writeString(progresstext.get());
        dest.writeString(error.get());
    }

    public static final Creator<DialogProgressModel> CREATOR = new Creator<DialogProgressModel>() {
        @Override
        public DialogProgressModel createFromParcel(Parcel in) {
            return new DialogProgressModel(in);
        }

        @Override
        public DialogProgressModel[] newArray(int size) {
            return new DialogProgressModel[size];
        }
    };
}
