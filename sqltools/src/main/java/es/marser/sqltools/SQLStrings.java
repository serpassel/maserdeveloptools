package es.marser.sqltools;

import android.database.Cursor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import es.marser.annotation.DbColumn;
import es.marser.annotation.DbColumnInclosed;
import es.marser.annotation.DbPrimaryKey;
import es.marser.annotation.DbTable;
import es.marser.tools.TextTools;



/**
 * @author sergio
 *         Created by Sergio on 21/04/2017.
 *         Métodos estáticos para creación de sentencias SQL
 *         
 *         <p>Estructura de base de datos
 *         <p>Recuperación de datos
 *         <p>Manejo de registros
 *         <p>Filtro y orden
 *         <p>Transformación de clases mapeadas en datos
 *         <p>Transformación de datos en clase mapeadas
 *         
 *         <p>
 *         Static methods for creating SQL statements
 *         
 *         <p>[EN]  Database structure
 *         <p>[EN]  Data recovery
 *         <p>[EN]  Records Management
 *         <p>[EN]  Filter and Order
 *         <p>[EN]  Transformation of mapped classes into data
 *         <p>[EN]  Transformation of data in mapped class
 *         
 */

@SuppressWarnings({"WeakerAccess", "unused", "SameParameterValue"})
public abstract class SQLStrings {

    /**
     * Enumeración de campos de autocreación
     * <p>
     * [EN]  Enumeration of self-creation fields
     */
    @SuppressWarnings("unused")
    public enum inclosed {
        /*Tiempo de creación del registro [EN]  Record creation time es*/
        CREATION {
            public String toString() {
                return "creacion";
            }
        },
        /*Última modificación del registro [EN]  Last modification of the registry*/
        MODIFICATION {
            public String toString() {
                return "modificacion";
            }
        }
    }

    //ESTRUCTURA DE BASE DE DATOS [EN]  DATABASE STRUCTURE_______________________________________________________

    /**
     * Creación de tablas por reflexión de java. Crea la tabla si no existe
     * <p>
     * [EN]  Creating tables by java reflection. Creates the table if it does not exist
     * <p>
     * <p>Nombre de la tabla {@link es.marser.annotation.DbTable} [EN]  Name of table
     * <p>Clave primaria {@link es.marser.annotation.DbPrimaryKey} [EN]  Primary Key
     * <p>Campos de la tabla {@link es.marser.annotation.DbColumn} [EN] Table Fields
     * <p>Campos autocreados {@link es.marser.annotation.DbTable} [EN] Self-created fields
     *
     * @param obj POJO mapeado [EN] [EN]  mapped POJO
     * @return Sentencia SQL de creación de tablas [EN]  SQL table creation statement
     */
    public static String createTable(Class obj) {

        StringBuilder out = new StringBuilder();
        Field[] f = obj.getDeclaredFields();
        Annotation a;

        out.append("CREATE TABLE IF NOT EXISTS ");
        /*Definir nombre de la tabla [EN]  Define table name*/
        a = obj.getAnnotation(DbTable.class);
        if (a != null) {
            out.append(((DbTable) a).name());
        }
        out.append("(");//Parámetro de apertura

        /*Definir clave primaria [EN]  Define primary key*/
        for (Field f1 : f) {
            a = f1.getAnnotation(DbPrimaryKey.class);
            if (a != null) {
                out.append(((DbPrimaryKey) a).id_name());//Nombre del campo de la clave primaria
                out.append(" ");
                out.append(getDbCastName(f1.getType()));//Tipo de datos, normalmento Integer en este campo
                out.append(" PRIMARY KEY");
                if (((DbPrimaryKey) a).autoincrement()) {
                    out.append(" AUTOINCREMENT");//Indica si el campo es autoincremental
                }
            }
        }

        /*Definir campos [EN]  Define Fields*/
        for (Field f1 : f) {
            a = f1.getAnnotation(DbColumn.class);
            if (a != null) {
                out.append(", ");
                out.append(((DbColumn) a).col_name());//Nombre del campo [EN]  Field Name
                out.append(" ");
                out.append(getDbCastName(f1.getType()));//Tipo de variable [EN]  Variable Type
                if (((DbColumn) a).notnull()) {
                    out.append(" NOT NULL");//No acepta valores nulos [EN]  Does not accept null values
                }
                if (((DbColumn) a).unique()) {
                    out.append(" UNIQUE");//  no acepta valores duplicados [EN]  does not accept duplicate values
                }

                if (!TextTools.isEmpty(((DbColumn) a).defaultValue())) {
                    out.append(getDbCastDefaultValue(f1.getType(), ((DbColumn) a).defaultValue()));
                }
            }
        }

        /*Agregar campo de autocreación [EN]  Add auto-create field*/
        out.append(", ")
                .append(inclosed.CREATION).append(" INTEGER")
                .append(", ")
                .append(inclosed.MODIFICATION)
                .append(" INTEGER")
                .append(")");

        return out.toString();
    }

