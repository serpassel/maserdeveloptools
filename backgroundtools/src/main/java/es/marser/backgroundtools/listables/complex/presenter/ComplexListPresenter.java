package es.marser.backgroundtools.listables.complex.presenter;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.events.ComplexTouchabeViewHandler;
import es.marser.backgroundtools.events.ViewComplexHandler;
import es.marser.backgroundtools.listables.base.presenter.BaseListPresenter;
import es.marser.backgroundtools.listables.complex.models.ComplexAdapterModel;
import es.marser.backgroundtools.listables.complex.models.ExpandableGroup;

/**
 * @author sergio
 *         Created by sergio on 10/12/17.
 *         Presnetador para listas complejas
 */

public class ComplexListPresenter <X extends ExpandableGroup<T>,
        T extends Parcelable,  CAM extends ComplexAdapterModel<X, T>>
        extends BaseListPresenter<CAM>
        implements ComplexTouchabeViewHandler<X, T>, ViewComplexHandler<X,T>{

    //CONTRUCTOR___________________________________
    @SuppressWarnings("unused")
    public ComplexListPresenter(@NonNull Context context, int viewlayout, @NonNull CAM listModel) {
        super(context, viewlayout, listModel);
        listModel.setComplexTouchabeViewHandler(this);
        listModel.setViewComplexHandler(this);
    }

    /**
     * Método para la carga de datos
     * <p>
     * [EN]  Method for data loading
     *
     * @param bundle Argumentos de carga de datos [EN]  Arguments of data loading
     */
    @Override
    public void load(@Nullable Bundle bundle) {

    }

    //TOUCHABLE VIEW_______________________________

    @Override
    public void onGroupClick(View view, int flap, View root, int index, X group) {

    }

    @Override
    public boolean onGroupLongClick(View view, int flap, View root, int index, X group) {
        return false;
    }

    @Override
    public void onChildClick(View view, int flap, View root, int groupid, X group, int childid, T child) {

    }

    @Override
    public boolean onChildLongClick(View view, int flap, View root, int groupid, X group, int childid, T child) {
        return false;
    }

    @Override
    public void onGroupExpanded(X group) {

    }

    @Override
    public void onGroupCollapsed(X group) {

    }

    @Override
    public void onClickGroupItem(int grouppos, X group, int flatpos, ListExtra selectionmode) {

    }

    @Override
    public boolean onLongClickGroupItem(int grouppos, X group, int flatpos, ListExtra selectionmode) {
        return false;
    }

    @Override
    public void onClickChildItem(int grouppos, int childpos, T child, int flatpos, ListExtra selectionmode) {

    }

    @Override
    public boolean onLongClickChildItem(int grouppos, int childpos, T child, int flatpos, ListExtra selectionmode) {
        return false;
    }
}
