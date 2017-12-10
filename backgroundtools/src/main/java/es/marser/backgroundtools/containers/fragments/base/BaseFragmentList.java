package es.marser.backgroundtools.containers.fragments.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import es.marser.backgroundtools.listables.base.presenter.BaseListPresenter;

/**
 * @author sergio
 *         Created by sergio on 1/11/17.
 *         Base para la construcción de fragmentos con lista reciclable
 *         <p>
 *         [EN]  Base for the construction of fragments with recyclable list
 */

@SuppressWarnings("unused")
public abstract class BaseFragmentList<SLP extends BaseListPresenter>
        extends BaseFragmentBin<SLP> {

    //BIN METHODS OF CONFIGURATION________________________________________________________
    @Override
    protected void postBuild(@Nullable Bundle args) {
        //Log.w(LOG_TAG.TAG, "DATOS NUEVOS");
        if (presenter.isEmpty()) {
            presenter.load(args);
        }
    }
}