    /**
     * Crea la sentencia de eliminación de una tabla
     * <p>
     * [EN]  Creates the delete statement for a table
     * Nombre de la tabla {@link es.marser.annotation.DbTable} [EN]  Name of table
     *
     * @param obj POJO mapeado [EN] mapped POJO
     * @return Sentencia SQL de eliminación de tablas [EN] SQL statement of drop tables
     * @see es.marser.sqltools.examples.PojoExample
     */
    public static String dropTable(Class obj) {
        Annotation a;
        a = obj.getAnnotation(DbTable.class);
        return dropTable(TextTools.nc(((DbTable) a).name()));
    }

    /**
     * Crea la sentencia de eliminación de una tabla
     * <p>
     * [EN]  Creates the delete statement for a table
     *
     * @param name Nombre de la tabla a eliminar [EN]  Name of the table to drop
     * @return Sentencia SQL de eliminación de tablas [EN]  SQL statement of drop tables
     * @see es.marser.sqltools.examples.PojoExample
     */
    public static String dropTable(String name) {
        return "DROP TABLE IF EXISTS " +
                name;
    }

    /**
     * Crea una sentencia para renombra una tabla
     * <p>
     * [EN]  Create a statement to rename a table
     *
     * @param oldname nombre antiguo [EN]  old name
     * @param newname nombre nuevo [EN]  new name
     * @return Sentencia para renombrar una tabla [EN] statement to rename a table
     */
    public static String renameTable(String oldname, String newname) {
        return "ALTER TABLE " + oldname + " RENAME TO " + newname;
    }

    /**
     * Sentencia SQl para copiar tablas
     * <p>
     * [EN]  SQl statement to copy tables
     *
     * @param fromtable nombre de la tabla origen <p>[EN]  name of the source table
     * @param totable   nombre de la tabla destino <p>[EN]  name of the target table
     * @return Sentencia SQL para duplicar tabla <p>[EN]  SQL statement to duplicate table
     */
    public static String copyTables(String fromtable, String totable) {
        return "INSERT INTO " + totable + " SELECT * FROM " + fromtable;
    }

    /**
     * Lista las nuevas columnas a añadir según la versión mapeada
     * <p>
     * [EN]  List the new columns to be added according to the mapped version
     * Nombre de la tabla {@link es.marser.annotation.DbTable} [EN]  Name of table
     * Campos de la tabla {@link es.marser.annotation.DbColumn} [EN] Table Fields
     *
     * @param obj        POJO mapeado <p> [EN]  POJO mapeado
     * @param oldversion versión de la base de datos anterior <p>[EN]  version of the previous database
     * @return Listado de nombre de columnas nuevas a añadir <p>[EN]  List of names of new columns to add
     */
    public static List<String> addColumns(Class obj, int oldversion) {
        List<String> out = new ArrayList<>();
        String tablename = ((DbTable) obj.getAnnotation(DbTable.class)).name();

        Field[] fields = obj.getDeclaredFields();
        for (Field f : fields) {
            Annotation a = f.getAnnotation(DbColumn.class);
            if (a != null && ((DbColumn) a).version() > oldversion) {
                out.add(addColumn(tablename, f));
            }
        }
        return out;
    }

    /**
     * Crea la sentencia SQl para agregar un columna a una tabla, por métodos de reflexión
     * <p>
     * [EN]  Creates the SQl statement to add a column to a table, by reflection methods
     * <p>
     * Campos de la tabla {@link es.marser.annotation.DbColumn} [EN] Table Fields
     *
     * @param tableName nombre de la tabla <p>[EN]  table name
     * @param field     campo a agregar <p>[EN]  field to add
     * @return Sentencia SQl agregar columna <p>EN]  SQl statement add column
     * @see es.marser.annotation
     */
    private static String addColumn(String tableName, Field field) {
        StringBuilder builder = new StringBuilder();
        Annotation a = field.getAnnotation(DbColumn.class);
        if (a != null) {
            builder.append("ALTER TABLE ")
                    .append(tableName)
                    .append(" ADD COLUMN ")
                    .append(((DbColumn) a).col_name())//Nombre del campo [EN]  Field Name
                    .append(" ")
                    .append(getDbCastName(field.getType()));//Tipo de variable [EN]  Variable Type


            if (((DbColumn) a).notnull()) {
                builder.append(" NOT NULL");//No acepta valores nulos [EN]  Does not accept null values
            }
            if (((DbColumn) a).unique()) {
                builder.append(" UNIQUE");//  no acepta valores duplicados [EN]  does not accept duplicate values
            }

            if (!TextTools.isEmpty(((DbColumn) a).defaultValue())) {
                builder.append(getDbCastDefaultValue(field.getType(), ((DbColumn) a).defaultValue()));
            }
        }
        return builder.toString();
    }

//LECTURA DE DATOS [EN]  DATA READING

