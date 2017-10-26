package es.marser.backgroundtools.objectslistables.complex.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import es.marser.backgroundtools.objectslistables.complex.holder.ChildViewHolderBinding;
import es.marser.backgroundtools.objectslistables.complex.holder.GroupViewHolderBinding;
import es.marser.backgroundtools.objectslistables.complex.models.ExpandableGroup;

/**
 * @author sergio
 *         Created by Sergio on 28/04/2017.
 *         Enlace de adapter con listas expandibles
 *         <p>
 *         [EN]  Link adapter with expandable lists
 */

@SuppressWarnings("unused")
public abstract class ComplexBindingAdapter<X extends ExpandableGroup<T>, T extends Parcelable>
        extends BaseComplexAdapter<
        GroupViewHolderBinding<X, T>,
        ChildViewHolderBinding<X, T>, X, T> {


    protected ComplexBindingAdapter() {
        super();
    }


    protected abstract int getGroupLayoutHolder();//{return 0;}

    protected abstract int getChildLayoutHolder();//{return 0;}

    @Override
    public GroupViewHolderBinding<X, T> onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from((parent.getContext()));
        ViewDataBinding dataBinding = DataBindingUtil.inflate(layoutInflater, getGroupLayoutHolder(), parent, false);
        return new GroupViewHolderBinding<>(dataBinding, this);
    }

    @Override
    public ChildViewHolderBinding<X, T> onCreateChildViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from((parent.getContext()));
        ViewDataBinding dataBinding = DataBindingUtil.inflate(layoutInflater, getChildLayoutHolder(), parent, false);
        return new ChildViewHolderBinding<>(dataBinding, this);
    }


    @Override
    public void onBindGroupViewHolder(GroupViewHolderBinding<X, T> holder, int flatPosition, X group) {
        holder.bind(group);
        if (getComplexTouchabeViewHandler() != null) {
            holder.attachTouchableViewHandler(getComplexTouchabeViewHandler());
        }
    }

    @Override
    public void onBindChildViewHolder(ChildViewHolderBinding<X, T> holder, int flatPosition, X group, int childIndex) {
        holder.bind(group.getItems().get(childIndex));

        if (getComplexTouchabeViewHandler() != null) {
            holder.attachTouchableViewHandler(getComplexTouchabeViewHandler());
        }
    }

}
