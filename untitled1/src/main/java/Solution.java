import java.util.*;

class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> set = new HashSet<>(wordDict);
        int max = 0;
        for(String s1 : wordDict){
            max = Math.max(max, s1.length());
        }
        boolean[] dp = new boolean[s.length()+1];
        dp[0] = true;
        for(int i=1; i<dp.length; i++){
            for(int j=0; j<i; j++){
                if((i-j)<=max && dp[j] && set.contains(s.substring(j,i))){
                    dp[i] = true;
                    break;
                }
            }
        }


        return dp[s.length()];
    }
    public boolean wordBreak1(String s, List<String> wordDict) {
        int max = 0;
        for(String s1 : wordDict){
            max = Math.max(max, s1.length());
        }
        return dfs(s, wordDict, 0, max);
    }

    public boolean dfs(String s, List<String> wordDict, int index, int max){
        if(index == s.length()){
            return true;
        }

        boolean flag = false;
        for(int i=index; i<s.length() && (i-index+1)<=max; i++){
            String str = s.substring(index, i+1);
            if(wordDict.contains(str)){
               flag = flag | dfs(s, wordDict, i+1, max);
            }
        }
        return flag;
    }

    private List<List<Integer>> lists = new ArrayList<>();
    public void ans(int[] num, int m, int index, List<Integer> list, boolean[] b){
        if(index == m) {
            lists.add(new ArrayList<>(list));
            return;
        }

        for(int i=0; i<num.length; i++){
            if(b[i] == false){
                b[i] = true;
                list.add(num[i]);
                ans(num, m, index+1, list, b);
                list.remove(list.size()-1);
                b[i] = false;
            }

        }

    }

    public static char majorityElement(String s) {
        int count = 0;
        char candidate = '\0';
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (count == 0) {
                candidate = c;
            }
            count += (c == candidate) ? 1 : -1;
        }
        return candidate;
    }
    public static boolean equalarr(int[] a1, int[] a2){
        for(int i=0; i<a1.length; i++){
            if(a1[i] != a2[i]){
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int len = in.nextInt();
        String s = in.next();

        char[] arr = new char[len];
        int[] count = new int[2];
        for(int i=0; i<len; i++){
            arr[i] = s.charAt(i);
            count[arr[i]-'0']++;
        }

        int[] ans = new int[2];

        for(int i=0; i<len/2; i++){
            ans[arr[i]-'0']++;
        }
        int t = equalarr(count,ans) ? 1 : 0;
        int start = 1;
        int end = len/2;
        while(start < len){
            int a = start % len;
            int b = end % len;
            ans[arr[a]-'0']--;
            ans[arr[b]-'0']++;
            if(equalarr(count,ans)){
                t++;
            }
            start++;
            end++;
        }
        System.out.print(t);
    }


}