import java.util.Arrays;

public class BinarySearch {

    int binarySearch(int[] arr, int l, int h, int val) {
        if (l > h) return -1;
        int mid = l + (h-l)/2;
        if (arr[mid] == val) return mid;
        if (val < arr[mid]) return binarySearch(arr, l, mid-1, val);
        else return binarySearch(arr, mid+1, h, val);
    }

    int searchInsert(int[] nums, int trgt) {
        int l = 0, r = nums.length-1;
        while (l < r) {
            int m = l + (r-l)/2;
            if (nums[m] == trgt) return m;
            else if (nums[m] < trgt) l = m+1;
            else r = m;
        }
        return nums[l] <= trgt ? l+1 : l;
    }

    int floorSortedArray(int[] nums, int trgt) {
        int l = 0, r = nums.length-1;
        while (l < r-1) {
            int m = l + (r-l)/2;
            if (nums[m] > trgt) r = m-1;
            else l = m;
        }
        return nums[r] <= trgt ? r : (nums[l] <= trgt ? l : -1);
    }

    // Question
    // Given an array of integers nums sorted in non-decreasing order, find the starting and ending position of a given target value.
    int[] searchRange(int[] nums, int target) {
        // Idea is to do two binary searches, one for first pos and another for last pos
        
        // For first pos, we can have two cases
        // if A[m] < target, then i = m+1, else if A[m] >= target then j = m
        // when loop terminates, the value of i/j is where the range starts
        // This is because, as we narrow our search range, we eventually reach a position when there are only two elems
        // And then eventually our i/j become equal at the target elem, if it doesn't exist, then we can simply return -1

        // Similar technique can be applied for last pos
        // if A[m] <= target, then i = m, else if A[m] > target, then j = m-1
        // Here the only change is the way we calculate m = (i+j)/2 + 1
        // This is because, when A[m]==target, we set i=m which would keep looping forever, as the old way of calculating m is biased toward i
        
        int[] res = new int[2];
        
        // calculating first pos
        int l = 0, r = nums.length-1;
        while (l < r) {
            int m = l + (r-l)/2;
            if (target > nums[m]) l = m+1;
            else if (target <= nums[m]) r = m;
        }
        if (nums[l] != target) return new int[]{-1, -1};
        else res[0] = l;

        // calculating last pos
        r = nums.length-1;
        while (l < r) {
            int m = l + (r-l)/2 + 1;
            if (target < nums[m]) r = m-1;
            else if (target >= nums[m]) l = m;
        }
        res[1] = r;

        return res;
    }

    int peakElement(int[] nums) {
        // O(n) logic
        // for (int i = 0; i < nums.length-1; i++) {
        //     if (nums[i] > nums[i+1]) return i;
        // }
        // return nums.length-1;

        // Idea is based on the fact that the array will contain ascending(rising) range and descending(falling) range
        // Therefore on which range our mid elem lies we can reduce our search space
        // If mid is greater then next elem, then it lies on falling slope, so peak lies to left of mid
        // If mid is less than next elem, then it lies on rising slope, so peak lies to the right
        // At the end we are just left with one elem which is our peak elem

        int l = 0, r = nums.length-1;
        while (l < r) {
            int m = l + (r-l)/2;
            if (nums[m] > nums[m+1]) r = m;
            else if (nums[m] < nums[m+1]) l = m+1;
        }
        return l;
    }

    int searchRotatedSortedArray(int[] nums, int target) {
        // Idea is to use modified binary search
        // After checking with mid elem, we try to find which part is sorted
        // If left part is sorted, we check if target lies in left, no, then we move to right
        // If not, then right part is sorted, so again we check if target lies in right, if not, we move to left

        int l = 0, r = nums.length-1;
        while (l <= r) {
            int m = l + (r-l)/2;
            if (nums[m] == target) return m;
            // check if left part sorted
            if (nums[l] <= nums[m]) {
                if (nums[l] <= target && target < nums[m]) r = m-1;
                else l = m+1;
            } else { // right sorted
                if (nums[m] < target && target <= nums[r]) l = m+1;
                else r = m-1;
            }
        }

        return -1;
    }

