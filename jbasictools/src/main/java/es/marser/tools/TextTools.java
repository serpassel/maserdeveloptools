package es.marser.tools;

import java.util.regex.Pattern;

/**
 * @author sergio
 *         Created by Sergio on 31/03/2016.
 *         [ES]
 *         Herramientas básicas para el manejo de string
 *         <p>
 *         1.- Identificación de cadenas. Analisis de patrones numéricos
 *         2.- Corrección de nulos. Evita las cadenas nulas o mal formadas
 *         3.- Uso de caracteres. Métodos para el manejo de los caracteres del string
 *         4.- Validadores de patrones. Email y password
 *         5.- Conversión de numeros. Para vinculación de vistas
 *         6.- Caracteres de escape de sqlite
 *         7.- StringBuilder. Borrado
 *         8.- Transformación vectorial
 *         <p>
 *         [EN]
 *         Basic tools for handling strings
 *         <p>
 *         1.- Identification of strings. Analysis of numerical patterns
 *         2.- Correction of nulls. Prevents null or poorly formed strings
 *         3.- Use of characters. Methods for handling string characters
 *         4.- Pattern validators. Email and password
 *         5.- Conversion of numbers. For linking views
 *         6.- Exit characters from sqlite
 *         7.- StringBuilder. deleted
 *         8.- Vector transformation
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class TextTools {

    public static String[] alfabetic = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public static final String PATTERN_EMAIL = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String DEFAULT_VALUE = "[PLACEHOLDER]";//" "
    public static final String OBJECT_SEPARATOR_SPLIT = "\\|";
    public static final String OBJECT_SEPARATOR = "|";
    public static final String ITEM_SEPARATOR = "\\";// + "\\";
    public static final String ITEM_SEPARATOR_SPLIT = "\\" + "\\";
    public static final String REG_SEPARATOR = "~";
    public static final String GROUP_SEPARATOR = Character.toString((char) 170);
    public static final String CLIPBOARD_SEPARATOR = Character.toString((char) 179);
    public static final String COMILLA_SQL_INSERT = "''";
    public static final String SALTO_LINEA = "\n";
    public static final String RETORNO_CARRO_SALTO_LINEA = "\r\n"; //
    public static final String TAB = "\t"; //
    public static final String DEFAULT_MARQUEE = "..."; //

    public static final char ITEM_SEPARATOR_CHAR = 92; //'\'
    public static final char GROUP_SEPARATOR_CHAR = 170;
    public static final char CLIPBOARD_SEPARATOR_CHAR = 179;
    public static final char REG_SEPARATOR_CHAR = 126; //'~'
    public static final char OBJECT_SEPARATOR_CHAR = 124; //'|'
    public static final char PERCENTAJE = 37; //'%'
    public static final char ALMOHADILLA = 35; //'#'
    public static final char COMILLA = 44; //''
    public static final char SPACE = 32; //" "

//1.- INDENTIFICACION DE CADENAS____________________________________________________________________________
//1.- INDENTIFICATION OF STRINGS______________________________________________________________________________

    /**
     * @param str Cadena a valorar  [EN] Chain to value
     * @return true si es numérica  [EN] true if it's numeric
     * @deprecated use {@link TextTools#isNumeric(String)} ()} instead.
     */
    @Deprecated
    public static boolean isANumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    /**
     * Se define como utilidad para comprobar números de colores
     * It is defined as a utility to check color numbers
     *
     * @param text Cadena a valorar [EN] Chain to value
     * @return true si tiene forma hexadecimal [EN] true if it has hexadecimal form
     */
    public static boolean isHexadecimal(String text) {
        text = nc(text).replace("#", "").trim();

        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B', 'C', 'D', 'E', 'F'};

        int hexDigitsCount = 0;

        for (char symbol : text.toCharArray()) {
            for (char hexDigit : hexDigits) {
                if (symbol == hexDigit || symbol == '.') {
                    hexDigitsCount++;
                    break;
                }
            }
        }
        return hexDigitsCount == text.length();
    }

    /**
     * @param str Cadena a valorar [EN] Chain to value
     * @return true si la cadena de texto es numérica [EN] true if the text string is numeric
     */
    public static boolean isNumeric(String str) {
        int point = 0;
        if (str == null || str.isEmpty()) {
            return false;
        }
        int i = 0;
        str = str.trim();
        //Comprobamos si es negativo
        if (str.charAt(i) == '-') {
            if (str.length() > 1) {
                i++;
            } else {
                return false;
            }
        }
        for (; i < str.length(); i++) {
            if (str.charAt(i) == '.') {
                ++point;
                if (point > 1) {
                    return false;
                }

                /*
                if (str.length() > 1) {
                    i++;
                }*/

            } else if (!Character.isDigit(str.charAt(i))) {
                //Si el número es muy largo puede entrar como notación cientifica
                if (!(str.charAt(i) == 'E' || str.charAt(i) == '+' || str.charAt(i) == '-')) {
                    return false;
                }
            }
        }
        return true;
    }

    //2.-CORRECCIÓN DE NULOS_________________________________________________________________________________
