package es.marser.maserdeveloptools;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

import es.marser.LOG_TAG;
import es.marser.backgroundtools.containers.dialogs.bases.BaseDialog;
import es.marser.backgroundtools.containers.dialogs.model.CalendarObservable;
import es.marser.backgroundtools.containers.dialogs.model.ExampleModelObject;
import es.marser.backgroundtools.containers.dialogs.task.OnDResult;
import es.marser.backgroundtools.containers.dialogs.task.OnResult;
import es.marser.backgroundtools.containers.dialogs.widget.auth.DialogCredential;
import es.marser.backgroundtools.containers.dialogs.widget.auth.DialogLogin;
import es.marser.backgroundtools.containers.dialogs.widget.calendar.CalendarChooser;
import es.marser.backgroundtools.containers.dialogs.widget.confirmation.NotificationDialogBinModel;
import es.marser.backgroundtools.containers.dialogs.widget.edition.EditDialogBinModel;
import es.marser.backgroundtools.containers.dialogs.widget.inputbox.DialogInputBox;
import es.marser.backgroundtools.containers.dialogs.widget.progress.BinIndeterminateDialog;
import es.marser.backgroundtools.containers.dialogs.widget.progress.BinProgressDialog;
import es.marser.backgroundtools.containers.dialogs.widget.toast.Launch_toast;
import es.marser.backgroundtools.enums.DialogExtras;
import es.marser.backgroundtools.enums.DialogIcon;
import es.marser.backgroundtools.widget.chooser.dialog.ChooserDialog;
import es.marser.backgroundtools.widget.files.dialogs.FileChooserDialog;
import es.marser.backgroundtools.widget.files.model.FileModel;
import es.marser.backgroundtools.widget.files.model.SimpleFileListModel;
import es.marser.backgroundtools.widget.files.presenter.SimpleFileListPresenter;
import es.marser.backgroundtools.widget.territories.model.AutonomousModel;
import es.marser.backgroundtools.widget.territories.model.ProvincieModel;
import es.marser.backgroundtools.widget.territories.model.VillageModel;
import es.marser.backgroundtools.widget.territories.presenter.AutonomousPresenter;
import es.marser.backgroundtools.widget.territories.presenter.ProvincePresenter;
import es.marser.backgroundtools.widget.territories.presenter.VillagePresenter;
import es.marser.generic.GenericFactory;
import es.marser.tools.MathTools;
import es.marser.tools.TextTools;

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
                                // Log.w(LOG_TAG.TAG, "Resultado");
                                if (result == DialogExtras.OK_EXTRA) {
                                    Launch_toast.informationToast(context, value.toString());
                                    //   Log.i(LOG_TAG.TAG, "Aceptar");
                                } else {
                                    // Log.i(LOG_TAG.TAG, "Cancelar");
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
                                switch (result) {
                                    case OK_EXTRA:
                                        Launch_toast.informationToast(context, result.name());
                                        break;
                                    case CANCEL_EXTRA:
                                        Launch_toast.errorToast(context, result.name());
                                        break;
                                    case OPTION_EXTRA:
                                        Launch_toast.warningToast(context, result.name());
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
                                switch (result) {
                                    case OK_EXTRA:
                                        Launch_toast.informationToast(context, result.name());
                                        break;
                                    case CANCEL_EXTRA:
                                        Launch_toast.errorToast(context, result.name());
                                        break;
                                    case OPTION_EXTRA:
                                        Launch_toast.warningToast(context, result.name());
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
                                switch (result) {
                                    case OK_EXTRA:
                                        Launch_toast.informationToast(context, result.name());
                                        break;
                                    case CANCEL_EXTRA:
                                        Launch_toast.errorToast(context, result.name());
                                        break;
                                    case OPTION_EXTRA:
                                        Launch_toast.warningToast(context, result.name());
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
                                switch (result) {
                                    case OK_EXTRA:
                                        Launch_toast.informationToast(context, result.name());
                                        break;
                                    case CANCEL_EXTRA:
                                        Launch_toast.errorToast(context, result.name());
                                        break;
                                    case OPTION_EXTRA:
                                        Launch_toast.warningToast(context, result.name());
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
                                switch (result) {
                                    case OK_EXTRA:
                                        Launch_toast.informationToast(context, result.name());
                                        break;
                                    case CANCEL_EXTRA:
                                        Launch_toast.errorToast(context, result.name());
                                        break;
                                    case OPTION_EXTRA:
                                        Launch_toast.warningToast(context, result.name());
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
                        SimpleFileListPresenter.BundleBuilder.createBundle(context),
                        readeable,
                        new SimpleFileListPresenter(context),
                        new SimpleFileListModel(context),
                        new OnResult<FileModel>() {
                            @Override
                            public void onResult(DialogExtras result, FileModel value) {
                                if (result == DialogExtras.OK_EXTRA) {
                                    Log.i(LOG_TAG.TAG, "Valor " + value.getFile().getAbsolutePath());
                                    Launch_toast.informationToast(context, value.getFile().getAbsolutePath());
                                } else {
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
                        SimpleFileListPresenter.BundleBuilder.createBundle(context, new String[]{".bc3"}),
                        readeable,
                        new SimpleFileListPresenter(context),
                        new SimpleFileListModel(context),
                        new OnResult<FileModel>() {
                            @Override
                            public void onResult(DialogExtras result, FileModel value) {
                                if (result == DialogExtras.OK_EXTRA) {
                                    Launch_toast.informationToast(context, value.getName());
                                } else {
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
                                if (result == DialogExtras.OK_EXTRA) {
                                    Launch_toast.warningToast(context, value.getDateLong());
                                } else {
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


    //TERRITORY_________________________________________________________________________________________

    public static BaseDialog autonomousChooser(final Context context) {

        final AutonomousModel autonomousModel = GenericFactory
                .BuildSingleObject(AutonomousModel.class,
                        context.getResources().getString(R.string.all_spain_ccaa)
                );

        OnResult<List<AutonomousModel>> result = new OnResult<List<AutonomousModel>>() {
            @Override
            public void onResult(DialogExtras result, List<AutonomousModel> value) {
                if (result == DialogExtras.OK_EXTRA) {

                    StringBuilder builder = new StringBuilder();

                    for (AutonomousModel pm : value) {
                        builder.append(pm.getName()).append(",\n");
                    }
                    TextTools.deleteLastBrand(builder, ";\n");

                    NotificationDialogBinModel.newInstance(
                            context,
                            NotificationDialogBinModel.createInformationBundle(context, builder.toString()));
                }
            }

            @Override
            public void onClick(View view, List<AutonomousModel> value) {

            }
        };

        Bundle bundle = AutonomousPresenter.createBundle(
                context,
                false,
                autonomousModel.preSelectValue(),
                true);

        AutonomousPresenter presenter = new AutonomousPresenter(context, false);

        ChooserDialog<AutonomousModel> dialog = ChooserDialog.newInstance(context, bundle, presenter, result);

        dialog.show();
        return dialog;
    }

    public static BaseDialog provincieChooser(final Context context) {

        OnResult<List<ProvincieModel>> result = new OnResult<List<ProvincieModel>>() {
            @Override
            public void onResult(DialogExtras result, List<ProvincieModel> value) {

                if (result == DialogExtras.OK_EXTRA) {
                    StringBuilder builder = new StringBuilder();
                    for (ProvincieModel pm : value) {
                        builder.append(pm.getName()).append(",\n");
                    }
                    TextTools.deleteLastBrand(builder, ";\n");
                    Launch_toast.informationToast(context, builder.toString());
                }
            }

            @Override
            public void onClick(View view, List<ProvincieModel> value) {

            }
        };

        Bundle bundle = ProvincePresenter.createBundle(context, -1, false, null);

        ProvincePresenter presenter = new ProvincePresenter(context, false);

        ChooserDialog<ProvincieModel> dialog = ChooserDialog.newInstance(context, bundle, presenter, result);
        dialog.show();
        return dialog;
    }

    public static BaseDialog provincieMultiChooser(final Context context) {
        OnResult<List<ProvincieModel>> result = new OnResult<List<ProvincieModel>>() {
            @Override
            public void onResult(DialogExtras result, List<ProvincieModel> value) {

                if (result == DialogExtras.OK_EXTRA) {
                    StringBuilder builder = new StringBuilder();
                    for (ProvincieModel pm : value) {
                        builder.append(pm.getName()).append(",\n");
                    }
                    TextTools.deleteLastBrand(builder, ";\n");
                    Launch_toast.informationToast(context, builder.toString());
                }
            }

            @Override
            public void onClick(View view, List<ProvincieModel> value) {

            }
        };

        Bundle bundle = ProvincePresenter.createBundle(context, -1, true, null);

        ProvincePresenter presenter = new ProvincePresenter(context, true);

        ChooserDialog<ProvincieModel> dialog = ChooserDialog.newInstance(context,
                bundle,
                presenter,
                result
        );
        dialog.show();
        return dialog;
    }

    public static BaseDialog preselectChooser(final Context context) {

        OnResult<List<ProvincieModel>> result = new OnResult<List<ProvincieModel>>() {
            @Override
            public void onResult(DialogExtras result, List<ProvincieModel> value) {

                if (result == DialogExtras.OK_EXTRA) {

                    StringBuilder builder = new StringBuilder();

                    for (ProvincieModel pm : value) {
                        builder.append(pm.getName()).append(",\n");
                    }
                    TextTools.deleteLastBrand(builder, ";\n");

                    NotificationDialogBinModel.newInstance(
                            context,
                            NotificationDialogBinModel.createInformationBundle(context, builder.toString())
                    ).show();
                }
            }

            @Override
            public void onClick(View view, List<ProvincieModel> value) {

            }
        };

        Bundle bundle = ProvincePresenter.createBundle(context, -1, true, "Almería, Burgos,");

        ProvincePresenter presenter = new ProvincePresenter(context, true);

        ChooserDialog<ProvincieModel> dialog = ChooserDialog.newInstance(context,
                bundle,
                presenter,
                result
        );
        dialog.show();
        return dialog;
    }

    public static BaseDialog andaluciaChooser(final Context context) {
        OnResult<List<ProvincieModel>> result = new OnResult<List<ProvincieModel>>() {
            @Override
            public void onResult(DialogExtras result, List<ProvincieModel> value) {

                if (result == DialogExtras.OK_EXTRA) {
                    StringBuilder builder = new StringBuilder();
                    for (ProvincieModel pm : value) {
                        builder.append(pm.getName()).append(",\n");
                    }
                    TextTools.deleteLastBrand(builder, ";\n");
                    Launch_toast.informationToast(context, builder.toString());
                }
            }

            @Override
            public void onClick(View view, List<ProvincieModel> value) {

            }
        };

        Bundle bundle = ProvincePresenter.createBundle(context, 1, true, null);

        ProvincePresenter presenter = new ProvincePresenter(context, false);

        ChooserDialog<ProvincieModel> dialog = ChooserDialog.newInstance(context, bundle, presenter, result);

        dialog.show();
        return dialog;
    }

    public static BaseDialog arabaVillagesChooser(final Context context) {
        OnResult<List<VillageModel>> result = new OnResult<List<VillageModel>>() {
            @Override
            public void onResult(DialogExtras result, List<VillageModel> value) {
                if (result == DialogExtras.OK_EXTRA) {

                    StringBuilder builder = new StringBuilder();

                    for (VillageModel pm : value) {
                        builder.append(pm.getName()).append(",\n");
                    }
                    TextTools.deleteLastBrand(builder, ";\n");

                    NotificationDialogBinModel.newInstance(
                            context,
                            NotificationDialogBinModel.createInformationBundle(context, builder.toString())
                    ).show();
                }
            }

            @Override
            public void onClick(View view, List<VillageModel> value) {

            }
        };
        Bundle bundle = VillagePresenter.createBundle(context, 1, false, null);

        VillagePresenter presenter = new VillagePresenter(context, false);

        ChooserDialog<VillageModel> dialog = ChooserDialog.newInstance(context, bundle, presenter, result);

        dialog.show();
        return dialog;
    }

    //INPUT_______________________________________________________________________________________________

    public static BaseDialog longInputBox(final Context context) {
        DialogInputBox dialog = DialogInputBox.newInstance(context,
                DialogInputBox.createBundle("Introducir texto", 6, "Texto Largo", 400),
                new OnResult<String>() {
                    @Override
                    public void onResult(DialogExtras result, String value) {
                        if (result == DialogExtras.OK_EXTRA) {
                            Launch_toast.informationToast(context, value);
                        }
                    }

                    @Override
                    public void onClick(View view, String value) {

                    }
                });
        dialog.show();
        return dialog;

    }

    public static BaseDialog numberBox(final Context context) {
        DialogInputBox dialog = DialogInputBox.newInstance(context,
                DialogInputBox.createNumberBundle(null, "Introducir texto"),
                new OnResult<String>() {
                    @Override
                    public void onResult(DialogExtras result, String value) {
                        if (result == DialogExtras.OK_EXTRA) {
                            Launch_toast.informationToast(context, value);
                        }
                    }

                    @Override
                    public void onClick(View view, String value) {

                    }
                });
        dialog.show();
        return dialog;

    }

    public static BaseDialog passwordBox(final Context context) {
        DialogInputBox dialog = DialogInputBox.newInstance(context,
                DialogInputBox.createPasswordBundle(null, 6),
                new OnResult<String>() {
                    @Override
                    public void onResult(DialogExtras result, String value) {
                        if (result == DialogExtras.OK_EXTRA) {
                            Launch_toast.informationToast(context, value);
                        }
                    }

                    @Override
                    public void onClick(View view, String value) {

                    }
                });
        dialog.show();
        return dialog;

    }

    //AUTH______________________________________________________________________________________________
    public static BaseDialog loginMailBox(final Context context) {
        DialogLogin dialog = DialogLogin.newInstance(context,
                DialogLogin.createMailPasswordBundle(null, 6),
                new OnDResult<String, String>() {
                    @Override
                    public void onResult(DialogExtras result, String value1, String value2) {
                        if (result == DialogExtras.OK_EXTRA) {
                            Launch_toast.informationToast(context, value1 + value2);
                        }
                    }

                    @Override
                    public void onClick(View view, String value, String value2) {

                    }
                });
        dialog.show();
        return dialog;

    }

    public static BaseDialog loginUserBox(final Context context) {
        DialogLogin dialog = DialogLogin.newInstance(context,
                DialogLogin.createUserPasswordBundle(null, 6),
                new OnDResult<String, String>() {
                    @Override
                    public void onResult(DialogExtras result, String value1, String value2) {
                        if (result == DialogExtras.OK_EXTRA) {
                            Launch_toast.informationToast(context, value1 + value2);
                        }
                    }

                    @Override
                    public void onClick(View view, String value, String value2) {

                    }
                });
        dialog.show();
        return dialog;

    }

    public static BaseDialog passwordModificationBox(final Context context) {
        DialogLogin dialog = DialogLogin.newInstance(context,
                DialogLogin.createModificationPasswordBundle(null, 6),
                new OnDResult<String, String>() {
                    @Override
                    public void onResult(DialogExtras result, String value1, String value2) {
                        if (result == DialogExtras.OK_EXTRA) {
                            Launch_toast.informationToast(context, value2);
                        }
                    }

                    @Override
                    public void onClick(View view, String value, String value2) {

                    }
                });
        dialog.show();
        return dialog;

    }

    public static BaseDialog credentialLogin(final Context context) {
        DialogCredential dialog = DialogCredential.newInstance(context,
                DialogCredential.createBundle(),
                new OnResult<DialogIcon>() {
                    @Override
                    public void onResult(DialogExtras result, DialogIcon value) {
                        if (result == DialogExtras.OK_EXTRA) {
                            Launch_toast.informationToast(context, value.name());
                        }
                    }

                    @Override
                    public void onClick(View view, DialogIcon value) {

                    }
                }
        );
        dialog.show();
        return dialog;

    }

    public static BaseDialog credentialReauth(final Context context) {
        DialogCredential dialog = DialogCredential.newInstance(context,
                DialogCredential.createBundle(context),
                new OnResult<DialogIcon>() {
                    @Override
                    public void onResult(DialogExtras result, DialogIcon value) {
                        if (result == DialogExtras.OK_EXTRA) {
                            Launch_toast.informationToast(context, value.name());
                        }
                    }

                    @Override
                    public void onClick(View view, DialogIcon value) {

                    }
                }
        );
        dialog.show();
        return dialog;

    }

}
