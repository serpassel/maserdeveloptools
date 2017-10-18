package es.marser.backgroundtools.dialogs.bases;

import android.app.AlertDialog;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.widget.ImageView;

import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.dialogs.model.DialogProgressModel;
import es.marser.tools.TextTools;

import static es.marser.backgroundtools.dialogs.bases.BaseDialog.DIALOG_ICON.*;

/**
 * @author sergio
 *         Created by Sergio on 06/09/2017.
 *         Base de construcción de Dialogos personalizados. Patrón de diseño MVC
 *         <p>
 *         Para que la librería funcione activar Biblioteca de vinculación de datos de android, en el módulo de la app
 *         <p>
 *         <p>
 *         <p>
 *         [EN]  Custom Dialogos building base. [EN]  MVC design pattern
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
    protected DialogProgressModel source;


    public BaseCustomBinDialog() {
        this.source = new DialogProgressModel();
    }

    @Override
    protected void createDialog() {

        if (getArguments() != null) {
            DIALOG_ICON dialog_icon = (DIALOG_ICON) getArguments().getSerializable(ICON_EXTRA.name());
            source.icon.set(dialog_icon != null ? dialog_icon : DEFAULT_ICON);
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
        viewDataBinding.setVariable(BR.model, source);
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
    public DialogProgressModel getDialogProgressObject() {
        return source;
    }

    /**
     * Recupera la variable del modelo
     * <p>
     * [EN]  Retrieve the model variable
     *
     * @param dialogProgressModel variable de modelo [EN]  model variable
     */
    public void setDialogProgressObject(DialogProgressModel dialogProgressModel) {
        this.source = dialogProgressModel;
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
        source.title.set(TextTools.nc(msg));
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
    public BaseCustomBinDialog setTemp(String msg) {
        source.temp.set(TextTools.nc(msg));
        return this;
    }

    @BindingAdapter(value = {"iconDialog"})
    public static void setDialogIcon(ImageView view, BaseDialog.DIALOG_ICON icon) {
        switch (icon) {
            case BC3_ICON:
                view.setImageResource(R.drawable.ic_bc3);
                break;
            case EXCEL_ICON:
                view.setImageResource(R.drawable.ic_xls);
                break;
            case PDF_ICON:
                view.setImageResource(R.drawable.ic_pdf);
                break;
            case DATABASE_ICON:
                view.setImageResource(R.drawable.ic_database);
                break;
            case CALC_ICON:
                view.setImageResource(R.drawable.ic_calculator);
                break;
            case WARNING_ICON:
                view.setImageResource(R.drawable.ic_warning);
                break;
            case ERROR_ICON:
                view.setImageResource(R.drawable.ic_error);
                break;
            case INFORMATION_ICON:
                view.setImageResource(R.drawable.ic_information);
                break;
            case ADD_ICON:
                view.setImageResource(R.drawable.ic_add);
                break;
            case EDIT_ICON:
                view.setImageResource(R.drawable.ic_edit);
                break;
            case QUESTION_ICON:
                view.setImageResource(R.drawable.ic_question);
                break;
            case LOADING_ICON:
            default:
                view.setImageResource(R.drawable.ic_sand_clock);
                break;
        }
    }

}
