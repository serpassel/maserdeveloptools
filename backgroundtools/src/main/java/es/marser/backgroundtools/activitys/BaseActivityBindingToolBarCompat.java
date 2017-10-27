package es.marser.backgroundtools.activitys;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.activitys.base.BaseActivity;


/**
 * Created by Sergio on 08/04/2017.
 * Actividad con barra de acciones
 */

public abstract class BaseActivityBindingToolBarCompat extends BaseActivity {

    /*Variable de presentador de vistas [EN]  View Presenter Variable*/
    protected ViewDataBinding viewDataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewDataBinding = DataBindingUtil.setContentView(this, getActivityLayout());

        /*Enlazar variables modelo [EN]  Link model variables*/
        bindObjects();
        /*Activar toolbar [EN]  Activar toolbar*/
        initToolbar();
    }

    protected abstract void bindObjects();

    protected int getActivityLayout() {
        return R.layout.ac_frag_toolbar;
    }

}
