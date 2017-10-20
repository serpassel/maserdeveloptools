package es.marser.backgroundtools.dialogs.bases;

import android.app.AlertDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;

import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.dialogs.model.ButtonsSetModel;
import es.marser.backgroundtools.dialogs.model.DialogModel;
import es.marser.backgroundtools.dialogs.model.StatusModel;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.tools.TextTools;

import static es.marser.backgroundtools.enums.DialogIcon.DEFAULT_ICON;
import static es.marser.backgroundtools.enums.DialogIcon.ICON_EXTRA;

/**
 * @author sergio
 *         Created by Sergio on 06/09/2017.
 *         Base de construcción de Dialogos personalizados. Patrón de diseño MVP
 *         <p>
 *         Para que la librería funcione activar Biblioteca de vinculación de datos de android, en el módulo de la app
 *         <p>
 *         <p>
 *         <p>
 *         [EN]  Custom Dialogos building base. [EN]  MVP design pattern
 *         <p>
 *         In order for the library to work activate android Databinding Library, in the module of the app
 *         <p>
 *         android {...
 *         dataBinding{
 *         enabled = true
 *         }
 *         }
 * @see BaseDialog
 */

@SuppressWarnings({"unused", "EmptyMethod", "UnusedReturnValue"})
public abstract class BaseCustomBinDialog extends BaseDialog {
    /*Vista Controladora [EN]  Controller View*/
    protected ViewDataBinding viewDataBinding;

    /*Variable modelo [EN]  Model variable*/
    protected DialogModel model;

    /*Variable modelo de configurador de botonera [EN]  Variable button configurator model*/
    protected ButtonsSetModel buttonsSetModel;

    /*Variable modelo de estado de vistas [EN]  View State Model Variable*/
    protected StatusModel statusModel;


    public BaseCustomBinDialog() {
        this.model = new DialogModel();
        this.buttonsSetModel = new ButtonsSetModel();
        this.statusModel = new StatusModel();
    }

    @Override
    protected void createDialog() {

        if (getArguments() != null) {
            DialogIcon dialog_icon = (DialogIcon) getArguments().getSerializable(ICON_EXTRA.name());
            model.icon.set(dialog_icon != null ? dialog_icon : DEFAULT_ICON);
        }
        buildDialog();
    }

    /**
     * Métodos e inicio de variables previas a la construcción del dialogo. Opcional
     * <p>
     * [EN]  Methods and start of variables prior to the construction of the dialogue.  Optional
     */
    protected void preBuild() {
    }

    /**
     * Construcción del cuadro de dialogo
     * <ul>
     * <il>Pre-creación [EN]  Pre-creation</il>
     * <il>Crear Dialogo [EN]  Create Dialogo</il>
     * <il>Enlazar modelo a la vista [EN]  Link model to view</il>
     * <il>Post-creación [EN]  Post-creation</il>
     * </ul>
     * [EN]  Construction of the dialog box
     */
    @SuppressWarnings("ConstantConditions")
    private void buildDialog() {
        preBuild();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewDataBinding = DataBindingUtil.inflate(inflater, getDialogLayout(), null, false);
        view = viewDataBinding.getRoot();

        builder.setView(view);
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        bindObject();
        postBuild();
    }

    /**
     * Métodos e inicio de variables posteriores a la construcción del dialogo. Opcional
     * <p>
     * [EN]  Methods and start of variables after the construction of the dialogue.  Optional
     */
    protected void postBuild() {
    }

    /**
     * Enlace de la vista. Obligatorio que la variable de modelo en la vista se denomine model
     * <p>
     * [EN]  View link.  Required for the model variable in the view to be named model
     */
    protected void bindObject() {
        viewDataBinding.setVariable(BR.model, model);
        viewDataBinding.executePendingBindings();

        viewDataBinding.setVariable(BR.buttonsetmodel, buttonsSetModel);
        viewDataBinding.executePendingBindings();

        viewDataBinding.setVariable(BR.statusmodel, statusModel);
        viewDataBinding.executePendingBindings();
    }

    /**
     * Recuperación de la vista. Obligatorio
     * [EN]  Recovery of sight.  required
     *
     * @return R.layout.view
     */
    protected abstract int getDialogLayout();


    /**
     * Optiene la variable del modelo de la vista
     * <p>
     * [EN]  Opt the view model variable
     *
     * @return Modelo de la vista [EN]  View model
     */
    public DialogModel getDialogModel() {
        return model;
    }

    /**
     * Recupera la variable del modelo
     * <p>
     * [EN]  Retrieve the model variable
     *
     * @param dialogProgressModel variable de modelo [EN]  model variable
     */
    public void setDialogModel(DialogModel model) {
        this.model = model;
    }

    /**
     * Recuperar la variable de configuración de la botonera
     * <p>
     * [EN]  Retrieve the button configuration variable
     *
     * @return Modelo de la botonera [EN]  Model of the keypad
     */
    public ButtonsSetModel getButtonsSetModel() {
        return buttonsSetModel;
    }

    /**
     * Introducir la variable de modelo de botonera
     * <p>
     * [EN]  Enter the button model variable
     *
     * @param buttonsSetModel Variable de modelo de botonera [EN]  Keyboard model variable
     */
    public void setButtonsSetModel(ButtonsSetModel buttonsSetModel) {
        this.buttonsSetModel = buttonsSetModel;
    }

    /**
     * Inserta el título del dialogo
     * <p>
     * [EN]  Insert the title of the dialog
     *
     * @param msg por defecto Cargando... [EN]  by default Loading ...
     * @return clase actual [EN]  current class
     */
    public BaseCustomBinDialog setTitle(String msg) {
        model.title.set(TextTools.nc(msg));
        return this;
    }

    /**
     * Inserta mensaje secundario
     * <p>
     * [EN]  Insert secondary message
     *
     * @param msg datos temporales [EN]  temporary data
     * @return clase actual [EN]  current class
     */
    public BaseCustomBinDialog setBody(String msg) {
        model.body.set(TextTools.nc(msg));
        return this;
    }
}
