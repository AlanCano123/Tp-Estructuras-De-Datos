package com.demo.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/algoritmos")
public class AlgoritmosController {

    /* =======================
       ORDENACIÓN BÁSICA
       ======================= */

    @PostMapping("/ordenacion/insercion")
    public int[] insercion(@RequestBody ArrayRequest req) {
        int[] a = req.data();
        for (int i = 1; i < a.length; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= 0 && a[j] > key) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = key;
        }
        return a;
    }

    @PostMapping("/ordenacion/seleccion")
    public int[] seleccion(@RequestBody ArrayRequest req) {
        int[] a = req.data();
        for (int i = 0; i < a.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < a.length; j++)
                if (a[j] < a[min]) min = j;
            int temp = a[min];
            a[min] = a[i];
            a[i] = temp;
        }
        return a;
    }

    @PostMapping("/ordenacion/intercambio")
    public int[] intercambio(@RequestBody ArrayRequest req) {
        int[] a = req.data();
        for (int i = 0; i < a.length; i++)
            for (int j = i + 1; j < a.length; j++)
                if (a[i] > a[j]) {
                    int t = a[i]; a[i] = a[j]; a[j] = t;
                }
        return a;
    }

    /* =======================
       ORDENACIÓN AVANZADA
       ======================= */

    @PostMapping("/ordenacion/mergesort")
    public int[] mergeSort(@RequestBody ArrayRequest req) {
        int[] a = req.data();
        mergeSortRec(a, 0, a.length - 1);
        return a;
    }

    private void mergeSortRec(int[] a, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            mergeSortRec(a, l, m);
            mergeSortRec(a, m + 1, r);
            merge(a, l, m, r);
        }
    }

    private void merge(int[] a, int l, int m, int r) {
        int[] temp = new int[r - l + 1];
        int i = l, j = m + 1, k = 0;
        while (i <= m && j <= r)
            temp[k++] = (a[i] <= a[j]) ? a[i++] : a[j++];
        while (i <= m) temp[k++] = a[i++];
        while (j <= r) temp[k++] = a[j++];
        System.arraycopy(temp, 0, a, l, temp.length);
    }

    @PostMapping("/ordenacion/quicksort")
    public int[] quicksort(@RequestBody ArrayRequest req) {
        int[] a = req.data();
        quicksortRec(a, 0, a.length - 1);
        return a;
    }

    private void quicksortRec(int[] a, int low, int high) {
        if (low < high) {
            int p = partition(a, low, high);
            quicksortRec(a, low, p - 1);
            quicksortRec(a, p + 1, high);
        }
    }

    private int partition(int[] a, int low, int high) {
        int pivot = a[high];
        int i = low - 1;
        for (int j = low; j < high; j++)
            if (a[j] <= pivot) {
                i++;
                int t = a[i]; a[i] = a[j]; a[j] = t;
            }
        int t = a[i + 1]; a[i + 1] = a[high]; a[high] = t;
        return i + 1;
    }

    @PostMapping("/ordenacion/heapsort")
    public int[] heapsort(@RequestBody ArrayRequest req) {
        int[] a = req.data();
        int n = a.length;
        for (int i = n / 2 - 1; i >= 0; i--) heapify(a, n, i);
        for (int i = n - 1; i > 0; i--) {
            int t = a[0]; a[0] = a[i]; a[i] = t;
            heapify(a, i, 0);
        }
        return a;
    }

    private void heapify(int[] a, int n, int i) {
        int largest = i, l = 2*i+1, r = 2*i+2;
        if (l < n && a[l] > a[largest]) largest = l;
        if (r < n && a[r] > a[largest]) largest = r;
        if (largest != i) {
            int t = a[i]; a[i] = a[largest]; a[largest] = t;
            heapify(a, n, largest);
        }
    }

    /* =======================
       BÚSQUEDA
       ======================= */

    @PostMapping("/busqueda/secuencial")
    public int secuencial(@RequestBody SearchRequest req) {
        for (int i = 0; i < req.data().length; i++)
            if (req.data()[i] == req.key()) return i;
        return -1;
    }

    @PostMapping("/busqueda/binaria")
    public int binaria(@RequestBody SearchRequest req) {
        int[] a = req.data();

        //Ordenamiento usando libreri estandar de Java
        Arrays.sort(a);

        //Busqueda binaria manual
        int l = 0, r = a.length - 1;
        while (l <= r) {
            int m = (l + r) / 2;
            if (a[m] == req.key()) return m;
            if (a[m] < req.key()) l = m + 1;
            else r = m - 1;
        }
        return -1;
    }

    @PostMapping("/busqueda/interpolacion")
    public int interpolacion(@RequestBody SearchRequest req) {
        int[] a = req.data();
        int low = 0, high = a.length - 1;
        while (low <= high && req.key() >= a[low] && req.key() <= a[high]) {
            int pos = low + ((req.key() - a[low]) * (high - low)) / (a[high] - a[low]);
            if (a[pos] == req.key()) return pos;
            if (a[pos] < req.key()) low = pos + 1;
            else high = pos - 1;
        }
        return -1;
    }

}

