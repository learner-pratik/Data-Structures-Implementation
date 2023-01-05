public class MathProblems {
    
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
}
