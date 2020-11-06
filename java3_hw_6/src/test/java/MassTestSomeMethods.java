import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class MassTestSomeMethods {

    static SomeMethods someMethods;
    private int[] m1Arr;
    private int[] m1Res;
    private int[] m2Arr;
    private boolean m2Res;

    public MassTestSomeMethods(int[] m1Arr, int[] m1Res, int[] m2Arr, boolean m2Res) {
        this.m1Arr = m1Arr;
        this.m1Res = m1Res;
        this.m2Arr = m2Arr;
        this.m2Res = m2Res;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7}, new int[]{1, 7}, new int[]{1, 1, 1, 4, 4, 1, 4, 4}, true},
                {new int[]{5, 2, 3, 6, 4, 1, 8, 2, 9}, new int[]{1, 8, 2, 9}, new int[]{1, 1, 1, 1, 1, 1}, false},
                {new int[]{7, 5, 4, 7, 4, 6, 4, 1, 1}, new int[]{1, 1}, new int[]{4, 4, 4, 4}, false},
                {new int[]{4, 8, 7, 6, 5, 6, 3, 2, 1}, new int[]{8, 7, 6, 5, 6, 3, 2, 1}, new int[]{1, 4, 4, 1, 1, 4, 3}, false}
        });
    }

    @BeforeClass
    public static void init() {
        someMethods = new SomeMethods();
    }

    @Test
    public void testMethod1() {
        Assert.assertArrayEquals(m1Res, someMethods.method1(m1Arr));
    }

    @Test
    public void testMethod2() {
        Assert.assertEquals(m2Res, someMethods.method2(m2Arr));
    }
}
