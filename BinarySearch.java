public class BinarySearch {

    int binarySearch(int[] arr, int l, int h, int val) {
        if (l > h) return -1;
        int mid = (l+h)/2;
        if (arr[mid] == val) return mid;
        if (val < arr[mid]) return binarySearch(arr, l, mid-1, val);
        else return binarySearch(arr, mid+1, h, val);
    }

    public static void main(String[] args) {
        BinarySearch bSearch = new BinarySearch();
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7};
        System.out.println(bSearch.binarySearch(arr, 0, 6, 6));
    }
}