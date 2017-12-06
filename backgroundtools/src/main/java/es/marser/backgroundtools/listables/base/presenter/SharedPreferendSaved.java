package es.marser.backgroundtools.listables.base.presenter;

import android.content.Context;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.systemtools.SharedPreferenceTools;

/**
 * @author sergio
 *         Created by sergio on 6/12/17.
 *         Objeto graba claves en sharedpreferences
 *         <p>
 *         [EN]  Object records keys in sharedpreferences
 */

public interface SharedPreferendSaved {
    //PREFERENCES_____________________________________________________________
    /**
     * Graba la selección de preferencias
     * <p>
     * [EN]  Record preferences selection
     */
    void savePreferences();
}