    /**
     * Recupera todos los registros de una tabla
     * <p>
     * [EN]  Recovers all records in a table
     * <p>Nombre de la tabla {@link es.marser.annotation.DbTable} [EN]  Name of table
     *
     * @param cls Clase mapeada [EN]  SQl statement for reading all records
     * @return Sentencia SQl para lectura de todos los registros [EN]  SQl statement for reading all records
     */
    public static String selectAll(Class cls) {
        StringBuilder out = new StringBuilder();
        Annotation a;
        out.append("SELECT * FROM ");
        a = cls.getAnnotation(DbTable.class); //Recuperamos el nombre de la tabla
        out.append(((DbTable) a).name());
        return out.toString();
    }

    /**
     * Genera clausula de búsqueda por la clave primaria. Añadir a sentencia select
     * <p>
     * [EN]  Generates primary keyword search clause. [EN]  Add to select statement
     * <p>
     * Clave primaria {@link es.marser.annotation.DbPrimaryKey} [EN]  Primary Key
     *
     * @param key Valor de la clave primaria [EN]  Primary Key Value
     * @param cls Clase mapeada [EN]  Primary Key Value
     * @return Cadena de texto con la clausula de búsqueda [EN]  Text string with the search clause
     */
    public static String findRecordSql(Object key, Class cls) {
        StringBuilder sql = new StringBuilder();
        sql.append(SQLStrings.selectAll(cls)); //Creamos la consulta general
        //Buscamos el nombre del campo con la clave forénea
        Field[] f = cls.getDeclaredFields();
        Annotation a;
        //Creamos la estructura de los campos del objeto hijo a filtrar
        if (key != null) {


            for (Field f1 : f) {
                a = f1.getAnnotation(DbPrimaryKey.class);
                if (a != null) {
                    sql.append(" WHERE ");
                    sql.append(((DbPrimaryKey) a).id_name());
                    sql.append(" = ");
                }
            }

            sql.append(transformValuesToDb(key));
        }
        return sql.toString();
    }

    /**
     * Crea un sentencia por la clave padre. Añadir a sentencia de selección
     * <p>
     * [EN]  Creates a statement by the parent key.  Add to selection statement
     * <p>
     * Campos de la tabla {@link es.marser.annotation.DbColumn} [EN] Table Fields
     *
     * @param parentKey Índice de registro padre [EN]  Parent Registration Index
     * @param cls       Clase mapeada [EN]  Mapped class
     * @return Clausula de búsqueda por la clave padre [EN]  Search clause by parent key
     */
    public static String findRecordByParent(Object parentKey, Class cls) {
        StringBuilder sql = new StringBuilder();
        sql.append(SQLStrings.selectAll(cls)); //Crear la consulta general [EN]  Create the general query
        //Buscar el nombre del campo con la clave forénea [EN]  Search for the field name with the forename
        Field[] f = cls.getDeclaredFields();
        Annotation a;
        //Crear la estructura de los campos del objeto hijo a filtrar [EN]  Create the structure of the child object fields to be filtered
        if (parentKey != null) {

            for (Field f1 : f) {
                a = f1.getAnnotation(DbColumn.class);
                if (a != null && ((DbColumn) a).isParentKey()) {
                    sql.append(" WHERE ");
                    sql.append(((DbColumn) a).col_name());
                    sql.append(" = ");
                }
            }

            sql.append(transformValuesToDb(parentKey));
        }
        return sql.toString();
    }

// MANEJO DE REGISTROS [EN]  RECORD MANAGEMENT________________________________________________________

