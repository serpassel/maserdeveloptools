package es.marser.backgroundtools.widget.files.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.enums.DialogIcon;
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
        this.file = new File("");
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
        notifyPropertyChanged(BR.relativePath);
        notifyPropertyChanged(BR.name);
        notifyPropertyChanged(BR.ext);
        notifyPropertyChanged(BR.fileIcon);
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

    @Bindable
    public DialogIcon getFileIcon() {
        if(isDirectory()){
            return DialogIcon.FOLDER_ICON;
        }
        switch (getExt().toLowerCase()) {
            case "bc3":
                return DialogIcon.BC3_ICON;
            case "ods":
            case "xls":
            case "xlsx":
            case "xlsm":
            case "xltx":
            case "xltm":
            case "xlam":
                return DialogIcon.EXCEL_ICON;
            case "pdf":
                return DialogIcon.PDF_ICON;
            case "jpg":
            case "jpeg":
                return DialogIcon.JPG_ICON;
            case "png":
                return DialogIcon.PNG_ICON;
            case "wpd":
            case "wps":
            case "doc":
            case "docm":
            case "docx":
            case "dot":
            case "dotx":
            case "dotm":
                return DialogIcon.DOC_ICON;
            case "htm":
            case "html":
                return DialogIcon.HTML_ICON;
            case "ppt":
            case "pptx":
            case "pptm":
            case "potx":
            case "pot":
            case "potm":
            case "ppam":
            case "pps":
            case "ppsx":
            case "ppsm":
                return DialogIcon.PPT_ICON;
            case "xml":
                return DialogIcon.XML_ICON;
            case "zip":
            case "gzip":
                return DialogIcon.ZIP_ICON;
            case "jar":
            case "rar":
            case "tar":
            case "tgz":
            case "ace":
            case "sitx":
            case "7z":
            case "gz":
                return DialogIcon.PACKED_ICON;
            case "mp3":
                return DialogIcon.MP3_ICON;
            case "txt":
                return DialogIcon.TXT_ICON;
            case "json":
                return DialogIcon.JSON_ICON;
            case "csv":
                return DialogIcon.CSV_ICON;
            case "psd":
                return DialogIcon.PSD_ICON;
            case "mp4":
            case "mkv":
            case "mpg":
            case "mpeg":
                return DialogIcon.MP4_ICON;
            case "avi":
            case "divx":
            case "mov":
            case "rm":
            case "3gp":
            case "3g2":
            case "3gpp":
            case "asf":
                return DialogIcon.AVI_ICON;
            case "rtf":
                return DialogIcon.RTF_ICON;
            case "dwg":
            case "dxf":
            return DialogIcon.DWG_ICON;
            case "css":
            return DialogIcon.CSS_ICON;
            case "js":
            return DialogIcon.JS_ICON;
            case "ai":
                return DialogIcon.AI_ICON;
            case "exe":
                return DialogIcon.EXE_ICON;
            case "iso":
                return DialogIcon.ISO_ICON;
            case "dbf":
            return DialogIcon.DBF_ICON;
            case "fla":
            return DialogIcon.FLA_ICON;
            default:
                return DialogIcon.FILE_ICON;
        }
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
