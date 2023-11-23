package me.hardstyl3r.zderzenia;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Main {
    static float g = 9.81f;

    public static void main(String[] args) {
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.FLOOR);

        System.out.println("Analiza zderzeń idealnie sprężystych. Podaj kolejno:");
        double v_0, mi_k, alpha;
        Scanner scanner = new Scanner(System.in);
        System.out.print("v_0 [m/s] - prędkość początkowa: ");
        v_0 = scanner.nextDouble();
        System.out.print("μ_k - współczynnik tarcia kinetycznego: ");
        mi_k = scanner.nextDouble();
        System.out.print("α [°] - kąt nachylenia równi pochyłej: ");
        alpha = Math.toRadians(scanner.nextInt());
        System.out.print("Czy wyświetlić obliczenia po kolei? [T/N] ");
        boolean displayCalculations = (scanner.next().equalsIgnoreCase("T"));
        System.out.print("Czy wyświetlić wykresy? [T/N] ");
        boolean displayCharts = (scanner.next().equalsIgnoreCase("T"));
        if (!displayCharts && !displayCalculations) return;

        double denominator = 2 * g * (sin(alpha) + mi_k * cos(alpha));
        double h, s, v = v_0;
        List<Double> listOfS = new ArrayList<>(), heights = new ArrayList<>(), velocities = new ArrayList<>();
        List<Integer> iterations = new ArrayList<>();

        int i = 0;
        if (displayCalculations) System.out.println("i\t\ts\t\t\th\t\t\tv");
        do {
            i++;
            s = Math.pow(v, 2) / denominator;
            h = s * sin(alpha);
            v = Math.sqrt(2 * g * h);

            listOfS.add(s);
            heights.add(h);
            velocities.add(v);
            iterations.add(i);
            if (displayCalculations)
                System.out.printf("%s.\t\t%s\t\t%s\t\t%s\n", i, df.format(s), df.format(h), df.format(v));
        } while (!df.format(s).equals("0"));

        if (!displayCharts) return;
        XYChart sOdn = QuickChart.getChart("Wykres drogi od iteracji", "n - iteracje", "s - droga", "s", iterations, listOfS);
        XYChart hOdn = QuickChart.getChart("Wykres wysokości od iteracji", "n - iteracje", "h - wysokość", "h", iterations, heights);
        XYChart vOdn = QuickChart.getChart("Wykres prędkości od iteracji", "n - iteracje", "v - prędkość", "v", iterations, velocities);

        new SwingWrapper(sOdn).setTitle(sOdn.getTitle()).displayChart();
        new SwingWrapper(hOdn).setTitle(hOdn.getTitle()).displayChart();
        new SwingWrapper(vOdn).setTitle(vOdn.getTitle()).displayChart();
    }
}