    /**
     * Crea sentencia SQL para inserción del registro
     * <p>
     * [EN]  Create SQL statement for record insertion
     * <p>Nombre de la tabla {@link es.marser.annotation.DbTable} [EN]  Name of table
     * <p>Clave primaria {@link es.marser.annotation.DbPrimaryKey} [EN]  Primary Key
     * <p>Campos de la tabla {@link es.marser.annotation.DbColumn} [EN] Table Fields
     * <p>Campos autocreados {@link es.marser.annotation.DbTable} [EN] Self-created fields
     *
     * @param obj Objecto mapeado [EN]  Mapped object
     * @return Sentencia SQL de inserción de datos [EN]  SQL statement of data insertion
     * @throws IllegalAccessException No existe el método de inserción en la clase mapeada [EN]  There is no insertion method in the mapped class
     * @see #insertValues(StringBuilder, Field, Object)
     */
    public static String insertRecord(Object obj) throws IllegalAccessException {
        StringBuilder out = new StringBuilder();
        StringBuilder col = new StringBuilder();
        StringBuilder values = new StringBuilder();
        Class cls = obj.getClass();

        Field[] f = cls.getDeclaredFields();
        Annotation a;

        //Nombre de la tabla [EN]  Name of table
        out.append("INSERT INTO ");
        a = cls.getAnnotation(DbTable.class);
        if (a == null) {
            throw new NullPointerException("Anotación de entidad nula [EN]  Annotation of null entity");
        }
        out.append(((DbTable) a).name());
        /*
         * Iniciar la entrada de campos y valores
         * Utilizar dos StringBuilder una para cada tipo de dato
         *
         * [EN]  Start entering fields and values
         * Use two StringBuilder one for each data type
         */
        //Abrir los paréntesis [EN]  Open parentheses
        col.append("(");
        values.append("(");

        //Comenzar por el primary key [EN]  Start with the primary key
        for (Field f1 : f) {
            a = f1.getAnnotation(DbPrimaryKey.class);
            if (a != null) {
                col.append(((DbPrimaryKey) a).id_name());//Nombre del campo de la clave primaria [EN]  Name of the primary key field
                //Comprobar si es autoincremental [EN]  / Check if it is autoincremental
                if (((DbPrimaryKey) a).autoincrement()) {
                    values.append("NULL");
                } else {
                    //Hackear la variable [EN]  Hack the variable
                    f1.setAccessible(true);
                    //Comprobar el tipo de valor para asignarlo [EN]  Check the type of value to assign it
                    insertValues(values, f1, obj);
                }
            }
        }
        //Continuar con el resto de valores [EN]  Continue with the rest of values
        for (Field f1 : f) {
            a = f1.getAnnotation(DbColumn.class);
            if (a != null) {
                col.append(", ");
                col.append(((DbColumn) a).col_name());//Nombre del campo de la clave primaria [EN]  Name of the primary key field
                f1.setAccessible(true);//Hackear la variable [EN]  Hack the variable
                //Comprobar el tipo de valor para asignarlo [EN]  Check the type of value to assign it
                values.append(", ");
                insertValues(values, f1, obj);
            }

        }

        //Insertar las fechas de creación y modificación [EN]  Insert the creation and modification dates
        GregorianCalendar gc = new GregorianCalendar();
        //Nombres de campo [EN]  Field names
        col.append(", ");
        col.append(inclosed.CREATION);
        col.append(", ");
        col.append(inclosed.MODIFICATION);
        //Valores de campo [EN]  Field Values
        values.append(", ");
        values.append(gc.getTimeInMillis());
        values.append(", ");
        values.append(gc.getTimeInMillis());

        //Volcar a la variable de construcción principal [EN]  Dump the main build variable
        out.append(col.toString());//Introducir lo nombres de columna [EN]  Enter column names
        out.append(") VALUES ");//Iniciar entrada de valores [EN]  Start values ​​input
        out.append(values.toString());//Introducir los valores [EN]  Enter values
        out.append(")");

        return out.toString();

    }

    /**
     * Crea sentencia SQL para actualizar o insertar (si no existe) un registro
     * <p>
     * [EN]  Create SQL statement to update or insert (if there is not) a record
     *
     * @param obj Objecto mapeado [EN]  Mapped object
     * @return Sentencia SQL de actualización o inserción de datos [EN]  SQL statement to update or insert data
     * @throws IllegalAccessException No existe el método de inserción en la clase mapeada [EN]  There is no insertion method in the mapped class
     */
    public static String insertOrReplaceRecord(Object obj)
            throws IllegalAccessException {
        StringBuilder out = new StringBuilder();
        StringBuilder col = new StringBuilder();
        StringBuilder values = new StringBuilder();
        Class cls = obj.getClass();

        Field[] f = cls.getDeclaredFields();
        Annotation a;

        //Nombre de la tabla [EN]  Name of table
        out.append("INSERT OR REPLACE INTO ");
        a = cls.getAnnotation(DbTable.class);
        if (a == null) {
            throw new NullPointerException("Anotación de entidad nula [EN]  Annotation of null entity");
        }
        out.append(((DbTable) a).name());
        /*
         * Iniciar la entrada de campos y valores
         * Utilizar dos StringBuilder una para cada tipo de dato
         *
         * [EN]  Start entering fields and values
         *  Use two StringBuilder one for each data type
         */
        //Abrir paréntesis [EN]  Open parentheses
        col.append("(");
        values.append("(");
        //Comenzar por el primary key [EN]  Start with the primary key
        for (Field f1 : f) {
            a = f1.getAnnotation(DbPrimaryKey.class);
            if (a != null) {
                col.append(((DbPrimaryKey) a).id_name());//Nombre del campo de la clave primaria [EN]  Name of the primary key field
                //Comprobar si es autoincremental [EN]  Check if it is autoincremental
                if (((DbPrimaryKey) a).autoincrement()) {
                    values.append("NULL");
                } else {
                    //Hackear variable [EN]  Hacking variable
                    f1.setAccessible(true);
                    //Comprobar el tipo de valor y asignarlo [EN]  Check the value type and assign it
                    insertValues(values, f1, obj);
                }
            }
        }
        //Continuar con el resto de valores [EN]  Continue with the rest of values
        for (Field f1 : f) {
            a = f1.getAnnotation(DbColumn.class);
            if (a != null) {
                col.append(", ");
                col.append(((DbColumn) a).col_name());//Nombre del campo [EN]  Field Name
                f1.setAccessible(true);//Hackear variable [EN]  Hacking variable
                //Comprobar el tipo de valor y asignarlo [EN]  Check the value type and assign it
                values.append(", ");
                insertValues(values, f1, obj);
            }

        }

        //Insertar las fechas de creación y modificación [EN]  Insert the creation and modification dates
        GregorianCalendar gc = new GregorianCalendar();
        //Nombres de campo [EN]  Field names
        col.append(", ");
        col.append(inclosed.CREATION);
        col.append(", ");
        col.append(inclosed.MODIFICATION);
        //Valores de campo [EN]  Field Values
        values.append(", ");
        values.append(gc.getTimeInMillis());
        values.append(", ");
        values.append(gc.getTimeInMillis());

        //Volcar a la variable de construcción principal [EN]  Dump the main build variable
        out.append(col.toString());//Introducir lo nombres de columna [EN]  Enter column names
        out.append(") VALUES ");//Iniciar entrada de valores [EN]  Start values ​​input
        out.append(values.toString());//Introducir los valores [EN]  Enter values
        out.append(")");

        return out.toString();
    }

