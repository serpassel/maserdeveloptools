package es.marser.maserdeveloptools;


import android.support.test.filters.LargeTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import es.marser.maserdeveloptools.backgroundtest.ChooserTest;
import es.marser.maserdeveloptools.backgroundtest.CredentialDialogTest;
import es.marser.maserdeveloptools.backgroundtest.DialogTest;
import es.marser.maserdeveloptools.backgroundtest.FileChooserTest;
import es.marser.maserdeveloptools.backgroundtest.FilePathUtilTest;
import es.marser.maserdeveloptools.backgroundtest.LaunchToastTest;
import es.marser.maserdeveloptools.backgroundtest.NotificationTest;
import es.marser.maserdeveloptools.backgroundtest.NotificationTest2;
import es.marser.maserdeveloptools.backgroundtest.ResourcesAccessTest;
import es.marser.maserdeveloptools.listables.ExpandControllerTest;
import es.marser.maserdeveloptools.listables.SelectionControllerTest;
import es.marser.maserdeveloptools.sqltools.CRUDHandlerTest;
import es.marser.maserdeveloptools.sqltools.CRUDHandlerTest2;

/**
 * @author sergio
 *         Created by sergio on 23/09/17.
 */
@LargeTest
@RunWith(Suite.class)
@Suite.SuiteClasses({
        DialogTest.class,
        LaunchToastTest.class,
        CRUDHandlerTest.class,
        CRUDHandlerTest2.class,
        NotificationTest.class,
        NotificationTest2.class,
        SelectionControllerTest.class,
        ExpandControllerTest.class,
        ResourcesAccessTest.class,
        FilePathUtilTest.class,
        FileChooserTest.class,
        CredentialDialogTest.class,
        ChooserTest.class
})
public class AndroidSuiteTest {

}
