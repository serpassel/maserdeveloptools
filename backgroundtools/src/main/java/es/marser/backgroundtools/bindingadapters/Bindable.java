package es.marser.backgroundtools.bindingadapters;

/**
 * @author sergio
 *         Created by sergio on 4/12/17.
 *         Definición de objeto para introducir en una variable XML en {@link android.databinding.ViewDataBinding}
 *         <p>
 *         [EN]  Object definition to enter in an XML variable in {@link android.databinding.ViewDataBinding}
 */

public interface Bindable {
    /**
     *
     * @return  Objeto a vincular [EN]  Object to link
     */
    Object getObject();
    /**
     *
     * @return variable sobre la que se enlaza del tipo BR [EN]  variable on which type BR is linked
     */
    int getBR();
}
