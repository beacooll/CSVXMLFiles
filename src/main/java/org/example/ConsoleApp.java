package org.example;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleApp {
    public static void main(String[] args) {
        String path;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        while (true){
            System.out.println("Введите путь к файлу или \"end.\" для прекращения работы программы");
            try {
                String input = in.readLine();
                if (input.equals("end.")) {
                    break;
                } else if (input.endsWith(".xml")) {
                    path = input;
                    XMLFile xmlFile = new XMLFile(path);
                } else if (input.endsWith(".csv")) {
                    path = input;
                    CSVFile csvFile = new CSVFile(path);
                } else {
                    System.out.println("Путь к файлу указан некорректно");
                }
            } catch (IOException e) {
                System.err.println("Ошибка при чтении: " + e.getMessage());
            } catch (ParserConfigurationException | NumberFormatException e){
                System.err.println("Введенный файл неоректен");
            } catch (SAXException e) {
                System.err.println("Выбранный файл не найден");
            }
        }

    }
}