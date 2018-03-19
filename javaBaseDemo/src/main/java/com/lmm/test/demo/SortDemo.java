package com.lmm.test.demo;

import java.util.Arrays;

/**
 * Created by Administrator on 2018/3/19.
 */
public class SortDemo {
    public static void insertSort(int[] a) {
        if (a == null || a.length < 2) {
            return;
        }
        for (int i = 1; i < a.length; i++) {
            int temp = a[i];
            int j;
            for (j = i; j > 0 && temp < a[j - 1]; j--) {
                a[j] = a[j - 1];
            }
            a[j] = temp;
        }
    }

    public static void selectSort(int[] a) {
        if (a == null || a.length < 2) {
            return;
        }
        for (int i = 0; i < a.length - 1; i++) {
            int index = i, j;
            for (j = i + 1; j < a.length; j++) {
                if (a[j] < a[index]) {
                    index = j;
                }
                //交换，i和index
                a[i] = a[i] + a[index];
                a[index] = a[i] - a[index];
                a[i] = a[i] - a[index];
            }
        }
    }

    public static void bubbleSort(int[] a) {
        if (a == null || a.length < 2) {
            return;
        }
        for (int i = 0; i < a.length - 1; i++) {
            int j;
            for (j = i + 1; j < a.length; j++) {
                if (a[j] < a[i]) {
                    a[i] = a[i] + a[j];
                    a[j] = a[i] - a[j];
                    a[i] = a[i] - a[j];
                }
            }
        }
    }

    public static void fastSort(int[] a, int low, int hight) {
        if (a == null || a.length < 2) {
            return;
        }
        int i = low, j = hight;
        if (hight > low) {
            int key = a[low];
            while (low < hight) {
                while (hight > low && a[hight] >= key) {
                    hight--;
                }
                a[low] = a[hight];
                while (hight > low && a[low] <= key) {
                    low++;
                }
                a[hight] = a[low];
            }
            a[low] = key;
            fastSort(a, i, low - 1);
            fastSort(a, low + 1, j);
        }
    }

    public static void main(String[] args) {
        int[] a = {52, 63, 1, 25, 96, 0, 23, 84, 12};
        //insertSort(a);
        //selectSort(a);
        //bubbleSort(a);
        fastSort(a, 0, a.length - 1);
        System.out.println(Arrays.toString(a));
    }
}
