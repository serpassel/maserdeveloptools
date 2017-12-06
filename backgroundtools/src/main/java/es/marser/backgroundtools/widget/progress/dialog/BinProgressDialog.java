package es.marser.backgroundtools.widget.progress.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import es.marser.backgroundtools.R;
import es.marser.backgroundtools.containers.dialogs.bases.BaseDialogBin;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.backgroundtools.widget.progress.ProgressBarUpdater;
import es.marser.backgroundtools.widget.progress.model.ProgressModel;
import es.marser.backgroundtools.widget.progress.presenter.ProgressPresenter;

/**
 * @author sergio
 *         Created by Sergio on 05/09/2017.
 *         Cuadro de progreso personalizado MVP
 *         <p>
 *         [EN]  MVP Custom Progress Box
 * @see BaseDialogBin
 */

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class BinProgressDialog extends BaseDialogBin<ProgressPresenter> implements ProgressBarUpdater {
    /**
     * Crear una nueva instancia del Dialogo
     * <p>
     * [EN]  Error Text to Display
     *
     * @param bundle Icono de la aplicación
     * @return nueva instancia
     * @see #createBundle(String)
     */
    public static BinProgressDialog newInstance(@NonNull Context context, @Nullable Bundle bundle) {
        if (bundle == null) {
            bundle = createBundle(DialogIcon.DEFAULT_ICON);
        }
        bundle.putString(DialogExtras.TITLE_EXTRA.name(), context.getResources().getString(R.string.bt_loading));
        /*PRESENTER*/
        ProgressPresenter presenter = new ProgressPresenter(context);
        presenter.setArguments(bundle);

        /*DIALOG*/
        BinProgressDialog instance = new BinProgressDialog();
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
     * @see ProgressModel#BC3_ICON
     * @see ProgressModel#PDF_ICON
     * @see ProgressModel#EXCEL_ICON
     * @see ProgressModel#DATABASE_ICON
     * @see ProgressModel#LOADING_ICON
     * @see ProgressModel#CALC_ICON
     */
    public static Bundle createBundle(DialogIcon icon) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DialogIcon.ICON_EXTRA.name(), icon);
        return bundle;
    }

    @Override
    public void setMax(@Nullable Integer max) {
        if(getPresenter() != null){
            getPresenter().setMax(max);
        }
    }

    @Override
    public void indeterminate(boolean value) {
        if(getPresenter() != null){
            getPresenter().indeterminate(value);
        }
    }

  @Override
  public void setProgress(@NonNull Integer value) {
      if(getPresenter() != null){
          getPresenter().setProgress(value);
      }
    }

    @Override
    public void increment(@NonNull Integer value) {
        if(getPresenter() != null){
            getPresenter().increment(value);
        }
    }

    @Override
    public void autoIncrement() {
        increment(1);
    }

    @Override
    public void addError(@NonNull String error) {
        if(getPresenter() != null){
            getPresenter().addError(error);
        }
    }
}
