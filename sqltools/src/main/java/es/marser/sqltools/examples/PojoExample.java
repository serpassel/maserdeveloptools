package es.marser.sqltools.examples;

import es.marser.annotation.DbColumn;
import es.marser.annotation.DbColumnInclosed;
import es.marser.annotation.DbPrimaryKey;
import es.marser.annotation.DbTable;
import es.marser.tools.DateTools;

/**
 * @author sergio
 *         Created by sergio on 28/09/17.
 *         POJO Mapeado de ejemplo
 *         <p>
 *         [EN]  POJO Sample Mapping
 *
 */

@DbTable(name = "example")
@SuppressWarnings({"unused", "WeakerAccess", "UnusedReturnValue", "SameParameterValue"})
public class PojoExample {
    @DbPrimaryKey
    private String key;
    @DbColumn(col_name = "name")
    private String name;
    @DbColumn(col_name = "age")
    private String age;

    @DbColumnInclosed(col_name = "creacion")
    private String creacion;

    @DbColumnInclosed(col_name = "modificacion")
    private String modificacion;

    public PojoExample() {
        this(DateTools.getTimeStamp(), "PRUEBA", "10");
    }

    public PojoExample(String key, String name, String age) {
        this.key = key;
        this.name = name;
        this.age = age;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public PojoExample setName(String name) {
        this.name = name;
        return this;
    }

    public String getAge() {
        return age;
    }

    public PojoExample setAge(String age) {
        this.age = age;
        return this;
    }

    public String getCreacion() {
        return creacion;
    }

    public PojoExample setCreacion(String creacion) {
        this.creacion = creacion;
        return this;
    }

    public String getModificacion() {
        return modificacion;
    }

    public PojoExample setModificacion(String modificacion) {
        this.modificacion = modificacion;
        return this;
    }

    @Override
    public String toString() {
        return "example|" +
                key + "|" +
                name + '|' +
               age + '|' ;
    }
}
