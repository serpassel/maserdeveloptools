package es.marser.backgroundtools.containers.fragments.base;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.listables.simple.model.SimpleAdapterModel;
import es.marser.backgroundtools.listables.simple.presenter.SimpleListPresenter;

/**
 * @author sergio
 *         Created by sergio on 1/11/17.
 *         Base para la construcción de fragmentos con lista reciclable
 *         <p>
 *         [EN]  Base for the construction of fragments with recyclable list
 */

@SuppressWarnings("unused")
public abstract class BaseFragmentListBin<
        T extends Parcelable,
        SLM extends SimpleAdapterModel<T>,
        SLP extends SimpleListPresenter<T, SLM>
        >
        extends BaseFragmentBin<SLP> {

    //BIN METHODS OF CONFIGURATION________________________________________________________
    @Override
    protected void postBuild(@Nullable Bundle args) {
        //Log.w(LOG_TAG.TAG, "DATOS NUEVOS");
        if (presenter.isEmpty()) {
            presenter.load(args);
        }
    }

    //VIEWS________________________________________________________________
    @Override
    protected int getFragmentLayout() {
        return R.layout.mvp_frag_simple_list;
    }

}
