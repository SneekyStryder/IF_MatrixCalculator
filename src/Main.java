import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Matrix Calculator");
        System.out.println("-----------------");
        System.out.println("Enter the dimensions of the matrices:");

        int rows1 = getMatrixDimension("Matrix 1 - Number of rows: ");
        int cols1 = getMatrixDimension("Matrix 1 - Number of columns: ");

        int rows2 = getMatrixDimension("Matrix 2 - Number of rows: ");
        int cols2 = getMatrixDimension("Matrix 2 - Number of columns: ");

        if (cols1 != rows2) {
            System.out.println("Error: The number of columns in Matrix 1 must be equal to the number of rows in Matrix 2.");
            return;
        }

        int[][] matrix1 = readMatrix("Matrix 1", rows1, cols1);
        int[][] matrix2 = readMatrix("Matrix 2", rows2, cols2);

        System.out.println("\nMatrix 1:");
        printMatrix(matrix1);
        System.out.println("\nMatrix 2:");
        printMatrix(matrix2);

        int[][] product = multiplyMatrices(matrix1, matrix2);
        System.out.println("\nMatrix 1 * Matrix 2:");
        printMatrix(product);

        int[][] sum = addMatrices(matrix1, matrix2);
        System.out.println("\nMatrix 1 + Matrix 2:");
        printMatrix(sum);

        System.out.println("\nRow Echelon Form of Matrix 1:");
        int[][] rowEchelonForm = convertToRowEchelonForm(matrix1);
        printMatrix(rowEchelonForm);
    }

    private static int getMatrixDimension(String prompt) {
        System.out.print(prompt);
        return scanner.nextInt();
    }

    private static int[][] readMatrix(String matrixName, int rows, int cols) {
        System.out.println("\nEnter the elements of " + matrixName + ":");

        int[][] matrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(matrixName + "[" + (i + 1) + "][" + (j + 1) + "]: ");
                matrix[i][j] = scanner.nextInt();
            }
        }
        return matrix;
    }

    private static void printMatrix(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }

    private static int[][] multiplyMatrices(int[][] matrix1, int[][] matrix2) {
        int rows1 = matrix1.length;
        int cols1 = matrix1[0].length;
        int cols2 = matrix2[0].length;

        int[][] product = new int[rows1][cols2];

        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols2; j++) {
                for (int k = 0; k < cols1; k++) {
                    product[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }

        return product;
    }

    private static int[][] addMatrices(int[][] matrix1, int[][] matrix2) {
        int rows1 = matrix1.length;
        int cols1 = matrix1[0].length;

        int[][] sum = new int[rows1][cols1];

        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols1; j++) {
                sum[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }

        return sum;
    }

    private static int[][] convertToRowEchelonForm(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        int[][] rowEchelonForm = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(matrix[i], 0, rowEchelonForm[i], 0, cols);
        }

        for (int i = 0; i < rows; i++) {
            // Find the pivot element
            int pivot = rowEchelonForm[i][i];
            if (pivot == 0) {
                // Swap rows to bring a non-zero pivot
                int j = i + 1;
                while (j < rows && rowEchelonForm[j][i] == 0) {
                    j++;
                }
                if (j == rows) {
                    continue; // No pivot found in this column
                }
                int[] temp = rowEchelonForm[i];
                rowEchelonForm[i] = rowEchelonForm[j];
                rowEchelonForm[j] = temp;
                pivot = rowEchelonForm[i][i];
            }

            // Scale the current row
            for (int j = i; j < cols; j++) {
                rowEchelonForm[i][j] /= pivot;
            }

            // Perform row operations to eliminate elements below the pivot
            for (int j = i + 1; j < rows; j++) {
                int factor = rowEchelonForm[j][i];
                for (int k = i; k < cols; k++) {
                    rowEchelonForm[j][k] -= factor * rowEchelonForm[i][k];
                }
            }
        }

        return rowEchelonForm;
    }
}
