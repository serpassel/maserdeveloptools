package es.marser.backgroundtools.widget.progress.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.dialogs.bases.BaseDialogBin;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.backgroundtools.widget.progress.presenter.ProgressPresenter;

/**
 * @author sergio
 *         Created by Sergio on 05/09/2017.
 *         Cuadro de progreso indeterminado personalizado MVP
 *         <p>
 *         [EN]  MVP Custom Indeterminate Progress Chart
 * @see BaseDialogBin
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public class IndeterminateDialog extends BaseDialogBin<ProgressPresenter> {

    /**
     * Crear una nueva instancia del Dialogo
     * <p>
     * [EN]  Error Text to Display
     *
     * @param bundle Icono de la aplicación, modo, ejecución [EN]  Application icon, mode, execution
     * @return nueva instancia [EN]  new instance
     * @see #createBundle(String)
     */

    public static IndeterminateDialog newInstance(@NonNull Context context,
                                                  @Nullable Bundle bundle,
                                                  @NonNull DialogExtras modeextras) {
        if (bundle == null) {
            bundle = createBundle(DialogIcon.DEFAULT_ICON);
        }
        bundle.putString(DialogExtras.TITLE_EXTRA.name(), context.getResources().getString(R.string.bt_loading));

        /*PRESENTER*/
        ProgressPresenter presenter;

        switch (modeextras) {
            case MODE_BOX_EXTRAS:
                presenter = new ProgressPresenter(context, R.layout.mvp_dialog_indeterminate);
                break;
            case MODE_SPINNER_EXTRAS:
            default:
                presenter = new ProgressPresenter(context, R.layout.mvp_spinner);
                break;
        }
        presenter.setArguments(bundle);

        /*DIALOG*/
        IndeterminateDialog instance = new IndeterminateDialog();
        instance.setContext(context);
        instance.setPresenter(presenter);

        return instance;
    }


    /**
     * Utilidad de creación de los argumentos de iniciales
     * <p>
     * [EN]  Arguments creation utility
     *
     * @param icon Nombre de icono [EN]  Arguments creation utility
     * @return Bundle construido [EN]  Built Bundle
     */
    public static Bundle createBundle(@Nullable DialogIcon icon) {
        Bundle bundle = new Bundle();

        if (icon == null) {
            bundle.putSerializable(DialogIcon.ICON_EXTRA.name(), DialogIcon.DEFAULT_ICON);
        } else {
            //Log.d("BACK$", "NO NULO");
            bundle.putSerializable(DialogIcon.ICON_EXTRA.name(), icon);
        }
        return bundle;
    }
}
