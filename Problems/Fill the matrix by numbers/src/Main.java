import java.util.Arrays;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        int[][] rtnMd = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rtnMd[i][j] = Math.abs(i - j);
            }
        }
        System.out.println(Arrays.deepToString(rtnMd).replace("], ", "\n").replaceAll("\\[|,", "").replace("]]", "")+"\n");
    }
}
