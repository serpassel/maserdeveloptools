package es.marser.crypt;


import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

/**
 * @author sergio
 *         Created by Sergio on 20/08/2017.
 *         Encriptación y desencriptación de cadenas de bytes
 *         <p>
 *         [EN]  Encryption and decryption of byte strings
 */

@SuppressWarnings("unused")
public abstract class EncryptBytesTools {

    private final static String algorithm = "AES/CBC/PKCS5Padding";

    /**
     * Encriptar cadena de bytes
     * <p>
     * [EN]  Encrypt byte string
     *
     * @param in   cadena de bytes original [EN]  original byte string
     * @param hash hash de codificación [EN]  hash encoding
     * @return cadena de bytes encriptada [EN]  encrypted byte string
     */
    public static byte[] encrypt(byte[] in, String hash) {
        return cipher(in, Cipher.ENCRYPT_MODE, hash);
    }

    /**
     * Desencriptado de cadea de bytes
     * <p>
     * [EN]  Decode of byte string
     *
     * @param in   cadena de bytes cifrados [EN]  encrypted byte string
     * @param hash clave de cifrado [EN]  encrypted byte string
     * @return arreglo de bytes original [EN]  original byte array
     */
    public static byte[] decrypt(byte[] in, String hash) {
        return cipher(in, Cipher.DECRYPT_MODE, hash);
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

        byte[] iv = new byte[aes.getBlockSize()];
        IvParameterSpec ivparam = new IvParameterSpec(iv);
        try {
            aes.init(mode, KeyGenerator.keyGenerator(hash), ivparam);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException | UnsupportedEncodingException e) {
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
}