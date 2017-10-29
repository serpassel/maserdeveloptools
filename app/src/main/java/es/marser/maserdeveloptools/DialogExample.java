package es.marser.maserdeveloptools;

import android.content.Context;
import android.util.Log;
import android.view.View;

import es.marser.LOG_TAG;
import es.marser.backgroundtools.dialogs.bases.BaseDialog;
import es.marser.backgroundtools.dialogs.confirmation.NotificationDialogBinModel;
import es.marser.backgroundtools.dialogs.edition.EditDialogBinModel;
import es.marser.backgroundtools.dialogs.model.ExampleModelObject;
import es.marser.backgroundtools.dialogs.progress.BinIndeterminateDialog;
import es.marser.backgroundtools.dialogs.progress.BinProgressDialog;
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
        BinIndeterminateDialog binDialog = BinIndeterminateDialog.newInstace(context,
                BinIndeterminateDialog
                        .createBundle(
                                DialogIcon.LOADING_ICON)
        );
        binDialog.setBody("[Placeholder]");
        binDialog.show();

        return binDialog;
    }

    public static BaseDialog indeterminateSpinner(Context context) {
        BinIndeterminateDialog binDialog = BinIndeterminateDialog.newInstace(context, BinIndeterminateDialog.createBundle(null));
        binDialog.show();
        return binDialog;
    }

    public static BaseDialog progressIndeterminateBox(Context context) {
        BinProgressDialog bar = BinProgressDialog
                .newInstace(context,
                        BinProgressDialog
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

        BinProgressDialog bar = BinProgressDialog
                .newInstace(context,
                        BinProgressDialog
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
        EditDialogBinModel gene =
                EditDialogBinModel.newInstance(
                        context,
                        EditDialogBinModel
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
        EditDialogBinModel gene =
                EditDialogBinModel.newInstance(
                        context,
                        EditDialogBinModel
                                .createBundle(R.layout.mvp_example_edit_model_object, new ExampleModelObject()),
                        result
                );

        gene.show();
        return gene;
    }


    private static String body_example = "Android Open Source UI textview android justifiedtextview Android UI textview ufo22940268 android justifiedtextview htm Android Open Source UI textview android justifiedtextview Android Open Source UI textview justify textview android Android UI textview nikoo28 justify textview android htm Android Open Source UI textview justify textview android This is a simple implementation to get text in justified manner in any android application";

    @SuppressWarnings("UnusedReturnValue")
    public static BaseDialog notificationInformation(Context context) {
        NotificationDialogBinModel dialog =
                NotificationDialogBinModel.newInstace(
                        context,
                        NotificationDialogBinModel.createInformationBundle(context, body_example)
                );
        dialog.show();
        return dialog;
    }

    public static BaseDialog notificationError(Context context) {
        NotificationDialogBinModel dialog =
                NotificationDialogBinModel.newInstace(
                        context,
                        NotificationDialogBinModel.createErrorBundle(context, body_example)
                );
        dialog.show();
        return dialog;
    }

    public static BaseDialog notificationWarning(Context context) {
        NotificationDialogBinModel dialog =
                NotificationDialogBinModel.newInstace(
                        context,
                        NotificationDialogBinModel.createWarningBundle(context, body_example)
                );
        dialog.show();
        return dialog;
    }

    public static BaseDialog notificationHelp(Context context) {
        NotificationDialogBinModel dialog =
                NotificationDialogBinModel.newInstace(
                        context,
                        NotificationDialogBinModel.createHelpBundle(context, body_example)
                );
        dialog.show();
        return dialog;
    }

    public static BaseDialog notificationConfirmation(Context context) {
        NotificationDialogBinModel dialog =
                NotificationDialogBinModel.newInstace(
                        context,
                        NotificationDialogBinModel.createConfirmationBundle(context, body_example)
                );
        dialog.show();
        return dialog;
    }

    public static BaseDialog notificationConfirmationKey(Context context) {
        NotificationDialogBinModel dialog =
                NotificationDialogBinModel.newInstace(
                        context,
                        NotificationDialogBinModel.createConfirmationBundle(context, body_example)
                );
        dialog.setKeyName("Test_key");
        dialog.show();
        return dialog;
    }

    public static BaseDialog notificationOkCancelError(Context context) {
        NotificationDialogBinModel dialog =
                NotificationDialogBinModel.newInstace(
                        context,
                        NotificationDialogBinModel.createOkCancelErrorBundle(context, body_example)
                );
        dialog.show();
        return dialog;
    }

    public static BaseDialog notificationYesNoCancelConfirmation(Context context) {
        NotificationDialogBinModel dialog =
                NotificationDialogBinModel.newInstace(
                        context,
                        NotificationDialogBinModel.createYesNoCancelConfirmationBundle(context, body_example)
                );
        dialog.show();
        return dialog;
    }

    public static BaseDialog notificationDeleteRecords(Context context) {
        NotificationDialogBinModel dialog =
                NotificationDialogBinModel.newInstace(
                        context,
                        NotificationDialogBinModel.createDeleteRecordsBundle(context)
                );
        dialog.show();
        return dialog;
    }

}
