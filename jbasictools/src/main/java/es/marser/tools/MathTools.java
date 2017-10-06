package es.marser.tools;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Stack;

/**
 * @author Sergio
 *         Created by Sergio on 18/03/2016.
 *         [ES] Herramientas básicas para el manejo de numérico
 *         1.- Corrección de nulos
 *         2.- Redondeo
 *         3.- Conversiones
 *         4.- Comparadores
 *         5.- Formateo de numeros
 *         6.- Cálculos
 *         <p>
 *         [EN]  Basic tools for handling numerical
 *         1.- Correction of nulls
 *         2.- Rounding
 *         3.- Conversions
 *         4.- Comparators
 *         5.- Formatting numbers
 *         6.- Calculations
 */

@SuppressWarnings({"unused", "WeakerAccess", "SameParameterValue"})
public abstract class MathTools {

    //1.- CORRECCIÓN DE NULOS_______________________________________________
// 1.- NULL CORRECTION_____________________________________________________

    /**
     * Corrección de nulos en BigDecimal
     * <p>
     * [EN]  Correction of null in BigDecimal
     *
     * @param in Entrada BigDecimal [EN]  BigDecimal input
     * @return 0.0 entrada nula [EN]  0.0 null input
     */
    public static BigDecimal notNaN(BigDecimal in) {
        return in != null ? in : new BigDecimal("0.0");
    }

    /**
     * Corrección de nulos en BigDecimal
     * <p>
     * [EN]  Correction of null in BigDecimal
     *
     * @param in       Entrada BigDecimal [EN]  BigDecimal input
     * @param defaultN Valor por defecto en caso de nulo <p>[EN]  Default value in case of null
     * @return valor por defecto para entrada nula [EN]  [EN]  default value for null input
     */
    public static BigDecimal notNaN(BigDecimal in, BigDecimal defaultN) {
        return in != null ? in : notNaN(defaultN);
    }

    /**
     * Corrección de nulos en Double
     * <p>
     * [EN]  Correction of null in Double
     *
     * @param in Entrada Double [EN]  Double input
     * @return 0.0 entrada nula [EN]  0.0 null input
     * @deprecated use {@link #notNaN(BigDecimal)} instead
     */
    @Deprecated
    public static double notNaN(Double in) {
        return in != null ? in : 0.0;
    }

    /**
     * Corrección de nulos en Double
     * <p>
     * [EN]  Correction of null in Double
     *
     * @param in       Entrada Double [EN]  Double input
     * @param defaultN Valor por defecto en caso de nulo <p>[EN]  Default value in case of null
     * @return valor por defecto para entrada nula [EN]  [EN]  default value for null input
     * @deprecated use {@link #notNaN(BigDecimal)}  instead
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    public static double notNaN(Double in, Double defaultN) {
        return in != null ? in : notNaN(defaultN);
    }

    /**
     * Corrección de nulos en Integer
     * <p>
     * [EN]  Correction of null in Integer
     *
     * @param in Entrada Integer [EN]  Integer input
     * @return 0 entrada nula [EN]  0 null input
     */
    public static int notNaN(Integer in) {
        return in != null ? in : 0;
    }

    /**
     * Corrección de nulos en Integer
     * <p>
     * [EN]  Correction of null in Integer
     *
     * @param in       Entrada Integer [EN]  Integer input
     * @param defaultN Valor por defecto en caso de nulo <p>[EN]  Default value in case of null
     * @return valor por defecto para entrada nula [EN]  [EN]  default value for null input
     */
    public static int notNaN(Integer in, Integer defaultN) {
        return in != null ? in : notNaN(defaultN);
    }

    /**
     * Devuelve true si el valor es 0 o nulo
     *
     * @param value valor numérico
     * @return true para nulo o 0 [EN]  true for null o 0
     * @deprecated use {@link  #isEmpty(BigDecimal)} instead
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    public static boolean isEmpty(Double value) {
        return value == null || areEquals(value, 0.0);
    }

    /**
     * Devuelve true si el valor es 0 o nulo
     *
     * @param value valor numérico
     * @return true para nulo o 0 [EN]  true for null o 0
     */
    public static boolean isEmpty(BigDecimal value) {
        return value == null || areEquals(value, new BigDecimal("0.0"));
    }