    /**
     * Método para creación de sentencia SQL para inserción de datos sólo si no existe
     * <p>
     * [EN]  Method for creating SQL statement for data insertion only if it does not exist
     *
     * @param obj Objecto mapeado [EN]  Mapped object
     * @return Sentencia SQL de inserción de datos sóĺo si no existe [EN]  Data insertion SQL statement only if it does not exist
     * @throws IllegalAccessException No existe el método de inserción en la clase mapeada [EN]  There is no insertion method in the mapped class
     */
    public static String insertOrIgnoreRecord(Object obj)
            throws IllegalAccessException {
        StringBuilder out = new StringBuilder();
        StringBuilder col = new StringBuilder();
        StringBuilder values = new StringBuilder();
        Class cls = obj.getClass();

        Field[] f = cls.getDeclaredFields();
        Annotation a;

        //Nombre de la tabla [EN]  Name of table
        out.append("INSERT OR IGNORE INTO ");
        a = cls.getAnnotation(DbTable.class);
        if (a == null) {
            throw new NullPointerException("Anotación de entidad nula [EN]  Annotation of null entity");
        }
        out.append(((DbTable) a).name());
        /*
         * Iniciar la entrada de campos y valores
         * Utilizar dos StringBuilder una para cada tipo de dato
         *
         * [EN]  Start entering fields and values
         * Use two StringBuilder one for each data type
         */
        //Abrir los paréntesis [EN]  Open parentheses
        col.append("(");
        values.append("(");

        //Comenzar por el primary key [EN]  Start with the primary key
        for (Field f1 : f) {
            a = f1.getAnnotation(DbPrimaryKey.class);
            if (a != null) {
                col.append(((DbPrimaryKey) a).id_name());//Nombre del campo de la clave primaria
                //Comprobamos si es autoincremental
                if (((DbPrimaryKey) a).autoincrement()) {
                    values.append("NULL");
                } else {
                    //Hackeamos la variable
                    f1.setAccessible(true);
                    //Comprobamos el tipo de valor para asignarlo
                    insertValues(values, f1, obj);
                }
            }
        }
        //Continuamos con el resto de valores
        for (Field f1 : f) {
            a = f1.getAnnotation(DbColumn.class);
            if (a != null) {
                col.append(", ");
                col.append(((DbColumn) a).col_name());//Nombre del campo de la clave primaria
                f1.setAccessible(true);//Hacemos el campo accesible para evitar restrincciones de acceso private, protected etc
                //Comprobamos el tipo de valor para asignarlo
                values.append(", ");
                insertValues(values, f1, obj);
            }

        }

        //Insertamos las fechas de creación y modificación
        GregorianCalendar gc = new GregorianCalendar();
        //Nombres de campo
        col.append(", ");
        col.append(inclosed.CREATION);
        col.append(", ");
        col.append(inclosed.MODIFICATION);
        //Valores de campo
        values.append(", ");
        values.append(gc.getTimeInMillis());
        values.append(", ");
        values.append(gc.getTimeInMillis());

        //Volcamos a la variable de construcción principal
        out.append(col.toString());//Introducimos lo nombres de columna
        out.append(") VALUES ");//Iniciamos entrada de valores
        out.append(values.toString());//Introducimos los valores
        out.append(")");

        return out.toString();

    }


