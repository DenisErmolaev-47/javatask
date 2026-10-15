import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Калькулятор матриц комплексных чисел ===");

        ComplexMatrix matrixA = null;

        while (true) {
            System.out.println("\n-----------------------------------------");
            if (matrixA != null) {
                System.out.println("Текущая матрица (A):");
                System.out.println(matrixA);
            } else {
                System.out.println("Матрица A не задана.");
            }

            System.out.println("Меню:");
            System.out.println("1. Задать/изменить матрицу A");
            if (matrixA != null) {
                System.out.println("2. Выполнить операцию с матрицей A и новой матрицей B");
                System.out.println("3. Вычислить определитель матрицы A");
                System.out.println("4. Транспонировать матрицу A");
            }
            System.out.println("0. Выход");
            System.out.print("Выберите пункт меню: ");

            int choice = scanner.nextInt();
            if (choice == 0) {
                System.out.println("Выход из программы.");
                break;
            } else if (choice == 1) {
                System.out.println("\n--- Ввод матрицы A ---");
                matrixA = inputMatrix(scanner);
                System.out.println("Матрица A успешно сохранена!");
            } else if (matrixA != null && (choice == 2 || choice == 3 || choice == 4)) {
                try {
                    if (choice == 2) {
                        System.out.println("\n--- Ввод второй матрицы (B) ---");
                        ComplexMatrix matrixB = inputMatrix(scanner);
                        System.out.println("Матрица B:");
                        System.out.println(matrixB);

                        System.out.println("Выберите операцию между A и B:");
                        System.out.println("1. Сложение (A + B)");
                        System.out.println("2. Вычитание (A - B)");
                        System.out.println("3. Умножение (A * B)");
                        System.out.println("4. Деление (A / B)");
                        System.out.print("Ваш выбор: ");

                        int op = scanner.nextInt();
                        switch (op) {
                            case 1:
                                matrixA = matrixA.add(matrixB);
                                System.out.println("Результат (A + B) сохранен как новая матрица A:");
                                break;
                            case 2:
                                matrixA = matrixA.subtract(matrixB);
                                System.out.println("Результат (A - B) сохранен как новая матрица A:");
                                break;
                            case 3:
                                matrixA = matrixA.multiply(matrixB);
                                System.out.println("Результат (A * B) сохранен как новая матрица A:");
                                break;
                            case 4:
                                matrixA = matrixA.divide(matrixB);
                                System.out.println("Результат (A / B) сохранен как новая матрица A:");
                                break;
                            default:
                                System.out.println("Неверная операция.");
                                continue;
                        }
                        System.out.println(matrixA);

                    } else if (choice == 3) {
                        Complex det = matrixA.determinant();
                        System.out.println("Определитель матрицы A: " + det);

                    } else if (choice == 4) {
                        matrixA = matrixA.transpose();
                        System.out.println("Результат транспонирования сохранен как новая матрица A:");
                        System.out.println(matrixA);
                    }
                } catch (Exception e) {
                    System.out.println("Ошибка выполнения: " + e.getMessage());
                }
            } else {
                System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
        scanner.close();
    }

    private static ComplexMatrix inputMatrix(Scanner scanner) {
        System.out.print("Введите количество строк: ");
        int rows = scanner.nextInt();
        System.out.print("Введите количество столбцов: ");
        int cols = scanner.nextInt();

        ComplexMatrix matrix = new ComplexMatrix(rows, cols);
        System.out.println("Вводите элементы построчно (сначала действительную, затем мнимую часть через пробел):");

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print("Элемент [" + i + "][" + j + "]: ");
                double real = scanner.nextDouble();
                double imag = scanner.nextDouble();
                matrix.setElement(i, j, new Complex(real, imag));
            }
        }
        return matrix;
    }
}