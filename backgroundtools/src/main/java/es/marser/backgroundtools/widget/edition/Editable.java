package es.marser.backgroundtools.widget.edition;

import es.marser.backgroundtools.widget.edition.dialog.EditDialog;

/**
 * @author sergio
 *         Created by Sergio on 17/05/2017.
 *         Marcador de objetos editables. Se utiliza para indicar si un resgistro se está editando
 *         <p>
 *         [EN]  Marker of editable objects.  Used to indicate if a record is being edited
 *
 *         @see EditDialog
 */

interface Editable {
    /**
     * Variable de edicción
     * @param value verdadero si está en edición [EN]  true if edited
     */
    void setEditing(boolean value);
}
