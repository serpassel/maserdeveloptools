package es.marser.backgroundtools.containers.fragments.widget;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import es.marser.backgroundtools.containers.fragments.base.BaseFragmentListBin;
import es.marser.backgroundtools.listables.simple.model.SimpleAdapterModel;
import es.marser.backgroundtools.listables.simple.presenter.SimpleListPresenter;

/**
 * @author sergio
 *         Created by sergio on 30/11/17.
 *         Base para contrucción de fragmento de lista simple
 *         <p>
 *         [EN]  Base for simple list fragment construction
 */

@SuppressWarnings("unused")
public class SimpleListFragment<T extends Parcelable>
        extends BaseFragmentListBin<
        T, SimpleAdapterModel<T>,
        SimpleListPresenter<T, SimpleAdapterModel<T>>
        > {

    public static <T extends Parcelable> SimpleListFragment<T> newInstance(
            @Nullable Bundle bundle,
            SimpleAdapterModel<T> model,
            SimpleListPresenter<T, SimpleAdapterModel<T>> presenter) {
        SimpleListFragment<T> instance = new SimpleListFragment<>();
        instance.setArguments(bundle);
        instance.setPresenter(presenter);
        instance.setSimpleListModel(model);
        return instance;
    }
}
