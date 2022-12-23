import java.util.ArrayList;

public class Notion {

    // ------------ Helper functions -----------------
    void swap(int a, int b, int[] arr) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
        return;
    }
    
    // --------- Math Problems ----------------
    int countDigits(int n) {
        if (n/10 == 0) return 1;
        return 1 + countDigits(n/10);
    }

    int countDigits2(int n) {
        return (int) Math.floor(Math.log10(n)+1);
    }

    // int range - [-2147483648 <=> 2147483647]
    int reverseInteger(int n) {
        int ans = 0, temp = n;
        while (temp != 0) {
            int x = temp%10;
            if (ans > Integer.MAX_VALUE/10 || (ans == Integer.MAX_VALUE && x > 7)) return 0;
            if (ans < Integer.MIN_VALUE/10 || (ans == Integer.MIN_VALUE && x < -8)) return 0;
            ans = ans*10 + x;
            temp /= 10;
        }
        return ans;
    }

    boolean palindromeNumber(int n) {
        if (n < 0) return false;
        char[] s = String.valueOf(n).toCharArray();
        int l = 0, r = s.length-1;
        while (l < r) {
            if (s[l] != s[r]) return false;
            l++; r--;
        }
        return true;
    }
 
    int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a%b);
    }

    int lcm(int a, int b) {
        return (int) (a*b)/gcd(a, b);
    }

    boolean armstrongNumber(int n) {
        int temp = n, ans = 0;
        int cnt = countDigits(temp);
        while (temp != 0) {
            int x = n%10;
            ans += (x == 0) ? 0 : (int) Math.pow(x, cnt);
            temp /= 10;
        }
        return (ans == n) ? true : false;
    }

    int sumOfDivisorsNumber(int n) {
        int ans = 0;
        for (int i = 1; i <= n; i++) ans += (n/i)*i;
        return ans;
    }

    boolean primeNumber(int n) {
        for (int i = 2; i <= (int) Math.sqrt(n); i++) {
            if (n%i == 0) return false;
        }
        return true;
    }

    // ------------- Recursion -------------------
    void printNumberAscending(int n) {
        if (n == 0) return;
        printNumberAscending(n-1);
        System.out.print(n+", ");
    }

    void primeNumbersDescending(int n) {
        if (n == 0) return;
        System.out.print(n+", ");
        primeNumbersDescending(n-1);
    }

    int sumOfNumbers(int n) {
        if (n == 1) return n;
        return n + sumOfNumbers(n-1);
    }

    int factorial(int n) {
        if (n == 1) return n;
        return n*factorial(n-1);
    }

    void reverseArray(int left, int right, int[] arr) {
        if (left >= right) return;
        swap(left, right, arr);
        reverseArray(left+1, right-1, arr);
    }

    boolean palindromeString(String s, int index) {
        if (index >= s.length()/2) return true;
        int n = s.length()-index-1;
        if (s.charAt(index) != s.charAt(n)) return false;
        return palindromeString(s, index+1);
    }

    int fibonacciNumber(int n) {
        if (n <= 1) return n;
        return fibonacciNumber(n-1)+fibonacciNumber(n-2);
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

    // -------------- Arrays ------------------
    int secondLargest(int[] arr) {
        int largest = Integer.MIN_VALUE, sLargest = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > largest) {
                sLargest = largest;
                largest = arr[i];
            }
            else if (arr[i] > sLargest && arr[i] != largest) sLargest = arr[i];
        }
        return sLargest;
    }

    int secondSmallest(int[] arr) {
        int smallest = Integer.MIN_VALUE, sSmallest = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < smallest) {
                sSmallest = smallest;
                smallest = arr[i];
            } else if (arr[i] < sSmallest && arr[i] != smallest) sSmallest = arr[i];
        }
        return sSmallest;
    }

    int removeDuplicatesSortedArray(int[] arr) {
        int i = 0;
        for (int j = 1; j < arr.length; j++) {
            if (arr[j] != arr[i]) arr[++i] = arr[j];
        }
        return i+1;
    }

    void rotateArrayRight(int[] arr, int k) {
        int n = arr.length;
        reverseArray(0, n-k-1, arr);
        reverseArray(k, n-1, arr);
        reverseArray(0, n-1, arr);
    }

    void rotateArrayLeft(int[] arr, int k) {
        int n = arr.length;
        reverseArray(0, k-1, arr);
        reverseArray(k, n-1, arr);
        reverseArray(0, n-1, arr);
    }

    void moveZeroesToEnd(int[] arr) {
        int j = 0, n = arr.length;
        for (int i = 1; i < n; i++) {
            if (arr[i] != 0) arr[++j] = arr[i];
        }
        for (int i = j+1; i < n; i++) arr[i] = 0;
    }

    int binarySearch(int[] arr, int val) {
        int l = 0, r = arr.length-1;
        while (l <= r) {
            int mid = (l+r)/2;
            if (arr[mid] == val) return mid;
            if (arr[mid] < val) l = mid+1;
            else r = mid-1;
        }
        return -1;
    }

    int[] unionSortedArrays(int[] arr1, int[] arr2) {
        ArrayList<Integer> aList = new ArrayList<Integer>();
        int i = 0, j = 0, l1 = arr1.length, l2 = arr2.length;
        while (i < l1 && j < l2) {
            if (arr1[i] < arr2[j]) {
                if (i == 0 || arr1[i-1] != arr1[i]) aList.add(arr1[i]);
                i++;
            }
            else if (arr1[i] > arr2[j]) {
                if (j == 0 || arr2[j-1] != arr2[j]) aList.add(arr2[j]);
                j++;
            }
            else {
                if (i == 0 || arr1[i-1] != arr1[i]) aList.add(arr1[i]);
                i++; j++;
            }
        }
        while (i < l1) {
            if (i == 0 || arr1[i-1] != arr1[i]) aList.add(arr1[i]);
            i++;
        }
        while (j < l2) {
            if (j == 0 || arr2[j-1] != arr2[j]) aList.add(arr2[j]);
            j++;
        }
        
        int[] ans = new int[aList.size()];
        for (int k = 0; k < aList.size(); k++) ans[k] = aList.get(k);
        return ans;
    }

    int[] insersectionSortedArrays(int[] arr1, int[] arr2) {
        ArrayList<Integer> aList = new ArrayList<Integer>();
        int i = 0, j = 0, l1 = arr1.length, l2 = arr2.length;

        while (i < l1 && j < l2) {
            if (arr1[i] == arr2[j]) {
                if (aList.isEmpty() || aList.get(aList.size()-1) != arr1[i]) {
                    aList.add(arr1[i]);
                }
                i++; j++;
            }
            if (arr1[i] < arr2[j]) i++;
            if (arr1[i] > arr2[j]) j++;
        }

        int[] ans = new int[aList.size()];
        for (int k = 0; k < aList.size(); k++) ans[k] = aList.get(k);
        return ans;
    }

}

