package es.marser.sqltools.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sergio
 *         Created by Sergio on 01/02/2017.
 *         Anotaciones para mapeo de POJOs  con conexión a bases de datos SQL.
 *         <p>
 *         Clave Primaria
 *         <p>
 *         [EN]
 *         Annotations for mapping POJOs with connection to SQL databases.
 *         <p>
 *         [EN]  Primary Key
 */
@SuppressWarnings("unused")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)//Indica que la anotación es para un campo
public @interface DbPrimaryKey {
    /**
     * Campo de clave primaria
     * <p>
     * [EN]  Primary key field
     *
     * @return nombre del campo que contiene la clave primaria
     * <p>[EN]  name of the field containing the primary key
     */
    String id_name() default "key";

    /**
     * Indicador de campo autoincremental
     * <p>
     * [EN]  Autoincremental field indicator
     *
     * @return verdadero si el campo debe de ser autoincremental <p>[EN]  true if the field must be autoincremental
     */
    boolean autoincrement() default false;
}
