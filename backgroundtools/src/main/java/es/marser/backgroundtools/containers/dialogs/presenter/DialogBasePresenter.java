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


    //CONSTRUCTORS___________________________________________________________
    public DialogBasePresenter(@NonNull Context context, int viewLayout) {
        super(context, viewLayout);
        this.model = new DialogModel();
        this.buttonsSetModel = new ButtonsSetModel();
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

    public void setClosableView(ClosableView closableView) {
        this.closableView = closableView;
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        DialogIcon dialog_icon = null;

        if (args != null) {
            dialog_icon = (DialogIcon) args.getSerializable(ICON_EXTRA.name());
        }
        model.icon.set(dialog_icon != null ? dialog_icon : DEFAULT_ICON);
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
