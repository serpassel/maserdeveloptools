package es.marser.sqltools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import es.marser.LOG_TAG;
import es.marser.async.AsyncPublishObject;
import es.marser.async.DataUploaderTask;
import es.marser.async.TaskFailure;
import es.marser.async.TaskResult;


/**
 * @author sergio
 *         Created by Sergio on 31/03/2016.
 *         Clase para manejo de registros SQLite
 *         <p>
 *         <ul>
 *         <il>Eventos de inicio</il>
 *         <il>Estructura de la base de datos</il>
 *         <il>Estado y varibles</il>
 *         <il>Manejo de conexión de datos</il>
 *         <il>Añadir, actualizar y eliminar registros</il>
 *         <il>Añadir, actualizar y eliminar registros. Métodos asíncronos</il>
 *         <il>Funciones de agregado</il>
 *         <il>Lectura de registros, síncrona</il>
 *         <il>Lectura de registros, asíncrona</il>
 *         <il>Grabación por bloques, asíncrona</il>
 *         <il>Oyentes</il>
 *         </ul>
 *         <p>
 *         [EN]  Class for handling SQLite records
 *         <ul>
 *         <il>Start events</il>
 *         <il>Structure of the database</il>
 *         <il>State and varibles</il>
 *         <il>Data connection handling</il>
 *         <il>Add, update and delete records</il>
 *         <il>Add, update and delete records.  Asynchronous Methods</il>
 *         <il>Aggregate functions</il>
 *         <il>Reading records, synchronous</il>
 *         <il>Reading records, asynchronous</il>
 *         <il>Block Recording, Asynchronous</il>
 *         <il>Listeners</il>
 *         <p>
 *         </ul>
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class CRUDHandler extends SQLiteOpenHelper {

    /*Nombre de la base de datos por defecto [EN]  Default database name*/
    public static final String DEFAULT_DATABASE_USERSNAME = "dbusers";
    /*Objeto de con conexión a SQLite [EN]  Version of the database*/
    private SQLiteDatabase db = null;
    /*Contexto de la aplicación [EN]  Context of the application*/
    private Context context;
    /*Arreglo de clases mapeadas para creación de tablas [EN]  Array of mapped classes for creating tables */
    private Class[] tables;

    //START EVENTS_________________________________________________________________________________

    /**
     * Constructor de la clase
     * <p>
     * [EN]  Class Builder
     *
     * @param context Contexto de la aplicación [EN]  Context of the application
     * @param tables  Arreglo de clases mapeadas para creacíon de tablas [EN]  Array of mapped classes for creating tables
     * @param name    Nombre de la base de datos [EN]  Database name
     * @param version versión de base de datos [EN]  database version
     */
    public CRUDHandler(Context context, String name, int version, Class... tables) {
        super(context, name, null, version);
        this.context = context;
        if (tables == null) {
            tables = new Class[]{};
        }
        this.tables = tables;
    }

    public CRUDHandler(Context context, @NonNull DatabaseSettings settings) {
        this(context, settings.getName(), settings.getVersion(), settings.getTables());
    }

    /**
     * Método llamado la primera vez que se conecta la base de datos
     * <p>
     * [EN]  Method called the first time the database is connected
     *
     * @param db Objeto de base de datos SQLite [EN]  Method called the first time the database is connected
     * @see CRUDHandler#createTables(SQLiteDatabase)
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //Creamos las tablas [EN]  We create the tables
            createTables(db);
        } catch (Exception ignored) {
        }
    }

    /**
     * Método llamado cuando se actualiza la base de datos
     * <p>
     * [EN]  Method called when updating the database
     *
     * @param db         Objeto de base de datos SQLite [EN]  Method called the first time the database is connected
     * @param oldVersion Versión vieja de la base de datos [EN]  Old version of the database
     * @param newVersion Versión nueva de la base de datos [EN]  Old version of the database
     * @see CRUDHandler#upDateTables(SQLiteDatabase, int)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*Actualizar las tablas si se ha modificado la versión de la base de datos
        [EN]  Update the tables if the database version has been modified*/

        if (oldVersion < newVersion) {
            upDateTables(db, oldVersion);
        }
    }

    //STRUCTURE OF THE DATABASE________________________________________________________________________________

    /**
     * Agregar nuevas tablas y columnas a las tablas existentes
     * <p>
     * Las clase debe de estar mapeadas según el paquete de anotaciones
     * <p>
     * [EN]  Add new tables and columns to existing tables
     *
     * @param db         Objeto de base de datos SQLite [EN]  Method called the first time the database is connected
     * @param oldVersion Versión vieja de la base de datos [EN]  Old version of the database
     * @see es.marser.annotation
     * @see es.marser.sqltools.examples.PojoExample
     */
    protected void upDateTables(SQLiteDatabase db, int oldVersion) {
        /*Crear tablas por si existiera una nueva [EN]  Create tables if there is a new one*/
        createTables(db);
        for (Class l : tables) {
        /*Agregar las columnas nuevas [EN]  Add new columns*/
            List<String> columns = SQLStrings.addColumns(l, oldVersion);
            for (String col : columns) {
                db.execSQL(col);
                Log.i(LOG_TAG.TAG, "Columna actualizada " + col);
            }

        }
    }

    /**
     * Método de creación de tablas. Crea las tablas si no existen.
     * <p>
     * Las clase debe de estar mapeadas según el paquete de anotaciones
     * <p>
     * [EN]  Method of creating tables.  Create tables if they do not exist
     * <p>
     * [EN]  Classes must be mapped according to the annotation package
     *
     * @param db Objeto de base de datos SQLite [EN]  Method called the first time the database is connected
     * @see es.marser.annotation
     * @see es.marser.sqltools.examples.PojoExample
     */
    protected final void createTables(SQLiteDatabase db) {
        String sql;
        /*Recuperar listado de clase mapeadas con las tablas [EN]  Retrieve class list mapped with tables*/
        for (Class l : tables) {
            /*Obtener la sentencia de */
            sql = SQLStrings.createTable(l);
            Log.w(LOG_TAG.TAG, "Crear tablas " + sql);
            /*Reconectar base de datos para garantizar su estado [EN]  Reconnect database to ensure its status*/
            reconectDatabase();
            /*Ejecutar setencia [EN]  Execute setencia */
            try {
                db.execSQL(sql);
                Log.i(LOG_TAG.TAG, "Tabla creada " + sql);
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * Borrar tablas
     * <p>
     * [EN]  Delete tables
     *
     * @param db Objeto de base de datos SQLite [EN]  Method called the first time the database is connected
     */
    protected void dropTables(SQLiteDatabase db) {
        for (Class l : tables)
            try {
                db.execSQL(SQLStrings.dropTable(l));//Ejecutamos la sentencia
            } catch (Exception ignored) {
            }
    }

    /**
     * Borrar base de datos
     * <p>
     * [EN]  Delete Database
     *
     * @return verdadero si se ha borrado la base de datos [EN]  true if the database has been deleted
     */
    @SuppressWarnings("UnusedReturnValue")
    public boolean deleteDatabase() {
       /*Desconectar antes de borrar [EN]  Disconnect before deleting*/
        if (db != null && db.isOpen()) {
            db.close();
        }
        return context.deleteDatabase(getDatabaseName());
    }

    //STATE AND VARIBLES_______________________________________________________________________________________

    /**
     * Comprobar si la base de datos está conectada
     * <p>
     * [EN]  Check if the database is connected
     *
     * @return verdadero si está conectada [EN]  true if connected
     */
    public boolean isOpen() {
        return this.db.isOpen();
    }

    /**
     * Recuperar el objeto de la base de datos
     * <p>
     * [EN]  Retrieve the object from the database
     *
     * @return Objeto de base de datos [EN]  Database Object
     */
    public SQLiteDatabase getDb() {
        return this.db;
    }

    //DATA CONNECTION HANDLING_______________________________________________________________________________

    /**
     * Conectar la base de datos
     * <p>
     * [EN]  Connect the database
     */
    public void conectDatabase() {
        this.db = getWritableDatabase();
    }

    /**
     * Método de reconexión de datos
     * <p>
     * [EN]  Data Reconnection Method
     */
    public void reconectDatabase() {
        if (this.db != null && !this.db.isOpen()) {
            conectDatabase();
        }
    }

    /**
     * Desconectar la conexión a datos
     * <p>
     * [EN]  Disconnect the data connection
     */
    public void disconectDatabase() {
        this.close();
    }

    /**
     * Conexión asíncrona de base de datos
     * <p>
     * [EN]  Asynchronous database connection
     *
     * @param onConect Oyente de resultado [EN]  Asynchronous database connection
     */
    public void asyncConectDatabase(final OnConect onConect) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                conectDatabase();
                /*Indicar inalización [EN]  Indicate inactivation*/
                if (onConect != null) {
                    onConect.onFinish(Activity.RESULT_OK, true);
                }
            }
        });
    }

    /**
     * Reconexión asíncrona de base de datos
     * <p>
     * [EN]  Database asynchronous reconnection es
     *
     * @param dbResult Oyente de resultado [EN]  Asynchronous database connection
     */
    public void asyncReconectDatabase(final OnConect dbResult) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                reconectDatabase();
                /*Indicar inalización [EN]  Indicate inactivation*/
                if (dbResult != null) {
                    dbResult.onFinish(Activity.RESULT_OK, true);
                }
            }
        });
    }

