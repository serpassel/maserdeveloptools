package es.marser.backgroundtools.systemtools;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.content.FileProvider;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;

import es.marser.async.DataUploaderTask;
import es.marser.backgroundtools.containers.dialogs.widget.toast.Launch_toast;
import es.marser.tools.MathTools;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by Sergio on 24/03/2017.
 *         Manejador de archivos y directorios
 *         <p>
 *         Para el funcionamiento de la clase se necesita tener los permisos de lectura y escritura del disco
 *         <p>
 *         <ul>
 *         <il>Banderas de estado de tarjeta externa</il>
 *         <il>Directorios del sistema</il>
 *         <il>Manejo de nombres de archivos</il>
 *         <il>Operaciones con archivos</il>
 *         <il>Lectura de directorios de archivos</il>
 *         </ul>
 *         [EN]  File and directory handler
 *         <p>
 *         <p>
 *         For the operation of the class you need to have the read and write permissions of the disk
 *         <ul>
 *         <il>External card status flags</il>
 *         <il>System Directories</il>
 *         <il>File Name Handling</il>
 *         <il>File operations</il>
 *         <il>Reading File Directories</il>
 *         </ul>
 *         <p>
 *         pre-Marshmallow Android 6.0(M) API-23
 *         <p>
 *         /<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 *         <p>
 *         /<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
 */

@SuppressWarnings({"unused", "WeakerAccess", "SameParameterValue"})
public abstract class FilePathUtil {

    //EXTERNAL CARD STATUS FLAGS___________________________________________________________________________

    /**
     * Comprobar si la tarjeta externa está disponible para lectura
     * <p>
     * [EN]  Check if the external card is available for reading
     *
     * @return verdadero si es leible [EN]  true if it is readable
     */
    public static boolean isExternalReadable() {
        switch (Environment.getExternalStorageState()) {
            case Environment.MEDIA_MOUNTED:
                return true;
            case Environment.MEDIA_MOUNTED_READ_ONLY:
                return true;
            default:
                return false;
        }
    }

    /**
     * Comprobar si la tarjeta externa está disponible para escritura
     * <p>
     * [EN]  Check if the external card is available for writing
     *
     * @return verdadero si es escribible [EN]  true if it is writable
     */
    public static boolean isExternalWritable() {
  /*Comprobamos el estado de la memoria externa (tarjeta SD) [EN]  Check the status of external memory (SD card)*/
        switch (Environment.getExternalStorageState()) {
            case Environment.MEDIA_MOUNTED:
                return true;
            case Environment.MEDIA_MOUNTED_READ_ONLY:
            default:
                return false;
        }
    }

    //SYSTEM DIRECTORIES____________________________________________________________________________________

    /**
     * Directorio raiz del disco externo
     * <p>
     * [EN]  External disk root directory
     *
     * @return Objeto file con el directorio raiz del disco externo [EN]  Object file with the root directory of the external disk
     */
    public static File getAndroidPath() {
        return Environment.getExternalStorageDirectory();
    }

    /**
     * Recupera el directorio de la aplicación
     * <p>
     * [EN]  Retrieve the application directory
     *
     * @return directorio de la aplicación [EN]  application directory
     */
    public static File getAppPath(String appName) {

        return new File(getAndroidPath(), TextTools.nc(appName));
    }

    /**
     * Ruta del directorio desde la ruta del directorio raíz externo
     * <p>
     * [EN]  Directory path from the external root directory path
     *
     * @param absolutedPath Ruta completa [EN]  Complete path
     * @return Ruta relativa [EN]  Relative path
     */
    public static String getRelativeAppPath(String absolutedPath) {
        String out = "";
        if (absolutedPath != null) {

            int index = getAndroidPath().toString().length();
            out = (absolutedPath.length() < index || TextTools.isEmpty(absolutedPath))
                    ? ""
                    : absolutedPath.substring(getAndroidPath().toString().length());
        }
        return TextTools.isEmpty(out) ? File.separator : out;
    }

