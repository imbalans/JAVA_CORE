package lesson1;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // Ex. 01
        Integer[] intArr = {10, 20};
        System.out.println(Arrays.toString(intArr));
        swap(intArr, 0, 1);
        System.out.println(Arrays.toString(intArr));

        // Ex. 02
        toArrayList(intArr);
        System.out.println(Arrays.toString(intArr));

        //Ex. 03
        Apple apple1 = new Apple(); // 1
        Apple apple2 = new Apple(); // 1
        Apple apple3 = new Apple(); // 1

        Orange orange1 = new Orange(); // 1.5
        Orange orange2 = new Orange(); // 1.5

        Box<Apple> box1 = new Box<>(apple1, apple2, apple3);
        Box<Orange> box2 = new Box<>(orange1, orange2);

        System.out.println(box1.compare(box2));

        Box<Orange> box3 = new Box<Orange>();
        box2.transfer(box3);

    }

    public static void swap(Object[] arr, int index1, int index2){
        Object change = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = change;
    }

    public static <T> ArrayList toArrayList(T[] arr){
        return new ArrayList<>(Arrays.asList(arr));
    }
}

