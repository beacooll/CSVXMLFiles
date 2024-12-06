package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class CSVFile {
    private HashMap<Address, Integer> addressBook = new HashMap<>();

    public CSVFile(String path) throws IOException, NumberFormatException{
        this.addressBook = new HashMap<>();

        makeCSV(path);
    }

    private void makeCSV(String path){
        try {
            long startTime = System.currentTimeMillis();
            readCSV(path);
            long Time1 = System.currentTimeMillis() - startTime;

            startTime = System.currentTimeMillis();
            displayDuplicateAddresses();
            long Time2 = System.currentTimeMillis() - startTime;

            startTime = System.currentTimeMillis();
            displayFloorsByCity();
            long Time3 = System.currentTimeMillis() - startTime;

            System.out.println();
            System.out.println("Время чтения CSV-файла: " + (Time1) + " миллисекунд");

            System.out.println();
            System.out.println("Время поиска дубликатов CSV-файла: " + (Time2) + " миллисекунд");

            System.out.println();
            System.out.println("Время обработки CSV-файла: " + (Time1 + Time2 + Time3) + " миллисекунд\n");
        }catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
        catch (NumberFormatException e) {
            System.err.println("Ошибка преобразования этажа или дома в число: " + e.getMessage());
        }
    }

    private void readCSV(String path)  throws IOException, NumberFormatException{
        BufferedReader csv = new BufferedReader(new FileReader(path));
        String address;
        csv.readLine();
        while ((address = csv.readLine()) != null) {
            String[] arr = address.split(";");
            Address newAddress = new Address(arr[0].trim(), arr[1].trim(), Integer.parseInt(arr[2].trim()), Integer.parseInt(arr[3].trim()));
            addressBook.put(newAddress, addressBook.getOrDefault(newAddress, 0) + 1);
        }
    }
    public void displayDuplicateAddresses() {
        HashMap<Address, Integer> duplicates = new HashMap<>();
        for (Map.Entry<Address, Integer> entry : addressBook.entrySet()) {
            if (entry.getValue() > 1) {
                duplicates.put(entry.getKey(), entry.getValue());
            }
        }
        if (duplicates.isEmpty()) {
            System.out.println("Дублирующихся записей не найдено.");
            System.out.println();
        } else {
            System.out.println("Дублирующиеся записи:");
            for (Map.Entry<Address, Integer> entry : duplicates.entrySet()) {
                System.out.println(entry.getKey() + " - " + entry.getValue() + " раз(а)");
            }
            System.out.println();
        }
    }

    public void displayFloorsByCity() {
        HashMap<String, int[]> cityFloors = new HashMap<>();

        for (Map.Entry<Address, Integer> entry : addressBook.entrySet()) {
            Address address = entry.getKey();
            int floors = address.getFloor();
            String city = address.getCity();

            if (!cityFloors.containsKey(city)) {
                cityFloors.put(city, new int[6]);
            }
            if (floors >= 1 && floors <= 5) {
                cityFloors.get(city)[floors]++;
            }
        }

        System.out.println("Количество этажей в каждом городе:\n");
        for (Map.Entry<String, int[]> entry : cityFloors.entrySet()) {
            String city = entry.getKey();
            int[] floorsCount = entry.getValue();
            System.out.print(city + ": \n");
            for (int i = 1; i <= 5; i++) {
                System.out.print(i + " этажных: " + floorsCount[i] + ", \n");
            }
            System.out.println();
        }
    }
}

