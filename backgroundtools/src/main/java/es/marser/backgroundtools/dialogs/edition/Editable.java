package es.marser.backgroundtools.dialogs.edition;

/**
 * @author sergio
 *         Created by Sergio on 17/05/2017.
 *         Marcador de objetos editables. Se utiliza para indicar si un resgistro se está editando
 *         <p>
 *         [EN]  Marker of editable objects.  Used to indicate if a record is being edited
 *
 *         @see es.marser.backgroundtools.dialogs.edition.GenericEditDialog
 */

interface Editable {
    /**
     * Variable de edicción
     * @param value verdadero si está en edición [EN]  true if edited
     */
    void setEditing(boolean value);
}