    /**
     * Transforma el valor double a string sin nulos
     * [EN]  Transforms the value double to string without nulls
     *
     * @param value entrada Double [EN]  Double input
     * @return Valor en texto o cadena vacía para nulos [EN]  Text value or empty string for nulls
     */
    @Deprecated
    public static String valueToNotNullString(Double value) {
        return value != null ? String.valueOf(value) : "";
    }

    //2.- REDONDEO___________________________________________________________
    // 2.- ROUNDING___________________________________________________________

    /**
     * Redondea un número
     * Round a number
     *
     * @param input   número en texto [EN]  number in text
     * @param digitos dígitos de redondeo [EN]  rounding digits
     * @return número redondeado o cadena vacía en caso de error [EN]  rounded number or empty string in case of error
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static String round(String input, int digitos) {
        /*Si no es numérico devolvemos cadena vacía
        [EN]  If it is not numeric return empty string*/
        if (!TextTools.isNumeric(input)) {
            return "";
        }

        /*Si los dígitos son negativos devolvemos el mismo número
        * [EN]  If the digits are negative we return the same number*/
        if (digitos < 0) {
            return input;
        }
        /*ante cualquier error adicional devuelve cadena vacía
         [EN]  any additional error returns empty string*/
        BigDecimal bigDecimal;
        try {
            bigDecimal = new BigDecimal(input);
        } catch (NumberFormatException ex) {
            return "";
        }
        /*Realizar un pre-redondeo evita el error del 5
         [EN]  Performing a pre-rounding avoids the error of 5*/
        bigDecimal.setScale(digitos + 2, RoundingMode.HALF_UP);
        return bigDecimal.setScale(digitos, RoundingMode.HALF_UP).toString();
    }

    /**
     * Redondea un número
     * <p>
     * [EN]  Round a number
     *
     * @param in      número en BigDecimal [EN]  number in BigDecimal
     * @param digitos dígitos de redondeo [EN]  rounding digits
     * @return número redondeado o cadena vacía en caso de error [EN]  rounded number or empty string in case of error
     */
    public static BigDecimal round(BigDecimal in, int digitos) {
        /*Si los dígitos son negativos devolvemos el mismo número
        * [EN]  If the digits are negative we return the same number*/
        if (digitos < 0) {
            return notNaN(in);
        }
        return notNaN(in).setScale(digitos, RoundingMode.HALF_UP);
    }

    /**
     * Redondea un número
     * <p>
     * [EN]  Round a number
     *
     * @param in      número en Double [EN]  number in Double
     * @param digitos dígitos de redondeo [EN]  rounding digits
     * @return número redondeado o cadena vacía en caso de error [EN]  rounded number or empty string in case of error
     * @deprecated use {@link #notNaN(BigDecimal)} instead
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    public static double round(Double in, int digitos) {
        if (digitos < 0) {
            return notNaN(in);
        }

        in = notNaN(in);
        /*Realizar un pre-redondeo evita el error del 5
         [EN]  Performing a pre-rounding avoids the error of 5*/
        in = new BigDecimal(String.valueOf(in)).setScale(digitos + 2, RoundingMode.HALF_UP).doubleValue();
        return new BigDecimal(String.valueOf(in)).setScale(digitos, RoundingMode.HALF_UP).doubleValue();
    }

    // 3.- CONVERSION_________________________________________________________
    // 3.- CONVERSION ______________________________________________________

    /**
     * Conversión de cadena de texto a double
     * [EN]  Converting string to double
     *
     * @param input cadena de texto de entrada [EN]  input string
     * @return double convertido [EN]  double converted
     * @deprecated use {@link #parseBigDecimal(String)} instead
     */
    @Deprecated
    public static double parse(String input) {
        double out;
        if (!TextTools.isNumeric(input)) {
            return 0.0;
        }
        try {
            out = Double.parseDouble(input);
        } catch (Exception ex) {
            return 0.0;
        }
        return out;
    }

    /**
     * Conversión de cadena de texto a integer
     * [EN]  Converting string to integer
     *
     * @param input cadena de texto de entrada [EN]  input string
     * @return entero convertido [EN]  integer converted
     */
    public static int parseInt(String input) {
        int out;

        if (!TextTools.isNumeric(input)) {
            return 0;
        }
        try {
            out = new BigDecimal(input).toBigInteger().intValue();
        } catch (Exception ex) {
            return 0;
        }
        return out;
    }

    /**
     * Conversión de double a bigdecimal
     * [EN]  Converting double to bigdecimal
     *
     * @param input   Double
     * @param rounder redondeo de decimales [EN]  rounding decimals
     * @return bigdecimal convertido [EN]  bigdecimal converted
     * @deprecated use {@link #parseBigDecimal(String)}  instead
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    public static BigDecimal parseBigDecimal(Double input, Integer rounder) {
        if (rounder == null) {
            return new BigDecimal(notNaN(input));
        }
        return new BigDecimal(round(input, rounder));
    }

    /**
     * Conversión de cadena de texto a bigdecimal
     * [EN]  Converting string to bigdecimal
     *
     * @param in entrada cadena de texto numérica [EN]  input numeric text string
     * @return 0.0 con cadena no numérica [EN]  0.0 with non-numeric string
     */
    public static BigDecimal parseBigDecimal(String in) {
        return TextTools.isNumeric(in) ? new BigDecimal(in) : new BigDecimal("0.0");
    }

    //4.- COMPARADORES________________________________________________________
    //4.- COMPARATORS_____________________________________________________

    /**
     * Compara Doubles
     * [EN]  Compare Doubles
     *
     * @param d1 valor 1 [EN]  value 1
     * @param d2 valor 2 [EN]  value 2
     * @return true si son iguales [EN]  true if they are the same
     * @deprecated use {@link #areEquals(BigDecimal, BigDecimal)} instead
     */
    @Deprecated
    public static boolean areEquals(Double d1, Double d2) {
        return !(d1 == null || d2 == null) && d1.compareTo(d2) == 0;
    }

    /**
     * Compara bigdecimal
     * [EN]  Compare bigdecimal
     *
     * @param d1 valor 1 [EN]  value 1
     * @param d2 valor 2 [EN]  value 2
     * @return true si son iguales [EN]  true if they are the same
     */
    public static boolean areEquals(BigDecimal d1, BigDecimal d2) {
        return ((d1 != null) && (d2 != null) && (d1.compareTo(d2) == 0));
    }

    /**
     * Compara cadena numérica
     * [EN]  Compare numeric string
     *
     * @param d1 valor 1 [EN]  value 1
     * @param d2 valor 2 [EN]  value 2
     * @return true si son iguales [EN]  true if they are the same
     */
    public static boolean areEquals(String d1, String d2) {
        return TextTools.isNumeric(d1)
                && TextTools.isNumeric(d2)
                && areEquals(new BigDecimal(d1), new BigDecimal(d2));
    }

    /**
     * Comparación de dos números redondeados
     * [EN]  Comparison of two rounded numbers
     *
     * @param num1  entrada 1 [EN]  input 1
     * @param num2  entrada 2 [EN]  input 2
     * @param round digitos de redondeo [EN]  rounding digits
     * @return true si es el mismo número
     */
    public static boolean equalNum(BigDecimal num1, BigDecimal num2, Integer round) {
        /*Ignorar redondeador nulo o negativo [EN]  Ignore null or negative rounder*/
        if (round == null || round < 0) {
            return areEquals(num1, num2);
        }
        String snum1 = format(num1, round);
        String snum2 = format(num1, round);
        return snum1.equals(snum2);
    }

    /**
     * Comparación de dos números redondeados
     * [EN]  Comparison of two rounded numbers
     *
     * @param num1  entrada 1 [EN]  input 1
     * @param num2  entrada 2 [EN]  input 2
     * @param round digitos de redondeo [EN]  rounding digits
     * @return true si es el mismo número
     * @deprecated use {@link #equalNum(BigDecimal, BigDecimal, Integer)}  instead
     */
    @Deprecated
    public static boolean equalNum(BigDecimal num1, Double num2, Integer round) {
       /*valor nulo devuelve falso [EN]  null value returns false*/
        if (num1 == null || num2 == null) {
            return false;
        }
        /*10 decimales para redondeador nulo o negativo [EN]  10 decimal places for null or negative rounder*/
        if (round == null || round < 0) {
            round = 10;
        }
        String snum1 = format(num1, round);
        String snum2 = format(num1, round);
        return snum1.equals(snum2);
    }

    /**
     * Comparación de dos números redondeados
     * [EN]  Comparison of two rounded numbers
     *
     * @param num1  entrada 1 [EN]  input 1
     * @param num2  entrada 2 [EN]  input 2
     * @param round digitos de redondeo [EN]  rounding digits
     * @return true si es el mismo número
     */
    public static boolean equalNum(BigDecimal num1, String num2, Integer round) {
       /*valor nulo devuelve falso [EN]  null value returns false*/
        if (num1 == null || num2 == null) {
            return false;
        }
        /*10 decimales para redondeador nulo o negativo [EN]  10 decimal places for null or negative rounder*/
        if (round == null || round < 0) {
            round = 10;
        }
        return areEquals(round(num1, round), round(new BigDecimal(num2), round));
    }

    /**
     * Comparación de dos números redondeados
     * [EN]  Comparison of two rounded numbers
     *
     * @param num1  entrada 1 [EN]  input 1
     * @param num2  entrada 2 [EN]  input 2
     * @param round digitos de redondeo [EN]  rounding digits
     * @return true si es el mismo número
     * @deprecated use {@link #equalNum(BigDecimal, String, Integer)}  instead
     */
    public static boolean equalNum(Double num1, String num2, Integer round) {
      /*valor nulo devuelve falso [EN]  null value returns false*/
        if (num1 == null || num2 == null) {
            return false;
        }
        /*10 decimales para redondeador nulo o negativo [EN]  10 decimal places for null or negative rounder*/
        if (round == null || round < 0) {
            round = 10;
        }
        return areEquals(round(new BigDecimal(num1), round), round(new BigDecimal(num2), round));
    }

    /**
     * Comparación de dos números redondeados
     * [EN]  Comparison of two rounded numbers
     *
     * @param num1  entrada 1 [EN]  input 1
     * @param num2  entrada 2 [EN]  input 2
     * @param round digitos de redondeo [EN]  rounding digits
     * @return true si es el mismo número
     */
    public static boolean equalNum(String num1, String num2, Integer round) {
      /*valor nulo devuelve falso [EN]  null value returns false*/
        if (num1 == null || num2 == null) {
            return false;
        }
        /*10 decimales para redondeador nulo o negativo [EN]  10 decimal places for null or negative rounder*/
        if (round == null || round < 0) {
            round = 10;
        }
        return areEquals(round(new BigDecimal(num1), round), round(new BigDecimal(num2), round));
    }

