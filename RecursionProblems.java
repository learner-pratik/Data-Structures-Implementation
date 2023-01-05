public class RecursionProblems {

    // ------------ Helper functions -----------------
    void swap(int a, int b, int[] arr) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
        return;
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

}
