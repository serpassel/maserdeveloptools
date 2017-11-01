package es.marser.backgroundtools.objectslistables.headerbody.holder;

import android.databinding.ViewDataBinding;

import es.marser.backgroundtools.objectslistables.base.holder.ViewHolderType;
import es.marser.backgroundtools.objectslistables.base.controller.ViewHolderController;
import es.marser.backgroundtools.objectslistables.simple.holder.ViewHolderBinding;

/**
 * @author sergio
 *         Created by sergio on 1/11/17.
 */

public class BodyViewHolderBinding<B> extends ViewHolderBinding<B> {

    @Override
    public int getIndexTypeView() {
        return ViewHolderType.BODY.ordinal();
    }

    public BodyViewHolderBinding(ViewDataBinding itemViewBindable, ViewHolderController<B> viewHolderController) {
        super(itemViewBindable, viewHolderController);
    }
}
