package es.marser.backgroundtools.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.activitys.base.BaseActivity;

/**
 * @author sergio
 *         Created by Sergio on 08/04/2017.
 *         Base de construcción de actividades con barra de menús
 *         <p>
 *         [EN]  Activity building base with menu bar
 * @see BaseActivity
 */

public abstract class BaseActivityToolBarCompat extends BaseActivity {

    protected Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityLayout());

        toolbar = findViewById(R.id.app_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    /**
     * @return Índide el archivo layout de la vista
     * [EN]  Indents the layout layout file
     */
    protected int getActivityLayout() {
        return R.layout.ac_frag_toolbar;
    }

}
