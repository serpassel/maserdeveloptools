package es.marser.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Sergio Pascual
 *         Created by Sergio on 01/02/2017.
 *          Anotaciones para mapeo de POJOs  con conexión a bases de datos SQL
 *         <p>
 *         Esctructura de la tabla en la que se albergan los registros de los que depende el POJO
 *         <p>
 *         Annotations for mapping POJOs with connection to SQL databases
 *         <p>
 *         [EN]  Structure of the table in which the registers on which the POJO depends
 *
 */
@SuppressWarnings("unused")
@Retention(RetentionPolicy.RUNTIME)//La anotación se usa en tiempo de ejecución

public @interface DbTable {
    /**
     * Nombre de la tabla. Cumplimentación obligatoria
     * <p>
     * [EN]  Name of the table.  Mandatory filling
     * @return nombre de la tabla <p>[EN]  table name
     */
    String name() default "tbTable";
}
