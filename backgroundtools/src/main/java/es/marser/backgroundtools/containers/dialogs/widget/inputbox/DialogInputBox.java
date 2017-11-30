package es.marser.backgroundtools.containers.dialogs.widget.inputbox;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.dialogs.bases.BaseDialogBinModel;
import es.marser.backgroundtools.containers.dialogs.task.OnResult;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 7/11/17.
 *         Cuadro de dialogo para insertar textos
 *         <p>
 *         [EN]  Dialog box to insert texts
 */

@SuppressWarnings("unused")
public class DialogInputBox extends BaseDialogBinModel {

    protected OnResult<String> result;

    protected BoxSettings boxSettings;

    //INSTANCE____________________________________________________________________
    public static DialogInputBox newInstance(
            @NonNull Context context,
            @NonNull Bundle bundle,
            @NonNull OnResult<String> result) {

        DialogInputBox instance = new DialogInputBox();
        instance.setArguments(bundle);
        instance.setContext(context);
        instance.setResult(result);
        return instance;
    }

    //BUNDLE_____________________________________________________________________
    public static Bundle createBundle(String hint) {
        return createBundle(null, hint);
    }

    /**
     * Creación de argumentos
     * <p>
     * [EN]  Creating arguments
     *
     * @param icon        Icono de cabecera [EN]  Header icon
     * @param title       Título de cabecera [EN]  Header title
     * @param boxSettings Configuración de la caja de texto [EN]  Text box settings
     * @return Argumentos de configuración [EN]  Configuration arguments
     */
    public static Bundle createBundle(
            @NonNull DialogIcon icon,
            String title,
            @NonNull BoxSettings boxSettings) {
        Bundle bundle = new Bundle();
        /*Fixed*/
        bundle.putSerializable(DialogIcon.ICON_EXTRA.name(), icon);

        /*Variables*/
        bundle.putString(DialogExtras.TITLE_EXTRA.name(), TextTools.nc(title));

        /*settings*/
        bundle.putParcelable(DialogExtras.SETTING_INPUTBOX_EXTRA.name(), boxSettings);

        return bundle;
    }

    public static Bundle createPasswordBundle(String title, int passlength) {
       /*settings*/
        BoxSettings boxSettings = new BoxSettings(passlength);
        boxSettings.setHint("Contraseña");
        return createBundle(DialogIcon.PASSWORD_ICON, TextTools.nc(title, "Introducir contraseña"), boxSettings);
    }

    /**
     * Formación de argumentos para cuadro de texto largo
     * <p>
     * [EN]  Argument formation for long text box
     *
     * @param title   Título del cuadro [EN]  Title of the picture
     * @param lines   numéro de líneas para el cuadro [EN]  number of lines for the table
     * @param hint    Texto del cuadro de texto [EN]  Text box text
     * @param counter Máximo de caracteres admitdos [EN]  Maximum allowed characters
     * @return Argumentos [EN]  Arguments
     */
    public static Bundle createBundle(String title, int lines, String hint, int counter) {
        BoxSettings boxSettings = new BoxSettings(lines, hint);
        boxSettings.setCounterCount(counter);
        if (lines > 1) {
            return createBundle(DialogIcon.MULTILINE_ICON, TextTools.nc(title, "entrada"), boxSettings);
        }
        return createBundle(DialogIcon.EDITTEXT_ICON, TextTools.nc(title, "entrada"), boxSettings);
    }

    /**
     * Formación de argumentos para cuadro de texto corto
     * <p>
     * [EN]  Argument formation for short text box
     *
     * @param title Título del cuadro [EN]  Title of the picture
     * @param hint  Texto del cuadro de texto [EN]  Text box text
     * @return Argumentos [EN]  Arguments
     */
    public static Bundle createBundle(String title, String hint) {
        return createBundle(title, 1, hint, 0);
    }

    /**
     * Formación de argumentos para cuadro de texto de correo electrónico
     * <p>
     * [EN]  Argument formation for email text box
     *
     * @param title Título del cuadro [EN]  Title of the picture
     * @param hint  Texto del cuadro de texto [EN]  Text box text
     * @return Argumentos [EN]  Arguments
     */
    public static Bundle createMailBundle(String title, String hint) {
        /*settings*/
        BoxSettings boxSettings = new BoxSettings(hint);
        boxSettings.setInputType(BoxSettings.textEmailAddress);
        return createBundle(DialogIcon.MAIL_ICON, TextTools.nc(title, "Introducir correo electrónico"), boxSettings);
    }

    /**
     * Formación de argumentos para cuadro de texto numérico
     * <p>
     * [EN]  Argument formation for numeric text box
     *
     * @param title Título del cuadro [EN]  Title of the picture
     * @param hint  Texto del cuadro de texto [EN]  Text box text
     * @return Argumentos [EN]  Arguments
     */
    public static Bundle createNumberBundle(String title, String hint) {
        /*settings*/
        BoxSettings boxSettings = new BoxSettings(hint);
        boxSettings.setInputType(BoxSettings.number);
        return createBundle(DialogIcon.EDITTEXT_ICON, TextTools.nc(title, "entrada"), boxSettings);
    }

    /**
     * Formación de argumentos para cuadro de texto numérico
     * <p>
     * [EN]  Argument formation for numeric text box
     *
     * @param title Título del cuadro [EN]  Title of the picture
     * @param hint  Texto del cuadro de texto [EN]  Text box text
     * @return Argumentos [EN]  Arguments
     */
    public static Bundle createNumberDecimalBundle(String title, String hint) {
        /*settings*/
        BoxSettings boxSettings = new BoxSettings(hint);
        boxSettings.setInputType(BoxSettings.numberDecimal);
        return createBundle(DialogIcon.EDITTEXT_ICON, TextTools.nc(title, "entrada"), boxSettings);
    }


    //CREACIÓN____________________________________________________________________
    @Override
    protected int getDialogLayout() {
        return R.layout.mvp_dialog_inputbox;
    }

    @Override
    protected void preBuild() {
        if (getArguments() != null) {
            model.title.set(getArguments().getString(DialogExtras.TITLE_EXTRA.name(),
                    getContext().getResources().getString(R.string.bt_dialog_input_box_title)));
            boxSettings = getArguments().getParcelable(DialogExtras.SETTING_INPUTBOX_EXTRA.name());
        }

        if (boxSettings == null) {
            boxSettings = new BoxSettings();
        }

        //Configurar botones [EN]  Configure buttons
        buttonsSetModel.ok_name.set(context.getResources().getString(R.string.bt_ACTION_OK));
        buttonsSetModel.cancel_name.set(context.getResources().getString(R.string.bt_ACTION_CANCEL));
    }

    //BINDING______________________________________________________________________
    @Override
    protected void bindObject() {
        super.bindObject();
        viewDataBinding.setVariable(BR.settings, boxSettings);
        viewDataBinding.executePendingBindings();
    }

    //PRESENTERS________________________________________________________________
    @Override
    public void onOk(View v) {

        if (boxSettings.validate()) {
            result.onResult(DialogExtras.OK_EXTRA, boxSettings.getBody());
            close();
        }
    }

    @Override
    public void onCancel(View v) {
        result.onResult(DialogExtras.CANCEL_EXTRA, boxSettings.getBody());
        close();
    }

    //PROPERTIES_________________________________________________________________
    public OnResult<String> getResult() {
        return result;
    }

    public void setResult(OnResult<String> result) {
        this.result = result;
    }

    public BoxSettings getBoxSettings() {
        return boxSettings;
    }

    public void setBoxSettings(BoxSettings boxSettings) {
        this.boxSettings = boxSettings;
    }
}
