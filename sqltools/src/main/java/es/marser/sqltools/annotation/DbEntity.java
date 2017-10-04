package es.marser.sqltools.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author sergio
 *         Created by Sergio on 01/02/2017.
 *         Anotaciones para mapeo de POJOs  con conexión a bases de datos SQL
 *         <p>
 *         Anotación de entidad. Indica el nombre de la entidad de conexión. Actualmente en desuso
 *         <p>
 *         Annotations for mapping POJOs with connection to SQL databases
 *         <p>
 *         [EN]  Annotation of entity.  Indicates the name of the connection entity. Deprecated
 *         @deprecated
 *
 */
@Retention(RetentionPolicy.RUNTIME)//La anotación se usa en tiempo de ejecución

@SuppressWarnings("unused")
@Deprecated
public @interface DbEntity {
    /**
     * Nombre de la base de datos a la que pertence el objeto
     * <p>
     * [EN]  Name of the database to which the object belongs
     *
     * @return nombre de la base de datos <p> [EN]  database name
     */
    String name() default "DBData";
}
