package es.marser.crypt;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * @author sergio
 *         Created by Sergio on 21/08/2017.
 *         Encriptación y desencriptación de cadenas de texto
 *         <p>
 *         [EN]  Encryption and decryption of text strings
 */

@SuppressWarnings("unused")
public abstract class EncryptStringTools {
    static byte[] key = "!@#$!@#$%^&**&^%".getBytes();
    private final static String algorithm = "AES";

    /**
     * Método de encriptación AES de String en UTF-8
     * <p>
     * [EN]  UTF-8 String AES Encryption Method
     *
     * @param data Cadena original [EN]  Original string
     * @param hash hash de codificación [EN]  Original string
     * @return Cadena de texto encriptada [EN]  Encrypted text string
     */
    public static String encrypt(String data, String hash) {
        byte[] encryptedByteValue = new byte[0];
        try {
            encryptedByteValue = new Base64().encode(cipher(data.getBytes("UTF-8"), Cipher.ENCRYPT_MODE, hash));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return new String(encryptedByteValue);
    }

    /**
     * Método de desencriptación AES de String en UTF-8
     * <p>
     * [EN]  String AES decryption method in UTF-8
     *
     * @param data cadena encriptada [EN]  encrypted string
     * @param hash hash de codificación [EN]  hash encoding
     * @return cadena original [EN]  original string
     */
    public static String decrypt(String data, String hash) {

        byte[] encryptedData = new byte[0];
        try {
            encryptedData = new Base64().decode(data.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String out = null;
        try {
            out = new String(cipher(encryptedData, Cipher.DECRYPT_MODE, hash), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return out;
    }

    /**
     * Base para encriptado y desencriptado
     * <p>
     * [EN]  Base for encryption and decryption
     *
     * @param data Cadena de bytes a procesar [EN]  String of bytes to process
     * @param mode encriptado o desencriptado [EN]  Base for encryption and decryption
     * @param hash hash de codificación [EN]  hash encoding
     * @return Cadena de bytes procesada [EN]  Processed byte string
     */
    public static byte[] cipher(byte[] data, int mode, String hash) {
        Cipher aes = null;
        try {
            aes = Cipher.getInstance(algorithm);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }

        if (aes == null) {
            return new byte[0];
        }


        try {
            aes.init(mode, KeyGenerator.keyGenerator(hash));
        } catch (InvalidKeyException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] out = new byte[0];
        try {
            out = aes.doFinal(data);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return out;
    }

    /**
     * Transforma una cadena de byte en integer hexadecimal
     * <p>
     * [EN]  Transforms a byte string into integer hexadecimal
     *
     * @param in cadena de bytes [EN]  byte string
     * @return valor en hexadecimal [EN]  value in hexadecimal
     */
    public static String bytesToHexString(byte[] in) {
        String out = "";
        for (byte b : in) {
            out += Integer.toHexString(0xFF & b);
        }
        return out;
    }

}
