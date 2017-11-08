package es.marser.maserdeveloptools.backgroundtest;


import android.support.test.filters.LargeTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import es.marser.backgroundtools.dialogs.widget.toast.Launch_toast;
import es.marser.backgroundtools.systemtools.FilePathUtil;

/**
 * @author sergio
 *         Created by sergio on 23/09/17.
 *         Suite de tests estáticos
 *         [EN] Static test suite
 */
@LargeTest
@RunWith(Suite.class)
@Suite.SuiteClasses({
        UriToolsTest.class,
        DialogTest.class,
        FileChooserTest.class,
        FilePathUtil.class,
        Launch_toast.class,
        NotificationTest.class,
        NotificationTest2.class,
        ResourcesAccessTest.class,
        DialogInputTest.class,
        ChooserTest.class

})
public class StaticSuiteTest {

}
