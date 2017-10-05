package es.marser.backgroundtools.dialogs;

import android.content.Context;
import android.os.Bundle;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.dialogs.bases.BaseCustomBinDialog;
import es.marser.backgroundtools.dialogs.model.DialogProgressModel;

/**
 * @author sergio
 *         Created by Sergio on 05/09/2017.
 *         Cuadro de progreso indeterminado personalizado MVP
 *         <p>
 *         [EN]  MVP Custom Indeterminate Progress Chart
 *
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public class CustomInterminateBinDialog extends BaseCustomBinDialog {

    /**
     * Crear una nueva instancia del Dialogo
     * <p>
     * [EN]  Error Text to Display
     *
     * @param bundle Icono de la aplicación
     * @return nueva instancia
     * @see #createBundle(String)
     */

    public static CustomInterminateBinDialog newInstace(Context context, Bundle bundle){
    CustomInterminateBinDialog instance = new CustomInterminateBinDialog();
        instance.setContext(context);
       if(bundle !=null) {
           bundle = createBundle(DEFAULT_ICON);
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
     */
    public static Bundle createBundle(String icon){
        Bundle bundle = new Bundle();
        bundle.putString(ICON_EXTRA, icon);
        return bundle;
    }

    @Override
    protected int getDialogLayout() {
        return 0;
    }
}
