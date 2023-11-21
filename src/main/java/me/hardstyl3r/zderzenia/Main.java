package me.hardstyl3r.zderzenia;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
        System.out.println("Czy wyświetlić obliczenia po kolei? [T/N]");
        boolean displayCalculations = (scanner.next().equalsIgnoreCase("T"));
        if (!displayCalculations) return;

        double denominator = 2 * g * (sin(alpha) + mi_k * cos(alpha));
        double h, s, v = v_0;
        ArrayList<String> listOfS = new ArrayList<>(), heights = new ArrayList<>(), velocities = new ArrayList<>();

        int i = 0;
        if (displayCalculations) System.out.println("i\ts\t\th\t\tv");
        do {
            s = Math.pow(v, 2) / denominator;
            h = s * sin(alpha);
            v = Math.sqrt(2 * g * h);

            listOfS.add(df.format(Math.pow(v, 2) / denominator));
            heights.add(df.format(h));
            velocities.add(df.format(v));

            i++;
            if (displayCalculations)
                System.out.printf("%s.\t%s\t%s\t%s\n", i, df.format(s), df.format(h), df.format(v));
        } while (!df.format(s).equals("0"));
    }
}
