import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {

        PrintStream out = System.out;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        ArrayList<Double> x = new ArrayList<>();
        ArrayList<Double> f_x = new ArrayList<>();

        int cantidadDePuntos;
        int cantidadDeIntervalos;

        out.println("Ingrese la cantidad de intervalos: ");
        cantidadDeIntervalos = Integer.parseInt(in.readLine());
        cantidadDePuntos = cantidadDeIntervalos + 1;

        for (int i = 0; i < cantidadDePuntos; i++) {

            while (true) {
                try {
                    out.println("Ingrese la abscisa del punto #" + (i + 1) + ": ");
                    Double xi = Double.parseDouble(in.readLine());

                    if (i > 0 && xi <= x.get(i - 1)) {
                        out.println("Error: la abscisa debe ser mayor que la del punto anterior.");
                        continue;
                    }

                    x.add(xi);
                    break;

                } catch (NumberFormatException e) {
                    out.println("El valor ingresado no es un número");
                }
            }

            while (true) {
                try {
                    out.println("Ingrese la ordenada del punto #" + (i + 1) + ": ");
                    Double yi = Double.parseDouble(in.readLine());

                    f_x.add(yi);
                    break;

                } catch (NumberFormatException e) {
                    out.println("El valor ingresado no es un número");
                }
            }
        }

        out.println("RESULTADOS DE LAS DERIVADAS Y ERRORES");

        for (int i = 0; i < cantidadDeIntervalos; i++) {

            double xi = x.get(i);
            double xi1 = x.get(i + 1);
            double yi = f_x.get(i);
            double yi1 = f_x.get(i + 1);

            if (!validarIncremento(xi, xi1)) {
                throw new ArithmeticException("Error: la abscisa de la coordenada #" + (i + 1) + " no puede ser menor a la de la coordenada #" + i);
            }

            double derivada = aproximarDerivada(xi, xi1, yi, yi1);

            out.println("\nIntervalo #" + (i + 1));
            out.println("Derivada aproximada: " + derivada);

            if (i > 0) {

                double h = x.get(i + 1) - x.get(i);
                double error = errorTruncamiento(f_x.get(i - 1), f_x.get(i), f_x.get(i + 1), h);
                out.println("Error de truncamiento estimado: " + error);

            } else {
                out.println("Error de truncamiento: no aplicable (no existe punto x_{i-1}).");
            }
        }

    }

    public static Double aproximarDerivada(Double xi, Double xi1, Double f_xi, Double f_xi1) {
        return (f_xi1 - f_xi) / (xi1 - xi);
    }

    public static boolean validarIncremento(Double xi, Double xiMas1) {
        return xiMas1 > xi;
    }

    public static double errorTruncamiento(Double f_xi_1, Double f_xi, Double f_xi1, Double h) {
        return (f_xi1 - 2 * f_xi + f_xi_1) / (2 * h);
    }
}
