package es.marser.generic;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import es.marser.annotation.DbColumn;
import es.marser.async.DataUploaderTask;
import es.marser.tools.BooleanTools;
import es.marser.tools.MathTools;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by Sergio on 02/09/2017.
 *         Creación de objetos genéricos, a partir de una cadena de texto separados por un marcador
 *         Creación de sub-registros, a partir de una cadena de texto separados por un marcador
 *         El registro debe de llevar un campo adicional a su comeinezo que lo identifique, y que no se considerará en su creación
 *         <p>
 *         [EN]
 *         Creating generic objects, from a string of text separated by a marker
 *         Creating Sub-Records, from a Text String Separated by a Placemark
 *         The record must carry an additional field to its identifying origin, and that it will not be considered in its creation
 */

@SuppressWarnings("unused")
public class GenericFactory {

    /**
     * Crear instacia de un objeto genérico por reflexión
     * <p>
     * [EN]  Create Instance of a Generic Object by Reflection
     *
     * @param cls clase genérica [EN]  generic class
     * @param <T> objeto genérico [EN]  generic object
     * @return Objeto instanciado, nulo en caso de error [EN]  Object instantiated, null in case of error
     */
    public static <T> T getInstance(Class<T> cls) {
        T out = null;
        try {
            out = cls.newInstance();
        } catch (Exception ignored) {
        }
        return out;
    }

