import java.util.Arrays;

public class SomeMethods {

    public int[] method1(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Аргумент не должен быть null");
        }
        if (arr.length == 0) {
            throw new IllegalArgumentException("Передан пустой массив в качестве аргумента");
        }

        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i] == 4) {
                return Arrays.copyOfRange(arr, i + 1, arr.length);
            }
        }

        throw new RuntimeException("В переданном массиве нет четверок");
    }

    public boolean method2(int[] arr) {

        boolean contain1 = false;
        boolean contain4 = false;

        for (int i = 0; i < arr.length; i++) {
            switch (arr[i]) {
                case 1:
                    contain1 = true;
                    break;
                case 4:
                    contain4 = true;
                    break;
                default:
                    return false;
            }
        }

        if (contain1 && contain4) {
            return true;
        } else {
            return false;
        }

    }
}

