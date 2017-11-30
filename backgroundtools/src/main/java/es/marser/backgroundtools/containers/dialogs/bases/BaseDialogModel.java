package es.marser.backgroundtools.containers.dialogs.bases;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;

/**
 * @author sergio
 *         Created by Sergio on 06/09/2017.
 *         Base de construcción de Dialogos personalizados. Patrón de diseño MVC
 *         <p>
 *         [EN]  Custom Dialogos building base. [EN]  MVC design pattern
 */

@SuppressWarnings("unused")
public abstract class BaseDialogModel extends BaseDialog {

    @Override
    protected void createDialog() {
        init();
    }

    /**
     * Iniciar los componentes del dialogo
     * <p>
     * [EN]  Start the components of the dialog
     */
    private void init() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            view = inflater.inflate(getDialogLayout(), null);
        }
        builder.setView(view);
        dialog = builder.create();

        postCreate();
    }

    /**
     * Método añadir inicio de variables adicionales
     * <p>
     * [EN]  Add additional variables start method
     */
    protected abstract void postCreate();

    /**
     * Indicar la variable del recurso layout xml
     * <p>
     *     [EN]  Display layout variable xml
     * @return R.layout.view_id
     */
    protected abstract int getDialogLayout();
}
