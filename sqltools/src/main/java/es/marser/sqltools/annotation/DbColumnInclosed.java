package es.marser.sqltools.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sergio
 *         Created by Sergio on 01/02/2017.
 *         Anotaciones para mapeo de POJOs  con conexi&oacute;n a bases de datos SQL.
 *         <p>
 *         Registros incluidos por defecto
 *         <p>creacion</p>
 *         <p>modificacion</p>
 *         <p>
 *         Annotations for mapping POJOs with connection to SQL databases. [EN]  Reading of predefined external exchange records
 *         [EN]  Definition of table columns
 * @see es.marser.sqltools.SQLStrings.inclosed#CREATION
 * @see es.marser.sqltools.SQLStrings.inclosed#MODIFICATION
 */
@SuppressWarnings("unused")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DbColumnInclosed {
    /**
     * Define el nombre del campo de la tabla. Debe de coincidir con el nombre de la variable del POJO
     *
     * @return Nombre del campo <p>[EN]  Field Name
     */
    String col_name() default "";
}
