package es.marser.sqltools;

import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 16/10/17.
 *         Configurador de base de datos
 *         <ul>
 *             <il>name: Nombre de la base de datos</il>
 *             <il>version: versión de la base de datos</il>
 *             <il>tables: clases mapeadas con la definción de las tablas</il>
 *         </ul>
 *         <p>
 *         [EN]  Database Configurator
 *         <ul>
 *             <il>name: Database name</il>
 *             <il>version: version of the database</il>
 *             <il>tables: classes mapped to the definition of tables</il>
 *         </ul>
 */

public class DatabaseSettings {

    private static String default_databasename = "database";

    private String name;
    private int version;
    private Class[] tables;

    public DatabaseSettings() {
        this(default_databasename, 1, new Class[]{});
    }

    public DatabaseSettings(Class[] tables) {
        this(default_databasename, 1, tables);
    }

    public DatabaseSettings(String name, int version, Class[] tables) {
        this.name = TextTools.nc(name, default_databasename);
        this.version = version;
        this.tables = tables;
    }

    public String getName() {
        return name;
    }

    public DatabaseSettings setName(String name) {
        this.name = name;
        return this;
    }

    public int getVersion() {
        return version;
    }

    public DatabaseSettings setVersion(int version) {
        this.version = version;
        return this;
    }

    public Class[] getTables() {
        return tables;
    }

    public DatabaseSettings setTables(Class[] tables) {
        this.tables = tables;
        return this;
    }
}