//2.- Correction of nulls_________________________________________________________________________________

    /**
     * @param in Cadena de entrada [EN] Input Chain
     * @return "" si el objeto es nulo [EN] if the object is null
     */
    public static String nc(String in) {
        return in != null ? in : "";
    }

    /**
     * @param in           Cadena de entrada [EN] Input Chain
     * @param defaultValue Valor alternativo en caso de nulo [EN] Alternative value in case of null
     * @return defaultValue si in es nulo, o "" si ambas entradas son nulas [EN] if in is null, or if both entries are null
     */
    public static String nc(String in, String defaultValue) {
        return in != null ? in : nc(defaultValue);
    }

    /**
     * Ejecuta toString de la clase Obj o devuelve ""
     * [EN] Executes toString of class Obj or returns ""
     *
     * @param in Objecto a transformar en string [EN] Object to transform into string
     * @return "" si el objeto es nulo o in.toString() [EN] if the object is null or in.toString ()
     */
    public static String nc(Object in) {
        return in != null ? in.toString() : "";
    }

    /**
     * @param text Cadena para comprobar [EN] Chain to check
     * @return true si la cadena es nula o está vacía [EN] true if the string is null or empty
     */
    public static boolean isEmpty(String text) {
        return text == null || text.trim().equals("") || text.trim().equals("null") || text.trim().equals("NULL");
    }

    /**
     * @param in String numérica [EN] Numeric string
     * @return si tiene algún error devuelve 0.0 [EN] if you have any error returns 0.0
     */
    public static String ncNumeric(String in) {
        return isNumeric(in) ? in : "0.0";
    }

    /**
     * @param in           Cadena numérica de entrada [EN] Number input string
     * @param defaultValue Valor numérico alternativo [EN] Alternative numeric value
     * @return 0.0 si ambas cadenas son nulas [EN] 0.0 if both strings are null
     */
    public static String ncNumeric(String in, String defaultValue) {
        return isNumeric(in) ? in : ncNumeric(defaultValue);
    }

    /**
     * @param in    cadena de comprobación [EN] check chain
     * @param alter resultado alternativo [EN] alternative result
     * @return Devuelve la cadena de entrada o el valor alternativo si esta está vacía, o "", si ambos están vacío o son nulos
     * [EN] Returns the input string or the alternative value if it is empty, or, if both are empty or are null
     */
    public static String notEmpty(String in, String alter) {
        return isEmpty(in) ? nc(alter) : in;
    }

    //3.- USO DE CARACTERES______________________________________________________________________________________
    //3.- USE OF CHARACTERS

    /**
     * @param in Entrada cadena [EN] Entry string
     * @return La primera letra en mayúsculas [EN] The first letter in capital letters
     */
    public static String capitalizeFirstChar(String in) {
        if (isEmpty(in)) {
            return "";
        }
        return in.substring(0, 1).toUpperCase() + in.substring(1);
    }

    /**
     * @param text  Cadena de entrada [EN] Input Chain
     * @param limit limite de caracteres [EN] character limit
     * @return el texto limitado [EN] limited text
     */
    public static String limitText(String text, int limit) {
        return limit >= 0 ? nc(text).substring(0, Math.min(limit, nc(text).length())) : nc(text);
    }

    /**
     * @param text  Cadena de entrada [EN] Input Chain
     * @param limit limite de texto [EN] text limit
     * @return Cadena de caracteres desde el final hasta el límite [EN] String from end to end
     */
    public static String lastText(String text, int limit) {
        return limit >= 0 ? nc(nc(text).substring(Math.max(0, nc(text).length() - limit), nc(text).length())) : nc(text);
    }

    /**
     * Considerar la longitud adicional del marcador
     * [EN] Consider additional marker length
     *
     * @param text  cadena de entrada [EN] input string
     * @param lengh longitud máxima [EN] maximum length
     * @param marq marca de sustitución de caracteres [EN]  character replacement mark
     * @return cadena reducida con el texto de marca si se hubiere reducido [EN] reduced string with brand text if reduced
     */
    public static String limitMarqueeText(String text, int lengh, String marq) {
        marq = nc(marq, ".");
        //Comprobaos que la longitud indicada no es negativa si es así ponemos 0
        if (lengh < 0) {
            lengh = 0;
        }
        //Si la cadena es mayor la trasnformamos
        if (nc(text).length() > lengh) {
            text = limitText(nc(text), lengh);
            text += marq;
        }
        return text;
    }

    /**
     * Limita el texto añadiendo un marca
     * [EN] Limit text by adding a tag
     *
     * @param text  cadena de entrada [EN] input string
     * @param lengh longitud del texto [EN] text length
     * @return texto recortado con el marcador por defecto ... [EN] text trimmed with the default marker ...
     */
    public static String limitMarqueeText(String text, int lengh) {
        return marqueeText(text, lengh, null);
    }

    /**
     * Completa una cadena de texto con un marcador
     * [EN]  Complete a text string with a marker
     *
     * @param text    cadena de texto a completar [EN]  string to complete
     * @param lengh   longitud total de la cadena [EN]  total chain length
     * @param charter caracter de autocompletado [EN]  character of autocompletado
     * @return cadena texto completada [EN]  string text completed
     */
    public static String marqueeText(String text, int lengh, String charter) {
        text = limitText(nc(text), lengh - 3);
        StringBuilder builder = new StringBuilder();
        builder.append(text);
        while (builder.length() < lengh) {
            builder.append(charter);
        }
        // Log.i(MainCRUD.TAG, "Longitud " + builder.length());
        return builder.toString();
    }

    /**
     * @param in Cadena de entrada [EN] Input Chain
     * @return Primer caracter si lo hubiere [EN] First character if there is
     */
    public static String firstChar(String in) {
        return (in != null && in.length() > 0) ? in.substring(0, 1) : "";
    }

    /**
     * @param in       Cadena de entrada [EN] Input Chain
     * @param defaultt Caracter por defecto en caso de nulo [EN] Default character in case of null
     * @return El primer caracter de la cadena o el valor por defecto en caso de nulo [EN] The first character of the string or the default value in case of null
     */
    public static String firstChar(String in, char defaultt) {
        return (in != null && in.length() > 0) ? in.substring(0, 1) : String.valueOf(defaultt);
    }

    /**
     * Utilidad para manejo de codigos
     * [EN] Utility for handling codes
     *
     * @param in Cadena de entrada [EN] Input Chain
     * @return El segundo caracter de la cadena o vacío si no hay [EN] The second character of the string or void if there is no
     */
    public static String secondChar(String in) {
        return (in != null && in.length() > 1) ? in.substring(1, 2) : "";
    }

    //4.- VALIDADORES DE PATRONES_________________________________________________________________________________
