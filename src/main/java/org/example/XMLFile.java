package org.example;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class XMLFile {
    private HashMap<Address, Integer> addressBook = new HashMap<>();

    public XMLFile(){
        this.addressBook = new HashMap<>();

        long startTime = System.currentTimeMillis();
        readXML();
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
        System.out.println("Время обработки CSV-файла: " + (Time1 + Time2 + Time3) + " миллисекунд");
    }

    private void readXML(){
        Path path = Paths.get("src", "address.xml");
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(path.toString());
            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName("item");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String city = element.getAttribute("city");
                    String street = element.getAttribute("street");
                    int houseNumber = Integer.parseInt(element.getAttribute("house"));
                    int floor = Integer.parseInt(element.getAttribute("floor"));

                    Address newAddress = new Address(city, street, houseNumber, floor);
                    addressBook.put(newAddress, addressBook.getOrDefault(newAddress, 0) + 1);

                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        } catch (ParserConfigurationException e) {
            System.err.println("Ошибка конфигурации парсера: " + e.getMessage());
        } catch (SAXException e) {
            System.err.println("Ошибка при парсинге XML: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Ошибка преобразования этажа или дома в число: " + e.getMessage());
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
