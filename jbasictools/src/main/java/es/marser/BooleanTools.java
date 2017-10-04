package es.marser;

/**
 * @author sergio
 *         Created by Sergio on 04/04/2016.
 *         Herramientas básicas para el manejo de booleanas
 *         <p>
 *         [EN]  Basic tools for handling boolean
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class BooleanTools {

    /**
     * Entero a booleana
     * \n[EN]  integer to boolean
     *
     * @param input entero [EN]  integer
     * @return false negativo true cero o positivo [EN]  false negative true zero or positive
     */
    public static boolean parseInt(int input) {
        return input >= 0;
    }

    /**
     * Cadena de texto a booleana
     * [EN]  Text string to Boolean
     *
     * @param input booleana o entero en texto [EN]  boolean or integer in text
     * @return false or true
     */
    public static boolean parseString(String input) {
        if (TextTools.isNumeric(input)) {
            return parseInt(Integer.parseInt(input));
        }
        return input != null &&
                (input.equals("true") || input.equals("false")) &&
                Boolean.parseBoolean(input);
    }

    /**
     * Transforma valor booleano a entero
     * [EN]  Transforms Boolean value to integer
     *
     * @param input valor booleano \n[EN]  Boolean value
     * @return true = 0 -- false = -1
     */
    public static int booleanToInt(boolean input) {
        if (input) {
            return 0;//Verdadero
        } else {
            return -1;//Falso
        }
    }

    /**
     * Invierte el valor de un entero booleano
     * [EN]  Invert the value of a boolean integer
     *
     * @param in valor entero booleano [EN]  Boolean integer value
     * @return valor invertido [EN]  invested value
     */
    public static int inverseInBoolean(int in) {
        return (in + 1) * -1;
    }

    /**
     * Evita valores nulos
     * [EN]  Prevents null values
     *
     * @param input    Valor de análisis [EN]  Analysis value
     * @param defaultR Valor por defecto [EN]  Default value
     * @return valor por defecto si es nulo, o entrada en caso contrario [EN]  default value if null, or otherwise entered
     */
    public static boolean nc(Boolean input, boolean defaultR) {
        return input != null ? input : defaultR;
    }

    /**
     * Evita valores nulos
     * [EN]  Prevents null values
     *
     * @param input Valor de análisis [EN]  Analysis value
     * @return false por defecto en caso de entrada nula [EN]  false by default in case of invalid entry
     */
    public static boolean nc(Boolean input) {
        return nc(input, false);
    }
}