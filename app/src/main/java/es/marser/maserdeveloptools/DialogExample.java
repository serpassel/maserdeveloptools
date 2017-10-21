package es.marser.maserdeveloptools;

import android.content.Context;
import android.util.Log;
import android.view.View;

import es.marser.LOG_TAG;
import es.marser.backgroundtools.dialogs.bases.BaseDialog;
import es.marser.backgroundtools.dialogs.confirmation.NotificationDialog;
import es.marser.backgroundtools.dialogs.edition.GenericEditDialog;
import es.marser.backgroundtools.dialogs.model.ExampleModelObject;
import es.marser.backgroundtools.dialogs.progress.CustomInterminateBinDialog;
import es.marser.backgroundtools.dialogs.progress.CustomProgressBinDialog;
import es.marser.backgroundtools.dialogs.task.OnResult;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.tools.MathTools;

/**
 * @author sergio
 *         Created by sergio on 6/10/17.
 *         Ejemplo de dialogos personalizados
 *         <p>
 *         [EN]  Example of custom dialogs
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public class DialogExample {

    public static BaseDialog indeterminateBox(Context context) {
        CustomInterminateBinDialog binDialog = CustomInterminateBinDialog.newInstace(context,
                CustomInterminateBinDialog
                        .createBundle(
                                DialogIcon.LOADING_ICON)
        );
        binDialog.setBody("[Placeholder]");
        binDialog.show();

        return binDialog;
    }

    public static BaseDialog indeterminateSpinner(Context context) {
        CustomInterminateBinDialog binDialog = CustomInterminateBinDialog.newInstace(context, CustomInterminateBinDialog.createBundle(null));
        binDialog.show();
        return binDialog;
    }

    public static BaseDialog progressIndeterminateBox(Context context) {
        CustomProgressBinDialog bar = CustomProgressBinDialog
                .newInstace(context,
                        CustomProgressBinDialog
                                .createBundle(
                                        DialogIcon.EXCEL_ICON
                                ));
        bar.setMax(null);
        bar.show();
        return bar;
    }

    public static BaseDialog progressBox(Context context) {
        int max = 1000;
        String headTitle = "Leyendo xls...";

        CustomProgressBinDialog bar = CustomProgressBinDialog
                .newInstace(context,
                        CustomProgressBinDialog
                                .createBundle(
                                        DialogIcon.EXCEL_ICON
                                ));
        bar.show();

        bar.setTitle(headTitle);
        bar.setMax(max);
        bar.setBody("[PlaceHolder]");

        for (int i = 0; i < max; ++i) {
            bar.setProgress(i);

            if (MathTools.isMultiple(i, 100)) {
                bar.addError("Error " + i);
            }
        }
        return bar;
    }

    public static BaseDialog editGeneric(Context context) {
        GenericEditDialog gene =
                GenericEditDialog.newInstance(
                        context,
                        GenericEditDialog
                                .createBundle(R.layout.mvp_example_edit_model_object, new ExampleModelObject()),
                        new OnResult<ExampleModelObject>() {
                            @Override
                            public void onResult(DialogExtras result, ExampleModelObject value) {
                                Log.w(LOG_TAG.TAG, "Resultado");

                                if (result == DialogExtras.OK_EXTRA) {
                                    Log.i(LOG_TAG.TAG, "Aceptar");
                                } else {
                                    Log.i(LOG_TAG.TAG, "Cancelar");
                                }
                            }

                            @Override
                            public void onClick(View v, ExampleModelObject value) {

                            }
                        }
                );

        gene.show();
        return gene;
    }

    public static BaseDialog editGeneric(Context context, OnResult<ExampleModelObject> result) {
        GenericEditDialog gene =
                GenericEditDialog.newInstance(
                        context,
                        GenericEditDialog
                                .createBundle(R.layout.mvp_example_edit_model_object, new ExampleModelObject()),
                        result
                );

        gene.show();
        return gene;
    }


    private static String body_example = "Android Open Source UI textview android justifiedtextview Android UI textview ufo22940268 android justifiedtextview htm Android Open Source UI textview android justifiedtextview Android Open Source UI textview justify textview android Android UI textview nikoo28 justify textview android htm Android Open Source UI textview justify textview android This is a simple implementation to get text in justified manner in any android application";

    @SuppressWarnings("UnusedReturnValue")
    public static BaseDialog notificationInformation(Context context) {
        NotificationDialog dialog =
                NotificationDialog.newInstace(
                        context,
                        NotificationDialog.createInformationBundle(context, body_example)
                );
        dialog.show();
        return dialog;
    }

    public static BaseDialog notificationError(Context context) {
        NotificationDialog dialog =
                NotificationDialog.newInstace(
                        context,
                        NotificationDialog.createErrorBundle(context, body_example)
                );
        dialog.show();
        return dialog;
    }

    public static BaseDialog notificationWarning(Context context) {
        NotificationDialog dialog =
                NotificationDialog.newInstace(
                        context,
                        NotificationDialog.createWarningBundle(context, body_example)
                );
        dialog.show();
        return dialog;
    }

    public static BaseDialog notificationHelp(Context context) {
        NotificationDialog dialog =
                NotificationDialog.newInstace(
                        context,
                        NotificationDialog.createHelpBundle(context, body_example)
                );
        dialog.show();
        return dialog;
    }

    public static BaseDialog notificationConfirmation(Context context) {
        NotificationDialog dialog =
                NotificationDialog.newInstace(
                        context,
                        NotificationDialog.createConfirmationBundle(context, body_example)
                );
        dialog.show();
        return dialog;
    }

    public static BaseDialog notificationOkCancelError(Context context) {
        NotificationDialog dialog =
                NotificationDialog.newInstace(
                        context,
                        NotificationDialog.createOkCancelErrorBundle(context, body_example)
                );
        dialog.show();
        return dialog;
    }

    public static BaseDialog notificationYesNoCancelConfirmation(Context context) {
        NotificationDialog dialog =
                NotificationDialog.newInstace(
                        context,
                        NotificationDialog.createYesNoCancelConfirmationBundle(context, body_example)
                );
        dialog.show();
        return dialog;
    }
}