    /**
     * Método para creación de sentencia SQL para actualización de datos
     * <p>
     * [EN]  Method for creating SQL statement for data update
     *
     * @param obj Objecto mapeado [EN]  Mapped object
     * @return Sentencia SQL de actualización de datos [EN]  SQL statement of data update
     * @throws IllegalAccessException No existe el método de inserción en la clase mapeada [EN]  There is no insertion method in the mapped class
     */
    public static String upRecord(Object obj)
            throws IllegalAccessException {

        StringBuilder out = new StringBuilder();
        Class cls = obj.getClass();

        Field[] f = cls.getDeclaredFields();
        Annotation a;

        a = cls.getAnnotation(DbTable.class);
        out.append("UPDATE ");
        if (a != null) {
            out.append(((DbTable) a).name());
            out.append(" SET ");
        }
        //Comenzamos a actualizar registros
        for (Field f1 : f) {
            a = f1.getAnnotation(DbColumn.class);
            if (a != null) {
                out.append(((DbColumn) a).col_name());//Nombre del campo de la clave primaria
                out.append(" = ");
                f1.setAccessible(true);//Hacemos el campo accesible para evitar restrincciones de acceso private, protected etc
                //Comprobamos el tipo de valor para asignarlo
                insertValues(out, f1, obj);
                out.append(", ");
            }
        }

        //Modificamos la fecha de modificación del registro
        out.append(inclosed.MODIFICATION);
        out.append(" = ");
        out.append(new GregorianCalendar().getTimeInMillis());

        //Condición para actualizar un registro por su clave primaria
        out.append(" WHERE ");

        for (Field f1 : f) {
            a = f1.getAnnotation(DbPrimaryKey.class);
            if (a != null) {
                out.append(((DbPrimaryKey) a).id_name());//Nombre del campo de la clave primaria
                //Comprobamos si es autoincremental
                f1.setAccessible(true);
                //Comprobamos el tipo de valor para asignarlo
                out.append(" = ");
                insertValues(out, f1, obj);
            }
        }
        return out.toString();
    }

    /**
     * Método para creación de sentencia SQL para eliminación de datos
     * <p>
     * [EN]  Method for creating SQL statement for data deletion
     *
     * @param obj Objecto mapeado [EN]  Mapped object
     * @return Sentencia SQL de eliminación de datos [EN]  Data delete SQL statement es
     * @throws IllegalAccessException No existe el método en la clase mapeada [EN]  There is no method in the mapped class
     */
    public static String deleteRecord(Object obj) throws IllegalAccessException {
        StringBuilder out = new StringBuilder();
        Class cls = obj.getClass();

        Field[] f = cls.getDeclaredFields();
        Annotation a;
        //Nombre de la tabla
        out.append("DELETE FROM ");
        a = cls.getAnnotation(DbTable.class);
        if (a != null) {
            out.append(((DbTable) a).name());
            out.append(" WHERE ");
        }
        //Localizamos la primary key
        for (Field f1 : f) {
            a = f1.getAnnotation(DbPrimaryKey.class);
            if (a != null) {
                out.append(((DbPrimaryKey) a).id_name());//Nombre del campo de la clave primaria
                //Hackeamos la variable
                f1.setAccessible(true);
                //Comprobamos el tipo de valor para asignarlo
                out.append(" = ");
                insertValues(out, f1, obj);
            }
        }
        return out.toString();
    }

//FILTRO Y ORDEN [EN]  FILTER AND ORDER_________________________________________________________________________

    /**
     * Clausula SQL para aplicar un orden
     * Introducir el nombre del campo sobre el que se quiere aplicar el orden
     * Opcionalmente se puede añadir a la entrada  ASC o DESC o agregar más clausulas con AND
     * <p>
     * [EN]  SQL clause to apply an order
     * Enter the name of the field on which the order is to be applied
     * [EN]  Optionally can be added to the ASC or DESC input or add more clauses with AND
     *
     * @param order Cadena de texto con el nombre del campo sobre el que se quiere ordernar [EN]  Text string with the name of the field you want to sort
     * @return Cadena de texto con la clausula SQL de ordenamiento [EN]  String of text with the ordering SQL clause
     */
    public static String createOrder(String order) {
        return " ORDER BY " +
                TextTools.nc(order);
    }

    /**
     * Clausula SQL para aplicar un filtro
     * <p>
     * [EN]  SQL clause to apply an order
     *
     * @param filter Clausula SQl para filtrar en texto [EN]  SQl clause to filter in text
     * @return Cadena de texto con la clausula SQL para filtro [EN]  String with SQL clause for filter
     */
    public static String createFilter(String filter) {
        return " WHERE " +
                TextTools.nc(filter);
    }

