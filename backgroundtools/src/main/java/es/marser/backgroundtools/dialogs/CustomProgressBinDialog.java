package es.marser.backgroundtools.dialogs;

import android.content.Context;
import android.os.Bundle;

import es.marser.backgroundtools.R;
import es.marser.tools.MathTools;
import es.marser.backgroundtools.dialogs.bases.BaseCustomBinDialog;
import es.marser.backgroundtools.dialogs.model.DialogProgressModel;

/**
 * @author sergio
 *         Created by Sergio on 05/09/2017.
 *         Cuadro de progreso personalizado MVP
 *         <p>
 *         [EN]  MVP Custom Progress Box
 * @see es.marser.backgroundtools.dialogs.bases.BaseCustomBinDialog
 */

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class CustomProgressBinDialog extends BaseCustomBinDialog {

    /**
     * Crear una nueva instancia del Dialogo
     * <p>
     * [EN]  Error Text to Display
     *
     * @param bundle Icono de la aplicación
     * @return nueva instancia
     * @see #createBundle(String)
     */
    public static CustomProgressBinDialog newInstace(Context context, Bundle bundle) {
        CustomProgressBinDialog instance = new CustomProgressBinDialog();
        instance.setContext(context);
        if (bundle == null) {
            bundle = createBundle(DEFAULT_ICON);
        }
        instance.setArguments(bundle);
        return instance;
    }

    /**
     * Utilidad de creación de los argumentos de iniciales
     * <p>
     * [EN]  Arguments creation utility
     *
     * @param icon Nombre de icono [EN]  Arguments creation utility
     * @return Bundle construido [EN]  Built Bundle
     * @see DialogProgressModel#BC3_ICON
     * @see DialogProgressModel#PDF_ICON
     * @see DialogProgressModel#EXCEL_ICON
     * @see DialogProgressModel#DATABASE_ICON
     * @see DialogProgressModel#LOADING_ICON
     * @see DialogProgressModel#CALC_ICON
     */
    public static Bundle createBundle(String icon) {
        Bundle bundle = new Bundle();
        bundle.putString(ICON_EXTRA, icon);
        return bundle;
    }

    @Override
    protected int getDialogLayout() {
        return R.layout.mvp_dialog_progress;
    }

    /**
     * Introducir el valor máximo de la barra de progreso
     * <p>
     * [EN]  Enter the maximum value of the progress bar
     *
     * @param max valor máximo [EN]  maximum value
     * @return this
     */
    public CustomProgressBinDialog setMax(Integer max) {
        if (max == null || max < 0) {
            indeterminate(true);
        } else {
            indeterminate(false);
            source.max.set(String.valueOf(max));
        }
        return this;
    }

    /**
     * Indicar si la barra es de progreso o indeterminada
     *
     * @param value verdadero indeterminada [EN]  true indeterminate
     * @return this
     */
    public CustomProgressBinDialog indeterminate(boolean value) {
        source.indeterminate.set(value);
        return this;
    }

    /**
     * Indicar el progreso
     * <p>
     * [EN]  true indeterminate
     *
     * @param value valor absoluto del progreso [EN]  absolute value of progress
     * @return this
     */
    public CustomProgressBinDialog setProgress(Integer value) {
        if (!source.indeterminate.get()) {
            if (value != null) {
                source.progress.set(String.valueOf(value));
                source.progresstext.set("Progreso: " + source.progress.get() + " de " + source.max.get());
            }
        }
        return this;
    }

    /**
     * Incrementa el progreso de la barra en una cantidad determinada
     * <p>
     * [EN]  Increases the progress of the bar by a certain amount
     *
     * @param value valor a incrementar el progreso [EN]  value to increase progress
     * @return this
     */
    public CustomProgressBinDialog increment(Integer value) {

        if (value != null) {
            int actual = MathTools.parseInt(source.progress.get()) + value;
            source.progress.set(String.valueOf(actual));
            if (!source.indeterminate.get()) {
                source.progresstext.set("Progreso: " + source.progress.get() + " de " + source.max.get());
            }
        }
        return this;
    }

    /**
     * Incrementa el progreso de la barra en una unidad
     * <p>
     * [EN]  Increases the progress of the bar in a unit
     *
     * @return this
     */
    public CustomProgressBinDialog autoIncrement() {
        increment(1);
        return this;
    }

    /**
     * Agregar línea de error al dialogo de progreso
     * <p>
     * [EN]  Add error line to progress dialog
     *
     * @param error Texto de error a mostrar [EN]  Error Text to Display
     * @return this
     */
    public CustomProgressBinDialog addError(String error) {
        String in = source.error.get() + error + "\n";
        source.error.set(in);
        return this;
    }
}
