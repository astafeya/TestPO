import example.org.Matrix;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class MatrixTest {
    private static double[][] matrix1;
    private static double[][] matrix2;
    private static double[][] matrix3;
    private static double[][] matrix4;
    private double[][] expected;

    @BeforeAll
    public static void setUp() {
        int size = 5;
        matrix1 = new double[size][size];
        matrix2 = new double[size][size];
        matrix3 = new double[size][size + 3];
        matrix4 = new double[size + 1][size];

        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[0].length; j++) {
                matrix1[i][j] = i * 10 + j;
                matrix2[i][j] = j * 20 + i;
                matrix3[i][j] = i + j + 5;
                matrix4[i][j] = i + j + 5;
            }
            matrix3[i][size] = i + size + 5;
            matrix3[i][size + 1] = i + (size + 1) + 5;
            matrix3[i][size + 2] = i + (size + 2) + 5;
        }
        for (int i = 0; i < matrix4[0].length; i++) {
            matrix4[size][i] = size + i + 5;
        }
    }

    @Test
    public void addProportionalMatrices() {
        expected = new double[matrix1.length][matrix1[0].length];
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[0].length; j++) {
                expected[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
        double[][] actual = Matrix.add(matrix1, matrix2);

        for (int i = 0; i < expected.length; i++) {
            Assert.assertArrayEquals(expected[i], actual[i], 1E-15);
        }
    }

    @Test
    public void addDisproportionalMatrices() throws IllegalArgumentException {
        try {
            Matrix.add(matrix1, matrix3);
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void subtractProportionalMatrices() {
        expected = new double[matrix1.length][matrix1[0].length];
        for (int i = 0; i < expected.length; i++) {
            for (int j = 0; j < expected[0].length; j++) {
                expected[i][j] = matrix1[i][j] - matrix2[i][j];
            }
        }
        double[][] actual = Matrix.subtract(matrix1, matrix2);

        for (int i = 0; i < expected.length; i++) {
            Assert.assertArrayEquals(expected[i], actual[i], 1E-15);
        }
    }

    @Test
    public void subtractDisproportionalMatrices() throws IllegalArgumentException {
        try {
            Matrix.subtract(matrix1, matrix3);
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void multiplyByNumber() {
        expected = new double[matrix1.length][matrix1[0].length];
        double number = 5.6;
        for (int i = 0; i < expected.length; i++) {
            for (int j = 0; j < expected[0].length; j++) {
                expected[i][j] = matrix1[i][j] * number;
            }
        }
        double[][] actual = Matrix.multiplyByNumber(matrix1, number);

        for (int i = 0; i < expected.length; i++) {
            Assert.assertArrayEquals(expected[i], actual[i], 1E-15);
        }
    }

    @Test
    public void multiplyProportionalMatricesSingleSizes() {
        expected = new double[matrix1.length][matrix2[0].length];
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix2[0].length; j++) {
                expected[i][j] = i * 100 + j * 200 + i * j * 1000 + 30;
            }
        }
        double[][] actual = Matrix.multiply(matrix1, matrix2);

        for (int i = 0; i < expected.length; i++) {
            Assert.assertArrayEquals(expected[i], actual[i], 1E-15);
        }
    }

    @Test
    public void multiplyProportionalMatricesDifferentSizes() {
        expected = new double[matrix1.length][matrix3[0].length];
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix3[0].length; j++) {
                expected[i][j] = i * 350 + j * 10 + i * j * 50 + 80;
            }
        }
        double[][] actual = Matrix.multiply(matrix1, matrix3);

        for (int i = 0; i < expected.length; i++) {
            Assert.assertArrayEquals(expected[i], actual[i], 1E-15);
        }
    }

    @Test
    public void multiplyDisproportionalMatrices() throws IllegalArgumentException {
        try {
            Matrix.multiply(matrix1, matrix4);
        } catch (IllegalArgumentException e) {

        }
    }

    @ParameterizedTest
    @CsvSource({
            "1, 5, 1, 5",
            "2, 5, 2, 5"
    })
    public void isSingleSize(int rowsM1, int columnsM1, int rowsM2, int columnsM2) {
        Assert.assertTrue(Matrix.isMatrixHaveSingleSize(rowsM1, columnsM1, rowsM2, columnsM2));
    }

    @ParameterizedTest
    @CsvSource({
            "1, 5, 5, 1",
            "2, 5, 2, 2"
    })
    public void isDifferentSize(int rowsM1, int columnsM1, int rowsM2, int columnsM2) {
        Assert.assertFalse(Matrix.isMatrixHaveSingleSize(rowsM1, columnsM1, rowsM2, columnsM2));
    }

    @ParameterizedTest
    @CsvSource({
            "2",
            "8",
            "15"
    })
    public void multiplyByNumber(double number) {
        expected = new double[matrix1.length][matrix1[0].length];
        for (int i = 0; i < expected.length; i++) {
            for (int j = 0; j < expected[0].length; j++) {
                expected[i][j] = matrix1[i][j] * number;
            }
        }
        double[][] actual = Matrix.multiplyByNumber(matrix1, number);

        for (int i = 0; i < expected.length; i++) {
            Assert.assertArrayEquals(expected[i],
                    actual[i],
                    1E-15);
        }
    }

    @Test
    public void addFromFiles() {
        double[][] m1 = Matrix.getMatrixFromFile("resources/Matrix1.txt", 5, 5);
        double[][] m2 = Matrix.getMatrixFromFile("resources/Matrix2.txt", 5, 5);
        expected = Matrix.getMatrixFromFile("resources/Add.txt", 5, 5);
        double [][] actual = Matrix.add(m1, m2);

        for (int row = 0; row < m1.length; row++) {
            Assert.assertArrayEquals(expected[row],
                    actual[row],
                    1E-15);
        }
    }

    @Test
    public void subFromFiles() {
        double[][] m1 = Matrix.getMatrixFromFile("resources/Matrix1.txt", 5, 5);
        double[][] m2 = Matrix.getMatrixFromFile("resources/Matrix2.txt", 5, 5);
        expected = Matrix.getMatrixFromFile("resources/Sub.txt", 5, 5);
        double [][] actual = Matrix.subtract(m1, m2);

        for (int row = 0; row < m1.length; row++) {
            Assert.assertArrayEquals(expected[row],
                    actual[row],
                    1E-15);
        }
    }

    @Test
    public void multiFromFiles() {
        double[][] m1 = Matrix.getMatrixFromFile("resources/Matrix3.txt", 4, 2);
        double[][] m2 = Matrix.getMatrixFromFile("resources/Matrix4.txt", 2, 4);
        expected = Matrix.getMatrixFromFile("resources/Multi.txt",  2,   2);
        double [][] actual = Matrix.multiply(m1, m2);

        for (int row = 0; row < m1.length; row++) {
            Assert.assertArrayEquals(expected[row],
                    actual[row],
                    1E-15);
        }
    }
}
