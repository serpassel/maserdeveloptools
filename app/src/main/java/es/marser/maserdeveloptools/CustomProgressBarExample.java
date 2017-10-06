package es.marser.maserdeveloptools;

import android.content.Context;

import es.marser.backgroundtools.dialogs.CustomInterminateBinDialog;
import es.marser.backgroundtools.dialogs.CustomProgressBinDialog;
import es.marser.backgroundtools.dialogs.bases.BaseCustomBinDialog;
import es.marser.backgroundtools.dialogs.model.DialogProgressModel;
import es.marser.tools.MathTools;

/**
 * @author sergio
 *         Created by sergio on 6/10/17.
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public class CustomProgressBarExample {

    public static BaseCustomBinDialog indeterminateBox(Context context) {
        CustomInterminateBinDialog binDialog = CustomInterminateBinDialog.newInstace(context,
                CustomInterminateBinDialog
                        .createBundle(
                                DialogProgressModel.LOADING_ICON
                        )
        );
        binDialog.setTemp("[Placeholder]");
        binDialog.show();

        return binDialog;
    }

    public static void indeterminateSpinner(Context context) {
        CustomInterminateBinDialog.newInstace(context, CustomInterminateBinDialog.createBundle(null)).show();
    }

    public static void progressIndeterminateBox(Context context) {
        CustomProgressBinDialog bar = CustomProgressBinDialog
                .newInstace(context,
                        CustomProgressBinDialog
                                .createBundle(
                                        CustomProgressBinDialog.EXCEL_ICON
                                ));
        bar.setMax(null);
        bar.show();
    }

    public static void progressBox(Context context) {
        int max = 1000;
        String headTitle = "Leyendo xls...";

        CustomProgressBinDialog bar = CustomProgressBinDialog
                .newInstace(context,
                        CustomProgressBinDialog
                                .createBundle(
                                        CustomProgressBinDialog.EXCEL_ICON
                                ));
        bar.show();

        bar.setTitle(headTitle);
        bar.setMax(max);
        bar.setTemp("[PlaceHolder]");

        for(int i = 0;i<max;++i){
            bar.setProgress(i);

            if(MathTools.isMultiple(i,100)){
                bar.addError("Error " + i);
            }
        }
    }
}
