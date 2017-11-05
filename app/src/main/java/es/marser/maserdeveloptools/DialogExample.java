package es.marser.maserdeveloptools;

import android.content.Context;
import android.util.Log;
import android.view.View;

import es.marser.LOG_TAG;
import es.marser.backgroundtools.dialogs.bases.BaseDialog;
import es.marser.backgroundtools.dialogs.model.CalendarObservable;
import es.marser.backgroundtools.dialogs.model.FileModel;
import es.marser.backgroundtools.dialogs.widget.calendar.CalendarChooser;
import es.marser.backgroundtools.dialogs.widget.confirmation.NotificationDialogBinModel;
import es.marser.backgroundtools.dialogs.widget.edition.EditDialogBinModel;
import es.marser.backgroundtools.dialogs.widget.file.FileChooserDialog;
import es.marser.backgroundtools.dialogs.model.ExampleModelObject;
import es.marser.backgroundtools.dialogs.widget.progress.BinIndeterminateDialog;
import es.marser.backgroundtools.dialogs.widget.progress.BinProgressDialog;
import es.marser.backgroundtools.dialogs.task.OnResult;
import es.marser.backgroundtools.dialogs.widget.toast.Launch_toast;
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

@SuppressWarnings({"WeakerAccess", "unused", "UnusedReturnValue"})
public class DialogExample {

    public static BaseDialog indeterminateBox(Context context) {
        BinIndeterminateDialog binDialog = BinIndeterminateDialog.newInstance(context,
                BinIndeterminateDialog
                        .createBundle(
                                DialogIcon.LOADING_ICON)
        );
        binDialog.setBody("[Placeholder]");
        binDialog.show();

        return binDialog;
    }

    public static BaseDialog indeterminateSpinner(Context context) {
        BinIndeterminateDialog binDialog = BinIndeterminateDialog.newInstance(context, BinIndeterminateDialog.createBundle(null));
        binDialog.show();
        return binDialog;
    }