    // Problem similar to first but array has duplicates
    boolean searchRotatedSortedArray2(int[] nums, int target) {
        // Idea is to divide the search array into two parts based on rotated index
        // and check in which part of array target and mid index lies
        // Accordingly we adjust the left and right indices
        // Edge case is when mid equals left, that means elem can lie in both parts
        // So we need to increment the left index
        // Therefore due to this, we have worst case TC O(N), this happens when all elems are same and target doesn't exist

        int l = 0, r = nums.length-1;
        while (l <= r) {
            int m = l + (r-l)/2;
            
            if (nums[m] ==  target) return true;

            // check if binary search is helpful
            if (nums[l] == nums[m]) {
                l++;
                continue;
            }

            // check if mid and target exist in first part of array
            boolean mIndex = nums[l] <= nums[m];
            boolean tIndex = nums[l] <= target;

            if (mIndex ^ tIndex) { // both mid and target lie in diff parts
                if (mIndex) l = m+1;
                else r = m-1;
            } else {
                if (target > nums[m]) l = m+1;
                else r = m-1;
            }
        }

        return false;
    }

    // array contains unique elems
    int findMinRotatedSortedArray(int[] nums) {
        int l = 0, r = nums.length-1;
        
        // if no rotation
        if (nums[l] < nums[r]) return nums[l];

        while (l < r) {
            int m = l + (r-l)/2;
            if (nums[m] == nums[0]) l++; // increment since we dont know which part min lies
            else if (nums[m] > nums[0]) l = m+1; // min lies on right part
            else r = m; // min lies on left part
        }

        return nums[l];
    }

    // [1,1,2,3,3,4,4,8,8]
    int singleNonDuplicate(int[] nums) {
        // Idea to use position of mid to determine whether to go left or right
        int l = 0, r = nums.length-1;
        while (l < r) {
            int m = l + (r-l)/2;
            if ((m%2 == 0 && nums[m] == nums[m+1]) || (m%2 == 1 && nums[m-1] == nums[m])) l = m+1;
            else r = m;
        }
        return nums[l];
    }

    int[] findPeakGrid(int[][] mat) {
        // Idea is to apply binary search on either row or col
        // So once we get midRow or midCol, we search for maxElem in that midRange
        // Then we check if neighbors are larger then this max elem, if so we towards starr or end range

        int startRow = 0, endRow = mat.length-1;
        while (startRow <= endRow) {
            int midRow = startRow + (endRow-startRow)/2;

            int maxCol = 0;
            for (int col = 0; col < mat[0].length; col++) {
                maxCol = mat[midRow][col] > mat[midRow][maxCol] ? col : maxCol;
            }

            boolean topIsBig = midRow - 1 >= startRow && mat[midRow-1][maxCol] > mat[midRow][maxCol];
            boolean bottomIsBig = midRow + 1 <= endRow && mat[midRow+1][maxCol] > mat[midRow][maxCol];

            if (!topIsBig && !bottomIsBig) return new int[]{midRow, maxCol};
            
            if (topIsBig) endRow = midRow-1;
            else if (bottomIsBig) startRow = midRow+1;
        }

        return null;
    }

    int medianSortedRowMatrix(int[][] mat) { 
        // Idea lies in the fact that all matrix numbers are present in a certain range
        // This range will be our search space for binary search algo
        // When we keep count of how many no's are <= curr no, we observe a monotonically increasing search space
        // To implement algo find how many elems in matrix are <= mid elem
        // Once we find that, in order to get the median, we need to shrink our search space
        // Thus based on where our median can lie, we can move to either or right
        // When low crosses high, that val of low is our median elem

        int m = mat.length, n = mat[0].length;
        int low = 0, high = 2000;

        while (low <= high) {
            int mid = low + (high-low)/2;
            int cnt = 0;

            for (int i = 0; i < m; i++) cnt += elemsLessThanEqualToMid(mat[i], mid);

            if (cnt <= (m*n)/2) low = mid+1;
            else high = mid-1;
        }

        return low;
    }

