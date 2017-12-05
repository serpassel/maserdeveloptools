package es.marser.backgroundtools.containers.dialogs.widget.progress;

import android.content.Context;
import android.os.Bundle;

import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.dialogs.model.DialogProgressModel;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.tools.MathTools;
import es.marser.backgroundtools.containers.dialogs.bases.BaseDialogBinDecrep;

/**
 * @author sergio
 *         Created by Sergio on 05/09/2017.
 *         Cuadro de progreso personalizado MVP
 *         <p>
 *         [EN]  MVP Custom Progress Box
 * @see BaseDialogBinDecrep
 */

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class BinProgressDialog extends BaseDialogBinDecrep {

    private DialogProgressModel source;

    /**
     * Crear una nueva instancia del Dialogo
     * <p>
     * [EN]  Error Text to Display
     *
     * @param bundle Icono de la aplicación
     * @return nueva instancia
     * @see #createBundle(String)
     */
    public static BinProgressDialog newInstance(Context context, Bundle bundle) {
        BinProgressDialog instance = new BinProgressDialog();
        instance.setContext(context);
        if (bundle == null) {
            bundle = createBundle(DialogIcon.DEFAULT_ICON);
        }
        instance.setSource(new DialogProgressModel());
        instance.setTitle(context.getResources().getString(R.string.bt_loading));
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
    public static Bundle createBundle(DialogIcon icon) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DialogIcon.ICON_EXTRA.name(), icon);
        return bundle;
    }

    @Override
    protected int getDialogLayout() {
        return R.layout.mvp_dialog_progress;
    }

    @Override
    protected void bindObject() {
        viewDataBinding.setVariable(BR.model, source);
        viewDataBinding.executePendingBindings();
    }

    /**
     * Introducir el valor máximo de la barra de progreso
     * <p>
     * [EN]  Enter the maximum value of the progress bar
     *
     * @param max valor máximo [EN]  maximum value
     * @return this
     */
    public BinProgressDialog setMax(Integer max) {
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
    public BinProgressDialog indeterminate(boolean value) {
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
    public BinProgressDialog setProgress(Integer value) {
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
    @SuppressWarnings("SameParameterValue")
    public BinProgressDialog increment(Integer value) {

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
    public BinProgressDialog autoIncrement() {
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
    public BinProgressDialog addError(String error) {
        String in = source.error.get() + error + "\n";
        source.error.set(in);
        return this;
    }

    /**
     *
     * Devuelve el modelo de datos
     *
     * @return modelo de datos
     */
    public DialogProgressModel getSource() {
        return source;
    }

    /**
     * Insertar modelo de datos
     * <p>
     * [EN]  Insert data model
     *
     * @param source modelo de datos
     */
    public void setSource(DialogProgressModel source) {
        super.setDialogModel(source);
        this.source = source;
    }
}
