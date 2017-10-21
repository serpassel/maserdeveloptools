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

    @Test
    public void dialog6() {
        mActivityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseDialog bd6;
                //6
                bd6 = DialogExample.notificationInformation(mActivityRule.getActivity());
                Assert.assertTrue(bd6.isShowing());
                Log.i(LOG_TAG.TAG, "BaseDialog 6");
                bd6.close();
            }
        });
        mActivityRule.getActivity().finish();
    }

    @Test
    public void dialog7() {
        mActivityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseDialog bd7;
                //7
                bd7 = DialogExample.notificationError(mActivityRule.getActivity());
                Assert.assertTrue(bd7.isShowing());
                Log.i(LOG_TAG.TAG, "BaseDialog 7");
                bd7.close();
            }
        });
        mActivityRule.getActivity().finish();
    }

    @Test
    public void dialog8() {
        mActivityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseDialog bd8;
                //8
                bd8 = DialogExample.notificationWarning(mActivityRule.getActivity());
                Assert.assertTrue(bd8.isShowing());
                Log.i(LOG_TAG.TAG, "BaseDialog 8");
                bd8.close();
            }
        });
        mActivityRule.getActivity().finish();
    }

    @Test
    public void dialog9() {
        mActivityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseDialog bd9;
                //9
                bd9 = DialogExample.notificationHelp(mActivityRule.getActivity());
                Assert.assertTrue(bd9.isShowing());
                Log.i(LOG_TAG.TAG, "BaseDialog 9");
                bd9.close();
            }
        });
        mActivityRule.getActivity().finish();
    }

    @Test
    public void dialog10() {
        mActivityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseDialog bd10;
                //10
                bd10 = DialogExample.notificationConfirmation(mActivityRule.getActivity());
                Assert.assertTrue(bd10.isShowing());
                Log.i(LOG_TAG.TAG, "BaseDialog 10");
                bd10.close();
            }
        });
        mActivityRule.getActivity().finish();
    }

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
}