    /**
     * Carpeta de descargas del sistema
     * <p>
     * [EN]  System Download Folder
     *
     * @return Ruta de la carpeta de descargas del sistema [EN]  Path of the system download folder
     */
    public static File getDownloadPath() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    }

    /**
     * Carpeta de Raíz
     * <p>
     * [EN]  Root Folder
     *
     * @return Ruta de la carpeta raíz [EN]  Root folder path
     */
    public static File getRootPath() {
        return new File(File.separator);
    }

    //FILE NAME HANDLING____________________________________________________________________________________

    /**
     * Devuelve la extension de un archivo
     * <p>
     * [EN]  Returns the extension of a file
     *
     * @param file Objeto file con los datos del archivo [EN]  Object file with file data
     * @return Cadena con la extensión del archivo [EN]  String with file extension
     */
    public static String getExtension(File file) {
        String extension = "";
        if (file == null) {
            return extension;
        }
        String fileName = file.getName();
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1);
        }
        return extension;
    }

    /**
     * Devuelve el nombre del archivo sin la extensión
     * <p>
     * [EN]  Returns the name of the file without the extension
     *
     * @param file recurso de entrada [EN]  input resource
     * @return Nombre del archivo sin la extensión [EN]  Filename without extension
     */
    public static String getFileSimpleName(File file) {

        String filename = file.getName();
        filename = filename.substring(0, filename.lastIndexOf(getExtension(file)) - 1);
        return filename;
    }

    /**
     * Renombrar un archivo si ya existe
     * <p>
     * [EN]  Rename a file if it already exists
     *
     * @param path ruta del archivo [EN]  file path
     * @param name nombre del archivo [EN]  file name
     * @return Nombre del archivo libre [EN]  Free file name
     */
    public static String AutoRenameFile(File path, String name) {

        String name1 = name.substring(0, name.lastIndexOf("."));
        String ext = name.substring(name.lastIndexOf("."), name.length());

        int i = 0;
        String newname = name1 + "_(" + MathTools.formatCifra(i, 2) + ")" + ext;
        File input = new File(path, newname);
        while (input.exists()) {
            ++i;
            newname = name1 + "_(" + MathTools.formatCifra(i, 2) + ")" + ext;
            input = new File(path, newname);
        }
        return newname;
    }

    /**
     * Renombrar un archivo si ya existe
     * <p>
     * [EN]  Rename a file if it already exists
     *
     * @param path ruta del archivo [EN]  file path
     * @return Objeto File del nuevo archivo [EN]  File object of the new file
     */
    public static File AutoRenameFile(File path) {
        File parent = path.getParentFile();
        String name = path.getName();
        return new File(parent, AutoRenameFile(parent, name));
    }

    //FILE OPERATIONS____________________________________________________________________________________________

    /**
     * Eliminar una ruta completa
     * <p>
     * [EN]  Delete a full path
     *
     * @param file Objeto a borrar [EN]  Object to be deleted
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void delete(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                /*Si es un directorio antes borramos sus archivos
                [EN]  If it is a directory before we delete your files*/
                for (File f : file.listFiles()) {
                    delete(f);
                }
            } else {
                file.delete();
            }
        }
    }

    /**
     * Abre un archivo con la aplicación asignada por el sistema
     * <p>
     * [EN]  Opens a file with the application assigned by the system
     *
     * @param activity Actividad de origen [EN]  Source Activity
     * @param file     Recurso de entrada [EN]  Input resource
     */
    public static void exec(Activity activity, File file) {
        /*Revocación de recursos incorrectos [EN]  Revocation of incorrect resources*/

        if (activity == null || file == null) {
            return;
        }

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);

        Uri apkURI = FileProvider.getUriForFile(
                activity,
                activity.getApplicationContext()
                        .getPackageName() + ".provider", file);

        intent.setDataAndType(apkURI, getMimeType(file));
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            activity.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Launch_toast.errorToast(activity, "No hemos encontrado ninguna aplicación para abrir el archivo");
        }
    }

    /**
     * Devuelve registro de mimetype según el mapa de android
     * <p>
     * [EN]  Returns mimetype record according to android map
     *
     * @param file Recurso de origen [EN]  Source resource
     * @return valor de mymetype para el archivo de entrada [EN]  value of mymetype for the input file
     */
    public static String getMimeType(File file) {
        String myme = file != null ? MimeTypeMap.getSingleton().getMimeTypeFromExtension(TextTools.nc(getExtension(file).toLowerCase())) : null;
        return myme != null ? myme : "*/*";
    }

    /**
     * Copia el archivo de origen sobreescribiendo
     * <p>
     * [EN]  Copy the source file overwriting
     *
     * @param sourceFile      Recurso de entrada [EN]  Input resource
     * @param destinationFile Recurdo de salida [EN]  Departure time
     */
    public static void fileCopyOverWrite(final File sourceFile, final File destinationFile) {

        /*Recursos erróneos [EN]  Wrong resources*/
        if (sourceFile == null || !sourceFile.exists() || destinationFile == null) {
            return;
        }

        /*Abrir nuevo hilo para la copia del archivo [EN]  Open new thread for file copy*/
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                FileOutputStream out;//Stream de salida [EN]  Output stream
                FileInputStream in;//Stream de entrada [EN]  Input stream
                try {
                    in = new FileInputStream(sourceFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return;
                }
                try {
                    out = new FileOutputStream(destinationFile);
                    int c;

                    while ((c = in.read()) != -1) {
                        out.write(c);
                    }
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    //READING FILE DIRECTORIES_____________________________________________________________________________________

    /**
     * Recuperación asíncrona de objetos File de una ruta
     * <p>
     * [EN]  Retrieving asynchronous objects from a file
     *
     * @param path   Ruta de origen [EN]  Source Path
     * @param filter Filter de objetos por nombre [EN]  Filter objects by name
     * @param task   Oyente de resultados [EN]  Listener results
     */
    public static void getAsyncFiles(File path, String[] filter, DataUploaderTask<Void, File, Void> task) {
        new AsyncLoadFilesPath(task).execute(new FileLoader(path, filter));
    }

    /**
     * Recuperación síncrona de archivos de una ruta
     * <p>
     * [EN]  Synchronous File Recovery for a Path
     *
     * @param path   Ruta de origen [EN]  Source Path
     * @param filter Filter de objetos por nombre [EN]  Filter objects by name
     * @return Arreglo de objetos file en la ruta [EN]  Array of objects file in the path
     */
    public static File[] getUnSyncFiles(File path, String[] filter) {
        FileLoader fileLoader = new FileLoader(path, filter);
        return fileLoader.getPathFiles();
    }

    /**
     * Subclase para la carga asincrona de los archivos y directorios de un directorio
     * <p>
     * [EN]  Subclass for the asynchronous load of the files and directories of a directory
     */
    @SuppressWarnings({"CanBeFinal", "unused"})
    private static class AsyncLoadFilesPath extends AsyncTask<FileLoader, Bundle, Void> {

        private DataUploaderTask<Void, File, Void> taskLoadingResult;
        private String filter;

        @SuppressWarnings("unused")
        public enum Extras {
            INDEX_EXTRA,
            OBJECT_EXTRA,
            THROWABLE_EXTRA
        }

        public AsyncLoadFilesPath(DataUploaderTask<Void, File, Void> taskLoadingResult) {
            this.taskLoadingResult = taskLoadingResult;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (taskLoadingResult != null) {
                taskLoadingResult.onStart(null);
            }
        }

        @Override
        protected Void doInBackground(FileLoader... params) {
            FileLoader fileLoader = params[0];

            if (fileLoader != null && fileLoader.path != null && fileLoader.getPathFiles() != null) {

                for (File file : fileLoader.getPathFiles()) {

                    /*Mostrar archivos ocultos [EN]  Show hidden files */
                    if (fileLoader.showHidden || !file.isHidden()) {

                    /*Crear bundle de publicación [EN]  Create publishing bundle*/
                        Bundle bundle = new Bundle();
                    /*Argumento de tipo de información [EN]  Information Type Argument*/
                        bundle.putSerializable(Extras.INDEX_EXTRA.name(), Extras.OBJECT_EXTRA);
                    /*Argumento con el valor de la información [EN]  Argument with the value of the information*/
                        bundle.putSerializable(Extras.OBJECT_EXTRA.name(), file);
                    /*Publicar información [EN]  Publish Information*/
                        publishProgress(bundle);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Bundle... values) {
            super.onProgressUpdate(values);

            for (Bundle b : values) {
                if (b == null) {
                    continue;
                }
                Extras ex = (Extras) b.getSerializable(Extras.INDEX_EXTRA.name());
                if (ex != null) {
                    switch (ex) {
                        case OBJECT_EXTRA:
                            try {
                                File in = (File) b.getSerializable(Extras.OBJECT_EXTRA.name());
                                if (taskLoadingResult != null && in != null) {
                                    taskLoadingResult.onUpdate(in);
                                }
                            } catch (ClassCastException e) {
                                if (taskLoadingResult != null) {
                                    taskLoadingResult.onFailure(e);
                                }
                            }
                            break;
                        case THROWABLE_EXTRA:
                            if (taskLoadingResult != null) {
                                try {
                                    Throwable throwable = (Throwable) b.getSerializable(Extras.THROWABLE_EXTRA.name());
                                    taskLoadingResult.onFailure(throwable);
                                } catch (ClassCastException e) {
                                    if (taskLoadingResult != null) {
                                        taskLoadingResult.onFailure(e);
                                    }
                                }
                            }
                            break;
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (taskLoadingResult != null) {
                taskLoadingResult.onFinish(aVoid);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            if (taskLoadingResult != null) {
                taskLoadingResult.onFinish(null);
            }
        }
    }

    /**
     * Subclase para introducir parámetros en la tarea asyncrona
     * <p>
     * [EN]  Subclass to enter parameters in the asynchronous task
     */
    @SuppressWarnings({"CanBeFinal", "unused"})
    public static class FileLoader implements Parcelable {
        private String[] filter;
        private File path;
        public boolean showHidden;

        public FileLoader() {
            this(null, new String[]{});
        }


        public FileLoader(String... filter) {
            this(null, filter);
        }

        public FileLoader(File path) {
            this(path, new String[]{});
        }

        public FileLoader(File path, String... filter) {
            this(path, false, filter);
        }

        public FileLoader(File path, boolean showHidden, String... filter) {
            if (filter == null) {
                filter = new String[]{};
            }
            this.filter = filter;
            this.path = path;
            this.showHidden = showHidden;
        }

        /**
         * Crear un filtro por nombre para listar un directorio
         * <p>
         * [EN]  Create a filter by name to list a directory
         *
         * @return Filtro para listado de archivos y directorios {@link FilenameFilter}
         * [EN]  Filter for listing files and directories {@link FilenameFilter}
         */
        public FilenameFilter getFilenameFilter() {
            return new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    boolean result = filter.length <= 0;

                    for (String s : filter) {
                        if (result) {
                            break;
                        }
                        result = name.toLowerCase().endsWith(s);
                    }

                    if (!result) {
                        File f = new File(dir, name);
                        result = f.exists() && f.isDirectory();
                    }
                    //  Log.i(LOG_TAG.TAG, "Comprobación " + name + " " + result);
                    return result;
                }
            };
        }

        /**
         * Arhivos y directorios del directorio según el filtro
         * <p>
         * [EN]  Arhivos and directory of the directory according to the filter
         *
         * @return arrglo de ficheros y directorios [EN]  files and directories
         */
        public File[] getPathFiles() {
            if (path == null || !path.exists()) {
                return new File[]{};
            }

            File out = path.isDirectory() ? path : path.getParentFile();

            return (out != null && out.exists()) ? out.listFiles(getFilenameFilter()) : new File[]{};
        }

        //IMPLEMENTATION PARCELABLE______________________________________________________________________________
        public static final Creator<FileLoader> CREATOR = new Creator<FileLoader>() {
            @Override
            public FileLoader createFromParcel(Parcel in) {
                return new FileLoader(in);
            }

            @Override
            public FileLoader[] newArray(int size) {
                return new FileLoader[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        protected FileLoader(Parcel in) {
            in.readStringArray(filter);
            showHidden = in.readByte() != 0;
            path = (File) in.readSerializable();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeStringArray(filter);
            dest.writeByte((byte) (showHidden ? 1 : 0));
            dest.writeSerializable(path);
        }
    }

}