    public static BaseDialog progressIndeterminateBox(Context context) {
        BinProgressDialog bar = BinProgressDialog
                .newInstance(context,
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
                .newInstance(context,
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

    public static BaseDialog editGeneric(final Context context) {
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
                                    Launch_toast.informationToast(context, value.toString());
                                    Log.i(LOG_TAG.TAG, "Aceptar");
                                } else {
                                    Log.i(LOG_TAG.TAG, "Cancelar");
                                    Launch_toast.errorToast(context, "Operación cancelada");
                                }
                            }

                            @Override
                            public void onClick(View view, ExampleModelObject value) {

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


    private static String body_example = "En un lugar de la Mancha, de cuyo nombre no quiero acordarme, no ha mucho tiempo que vivía un hidalgo de los de lanza en astillero, adarga antigua, rocín flaco y galgo corredor. Una olla de algo más vaca que carnero, salpicón las más noches, duelos y quebrantos los sábados, lentejas los viernes, algún palomino de añadidura los domingos, consumían las tres partes de su hacienda. El resto della concluían sayo de velarte, calzas de velludo para las fiestas con sus pantuflos de lo mismo, los días de entre semana se honraba con su vellori de lo más fino.";

    @SuppressWarnings("UnusedReturnValue")
    public static BaseDialog notificationInformation(Context context) {
        NotificationDialogBinModel dialog =
                NotificationDialogBinModel.newInstance(
                        context,
                        NotificationDialogBinModel.createInformationBundle(context, body_example)
                );
        dialog.show();
        return dialog;
    }

    public static BaseDialog notificationError(Context context) {
        NotificationDialogBinModel dialog =
                NotificationDialogBinModel.newInstance(
                        context,
                        NotificationDialogBinModel.createErrorBundle(context, body_example)
                );
        dialog.show();
        return dialog;
    }

    public static BaseDialog notificationWarning(Context context) {
        NotificationDialogBinModel dialog =
                NotificationDialogBinModel.newInstance(
                        context,
                        NotificationDialogBinModel.createWarningBundle(context, body_example)
                );
        dialog.show();
        return dialog;
    }

    public static BaseDialog notificationHelp(Context context) {
        NotificationDialogBinModel dialog =
                NotificationDialogBinModel.newInstance(
                        context,
                        NotificationDialogBinModel.createHelpBundle(context, body_example)
                );
        dialog.show();
        return dialog;
    }

    public static BaseDialog notificationConfirmation(final Context context) {
        NotificationDialogBinModel dialog =
                NotificationDialogBinModel.newInstance(
                        context,
                        NotificationDialogBinModel.createConfirmationBundle(context, body_example), new OnResult<Void>() {
                            @Override
                            public void onResult(DialogExtras result, Void value) {
                                switch (result){
                                    case OK_EXTRA:
                                        Launch_toast.informationToast(context, result.name());
                                        break;
                                    case CANCEL_EXTRA:
                                        Launch_toast.errorToast(context, result.name());
                                        break;
                                    case OPTION_EXTRA:
                                        Launch_toast.warningToast(context,result.name());
                                        break;
                                }
                            }

                            @Override
                            public void onClick(View view, Void value) {

                            }
                        }
                );
        dialog.show();
        return dialog;
    }

    public static BaseDialog notificationConfirmationKey(final Context context) {
        NotificationDialogBinModel dialog =
                NotificationDialogBinModel.newInstance(
                        context,
                        NotificationDialogBinModel.createConfirmationBundle(context, body_example), new OnResult<Void>() {
                            @Override
                            public void onResult(DialogExtras result, Void value) {
                                switch (result){
                                    case OK_EXTRA:
                                        Launch_toast.informationToast(context, result.name());
                                        break;
                                    case CANCEL_EXTRA:
                                        Launch_toast.errorToast(context, result.name());
                                        break;
                                    case OPTION_EXTRA:
                                        Launch_toast.warningToast(context,result.name());
                                        break;
                                }
                            }

                            @Override
                            public void onClick(View view, Void value) {

                            }
                        }
                );
        dialog.setKeyName("Test_key");
        dialog.show();
        return dialog;
    }

    public static BaseDialog notificationOkCancelError(final Context context) {
        NotificationDialogBinModel dialog =
                NotificationDialogBinModel.newInstance(
                        context,
                        NotificationDialogBinModel.createOkCancelErrorBundle(context, body_example), new OnResult<Void>() {
                            @Override
                            public void onResult(DialogExtras result, Void value) {
                                switch (result){
                                    case OK_EXTRA:
                                        Launch_toast.informationToast(context, result.name());
                                        break;
                                    case CANCEL_EXTRA:
                                        Launch_toast.errorToast(context, result.name());
                                        break;
                                    case OPTION_EXTRA:
                                        Launch_toast.warningToast(context,result.name());
                                        break;
                                }
                            }

                            @Override
                            public void onClick(View view, Void value) {

                            }
                        }
                );
        dialog.show();
        return dialog;
    }

    public static BaseDialog notificationYesNoCancelConfirmation(final Context context) {
        NotificationDialogBinModel dialog =
                NotificationDialogBinModel.newInstance(
                        context,
                        NotificationDialogBinModel.createYesNoCancelConfirmationBundle(context, body_example), new OnResult<Void>() {
                            @Override
                            public void onResult(DialogExtras result, Void value) {
                                switch (result){
                                    case OK_EXTRA:
                                        Launch_toast.informationToast(context, result.name());
                                        break;
                                    case CANCEL_EXTRA:
                                        Launch_toast.errorToast(context, result.name());
                                        break;
                                    case OPTION_EXTRA:
                                        Launch_toast.warningToast(context,result.name());
                                        break;
                                }
                            }

                            @Override
                            public void onClick(View view, Void value) {

                            }
                        }
                );
        dialog.show();
        return dialog;
    }

    public static BaseDialog notificationDeleteRecords(final Context context) {
        NotificationDialogBinModel dialog =
                NotificationDialogBinModel.newInstance(
                        context,
                        NotificationDialogBinModel.createDeleteRecordsBundle(context), new OnResult<Void>() {
                            @Override
                            public void onResult(DialogExtras result, Void value) {
                                switch (result){
                                    case OK_EXTRA:
                                        Launch_toast.informationToast(context, result.name());
                                        break;
                                    case CANCEL_EXTRA:
                                        Launch_toast.errorToast(context, result.name());
                                        break;
                                    case OPTION_EXTRA:
                                        Launch_toast.warningToast(context,result.name());
                                        break;
                                }
                            }

                            @Override
                            public void onClick(View view, Void value) {

                            }
                        }
                );
        dialog.show();
        return dialog;
    }

    @SuppressWarnings("All")
    public static BaseDialog fileSelector(final Context context, boolean readeable) {


        FileChooserDialog dialog =
                FileChooserDialog.newInstance(
                        context,
                        FileChooserDialog.createBundle(context),
                        readeable,
                        new OnResult<FileModel>() {
                            @Override
                            public void onResult(DialogExtras result, FileModel value) {
                                if(result == DialogExtras.OK_EXTRA){
                                    Log.i(LOG_TAG.TAG, "Valor " + value.getFile().getAbsolutePath());
                                    Launch_toast.informationToast(context, value.getFile().getAbsolutePath());
                                }else{
                                    Launch_toast.errorToast(context, "Operación cancelada");
                                }
                            }

                            @Override
                            public void onClick(View view, FileModel value) {

                            }
                        }
                );
        dialog.show();
        return dialog;
    }

    public static BaseDialog filefilterSelector(final Context context, boolean readeable) {

        FileChooserDialog dialog =
                FileChooserDialog.newInstance(
                        context,
                        FileChooserDialog.createBundle(context, new String[]{".bc3"}),
                        readeable,
                        new OnResult<FileModel>() {
                            @Override
                            public void onResult(DialogExtras result, FileModel value) {
                                if(result == DialogExtras.OK_EXTRA){
                                    Launch_toast.informationToast(context, value.getName());
                                }else{
                                    Launch_toast.errorToast(context, "Operación cancelada");
                                }
                            }

                            @Override
                            public void onClick(View view, FileModel value) {

                            }
                        }
                );
        dialog.show();
        return dialog;
    }

    public static BaseDialog calendarChooser(final Context context) {

        CalendarChooser dialog =
                CalendarChooser.newInstance(
                        context,
                        CalendarChooser.createBundle(context),
                        new OnResult<CalendarObservable>() {
                            @Override
                            public void onResult(DialogExtras result, CalendarObservable value) {
                                if(result == DialogExtras.OK_EXTRA){
                                    Launch_toast.warningToast(context, value.getDateLong());
                                }else{
                                    Launch_toast.errorToast(context, "Operación cancelada");
                                }
                            }

                            @Override
                            public void onClick(View view, CalendarObservable value) {

                            }
                        }
                );
        dialog.show();
        return dialog;
    }

}
