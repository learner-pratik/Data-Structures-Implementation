import java.util.Arrays;

public class Heap {

    void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // implementing min-heap
    void deleteHeapify(int[] arr, int s) {
        int smallest = s;
        int l = 2*s;
        int r = 2*s + 1;

        if (l <= arr[0] && arr[l] < arr[smallest]) smallest = l;
        if (r <= arr[0] && arr[r] < arr[smallest]) smallest = r;

        if (smallest != s) {
            swap(arr, s, smallest);
            deleteHeapify(arr, smallest);
        }
    }

    // implementing min-heap
    void insertHeapify(int[] arr, int m) {
        int par = m/2;
        if (par != 0 && arr[par] > arr[m]) {
            swap(arr, par, m);
            insertHeapify(arr, par);
        }
    }

    void delete(int[] arr) {
        int last = arr[arr[0]];
        arr[1] = last;
        arr[arr[0]] = 0;
        arr[0]--;
        deleteHeapify(arr, 1);
    }

    void insert(int[] arr, int val) {
        arr[0]++;
        arr[arr[0]] = val;
        insertHeapify(arr, arr[0]);
    }

    public static void main(String[] args) {
        int MAX = 20;
        int[] heap = new int[MAX];

        heap[0] = 9;
        heap[1] = 2;
        heap[2] = 3;
        heap[3] = 4;
        heap[4] = 5;
        heap[5] = 6;
        heap[6] = 7;
        heap[7] = 8;
        heap[8] = 9;
        heap[9] = 10;

        Heap heapObj = new Heap();
        heapObj.insert(heap, 1);
        System.out.println(Arrays.toString(heap));

        heapObj.delete(heap);
        System.out.println(Arrays.toString(heap));
    }
}