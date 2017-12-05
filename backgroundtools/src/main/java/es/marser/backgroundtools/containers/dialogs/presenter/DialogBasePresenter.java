package es.marser.backgroundtools.containers.dialogs.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.bindingadapters.BinderContainer;
import es.marser.backgroundtools.containers.dialogs.model.ButtonsSetModel;
import es.marser.backgroundtools.containers.dialogs.model.ClosableView;
import es.marser.backgroundtools.containers.dialogs.model.DialogModel;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.backgroundtools.handlers.WindowAction;
import es.marser.backgroundtools.listables.base.presenter.BasePresenter;

import static es.marser.backgroundtools.enums.DialogIcon.DEFAULT_ICON;
import static es.marser.backgroundtools.enums.DialogIcon.ICON_EXTRA;

/**
 * @author sergio
 *         Created by sergio on 5/12/17.
 *         Presentador de vista de cuadros de dialogo
 *         <p>
 *         [EN]  Presenter of view of dialog boxes
 */

public class DialogBasePresenter extends BasePresenter implements WindowAction {
    /*Variable modelo [EN]  Model variable*/
    protected DialogModel model;
    /*Variable modelo de configurador de botonera [EN]  Variable button configurator model*/
    protected ButtonsSetModel buttonsSetModel;
    /*Variable para cierre de contenedores [EN]  Variable for container closure*/
    protected ClosableView closableView;
    /*Variable de argumentos [EN]  Variable arguments*/
    private Bundle arguments;

    /*Variable de la vista principal [EN]  Variable of the main view*/
    private int dialogLayout;


    //CONSTRUCTORS___________________________________________________________
    public DialogBasePresenter(@NonNull Context context) {
        super(context);
        this.model = new DialogModel();
        this.buttonsSetModel = new ButtonsSetModel();
    }

    public DialogBasePresenter(@NonNull Context context, int dialogLayout) {
        this(context);
        this.dialogLayout = dialogLayout;
    }

    /**
     * Indicador del conmienzo de la vinculación de vistas {@link ViewDataBinding}
     * <p>
     * [EN]  Join linking view indicator
     *
     * @param binderContainer Objeto de enlace de vistas [EN]  View link object
     */
    @Override
    public void onBindObjects(@NonNull BinderContainer binderContainer) {
        super.onBindObjects(binderContainer);
        binderContainer.bindObject(BR.model, model);
        binderContainer.bindObject(BR.winaction, this);
        binderContainer.bindObject(BR.buttonsetmodel, buttonsSetModel);
    }

    //VARIABLES______________________________________________________________
    public DialogModel getModel() {
        return model;
    }

    public ClosableView getClosableView() {
        return closableView;
    }

    public void setClosableView(ClosableView closableView) {
        this.closableView = closableView;
    }

    @Nullable
    public Bundle getArguments() {
        return arguments;
    }

    public void setArguments(@Nullable Bundle args) {
        this.arguments = arguments;
        DialogIcon dialog_icon = null;

        if (args != null) {
            dialog_icon = (DialogIcon) args.getSerializable(ICON_EXTRA.name());
        }
        model.icon.set(dialog_icon != null ? dialog_icon : DEFAULT_ICON);
    }

    public int getDialogLayout() {
        return dialogLayout;
    }

    public void setDialogLayout(int dialogLayout) {
        this.dialogLayout = dialogLayout;
    }

    //WINDOWACTION_________________________________________________________
    /**
     * Acción aceptar
     * <p>
     * [EN]  Accept action
     *
     * @param v Vista pulsada [EN]  Pulsed view
     */
    @Override
    public void onOk(View v) {

    }

    /**
     * Acción cancelar
     * <p>
     * [EN]  Action cancel
     *
     * @param v Vista pulsada [EN]  Pulsed view
     */
    @Override
    public void onCancel(View v) {

    }

    /**
     * Acción en opción
     * <p>
     * [EN] Action in option
     *
     * @param v Vista pulsada [EN]  Pulsed view
     */
    @Override
    public void onOption(View v) {

    }
}
