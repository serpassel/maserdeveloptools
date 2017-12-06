package es.marser.backgroundtools.containers.dialogs.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.bindingadapters.BinderContainer;
import es.marser.backgroundtools.containers.dialogs.model.ButtonsSetModel;
import es.marser.backgroundtools.containers.dialogs.model.ClosableView;
import es.marser.backgroundtools.containers.dialogs.model.DialogModel;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.backgroundtools.handlers.WindowAction;
import es.marser.backgroundtools.listables.base.presenter.BasePresenter;
import es.marser.backgroundtools.listables.base.presenter.SharedPreferendSaved;
import es.marser.backgroundtools.systemtools.SharedPreferenceTools;
import es.marser.tools.TextTools;

import static es.marser.backgroundtools.enums.DialogIcon.DEFAULT_ICON;

/**
 * @author sergio
 *         Created by sergio on 5/12/17.
 *         Presentador de vista de cuadros de dialogo
 *         <p>
 *         [EN]  Presenter of view of dialog boxes
 */

public class DialogBasePresenter extends BasePresenter implements WindowAction, SharedPreferendSaved {
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
            //DIALOG MODEL_____________________________________________________________
            dialog_icon = (DialogIcon) args.getSerializable(DialogExtras.ICON_EXTRA.name());
            model.title.set(getArguments().getString(DialogExtras.TITLE_EXTRA.name(), ""));
            model.body.set(getArguments().getString(DialogExtras.BODY_EXTRA.name(), ""));

            String key = getArguments().getString(DialogExtras.KEY_EXTRA.name());

            if (!TextTools.isEmpty(key)) {
                model.keyname.set(key);
                model.key.set(SharedPreferenceTools.getBoolean(getContext(), false, BundleBuilder.sharedBox(getContext()), key));
            }

            //BUTTON SET MODEL________________________________________________________
            String ok_name = getArguments().getString(DialogExtras.OK_EXTRA.name());
            String cancel_name = getArguments().getString(DialogExtras.CANCEL_EXTRA.name());
            String option_name = getArguments().getString(DialogExtras.OPTION_EXTRA.name());

            if (ok_name != null) {
                buttonsSetModel.ok_name.set(ok_name);
            }

            if (cancel_name != null) {
                buttonsSetModel.cancel_name.set(cancel_name);
            }

            if (option_name != null) {
                buttonsSetModel.option_name.set(option_name);
            }
        }
        model.icon.set(dialog_icon != null ? dialog_icon : DEFAULT_ICON);
    }

    //PREFERENCES_____________________________________________________________
    /**
     * Graba la selección de preferencias
     * <p>
     * [EN]  Record preferences selection
     */
    @Override
    public void savePreferences() {
        SharedPreferenceTools.setBoolean(getContext(),
                model.key.get(),
                getContext().getResources().getString(R.string.bt_dialog_functionality_preferences),
                model.keyname.get());
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
