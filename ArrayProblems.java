import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ArrayProblems {

    // ------------ Helper functions -----------------
    void swap(int a, int b, int[] arr) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
        return;
    }

    void reverseArray(int left, int right, int[] arr) {
        if (left >= right) return;
        swap(left, right, arr);
        reverseArray(left+1, right-1, arr);
    }
    
    // -------------- Arrays ------------------
    int secondLargest(int[] arr) {
        // For each element check whether it is greater than current largest or second largest element
        // If greater than largest  element, update both largest and second largest
        // else if greater than second largest, update only the second largest
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
        // Idea is keep a pointer which only moves when array elements are different
        // And replaces pointer element with current element
        int i = 0;
        for (int j = 1; j < arr.length; j++) {
            if (arr[j] != arr[i]) arr[++i] = arr[j];
        }
        return i+1;
    }

    void rotateArrayRight(int[] arr, int k) {
        // Idea is to reverse the parts of the array followed by the entire array
        // Main array can be reversed before or after reversing the parts
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
        // Idea is shift non zero elements to the end
        // And then add zeros at the remaining positions
        int j = -1, n = arr.length;
        for (int i = 0; i < n; i++) {
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
        // Idea is to add the smaller element in the new list
        // During adding we also need to check for duplicates, so check if curr elem is equal to previous elem or not
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
        // Idea os to add element is presnt in both arrays
        // Also to avoid duplicates, check if last elem in the list is equal to curr elem which we will add
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

    int[] repeatingAndMissingNumber(int[] arr) {
        // Idea is to use linear equations
        // Calculate sum of N nums and sum of N squared nums
        // Similarly calculate arr sum and arr squared sum
        // Find a+b and a-b and then use this to find a, b
        int N = arr.length;

        int sumNums, sumSquaredNums;
        sumNums = N*(N+1)/2;
        sumSquaredNums = (N*(N+1)*(2*N+1))/6;

        int arrSum = 0, arrSquaredSum = 0;
        for (int a : arr) {
            arrSum += a;
            arrSquaredSum += a*a;
        }

        int aMinusB = arrSum-sumNums;
        int aPlusB = (arrSquaredSum-sumSquaredNums)/aMinusB;

        int a = (aPlusB+aMinusB)/2;
        int b = Math.abs(aPlusB-aMinusB)/2;

        return new int[]{a, b};
    }

    int[] repeatingAndMissingNumberXor(int[] arr) {
        // Above method works but large calculations can cause overflow
        // Therefore other idea is to use properties of xor
        // We first xor all array elems and then xor this res with nums from 1-n
        // We will get xor of a^b, elems which we need to find
        // Now we know that 1^0 = 1, therefore we find the rightmost set bit in xor res
        // (n&~(n-1)) always return the binary no containing rightmost set bit as 1
        // Once we get that we know either a or b has that rightmost set bit
        // Thus we divide arr elems into two buckets and we add elems based on whether this bit is set or not
        // Again we add elems from 1-n based on same set bit
        // Xoring these buckets gives us the nums a and b

        int a = 0, b = 0, xor = 0;
        int n = arr.length;

        for (int num : arr) xor ^= num;
        for (int i = 1; i <= n; i++) xor ^= i;

        int setBitNum = xor & ~(xor-1);

        for (int num : arr) {
            if ((num & setBitNum) != 0) a ^= num;
            else b ^= num;
        }
        for (int i = 1; i <= n; i++) {
            if ((i & setBitNum) != 0) a ^= i;
            else b ^= i;
        }

        return new int[]{a, b};
    }

    int maxConsecutiveOnes(int[] arr) {
        // Idea is keep track of current len and reset it when zero
        // Also update max len when resetting curr len
        
        int mxLen = 0, currLen = 0;
        for (int a : arr) {
            if (a == 0) {
                mxLen = Math.max(mxLen, currLen);
                currLen = 0;
            }
            else currLen++;
        }
        mxLen = Math.max(mxLen, currLen);
        return mxLen;
    }

    int longestSubarrayWithSumK(int[] arr, int K) {
        // Idea is to use sliding window technique
        // Keep incrementing right end of window until you reach sum k
        // If window sum equals k, then update max len
        // increment start of wind and subtract that num from sum
        
        int start = 0, end = -1, currSum = 0, n = arr.length, mxLen = 0;;
        while (start < n) {
            
            while ((end+1) < n && currSum+arr[end+1] <= K) currSum += arr[++end];
            
            if (currSum == K) mxLen = Math.max(mxLen, end-start+1);
            
            currSum -= arr[start++];
        }

        return mxLen;
    }

    int smallestSubArrayWithSumK(int[] arr, int K) {
        // idea is to use sliding window technique

        int MX_NUM = 1_00_00_1, n = arr.length;
        int start = 0, end = 0, currSum = 0, minLen = MX_NUM;

        while (start < n) {
            while (end < n && currSum < K) currSum += arr[end++];

            if (currSum >= K) minLen = Math.min(minLen, end-start);

            currSum -= arr[start++];
        }

        if (minLen==MX_NUM) return 0;
        return minLen;
    }

    int subArraysWithProductLessThanK(int[] arr, int K) {
         // idea is to use sliding window technique

        int n = arr.length, cnt = 0;
    
        for (int start = 0; start < n; start++) {
            int end = start, currProd = 1;

            while (end < n && currProd*arr[end] < K) {
                currProd *= arr[end];
                cnt++; end++;
            }
        }

        return cnt;
    }

    int subArraysWithSumK(int[] arr, int K) {
        // Similar idea as longest subarray sum
        // Increment count when window sum equals k
        int start = 0, end = 0, currSum = 0, cnt = 0, n = arr.length;
        while (start < n) {
            while (end < n && currSum + arr[end] <= K) currSum += arr[end++];
            if (currSum == K) cnt++;
            currSum -= arr[start++];
        }
        return cnt;
    }

    int missingNumber(int[] arr) {
        // Use basic math
        int N = arr.length+1;
        int sumNums = N*(N+1)/2;
        int arrSum  = 0;
        for (int a : arr) arrSum += a;
        return sumNums - arrSum;
    }

    boolean searchSortedMatrix(int[][] arr, int key) {
        // Idea is to use binary search since all rows lined one after another form a sorted array
        int m = arr.length, n = arr[0].length;
        int low = 0, high = m*n-1;
        while (low <= high) {
            int mid = (low+high)/2;
            if (arr[mid/n][mid%n] == key) return true;
            if (key < arr[mid/n][mid%n]) high = mid-1;
            else low = mid+1;
        }
        return false;
    }

    // Question - Array where every elem appears twice except one, find that single number
    int singleNumber(int[] arr) {
        // Do xor of all elems, all doubly occuring numbers will become 0 and single num remains
        int ans = 0;
        for (int a : arr) ans ^= a;
        return ans;
    }

    int rowWithMax1s(int[][] arr) {
        // Idea is to search for each col and return row which has first one
        int n = arr.length, m = arr[0].length;
        for (int c = 0; c < m; c++) {
            for (int r = 0; r < n; r++) {
                if (arr[r][c] == 1) return r;
            }
        }
        return -1;
    }

    int[] twoSum(int[] arr, int trgt) {
        // Use hashing to store arr values and then check if trgt - arr[i] exists in hash table
        Map<Integer, Integer> hashTable = new HashMap<>();
        for (int i = 0; i < arr.length; i++) hashTable.put(arr[i], i);

        int[] res = null;
        for (int i = 0; i < arr.length; i++) {
            if (hashTable.containsKey(trgt-arr[i])) {
                res = new int[]{i, hashTable.get(trgt-arr[i])};
            }
        }
        return res;
    }

    int[] twoSumSorted(int[] arr, int trgt) {
        // Using two pointer approach
        int low = 0, high = arr.length-1;
        while (low < high) {
            if (arr[low] + arr[high] == trgt) return new int[]{low, high};
            else if (arr[low] + arr[high] < trgt) low++;
            else high--;
        }
        return new int[]{};
    }

    // [2,0,2,1,1,0]
    void sortArray0s1s2s(int[] arr) {
        // Idea is have three pointers low mid and high
        // And we swap the values based on val at mid position
        // We increment mid along with low because mid and low start from same index, therefore we do not want to come before low
        // thus from index 0 to low-1, we have 0s, from low to mid-1, we have 1s and from mid to high we have 2s
        int low = 0, mid = 0, high = arr.length-1;
        while (mid <= high) {
            if (arr[mid] == 0) {
                swap(low, mid, arr);
                low++; mid++;
            } else if (arr[mid] == 1) mid++;
            else {
                swap(mid, high, arr);
                high--;
            }
        }
    }

    // [2,2,1,1,1,2,2]
    int majorityElement(int[] arr) {
        // Idea is that since majority elem occurs more than N/2 time
        // majority elem will occur N/2+x times
        // therefore when we traverse the array and keep track of cnt and majority, at the end we will encounter our ans
        int cnt = 0, majority = 0;
        for (int a : arr) {
            if (cnt == 0) majority = a;
            
            if (a == majority) cnt++;
            else cnt--;
        }
        return cnt;
    }

    int maxSubArraySum(int[] arr) {
        // Idea is to keep track of max sum found so far and curr sum
        // If curr sum becomes negative, we make curr sum as 0 as this wont contribute to final sum
        // If curr sum exceeds max sum, we update it
        int maxSum = Integer.MIN_VALUE, currSum = 0;
        for (int a : arr) {
            currSum += a;
            if (currSum > maxSum) maxSum = currSum;
            if (currSum < 0) currSum = 0;
        }
        return maxSum;
    }

    int maxProfit(int[] arr) {
        // Idea is assume that if we sell stock on each day, whats the max profit we can make for that day
        // Therefore we always keep track of min value found so far and use that to calculate profit
        int minVal = Integer.MIN_VALUE, maxProfit = 0;
        for (int i = 1; i < arr.length; i++) {
            minVal = Math.min(minVal, arr[i]);
            maxProfit = Math.max(maxProfit, arr[i]-minVal);
        }
        return maxProfit;
    }

    void rearrangeArray(int[] arr) {
        // Idea is to first store pos and neg elems in different arrays
        // And then insert in proper pos in original array
        int n = arr.length;

        int[] pos = new int[n/2];
        int[] neg = new int[n/2];

        int i = 0, j = 0;
        for (int a : arr) {
            if (a>=0) pos[i++] = a;
            else neg[j++] = a;
        }

        i = 0; j = 0;
        for (int k = 0; k < n; k++) {
            if (k%2==0) arr[k] = pos[i++];
            else arr[k] = neg[j++];
        }
    }

    void nextPermutation(int[] arr) {
        // Idea works on the fact that there is always increasing sequence of permutation
        // We first need to find breakpoint where sequence goes from increasing to decreasing
        // Traverse from end till we find elem less than next elem (at idx i)
        // Now swap this elem with the next greater elem
        // Finally reverse the remaining part of array from i+1
        
        int n = arr.length;
        
        int i = n-2;
        while (i>=0 && arr[i] >= arr[i+1]) i--;

        if (i >= 0) {
            int j = n-1;
            while (arr[j] <= arr[i]) j--;
            swap(i, j, arr);
        }

        reverseArray(i+1, n-1, arr);
    }

    Integer[] arrayLeaders(int[] arr) {
        // Idea is that if current elem is greater than max elem in right part of array, it is greater than all other elems
        // So traverse from end and always update the max elem
        ArrayList<Integer> list = new ArrayList<>();
        int n = arr.length;
        
        int currMx = Integer.MIN_VALUE;
        for (int i = n-1; i >= 0; i--) {
            if (arr[i] > currMx) list.add(arr[i]);
            currMx = Math.max(currMx, arr[i]); 
        }

        return list.toArray(new Integer[0]);
    }

    int longestSeqConsecutiveNums(int[] arr) {
        // Idea is to first hash all array values
        // Then we check for each elem if it is the first elem in seq i.e arr doesnt have elem-1
        // Then we calculate len of this seq by checking which consecutive elems exist
        Set<Integer> hashSet = new HashSet<>();
        for (int a : arr) hashSet.add(a);

        int longestSeq = 0;
        for (int num : arr) {
            if (!hashSet.contains(num-1)) {
                int currNum = num;
                int seq = 0;
                while (hashSet.contains(currNum)) {
                    currNum++;
                    seq++;
                }
                longestSeq = Math.max(seq, longestSeq);
            }
        }

        return longestSeq;
    }

    void setMatrixZero(int[][] arr) {
        // Idea is to store which rows and cols will be zero
        // We use the respective first row and col of arr for the same
        // Also have an additional var since first elem cannot represent both row and col 

        // first col to denote rows with zero
        // first row to denote cols with zero (first elem only used for row)
        boolean zeroCol = false;
        int m = arr.length, n = arr[0].length;
        for(int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (arr[r][c] == 0) {
                    arr[r][0] = 0;
                    if (c == 0) zeroCol = true;
                    else arr[0][c] = 0;
                }
            }
        }

        for (int r = m-1; r >= 0; r--) {
            for (int c = n-1; c >= 0; c--) {
                if (arr[r][0] == 0) arr[r][c] = 0;
                else {
                    if (c == 0 && zeroCol) arr[r][c] = 0;
                    else if (arr[0][c] == 0) arr[r][c] = 0;
                }
            }
        }
    }

    void rotateMatrix(int[][] arr) {
        int m = arr.length, n = arr[0].length;
        
        // calculate transpose
        for (int i = 0; i < m; i++) {
            for (int j = i; j < n; j++) {
                int tmp = arr[i][j];
                arr[i][j] = arr[j][i];
                arr[j][i] = tmp;
            }
        }

        // reverse each row
        for (int i = 0; i < m; i++) reverseArray(0, n-1, arr[i]);
    }

    void spiralMatrixTraversal(int[][] arr) {
        int m = arr.length, n = arr[0].length;
        int top = 0, bottom = m-1, left = 0, right = n-1;

        int i, j;
        while (top <= bottom && left <= right) {
            // traverse left to right
            i = left;
            while (i <= right) System.out.print(arr[top][i++]+", ");
            top++;

            // traverse top to bottom
            j = top;
            while (j <= bottom) System.out.print(arr[j++][right]+", ");
            right--;

            // traverse right to left
            i = right;
            while (i >= left) System.out.print(arr[bottom][i--]+", ");
            bottom--;

            // traverse bottom to top
            j = bottom;
            while (j >= top) System.out.print(arr[j--][left]+", ");
            left++;
        }
    }

    List<List<Integer>> pascalsTriangle(int n) {
        List<List<Integer>> resList = new ArrayList<>();
        
        List<Integer> row = null, prev = null;
        for (int i = 0; i < n; i++) {
            row = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i) row.add(1);
                else row.add(prev.get(j-1)+prev.get(j));
            }
            resList.add(row);
            prev = row;
        }

        return resList;
    }

    ArrayList<Integer> majorityElementNBy3Times(int[] arr) {
        // Idea is to extend Boyer Moore's Voting Algo
        // We know that there can be at max only two majoroty elems appearing in arr
        // Thus we initialize vars and run the algo
        // But again we need to traverse arr to check if obtained nums appears more than n/3 times

        int num1 = 0, num2 = 0, cnt1 = 0, cnt2 = 0;
        for (int a : arr) {
            if (a == num1) cnt1++;
            else if (a == num2) cnt2++;
            else if (cnt1 == 0) {
                num1 = a; cnt1++;
            } else if (cnt2 == 0) {
                num2 = a; cnt2++;
            } else {
                cnt1--; cnt2--;
            }
        }

        ArrayList<Integer> resList = new ArrayList<>();
        cnt1 = 0; cnt2 = 0;
        for (int a : arr) {
            if (num1 == a) cnt1++;
            else if (num2 == a) cnt2++;
        }

        if (cnt1 > arr.length/3) resList.add(num1);
        if (cnt2 > arr.length/3) resList.add(num2);
        return resList;
    }

    ArrayList<ArrayList<Integer>> tripletsSumToZero(int[] arr) {
        // Idea is to first sort the list
        // We can then use the two pointer approach
        // Fix one var i and on remaining arr, use two pointer technique to check if two nums exists whose sum equal to 0-arr[i]
        // Care must be taken to properly update low and high pointers in loop
        
        ArrayList<ArrayList<Integer>> resList = new ArrayList<>();
        int n = arr.length;

        Arrays.sort(arr);

        for (int i = 0; i < n-2; i++) {
            if (i == 0 || (i > 0 && arr[i] != arr[i-1])) {
                int low = i+1, high = n-1, val = 0 - arr[i];

                while (low < high) {
                    if (arr[low]+arr[high] == val) {
                        ArrayList<Integer> l = new ArrayList<>();
                        
                        l.add(arr[i]);
                        l.add(arr[low]);
                        l.add(arr[high]);

                        resList.add(l);

                        while (low < high && arr[low] == arr[low+1]) low++;
                        while (low < high && arr[high] == arr[high-1]) high--;

                        low++;
                        high--;
                    } else if (arr[low]+arr[high] < val) low++;
                    else high--;
                }
            }
        }

        return resList;
    }

    ArrayList<ArrayList<Integer>> quadsSumToTarget(int[] arr, int K) {
        // Fix two pointers and use two pointer technique to search remaining array
        
        ArrayList<ArrayList<Integer>> resList = new ArrayList<>();
        int n = arr.length;

        Arrays.sort(arr);

        for (int i = 0; i < n-3; i++) {
            if (i == 0 || (i > 0 && arr[i] != arr[i-1])) {

                for (int j = i+1; j < n-2; j++) {
                    if (j == i+1 || (j > i+1 && arr[j] != arr[j-1])) {

                        int low = j+1, high = n-1, trgt = K-arr[i]-arr[j];
                        while (low < high) {
                            
                            if (arr[low]+arr[high] == trgt) {
                                ArrayList<Integer> l = new ArrayList<>();
                        
                                l.add(arr[i]);
                                l.add(arr[j]);
                                l.add(arr[low]);
                                l.add(arr[high]);

                                resList.add(l);

                                while (low < high && arr[low] == arr[low+1]) low++;
                                while (low < high && arr[high] == arr[high-1]) high--;

                                low++;
                                high--;

                            } else if (arr[low]+arr[high] < trgt) low++;
                            else high--;
                        }

                    }
                }
            }
        }

        return resList;
    }

    int longestSubArrWithZeroSum(int[] arr) {
        // Intuition is based on fact that if we know sum[i,j] = S and sum[i, x] = S where i < x < j
        // we can conclude that sum[x+1, j] = 0
        // So we store prefix sum of every elem and if prefix sum of two elems is same, then sum of second part of subarr is zero
        // Also we do not update index of a sum if seen again because we need len of longest subarr

        HashMap<Integer, Integer> prefixSum = new HashMap<>();

        int mxLen = 0, currSum = 0;
        for (int i = 0; i < arr.length; i++) {
            currSum += arr[i];
            
            if (currSum == 0) mxLen = i+1;
            else {
                if (prefixSum.containsKey(currSum)) mxLen = Math.max(mxLen, i-prefixSum.get(currSum));
                else prefixSum.put(currSum, i);
            }

        }

        return mxLen;
    }

    int subArraysWithXorK(int[] arr, int K) {
        // Idea is to maintain frequency of prefix xors odf elems
        // If xor[i, j] = A and xor[i, x] = B and A*K = B, this means xor[x+1, j] = K
        // So calculate prefix xors and use that to find count

        HashMap<Integer, Integer> prefixXor = new HashMap<>();
        
        int cnt = 0, currXor = 0;
        for (int a : arr) {
            currXor ^= a;
            
            if (currXor == K) cnt++;
            else {
                if (prefixXor.containsKey(currXor ^ K)) cnt += prefixXor.get(currXor ^ K);
                else prefixXor.put(currXor, prefixXor.getOrDefault(currXor, 0)+1);
            }
        }

        return cnt;
    }

    int[][] mergeIntervals(int[][] arr) {
        // Here we assume we have sorted intervals
        // If not, then we need to sort the intervals and therefore better to use arrayList instead of arr
        
        ArrayList<ArrayList<Integer>> resList = new ArrayList<>();

        int n = arr.length;
        for (int i = 0; i < n; i++) {
            if (i < n-1 && arr[i][1] >= arr[i+1][0]) arr[i+1][0] = arr[i][0];
            else {
                ArrayList<Integer> l = new ArrayList<>();
                l.add(arr[i][0]); l.add(arr[i][1]);
                resList.add(l);
            }
        }

        int[][] resArr = new int[resList.size()][2];
        int j = 0;
        for (ArrayList<Integer> res : resList) {
            resArr[j][0] = res.get(0);
            resArr[j][1] = res.get(1);
            j++;
        }
        return resArr;
    }

    void mergeSortedArrays(int[] arr1, int[] arr2) {
        // Idea is based on using intuition of insertion sort
        // We traverse through first array and whenever we find elem greater than first elem in second array, we swap that
        // After swapping we sort the array
        // This works because since array is sorted, first elem of arr2 is always inserted in proper position

        int n = arr1.length;
        for (int i = 0; i < n; i++) {
            if (arr1[i] > arr2[0]) {
                int tmp = arr1[i];
                arr1[i] = arr2[0];
                arr2[0] = tmp;
            }

            Arrays.sort(arr2);
        }
    }

    int countInversions(int[] arr, int[] tmp, int left, int right) {
        // Idea is to use mergesort technique which in turn utilizes divide and conquer strategy
        // For our question we need to satisfy two conditions, indices i < j and elems arr[i] > arr[j]
        // When we divide arr into two parts, and compare elems in these parts, i < j always
        // So oly we need to check for second condition which we do in the merging step
        // Since the two halves are already sorted, if we find arr[i] > arr[j], then all i+1 till mid elems will be greater than arr[j]
        // And finally we add all the counts and return the ans

        int cnt = 0;
        
        if (left < right) {
            int mid = (left+right)/2;

            cnt += countInversions(arr, tmp, left, mid);
            cnt += countInversions(arr, tmp, mid+1, right);

            cnt += mergeInversions(arr, tmp, left, mid+1, right);
        }

        return cnt;
    }

    int mergeInversions(int[] arr, int[] tmp, int left, int mid, int right) {
        int invCnt = 0;
        int i = left, j = mid, k = left;

        while (i <= mid-1 && j <= right) {
            if (arr[i] <= arr[j]) tmp[k++] = arr[i++];
            else {
                tmp[k++] = arr[j++];
                invCnt += mid-i;
            }
        }

        while (i <= mid-1) tmp[k++] = arr[i++];
        while (j <= right) tmp[k++] = arr[j++];

        for (int m = left; m <= right; m++) {
            arr[m] = tmp[m];
        }

        return invCnt;
    }

    int countReversePairs(int[] arr, int[] tmp, int left, int right) {
        // Idea is similar to previous sum
        // Here only change will be with the merging step
        // Before merging, we will check for reverse pairs
        // For every ith elem, we increase j index as long as arr[i] > 2*arr[j], then we add those num of elems to cnt
        // We do not set j back to orginal pos because since ith elem already satisfied the cond, all i+1 elems will also satisfy condition
        // Thus we traverse arr only once

        int cnt = 0;

        if (left < right) {
            int mid = (left+right)/2;

            cnt += countReversePairs(arr, tmp, left, mid);
            cnt += countReversePairs(arr, tmp, mid+1, right);

            cnt += mergeReversePairs(arr, tmp, left, mid+1, right);
        }

        return cnt;
    }

    int mergeReversePairs(int[] arr, int[] tmp, int left, int mid, int right) {
        int revPairCnt = 0;

        int q = mid;
        for (int p = left; p <= mid-1; p++) {
            while (q <= right && arr[p] > 2*arr[q]) q++;
            revPairCnt += q-mid;
        }
        
        int i = left, j = mid, k = left;

        while (i <= mid-1 && j <= right) {
            if (arr[i] <= arr[j]) tmp[k++] = arr[i++];
            else tmp[k++] = arr[j++];
        }

        while (i <= mid-1) tmp[k++] = arr[i++];
        while (j <= right) tmp[k++] = arr[j++];

        for (int m = left; m <= right; m++) arr[m] = tmp[m];

        return revPairCnt;
    }

    int maxProductSubArray(int[] arr) {
        // Idea is to apply technique of kadanes algorithm
        // We know that prod of two large neg nums can also give us a postive num
        // Thus we store both max and min prod at each step and use that to compute res
        // Note that when comparing with max and min prod, we also compare with curr elem, this eleminates of 0 prod being carried forward
        
        int res = arr[0], prodMx = arr[0], prodMn = arr[0];

        for (int i = 1; i < arr.length; i++) {
            int tmp = Math.max(arr[i], Math.max(prodMx*arr[i], prodMn*arr[i]));
            prodMn = Math.min(arr[i], Math.min(prodMx*arr[i], prodMn*arr[i]));
            prodMx = tmp;

            res = Math.max(res, prodMx);
        }

        return res;
    }

}
