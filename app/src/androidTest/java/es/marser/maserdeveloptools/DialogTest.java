package es.marser.maserdeveloptools;

import android.support.test.filters.MediumTest;
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

/**
 * @author sergio
 *         Created by sergio on 8/10/17.
 *         Test de cuadros de dialogos
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class DialogTest {

    @Rule
    public UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();

    @Rule
    public ActivityTestRule<DevelopTools> mActivityRule = new ActivityTestRule<>(
            DevelopTools.class);


    @Test
    public void dialog1() {
        mActivityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseDialog bd1;
                //1
                bd1 = DialogExample.indeterminateBox(mActivityRule.getActivity());
                Assert.assertTrue(bd1.isShowing());
                Log.i(LOG_TAG.TAG, "BaseDialog 1");
                bd1.close();
            }
        });
        mActivityRule.getActivity().finish();
    }

    @Test
    public void dialog2() {
        mActivityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseDialog bd2;
                //2
                bd2 = DialogExample.indeterminateBox(mActivityRule.getActivity());
                Assert.assertTrue(bd2.isShowing());
                Log.i(LOG_TAG.TAG, "BaseDialog 2");
                bd2.close();
            }
        });
        mActivityRule.getActivity().finish();
    }

    @Test
    public void dialog3() {
        mActivityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseDialog bd3;
                //3
                bd3 = DialogExample.progressIndeterminateBox(mActivityRule.getActivity());
                Assert.assertTrue(bd3.isShowing());
                Log.i(LOG_TAG.TAG, "BaseDialog 3");
                bd3.close();
            }
        });
        mActivityRule.getActivity().finish();
    }

    @Test
    public void dialog4() {
        mActivityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseDialog bd4;
                //4
                bd4 = DialogExample.progressBox(mActivityRule.getActivity());
                Assert.assertTrue(bd4.isShowing());
                Log.i(LOG_TAG.TAG, "BaseDialog 4");
                bd4.close();
            }
        });
        mActivityRule.getActivity().finish();
    }

    @Test
    public void dialog5() {
        mActivityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseDialog bd5;
                //5
                bd5 = DialogExample.editGeneric(mActivityRule.getActivity());
                Assert.assertTrue(bd5.isShowing());
                Log.i(LOG_TAG.TAG, "BaseDialog 5");
                bd5.close();
            }
        });
        mActivityRule.getActivity().finish();
    }
}
