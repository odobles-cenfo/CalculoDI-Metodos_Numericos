import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MetodoDiferenciasFinitas {


    // 1. DEFINICIÓN DE LA FUNCIÓN Y SU DERIVADA EXACTA  
    // ====================================================================

    /**
     * Define la función matemática a derivar. (Ejemplo: f(x) = x^4)
     * @param x El valor de la abscisa.
     * @return El valor de la ordenada f(x).
     */
    public static double funcion(double x) {
        return Math.pow(x, 4); 
    }

    /**
     * Define la derivada analítica (exacta) de la función. (Ejemplo: f'(x) = 4x^3)
     * @param x El punto donde se evalúa la derivada.
     * @return El valor exacto de la derivada.
     */
    public static double derivadaExacta(double x) {
        return 4 * Math.pow(x, 3);
    }

  
    // 2. FUNCIONES PRINCIPALES DEL MÉTODO 
    // ====================================================================

    /**
     * Aproxima la derivada de una función usando la Diferencia Finita hacia Adelante.
     * @param xi El punto actual x_i.
     * @param xi1 El punto siguiente x_{i+1}.
     * @param f_xi El valor de la función en x_i.
     * @param f_xi1 El valor de la función en x_{i+1}.
     * @return La derivada aproximada.
     */
    public static Double aproximarDerivada(Double xi, Double xi1, Double f_xi, Double f_xi1) {
        // Fórmula de la pendiente (f(x_{i+1}) - f(x_i)) / (x_{i+1} - x_i)
        double numerador = f_xi1 - f_xi;
        double denominador = xi1 - xi;
        return numerador / denominador;
    }
    
    
    // 3. FUNCIONES DE ERROR 
    // ====================================================================

    /**
     * Calcula el Error Absoluto.
     * @param valorExacto Valor real de la derivada.
     * @param valorAproximado Valor calculado por el método numérico.
     * @return El Error Absoluto.
     */
    public static double calcularErrorAbsoluto(double valorExacto, double valorAproximado) {
        return Math.abs(valorExacto - valorAproximado);
    }

    /**
     * Calcula el Error Relativo.
     * @param valorExacto Valor real de la derivada.
     * @param valorAproximado Valor calculado por el método numérico.
     * @return El Error Relativo (en porcentaje).
     */
    public static double calcularErrorRelativo(double valorExacto, double valorAproximado) {
        if (valorExacto == 0) return 0.0; // Evita división por cero
        return calcularErrorAbsoluto(valorExacto, valorAproximado) / Math.abs(valorExacto);
    }


    // 4. FUNCIÓN PRINCIPAL MAIN
    // ====================================================================

    public static void main(String[] args) {

        PrintStream out = System.out;
        Scanner in = new Scanner(System.in);

        double inicioRango, finRango;
        int cantidadDeIntervalos;

        try {
            // Modificar límites de derivación
            out.println("--- Ingrese la derivada ---");
            out.print("Ingrese el límite inferior del rango (a): ");
            inicioRango = in.nextDouble();
            out.print("Ingrese el límite superior del rango (b): ");
            finRango = in.nextDouble();
            
            //  Modificar número de intervalos o particiones
            out.print("Ingrese la cantidad de intervalos (N): ");
            cantidadDeIntervalos = in.nextInt();
            
        } catch (InputMismatchException e) {
            out.println("\nError: Asegúrese de ingresar solo números. Programa finalizado.");
            in.close();
            return;
        }

        if (inicioRango >= finRango) {
            out.println("\nError: El límite superior debe ser mayor que el límite inferior. Programa finalizado.");
            in.close();
            return;
        }

        // Cálculo del paso h (h = (b - a) / N)
        double h = (finRango - inicioRango) / cantidadDeIntervalos;
        out.printf("\nEl paso h calculado es: %.6f\n", h);

        // Medición de tiempo (Inicio)
        long tiempoInicio = System.nanoTime();
        int iteraciones = 0;

        out.println("\n========================================================");
        out.println(" RESULTADOS DE LA DERIVACIÓN POR DIFERENCIAS FINITAS (h=" + String.format("%.4f", h) + ")");
        out.println("========================================================");
        out.printf("%-10s %-10s %-15s %-15s %-15s %-15s\n", 
            "Intervalo", "Punto x_i", "Aprox. Numérica", "Valor Exacto", "Error Abs.", "Error Relativo (%)");
        out.println("--------------------------------------------------------------------------------------------------");

        // Bucle principal para el cálculo de diferencias finitas
        for (int i = 0; i < cantidadDeIntervalos; i++) {
            
            // Generación de puntos (Ya no se piden manuales)
            double xi = inicioRango + i * h;
            double xi1 = xi + h; 

            double f_xi = funcion(xi);
            double f_xi1 = funcion(xi1);

            // Muestra el algoritmo paso a paso (Valores usados)
            out.println("\n--- Paso #" + (i + 1) + " (Iteración " + (i + 1) + ") ---");
            out.printf("Punto de inicio (x_i): %.4f | Valor f(x_i): %.4f\n", xi, f_xi);
            out.printf("Punto siguiente (x_{i+1}): %.4f | Valor f(x_{i+1}): %.4f\n", xi1, f_xi1);

            // Cálculo de la derivada aproximada 
            double derivadaAproximada = aproximarDerivada(xi, xi1, f_xi, f_xi1);

            // Cálculo del valor exacto 
            // Nota: La derivada en diferencias finitas hacia adelante se aproxima en el punto x_i
            double valorExacto = derivadaExacta(xi); 
            
            // Cálculo de Errores 
            double errorAbsoluto = calcularErrorAbsoluto(valorExacto, derivadaAproximada);
            double errorRelativo = calcularErrorRelativo(valorExacto, derivadaAproximada) * 100;

            // Salida de datos en formato de tabla
            out.printf("%-10d %-10.4f %-15.6f %-15.6f %-15.6f %-15.4f\n",
                (i + 1), xi, derivadaAproximada, valorExacto, errorAbsoluto, errorRelativo);
            
            iteraciones++;
        }

        //  Medición de tiempo (Fin y Reporte)
        long tiempoFin = System.nanoTime();
        double tiempoTotalMS = (tiempoFin - tiempoInicio) / 1_000_000.0; // En milisegundos

        out.println("\n========================================================");
        out.println("MÉTRICAS DE RENDIMIENTO ");
        out.println("========================================================");
        out.printf("Tiempo de ejecución total: %.4f ms\n", tiempoTotalMS);
        out.println("Número de iteraciones (cálculos de derivada): " + iteraciones);
        out.println("========================================================");

        in.close();
    }
}


