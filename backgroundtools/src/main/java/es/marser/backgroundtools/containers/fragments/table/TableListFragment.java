package es.marser.backgroundtools.containers.fragments.table;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.fragments.base.BaseFragmentListBin;
import es.marser.backgroundtools.listables.table.model.TableAdapterModel;
import es.marser.backgroundtools.listables.table.presenter.TableListPresenter;

/**
 * @author sergio
 *         Created by sergio on 30/11/17.
 *         Base para contrucción de fragmento de lista simple
 *         <p>
 *         [EN]  Base for simple list fragment construction
 */

@SuppressWarnings("unused")
public class TableListFragment<H extends Parcelable, B extends Parcelable>
        extends BaseFragmentListBin<TableListPresenter<H, B, TableAdapterModel<H, B>>> {

    public static <H extends Parcelable, B extends Parcelable> TableListFragment<H, B> newInstance(
            @Nullable Bundle bundle,
            TableAdapterModel<H, B> model,
            TableListPresenter<H, B, TableAdapterModel<H, B>> presenter) {

        TableListFragment<H, B> instance = new TableListFragment<>();
        instance.setArguments(bundle);
        presenter.setListmodel(model);

        if (presenter.getViewLayout() < 0) {
            presenter.setViewLayout(R.layout.mvp_frag_simple_list);
        }

        instance.setPresenter(presenter);

        return instance;
    }
}