//ADD, UPDATE AND DELETE RECORDS______________________________________________________________________________

    /**
     * Lista de acciones CRUD
     * <p>
     * [EN]  List of CRUD actions
     */
    @SuppressWarnings("unused")
    enum CrudAction {
        INSERT_OR_IGNORE, INSERT_OR_REPLACE, DELETE, UPDATE
    }

    /**
     * Ejecución de acción sobre registros, CRUD
     * <p>
     * [EN]  Enforcement of record action, CRUD
     *
     * @param in   Registro. Clase mapeada
     * @param mode Tipo de acción [EN]  Type of action
     * @return Clase Throwable en caso de error, objeto nulo si la acción se ha realizado [EN]  Throwable class on error, null object if action taken
     */
    private Throwable crudAction(Object in, CrudAction mode) {
        if (this.db != null && in != null) {
            try {
                /*Asegurar conexión a base de datos [EN]  Secure connection to database*/
                reconectDatabase();
                switch (mode) {
                    case INSERT_OR_IGNORE:
                /*Agrega el registro sólo si no existe [EN]  Add the record only if it does not exist*/
                        this.db.execSQL(SQLStrings.insertOrIgnoreRecord(in));
                        break;
                     /*Agrega el registro si no existe o lo reeplaza en caso contrario [EN]  Add the record if it does not exist or replace it otherwise*/
                    case INSERT_OR_REPLACE:
                        this.db.execSQL(SQLStrings.insertOrReplaceRecord(in));
                        break;
                    /*Elimina el registro de la base de datos [EN]  Deletes the database log*/
                    case DELETE:
                        this.db.execSQL(SQLStrings.deleteRecord(in));
                        break;
                    /*Actualizar registro en base de datos [EN]  Update log to database*/
                    case UPDATE:
                        this.db.execSQL(SQLStrings.upRecord(in));
                        break;
                }
            } catch (IllegalAccessException e) {
                return e;
            } catch (SQLiteException e) {
                return e;
            }
            return null;
        }
        return new NullPointerException("Entrada o conexión nula");
    }

    /**
     * Agregar un registro nuevo sólo si no existe
     * <p>
     * [EN]  Add a new record only if it does not exist
     *
     * @param in Registro de entrada mapeado [EN]  Mapped input record
     * @return nulo si es correcto o el objeto de error [EN]  null if it is correct or the object of error
     */
    @SuppressWarnings("UnusedReturnValue")
    public Throwable addRecord(Object in) {
        return crudAction(in, CrudAction.INSERT_OR_IGNORE);
    }

    /**
     * Agregar un registro nuevo si no existe o reemplazar uno existente
     * <p>
     * [EN]  Add a new record if it does not exist or replace an existing one
     *
     * @param in Registro de entrada mapeado [EN]  Mapped input record
     * @return nulo si es correcto o el objeto de error [EN]  null if it is correct or the object of error
     */
    public Throwable addOrReplaceRecord(Object in) {
        return crudAction(in, CrudAction.INSERT_OR_REPLACE);
    }

    /**
     * Eliminar  un registro existente
     * <p>
     * [EN]  Delete an Existing Record
     *
     * @param in Registro de entrada mapeado [EN]  Mapped input record
     * @return nulo si es correcto o el objeto de error [EN]  null if it is correct or the object of error
     */
    public Throwable delRecord(Object in) {
        return crudAction(in, CrudAction.DELETE);
    }

    /**
     * Actualizar  un registro existente
     * <p>
     * [EN]  Update an existing record
     *
     * @param in Registro de entrada mapeado [EN]  Mapped input record
     * @return nulo si es correcto o el objeto de error [EN]  null if it is correct or the object of error
     */
    public Throwable updateRecord(Object in) {
        return crudAction(in, CrudAction.UPDATE);
    }

