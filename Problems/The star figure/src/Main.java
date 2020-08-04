import java.util.Arrays;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        String[][] star = new String[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int mid = n/2;
                if ((i == j) || (i == mid || j == mid) || (j == (n-1)-i)) {
                    star[i][j] = "*";
                } else {
                    star[i][j] = ".";
                }
            }
        }
        System.out.println(Arrays.deepToString(star).replace("], ", "\n").replaceAll("\\[|,", "").replace("]]", "")+"\n");
    }
}
