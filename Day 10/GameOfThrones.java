public class GameOfThrones {
    public static boolean isPalindrome(String str) {
        int[] count = new int[26];
        for (char c : str.toCharArray()) {
            count[c - 'a']++;
        }
        int oddCount = 0;
        for (int i : count) {
            if (i % 2 != 0) {
                oddCount++;
            }
        }
        return oddCount <= 1;
    }

    public static void main(String[] args) {
        String str = "aabb";
        System.out.println("Is palindrome permutation: " + isPalindrome(str));
    }
}
