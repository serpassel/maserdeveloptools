package es.marser.backgroundtools.dialogs.progress;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.dialogs.bases.BaseCustomBinDialog;
import es.marser.backgroundtools.dialogs.model.DialogProgressModel;
import es.marser.tools.TextTools;

/**
 * @author sergio
 *         Created by Sergio on 05/09/2017.
 *         Cuadro de progreso indeterminado personalizado MVP
 *         <p>
 *         [EN]  MVP Custom Indeterminate Progress Chart
 * @see es.marser.backgroundtools.dialogs.bases.BaseCustomBinDialog
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public class CustomInterminateBinDialog extends BaseCustomBinDialog {
    public static final String MODE_BOX_EXTRA = "mode_box_extra";
    public static final String MODE_SPINNER_EXTRA = "mode_spinner_extra";

    /**
     * Crear una nueva instancia del Dialogo
     * <p>
     * [EN]  Error Text to Display
     *
     * @param bundle Icono de la aplicación, modo, ejecución [EN]  Application icon, mode, execution
     * @return nueva instancia [EN]  new instance
     * @see #createBundle(String)
     */

    public static CustomInterminateBinDialog newInstace(Context context, Bundle bundle) {
        CustomInterminateBinDialog instance = new CustomInterminateBinDialog();
        instance.setContext(context);
        if (bundle == null) {
            bundle = createBundle(DIALOG_ICON.DEFAULT_ICON);
        }
        instance.setArguments(bundle);
        return instance;
    }

    /**
     * Utilidad de creación de los argumentos de iniciales
     * <p>
     * [EN]  Arguments creation utility
     *
     * @param icon Nombre de icono [EN]  Arguments creation utility
     * @return Bundle construido [EN]  Built Bundle
     * @see DialogProgressModel#BC3_ICON
     * @see DialogProgressModel#PDF_ICON
     * @see DialogProgressModel#EXCEL_ICON
     * @see DialogProgressModel#DATABASE_ICON
     * @see DialogProgressModel#LOADING_ICON
     * @see DialogProgressModel#CALC_ICON
     * @see #MODE_BOX_EXTRA
     * @see #MODE_SPINNER_EXTRA
     */
    public static Bundle createBundle(DIALOG_ICON icon) {
        return createBundle(icon, MODE_BOX_EXTRA);
    }

    public static Bundle createBundle(DIALOG_ICON icon, String mode) {
        Log.d("BACK$", "CREAR BUNDLE");
        Bundle bundle = new Bundle();

        if(icon == null){
            bundle.putSerializable(DIALOG_ICON.ICON_EXTRA.name(), DIALOG_ICON.DEFAULT_ICON);
            bundle.putString(MODE_BOX_EXTRA, MODE_SPINNER_EXTRA);
            Log.d("BACK$", "NULO");
        }else{
            Log.d("BACK$", "NO NULO");
            bundle.putSerializable(DIALOG_ICON.ICON_EXTRA.name(), icon);
            bundle.putString(MODE_BOX_EXTRA, TextTools.notEmpty(mode, MODE_BOX_EXTRA));
        }
        return bundle;
    }

    @Override
    protected int getDialogLayout() {
        switch (getArguments().getString(MODE_BOX_EXTRA, MODE_BOX_EXTRA)) {
            case MODE_BOX_EXTRA:
                return R.layout.mvp_dialog_indeterminate;
            default:
                return R.layout.mvp_spinner;

        }
    }
}
