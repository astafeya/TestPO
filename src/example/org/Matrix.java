package example.org;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Matrix {

    public static double[][] multiply(double[][] firstMatrix, double[][] secondMatrix) {
        if (firstMatrix[0].length != secondMatrix.length) {
            throw new IllegalArgumentException();
        }
        double[][] result = new double[firstMatrix.length][secondMatrix[0].length];

        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                result[row][col] = multiplyMatricesCell(firstMatrix, secondMatrix, row, col);
            }
        }
        return result;
    }
    private static double multiplyMatricesCell(double[][] firstMatrix, double[][] secondMatrix, int row, int col) {
        double cell = 0;
        for (int i = 0; i < secondMatrix.length; i++) {
            cell += firstMatrix[row][i] * secondMatrix[i][col];
        }
        return cell;
    }

    public static void print(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++)
                System.out.print(matrix[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }

    public static boolean isMatrixHaveSingleSize(int rowsM1, int columnsM1, int rowsM2, int columnsM2) {
        if (rowsM1 == rowsM2 && columnsM1 == columnsM2) {
            return true;
        }
        else {
            return false;
        }
    }

    public static double[][] add(double[][] firstMatrix, double[][] secondMatrix) {
        if (!isMatrixHaveSingleSize(firstMatrix.length, firstMatrix[0].length, secondMatrix.length,
                secondMatrix[0].length)) {
            throw new IllegalArgumentException();
        }

        double[][] result = new double[firstMatrix.length][firstMatrix[0].length];
        for (int i = 0; i < firstMatrix.length; i++) {
            for (int j = 0; j < firstMatrix[0].length; j++) {
                result[i][j] = firstMatrix[i][j] + secondMatrix[i][j];
            }
        }
        return result;
    }

    public static double[][] subtract(double[][] firstMatrix, double[][] secondMatrix) {
        if (!isMatrixHaveSingleSize(firstMatrix.length, firstMatrix[0].length, secondMatrix.length,
                secondMatrix[0].length)) {
            throw new IllegalArgumentException();
        }

        double[][] result = new double[firstMatrix.length][firstMatrix[0].length];
        for (int i = 0; i < firstMatrix.length; i++) {
            for (int j = 0; j < firstMatrix[0].length; j++) {
                result[i][j] = firstMatrix[i][j] - secondMatrix[i][j];
            }
        }
        return result;
    }

    public static double[][] multiplyByNumber(double[][] matrix, double number) {
        double[][] result = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result[i][j] = matrix[i][j] * number;
            }
        }
        return result;
    }

    public static double[][] getMatrixFromFile(String file, int column, int rows) {

        try (InputStream inputStream = new FileInputStream(file)) {

            BufferedInputStream bufIn = new BufferedInputStream(inputStream, 100);
            int i;
            String matrix = "";
            while ((i = bufIn.read()) != -1) {
                matrix = matrix + ((char) i);
            }
            matrix += " ";

            return getMatrix(matrix, column, rows);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static double [][] getMatrix(String matrix, int column, int rows) {

        int n = 0, row = 0, col = 0;
        double [][] matrix1 = new double[rows][column];
        for (int j = 0; j < matrix.length(); j++) {
            if (matrix.charAt(j) == ' ' || matrix.charAt(j) == '\n') {
                char[] dst = new char[j-n];
                matrix.getChars(n, j, dst, 0);
                n = j + 1;
                matrix1[row][col] = Double.parseDouble(new String(dst));
                if ( matrix.charAt(j) == '\n') {
                    row++;
                    col = 0;
                }
                if ( matrix.charAt(j) != '\n') {
                    col++;
                }
            }
        }
        return matrix1;
    }
}
