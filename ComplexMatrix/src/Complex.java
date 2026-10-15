public class Complex {
    private double real;
    private double imaginary;

    public Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public double getReal() {
        return real;
    }

    public double getImaginary() {
        return imaginary;
    }

    public Complex add(Complex other) {
        return new Complex(this.real + other.real, this.imaginary + other.imaginary);
    }

    public Complex subtract(Complex other) {
        return new Complex(this.real - other.real, this.imaginary - other.imaginary);
    }

    public Complex multiply(Complex other) {
        double r = this.real * other.real - this.imaginary * other.imaginary;
        double i = this.real * other.imaginary + this.imaginary * other.real;
        return new Complex(r, i);
    }

    public Complex divide(Complex other) {
        double denominator = other.real * other.real + other.imaginary * other.imaginary;
        if (denominator == 0) {
            throw new ArithmeticException("Деление на ноль (нулевое комплексное число).");
        }
        double r = (this.real * other.real + this.imaginary * other.imaginary) / denominator;
        double i = (this.imaginary * other.real - this.real * other.imaginary) / denominator;
        return new Complex(r, i);
    }

    public double module() {
        return Math.sqrt(real * real + imaginary * imaginary);
    }

    @Override
    public String toString() {
        if (imaginary == 0) {
            return String.format("%.2f", real);
        }
        if (real == 0) {
            return String.format("%.2fi", imaginary);
        }
        if (imaginary < 0) {
            return String.format("%.2f - %.2fi", real, -imaginary);
        }
        return String.format("%.2f + %.2fi", real, imaginary);
    }
}