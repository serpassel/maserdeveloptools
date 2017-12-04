package es.marser.backgroundtools.bindingadapters;

import android.support.annotation.NonNull;

/**
 * @author sergio
 *         Created by sergio on 4/12/17.
 *         Definición de clase con capacidad de vinculación de variables a vistas de tipo {@link android.databinding.ViewDataBinding}
 *         <p>
 *         [EN]  Class definition with ability to link variables to type views
 */

@SuppressWarnings("unused")
public interface BinderContainer {
    void bindObject(int var, @NonNull Object obj);
}
