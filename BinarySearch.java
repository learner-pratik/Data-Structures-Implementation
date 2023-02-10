public class BinarySearch {

    int binarySearch(int[] arr, int l, int h, int val) {
        if (l > h) return -1;
        int mid = l + (h-l)/2;
        if (arr[mid] == val) return mid;
        if (val < arr[mid]) return binarySearch(arr, l, mid-1, val);
        else return binarySearch(arr, mid+1, h, val);
    }

    public static void main(String[] args) {
        BinarySearch bSearch = new BinarySearch();
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7};
        System.out.println(bSearch.binarySearch(arr, 0, 6, 6));
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
}