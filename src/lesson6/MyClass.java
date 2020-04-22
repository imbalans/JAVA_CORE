package lesson6;

import java.util.Arrays;

public class MyClass {
    public static void main(String[] args) {
        // ex. 2
        System.out.println(Arrays.toString(checkArray(new int[]{1, 2, 3, 4, 5, 6, 7})));
        System.out.println(Arrays.toString(checkArray(new int[]{1, 2, 3, 5, 6, 7, 8, 9, 4})));
        System.out.println(Arrays.toString(checkArray(new int[]{1, 2, 3, 5, 6, 7, 8})));

        // ex. 3
        System.out.println(checkArray2(new int[]{1, 2, 3, 4, 5}));
        System.out.println(checkArray2(new int[]{1, 1, 1, 4, 4, 4}));
        System.out.println(checkArray2(new int[]{1, 1}));
        System.out.println(checkArray2(new int[]{4, 4}));
    }

    public static int[] checkArray(int[] arr) {
        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i] == 4) {
                return Arrays.copyOfRange(arr, i + 1, arr.length);
            }
        }
        throw new RuntimeException("В массиве отсутствует цифра 4 !!!!");
    }

    public static boolean checkArray2(int[] arr){
        boolean has1 = false;
        boolean has4 = false;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 1 && arr[i] != 4) {
                return false;
            }
            if (arr[i] == 1) {
                has1 = true;
            }
            if (arr[i] == 4) {
                has4 = true;
            }
        }
        return has1 & has4;
    }
}
