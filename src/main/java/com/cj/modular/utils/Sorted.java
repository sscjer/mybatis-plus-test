package com.cj.modular.utils;

import java.util.*;

/**
 * <p>
 *
 * </p>
 *
 * @author Caoj
 * @version 2022-08-21 14:22
 */
public class Sorted {
    public static void main(String[] args) {
        int[] arr = {234, 12, 456, 7, 44, 61, 89, 39, 51, 97};
        System.out.println(Arrays.toString(arr));
        sort(arr, 1, 1);
        System.out.println(Arrays.toString(arr));

        TreeSet<Sorted> sorteds = new TreeSet<>(new Comparator<Sorted>() {
            @Override
            public int compare(Sorted o1, Sorted o2) {
                return 0;
            }
        });
        new Thread(() -> System.out.println("test")).start();
        new ThreadLocal() {
            @Override
            protected Object initialValue() {
                return super.initialValue();
            }
        }.initialValue();
    }

    public static void sort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int insertNum = arr[i];
            int index = i - 1;
            while (index >= 0 && insertNum < arr[index]) {
                arr[index + 1] = arr[index];
                index--;
            }
            arr[index + 1] = insertNum;
        }
    }

    public static void sort(int[] arr, int low, int high) {
        int start = low;
        int end = high;
        int key = arr[low];
        while(end > start) {
            while(end > start && arr[end] >= key) {
                end--;
                if (arr[end] <= key) {
                    int temp = arr[end];
                    arr[end] = arr[start];
                    arr[start] = temp;
                }
            }
            while (end > start && arr[start] <= key) {
                //如果没有比关键值大的，比较下一个，直到有比关键值大的交换位置
                start++;
                if (arr[start] >= key) {
                    int temp = arr[start];
                    arr[start] = arr[end];
                    arr[end] = temp;
                }
            }
            if(start>low) {sort(arr,low,start-1);}//左边序列。第一个索引位置到关键值索引-1
            if(end<high) {sort(arr,end+1,high);}//右边序列。从关键值索引+1到最后一个
        }
    }
}
