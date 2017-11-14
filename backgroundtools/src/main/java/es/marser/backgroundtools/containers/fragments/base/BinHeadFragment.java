package es.marser.backgroundtools.containers.fragments.base;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author sergio
 *         Created by sergio on 14/11/17.
 *         Definición de fragment con elemento de cabecera
 *         <p>
 *         [EN]  Definition of fragment with header element
 */

public interface BinHeadFragment<T extends Parcelable> {
    //INSTANTIATE VARIABLES______________________________________________________________________________

    /**
     * Crear una instancia para el objeto modelo {@link #instanceVariables()}
     * <p>
     * [EN]  Create an instance for the model object
     *
     * @return Nuevo objeto genérico [EN]  New generic object
     * <code>
     * <p>
     * </code>
     */
    @NonNull
    T getNewModelInstance();


    //LINK VARIABLES______________________________________________________________________________________

    /**
     * Enlaza el objeto de cabecera con el presentador,
     * {@link  #OnCreateView} en el método OnCreateView
     * <p>
     * [EN]  Binds the header object with the presenter
     * <code> {
     * <p>
     * if (savedInstanceState != null) {
     * model = savedInstanceState.getParcelable(bundle_model_table_key);
     * }
     * <p>
     * if (model == null) {
     * model = getNewModelInstance();
     * }
     * if (getModel() == null) {
     * setModel(model);
     * }
     * }</code>
     */
    void binObjects(@Nullable Bundle savedInstanceState);

    /**
     * Asignación del objeto genérico de cabecera
     * {@link #binObjectsl()}
     * <p>
     * [EN]  Assigning the generic header object
     *
     * @param model Objeto modelo [EN]  Model object
     *              <code> {
     *              if (model != null) {
     *              this.model = model;
     *              viewDataBinding.setVariable(BR.model, model);
     *              viewDataBinding.executePendingBindings();
     *              }
     *              }</code>
     */
    void setModel(T model);

    /**
     * Devuelve el Objeto genérico de cabecera
     * <p>
     * [EN]  Returns the generic Header Object
     *
     * @return Objeto genérico de cabecera [EN]  Generic Header Object
     * <code> {
     * return model;
     * }</code>
     */
    T getModel();
}
