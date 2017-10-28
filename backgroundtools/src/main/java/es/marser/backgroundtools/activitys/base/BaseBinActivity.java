package es.marser.backgroundtools.activitys.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * @author sergio
 *         Created by sergio on 28/10/17.
 *         Base de creación de actividades con patrón de diseño MVP
 *         <p>
 *         [EN]  MVP design pattern activity creation base
 */

public abstract class BaseBinActivity extends BaseActivity {
    /*Variable de presentador de vistas [EN]  View Presenter Variable*/
    protected ViewDataBinding viewDataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDataBinding = DataBindingUtil.setContentView(this, getActivityLayout());
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        /*Enlazar variables modelo [EN]  Link model variables*/
        bindObjects();
    }

    /**
     * Método para enlace de variables a las vistas
     * <p>
     * [EN]  Method for linking variables to views
     */
    protected abstract void bindObjects();
}
