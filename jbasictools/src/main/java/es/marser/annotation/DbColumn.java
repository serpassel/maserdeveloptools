package es.marser.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sergio
 *         Created by Sergio on 01/02/2017.
 *         Anotaciones para mapeo de POJOs  con conexión a bases de datos SQL. Lectura de registros externos predefinidos de intercambio
 *         <p>
 *         Definición de columnas de la tabla
 *         <p>
 *         Annotations for mapping POJOs with connection to SQL databases. [EN]  Reading of predefined external exchange records
 *         <p>
 *         [EN]  Definition of table columns
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@SuppressWarnings("unused")
public @interface DbColumn {
    /**
     * Define el nombre del campo de la tabla. Debe de coincidir con el nombre de la variable del POJO
     * <p>
     * [EN]  Defines the field name of the table.  It must match the name of the POJO variabl
     *
     * @return Nombre del campo <p>[EN]  Field Name
     */
    String col_name() default "";

    /**
     * Indica si el valor del campo es único en el conjunto de regitros
     * <p>
     * [EN]  Indicates if the value of the field is unique in the set of registers
     *
     * @return verdadero si es único. Por defecto falso <p>EN]  true if it is unique.  Default false
     */
    boolean unique() default false;

    /**
     * Indicar para campos no nulos
     * <p>
     * [EN]  Indicate for non-null fields
     *
     * @return verdadero si el valor del campo no puede ser nulo <p>[EN]  true if the field value can not be null
     */
    boolean notnull() default false;

    /**
     * Indicador para valor de campo correspondiente con el índice del registro padre
     * <p>
     * [EN]  Indicator for corresponding field value with the index of the parent record
     *
     * @return verdadero si el campo se corresponde con el índice del registro padre en bases de datos relaccionadas
     * <p>[EN]  true if the field corresponds to the index of the parent record in related databases
     */
    boolean isParentKey() default false;

    /**
     * Indicador de indexación del campo
     * <p>
     * [EN]  Field Indexing Indicator
     *
     * @return verdadero si el campo está indexado
     * <p>[EN]  true if the field is indexed
     */
    boolean isIndex() default false;

    /**
     * Indicador para valores de campos con uso de clave foránea
     * <p>
     * [EN]  Indicator for field values ​​using foreign key
     *
     * @return verdadero si el valor del campo es una clave foránea
     * <p>
     * [EN]  true if the field value is a foreign key
     */
    boolean isForeKey() default false;

    /**
     * Valor por defecto del campo. Sólo para campos de texto
     * <p>
     * Default field value.  Only for text fields
     *
     * @return Valor por defecto del campo <p> [EN]  Default field value
     */
    String defaultValue() default "";

    /**
     * Valor de la versión del campo para actualizaciones de la base de datos
     * <p>
     * el valor por defecto es 1, si el campo se hubiere agregado en versiones posteriores,
     * <p>
     * es obligatoria su cumplimentación o no se agregará la columna a la base de datos
     * <p>
     * [EN]
     * Value of the version field for database updates
     * <p>
     * the default value is 1, if the field has been added in later versions,
     * <p>
     * it is mandatory to fill in or the column will not be added to the database
     *
     * @return versión de la base de datos en la que ha sido agregado
     * <p>[EN]  version of the database in which it has been added
     */
    int version() default 1;

    /*Atributos para lectura de registros con marcador. Sólo son necesarios si existe una estructura de registros predefinida para compartir información
    <p>
    [EN]  Attributes for reading records with marker [EN]  They are only required if there is a predefined record structure to share information
    <p>
      <code>field|field|field</code>
    */

    /**
     * Posición del campo en la cadena de texto o registro
     * <p>
     * [EN]  Position of the field in the text string or record
     *
     * @return posición donde se encuentra el campo en los registros predefinidos
     * [EN]  position where the field is located in the predefined registers
     */
    int indexorder() default -1;

    /**
     * Indicador para lectura de campo en registro de intercambio predefinido. Valor por defecto verdadero
     * <p>
     * [EN]  Indicator for field reading in predefined exchange register. [EN]  Default value true
     *
     * @return verdadero  si el campo aparece en el registro de intercambio
     * <p>
     * [EN]  true if the field appears in the swap log
     */
    boolean readabled() default true;//Se utiliza para filtrar el campos como leible en la importación de datos

}