// 4.- VALIDATORS OF PATTERNS____________________________________________________________________________________

    /**
     * @param password entrada de la contraseña [EN] password entry
     * @return true si la contraseña es mayor de 6 caracteres [EN] true if the password is greater than 6 characters
     */
    public static boolean validatePassword(String password) {
        return password != null && password.length() >= 6;
    }

    /**
     * @param password     entrada nueva [EN] new entry
     * @param confirmation valor para confirmación [EN] value for confirmation
     * @return true si ambas cadenas coinciden y no son nulas [EN] true if both strings match and are not null
     */
    public static boolean validateAndConfirmPassword(String password, String confirmation) {
        return (password != null && confirmation != null && password.equals(confirmation));
    }

    /**
     * Solo analiza la formación, no asegura su existencia
     * [EN] It only analyzes the formation, does not assure its existence
     *
     * @param email emails
     * @return devuelve true si el patrón representa un email [EN] returns true if the pattern represents an email
     */
    public static boolean validateMail(String email) {
        return email != null && Pattern.compile(PATTERN_EMAIL).matcher(email).matches();
    }

    //5.- CONVERSION DE NUMEROS _________________________________________________________________________________
//5.- NUMBER CONVERSION________________________________________________________________________________________

    /**
     * @param in valor entero de entrada [EN] input integer value
     * @return valor en string o valor vacio [EN] value in string or value empty
     */
    public static String numberToString(Integer in) {
        return in != null ? String.valueOf(in) : "";
    }

    /**
     * @param in Valor double de entrada [EN] Input double value
     * @return valor en string o valor vacio [EN] value in string or value empty
     */
    public static String numberToString(Double in) {
        return in != null ? String.valueOf(in) : "";
    }

    //6.- SOLite Caracteres reservados___ _________________________________________________________________________________