    /**
     * Crea un filtro por la clave foránea
     * <p>
     * [EN]  Creates a filter by the foreign key
     *
     * @param obj Objecto mapeado [EN]  Mapped object
     * @return Clausula SQL para aplicar el filtro por la clave foránea [EN]  SQL clause to apply the filter by the foreign key
     * @throws IllegalAccessException No existe el método en la clase mapeada [EN]  There is no method in the mapped class
     */
    public static String createForeKeyFilter(Object obj) throws IllegalAccessException {
        StringBuilder out = new StringBuilder();
        Field[] f = obj.getClass().getDeclaredFields();
        Annotation a;
        out.append(" WHERE ");
        //Recorremos las variables
        int i = 0;
        for (Field f1 : f) {
            a = f1.getAnnotation(DbColumn.class);
            if (((DbColumn) a).isForeKey()) {
                if (i > 0) {
                    out.append(" AND ");
                }
                i++;
                out.append(((DbColumn) a).col_name());//Nombre del campo
                out.append(" = ");//Introducimos el símbolo de igualdad
                f1.setAccessible(true);//Hackeamos la variables para evitar claves private, protected etc
                insertValues(out, f1, obj);//Insertamos el valor
            }
        }
        return out.toString();
    }

    //TRANSFORMACION DE CLASE MAPEADA [EN]  TRANSFORMATION OF CLASS MAPPING___________________________________

    /**
     * Traducción de valores de objetos como valor de SQLite
     * <p>
     * [EN]  Translation of object values ​​as SQLite value
     *
     * @param value Valor del campo [EN]  Field value
     * @return Cadena de texto con valor traducido a SQLite [EN]  String of text with translated value to SQLite
     */
    public static StringBuilder transformValuesToDb(Object value) {
        StringBuilder out = new StringBuilder();
        if (value == null) {
            return null;
        }
        switch (value.getClass().getSimpleName()) {
            case "String":
                out.append("'");
                out.append(TextTools.toSQLString((String) value));
                out.append("'");
                break;
            case "Date":
                out.append(((Date) value).getTime());
                break;
            case "Boolean":
                if ((Boolean) value) {
                    out.append(0);
                } else {
                    out.append(-1);
                }
                break;
            case "boolean":
                if ((boolean) value) {
                    out.append(0);
                } else {
                    out.append(-1);
                }
                break;

            case "Object":
                out.append("'");
                out.append(TextTools.toSQLString(value.toString()));
                out.append("'");
                break;
            default:
                out.append(value);
                break;
        }

        return out;
    }

    /**
     * Selección del tipo de campo SQLite según el valor a registrar
     *<ul>
     * <li>String</li>
     * <li>double</li>
     * <li>Double</li>
     * <li>BigDecimal (utilizar String)</li>
     * <li>int</li>
     * <li>Integer</li>
     * <li>long</li>
     * <li>Long</li>
     * <li>Date</li>
     * <li>Boolean</li>
     * <li>boolean</li>
     * </ul>
     * <p>
     * [EN]  Selection of the type of SQLite field according to the value to register
     *
     * @param cls Clase de valor del campo [EN]  Value class of the field
     * @return Nombre del campo en SQLite [EN]  Field Name in SQLite
     */
    public static String getDbCastName(Class cls) {
        switch (cls.getSimpleName()) {
            case "String":
                return "TEXT";
            case "double":
                return "NUM";
            case "Double":
                return "NUM";
            case "BigDecimal":
                return "NUM";
            case "int":
                return "INTEGER";
            case "long":
                return "INTEGER";
            case "Long":
                return "INTEGER";
            case "Integer":
                return "INTEGER";
            case "Date":
                return "INTEGER";
            case "Boolean":
                return "INTEGER";
            case "boolean":
                return "INTEGER";
            default:
                return "TEXT";
        }
    }

    /**
     * Clausula SQLite con el valor por defecto de un campo
     * <p>
     * [EN]  SQLite clause with the default value of a field
     *
     * @param cls          clase de la variable de campo [EN]  class of the field variable
     * @param defaultvalue valor por defecto [EN]  Default value
     * @return Clausula para agregar el valor por defecto [EN]  Clause to add the default
     */
    private static String getDbCastDefaultValue(Class cls, String defaultvalue) {

        StringBuilder builder = new StringBuilder();
        switch (cls.getSimpleName()) {
            case "String":
                builder.append(" DEFAULT ").append("'").append(defaultvalue).append("'");
                break;
            case "double":
                builder.append(" DEFAULT ").append(Double.parseDouble(defaultvalue));
                break;
            case "Double":
                builder.append(" DEFAULT ").append(Double.parseDouble(defaultvalue));
                break;
            case "BigDecimal":
                builder.append(" DEFAULT ").append(new BigDecimal(defaultvalue));
                break;
            case "int":
                builder.append(" DEFAULT ").append(Integer.parseInt(defaultvalue));
                break;
            case "Integer":
                builder.append(" DEFAULT ").append(Integer.parseInt(defaultvalue));
                break;
            case "Date":
                builder.append(" DEFAULT ").append(Integer.parseInt(defaultvalue));
                break;
            case "Boolean":
                builder.append(" DEFAULT ").append(Integer.parseInt(defaultvalue));
                break;
            case "boolean":
                builder.append(" DEFAULT ").append(Integer.parseInt(defaultvalue));
                break;
            default:
                builder.append(" DEFAULT ").append("'").append(defaultvalue).append("'");
                break;
        }

        return builder.toString();
    }

