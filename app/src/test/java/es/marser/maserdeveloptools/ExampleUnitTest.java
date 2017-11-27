package es.marser.maserdeveloptools;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

import es.marser.tools.SystemColor;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@SuppressWarnings("unused")
public class ExampleUnitTest {

    ArrayList<Integer> aint;

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Before
    public void init() {
        aint = new ArrayList<>();

        long temp = System.nanoTime();
        for (int i = 0; i < 12; ++i) {
            bucle5(1, aint);
            bucle6(7, aint);
            bucle7(42, aint);
        }

        long t1 = System.nanoTime() - temp;

        //   printTime(SystemColor.ANSI_YELLOW ,"Tiempo de carga ", t1);

    }

    @Deprecated
    @Test
    public void arrayListSortTemp() {
        //Ordenar Lista
        long temp = System.nanoTime();
        printTime(SystemColor.ANSI_YELLOW, "Comienzo ", temp);

        aint.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        //Tiempo de oredenamitento
        long t1 = System.nanoTime() - temp;
        // printTime(SystemColor.ANSI_GREEN, "Order Time ", t1);
        //Buscar los 7

        int firstIndex = aint.indexOf(7);
        int lastIndex = aint.lastIndexOf(7);
        System.out.println(SystemColor.ANSI_BLUE + "índices " + firstIndex + "/" + lastIndex);

        long t2 = System.nanoTime() - temp;
        // printTime(SystemColor.ANSI_CYAN, "Locate Index ", t2);

        //List<Integer> sub = aint.subList(firstIndex, lastIndex);

        //long t3 = System.nanoTime() - temp;
        printTime(SystemColor.ANSI_PURPLE, "Order ", t2);
    }

    @Test
    public void arrayList() {
        //Ordenar Lista
        long temp = System.nanoTime();
        printTime(SystemColor.ANSI_YELLOW, "Comienzo ", temp);

        //Buscar los 7
        int firstIndex = aint.indexOf(7);
        int lastIndex = aint.lastIndexOf(7);
        System.out.println(SystemColor.ANSI_BLUE + "índices " + firstIndex + "/" + lastIndex);

        Assert.assertEquals(countFindIndexOcurrences(7), 12*42);
        long t2 = System.nanoTime() - temp;
        printTime(SystemColor.ANSI_CYAN, "Find 7 ", t2);

        Assert.assertEquals(countFindIndexOcurrences(6), 12*7);
         t2 = System.nanoTime() - temp;
        printTime(SystemColor.ANSI_CYAN, "Find 6 ", t2);
    }

    private int countFindIndexOcurrences(Integer in) {
        long temp = System.nanoTime();
        int count = 0;
        for (int i = 0; i <= aint.lastIndexOf(in); ++i) {

            if(Objects.equals(aint.get(i), in)){
                ++count;
            }
        }

        long t2 = System.nanoTime() - temp;
        printTime(SystemColor.ANSI_CYAN, "desOrder ", t2);
        return count;
    }

    private void bucle5(int iter, ArrayList<Integer> in) {
        for (int i = 0; i < iter; ++i) {
            in.add(5);
        }
    }

    private void bucle6(int iter, ArrayList<Integer> in) {
        for (int i = 0; i < iter; ++i) {
            in.add(6);
        }
    }

    private void bucle7(int iter, ArrayList<Integer> in) {
        for (int i = 0; i < iter; ++i) {
            in.add(7);
        }
    }

    private void printTime(String color, String tag, long time) {
        double dtime = (double) time / 1000000.0;
        System.out.println(color + tag + dtime);
    }

}