    int elemsLessThanEqualToMid(int[] arr, int target) {
        int l = 0, r = arr.length-1;
        while (l <= r) {
            int m = l + (r-l)/2;
            if (arr[m] <= target) l = m+1;
            else r = m-1;
        }
        return l;
    }

    long squareRootNumber(long x) {
        // Binary Search can be implemented to any search space which is monotonic in nature
        // So it can be a increasing sequence or decreasing sequence
        // Our search space lies between [1, x], where everytime we find the mid
        // If mid*mid > x, we move towards left else we move towards right

        long low = 1, high = x;
        while (low < high-1) {
            long mid = low + (high-low)/2;
            if (mid*mid >= x) high = mid;
            else low = mid;
        }

        return (low*low <= x && x < high*high) ? low : high;
    }

    int nthRootNumber(int n, int m) {
        // Similar idea as above

        int low = 1, high = m;
        while (low < high - 1) {
            int mid = low + (high-low)/2;
            if (multiplyNTimes(mid, n) < m) low = mid;
            else high = mid;
        }

        return (multiplyNTimes(low, n) <= m && multiplyNTimes(high, n) < m) ? low : high;
    }

    int multiplyNTimes(int num, int n) {
        long res = 1;
        int maxVal = 1_000_000_009;
        
        for (int i = 0; i < n; i++) {
            if (res*num > maxVal) return maxVal;
            res *= num;
        }

        return (int) res;
    }

    int minEatingSpeed(int[] piles, int h) {
        // Idea is to use the fact that value if k(min eating speed) will lie in a certain range
        // Thus we can apply binary search to find this k
        // max value of range will be the max no of bananas in pile
        // Everytime, for each mid, we check if for this value, koko can eat all bananas in h hours
        // Based on this, we move towards left or right

        int low = 1, high = 0;
        for (int pile : piles) high = Math.max(pile, high);

        while (low < high) {
            int mid = low + (high-low)/2;
            if (canEatAll(piles, mid, h)) high = mid;
            else low = mid+1;
        }

        return low;
    }

    boolean canEatAll(int[] piles, int k, int h) {
        int hourCnt = 0;
        for (int pile : piles) {
            hourCnt += pile / k;
            if (pile % k == 0) hourCnt++;
        }

        return hourCnt <= h;
    }

    int minDaysToMakeBouquets(int[] bloomDay, int m, int k) {
        // Some pointers to consider here
        // 1. Flowers picked for each bouquet must be adjacent
        // 2. Min days will lie between [min bloom days for flower, max bloom days for flower]
        // 3. Thus we have a search range and we can apply binary search
        // 4. For each mid value, we need to see if we can create the bouquets in mid days

        int n = bloomDay.length;
        if (k > n || m > n || m*k > n) return -1;

        int low = 1_000_000_009, high = 1;
        for (int day : bloomDay) {
            low = Math.min(day, low);
            high = Math.max(day, high);
        }

        while (low < high) {
            int mid = low + (high-low)/2;
            if (canMakeBouquets(bloomDay, mid, m, k)) high = mid;
            else low = mid+1;
        }

        return low;
    }

    boolean canMakeBouquets(int[] bloomDays, int days, int m, int k) {
        int flowers = 0, bouquetCnt = 0;
        
        for (int i = 0; i < bloomDays.length; i++) {
            if (bloomDays[i] > days) flowers = 0;
            else if (++flowers >= k) {
                bouquetCnt++;
                flowers = 0;
            }
        }

        return bouquetCnt >= m;
    }

