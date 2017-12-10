package es.marser.backgroundtools.listables.complex.models;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.events.ComplexTouchabeViewHandler;
import es.marser.backgroundtools.events.ViewComplexHandler;
import es.marser.backgroundtools.listables.base.model.BaseAdapterModel;
import es.marser.backgroundtools.listables.complex.adapter.ComplexAdapter;

/**
 * @author sergio
 *         Created by sergio on 10/12/17.
 *         Modelo para adaptador de listas complejas
 */

@SuppressWarnings("unused")
public class ComplexAdapterModel<X extends ExpandableGroup<T>,
        T extends Parcelable> extends BaseAdapterModel<ComplexAdapter<X, T>> {

    //CONSTRUCTORS________________________________
    public ComplexAdapterModel(@NonNull Context context, @NonNull LinearLayoutManager layoutManager) {
        super(context, layoutManager);
    }

    //SELECTIONABLE________________________________


    @Nullable
    @Override
    public ListExtra getSelectionmode(@Nullable Integer viewtype) {
        return null;
    }

    @Override
    public void setSelectionmode(@Nullable Integer viewType, @NonNull ListExtra selectionmode) {

    }


    //PRESENTER____________________________________

    public void setComplexTouchabeViewHandler(@Nullable ComplexTouchabeViewHandler<X, T> complexTouchabeViewHandler) {
        if(getAdapter() != null){
            getAdapter().setComplexTouchabeViewHandler(complexTouchabeViewHandler);
        }
    }

    @Nullable
    public ComplexTouchabeViewHandler<X, T> getComplexTouchabeViewHandler() {
        return getAdapter() != null ? getAdapter().getComplexTouchabeViewHandler() : null;
    }

    public void removeComplexTouchabeViewHandler() {
        if(getAdapter() != null){
            getAdapter().removeComplexTouchabeViewHandler();
        }
    }

    /*ITEM EVENT*/
    public void setViewComplexHandler(@Nullable ViewComplexHandler<X, T> viewComplexHandler) {
        if(getAdapter() != null){
            getAdapter().setViewComplexHandler(viewComplexHandler);
        }
    }

    @Nullable
    public ViewComplexHandler<X, T> getViewComplexHandler() {
        return getAdapter() != null ? getAdapter().getViewComplexHandler() : null;
    }

    public void removeViewComplexHandler() {
       if(getAdapter() != null){
           getAdapter().removeComplexTouchabeViewHandler();
       }
    }

}
