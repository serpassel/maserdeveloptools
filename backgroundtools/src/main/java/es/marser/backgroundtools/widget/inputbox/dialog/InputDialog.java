package es.marser.backgroundtools.widget.inputbox.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.dialogs.bases.BaseDialogBin;
import es.marser.backgroundtools.containers.dialogs.presenter.BundleBuilder;
import es.marser.backgroundtools.containers.dialogs.task.OnResult;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.backgroundtools.widget.inputbox.model.BoxModel;
import es.marser.backgroundtools.widget.inputbox.presenter.InputPresenter;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by sergio on 7/11/17.
 *         Cuadro de dialogo para insertar textos
 *         <p>
 *         [EN]  Dialog box to insert texts
 */

@SuppressWarnings("unused")
public class InputDialog extends BaseDialogBin<InputPresenter> {

    //INSTANCE____________________________________________________________________
    public static InputDialog newInstance(
            @NonNull Context context,
            @NonNull Bundle bundle,
            @NonNull OnResult<String> result) {

        /*PRESENTER*/
        InputPresenter presenter = new InputPresenter(context);
        presenter.setResult(result);
        presenter.setArguments(bundle);
        
        /*DIALOG*/
        InputDialog instance = new InputDialog();
        instance.setContext(context);
        instance.setPresenter(presenter);
        return instance;
    }


    //BUNDLES BUILDER_______________________________________________________________
    public static Bundle createBundle(@NonNull Context context, String hint) {
        return createBundle(context, null, hint);
    }

    /**
     * Creación de argumentos
     * <p>
     * [EN]  Creating arguments
     *
     * @param icon        Icono de cabecera [EN]  Header icon
     * @param title       Título de cabecera [EN]  Header title
     * @param boxModel Configuración de la caja de texto [EN]  Text box settings
     * @return Argumentos de configuración [EN]  Configuration arguments
     */
    public static Bundle createBundle(@NonNull Context context,
                                      @NonNull DialogIcon icon,
                                      String title,
                                      @NonNull BoxModel boxModel) {
        Bundle bundle = new Bundle();
       
        /*DIALOG MODEL*/
        bundle.putAll(BundleBuilder.createDialogModelBundle(icon, title, null, null));
        
        /*BUTTON SET MODEL*/
        bundle.putAll(BundleBuilder.createButtonSetModelBundle(
                context.getResources().getString(R.string.bt_ACTION_OK),
                context.getResources().getString(R.string.bt_ACTION_CANCEL),
                null));
        
        /*BOX SETTINGS*/
        bundle.putParcelable(DialogExtras.SETTING_INPUTBOX_EXTRA.name(), boxModel);

        return bundle;
    }

    public static Bundle createPasswordBundle(@NonNull Context context, String title, int passlength) {
       /*settings*/
        BoxModel boxModel = new BoxModel(passlength);
        boxModel.setHint("Contraseña");
        return createBundle(
                context,
                DialogIcon.PASSWORD_ICON,
                TextTools.nc(title, "Introducir contraseña"),
                boxModel
        );
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
    public static Bundle createBundle(@NonNull Context context, String title, int lines, String hint, int counter) {
        BoxModel boxModel = new BoxModel(lines, hint);
        boxModel.setCounterCount(counter);
        if (lines > 1) {
            return createBundle(context, DialogIcon.MULTILINE_ICON, TextTools.nc(title, "entrada"), boxModel);
        }
        return createBundle(context, DialogIcon.EDITTEXT_ICON, TextTools.nc(title, "entrada"), boxModel);
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
    public static Bundle createBundle(@NonNull Context context, String title, String hint) {
        return createBundle(context, title, 1, hint, 0);
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
    public static Bundle createMailBundle(@NonNull Context context, String title, String hint) {
        /*settings*/
        BoxModel boxModel = new BoxModel(hint);
        boxModel.setInputType(BoxModel.textEmailAddress);
        return createBundle(context, DialogIcon.MAIL_ICON, TextTools.nc(title, "Introducir correo electrónico"), boxModel);
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
    public static Bundle createNumberBundle(@NonNull Context context, String title, String hint) {
        /*settings*/
        BoxModel boxModel = new BoxModel(hint);
        boxModel.setInputType(BoxModel.number);
        return createBundle(context, DialogIcon.EDITTEXT_ICON, TextTools.nc(title, "entrada"), boxModel);
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
    public static Bundle createNumberDecimalBundle(@NonNull Context context, String title, String hint) {
        /*settings*/
        BoxModel boxModel = new BoxModel(hint);
        boxModel.setInputType(BoxModel.numberDecimal);
        return createBundle(context, DialogIcon.EDITTEXT_ICON, TextTools.nc(title, "entrada"), boxModel);
    }
}