    int smallestDivisor(int[] nums, int threshold) {
        int low = 1, high = 0, n = nums.length;
        for (int num : nums) high = Math.max(num, high);

        while (low < high) {
            int mid = low + (high-low)/2;

            int currThresh = 0;
            for (int i = 0; i < n; i++) {
                if (nums[i]%mid == 0) currThresh += nums[i]/mid;
                else currThresh += nums[i]/mid + 1;
            }

            if (currThresh <= threshold) high = mid;
            else low = mid+1;
        }

        return low;
    }

    int shipWithinDays(int[] weights, int days) {
        // Idea is to apply binary search on range of weights

        int low = 0, high = 0;
        for (int weight : weights) {
            low = Math.max(low, weight);
            high += weight;
        }

        while (low < high) {
            int mid = low + (high-low)/2;
            
            int currWeight = 0, currDays = 1;
            for (int weight : weights) {
                if (currWeight + weight > mid) {
                    currDays++;
                    currWeight = 0;
                }
                currWeight += weight;
            }

            if (currDays <= days) high = mid;
            else low = mid+1;

        }

        return low;
    }

    double medianSortedArrays(int[] nums1, int[] nums2) {
        if (nums2.length < nums1.length) return medianSortedArrays(nums2, nums1);
        int n1 = nums1.length, n2 = nums2.length;

        int low = 0, high = n1;
        while (low <= high) {
            int mid1 = low + (high-low)/2;
            int mid2 = (n1+n2+1)/2 - mid1;

            int l1 = (mid1 == 0) ? Integer.MIN_VALUE : nums1[mid1-1];
            int l2 = (mid2 == 0) ? Integer.MIN_VALUE : nums2[mid2-1];
            int r1 = (mid1 == n1) ? Integer.MAX_VALUE : nums1[mid1];
            int r2 = (mid2 == n2) ? Integer.MAX_VALUE : nums2[mid2];

            if (l1 <= r2 && l2 <= r1) {
                if ((n1+n2)%2 == 0) return (Math.max(l1, l2) + Math.min(r1, r2))/2.0;
                else return Math.max(l1, l2);
            } else if (l1 > r2) high = mid1-1;
            else low = mid1+1;
        }

        return 0.0;
    }

    int aggresiveCows(int[] stalls, int n, int k) {
        // Idea is to apply binary search on the range of possible distances
        // Main objective is that min distance between any two cows is the max possible dist
        // Since the stalls positions are kind of like coordinates, we can sort the array to easily place cows
        // Now when checking whether we can place cows using mid as minDist, we can do that by placing first cow always at the start
        // This is kind of like greedy way and then we check if we can place all cows satisfying this dist

        Arrays.sort(stalls);
        
        int res = 0;
        int low = 1, high = stalls[n-1];
        while (low <= high) {
            int mid = low + (high-low)/2;

            int lastPlacedCow = stalls[0], cows = 1;
            for (int i = 1; i < n; i++) {
                if (stalls[i] - lastPlacedCow >= mid) {
                    cows++;
                    lastPlacedCow = stalls[i];
                }
            }

            if (cows >= k) {
                low = mid+1;
                res = mid;
            }
            else high = mid-1;
        }

        return res;
    }

    int findMinimumPages(int[] books, int m) {
        // Objective is to max pages alloted to a student which is min among all other permutations
        if (m > books.length) return -1;

        int low = 1_000_001, high = 0;
        for (int book : books) {
            low = Math.min(book, low);
            high += book;
        }

        int res = 0;
        while (low <= high) {
            int mid = low + (high-low)/2;

            if (isAllocationPossible(books, mid, m)) {
                res = mid;
                high = mid-1;
            } else low = mid+1;
        }

        return res;
    }

    boolean isAllocationPossible(int[] books, int maxPages, int totalStudents) {
        int students = 1, pages = 0;
        
        for (int book : books) {
            if (book > maxPages) return false;
            
            if (pages + book > maxPages) {
                students++;
                pages = 0;
            }
            pages += book;
        }

        return students <= totalStudents;
    }

