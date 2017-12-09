package es.marser.backgroundtools.listables.table.holder;

import android.databinding.ViewDataBinding;

import es.marser.backgroundtools.listables.base.holder.ViewHolderType;
import es.marser.backgroundtools.listables.base.controller.ViewHolderController;
import es.marser.backgroundtools.listables.simple.holder.ViewHolderBinding;

/**
 * @author sergio
 *         Created by sergio on 1/11/17.
 *         Holder de cabecera
 *         <p>
 *         [EN]  Header Holder
 */

public class HeaderViewHolderBinding<H> extends ViewHolderBinding<H> {

    @Override
    public int getIndexTypeView() {
        return ViewHolderType.HEAD.ordinal();
    }

    public HeaderViewHolderBinding(ViewDataBinding itemViewBindable, ViewHolderController<H> viewHolderController) {
        super(itemViewBindable, viewHolderController);
       // Log.i(LOG_TAG.TAG, "CREADO CABECERA ");
    }
}