//ADD, UPDATE AND DELETE RECORDS.  ASYNCHRONOUS METHODS_______________________________________________________

    /**
     * Clase para el manejo asíncrono del CRUD
     * <p>
     * [EN]  Class for asynchronous CRUD handling
     */
    @SuppressLint("StaticFieldLeak")
    @SuppressWarnings("unused")
    private abstract class AsyncUD extends AsyncTask<Object, Throwable, Boolean> {
        private OnChanged onResult;

        public AsyncUD(OnChanged onResult) {
            this.onResult = onResult;
        }

        @Override
        protected void onProgressUpdate(Throwable... values) {
            super.onProgressUpdate(values);
            if (onResult != null) {
                onResult.onFailure(values[0]);
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (onResult != null) {
                onResult.onFinish(Activity.RESULT_OK, aBoolean);
            }
        }
    }

    /**
     * Ejecución de acción asíncrona sobre registros, CRUD
     * <p>
     * [EN]  Execution of asynchronous action on registers, CRUD
     *
     * @param in        Clase Mapeada
     * @param onChanged Oyente de resultados [EN]  Listener results
     */
    @SuppressLint("StaticFieldLeak")
    private AsyncTask asyncCrudAction(Object in, CrudAction action, OnChanged onChanged) {
        if (in == null || action == null) {
            if (onChanged != null) {
                onChanged.onFailure(new NullPointerException("Null input"));
            } else {
                return null;
            }
        }

        return new AsyncUD(onChanged) {
            @Override
            protected Boolean doInBackground(Object... params) {
                Throwable t = crudAction(params[0], (CrudAction) params[1]);

                if (t != null) {
                    publishProgress(t);
                }
                return t == null;
            }
        }.execute(in, action);
    }

    /**
     * Agregar un registro nuevo sólo si no existe. Método asíncrono
     * <p>
     * [EN]  Add a new record only if it does not exist. Asynchronous method
     *
     * @param in        Registro de entrada mapeado [EN]  Mapped input record
     * @param onChanged Oyente de resultados [EN]  Listener results
     */
    public AsyncTask addRecord(Object in, OnChanged onChanged) {
        return asyncCrudAction(in, CrudAction.INSERT_OR_IGNORE, onChanged);
    }

    /**
     * Eliminar  un registro existente. Método asíncrono
     * <p>
     * [EN]  Delete an Existing Record. Asynchronous method
     *
     * @param in        Registro de entrada mapeado [EN]  Mapped input record
     * @param onChanged Oyente de resultados [EN]  Listener results
     */
    public AsyncTask delRecord(Object in, OnChanged onChanged) {
        return asyncCrudAction(in, CrudAction.INSERT_OR_REPLACE, onChanged);
    }

    /**
     * Actualizar  un registro existente. Método asíncrono.
     * <p>
     * [EN]  Update an existing record. Asynchronous method.
     *
     * @param in        Registro de entrada mapeado [EN]  Mapped input record
     * @param onChanged Oyente de resultados [EN]  Listener results
     */
    public AsyncTask updateRecord(Object in, OnChanged onChanged) {
        return asyncCrudAction(in, CrudAction.INSERT_OR_REPLACE, onChanged);
    }


//READING RECORDS, SYNCHRONOUS__________________________________________________________________________________

    /*RESULTADOS SINGULARES [EN]  SINGULAR RESULTS */

    /**
     * Recuperar registro de una tabla. Método síncrono
     * <p>
     * [EN]  Recover record from a table. Synchronous method
     *
     * @param sql Sentencia SQL para formular la lectura de la base de datos [EN]  SQL statement to formulate the reading of the database
     * @param cls clase del objeto a instanciar [EN]  class of the object to instantiate
     * @param <T> Objeto genérico Parcelable de lectura [EN]  Generic object Parcelable of reading
     * @return Primer objeto genérico leído [EN]  First generic object read
     */
    private <T> T getRecord(String sql, Class<T> cls) {
         /*Asegurar la conexión de la base de datos [EN]  Secure the database connection*/
        reconectDatabase();
                /*Crear cursor [EN]  Create cursor*/
        Cursor rs = db.rawQuery(sql, null);
        T o = null;
        if (rs.moveToFirst()) {
            try {
                        /*Publicar lectura [EN]  Publish reading*/
                o = SQLStrings.getGenericByReflection(rs, cls);
            } catch (ClassCastException | InstantiationException | IllegalAccessException ignored) {
            }
        }
                /*Cerrar cursor [EN]  Close cursor*/
        rs.close();
        return o;
    }

    /**
     * Localizar un registro por su clave primaria. Método síncrono
     * <p>
     * [EN]  Locate a record by its primary key. Synchronous method
     *
     * @param key Valor de la clave primaria [EN]  Primary Key Value
     * @param cls clase del objeto a instanciar [EN]  class of the object to instantiate
     * @param <T> Objeto genérico Parcelable de lectura [EN]  Generic object Parcelable of reading
     * @return Registro coincidente con la clave primaria [EN]  Record matching primary key
     */
    public <T> T findRecordByKey(Object key, Class<T> cls) {
        return getRecord(SQLStrings.findRecordSql(key, cls), cls);
    }

    /**
     * Recuperar el primer registro que coincide con el filtro. Método síncrono
     * <p>
     * [EN]  Recover the first record that matches the filter. Synchronous method
     *
     * @param filter Filtro de la búsqueda [EN]  Search Filter
     * @param cls    clase del objeto a instanciar [EN]  class of the object to instantiate
     * @param <T>    Objeto genérico Parcelable de lectura [EN]  Generic object Parcelable of reading
     * @return Primer registro que coincida con el filtro [EN]  First record that matches the filter
     */
    public <T> T findRecordByFilter(String filter, Class<T> cls) {
        return getRecord(SQLStrings.selectAll(cls) + SQLStrings.createFilter(filter), cls);
    }

    /*RESULTADO MULTIPLE [EN]  MULTIPLE RESULT*/

    /**
     * Recuperar registros de una tabla. Método síncrono
     * <p>
     * [EN]  Recover records from a table. Synchronous method
     *
     * @param sql Sentencia SQL para formular la lectura de la base de datos [EN]  SQL statement to formulate the reading of the database
     * @param cls clase del objeto a instanciar [EN]  class of the object to instantiate
     * @param <T> Objeto genérico Parcelable de lectura [EN]  Generic object Parcelable of reading
     * @return Lista de objetos genéricos leídos [EN]  List of read generic objects
     */
    private <T> List<T> getRecords(String sql, Class<T> cls) {
        List<T> out = new ArrayList<>();

        Log.w(LOG_TAG.TAG, "Sentencia SQL: " + sql);


         /*Asegurar la conexión de la base de datos [EN]  Secure the database connection*/
        reconectDatabase();
                /*Crear cursor [EN]  Create cursor*/
        Cursor rs = db.rawQuery(sql, null);
        T o;
        for (rs.moveToFirst(); !rs.isAfterLast(); rs.moveToNext()) {
            try {
                        /*Publicar lectura [EN]  Publish reading*/
                o = SQLStrings.getGenericByReflection(rs, cls);
                if (o != null) {
                    out.add(o);
                }
            } catch (ClassCastException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
                /*Cerrar cursor [EN]  Close cursor*/
        rs.close();
        return out;
    }

    /**
     * Recuperar todos los registros de una tabla. Método síncrono
     * <p>
     * [EN]  Retrieve all records in a table.  Synchronous method
     *
     * @param cls clase del objeto a instanciar [EN]  class of the object to instantiate
     * @param <T> Objeto genérico Parcelable de lectura [EN]  Generic object Parcelable of reading
     * @return Lista de objetos genéricos leídos [EN]  List of read generic objects
     */
    public <T> List<T> getAllRecords(Class<T> cls) {
        return getRecords(SQLStrings.selectAll(cls), cls);
    }

    /**
     * Recuperar todos los registros de una tabla aplicando el orden indicado. Método síncrono
     * <p>
     * [EN]  Retrieve all the records of a table in the order indicated.  Synchronous method
     *
     * @param order nombre del campo sobre el que se aplica el orden
     * @param cls   clase del objeto a instanciar [EN]  class of the object to instantiate
     * @param <T>   Objeto genérico Parcelable de lectura [EN]  Generic object Parcelable of reading
     * @return Lista de objetos genéricos leídos [EN]  List of read generic objects
     * @see SQLStrings#createOrder(String)
     */
    public <T> List<T> getAllRecordsOrderBy(String order, Class<T> cls) {
        return getRecords(SQLStrings.selectAll(cls) + SQLStrings.createOrder(order), cls);
    }

    /**
     * Recuperar lo registros coincidentes con el filtro. Método síncrono
     * <p>
     * [EN]  Recover the records matching the filter.  Synchronous method
     *
     * @param filter Filtro de la búsqueda [EN]  Search Filter
     * @param cls    clase del objeto a instanciar [EN]  class of the object to instantiate
     * @param <T>    Objeto genérico Parcelable de lectura [EN]  Generic object Parcelable of reading
     * @return Lista de objetos genéricos leídos [EN]  List of read generic objects
     */
    public <T> List<T> findRecordsByFilter(String filter, Class<T> cls) {
        return getRecords(SQLStrings.selectAll(cls) + SQLStrings.createFilter(filter), cls);
    }

    /**
     * Recuperar lo registros coincidentes con la clave foránea. Método síncrono
     * <p>
     * [EN]  Retrieve records matching the foreign key.  Synchronous method
     *
     * @param parentkey clave foránea [EN]  foreign key
     * @param cls       clase del objeto a instanciar [EN]  class of the object to instantiate
     * @param <T>       Objeto genérico Parcelable de lectura [EN]  Generic object Parcelable of reading
     * @return Lista de objetos genéricos leídos [EN]  List of read generic objects
     */
    public <T> List<T> findRecordsByParent(Object parentkey, Class<T> cls) {
        return getRecords(SQLStrings.findRecordByParent(parentkey, cls), cls);
    }

    /**
     * @param text de comienzo de la clave primaria [EN]  of the beginning of the primary key
     * @param cls  clase del objeto a instanciar [EN]  class of the object to instantiate
     * @param <T>  Objeto genérico Parcelable de lectura [EN]  Generic object Parcelable of reading
     * @return Todos los registros cuya clave comienza por el text de referencia [EN]  All records whose key begins with the reference text
     */
    public <T> List<T> findRecordsKeyStartWith(String text, Class<T> cls) {
        return findRecordsByFilter(SQLStrings.createKeyStartWith(text, cls), cls);}

    /**
     * @param text de terminación de la clave primaria [EN]  termination of the primary key
     * @param cls  clase del objeto a instanciar [EN]  class of the object to instantiate
     * @param <T>  Objeto genérico Parcelable de lectura [EN]  Generic object Parcelable of reading
     * @return Todos los registros cuya clave comienza por el text de referencia [EN]  All records whose key begins with the reference text
     */
    public <T> List<T> findRecordsKeyEndWith(String text, Class<T> cls) {
        return findRecordsByFilter(SQLStrings.createKeyEndWith(text, cls), cls);
    }

    /**
     * @param text contenido en la clave primaria [EN]  content in the primary key
     * @param cls  clase del objeto a instanciar [EN]  class of the object to instantiate
     * @param <T>  Objeto genérico Parcelable de lectura [EN]  Generic object Parcelable of reading
     * @return Todos los registros cuya clave comienza por el text de referencia [EN]  All records whose key begins with the reference text
     */
    public <T> List<T> findRecordsKeyContains(String text, Class<T> cls) {
        return findRecordsByFilter(SQLStrings.createKeyContains(text, cls), cls);}


//READING RECORDS, ASYNCHRONOUS___________________________________________________________________

    /*RESULTADOS SINGULARES [EN]  SINGULAR RESULTS */

    /**
     * Clase de lectura de dato simple asíncrona
     * <p>
     * [EN]  Asynchronous Simple Data Reading Class
     *
     * @param <T> Objeto genérico a instanciar [EN]  Generic object to instantiate
     */
    @SuppressLint("StaticFieldLeak")
    @SuppressWarnings("unused")
    private abstract class AsyncSingleCRUD<T> extends AsyncTask<String, Throwable, T> {
        protected OnSingleRead<T> onRead;

        public AsyncSingleCRUD(OnSingleRead<T> onRead) {
            this.onRead = onRead;
        }

        @Override
        protected void onProgressUpdate(Throwable... values) {
            super.onProgressUpdate(values);
            if (onRead != null) {
                /*Publicamos el error [EN]  We publish the error*/
                if (values[0] != null) {
                    onRead.onFailure(values[0]);
                }
            }
        }

        @Override
        protected void onPostExecute(T t) {
            super.onPostExecute(t);
            if (onRead != null) {
                onRead.onFinish(Activity.RESULT_OK, t);
            }
        }
    }

    /**
     * Recuperar un registro singular de una tabla. Método asíncrono
     * <p>
     * [EN]  Retrieve a unique record from a table.  Asynchronous method
     *
     * @param sql    Sentencia SQL para formular la lectura de la base de datos [EN]  SQL statement to formulate the reading of the database
     * @param cls    clase del objeto a instanciar [EN]  class of the object to instantiate
     * @param onRead Oyente de resultados [EN]  Listener results
     * @param <T>    Objeto genérico Parcelable de lectura [EN]  Generic object Parcelable of reading
     */
    @SuppressLint("StaticFieldLeak")
    private <T> AsyncTask getRecord(String sql, Class<T> cls, OnSingleRead<T> onRead) {
        return new AsyncSingleCRUD<T>(onRead) {
            @SuppressWarnings("unchecked")
            @Override
            protected T doInBackground(String... params) {

                T o = null;

                if (params[0] == null) {
                    publishProgress(new NullPointerException("sql nulo"));
                    return null;
                }

                Class<T> tClass;
                try {
                    tClass = (Class<T>) Class.forName(params[1]);
                } catch (ClassNotFoundException e) {
                    publishProgress(e);
                    return null;
                }

                /*Asegurar la conexión de la base de datos [EN]  Secure the database connection*/
                reconectDatabase();
                /*Crear cursor [EN]  Create cursor*/
                Cursor rs = db.rawQuery(params[0], null);
             /*Recorrer cursor [EN]  Move cursor*/
                if (rs.moveToFirst()) {
                    try {
                        /*Publicar lectura [EN]  Publish reading*/
                        o = SQLStrings.getGenericByReflection(rs, tClass);
                    } catch (ClassCastException | InstantiationException | IllegalAccessException e) {
                        /*Publicar error [EN]  Post error*/
                        publishProgress(e);
                    }
                }

                /*Cerrar cursor [EN]  Close cursor*/
                rs.close();
                return o;
            }
        }.execute(sql, cls.getCanonicalName());
    }

    /**
     * Localizar un registro por su clave primaria. Método síncrono
     * <p>
     * [EN]  Locate a record by its primary key. Synchronous method
     *
     * @param key    Valor de la clave primaria [EN]  Primary Key Value
     * @param cls    clase del objeto a instanciar [EN]  class of the object to instantiate
     * @param onRead Oyente de resultados [EN]  Listener results
     * @param <T>    Objeto genérico Parcelable de lectura [EN]  Generic object Parcelable of reading
     */
    public <T> AsyncTask findRecordByKey(Object key, Class<T> cls, OnSingleRead<T> onRead) {
        return getRecord(SQLStrings.findRecordSql(key, cls), cls, onRead);
    }

    /**
     * Recuperar el primer registro que coincide con el filtro
     * <p>
     * [EN]  Recover the first record that matches the filter
     *
     * @param filter Filtro de la búsqueda [EN]  Search Filter
     * @param cls    clase del objeto a instanciar [EN]  class of the object to instantiate
     * @param onRead Oyente de resultados [EN]  Listener results
     * @param <T>    Objeto genérico Parcelable de lectura [EN]  Generic object Parcelable of reading
     */
    public <T> AsyncTask findRecordByFilter(String filter, Class<T> cls, OnSingleRead<T> onRead) {
        return getRecord(SQLStrings.selectAll(cls) + SQLStrings.createFilter(filter), cls, onRead);
    }

    /*RESULTADO MULTIPLE [EN]  MULTIPLE RESULT*/

    /**
     * Clase de lectura de datos multiples asíncrona
     * <p>
     * [EN]  Asynchronous Multiple Data Reading Class
     *
     * @param <T> Objeto genérico a instanciar [EN]  Generic object to instantiate
     */
    @SuppressLint("StaticFieldLeak")
    @SuppressWarnings("unused")
    private abstract class AsyncListCRUD<T> extends AsyncTask<String, AsyncPublishObject<T>, Void> {

        protected OnRead<T> onRead;

        public AsyncListCRUD(OnRead<T> onRead) {
            this.onRead = onRead;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (onRead != null && !isCancelled()) {
                onRead.onStart(null);
            }
        }

        @SafeVarargs
        @Override
        protected final void onProgressUpdate(AsyncPublishObject<T>... values) {
            super.onProgressUpdate(values);
            if (onRead != null && !isCancelled()) {
                /*Publicamos el objeto [EN]  We publish the object*/
                if (values[0].getRecord() != null) {
                    onRead.onUpdate(values[0].getRecord());
                }
                /*Publicamos el error [EN]  We publish the error*/
                if (values[0].getE() != null) {
                    onRead.onFailure(values[0].getE());
                }
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (onRead != null && !isCancelled()) {
                onRead.onFinish(aVoid);
            }
        }
    }

    /**
     * Recuperar registros de una tabla. Método asíncrono
     * <p>
     * [EN]  Recover records from a table. Asynchronous method
     *
     * @param sql    Sentencia SQL para formular la lectura de la base de datos [EN]  SQL statement to formulate the reading of the database
     * @param cls    clase del objeto a instanciar [EN]  class of the object to instantiate
     * @param onRead Oyente de resultados [EN]  Listener results
     * @param <T>    Objeto genérico Parcelable de lectura [EN]  Generic object Parcelable of reading
     */
    @SuppressLint("StaticFieldLeak")
    private <T> AsyncTask getRecords(String sql, Class<T> cls, OnRead<T> onRead) {
        return new AsyncListCRUD<T>(onRead) {
            @SuppressWarnings("unchecked")
            @Override
            protected Void doInBackground(String... params) {

                if (params[0] == null) {
                    publishProgress(new AsyncPublishObject<T>(new NullPointerException("sql nulo")));
                    return null;
                }

                Class<T> tClass;
                try {
                    tClass = (Class<T>) Class.forName(params[1]);
                } catch (ClassNotFoundException e) {
                    publishProgress(new AsyncPublishObject<T>(e));
                    return null;
                }

                /*Asegurar la conexión de la base de datos [EN]  Secure the database connection*/
                reconectDatabase();
                /*Crear cursor [EN]  Create cursor*/
                Cursor rs = db.rawQuery(params[0], null);
             /*Recorrer cursor [EN]  Move cursor*/
                for (rs.moveToFirst(); !rs.isAfterLast(); rs.moveToNext()) {
                    try {
                        /*Publicar lectura [EN]  Publish reading*/
                        publishProgress(new AsyncPublishObject<>(SQLStrings.getGenericByReflection(rs, tClass)));
                    } catch (ClassCastException | InstantiationException | IllegalAccessException e) {
                        /*Publicar error [EN]  Post error*/
                        publishProgress(new AsyncPublishObject<T>(e));
                    }
                }

                /*Cerrar cursor [EN]  Close cursor*/
                rs.close();
                return null;
            }
        }.execute(sql, cls.getCanonicalName());
    }

    /**
     * Recuperar todos los registros de una tabla
     * <p>
     * [EN]  Retrieve all records in a table
     *
     * @param cls    clase del objeto a instanciar [EN]  class of the object to instantiate
     * @param onRead Oyente de resultados [EN]  Listener results
     * @param <T>    Objeto genérico Parcelable de lectura [EN]  Generic object Parcelable of reading
     */
    @SuppressWarnings("UnusedReturnValue")
    public <T> AsyncTask getAllRecords(Class<T> cls, OnRead<T> onRead) {
        return getRecords(SQLStrings.selectAll(cls), cls, onRead);
    }

    /**
     * Recuperar registros coincidentes con el filtro
     * <p>
     * [EN]  Recover matched records with filter
     *
     * @param filter Filtro de la búsqueda [EN]  Search Filter
     * @param cls    clase del objeto a instanciar [EN]  class of the object to instantiate
     * @param onRead Oyente de resultados [EN]  Listener results
     * @param <T>    Objeto genérico Parcelable de lectura [EN]  Generic object Parcelable of reading
     */
    public <T> AsyncTask findRecordsByFilter(String filter, Class<T> cls, OnRead<T> onRead) {
        return getRecords(SQLStrings.selectAll(cls) + SQLStrings.createFilter(filter), cls, onRead);
    }

    /**
     * Recuperar registros coincidentes con la clave foránea
     * <p>
     * [EN]  Recover records matching the foreign key
     *
     * @param parentkey clave foránea [EN]  foreign key
     * @param cls       clase del objeto a instanciar [EN]  class of the object to instantiate
     * @param onRead    Oyente de resultados [EN]  Listener results
     * @param <T>       Objeto genérico Parcelable de lectura [EN]  Generic object Parcelable of reading
     */
    public <T> AsyncTask findRecordsByParent(Object parentkey, Class<T> cls, OnRead<T> onRead) {
        return getRecords(SQLStrings.findRecordByParent(parentkey, cls), cls, onRead);
    }

    /**
     * @param text   de comienzo de la clave primaria [EN]  of the beginning of the primary key
     * @param cls    clase del objeto a instanciar [EN]  class of the object to instantiate
     * @param <T>    Objeto genérico Parcelable de lectura [EN]  Generic object Parcelable of reading
     * @param onRead Oyente de resultados [EN]  Listener results
     * @return Todos los registros cuya clave comienza por el text de referencia [EN]  All records whose key begins with the reference text
     */
    public <T> AsyncTask findRecordsKeyStartWith(String text, Class<T> cls, OnRead<T> onRead) {
       return  findRecordsByFilter(SQLStrings.createKeyStartWith(text, cls), cls, onRead);
    }

    /**
     * @param text   de terminación de la clave primaria [EN]  termination of the primary key
     * @param cls    clase del objeto a instanciar [EN]  class of the object to instantiate
     * @param <T>    Objeto genérico Parcelable de lectura [EN]  Generic object Parcelable of reading
     * @param onRead Oyente de resultados [EN]  Listener results
     * @return Todos los registros cuya clave comienza por el text de referencia [EN]  All records whose key begins with the reference text
     */
    public <T> AsyncTask findRecordsKeyEndWith(String text, Class<T> cls, OnRead<T> onRead) {
       return  findRecordsByFilter(SQLStrings.createKeyEndWith(text, cls), cls, onRead);
    }

    /**
     * @param text   contenido en la clave primaria [EN]  content in the primary key
     * @param cls    clase del objeto a instanciar [EN]  class of the object to instantiate
     * @param <T>    Objeto genérico Parcelable de lectura [EN]  Generic object Parcelable of reading
     * @param onRead Oyente de resultados [EN]  Listener results
     * @return Todos los registros cuya clave comienza por el text de referencia [EN]  All records whose key begins with the reference text
     */
    public <T> AsyncTask findRecordsKeyContains(String text, Class<T> cls, OnRead<T> onRead) {
        return  findRecordsByFilter(SQLStrings.createKeyContains(text, cls), cls, onRead);
    }

    //  AGGREGATE FUNCTIONS_______________________________________________________________

    /**
     * Recupera el número de registros para una consulta definida
     * <p>
     * [EN]  Retrieve the number of records for a defined query
     *
     * @param sql Sentencia SQL para formular la lectura de la base de datos [EN]  SQL statement to formulate the reading of the database
     * @return número de registros o -1 si no se genera el cursor [EN]  number of records or -1 if the cursor is not generated
     */
    private int recordsCount(String sql) {
        /*Asegurar la conexión de la base de datos [EN]  Secure the database connection*/
        reconectDatabase();
                /*Crear cursor [EN]  Create cursor*/
        Cursor rs = db.rawQuery(sql, null);
        int out = rs != null ? rs.getCount() : -1;
        if (rs != null) {
            rs.close();
        }
        return out;
    }

    /**
     * Recuperar el número de registros de una tabla
     * <p>
     * [EN]  Retrieve the number of records in a table
     *
     * @param cls clase del objeto a instanciar [EN]  class of the object to instantiate
     * @param <T> Objeto genérico Parcelable de lectura [EN]  Generic object Parcelable of reading
     * @return Número total de registros de una tabla [EN]  Total number of records in a table
     */
    public <T> int countAll(Class<T> cls) {
        return recordsCount(SQLStrings.selectAll(cls));
    }

    /**
     * Número de registros que se corresponden con un filtro
     * <p>
     * [EN]  Number of records that correspond to a filter
     *
     * @param filter filtro de búsqueda [EN]  search filter
     * @param cls clase del objeto a instanciar [EN]  class of the object to instantiate
     * @param <T> Objeto genérico Parcelable de lectura [EN]  Generic object Parcelable of reading
     * @return Número total de registros de una tabla [EN]  Total number of records in a table
     */
    public <T> int countByFilter(String filter, Class<T> cls) {
        return recordsCount(SQLStrings.selectAll(cls) + SQLStrings.createFilter(filter));
    }



    //BLOCK RECORDING, ASYNCHRONOUS

    @SuppressWarnings("unchecked")
    public AsyncTask addDataList(List<Object> data, OnBlockSaved onBlockSaved) {
        return new AddSyncList(onBlockSaved).execute(data);
    }

    /**
     * Subclase asíncrona de grabación por bloques
     * <p>
     * [EN]  Asynchronous Block Recording Subclass
     */
    @SuppressLint("StaticFieldLeak")
    @SuppressWarnings("unused")
    public class AddSyncList extends AsyncTask<List<Object>, Integer, Void> {
        protected OnBlockSaved onBlockSaved;

        public AddSyncList(OnBlockSaved onBlockSaved) {
            this.onBlockSaved = onBlockSaved;
        }

        @SafeVarargs
        @Override
        protected final Void doInBackground(List<Object>... params) {
            int count = 0;
            for (Object o : params[0]) {
                //Guardar objeto [EN]  Save object
                if (o != null) {
                    //noinspection ThrowableResultOfMethodCallIgnored
                    addRecord(o);
                }
                //Notificar grabación [EN]  Notify recording
                publishProgress(++count);
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (onBlockSaved != null && !isCancelled()) {
                onBlockSaved.onStart(null);
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (onBlockSaved != null && !isCancelled()) {
                onBlockSaved.onUpdate(values[0]);
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (onBlockSaved != null && !isCancelled()) {
                onBlockSaved.onFinish(null);
            }

        }
    }

    //LISTENERS_________________________________________________________________________________________________
    public interface OnConect extends TaskResult<Boolean> {
    }

    public interface OnChanged extends TaskFailure<Boolean> {
    }

    public interface OnRead<T> extends DataUploaderTask<Void, T, Void> {
    }

    public interface OnSingleRead<T> extends TaskFailure<T> {
    }

    public interface OnBlockSaved extends DataUploaderTask<Void, Integer, Void> {
    }
}
