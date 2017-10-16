package es.marser.sqltools;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * @author sergio
 *         Created by sergio on 16/10/17.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class CRUDHandlerTest {

    private CRUDHandler crudHandler;

    @Before
    public void setUp() throws Exception {
DatabaseSettings databaseSettings = new DatabaseSettings();
        crudHandler = new CRUDHandler(InstrumentationRegistry.getTargetContext(), databaseSettings);
    }

    @After
    public void tearDown() throws Exception {
        crudHandler.close();
    }

}