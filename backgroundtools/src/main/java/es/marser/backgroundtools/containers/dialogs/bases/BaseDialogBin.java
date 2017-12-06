package es.marser.backgroundtools.containers.dialogs.bases;

import android.app.AlertDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.Window;

import es.marser.backgroundtools.bindingadapters.BinderContainer;
import es.marser.backgroundtools.containers.dialogs.presenter.DialogBasePresenter;
import es.marser.tools.TextTools;

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
public abstract class BaseDialogBin<DBP extends DialogBasePresenter>
        extends BaseDialog
        implements BinderContainer {

    /*Vista Controladora [EN]  Controller View*/
    protected ViewDataBinding viewDataBinding;

    protected DBP presenter;

    @Override
    protected void createDialog() {
        preBuild();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            viewDataBinding = DataBindingUtil.inflate(inflater, getDialogLayout(), null, false);
        }
        view = viewDataBinding.getRoot();

        builder.setView(view);
        dialog = builder.create();

        Window w = dialog.getWindow();

        if (w != null) {
            w.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
        bindObject();
        postBuild();
    }

    /**
     * Métodos e inicio de variables previas a la construcción del dialogo. Opcional
     * <p>
     * [EN]  Methods and start of variables prior to the construction of the dialogue.  Optional
     */
    protected void preBuild() {
    }

    /**
     * Métodos e inicio de variables posteriores a la construcción del dialogo y a la vinculación de datos {@link #bindObject()}. Opcional
     * <p>
     * Methods and start of variables after the construction of the dialogue and the data link {@link #bindObject()}
     */
    protected void postBuild() {
    }

    /**
     * Enlace de objetos en la vista principal. Obligatorio que la variable de modelo en la vista se denomine model
     * <p>
     * Se ejecuta antes de {@link #postBuild()} y despueś de {@link #preBuild()} y de la creación del diálogo
     * <p>
     * [EN]  Link objects in the main view.  Obligatory that the model variable in the view is called model
     * It runs before {@link #postBuild()} and after {@link #preBuild()} and the creation of dialogue
     */
    protected void bindObject() {
        if (presenter != null) {
            presenter.onBindObjects(this);
        }
    }

    /**
     * Inserta el título del dialogo
     * <p>
     * [EN]  Insert the title of the dialog
     *
     * @param msg por defecto Cargando... [EN]  by default Loading ...
     * @return clase actual [EN]  current class
     */
    public BaseDialogBin setTitle(String msg) {
        if (presenter != null && presenter.getModel() != null) {
            presenter.getModel().title.set(TextTools.nc(msg));
        }
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
    public BaseDialogBin setBody(String msg) {
        if (presenter != null && presenter.getModel() != null) {
            presenter.getModel().body.set(TextTools.nc(msg));
        }
        return this;
    }


    //VARIABLES___________________________________________________________
    public DBP getPresenter() {
        return presenter;
    }

    public void setPresenter(DBP presenter) {
        this.presenter = presenter;
        this.presenter.setClosableView(this);
    }

    //BINDER CONTAINER_____________________________________________________
    @Override
    public void bindObject(int var, @NonNull Object obj) {
        viewDataBinding.setVariable(var, obj);
        viewDataBinding.executePendingBindings();
    }

    @Override
    protected int getDialogLayout() {
        return presenter != null ? presenter.getViewLayout() : -1;
    }
}
