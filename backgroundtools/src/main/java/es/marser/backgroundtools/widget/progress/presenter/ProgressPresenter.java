package es.marser.backgroundtools.widget.progress.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import es.marser.backgroundtools.BR;
import es.marser.backgroundtools.R;
import es.marser.backgroundtools.bindingadapters.BinderContainer;
import es.marser.backgroundtools.containers.dialogs.presenter.DialogBasePresenter;
import es.marser.backgroundtools.widget.progress.ProgressBarUpdater;
import es.marser.backgroundtools.widget.progress.model.ProgressModel;
import es.marser.tools.MathTools;

/**
 * @author sergio
 *         Created by sergio on 6/12/17.
 *         Presentador para cuadros de progreso
 *         <p>
 *         [EN]  Presenter for progress pictures
 */

@SuppressWarnings("unused")
public class ProgressPresenter extends DialogBasePresenter implements ProgressBarUpdater {

    //MODELS______________________________________
    private ProgressModel progressmodel;

    //CONSTRUCTORS________________________________
    public ProgressPresenter(@NonNull Context context, int viewLayout) {
        super(context, viewLayout);
        this.progressmodel = new ProgressModel();
    }

    public ProgressPresenter(@NonNull Context context) {
        this(context, R.layout.mvp_dialog_progress);
    }

    //OVERRIDE______________________________________
    @Override
    public void onBindObjects(@NonNull BinderContainer binderContainer) {
        super.onBindObjects(binderContainer);
        binderContainer.bindObject(BR.progressmodel, progressmodel);
    }

    //PROPERTIES____________________________________
    @SuppressWarnings("unused")
    public ProgressModel getProgressmodel() {
        return progressmodel;
    }

    @SuppressWarnings("unused")
    public void setProgressmodel(ProgressModel progressmodel) {
        this.progressmodel = progressmodel;
    }

    //PROGRESS BAR UPDATER_________________________
    @Override
    public void setMax(@Nullable Integer max) {
        if (max == null || max < 0) {
            indeterminate(true);
        } else {
            indeterminate(false);
            progressmodel.max.set(String.valueOf(max));
        }
    }

    @Override
    public void indeterminate(boolean value) {
        progressmodel.indeterminate.set(value);
    }

    @Override
    public void setProgress(@NonNull Integer value) {
        if (!progressmodel.indeterminate.get()) {
            progressmodel.progress.set(String.valueOf(value));
            progressmodel.progresstext.set("Progreso: " + progressmodel.progress.get() + " de " + progressmodel.max.get());
        }
    }

    @Override
    public void increment(@NonNull Integer value) {

        int actual = MathTools.parseInt(progressmodel.progress.get()) + value;
        progressmodel.progress.set(String.valueOf(actual));
        if (!progressmodel.indeterminate.get()) {
            progressmodel.progresstext.set("Progreso: " + progressmodel.progress.get() + " de " + progressmodel.max.get());
        }
    }

    @Override
    public void autoIncrement() {
        increment(1);
    }

    @Override
    public void addError(@NonNull String error) {
        String in = progressmodel.error.get() + error + "\n";
        progressmodel.error.set(in);
    }
}
