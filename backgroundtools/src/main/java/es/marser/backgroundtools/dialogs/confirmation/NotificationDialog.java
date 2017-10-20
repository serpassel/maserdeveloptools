package es.marser.backgroundtools.dialogs.confirmation;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.R;

import es.marser.backgroundtools.dialogs.bases.BaseCustomBinDialog;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.backgroundtools.enums.DialogModelExtras;
import es.marser.backgroundtools.handlers.WindowAction;

import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 19/10/17.
 *         Dialogos de notificaciones
 *         <p>
 *         [EN]  Notifications dialogs
 */

public class NotificationDialog extends BaseCustomBinDialog implements WindowAction {

    private static String body_example = "Android Open Source UI textview android justifiedtextview Android UI textview ufo22940268 android justifiedtextview htm Android Open Source UI textview android justifiedtextview Android Open Source UI textview justify textview android Android UI textview nikoo28 justify textview android htm Android Open Source UI textview justify textview android This is a simple implementation to get text in justified manner in any android application";

    /**
     * Crear una nueva instancia del Dialogo
     * <p>
     * [EN]  Error Text to Display
     *
     * @param bundle Argumentos del dialogo
     * @return nueva instancia
     * @see #createBundle(String)
     */
    public static NotificationDialog newInstace(Context context, Bundle bundle) {
        NotificationDialog instance = new NotificationDialog();
        instance.setContext(context);
        if (bundle == null) {
            bundle = createBundle(DialogIcon.DEFAULT_ICON);
        }
        instance.setArguments(bundle);
        return instance;
    }

    public static Bundle createBundle(DialogIcon icon, String title, String body) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DialogIcon.ICON_EXTRA.name(), icon);
        bundle.putString(DialogModelExtras.TITLE_EXTRA.name(), TextTools.nc(title, "Notificación"));
        bundle.putString(DialogModelExtras.BODY_EXTRA.name(), TextTools.nc(body));
        return bundle;
    }

    public static Bundle createBundle(DialogIcon icon) {
        return createBundle(icon, "Notificación", body_example);
    }

    @Override
    protected void postBuild() {
        super.postBuild();
        model.title.set(getArguments().getString(DialogModelExtras.TITLE_EXTRA.name(), "Notificación"));
        model.body.set(getArguments().getString(DialogModelExtras.BODY_EXTRA.name(), ""));

        buttonsSetModel.option_name.set("option");
    }

    @Override
    protected int getDialogLayout() {
        return R.layout.mvp_dialog_notification;
    }

    @Override
    protected void bindObject() {
        super.bindObject();
        viewDataBinding.setVariable(BR.winaction, this);
        viewDataBinding.executePendingBindings();
    }

    @Override
    public void onOk(View v) {

    }

    @Override
    public void onCancel(View v) {

    }

    @Override
    public void onOption(View v) {

    }
}
