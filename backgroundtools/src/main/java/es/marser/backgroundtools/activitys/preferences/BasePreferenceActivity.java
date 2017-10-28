package es.marser.backgroundtools.activitys.preferences;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.activitys.base.BaseActivity;


/**
 * @author sergio
 *         Created by Sergio on 08/04/2017.
 *         Base para creación de actividades de configuración
 *         <p>
 *         [EN]  Basis for creation of configuration activities
 */

@SuppressWarnings("unused")
public abstract class BasePreferenceActivity extends BaseActivity {
    protected Toolbar toolbar;
    protected PreferenceFragment customfragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityLayout());
    }

    @Override
    protected void instaceVariables() {
        customfragment = (PreferenceFragment) getFragmentManager()
                .findFragmentByTag(getResources()
                        .getString(R.string.FRAGMENT_PREFERENCE));
    }

    @Override
    protected void postinstaceVariables() {
        if (title() != null) {
            toolbar.setTitle(title());
        }
    }

    @Override
    public boolean activeToolbarSupport() {
        return title() != null;
    }

    protected abstract void event();

    /**
     * Título de la barra de herramientas
     * <p>
     * [EN]  Toolbar Title
     *
     * @return si el valor es nulo se elimina la barra de herramientas
     * [EN]  if the value is null, the toolbar is deleted
     */
    protected String title() {
        return getResources().getString(R.string.default_preference_title);
    }

    /**
     * Configuración de tema de visualización
     * <p>
     * [EN]  Display theme settings
     *
     * @param activity sin
     * @param attr     sin
     * @return sin
     */
    private static int getResIdFromAttribute(final Activity activity, final int attr) {
        if (attr == 0) {
            return 0;
        }
        final TypedValue typedvalueattr = new TypedValue();
        activity.getTheme().resolveAttribute(attr, typedvalueattr, true);
        return typedvalueattr.resourceId;
    }
}
