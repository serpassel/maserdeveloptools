package es.marser.backgroundtools.listables.base.presenter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.bindingadapters.BinderContainer;
import es.marser.backgroundtools.enums.ListExtra;
import es.marser.backgroundtools.listables.base.model.BaseAdapterModel;
import es.marser.backgroundtools.listables.base.model.Selectionable;

/**
 * @author sergio
 *         Created by sergio on 5/12/17.
 */

public abstract class BaseListPresenter<LM extends BaseAdapterModel>
        extends BasePresenter
        implements AdapterPresenter, Selectionable {

    private LM listmodel;

    //CONSTRUCTORS____________________________________________________
    public BaseListPresenter(@NonNull Context context, int viewlayout) {
        super(context, viewlayout);
    }

    public BaseListPresenter(@NonNull Context context, int viewlayout, @NonNull LM listModel) {
        super(context, viewlayout);
        setListmodel(listModel);
    }

    //ADAPTER PRESENTER_____________________________________________________
    /**
     * Indicador del conmienzo de la vinculación de vistas {@link ViewDataBinding}
     * <p>
     * [EN]  Join linking view indicator
     *
     * @param binderContainer Objeto de enlace de vistas [EN]  View link object
     */
    @Override
    public void onBindObjects(@NonNull BinderContainer binderContainer) {
        binderContainer.bindObject(BR.listmodel, listmodel);
    }


    //VARIABLES__________________________________________________________
    public LM getListmodel() {
        return listmodel;
    }

    public void setListmodel(LM listmodel) {
        this.listmodel = listmodel;
    }

    public boolean isEmpty(){
        return !(listmodel != null && listmodel.getAdapter() != null) || listmodel.getAdapter().getItemCount() <= 0;
    }

    //SAVED AND RESTORE____________________________________________________
    @Override
    public void onSaveInstanceState(@Nullable Bundle savedInstanceState) {
        if(listmodel != null){
            listmodel.onSaveInstanceState(savedInstanceState);
        }
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        if(listmodel != null){
            listmodel.onRestoreInstanceState(savedInstanceState);
        }
    }

    //SELECTIONABLE______________________________________________________________
    @Nullable
    @Override
    public ListExtra getSelectionmode(@Nullable Integer viewtype) {
        return listmodel != null ? listmodel.getSelectionmode(viewtype) : null;
    }

    @Override
    public void setSelectionmode(@Nullable Integer viewType, @NonNull ListExtra selectionmode) {
        if(listmodel != null){
            listmodel.setSelectionmode(viewType, selectionmode);
        }
    }
}
