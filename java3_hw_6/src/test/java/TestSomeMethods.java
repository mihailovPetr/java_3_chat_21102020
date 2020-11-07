import org.junit.BeforeClass;
import org.junit.Test;

public class TestSomeMethods {

    static SomeMethods someMethods;
    @BeforeClass
    public static void init() {
        someMethods = new SomeMethods();
    }


    @Test(expected = IllegalArgumentException.class)
    public void method1Test1() {
        someMethods.method1(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void method1Test2() {
        someMethods.method1(new int[]{});
    }
    @Test(expected = RuntimeException.class)
    public void method1Test3() {
        someMethods.method1(new int[]{5, 2, 3, 6, 3, 1, 8, 2, 9});
    }
}
