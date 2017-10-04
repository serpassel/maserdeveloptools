/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.marser;

import java.math.BigDecimal;

/**
 * CifrasEnLetras sirve para expresar una serie de cifras en letras.
 * A modo de ejemplo convierte 22 en veintidós.
 * Puede convertir un número entre una y ciento veintiséis cifras como máximo.
 * Este software está sujeto a la CC-GNU GPL
 *
 * @author Francisco Cascales fco@proinf.net
 * @version 0.05,  7-ene-2008 - Mejoras estructurales
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public class CifrasEnLetras {

    /////////////////////////////////////////////
    // CONSTANTES

    private final static String PREFIJO_ERROR = "Error: ";
    private final static String COMA = ",";
    private final static String MENOS = "-";

    /////////////////////////////////////////////
    // ENUMERACIONES

    private enum Genero {
        neutro, masculino, femenino;

        private boolean esMasculino() {
            return this == masculino;
        }

        private boolean esFemenino() {
            return this == femenino;
        }

        private static Genero desdeNumero(int numero) {
            if (numero == 0) {
                return neutro;
            } else if (numero == 1) return masculino;
            return femenino;
        }
    }

    /////////////////////////////////////////////
    // LISTAS

    /**
     * Letras de los números entre el 0 y el 29
     */
    private final static String[] listaUnidades = {
            "cero", "un", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve",
            "diez", "once", "doce", "trece", "catorce", "quince", "dieciséis", "diecisiete", "dieciocho", "diecinueve",
            "veinte", "veintiún", "veintidós", "veintitrés", "veinticuatro", "veinticinco", "veintiséis", "veintisiete", "veintiocho", "veintinueve"
    };
    /**
     * Letras de las decenas
     */
    private final static String[] listaDecenas = {
            "", "diez", "veinte", "treinta", "cuarenta", "cincuenta", "sesenta", "setenta", "ochenta", "noventa"
    };
    /**
     * Letras de las centenas
     */
    private final static String[] listaCentenas = {
            "", "cien", "doscientos", "trescientos", "cuatrocientos", "quinientos", "seiscientos", "setecientos", "ochocientos", "novecientos"
    };
    /**
     * Letras en singular de los órdenes de millón
     */
    private final static String[] listaOrdenesMillonSingular = {
            "", "millón", "billón", "trillón", "cuatrillón", "quintillón",
            "sextillón", "septillón", "octillón", "nonillón", "decillón",
            "undecillón", "duodecillón", "tridecillón", "cuatridecillón", "quidecillón",
            "sexdecillón", "septidecillón", "octodecillón", "nonidecillón", "vigillón"
    };
    /**
     * Letras en plural de los órdenes de millón
     */
    private final static String[] listaOrdenesMillonPlural = {
            "", "millones", "billones", "trillones", "cuatrillones", "quintillones",
            "sextillones", "septillones", "octillones", "nonillones", "decillones",
            "undecillones", "duodecillones", "tridecillones", "cuatridecillones", "quidecillones",
            "sexdecillones", "septidecillones", "octodecillones", "nonidecillones", "vigillones"
    };

    // ORDINALES (Wikipedia: "Nombres de los números en español", "Número ordinal")

    /**
     * Letras de los ordinales entre 0º y 19º
     */
    public final static String[] listaUnidadesOrdinales = {
            "ningún", "primer", "segundo", "tercer", "cuarto", "quinto", "sexto", "séptimo", "octavo", "noveno",
            "décimo", "undécimo", "duodécimo", "decimotercer", "decimocuarto", "decimoquinto", "decimosexto", "decimoséptimo", "decimoctavo", "decimonoveno"
    };
    /**
     * Letras de las decenas ordinales
     */
    public final static String[] listaDecenasOrdinales = {
            "", "décimo", "vigésimo", "trigésimo", "cuadragésimo", "quincuagésimo", "sexagésimo", "septuagésimo", "octogésimo", "nonagésimo"
    };
    /**
     * Letras de las centenas ordinales
     */
    public final static String[] listaCentenasOrdinales = {
            "", "centésimo", "ducentésimo", "tricentésimo", "cuadringentésimo", "quingentésimo", "sexcentésimo", "septingentésimo", "octingentésimo", "noningentésimo"
    };
    /**
     * Letras de las potencias de diez ordinales
     */
    public final static String[] listaPotenciasDiezOrdinales = {
            "", "décimo", "centésimo", "milésimo", "diezmilésimo", "cienmilésimo", "millonésimo"
    };

    /////////////////////////////////////////////
    // MÉTODOS PRINCIPALES

    /**
     * Convierte a letras los números entre 0 y 29.
     * Ejemplo: <code>convertirUnidades(21,Genero.femenino)</code> &rarr;
     *
     * @param unidades undades de la cifra
     * @param genero   género del texto
     * @return unidades en letras
     */
    private static String convertirUnidades(int unidades, Genero genero) {
        if (unidades == 1) {
            if (genero.esMasculino()) return "uno";
            else if (genero.esFemenino()) return "una";
        } else if (unidades == 21) {
            if (genero.esMasculino()) return "veintiuno";
            else if (genero.esFemenino()) return "veintiuna";
        }
        return listaUnidades[unidades];
    }

    /**
     * Convierte a letras las centenas
     * Ejemplo: <code>convertirCentenas(2,Genero.femenino)</code>
     *
     * @param centenas valor de las centenas
     * @param genero   género de las centenas
     * @return centenas en letras
     */
    private static String convertirCentenas(int centenas, Genero genero) {
        String resultado = listaCentenas[centenas];
        if (genero.esFemenino()) {
            resultado = resultado.replaceAll("iento", "ienta");
        }
        return resultado;
    }

    /**
     * Primer centenar: del cero al noventa y nueve.
     *
     * @param cifras cantidad
     * @param genero genero
     * @return cifra en letras
     */
    private static String convertirDosCifras(int cifras, Genero genero) {
        int unidad = cifras % 10;
        int decena = cifras / 10;
        if (cifras < 30) return convertirUnidades(cifras, genero);
        else if (unidad == 0) return listaDecenas[decena];
        else return listaDecenas[decena] + " y " + convertirUnidades(unidad, genero);
    }

    /**
     * Primer millar: del cero al novecientos noventa y nueve.
     *
     * @param cifras número de cifras
     * @param genero género de las cifras
     * @return letra tres primeras cifras de números superiores al millar
     */
    private static String convertirTresCifras(int cifras, Genero genero) {
        int decenas_y_unidades = cifras % 100;
        int centenas = cifras / 100;
        if (cifras < 100) return convertirDosCifras(cifras, genero);
        else if (decenas_y_unidades == 0) return convertirCentenas(centenas, genero);
        else if (centenas == 1) return "ciento " + convertirDosCifras(decenas_y_unidades, genero);
        else
            return convertirCentenas(centenas, genero) + " " + convertirDosCifras(decenas_y_unidades, genero);
    }

    /**
     * Primer millón: del cero al novecientos noventa y nueve mil novecientos noventa y nueve.
     *
     * @param cifras número de cifras
     * @param genero género de las cifras
     * @return letra tres primeras cifras de números superiores al millón
     */
    private static String convertirSeisCifras(int cifras, Genero genero) {
        int primerMillar = cifras % 1000;
        int grupoMiles = cifras / 1000;
        Genero generoMiles = genero.esMasculino() ? Genero.neutro : genero;
        if (grupoMiles == 0) return convertirTresCifras(primerMillar, genero);
        else if (grupoMiles == 1) {
            if (primerMillar == 0) return "mil";
            else return "mil " + convertirTresCifras(primerMillar, genero);
        } else if (primerMillar == 0) return convertirTresCifras(grupoMiles, generoMiles) + " mil";
        else
            return convertirTresCifras(grupoMiles, generoMiles) + " mil " + convertirTresCifras(primerMillar, genero);
    }

    /**
     * Números enteros entre el cero y el novecientos noventa y nueve mil novecientos noventa y nueve vigillones... etc, etc.<br />
     * Es decir entre el 0 y el (10<sup>126</sup>)-1 o bien números entre 1 y 126 cifras.<br />
     * Las cifras por debajo del millón pueden ir en masculino o en femenino.
     *
     * @param cifras                    cadena de texto numérica
     * @param genero                    genero de la cadena
     * @param separadorGruposSeisCifras marca de separación de los grupos
     * @return letras de la cifra
     */
    private static String convertirCifrasEnLetras(String cifras, Genero genero,
                                                  String separadorGruposSeisCifras) {
        // Inicialización
        cifras = cifras.trim();
        int numeroCifras = cifras.length();

        // Comprobación
        if (numeroCifras == 0) return PREFIJO_ERROR + "No hay ningún número";
        for (int indiceCifra = 0; indiceCifra < numeroCifras; ++indiceCifra) {
            char cifra = cifras.charAt(indiceCifra);
            boolean esDecimal = "0123456789".indexOf(cifra) >= 0;
            if (!esDecimal) return PREFIJO_ERROR + "Uno de los caracteres no es una cifra decimal";
        }
        if (numeroCifras > 126)
            return PREFIJO_ERROR + "El número es demasiado grande ya que tiene más de 126 cifras";

        // Preparación
        int numeroGruposSeisCifras = numeroCifras / 6 + Integer.signum(numeroCifras);
        String cerosIzquierda = repetirCaracter('0', numeroGruposSeisCifras * 6 - numeroCifras);
        cifras = cerosIzquierda + cifras;
        int ordenMillon = numeroGruposSeisCifras - 1;

        // Procesamiento
        StringBuilder resultado = new StringBuilder();
        for (int indiceGrupo = 0; indiceGrupo < numeroGruposSeisCifras * 6; indiceGrupo += 6) {
            int seisCifras = Integer.parseInt(cifras.substring(indiceGrupo, indiceGrupo + 6));
            if (seisCifras != 0) {
                if (resultado.length() > 0) resultado.append(separadorGruposSeisCifras);

                if (ordenMillon == 0) {
                    resultado.append(convertirSeisCifras(seisCifras, genero));
                } else if (seisCifras == 1) {
                    resultado.append("un ").append(listaOrdenesMillonSingular[ordenMillon]);
                } else {
                    resultado.append(convertirSeisCifras(seisCifras, Genero.neutro)).append(" ").append(listaOrdenesMillonPlural[ordenMillon]);
                }
            }
            ordenMillon--;
        }

        // Finalización
        if (resultado.length() == 0) resultado.append(listaUnidades[0]);
        return resultado.toString();
    }

    /**
     * Convierte una cadena numérica en textos con genero neutro
     *
     * @param cifras cadena de texto numérica
     * @return letras de la cadena
     */
    private static String convertirCifrasEnLetras(String cifras) {
        return convertirCifrasEnLetras(cifras, Genero.neutro, " ");
    }

    /**
     * Convierte una cadena numérica en textos con genero masculino
     *
     * @param cifras cadena de texto numérica
     * @return letras de la cadena
     */
    private static String convertirCifrasEnLetrasMasculinas(String cifras) {
        return convertirCifrasEnLetras(cifras, Genero.masculino, " ");
    }

    /**
     * Convierte una cadena numérica en textos con genero femenino
     *
     * @param cifras cadena de texto numérica
     * @return letras de la cadena
     */
    private static String convertirCifrasEnLetrasFemeninas(String cifras) {
        return convertirCifrasEnLetras(cifras, Genero.femenino, " ");
    }

    /**
     * Expresa un número con decimales y signo en letras
     * acompañado del tipo de medida para la parte entera y la parte decimal.
     * <ul>
     * <li>Los caracteres no numéricos son ignorados.</li>
     * <li>Los múltiplos de millón tienen la preposición <q>de</q> antes de la palabra.</li>
     * <li>El género masculino o femenino sólo puede influir en las cifras inferiores al millón</li>
     *
     * @param cifras                   cadena de texto numérica
     * @param numeroDecimales          Si es -1 el número de decimales es automático
     * @param palabraEnteraSingular    Letras de la cifra entera en singular
     * @param palabraEnteraPlural      Letras de la cifra entera en pural
     * @param esFemeninaPalabraEntera  verdadero si es de género femenimo
     * @param palabraDecimalSingular   Letras de la cifra decimal en singular
     * @param palabraDecimalPlural     Letras de la cifra decimal en pural
     * @param esFemeninaPalabraDecimal verdadero si es de género femenimo
     * @return letras de la cifra
     */
    private static String convertirNumeroEnLetras(String cifras, int numeroDecimales,
                                                  String palabraEnteraSingular, String palabraEnteraPlural, boolean esFemeninaPalabraEntera,
                                                  String palabraDecimalSingular, String palabraDecimalPlural, boolean esFemeninaPalabraDecimal) {
        // Limpieza
        cifras = dejarSoloCaracteresDeseados(cifras, "0123456789" + COMA + MENOS);

        // Comprobaciones
        int repeticionesMenos = numeroRepeticiones(cifras, MENOS);
        int repeticionesComa = numeroRepeticiones(cifras, COMA);
        if (repeticionesMenos > 1 || (repeticionesMenos == 1 && !cifras.startsWith(MENOS)))
            return PREFIJO_ERROR + "Símbolo negativo incorrecto o demasiados símbolos negativos";
        else if (repeticionesComa > 1) return PREFIJO_ERROR + "Demasiadas comas decimales";

        // Negatividad
        boolean esNegativo = cifras.startsWith(MENOS);
        if (esNegativo) cifras = cifras.substring(1);

        // Preparación
        int posicionComa = cifras.indexOf(COMA);
        if (posicionComa == -1) posicionComa = cifras.length();

        String cifrasEntera = cifras.substring(0, posicionComa);
        if (cifrasEntera.equals("") || cifrasEntera.equals(MENOS)) cifrasEntera = "0";
        String cifrasDecimal = cifras.substring(Math.min(posicionComa + 1, cifras.length()));

        if (numeroDecimales >= 0) {
            cifrasDecimal = cifrasDecimal.substring(0, Math.min(numeroDecimales, cifrasDecimal.length()));
            String cerosDerecha = repetirCaracter('0', numeroDecimales - cifrasDecimal.length());
            cifrasDecimal = cifrasDecimal + cerosDerecha;
        }

        // Cero
        boolean esCero = dejarSoloCaracteresDeseados(cifrasEntera, "123456789").equals("") &&
                dejarSoloCaracteresDeseados(cifrasDecimal, "123456789").equals("");

        // Procesar
        StringBuilder resultado = new StringBuilder();

        if (esNegativo && !esCero) resultado.append("menos ");

        String parteEntera = procesarEnLetras(cifrasEntera, palabraEnteraSingular, palabraEnteraPlural, esFemeninaPalabraEntera);
        if (parteEntera.startsWith(PREFIJO_ERROR)) return parteEntera;
        resultado.append(parteEntera);

        if (!cifrasDecimal.equals("")) {
            String parteDecimal = procesarEnLetras(cifrasDecimal, palabraDecimalSingular, palabraDecimalPlural, esFemeninaPalabraDecimal);
            if (parteDecimal.startsWith(PREFIJO_ERROR)) return parteDecimal;
            resultado.append(" con ");
            resultado.append(parteDecimal);
        }

        return resultado.toString();
    }

    /**
     * @param cifras Cadena de texto en
     * @return cifra en letras
     */
    public static String convertirNumeroEnLetras(String cifras) {
        return convertirNumeroEnLetras(cifras, -1, "", "", false, "", "", false);
    }

    /**
     * @param cifras          cadena de texto numérica
     * @param numeroDecimales número de decimales
     * @return Letras de la cadena de texto
     */
    public static String convertirNumeroEnLetras(String cifras, int numeroDecimales) {
        return convertirNumeroEnLetras(cifras, numeroDecimales, "", "", false, "", "", false);
    }

    /**
     * @param cifras        cadena de texto numérica
     * @param palabraEntera nomvre de la palabra entera
     * @return Letras de la cadena de texto
     */
    public static String convertirNumeroEnLetras(String cifras, String palabraEntera) {
        return convertirNumeroEnLetras(cifras, 0, palabraEntera, palabraEntera + "s", false, "", "", false);
    }

    /**
     * @param cifras                  cadena de texto numérica
     * @param palabraEntera           nombre de la palabra entera
     * @param esFemeninaPalabraEntera verdadero si la palabra entera es femenima
     * @return Letras de la cadena de texto
     */
    public static String convertirNumeroEnLetras(String cifras, String palabraEntera, boolean esFemeninaPalabraEntera) {
        return convertirNumeroEnLetras(cifras, 0, palabraEntera, palabraEntera + "s", esFemeninaPalabraEntera, "", "", false);
    }

    /**
     * @param cifras          cadena de texto numérica
     * @param palabraEntera   nombre de la palabra entera
     * @param numeroDecimales número de decimales
     * @param palabraDecimal  nombre de la palabra decimal
     * @return Letras de la cadena de texto
     */
    private static String convertirNumeroEnLetras(String cifras, int numeroDecimales, String palabraEntera, String palabraDecimal) {
        return convertirNumeroEnLetras(cifras, numeroDecimales,
                palabraEntera, palabraEntera + "s", false,
                palabraDecimal, palabraDecimal + "s", false);
    }

    /**
     * @param cifras                   cadena de texto numérica
     * @param numeroDecimales          Si es -1 el número de decimales es automático
     * @param esFemeninaPalabraEntera  verdadero si es de género femenimo
     * @param esFemeninaPalabraDecimal verdadero si es de género femenimo
     * @param palabraEntera            texto de la palbra entera
     * @param palabraDecimal           texto de la palabra decimal
     * @return letras de la cifra
     */
    private static String convertirNumeroEnLetras(String cifras, int numeroDecimales,
                                                  String palabraEntera, String palabraDecimal, boolean esFemeninaPalabraEntera, boolean esFemeninaPalabraDecimal) {
        return convertirNumeroEnLetras(cifras, numeroDecimales,
                palabraEntera, palabraEntera + "s", esFemeninaPalabraEntera,
                palabraDecimal, palabraDecimal + "s", esFemeninaPalabraDecimal);
    }

    /**
     * @param cifras          cadena de texto numérica
     * @param palabraSingular palabra singular
     * @param palabraPlural   palabra plural
     * @param esFemenina      verdadero si es femenima
     * @return Letras de la cadena de texto
     */
    private static String procesarEnLetras(String cifras,
                                           String palabraSingular, String palabraPlural, boolean esFemenina) {
        // Género
        Genero genero = Genero.neutro;
        if (esFemenina) genero = Genero.femenino;
        else if (palabraSingular.equals("")) genero = Genero.masculino;

        // Letras
        String letras = convertirCifrasEnLetras(cifras, genero, " ");
        if (letras.startsWith(PREFIJO_ERROR)) return letras;

        // Propiedades // 7-ene-2008
        boolean esCero = letras.equals(convertirUnidades(0, genero)) || letras.equals(""); //letras.equals("cero") || letras.equals("");
        boolean esUno = letras.equals(convertirUnidades(1, genero)); // letras.equals("un") || letras.equals("una") || letras.equals("uno");
        boolean esMultiploMillon = !esCero && cifras.endsWith("000000");

        // Palabra
        String palabra = "";
        if (!palabraSingular.equals("")) {
            if (esUno || palabraPlural.equals(""))
                palabra = palabraSingular;
            else
                palabra = palabraPlural;
        }

        // Resultado
        StringBuilder resultado = new StringBuilder();
        resultado.append(letras);
        if (!palabra.equals("")) {
            if (esMultiploMillon) resultado.append(" de ");
            else resultado.append(" ");
            resultado.append(palabra);
        }
        return resultado.toString();
    }

    /**
     * * @param cifras          cadena de texto numérica
     *
     * @param numeroDecimales número de decimales
     * @return Letras de la cadena de texto
     */
    private static String convertirEurosEnLetras(String cifras, int numeroDecimales) {
        return convertirNumeroEnLetras(cifras, numeroDecimales, "euro", "céntimo");
    }

    /**
     * Convertir cadena de texto entera numérica en euros
     *
     * @param euros cantidad
     * @return letras
     */
    public static String convertirEurosEnLetras(long euros) {
        String cifras = String.valueOf(euros);
        return convertirEurosEnLetras(cifras, 0);
    }

    /**
     * Convertir cadena de texto decimal con dos cifras en euros
     *
     * @param euros cantidad
     * @return letras
     * @deprecated use {@link CifrasEnLetras#convertirEurosEnLetras(BigDecimal)} instead
     */
    public static String convertirEurosEnLetras(double euros) {
        String cifras = String.valueOf(euros).replace('.', ',');
        return convertirEurosEnLetras(cifras, 2);
    }

    /**
     * Convertir cadena de texto decimal con dos cifras en euros
     *
     * @param euros cantidad
     * @return letras
     */
    public static String convertirEurosEnLetras(BigDecimal euros) {
        String cifras = String.valueOf(euros).replace('.', ',');
        return convertirEurosEnLetras(cifras, 2);
    }


    /////////////////////////////////////////////
    // FUNCIONES AUXILIARES

    /**
     * Crea un texto repitiendo el caracter el número de veces indicado
     *
     * @param caracter caracter a repetir
     * @param veces cantidad
     * @return concatenación de las repeticiones
     */
    public static String repetirCaracter(char caracter, int veces) {
        char[] arreglo = new char[veces];
        java.util.Arrays.fill(arreglo, caracter);
        return new String(arreglo);
    }

    /**
     * Borra todos los caracteres del texto que no sea alguno de los caracteres deseados.
     *
     * @param texto texto completo
     * @param caracteresDeseados caracteres a eliminar
     * @return texto limpio
     */
    public static String dejarSoloCaracteresDeseados(String texto, String caracteresDeseados) {
        int indice = 0;
        StringBuilder resultado = new StringBuilder(texto);
        while (indice < resultado.length()) {
            char caracter = resultado.charAt(indice);
            if (caracteresDeseados.indexOf(caracter) < 0) resultado.deleteCharAt(indice);
            else indice++;
        }
        return resultado.toString();
    }

    /**
     * Cuenta el número de repeticiones en el texto de los caracteres indicados
     *
     * @param texto cadena completa
     * @param caracteres cadena buscada
     * @return número de repeticiones
     */
    public static int numeroRepeticiones(String texto, String caracteres) {
        int resultado = 0;
        for (int indice = 0; indice < texto.length(); ++indice) {
            char caracter = texto.charAt(indice);
            if (caracteres.indexOf(caracter) >= 0) resultado++;
        }
        return resultado;
    }

    /**
     * Separa las cifras en grupos de 6 con subrayados y los grupos de 6 en grupos de 2 con punto
     *
     * @param cifras cadena de texto numérica
     * @return cadena formateada
     */
    public static String formatearCifras(String cifras) {
        cifras = dejarSoloCaracteresDeseados(cifras, "0123456789" + COMA + MENOS);
        if (cifras.length() == 0) return cifras;

        boolean esNegativo = cifras.startsWith(MENOS);
        if (esNegativo) cifras = cifras.substring(1);

        int posicionComa = cifras.indexOf(COMA);
        boolean esDecimal = posicionComa >= 0;

        if (!esDecimal) posicionComa = cifras.length();
        String cifrasEntera = cifras.substring(0, posicionComa);
        String cifrasDecimal = "";

        if (cifrasEntera.equals("")) {
            cifrasEntera = "0";
        }

        StringBuilder resultado = new StringBuilder();
        int numeroCifras = cifrasEntera.length();
        //int numeroGruposTresCifras = numeroCifras / 3 + Integer.signum(numeroCifras);
        boolean par = true;

        for (int indice = 0; indice < numeroCifras; indice += 3) {
            int indiceGrupo = numeroCifras - indice;
            String tresCifras = cifras.substring(Math.max(indiceGrupo - 3, 0), indiceGrupo);
            if (indice > 0) {
                resultado.insert(0, par ? '.' : '_');
                par = !par;
            }
            resultado.insert(0, tresCifras);
        }
        if (esNegativo) resultado.insert(0, MENOS);
        if (esDecimal) {
            resultado.append(COMA).append(cifrasDecimal);
        }

        return resultado.toString();
    }
}
