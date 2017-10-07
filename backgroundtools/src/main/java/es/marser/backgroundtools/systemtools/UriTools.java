package es.marser.backgroundtools.systemtools;


import android.net.Uri;
import android.webkit.URLUtil;

/**
 * @author sergio
 *         Created by Sergio on 28/03/2017.
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public class UriTools {
    public static final int FROM_GMAIL = 0;
    public static final int FROM_STORAGE = 1;
    public static final int FROM_UNKNOW = 2;

    /**
     * Comprobar origen de una URI
     * <p>
     * [EN]  Check origin of a URI
     *
     * @param uri Ruta de origen [EN]  Route of origin
     * @return Véase variables estáticas [EN]  See static variables
     */
    public static int locateUriProvider(Uri uri) {
        if (uri.toString().contains("//storage")) {
            return FROM_STORAGE;
        } else if (uri.toString().contains("//gmail")) {
            return FROM_GMAIL;
        } else {
            return FROM_UNKNOW;
        }
    }

    /**
     * Comporbar la validez de una Uri
     * <p>
     * [EN]  To compute the validity of a Uri
     *
     * @param uri Ruta de origen [EN]  Route of origin
     * @return verdadero si la Uri es correcta [EN]  true if the Uri is correct
     */
    public static boolean isValid(Uri uri) {
        return URLUtil.isValidUrl(uri.toString());
    }
}
