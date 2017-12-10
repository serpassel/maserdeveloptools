package es.marser.backgroundtools.widget.calendar.fragment;

import android.content.Context;

import es.marser.backgroundtools.containers.fragments.base.BaseFragmentList;
import es.marser.backgroundtools.widget.calendar.presenter.CalendarTablePresenter;

/**
 * @author sergio
 *         Created by sergio on 9/11/17.
 *         Fragmento selector de fechas
 *         <p>
 *         [EN]  Date selector fragment
 */
@SuppressWarnings("unused")
public class CalendarChooserFragment
        extends BaseFragmentList<CalendarTablePresenter> {

    //INSTANCE______________________________________
    public static CalendarChooserFragment newInstance() {

        return new CalendarChooserFragment();
    }

    /**
     * Called when a fragment is first attached to its context.
     * {@link #onCreate(Bundle)} will be called after this.
     *
     * @param context al que se adjunta el fragmento
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        CalendarTablePresenter presenter = new CalendarTablePresenter(context);
        setPresenter(presenter);
    }
}
