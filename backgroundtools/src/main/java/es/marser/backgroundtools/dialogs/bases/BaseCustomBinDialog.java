package es.marser.backgroundtools.dialogs.bases;

import android.app.AlertDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;

import es.marser.TextTools;
import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.dialogs.DialogProgressModel;

/**
 * @author sergio
 *         Created by Sergio on 06/09/2017.
 *         Base de construcción de Dialogos personalizados. Patrón de diseño MVC
 *         <p>
 *         [EN]  Custom Dialogos building base. [EN]  MVC design pattern
 */

@SuppressWarnings({"unused", "EmptyMethod"})
public abstract class BaseCustomBinDialog extends BaseDialog {
    protected ViewDataBinding viewDataBinding;

    protected DialogProgressModel source;

    public static final String BC3_ICON = DialogProgressModel.BC3_ICON;
    public static final String EXCEL_ICON = DialogProgressModel.EXCEL_ICON;
    public static final String PDF_ICON = DialogProgressModel.PDF_ICON;
    public static final String LOADING_ICON = DialogProgressModel.LOADING_ICON;
    public static final String DEFAULT_ICON = DialogProgressModel.LOADING_ICON;

    public static final String ICON_EXTRA = "icon_extra";

    public BaseCustomBinDialog() {
        this.source = new DialogProgressModel();
    }

    @Override
    protected void createDialog() {

        if (getArguments() != null) {
            source.icon.set(getArguments().getString(ICON_EXTRA, DialogProgressModel.LOADING_ICON));
        }
        buildDialog();
    }

    protected void preBuild() {
    }

    /**
     * 1.-
     * Constuye el cuadro de dialogo
     */
    private void buildDialog() {
        preBuild();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewDataBinding = DataBindingUtil.inflate(inflater, getDialogLayout(), null, false);
        view = viewDataBinding.getRoot();

        builder.setView(view);
        dialog = builder.create();
        bindObject();
        postBuild();
    }

    protected void postBuild() {
    }

    protected void bindObject() {
        viewDataBinding.setVariable(BR.model, source);
        viewDataBinding.executePendingBindings();

    }

    protected abstract int getDialogLayout();


    public DialogProgressModel getDialogProgressObject() {
        return source;
    }

    public void setDialogProgressObject(DialogProgressModel dialogProgressModel) {
        this.source = dialogProgressModel;
    }

    /**
     * Inserta el título de la carga
     *
     * @param msg por defecto Cargando...
     */
    public BaseCustomBinDialog setTitle(String msg) {
        source.title.set(TextTools.nc(msg));
        return this;
    }

    /**
     * Inserta el título de la carga
     *
     * @param msg datos temporales
     */
    public BaseCustomBinDialog setTemp(String msg) {
        source.temp.set(TextTools.nc(msg));
        return this;
    }
}
