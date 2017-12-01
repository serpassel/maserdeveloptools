package es.marser.backgroundtools.widget.files;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;

/**
 * @author sergio
 *         Created by sergio on 30/11/17.
 *         Definción de cambio de oyente en selectores de archivos
 *         <p>
 *         [EN]  Defining listener change in file selectors
 */

@SuppressWarnings("unused")
public interface OnPathChangedListener {

    /**
     * Avisador de cambio de archivo
     * <p>
     * [EN]  File change warning
     *
     * @param oldFile Archivo viejo [EN]  Old file
     * @param newPath Archivo nuevo [EN]  New file
     */
    void onPathChanged(@Nullable File oldFile, @NonNull File newPath);
}