    /**
     * Transforma un StringBuilder con la sentencia completa
     * <p>
     * [EN]  Transforms a StringBuilder with the complete statement
     *
     * @param f1  Campo de la clase [EN]  Class Field
     * @param obj valor del campo [EN]  field value
     */
    private static void insertValues(StringBuilder values, Field f1, Object obj)
            throws IllegalAccessException {

        if (f1.get(obj) == null) {
            values.append("NULL");
        } else {
            values.append(transformValuesToDb(f1.get(obj)));
        }
    }

    //TRANSFORMACIÓN DE DATOS EN CLASE MAPEADAS [EN]  TRANSFORMATION OF DATA IN CLASS MAPS______________________

    /**
     * Recupera los datos de una columna de la tabla de datos y setea una variable del objeto mapeado
     * <p>
     * [EN]  Retrieves the data of a column of the data table and sets a variable of the mapped object
     *
     * @param obj      variable de la clase [EN]  class variable
     * @param rs       cursor de datos [EN]  data cursor
     * @param f1       campo de la clase [EN]  class field
     * @param colindex índice de la columna [EN]  column index
     * @throws IllegalAccessException no se puede acceder al método [EN]  can not access method
     * @throws NoSuchFieldError       no se ha localizado el campo [EN]  field has not been located
     */
    private static void setColumnByReflection(Object obj, Cursor rs, Field f1, int colindex)
            throws IllegalAccessException, NoSuchFieldError {
        int b;
        f1.setAccessible(true);

        switch (f1.getType().getSimpleName()) {
            case "String":
                f1.set(obj, TextTools.fromSQLString(rs.getString(colindex)));
                break;
            case "Integer":
                f1.set(obj, rs.getInt(colindex));
                break;
            case "int":
                f1.set(obj, rs.getInt(colindex));
                break;
            case "Boolean":
                b = rs.getInt(colindex);
                if (b < 0) {
                    f1.set(obj, false);
                } else {
                    f1.set(obj, true);
                }
                break;
            case "boolean":
                b = rs.getInt(colindex);
                if (b < 0) {
                    f1.set(obj, false);
                } else {
                    f1.set(obj, true);
                }
                break;
            case "Double":
                f1.set(obj, rs.getDouble(colindex));
                break;
            case "double":
                f1.set(obj, rs.getDouble(colindex));
                break;
            case "BigDecimal":
                f1.set(obj, new BigDecimal(String.valueOf(rs.getDouble(colindex))));
                break;
            case "Date":
                b = rs.getInt(colindex);
                Date date = new Date();
                date.setTime(b);
                f1.set(obj, date);
                break;
            default:
                throw new NoSuchFieldError();
        }
    }

    /**
     * Transforma un registro de datos en un objeto mapeado
     * <p>
     * [EN]  Transforms a data record into a mapped object
     *
     * @param rs  Cursor con datos [EN]  Cursor with data
     * @param cls Clase mapeada a generar [EN]  Mapped class to generate
     * @param <T> Definición de clase genérica [EN]  Generic class definition
     * @return Objeto de entrada genérico con los valores del registro [EN]  Entry object with registry values
     * @throws IllegalAccessException no se puede acceder al método [EN]  can not access method
     * @throws InstantiationException no tiene constructores instaciables [EN]  does not have installable builders
     */
    public static <T> T getGenericByReflection(Cursor rs, Class<T> cls)
            throws IllegalAccessException, InstantiationException {

        Field[] f = cls.getDeclaredFields();
        Annotation a;
        T o;

        o = cls.newInstance();//Creamos un objeto nuevo
//Seteamos los campos
        for (Field f1 : f) {
            a = f1.getAnnotation(DbPrimaryKey.class);
            //Seteamos la clave primaria
            if (a != null) {
                setColumnByReflection(o, rs, f1, rs.getColumnIndex(((DbPrimaryKey) a).id_name()));
            }
            //Repetimos el proceso con las columnas de campos
            a = f1.getAnnotation(DbColumn.class);
            if (a != null) {
                setColumnByReflection(o, rs, f1, rs.getColumnIndex(((DbColumn) a).col_name()));
            }

            //Repetimos el proceso con las columnas de incluidas de modificación y creación
            a = f1.getAnnotation(DbColumnInclosed.class);
            if (a != null) {
                setColumnByReflection(o, rs, f1, rs.getColumnIndex(((DbColumnInclosed) a).col_name()));
            }
        }
        return o;
    }

}
