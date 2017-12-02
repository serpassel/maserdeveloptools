package es.marser.maserdeveloptools.listables;

import android.os.Bundle;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import es.marser.backgroundtools.listables.base.controller.ExpandController;
import es.marser.tools.MathTools;

/**
 * @author sergio
 *         Created by sergio on 2/12/17.
 *         Test para controladora de expansión de listas
 *         <p>
 *         [EN]  Test for controller of list expansion
 */
@RunWith(AndroidJUnit4.class)
public class ExpandControllerTest {

    private List<Integer> data;
    private ExpandController controller;
    private ExpandController controller2;

    @Before
    public void setUp() throws Exception {
        data = new ArrayList<>();

        for (int i = 1; i < 50; ++i) {
            data.add(i);
        }
        controller = new ExpandController();
        controller2 = new ExpandController();

        //Seleccionar pares
        for (int i = 0; i < data.size(); ++i) {
            if (MathTools.isEven(data.get(i))) {
                controller.setExpand(i, true);
                controller2.setExpand(i, true);
            }
        }
    }

    @Test
    public void controllerTest() {
        Assert.assertTrue(controller.getIdExpaned().size() == 24);

        controller.setExpand(3, false);
        Assert.assertTrue(controller.getIdExpaned().size() == 23);

        controller.setExpand(2, false);
        Assert.assertTrue(controller.getIdExpaned().size() == 23);

        controller.clear();
        Assert.assertTrue(controller.getIdExpaned().size() == 0);

        controller.expandAll(data.size());
        Assert.assertTrue(controller.getIdExpaned().size() == data.size());

        controller.delete(5);
        Assert.assertFalse(controller.isExpaned(5));
        Assert.assertTrue(controller.getIdExpaned().size() == data.size() - 1);

    }

    @Test
    public void setController2Test() {
        Assert.assertTrue(controller2.getIdExpaned().size() == 24);
    }

    @Test
    public void savedAndRestored() {
        Bundle saved = new Bundle();
        String id = "0";
        String id2 = "1";
        controller.onSaveInstanceState(saved, id);
        controller.onRestoreInstanceState(saved, id);

        controller2.onSaveInstanceState(saved, id2);
        controller2.onRestoreInstanceState(saved, id2);

        Assert.assertTrue(controller.getIdExpaned().size() == 24);

        Assert.assertTrue(controller2.getIdExpaned().size() == 24);

    }

}