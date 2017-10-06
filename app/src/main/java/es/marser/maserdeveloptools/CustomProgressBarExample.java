package es.marser.maserdeveloptools;

import android.content.Context;

import es.marser.backgroundtools.dialogs.CustomInterminateBinDialog;
import es.marser.backgroundtools.dialogs.model.DialogProgressModel;

/**
 * @author sergio
 *         Created by sergio on 6/10/17.
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public class CustomProgressBarExample {

    public static void indeterminateBox(Context context) {
        CustomInterminateBinDialog binDialog = CustomInterminateBinDialog.newInstace(context,
                CustomInterminateBinDialog
                        .createBundle(
                                DialogProgressModel.LOADING_ICON
                        )
        );
        binDialog.setTemp("[Placeholder]");
        binDialog.show();

    }

    public static void indeterminateSpinner(Context context) {
        CustomInterminateBinDialog.newInstace(context, CustomInterminateBinDialog.createBundle(null)).show();

    }
}
