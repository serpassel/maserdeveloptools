package es.marser.backgroundtools.objectslistables.headerbody.holder;

import android.databinding.ViewDataBinding;

import es.marser.backgroundtools.objectslistables.base.holder.ViewHolderType;
import es.marser.backgroundtools.objectslistables.base.controller.ViewHolderController;
import es.marser.backgroundtools.objectslistables.simple.holder.ViewHolderBinding;

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
    }
}
