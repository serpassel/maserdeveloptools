package es.marser.backgroundtools.dialogs.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.systemtools.FilePathUtil;

/**
 * @author sergio
 *         Created by Sergio on 31/03/2017.
 *         Modelo para objetos File MVP
 *         <p>
 *         [EN]  Template for File MVP objects
 */

@SuppressWarnings("unused")
public class FileModel extends BaseObservable implements Parcelable {
    private File file;
    private int position;

    //CONSTRUCTORS______________________________________________________________________
    public FileModel() {
    }

    public FileModel(File file) {
        this.file = file;
    }

    //OBSERVABLE VARIABLES_______________________________________________________________

    @Bindable
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
        notifyPropertyChanged(BR.file);
    }

    @Bindable
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
        notifyPropertyChanged(BR.position);
    }

    //OBSERVABLE METHODS____________________________________________________________________________
    @Bindable
    public String getName() {
        return file.getName();
    }

    @Bindable
    public String getExt() {
        if (isDirectory()) {
            return "";
        }
        return FilePathUtil.getExtension(file);
    }

    @Bindable
    public String getRelativePath() {
        if (file == null) {
            return "";
        }
        String rute = file.getAbsolutePath();
        return rute.substring(rute.indexOf(FilePathUtil.getAndroidPath().toString()));
    }

    //TRANSFORMATIONS______________________________________________________________________________
    /**
     * Optener la Uri de un archivo
     * <p>
     * [EN]  Opt the Uri of a file
     *
     * @return {@link android.net.Uri}
     */
    public Uri toUri() {
        if (file == null) return new Uri.Builder().build();
        return Uri.fromFile(file);
    }

//FLAGS___________________________________________________________________________________________
    /**
     * Comprobar si el archivo es un archivo png o jpg
     * <p>
     * [EN]  Check if the file is a png or jpg file
     *
     * @return verdadero si la extensión del archivo es png o jpg
     */
    public boolean isImage() {
        if (!isFile()) {
            return false;
        }

        switch (getExt().toLowerCase()) {
            case "jpg":
            case "png":
                return true;
            default:
                return false;
        }
    }

    /**
     * Comprobar si el archivo o directorio existe
     * <p>
     * [EN]  Check if the file or directory exists
     *
     * @return verdadero si la ruta existe [EN]  true if the path exists
     */
    public boolean exists() {
        return file != null && file.exists();
    }

    /**
     * Comprobar si la ruta corresponde con un directorio
     * <p>
     * [EN]  Check if the path corresponds to a directory
     *
     * @return Verdadero si es un directorio [EN]  True if it's a directory
     */
    public boolean isDirectory() {
        return file != null && file.isDirectory();
    }

    /**
     * Comprobar si la ruta corresponde con un archivo
     * <p>
     * [EN]  Check if the path corresponds to a file
     *
     * @return Verdadero si es un archivo [EN]  True if it is a file
     */
    public boolean isFile() {
        return file != null && file.isFile();
    }

    /**
     * Comprobar si es un archivo o directorio oculto
     * <p>
     * [EN]  Check if it is a hidden file or directory
     *
     * @return Verdadero si está oculto [EN]  True if hidden
     */
    public boolean isHidden() {
        return file != null && file.isHidden();
    }


    //PARCELABLE IMPLEMENTATION ________________________________________________________________
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(position);
        parcel.writeSerializable(file);
    }

    protected FileModel(Parcel in) {
        position = in.readInt();
        file = (File) in.readSerializable();
    }

    public static final Creator<FileModel> CREATOR = new Creator<FileModel>() {
        @Override
        public FileModel createFromParcel(Parcel in) {
            return new FileModel(in);
        }

        @Override
        public FileModel[] newArray(int size) {
            return new FileModel[size];
        }
    };
}
