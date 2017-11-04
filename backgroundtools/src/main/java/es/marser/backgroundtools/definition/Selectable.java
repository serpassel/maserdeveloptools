package es.marser.backgroundtools.definition;

import android.os.Parcelable;
import android.text.SpannableString;

/**
 * @author sergio
 *         Created by Sergio on 20/07/2017.
 *         Definición de objeto seleccionable para las listas predefinidas
 *         <p>
 *         [EN]  Definition of selectable object for predefined lists
 */

@SuppressWarnings("unused")
public interface Selectable extends Parcelable {
    SpannableString toSpannableString();
    String filterValue();
}
