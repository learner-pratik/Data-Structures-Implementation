public class SortingProblems {

    // ------------ Helper functions -----------------
    void swap(int a, int b, int[] arr) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
        return;
    }
    
    // ----------- Sorting -------------------
    void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n-1; i++) {
            int minIndex = i, j = i+1;
            while (j < n) {
                if (arr[j] < arr[i]) minIndex = j;
                j++;
            }
            swap(minIndex, i, arr);
        }
    }

    void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            int flag = 0;
            for (int j = 0; j < n-i-1; j++) {
                if (arr[j] > arr[j+1]) {
                    swap(j, j+1, arr);
                    flag = 1;
                }
            }
            if (flag == 0) break;
        }
    }

    void insertionSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int val = arr[i], j = i-1;
            while (j >= 0 && arr[j] > val) {
                arr[j+1] = arr[j];
                j--;
            }
            arr[j+1] = val;
        }
    }

    void mergeSort(int low, int high, int[] arr) {
        if (low < high) {
            int mid = (low+high)/2;
            mergeSort(low, mid, arr);
            mergeSort(mid+1, high, arr);
            merge(low, mid, high, arr);
        }
    }

    void merge(int low, int mid, int high, int[] arr) {
        int[] temp = new int[high-low+1];
        int i = low, j = mid+1, k = 0;
        while (i <= low && j <= high) {
            temp[k++] = (arr[i] < arr[j]) ? arr[i++] : arr[j++];
        }
        while (i <= low) temp[k++] = arr[i++];
        while (j <= high) temp[k++] = arr[j++];
        for (int p = 0; p < k; p++) arr[p+low] = temp[p];
    }

    void quickSort(int low, int high, int[] arr) {
        if (low < high) {
            int pivot = partition(low, high, arr);
            quickSort(low, pivot-1, arr);
            quickSort(pivot+1, high, arr);
        }
    }

    int partition(int low, int high, int[] arr) {
        int pivot = arr[high];
        int i = low-1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(i, j, arr);
            }
        }
        i++;
        swap(i, high, arr);
        return i;
    }
}
