package es.marser.maserdeveloptools.backgroundtest;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.marser.LOG_TAG;
import es.marser.backgroundtools.dialogs.bases.BaseDialog;
import es.marser.maserdeveloptools.DevelopTools;
import es.marser.maserdeveloptools.DialogExample;

/**
 * @author sergio
 *         Created by sergio on 8/10/17.
 *         Test de cuadros de dialogos
 */
@SuppressWarnings("unused")
@LargeTest
@RunWith(AndroidJUnit4.class)
public class NotificationTest2 {

    @Rule
    public UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();

    @Rule
    public ActivityTestRule<DevelopTools> mActivityRule = new ActivityTestRule<>(
            DevelopTools.class);

    @Test
    public void dialog11() {
        mActivityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseDialog bd11;
                //11
                bd11 = DialogExample.notificationOkCancelError(mActivityRule.getActivity());
                Assert.assertTrue(bd11.isShowing());
                Log.i(LOG_TAG.TAG, "BaseDialog 11");
                bd11.close();
            }
        });
        mActivityRule.getActivity().finish();
    }

    @Test
    public void dialog12() {
        mActivityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseDialog bd12;
                //12
                bd12 = DialogExample.notificationYesNoCancelConfirmation(mActivityRule.getActivity());
                Assert.assertTrue(bd12.isShowing());
                Log.i(LOG_TAG.TAG, "BaseDialog 12");
                bd12.close();
            }
        });
        mActivityRule.getActivity().finish();
    }

    @Test
    public void dialog13() {
        mActivityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseDialog bd13;
                //13
                bd13 = DialogExample.notificationDeleteRecords(mActivityRule.getActivity());
                Assert.assertTrue(bd13.isShowing());
                Log.i(LOG_TAG.TAG, "BaseDialog 13");
                bd13.close();
            }
        });
        mActivityRule.getActivity().finish();
    }

    @Test
    public void dialog14() {
        mActivityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseDialog bd14;
                //14
                bd14 = DialogExample.notificationConfirmationKey(mActivityRule.getActivity());
                Assert.assertTrue(bd14.isShowing());
                Log.i(LOG_TAG.TAG, "BaseDialog 14");
                bd14.close();
            }
        });
        mActivityRule.getActivity().finish();
    }
}
