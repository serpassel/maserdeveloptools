package es.marser.maserdeveloptools.backgroundtest;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.marser.LOG_TAG;
import es.marser.backgroundtools.containers.dialogs.bases.BaseDialog;
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
public class DialogCredentialTest {

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
                bd1 = DialogExample.credentialLogin(mActivityRule.getActivity());
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
                bd2 = DialogExample.credentialReauth(mActivityRule.getActivity());
                Assert.assertTrue(bd2.isShowing());
                Log.i(LOG_TAG.TAG, "BaseDialog 2");
                bd2.close();
            }
        });
        mActivityRule.getActivity().finish();
    }
}
