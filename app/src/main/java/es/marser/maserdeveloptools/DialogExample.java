package es.marser.maserdeveloptools;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

import es.marser.LOG_TAG;
import es.marser.backgroundtools.dialogs.bases.BaseDialog;
import es.marser.backgroundtools.dialogs.edition.GenericEditDialog;
import es.marser.backgroundtools.dialogs.model.ExampleModelObject;
import es.marser.backgroundtools.dialogs.progress.CustomInterminateBinDialog;
import es.marser.backgroundtools.dialogs.progress.CustomProgressBinDialog;
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
                                BaseDialog.DIALOG_ICON.LOADING_ICON)
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
                                        BaseDialog.DIALOG_ICON.EXCEL_ICON
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
                                        BaseDialog.DIALOG_ICON.EXCEL_ICON
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
                        new GenericEditDialog.OnResult<ExampleModelObject>() {
                            @Override
                            public void onResult(int result, ExampleModelObject value) {
                                Log.w(LOG_TAG.TAG, "Resultado");

                                if (result == Activity.RESULT_OK) {
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

    public static BaseDialog editGeneric(Context context, GenericEditDialog.OnResult<ExampleModelObject> result) {
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
}
