package es.marser.maserdeveloptools.listables;

import android.os.Bundle;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import es.marser.LOG_TAG;
import es.marser.backgroundtools.listables.base.controller.SelectionController;
import es.marser.backgroundtools.listables.base.listeners.OnItemChangedListener;
import es.marser.tools.MathTools;

/**
 * @author sergio
 *         Created by sergio on 2/12/17.
 *         Test para controladora de la selección de listas
 *         <p>
 *         [EN]  Test for controller of list selection
 */
@RunWith(AndroidJUnit4.class)
public class SelectionControllerTest {

    private List<Integer> data;
    private SelectionController controller;
    private SelectionController controller2;
    private OnItemChangedListener listener;

    @Before
    public void setUp() throws Exception {
        data = new ArrayList<>();

        for (int i = 1; i < 50; ++i) {
            data.add(i);
        }
        controller = new SelectionController();
        Assert.assertTrue(controller.isEmptySelected());
        controller2 = new SelectionController();

        //Seleccionar pares
        for (int i = 0; i < data.size(); ++i) {
            if (MathTools.isEven(data.get(i))) {
                controller.inputSelected(i, true);
                controller2.setSelected(i, true);
            }
        }

        controller.setOnSelectionChanged(listener);

        listener = new OnItemChangedListener() {
            @Override
            public void onItemChaged(int position) {
                Log.v(LOG_TAG.TAG, "Cambio " + position);
            }

            @Override
            public void onSelectionChanged() {
                Log.v(LOG_TAG.TAG, "Cambio de selección");
            }
        };
    }

    @Test
    public void controllerTest() {
        Assert.assertTrue(controller.getIdSelecteds().size() == 24);
     //   Log.d(LOG_TAG.TAG, "LastPosiciotn " + data.get(controller.getIdSelected()));
        Assert.assertTrue(data.get(controller.getIdSelected()) == 48);
        Assert.assertTrue(controller.getLastposition() == 45);

        controller.deselectedAll();
        Assert.assertTrue(controller.getIdSelecteds().size() == 0);

        Assert.assertTrue(controller.isEmptySelected());

        controller.selectedAll(data.size());
        Assert.assertTrue(controller.getIdSelecteds().size() == data.size());

        controller.clear();
        Assert.assertTrue(controller.isEmptySelected());

        controller.selectedAll(data.size());
        controller.delete(5);
        Assert.assertFalse(controller.get(5));
        Assert.assertTrue(controller.getIdSelecteds().size() == data.size()-1);

        controller.setSelected(3, false);
        Assert.assertTrue(controller.getIdSelecteds().size() == data.size()-2);

        controller.setSelected(2, false);
        Assert.assertTrue(controller.getIdSelecteds().size() == data.size()-2);
    }

    @Test
    public void setController2Test() {
        Assert.assertTrue(controller2.getIdSelecteds().size() == 24);
        Assert.assertTrue(data.get(controller2.getIdSelected()) == 2);
        Assert.assertEquals(-1, controller2.getLastposition());
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

        Assert.assertTrue(controller.getIdSelecteds().size() == 24);
        Assert.assertTrue(data.get(controller.getIdSelected()) == 48);
        Assert.assertEquals(45, controller.getLastposition());

        Assert.assertTrue(controller2.getIdSelecteds().size() == 24);
        Assert.assertTrue(data.get(controller2.getIdSelected()) == 2);
        Assert.assertEquals(-1, controller2.getLastposition());
    }

}