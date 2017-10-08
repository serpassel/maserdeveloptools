package es.marser.maserdeveloptools;

import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.marser.backgroundtools.toast.Launch_toast;

/**
 * @author sergio
 *         Created by sergio on 8/10/17.
 *         Dialogo de
 */
@SuppressWarnings({"CanBeFinal", "unused"})
@MediumTest
@RunWith(AndroidJUnit4.class)
class LaunchToastTest {

    @Rule
    public UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();

    @Rule
    public ActivityTestRule<DevelopTools> mActivityRule = new ActivityTestRule<>(
            DevelopTools.class);


    @Test
    public void toast1() {
        mActivityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Launch_toast.warningToast(mActivityRule.getActivity(), "Mensaje de advertencia");
            }
        });
        mActivityRule.getActivity().finish();
    }

    @Test
    public void toast2() {
        mActivityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Launch_toast.errorToast(mActivityRule.getActivity(), "Mensaje de error");
            }
        });
        mActivityRule.getActivity().finish();
    }

    @Test
    public void toast3() {
        mActivityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Launch_toast.informationToast(mActivityRule.getActivity(), "Mensaje de información");
            }
        });
        mActivityRule.getActivity().finish();
    }
}