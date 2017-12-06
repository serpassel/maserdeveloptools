package es.marser.backgroundtools.widget.progress;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author sergio
 *         Created by sergio on 6/12/17.
 *         Definición de clase actualizadora de barras de progreso
 *         <p>
 *         [EN]  Definition of progress bar update class
 */

@SuppressWarnings("unused")
public interface ProgressBarUpdater {
    /**
     * Introducir el valor máximo de la barra de progreso
     * <p>
     * [EN]  Enter the maximum value of the progress bar
     *
     * @param max valor máximo [EN]  maximum value
     */
    void setMax(@Nullable Integer max);

    /**
     * Indicar si la barra es de progreso o indeterminada
     *
     * @param value verdadero indeterminada [EN]  true indeterminate
     */
    void indeterminate(boolean value);

    /**
     * Indicar el progreso
     * <p>
     * [EN]  true indeterminate
     *
     * @param value valor absoluto del progreso [EN]  absolute value of progress
     */
    void setProgress(@NonNull Integer value);

    /**
     * Incrementa el progreso de la barra en una cantidad determinada
     * <p>
     * [EN]  Increases the progress of the bar by a certain amount
     *
     * @param value valor a incrementar el progreso [EN]  value to increase progress
     */
    void increment(@NonNull Integer value);

    /**
     * Incrementa el progreso de la barra en una unidad
     * <p>
     * [EN]  Increases the progress of the bar in a unit
     *
     */
    void autoIncrement();

    /**
     * Agregar línea de error al dialogo de progreso
     * <p>
     * [EN]  Add error line to progress dialog
     *
     * @param error Texto de error a mostrar [EN]  Error Text to Display
     */
    void addError(@NonNull String error);
}
