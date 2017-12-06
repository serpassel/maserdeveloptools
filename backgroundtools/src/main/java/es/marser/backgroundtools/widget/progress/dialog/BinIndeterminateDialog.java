package es.marser.backgroundtools.widget.progress.dialog;

import android.content.Context;
import android.os.Bundle;

import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.dialogs.bases.BaseDialogBin;
import es.marser.backgroundtools.widget.progress.presenter.ProgressPresenter;
import es.marser.backgroundtools.widget.progress.model.ProgressModel;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.DialogIcon;

/**
 * @author sergio
 *         Created by Sergio on 05/09/2017.
 *         Cuadro de progreso indeterminado personalizado MVP
 *         <p>
 *         [EN]  MVP Custom Indeterminate Progress Chart
 * @see BaseDialogBin
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public class BinIndeterminateDialog extends BaseDialogBin<ProgressPresenter> {
    private ProgressModel source;

    /**
     * Crear una nueva instancia del Dialogo
     * <p>
     * [EN]  Error Text to Display
     *
     * @param bundle Icono de la aplicación, modo, ejecución [EN]  Application icon, mode, execution
     * @return nueva instancia [EN]  new instance
     * @see #createBundle(String)
     */

    public static BinIndeterminateDialog newInstance(Context context, Bundle bundle) {
        BinIndeterminateDialog instance = new BinIndeterminateDialog();
        instance.setContext(context);
        if (bundle == null) {
            bundle = createBundle(DialogIcon.DEFAULT_ICON);
        }
        instance.setSource(new ProgressModel());
        instance.setTitle(context.getResources().getString(R.string.bt_loading));
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
     */
    public static Bundle createBundle(DialogIcon icon) {
        return createBundle(icon, DialogExtras.MODE_BOX_EXTRAS);
    }

    public static Bundle createBundle(DialogIcon icon, DialogExtras mode) {
        //Log.d("BACK$", "CREAR BUNDLE");
        Bundle bundle = new Bundle();

        if (icon == null) {
            bundle.putSerializable(DialogIcon.ICON_EXTRA.name(), DialogIcon.DEFAULT_ICON);
            bundle.putSerializable(DialogExtras.MODE_EXTRA.name(), DialogExtras.MODE_SPINNER_EXTRAS);
          //  Log.d("BACK$", "NULO");
        } else {
            //Log.d("BACK$", "NO NULO");
            bundle.putSerializable(DialogIcon.ICON_EXTRA.name(), icon);
            bundle.putSerializable(DialogExtras.MODE_EXTRA.name(), DialogExtras.MODE_BOX_EXTRAS);
        }
        return bundle;
    }

    @Override
    protected void bindObject() {
        viewDataBinding.setVariable(BR.model, source);
        viewDataBinding.executePendingBindings();
    }

    @Override
    protected int getDialogLayout() {
        DialogExtras mode = null;
        try {
            mode = (DialogExtras) getArguments().getSerializable(DialogExtras.MODE_EXTRA.name());
        } catch (ClassCastException ignored) {}
        if (mode == null) {
            mode = DialogExtras.MODE_SPINNER_EXTRAS;
        }

        switch (mode) {
            case MODE_BOX_EXTRAS:
                return R.layout.mvp_dialog_indeterminate;
            default:
                return R.layout.mvp_spinner;

        }
    }

    /**
     * Devuelve el modelo de datos
     *
     * @return modelo de datos
     */
    public ProgressModel getSource() {
        return source;
    }

    /**
     * Insertar modelo de datos
     * <p>
     * [EN]  Insert data model
     *
     * @param source modelo de datos
     */
    public void setSource(ProgressModel source) {
        super.setDialogModel(source);
        this.source = source;
    }
}