    int splitArray(int[] nums, int k) {
        // Idea is similar to previous problem
        // Here we will apply binary search on range of possible sum
        
        int low = 0, high = 0;
        for (int num : nums) {
            low = Math.max(low, num);
            high += num;
        }

        int res = 0;
        while (low <= high) {
            int mid = low + (high-low)/2;

            if (isSplitPossible(nums, k, mid)) {
                res = mid;
                high = mid-1;
            } else low = mid+1;
        }

        return res;
    }

    boolean isSplitPossible(int[] nums, int k, int maxSum) {
        int subArrCnt = 1, currSum = 0;
        for (int num : nums) {
            if (num > maxSum) return false;
            if (currSum + num > maxSum) {
                subArrCnt++;
                currSum = 0;
            }
            currSum += num;
        }

        return subArrCnt <= k;
    }

    int findKthPositive(int[] arr, int k) {
        // Idea is to apply binary search on search space of all possible integers
        // Once we find the mid, we check which no in arr is just less than mid
        // And then calculate pos of this mid in actual range

        int low = 1, high = 2000, n = arr.length;
        while (low < high) {
            int mid = low + (high-low)/2;

            int l = 0, r = n-1;
            while (l < r) {
                int m = l + (r-l)/2 + 1;
                if (arr[m] > mid) r = m-1;
                else l = m;
            }
            System.out.println("mid: "+mid+", arr val: "+arr[l]);

            int pos = (arr[l] > mid) ? mid : mid-l-1;
            if (pos < k) low = mid+1;
            else high = mid;

            System.out.println("low: "+low+", high: "+high);
        }

        return low;
    }

    double smallestMaxDistanceGasStation(int[] stations, int k) {
        // Objective is to add k more gas stations so that max dist between gas stations is minimized
        // Idea is to apply binary search on range of possible distances
        // For each mid dist, we need to check how many stations we must add to make dist btwn adjacent stations <= mid val
        // Also we set both high and low values to mid, because if we make low = mid+1, we might skip on ans as diff btwn them is very small

        int n = stations.length;
        double low = 0.0, high = stations[n-1] - stations[0];

        while ((high - low) > 1e-6) {
            double mid = low + (high - low)/2.0;

            int newStations = 0;
            for (int i = 0; i < n-1; i++) {
                newStations += Math.floor((stations[i+1]-stations[i])/mid);
            }

            if (newStations <= k) high = mid;
            else low = mid;
        }
        
        return Math.round(high*100) / 100.0;
    }

    long kthElementSortedArrays(int[] arr1, int[] arr2, int k) {
        // Idea is similar to finding median of two sorted arrays
        // In median case, our k was (n1+n2)/2

        if (arr1.length > arr2.length) return kthElementSortedArrays(arr2, arr1, k);

        int n1 = arr1.length, n2 = arr2.length;
        int low = Math.max(0, k-n2), high = Math.min(n1, k);

        while (low <= high) {
            int mid1 = low + (high-low)/2;
            int mid2 = k - mid1;
            
            int l1 = (mid1 == 0) ? Integer.MIN_VALUE : arr1[mid1-1];
            int l2 = (mid2 == 0) ? Integer.MIN_VALUE : arr2[mid2-1];
            int r1 = (mid1 == n1) ? Integer.MAX_VALUE : arr1[mid1];
            int r2 = (mid2 == n2) ? Integer.MAX_VALUE : arr2[mid2];

            if (l1 <= r2 && l2 <= r1) return Math.max(l1, l2);
            else if (l1 > r2) high = mid1-1;
            else low = mid1+1;
        }

        return 0;
    }

    public static void main(String[] args) {
        BinarySearch bSearch = new BinarySearch();
        int[] stations = new int[]{1,2,3,4,5,6,7,8,9,10};

        System.out.println(bSearch.smallestMaxDistanceGasStation(stations, 9));
    }
}