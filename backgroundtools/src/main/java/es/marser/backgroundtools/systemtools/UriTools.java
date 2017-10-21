package es.marser.backgroundtools.systemtools;


import android.net.Uri;
import android.webkit.URLUtil;

import es.marser.backgroundtools.enums.SystemExtras;

/**
 * @author sergio
 *         Created by Sergio on 28/03/2017.
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public class UriTools {
    /**
     * Comprobar origen de una URI
     * <p>
     * [EN]  Check origin of a URI
     *
     * @param uri Ruta de origen [EN]  Route of origin
     * @return Véase variables estáticas [EN]  See static variables
     */
    public static SystemExtras locateUriProvider(Uri uri) {
        if (uri.toString().contains("//storage")) {
            return SystemExtras.URI_FROM_STORAGE;
        } else if (uri.toString().contains("//gmail")) {
            return SystemExtras.URI_FROM_GMAIL;
        } else {
            return SystemExtras.URI_FROM_UNKNOW;
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