//6.- SOLite Reserved characters________________________________________________________________________________________

    /**
     * Corrector de entrada a SQLite escapa ' la comilla simple
     * [EN] Corrector input to SQLite escapes the single quote
     *
     * @param text Texto de entrada en la base de datos [EN] Input text in the database
     * @return Texto con los caracteres coregidos [EN] Text with chromatic characters
     */

    public static String toSQLString(String text) {
        return text.replace("'", "''");
    }

    /**
     * Corrector de salida SQLite
     * [EN] SQLite Output Corrector
     *
     * @param text texto en la base de datos [EN] text in the database
     * @return texto corregido por los caracteres no váidos [EN] text corrected by non-empty characters
     */
    public static String fromSQLString(String text) {

        return nc(text).replace("''", "'");
    }

    //7.- StringBuilder______________ _________________________________________________________________________________

    /**
     * Borra un stringbuilder
     * [EN] Delete a stringbuilder
     *
     * @param stringBuilder entrada [EN]input
     */
    public static void clearBuilder(StringBuilder stringBuilder) {
        if (stringBuilder != null) {
            stringBuilder.delete(0, stringBuilder.length());
        }
    }
    //8.- Transformación vectorial______________ _________________________________________________________________________________
//8.- Vector transformation_______________________________________________________________________________________________

    /**
     * Proceso de rotación del texto
     * [EN] Text rotation process
     *
     * @param in cadena de entrada [EN] input string
     * @return cadena rotada [EN] cadena rotada
     */
    public static String rotateString(char[] in) {
        if (in == null || in.length < 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = in.length - 1; i > -1; --i) {
            builder.append(in[i]);
        }
        return builder.toString();
    }

    /**
     * Intercala par e impares de una String
     * [EN]Interleaves even and odd strings
     *
     * @param in cadena de entrada [EN] input string
     * @return entremezclados por par e impar [EN] entremezclados por by e impar
     */
    public static String oddCouple(char[] in) {
        if (in == null || in.length < 0) {
            return "";
        }
        StringBuilder odd = new StringBuilder();
        StringBuilder couple = new StringBuilder();
        boolean flag = true;
        for (char c : in) {
            if (flag) {
                odd.append(c);
            } else {
                couple.append(c);
            }
            flag = !flag;
        }
        return odd.append(couple).toString();
    }

    /**
     * Transpone un string array
     * [EN]Transpose a string array
     *
     * @param in String array
     * @return String array transpuesto separado por | [EN] chain arrangement transposed separately by |
     */
    public static String transposetArray(String[] in) {
        if (in == null) {
            return "";
        }
        int len = 1;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            for (String s : in) {
                //Con esto comprobamos la longitud de la cadena comprobada
                if (s.length() >= len) {
                    len = s.length();
                }
                if (s.length() <= i) {
                    builder.append("");
                } else {
                    builder.append(s.toCharArray()[i]);
                }
            }
            builder.append("|");
        }
        return builder.toString();
    }
}