// 5.- FORMATEO DE NUMEROS_________________________________________________________
// 5.- FORMAT OF NUMBERS____________________________________________________________

    /**
     * Formateo de número
     * [EN]  Number formatting
     *
     * @param input Número [EN]  Number
     * @param round Dígitos de redondeo [EN]  Digits of rounding
     * @return Texto formateado [EN]  Formatted text
     */
    public static String format(BigDecimal input, int round) {
        String stylus = "#,##0";

        if (round > 0) {
            stylus = stylus + ".";
            for (int i = 0; i < round; ++i) {
                stylus = stylus + "0";
            }
        } else {
            stylus = "#,###.#";
        }
        return new DecimalFormat(stylus).format(round(input, round));
    }

    /**
     * Formateo de número
     * [EN]  Number formatting
     *
     * @param input Número [EN]  Number
     * @param round Dígitos de redondeo [EN]  Digits of rounding
     * @return Texto formateado [EN]  Formatted text
     */
    public static String format(String input, int round) {
        return format(round(parseBigDecimal(input), round), round);
    }

    /**
     * Formateo de número
     * [EN]  Number formatting
     *
     * @param input Número [EN]  Number
     * @param round Dígitos de redondeo [EN]  Digits of rounding
     * @return Texto formateado [EN]  Formatted text
     * @deprecated use {@link #format(BigDecimal, int)} instead
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    public static String format(Double input, int round) {
        return format(parseBigDecimal(input, round), round);
    }

    /**
     * Formateo de índices
     * [EN]  Formatting indexes
     *
     * @param input índice numérico positivo [EN]  positive numeric index
     * @param round dígitos total del índice [EN]  total index digits
     * @return ej 00001 para (1,5) [EN]  00001 for (1,5)
     */
    public static String formatCifra(Integer input, int round) {
        String stylus = "0";
        if (round > 0) {
            for (int i = 1; i < round; ++i) {
                stylus = stylus + "0";
            }
        }
        return new DecimalFormat(stylus).format(Math.abs(notNaN(input)));
    }

    /**
     * Formateo de número %
     * [EN]  Number formatting %
     *
     * @param input Número [EN]  Number
     * @param round Dígitos de redondeo [EN]  Digits of rounding
     * @return Texto formateado % [EN]  Formatted text %
     * @deprecated use {@link #formatPercentaje(BigDecimal, int)}  instead
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    public static String formatPercentaje(Double input, int round) {
        return formatPercentaje(parseBigDecimal(input, round), round);
    }

    /**
     * Formateo de número %
     * [EN]  Number formatting %
     *
     * @param input Número [EN]  Number
     * @param round Dígitos de redondeo [EN]  Digits of rounding
     * @return Texto formateado % [EN]  Formatted text %
     */
    public static String formatPercentaje(BigDecimal input, int round) {
        String stylus = "##0";
        if (round > 0) {
            stylus = stylus + ".";
            for (int i = 0; i < round; ++i) {
                stylus = stylus + "0";
            }
        } else {
            stylus = "#,###.#";
        }
        stylus = stylus + "%";
        return new DecimalFormat(stylus).format(round(input, round + 2));
    }

    /**
     * Formateo de número %
     * [EN]  Number formatting %
     *
     * @param input Número [EN]  Number
     * @param round Dígitos de redondeo [EN]  Digits of rounding
     * @return Texto formateado % [EN]  Formatted text %
     */
    public static String formatPercentaje(String input, int round) {
        return formatPercentaje(parseBigDecimal(input), round);
    }

    /**
     * Determina si un número(num), es multiplo de otro (ref)
     * [EN]  Determine whether a number (num) is multiplied by another (ref)
     *
     * @param num número [EN]  number
     * @param ref referencia [EN]  number
     * @return true si es mútilplo [EN]  true if multiple
     */
    public static boolean isMultiple(int num, int ref) {
        return num % ref == 0;
    }

    /**
     * Autonumerar un índice
     * [EN]  Autonumber an index
     *
     * @param value  índice a incrementar [EN]  index to increase
     * @param cifras dígitos del índice [EN]  index digits
     * @return índice incrementado [EN]  increased index
     */
    public static String autoNum(String value, int cifras) {
        /*autoajuste de índice inicial [EN]  initial index auto-tuning*/
        if (TextTools.isEmpty(value) || value.length() < cifras) {
            value = formatCifra(0, cifras);
        }
        return value.substring(0, value.length() - cifras)
                + MathTools.formatCifra(MathTools.parseInt(value.substring(value.length() - cifras, value.length())) + 1, cifras);
    }

    /**
     * Evaluación de una expresión escrita
     * [EN]  Evaluation of a written expression
     *
     * @param expr expresión operadores ‘(‘, ‘)’, ‘+’, ‘-‘, ‘*’, ‘/’ y ‘^’; las variables ‘a’, ‘b’, ‘c’ y ‘d’<p>
     *             [EN]  expression operators '(', ')', '', '-', '*', '/' and '^';  the variables 'a', 'b', 'c' and 'd'
     * @return valor calculado [EN]  calculated value
     * @deprecated use {@link #evalbd(String)} instead
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    public static Double eval(String expr) {
        return evalPostFija(postFija(expr));
    }

    /**
     * Transforma la expresión al método Polaco Inverso
     * [EN]  Transforms the expression to the Polish Inverse method
     *
     * @param expr expresión [EN]  Transforms the expression to the Polish Inverse method
     * @return resultado [EN]  result
     * @deprecated use {@link #evalPostFijaBd(String)} instead
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    private static Double evalPostFija(String expr) {

        /*Entrada (Expresión en Postfija) [EN]  Input (Express in Postfija)*/
        /*String expr = "2 23 6 + * 1 -"; equivale a 2*(23+6)-1 */
        String[] post = expr.split(" ");

        /*Declaración de las pilas [EN]  Declaration of batteries*/
        Stack<String> E = new Stack<>(); //Pila entrada
        Stack<String> P = new Stack<>(); //Pila de operandos

        //Añadir post (array) a la Pila de entrada (E)
        for (int i = post.length - 1; i >= 0; i--) {
            E.push(post[i]);
        }

        //Algoritmo de Evaluación Postfija
        String operadores = "+-*/%^";
        while (!E.isEmpty()) {
            if (operadores.contains("" + E.peek())) {
                P.push(evaluar(E.pop(), P.pop(), P.pop()) + "");
            } else {
                P.push(E.pop());
            }
        }
        return Double.parseDouble(P.peek());
    }

    /**
     * Resuelve la operación
     * [EN]  Resolve operation
     *
     * @param op operador [EN]  operator
     * @param n2 operandos [EN]  operands
     * @param n1 operandos [EN]  operands
     * @return resultado [EN]  result
     * @deprecated use {@link #evaluarbd(String, String, String)} instead
     */
    @Deprecated
    private static Double evaluar(String op, String n2, String n1) {
        double num1 = Double.parseDouble(n1);
        double num2 = Double.parseDouble(n2);
        if (op.equals("+")) return (num1 + num2);
        if (op.equals("-")) return (num1 - num2);
        if (op.equals("*")) return (num1 * num2);
        if (op.equals("/")) return (num1 / num2);
        if (op.equals("%")) return (num1 % num2);
        if (op.equals("^")) return (Math.pow(num1, num2));
        return 0.0;
    }

    /**
     * Evaluación de una expresión escrita
     * [EN]  Evaluation of a written expression
     *
     * @param expr expresión operadores ‘(‘, ‘)’, ‘+’, ‘-‘, ‘*’, ‘/’ y ‘^’; las variables ‘a’, ‘b’, ‘c’ y ‘d’<p>
     *             [EN]  expression operators '(', ')', '', '-', '*', '/' and '^';  the variables 'a', 'b', 'c' and 'd'
     * @return valor calculado [EN]  calculated value
     */
    public static BigDecimal evalbd(String expr) {
        return evalPostFijaBd(postFija(expr));
    }

    /**
     * Transforma la expresión al método Polaco Inverso
     * [EN]  Transforms the expression to the Polish Inverse method
     *
     * @param expr expresión [EN]  Transforms the expression to the Polish Inverse method
     * @return resultado [EN]  result
     */
    private static BigDecimal evalPostFijaBd(String expr) {

        //Entrada (Expresión en Postfija)
        //String expr = "2 23 6 + * 1 -"; // equivale a 2*(23+6)-1
        String[] post = expr.split(" ");

        //Declaración de las pilas
        Stack<String> E = new Stack<>(); //Pila entrada
        Stack<String> P = new Stack<>(); //Pila de operandos

        //Añadir post (array) a la Pila de entrada (E)
        for (int i = post.length - 1; i >= 0; i--) {
            E.push(post[i]);
        }

        //Algoritmo de Evaluación Postfija
        String operadores = "^+-*/%";
        while (!E.isEmpty()) {
            if (operadores.contains("" + E.peek())) {
                P.push(evaluarbd(E.pop(), P.pop(), P.pop()).toString());
            } else {
                P.push(E.pop());
            }
        }

        //Mostrar resultados:
        //System.out.println("Expresion: " + expr);
        //System.out.println("Resultado: " + P.peek());
        return new BigDecimal(P.peek());
    }

    /**
     * Resuelve la operación
     * [EN]  Resolve operation
     *
     * @param op operador [EN]  operator
     * @param n2 operandos [EN]  operands
     * @param n1 operandos [EN]  operands
     * @return resultado [EN]  result
     */
    private static BigDecimal evaluarbd(String op, String n2, String n1) {
        BigDecimal num1 = new BigDecimal(n1);
        BigDecimal num2 = new BigDecimal(n2);
        if (op.equals("+")) return (num1.add(num2));
        if (op.equals("-")) return (num1.subtract(num2));
        if (op.equals("*")) return (num1.multiply(num2));
        if (op.equals("/")) return (num1.divide(num2, MathContext.DECIMAL64));
        if (op.equals("%")) return (num1.divideAndRemainder(num2)[1]);
        if (op.equals("^")) {
            //Comprobamos si el número2 es entero
            if (n2.contains(".")) {
                //Es decimal
                return new BigDecimal(Math.pow(num1.doubleValue(), num2.doubleValue()));
            }

            return (num1.pow(num2.toBigInteger().intValue()));
        }
        return new BigDecimal("0.0");
    }

    /**
     * Cambio de notación
     * [EN]  Change of notation
     *
     * @param expresion expresión original
     * @return expresión de notación inversa [EN]  inverse notation expression
     */
    private static String postFija(String expresion) {
        String expr = depurar(expresion);
        String[] arrayInfix = expr.split(" ");
        String postfix = "";
        /*Declaración de las pilas [EN]  Declaration of stack*/
        Stack<String> E = new Stack<>(); //Pila entrada
        Stack<String> P = new Stack<>(); //Pila temporal para operadores
        Stack<String> S = new Stack<>(); //Pila salida

        //Añadir la array a la Pila de entrada (E)
        for (int i = arrayInfix.length - 1; i >= 0; i--) {
            E.push(arrayInfix[i]);
        }

        try {
            //Algoritmo Infijo a Postfijo
            while (!E.isEmpty()) {
                switch (pref(E.peek())) {
                    case 1:
                        P.push(E.pop());
                        break;
                    case 3:
                    case 4:
                    case 5:
                        while (pref(P.peek()) >= pref(E.peek())) {
                            S.push(P.pop());
                        }
                        P.push(E.pop());
                        break;
                    case 2:
                        while (!P.peek().equals("(")) {
                            S.push(P.pop());
                        }
                        P.pop();
                        E.pop();
                        break;
                    default:
                        S.push(E.pop());
                }
            }

            /*Eliminacion de `impurezas´ en la expresiones algebraicas [EN]  Elimination of 'impurities' in algebraic expressions*/
           // String infix = expr.replace(" ", "");
            postfix = S.toString().replaceAll("[\\]\\[,]", "");
        } catch (Exception ex) {
            System.out.println("Error en la expresión algebraica");
            System.err.println(ex.toString());
        }
        return postfix;
    }

    /**
     * Depura expresión algebraica
     * [EN]  Purges algebraic expression
     *
     * @param s expresión original [EN]  original expression
     * @return expresión depurada [EN]  purified expression
     */
    private static String depurar(String s) {
        s = s.replaceAll("\\s+", ""); //Elimina espacios en blanco
        s = "(" + s + ")";
        String simbols = "^+-*/()";
        String str = "";

        //Deja espacios entre operadores
        for (int i = 0; i < s.length(); i++) {
            if (simbols.contains("" + s.charAt(i))) {
                str += " " + s.charAt(i) + " ";
            } else str += s.charAt(i);
        }
        return str.replaceAll("\\s+", " ").trim();
    }

    /**
     * Establece la jerarquía de los operadores
     * [EN]  Sets the hierarchy of operators
     *
     * @param op operador [EN]  operator
     * @return jeraraquía del operador [EN] operator hierarchy
     */
    private static int pref(String op) {
        int prf = 99;
        if (op.equals("^")) prf = 5;
        if (op.equals("*") || op.equals("/")) prf = 4;
        if (op.equals("+") || op.equals("-")) prf = 3;
        if (op.equals(")")) prf = 2;
        if (op.equals("(")) prf = 1;
        return prf;
    }
}