    /**
     * Crea un objeto individual sobre un registro de texto cuyos campos están separados por un marcador
     * <p>
     * [EN]  Creates an individual object on a text record whose fields are separated by a marker
     *
     * @param cls      Clase genérica [EN]  Generic class
     * @param registro Valor del registro en cadena de texto [EN]  Value of the record in text string
     * @param market   Separador de campos del registro [EN]  Log Field Separator
     * @param <T>      Objeto genérico [EN]  Generic object
     * @return Objeto instanciado y seteado o nulo en caso de error [EN]  Object instantiated and set or null in case of error
     * @see #getEntryClassObjet(Field)
     */
    public static <T> T BuildSingleObject(Class<T> cls, String registro, String market) {

        registro = TextTools.nc(registro).trim();

        /*Arreglo de campos del registro de texto [EN]  Arrangement of text record fields*/
        String[] tokenizer = TextTools.getRecordSplit(registro, market);

        if (tokenizer.length == 0) {
            return null;
        }

        /*Obtener instancia vacía del objeto [EN]  Get Empty Object Instance*/
        T obj = getInstance(cls);

        /*Creación de variables de reflexión [EN]  Creating reflection variables*/
        String varname, methodname;
        DbColumn a;
        Method method = null;
        int i; //Contador de posición de campos [EN]  Field Position Counter

        for (Field field : cls.getDeclaredFields()) {
            /*Excluir anotaciones que no sean columna [EN]  Exclude annotations other than column*/

            a = field.getAnnotation(DbColumn.class);

            /*Comprobar que la columna ha sido marcada como columna de lectura ,(valor por defector verdadero)
            * [EN]  Check that the column has been marked as reading column, (true defector value)*/
            if (a != null && a.readabled()) {

                /*Recuperar el nombre de la variable y creación del método set
                * [EN]  Recover the name of the variable and create the set method*/
                varname = a.col_name();//Nombre de la variable [EN]  Name of the variable
                methodname = "set" + varname.substring(0, 1).toUpperCase() + varname.substring(1);//Método set [EN]  Method set

                try {
                    /*Recuperar el método de la clase [EN]  Retrieve the method of the class
                    * Nombre de la valriables methodname y tipo de clase de entrada field.getType()
                    * [EN]  Name of the validable methodname and type of input class field.getType ()
                    *
                    * ej: setName(String in) =
                    * cls.getMethod("setName", String.class)*/
                    method = cls.getMethod(methodname, getEntryClassObjet(field));

                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }

                /*Lectura de la posición en el registro de la variable en curso
                * Se debe de indicar en el clase del objeto
                * [EN]  Reading the position in the record of the current variable
                * [EN]  It must be indicated in the class of the objectt*/
                i = a.indexorder();

                if (tokenizer.length > i) {
                    /*Lectura de valor en el registro de entrada
                    * [EN]  Valued reading in the check-in */
                    String text = TextTools.nc(tokenizer[i]).trim();

                    try {
                        /*Ejecutar el método con el valor recuperado*/
                        if (method != null) {
                            switch (field.getType().getSimpleName()) {
                                case "Integer":
                                case "int":
                                    method.invoke(obj, MathTools.parseInt(text));
                                    break;
                                case "double":
                                case "Double":
                                    method.invoke(obj, MathTools.parseBigDecimal(text).doubleValue());
                                    break;
                                case "BigDecimal":
                                    method.invoke(obj, MathTools.parseBigDecimal(text));
                                    break;
                                case "Long":
                                case "long":
                                    method.invoke(obj, MathTools.parseBigDecimal(text).longValue());
                                    break;
                                case "Float":
                                case "float":
                                    method.invoke(obj, MathTools.parseBigDecimal(text).floatValue());
                                    break;
                                case "Boolean":
                                    method.invoke(obj, BooleanTools.parseStringNulable(text));
                                    break;
                                case "boolean":
                                    method.invoke(obj, BooleanTools.parseString(text));
                                    break;
                                case "String":
                                default:
                                    method.invoke(obj, text);
                                    break;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else
                    try {//Valores por defecto en caso de valores nulos o incompletos [EN]  Default values ​​in case of null or incomplete values
                        if (method != null) {
                            switch (field.getType().getSimpleName()) {
                                case "Integer":
                                case "int":
                                    method.invoke(obj, 0);
                                    break;
                                case "double":
                                case "Double":
                                    method.invoke(obj, 0.0);
                                    break;
                                case "BigDecimal":
                                    method.invoke(obj, new BigDecimal("0.0"));
                                    break;
                                case "Long":
                                case "long":
                                    method.invoke(obj, 0);
                                    break;
                                case "Float":
                                case "float":
                                    method.invoke(obj, 0.0f);
                                    break;
                                case "Boolean":
                                case "boolean":
                                    method.invoke(obj, false);
                                    break;
                                case "String":
                                default:
                                    method.invoke(obj, "");
                                    break;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
        return obj;
    }

    /**
     * Crear un objeto genérico separado por el separador de campos por defecto
     * <p>
     * [EN]  Create a generic object separated by the default field separator
     *
     * @param cls      Clase genérica [EN]  Generic class
     * @param registro Valor del registro en cadena de texto [EN]  Value of the record in text string
     * @param <T>      Objeto genérico [EN]  Generic object
     * @return Objeto instanciado y seteado o nulo en caso de error [EN]  Object instantiated and set or null in case of error
     */
    public static <T> T BuildSingleObject(Class<T> cls, String registro) {
        return BuildSingleObject(cls, registro, TextTools.OBJECT_SEPARATOR_SPLIT);
    }

    /**
     * Crear un objeto genérico separado por el separador de sub-campos por defecto
     * <p>
     * [EN]  Create a generic object separated by the default sub-field separator
     *
     * @param cls      Clase genérica [EN]  Generic class
     * @param registro Valor del registro en cadena de texto [EN]  Value of the record in text string
     * @param <T>      Objeto genérico [EN]  Generic object
     * @return Objeto instanciado y seteado o nulo en caso de error [EN]  Object instantiated and set or null in case of error
     */
    public static <T> T BuildSingleItem(Class<T> cls, String registro) {
        return BuildSingleItem(cls, registro, TextTools.ITEM_SEPARATOR_SPLIT);
    }

    /**
     * Crear un objeto genérico separado por el separador de sub-campos por defecto
     * <p>
     * [EN]  Create a generic object separated by the default sub-field separator
     *
     * @param cls      Clase genérica [EN]  Generic class
     * @param registro Valor del registro en cadena de texto [EN]  Value of the record in text string
     * @param market Marcador separador de registros [EN]  Bookmark record separator
     * @param <T>      Objeto genérico [EN]  Generic object
     * @return Objeto instanciado y seteado o nulo en caso de error [EN]  Object instantiated and set or null in case of error
     */
    public static <T> T BuildSingleItem(Class<T> cls, String registro, String market) {
        registro = "D" + market.charAt(0) + TextTools.nc(registro);
        return BuildSingleObject(cls, registro, market);
    }

    /**
     * Lista de registros de un subcampo
     * <p>
     * [EN]  List of records of a subfield
     *
     * @param cls      Clase genérica [EN]  Generic class
     * @param registro Cadena de texto con el resgitro de subcampo [EN]  Text string with subfield
     * @param iter     Longitud del registro individual dentro del subcampo [EN]  Length of the individual record within the subfield
     * @param market   Separador de sub-registros [EN]  Sub-record separator
     * @param onResult Oyente de resultados [EN]  Listener results
     * @param <T>      Objeto genérico [EN]  Generic object
     * @return Listado de registros del subcampo instanciados y seteados [EN]  List of subfield records instantiated and set
     */
    public static <T> List<T> itemsBuilder(Class<T> cls, String registro, int iter, String market, DataUploaderTask<Integer, T, List<T>> onResult) {
        List<T> result = new ArrayList<>();

        for (Annotation a : cls.getAnnotations())

            if (TextTools.isEmpty(registro) || TextTools.isEmpty(market)) {
            /*Publicar finalización de lectura
        [EN]  Publish end of reading*/
                if (onResult != null) {
                    onResult.onFinish(result);
                }
                return result;
            }

        String[] data = TextTools.getRecordSplit(registro, market);

        /*Publicar el número de registros
        [EN]  Publish the number of records*/
        if (onResult != null) {
            onResult.onStart(data.length);
        }
        char c = market.charAt(0);
        T o;
        StringBuilder work = new StringBuilder();//Variable de acumulación de datos, para registro individual [EN]  Variable of accumulation of data, for individual registration

        /*Calcular el número de campos. Separar los posibles registros
        [EN]  Calculate the number of fields.  Separate possible registrations*/
        for (int i = 0; i < data.length; i++) {

            work.append(data[i]).append(c);

            /*Comprobar si la iteranción es múltiplo de la longitud total del sub-registro individual
            * [EN]  Check if the iteration is multiple of the total length of the individual sub-record*/
            if (i != 0 && MathTools.isMultiple(i + 1, iter)) {

                /*Si la iteración es multiplo de la longitud, se ha completado de la lectura de un regsitro. Generar objeto
                * [EN]  If the iteration is multiple of the length, it has been completed from the reading of a regsitro.  Generate object*/
                o = BuildSingleItem(cls, work.toString(), market);
                if (o != null) {
                    result.add(o);
                }
                /*Publicar el registro generado [EN]  Publish the generated log*/
                if (onResult != null) {
                    onResult.onUpdate(o);
                }

                /*Borrar variable de acumulación de datos
                [EN]  Clear data accumulation variable*/
                work = new StringBuilder();
            }
        }
        /*Publicar finalización de lectura
        [EN]  Publish end of reading*/
        if (onResult != null) {
            onResult.onFinish(result);
        }
        return result;
    }

    /**
     * Lista de registros de un subcampo
     * <p>
     * [EN]  List of records of a subfield
     *
     * @param cls      Clase genérica [EN]  Generic class
     * @param registro Cadena de texto con el resgitro de subcampo [EN]  Text string with subfield
     * @param iter     Longitud del registro individual dentro del subcampo [EN]  Length of the individual record within the subfield
     * @param onResult Oyente de resultados [EN]  Listener results
     * @param <T>      Objeto genérico [EN]  Generic object
     * @return Listado de registros del subcampo instanciados y seteados [EN]  List of subfield records instantiated and set
     */
    public static <T> List<T> itemsBuilder(Class<T> cls, String registro, int iter, DataUploaderTask<Integer, T, List<T>> onResult) {
        return itemsBuilder(cls, registro, iter, TextTools.ITEM_SEPARATOR_SPLIT, onResult);
    }

    /**
     * Lista de registros de un subcampo
     * <p>
     * [EN]  List of records of a subfield
     *
     * @param cls      Clase genérica [EN]  Generic class
     * @param registro Cadena de texto con el resgitro de subcampo [EN]  Text string with subfield
     * @param onResult Oyente de resultados [EN]  Listener results
     * @param <T>      Objeto genérico [EN]  Generic object
     * @return Listado de registros del subcampo instanciados y seteados [EN]  List of subfield records instantiated and set
     */
    public static <T> List<T> itemsBuilder(Class<T> cls, String registro, DataUploaderTask<Integer, T, List<T>> onResult) {
        return itemsBuilder(cls, registro, getReadableColumnsCount(cls), TextTools.ITEM_SEPARATOR_SPLIT, onResult);
    }

    /**
     * Crear la clase de entrada de datos para un elemento dado
     * <p>
     * <p>
     * [EN]  Create the data entry class for a given element
     */
    private static Class getEntryClassObjet(Field f1) {

        switch (f1.getType().getSimpleName()) {
            case "String":
                return String.class;
            case "Integer":
                return Integer.class;
            case "int":
                return int.class;
            case "Boolean":
                return Boolean.class;
            case "boolean":
                return boolean.class;
            case "Double":
                return Double.class;
            case "double":
                return double.class;
            case "BigDecimal":
                return BigDecimal.class;
            case "Long":
                return Long.class;
            case "long":
                return long.class;
            case "float":
                return float.class;
            default:
                return String.class;
        }
    }

    /**
     * Contador de anotaciones de columna leíbles
     * <p>
     * [EN]  Readable column annotation counter
     *
     * @param cls Clase mapeada [EN]  Mapped class
     * @return Número de columnas leibles de una clase
     */
    public static int getReadableColumnsCount(Class cls) {
        int count = 0;

        for (Field f : cls.getDeclaredFields()) {
            DbColumn a = f.getAnnotation(DbColumn.class);
            if (a != null && a.readabled()) {
                ++count;
            }
        }
        return count;
    }
}
