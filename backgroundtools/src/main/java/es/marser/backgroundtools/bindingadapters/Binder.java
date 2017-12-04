package es.marser.backgroundtools.bindingadapters;

import android.support.annotation.Nullable;

import java.util.Collection;

/**
 * @author sergio
 *         Created by sergio on 4/12/17.
 *         Definición de clase que enlaza variables a vista, en {@link android.databinding.ViewDataBinding}
 *         <p>
 *         [EN]  Class definition that links variables to view, in {@link android.databinding.ViewDataBinding}
 */

@SuppressWarnings("unused")
public interface Binder {

    void addBindableCollection(@Nullable Collection<Bindable> list);

    void addBindable(@Nullable Bindable bindable);

    void removeBindable(@Nullable Bindable bindable);

    void bindBindable(@Nullable Bindable bindable);
}
