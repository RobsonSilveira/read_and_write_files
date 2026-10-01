package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import model.entities.Product;

public class Program {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.print("\nInforme o caminho do arquivo: ");
        String filePath = sc.nextLine();

        List<Product> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line = br.readLine();

            while (line != null) {

                String[] data = line.split(",");

                String name = data[0].trim();
                double price = Double.parseDouble(data[1].trim());
                int quantity = Integer.parseInt(data[2].trim());

                list.add(new Product(name, price, quantity));

                line = br.readLine();
            }

            if (!list.isEmpty()) {

                File sourceFile = new File(filePath);
                String sourceFolder = sourceFile.getParent();

                File outFolder = new File(sourceFolder, "out");
                outFolder.mkdirs();

                File summaryFile = new File(outFolder, "summary.csv");

                try (BufferedWriter bw = new BufferedWriter(new FileWriter(summaryFile))) {

                    for (Product p : list) {
                        bw.write(p.getName() + "," + p.totalPrice());
                        bw.newLine();
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        sc.close();
    }
}