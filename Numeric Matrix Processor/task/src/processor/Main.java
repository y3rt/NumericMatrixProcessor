package processor;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int action = setAction();
        if (action != 0) {
            doAction(action);
        }
    }
    private static double[][] enterMatrix() {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter matrix size: ");
        int m1Rows = s.nextInt();
        int m1Col = s.nextInt();
        double[][] m1 = new double[m1Rows][m1Col];

        System.out.println("Enter matrix: ");
        for (int i = 0; i < m1Rows; i++) {
            for (int j = 0; j < m1Col; j++) {
                m1[i][j] = s.nextDouble();
            }
        }
        return m1;
    }
    private static int setAction() {
        Scanner s = new Scanner(System.in);
        System.out.print("1. Add matrices\n" +
                "2. Multiply matrix to a constant\n" +
                "3. Multiply matrices\n" +
                "4. Transpose matrix\n" +
                "5. Calculate a determinant\n" +
                "6. Inverse matrix\n" +
                "0. Exit\n" +
                "Your Choice: ");
        return s.nextInt();
    }
    public static boolean sameSize(double[][] md1, double[][] md2){
        return md1.length == md2.length && md1[0].length == md2[0].length;
    }
    public static double[][] add(double[][] md1, double[][] md2){
        double[][] md3 = new double[md1.length][md1[0].length];

        for (int i = 0; i < md1.length; i++) {
            for (int j = 0; j < md1[0].length; j++) {
                md3[i][j] = md1[i][j] + md2[i][j];
            }
        }

        return md3;
    }
    public static double[][] productMatrix(double[][] md1, int c) {
        double[][] rtnMd = new double[md1.length][md1[0].length];
        for (int i = 0; i < md1.length; i++) {
            for (int j = 0; j < md1[0].length; j++) {
                rtnMd[i][j] = md1[i][j] * c;
            }
        }
        return rtnMd;
    }
    public static double[][] multiplyMatrices(double[][] md1, double[][] md2, int md1r, int md1c, int md2c) {
        double[][] rtnMd = new double[md1r][md2c];
        for(int i = 0; i < md1r; i++) {
            for (int j = 0; j < md2c; j++) {
                for (int k = 0; k < md1c; k++) {
                    rtnMd[i][j] += md1[i][k] * md2[k][j];
                }
            }
        }

        return rtnMd;
    }
    public static void transposeMatrix(int transType) {
        int action;
        double[][] m1t = enterMatrix();
        int w = m1t[0].length;
        int h = m1t.length;
        double[][] rtnMd = new double[h][w];
        switch (transType) {
            case 1: // main diagonal
                for (int i = 0; i < h; i++) {
                    for (int j = 0; j < w; j++) {
                        rtnMd[j][i] = m1t[i][j];
                    }
                }
                break;

            case 2: //side diagonal
                int k = w-1;
                for (int i = 0; i < h; i++) {
                    int l = w-1;
                    for (int j = 0; j < w; j++) {
                        rtnMd[i][j] = m1t[l][k];
                        l--;
                    }
                    k--;
                }
                break;

            case 3: // vertical
                for (int i = 0; i < h; i++) {
                    k = w-1;
                    for (int j = 0; j < w; j++) {
                        rtnMd[i][j] = m1t[i][k];
                        k--;
                    }
                }
                break;

            case 4:  // horizontal
                k = w-1;
                for (int i = 0; i < h; i++) {
                    for (int j = 0; j < w; j++) {
                        rtnMd[i][j] = m1t[k][j];
                    }
                    k--;
                }
                break;
        }
        System.out.println(Arrays.deepToString(rtnMd).replace("], ", "\n").replaceAll("\\[|,", "").replace("]]", "")+"\n");

    }
    static double determinantOfMatrix(double mat[][], int n) {
        double D = 0; // Initialize result

        // Base case : if matrix contains single
        // element
        if (n == 1)
            return mat[0][0];

        // To store cofactors
        double temp[][] = new double[n][n];

        // To store sign multiplier
        int sign = 1;

        // Iterate for each element of first row
        for (int f = 0; f < n; f++)
        {
            // Getting Cofactor of mat[0][f]
            getCofactor(mat, temp, 0, f, n);
            D += sign * mat[0][f]
                    * determinantOfMatrix(temp, n - 1);

            // terms are to be added with
            // alternate sign
            sign = -sign;
        }

        return D;
    }
    static void getCofactor(double mat[][], double temp[][], int p, int q, int n) {
        int i = 0, j = 0;

        // Looping for each element of
        // the matrix
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {

                // Copying into temporary matrix
                // only those element which are
                // not in given row and column
                if (row != p && col != q) {
                    temp[i][j++] = mat[row][col];

                    // Row is filled, so increase
                    // row index and reset col
                    //index
                    if (j == n - 1) {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }
    public static double[][] invert(double a[][]) {
        int n = a.length;
        double x[][] = new double[n][n];
        double b[][] = new double[n][n];
        int index[] = new int[n];
        for (int i=0; i<n; ++i)
            b[i][i] = 1;

        // Transform the matrix into an upper triangle
        gaussian(a, index);

        // Update the matrix b[i][j] with the ratios stored
        for (int i=0; i<n-1; ++i)
            for (int j=i+1; j<n; ++j)
                for (int k=0; k<n; ++k)
                    b[index[j]][k]
                            -= a[index[j]][i]*b[index[i]][k];

        // Perform backward substitutions
        for (int i=0; i<n; ++i)
        {
            x[n-1][i] = b[index[n-1]][i]/a[index[n-1]][n-1];
            for (int j=n-2; j>=0; --j)
            {
                x[j][i] = b[index[j]][i];
                for (int k=j+1; k<n; ++k)
                {
                    x[j][i] -= a[index[j]][k]*x[k][i];
                }
                x[j][i] /= a[index[j]][j];
            }
        }
        return x;
    }
    // Method to carry out the partial-pivoting Gaussian
    // elimination.  Here index[] stores pivoting order.
    public static void gaussian(double a[][], int index[]) {
        int n = index.length;
        double c[] = new double[n];

        // Initialize the index
        for (int i=0; i<n; ++i)
            index[i] = i;

        // Find the rescaling factors, one from each row
        for (int i=0; i<n; ++i)
        {
            double c1 = 0;
            for (int j=0; j<n; ++j)
            {
                double c0 = Math.abs(a[i][j]);
                if (c0 > c1) c1 = c0;
            }
            c[i] = c1;
        }

        // Search the pivoting element from each column
        int k = 0;
        for (int j=0; j<n-1; ++j)
        {
            double pi1 = 0;
            for (int i=j; i<n; ++i)
            {
                double pi0 = Math.abs(a[index[i]][j]);
                pi0 /= c[index[i]];
                if (pi0 > pi1)
                {
                    pi1 = pi0;
                    k = i;
                }
            }

            // Interchange rows according to the pivoting order
            int itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;
            for (int i=j+1; i<n; ++i)
            {
                double pj = a[index[i]][j]/a[index[j]][j];

                // Record pivoting ratios below the diagonal
                a[index[i]][j] = pj;

                // Modify other elements accordingly
                for (int l=j+1; l<n; ++l)
                    a[index[i]][l] -= pj*a[index[j]][l];
            }
        }
    }
    public static void doAction(int action){
        switch (action) {
            case 0:
                System.exit(0);
                break;
            case 1: //addition
                double[][] m1Add = enterMatrix();
                double[][] m2Add = enterMatrix();
                if (sameSize(m1Add,m2Add)) {
                    double[][] m3 = add(m1Add, m2Add);

                    System.out.println("The addition result is: ");
                    for (double[] doubles : m3) {
                        System.out.println(Arrays.toString(doubles).replaceAll("\\[|\\]|,", ""));
                    }
                    System.out.println();
                } else {
                    System.out.println("ERROR\n");
                }

                action = setAction();
                if (action != 0) {
                    doAction(action);
                }
                break;
            case 2: //multiplication of constant
                Scanner s = new Scanner(System.in);
                double[][] m1c = enterMatrix();
                System.out.print("Enter constant: ");
                int c = s.nextInt();
                double[][] m3a = productMatrix(m1c, c);
                System.out.println("The multiplication result is: ");
                System.out.println(Arrays.deepToString(m3a).replace("], ", "\n").replaceAll("\\[|,", "").replace("]]", "")+"\n");

                action = setAction();
                if (action != 0) {
                    doAction(action);
                }
                break;
            case 3: //multiplication of matrices
                double[][] m1 = enterMatrix();
                double[][] m2 = enterMatrix();
                double[][] m3b = multiplyMatrices(m1, m2, m1.length, m1[0].length, m2[0].length);
                System.out.println("The multiplication result is: ");
                System.out.println(Arrays.deepToString(m3b).replace("], ", "\n").replaceAll("\\[|,", "").replace("]]", "")+"\n");

                action = setAction();
                if (action != 0) {
                    doAction(action);
                }
                break;
            case 4: //Transpose Matrix
                System.out.println("1. Main diagonal\n"+
                        "2. Side diagonal\n"+
                        "3. Vertical line\n"+
                        "4. Horizontal line\n"+
                        "Your Choice: ");
                Scanner transS = new Scanner(System.in);
                int transType = transS.nextInt();
                transposeMatrix(transType);

                action = setAction();
                if (action != 0) {
                    doAction(action);
                }
                break;
            case 5:
                double[][] m1dm = enterMatrix();

                double detMat = determinantOfMatrix(m1dm, m1dm.length);
                System.out.println("The result is:\n" + detMat + "\n");
                action = setAction();
                if (action != 0) {
                    doAction(action);
                }
                break;
            case 6:
                double[][] mIa = enterMatrix();
                double[][] mIb = invert(mIa);
                System.out.println(Arrays.deepToString(mIb).replace("], ", "\n").replaceAll("\\[|,", "").replace("]]", "")+"\n");
                action = setAction();
                if (action != 0) {
                    doAction(action);
                }
                break;
        }
    }

//    private static double[][] firstMatrix() {
//        Scanner s = new Scanner(System.in);
//        System.out.print("Enter size of first matrix: ");
//        int m1Rows = s.nextInt();
//        int m1Col = s.nextInt();
//        double[][] m1 = new double[m1Rows][m1Col];
//
//        System.out.println("Enter first matrix: ");
//        for (int i = 0; i < m1Rows; i++) {
//            for (int j = 0; j < m1Col; j++) {
//                m1[i][j] = s.nextDouble();
//            }
//        }
//        return m1;
//    }
//    public static double[][] secondMatrix(){
//        Scanner s = new Scanner(System.in);
//        System.out.print("Enter size of second matrix: ");
//        int m2Rows = s.nextInt();
//        int m2Col = s.nextInt();
//
//        System.out.println("Enter second matrix: ");
//        double[][] mdArr = new double[m2Rows][m2Col];
//        for (int i = 0; i < m2Rows; i++) {
//            for (int j = 0; j < m2Col; j++) {
//                mdArr[i][j] = s.nextDouble();
//            }
//        }
//        return mdArr;
//    }
//    public static double[][] productMatricies(double[][] md1, double[][]md2){
//
//        int rtnMd_x = greaterHeight(md1, md2);
//        int rtnMd_y = greaterWidth(md1, md2);
//
//        double[][] rtnMd = new double[rtnMd_x][rtnMd_y];
//
//        for (int row = 0; row < rtnMd.length; row++) {
//            for (int col = 0; col < rtnMd[row].length; col++) {
//                rtnMd[row][col] = multiplyMatricesCell(md1, md2, row, col);
//            }
//        }
//
//        return rtnMd;
//    }
//    private static int multiplyMatricesCell(double[][] md1, double[][] md2, int row, int col) {
//        int cell = 0;
//        for (int i = 0; i < md2.length; i++) {
//            cell += md1[row][i] * md2[i][col];
//        }
//        return cell;
//    }
//    private static int greaterWidth(double[][] md1, double[][] md2) {
//        if ( md1[0].length > md2[0].length) {
//            return md1[0].length;
//        } else {
//            return md2[0].length;
//        }
//    }
//    private static int greaterHeight(double[][] md1, double[][] md2) {
//        if ( md1.length > md2.length) {
//            return md1.length;
//        } else {
//            return md2.length;
//        }
//    }
}
