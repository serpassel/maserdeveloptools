package es.marser.crypt;

import java.io.UnsupportedEncodingException;
import java.security.Key;

import javax.crypto.spec.SecretKeySpec;

import es.marser.tools.TextTools;


/**
 * @author sergio
 *         Created by Sergio on 21/08/2017.
 *         Codificador de claves hash
 *         <p>
 *         [EN]  Hash Coder
 */

@SuppressWarnings("unused")
public abstract class KeyGenerator {
    /**
     * Generador de claves de codificación, a partir del hash
     * <p>
     * [EN]  Coding key generator, from the hash
     *
     * @param hash hash de codificación [EN]  hash encoding
     * @return Llave para criptografía [EN]  Key for cryptography
     * @throws UnsupportedEncodingException Error de codicación del hash [EN]  Hash coding error
     */
    public static Key keyGenerator(String hash) throws UnsupportedEncodingException {
        while (hash.getBytes().length < 20) {
            hash += "J";
        }
        return new SecretKeySpec(hash.getBytes("UTF-8"), 0, 16, "AES");
    }

    /**
     * Generar un hash en base a un lista de textos
     * Secuencia de transformación de la lista original
     * <p>
     * [EN]  Generate a hash based on a list of texts
     * Sequence of transformation of the original list
     *
     * @param in Lista de cadenas de textos [EN]  List of text strings
     * @return hash de la lista de textos [EN]  hash of the text list
     */
    public static String keyGenere(String[] in) {
        /*Trasponer la matriz [EN]  Transpose the matrix*/
        String trans = TextTools.transposetArray(in);
        /*Ciframos con Cesar [EN]  Cipher with Cesar*/
        String encrypt = caesarEncrypt(trans, trans.length());
        /*Par e impar [EN]  Even and odd*/
        String oddcouple = TextTools.oddCouple(encrypt.toCharArray());
        /*Rotar valor [EN]  Rotate value*/
        return TextTools.rotateString(oddcouple.toCharArray());
    }

    /**
     * Generar un hash en base a una cadena de texto
     * Secuencia de transformación de la lista original
     * <p>
     * [EN]  Generate a hash based on a string of text
     * Sequence of transformation of the original list
     *
     * @param in Cadena de texto [EN]  String of text
     * @return hash de la cadena de texto [EN]  hash of the text string
     */
    public static String keyGenere(String in) {
        /*Cifrar por cesar [EN]  Cite by cesar*/
        String encrypt = caesarEncrypt(in, in.length());
        /*Par e impar [EN]  Even and odd*/
        String oddcouple = TextTools.oddCouple(encrypt.toCharArray());
        /*Rotar el valor [EN]  Rotate the value*/
        return TextTools.rotateString(oddcouple.toCharArray());
    }

    /**
     * Descifrado de hash
     * <p>
     * [EN]  Hash decryption
     *
     * @param key hash cifrado [EN]  encrypted hash
     * @return cadena de texto original [EN]  original text string
     */
    public static String keyHashed(String key) {
        //Extraemos par e impar y lo rotamos
        return TextTools.rotateString(TextTools.oddCouple(key.toCharArray()).toCharArray());
    }

    /**
     * Cifrado de cesar de una cadena de texto
     * <p>
     * [EN]  Cipher to stop a text string
     *
     * @param texto  cadena de texto de original [EN]  original text string
     * @param codigo codigo de desplazamiento [EN]  scrolling code
     * @return Cadena cifrada [EN]  Encrypted string
     */
    public static String caesarEncrypt(String texto, int codigo) {
        StringBuilder cifrado = new StringBuilder();
        codigo = codigo % 26;
        for (int i = 0; i < texto.length(); i++) {
            if (texto.charAt(i) >= 'a' && texto.charAt(i) <= 'z') {
                if ((texto.charAt(i) + codigo) > 'z') {
                    cifrado.append((char) (texto.charAt(i) + codigo - 26));
                } else {
                    cifrado.append((char) (texto.charAt(i) + codigo));
                }
            } else if (texto.charAt(i) >= 'A' && texto.charAt(i) <= 'Z') {
                if ((texto.charAt(i) + codigo) > 'Z') {
                    cifrado.append((char) (texto.charAt(i) + codigo - 26));
                } else {
                    cifrado.append((char) (texto.charAt(i) + codigo));
                }
            }
        }
        return cifrado.toString();
    }

    /**
     * Descifrado de cesar
     * <p>
     * [EN]  Decoding of cesar
     *
     * @param texto  texto cifrado [EN]  encrypted text
     * @param codigo codigo de desplazamiento [EN]  scrolling code
     * @return cadena de texto original [EN]  original text string
     */
    public static String caesarDecrypt(String texto, int codigo) {
        StringBuilder cifrado = new StringBuilder();
        codigo = codigo % 26;
        for (int i = 0; i < texto.length(); i++) {
            if (texto.charAt(i) >= 'a' && texto.charAt(i) <= 'z') {
                if ((texto.charAt(i) - codigo) < 'a') {
                    cifrado.append((char) (texto.charAt(i) - codigo + 26));
                } else {
                    cifrado.append((char) (texto.charAt(i) - codigo));
                }
            } else if (texto.charAt(i) >= 'A' && texto.charAt(i) <= 'Z') {
                if ((texto.charAt(i) - codigo) < 'A') {
                    cifrado.append((char) (texto.charAt(i) - codigo + 26));
                } else {
                    cifrado.append((char) (texto.charAt(i) - codigo));
                }
            }
        }
        return cifrado.toString();
    }
}
