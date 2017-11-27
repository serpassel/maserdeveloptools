package es.marser.backgroundtools.objectslistables.table.holder;

import android.databinding.ViewDataBinding;

import es.marser.backgroundtools.definition.Selectable;
import es.marser.backgroundtools.objectslistables.base.controller.ViewHolderController;
import es.marser.backgroundtools.objectslistables.base.holder.ViewHolderType;
import es.marser.backgroundtools.objectslistables.simple.holder.ViewHolderBinding;

/**
 * @author sergio
 *         Created by sergio on 1/11/17.
 */

@SuppressWarnings("unused")
public class TitleViewHolderBinding extends ViewHolderBinding<Selectable> {

    @Override
    public int getIndexTypeView() {
        return ViewHolderType.TITLE.ordinal();
    }

    public TitleViewHolderBinding(ViewDataBinding itemViewBindable, ViewHolderController<Selectable> viewHolderController) {
        super(itemViewBindable, viewHolderController);
    }
}
