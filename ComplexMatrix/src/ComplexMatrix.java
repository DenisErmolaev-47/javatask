public class ComplexMatrix {
    private Complex[][] data;
    private int rows;
    private int cols;

    public ComplexMatrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new Complex[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.data[i][j] = new Complex(0, 0);
            }
        }
    }

    public ComplexMatrix(Complex[][] data) {
        this.rows = data.length;
        this.cols = data[0].length;
        this.data = new Complex[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.data[i][j] = data[i][j];
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Complex getElement(int i, int j) {
        return data[i][j];
    }

    public void setElement(int i, int j, Complex value) {
        this.data[i][j] = value;
    }

    public ComplexMatrix add(ComplexMatrix other) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException("Сложение невозможно: размерности матриц не совпадают.");
        }
        ComplexMatrix result = new ComplexMatrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.data[i][j] = this.data[i][j].add(other.data[i][j]);
            }
        }
        return result;
    }

    public ComplexMatrix subtract(ComplexMatrix other) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException("Вычитание невозможно: размерности матриц не совпадают.");
        }
        ComplexMatrix result = new ComplexMatrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.data[i][j] = this.data[i][j].subtract(other.data[i][j]);
            }
        }
        return result;
    }

    public ComplexMatrix multiply(ComplexMatrix other) {
        if (this.cols != other.rows) {
            throw new IllegalArgumentException("Умножение невозможно: число столбцов первой матрицы не равно числу строк второй.");
        }
        ComplexMatrix result = new ComplexMatrix(this.rows, other.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                Complex sum = new Complex(0, 0);
                for (int k = 0; k < this.cols; k++) {
                    sum = sum.add(this.data[i][k].multiply(other.data[k][j]));
                }
                result.data[i][j] = sum;
            }
        }
        return result;
    }

    public ComplexMatrix transpose() {
        ComplexMatrix result = new ComplexMatrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.data[j][i] = this.data[i][j];
            }
        }
        return result;
    }

    public Complex determinant() {
        if (rows != cols) {
            throw new IllegalStateException("Определитель можно вычислить только для квадратной матрицы.");
        }
        int n = rows;
        if (n == 1) {
            return data[0][0];
        }
        if (n == 2) {
            Complex ad = data[0][0].multiply(data[1][1]);
            Complex bc = data[0][1].multiply(data[1][0]);
            return ad.subtract(bc);
        }

        Complex det = new Complex(0, 0);
        for (int p = 0; p < n; p++) {
            ComplexMatrix subMatrix = getSubMatrix(0, p);
            Complex cofactor = data[0][p].multiply(subMatrix.determinant());
            if (p % 2 == 1) {
                det = det.subtract(cofactor);
            } else {
                det = det.add(cofactor);
            }
        }
        return det;
    }

    private ComplexMatrix getSubMatrix(int excludeRow, int excludeCol) {
        int n = rows;
        ComplexMatrix sub = new ComplexMatrix(n - 1, n - 1);
        int r = 0;
        for (int i = 0; i < n; i++) {
            if (i == excludeRow) continue;
            int c = 0;
            for (int j = 0; j < n; j++) {
                if (j == excludeCol) continue;
                sub.data[r][c++] = this.data[i][j];
            }
            r++;
        }
        return sub;
    }

    public ComplexMatrix inverse() {
        if (rows != cols) {
            throw new IllegalStateException("Обратную матрицу можно найти только для квадратной матрицы.");
        }
        Complex det = this.determinant();
        if (det.module() < 1e-9) {
            throw new ArithmeticException("Обратная матрица не существует, так как определитель равен нулю.");
        }

        int n = rows;
        if (n == 1) {
            return new ComplexMatrix(new Complex[][]{{ new Complex(1, 0).divide(data[0][0]) }});
        }

        ComplexMatrix cofMatrix = new ComplexMatrix(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ComplexMatrix sub = getSubMatrix(i, j);
                Complex subDet = sub.determinant();
                if ((i + j) % 2 == 1) {
                    subDet = subDet.multiply(new Complex(-1, 0));
                }
                cofMatrix.data[i][j] = subDet;
            }
        }

        ComplexMatrix adj = cofMatrix.transpose();
        ComplexMatrix inv = new ComplexMatrix(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inv.data[i][j] = adj.data[i][j].divide(det);
            }
        }
        return inv;
    }

    public ComplexMatrix divide(ComplexMatrix other) {
        ComplexMatrix invOther = other.inverse();
        return this.multiply(invOther);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            sb.append("[ ");
            for (int j = 0; j < cols; j++) {
                sb.append(data[i][j].toString()).append("\t");
            }
            sb.append("]\n");
        }
        return sb.toString();
    }
}
