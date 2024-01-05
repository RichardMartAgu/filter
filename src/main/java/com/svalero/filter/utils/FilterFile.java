package com.svalero.filter.utils;


import com.svalero.filter.model.FilterItem;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FilterFile {

    private File file;

    public void createNewFile() {
        try {
            file = new File("C:\\Users\\richa\\IdeaProjects\\segundodam\\filter\\src\\main\\resources\\com.svalero.filter\\target\\filterRegister.txt");
            if (file.createNewFile()) {
                ShowAlert.showInformationAlert("INFORMATION", "Archivo Creado", "El archivo ha sido creado con éxito");
            }
        } catch (IOException e) {
            ShowAlert.showErrorAlert("Error", "Fallo al crear", "El archivo no se ha creado");
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    public void writeFile(ObservableList<String> strings) {
        try (FileWriter script = new FileWriter(file, true)) {
            String joinedString = String.join("\t", strings);
            script.write(joinedString + "\n");
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public List<FilterItem> getFilterItemsFromFile(String filePath) {
        List<FilterItem> filterItems = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {

                String[] parts = line.split("\t");
                if (parts.length == 3) {
                    String nameColumn = parts[0].trim();
                    String filters = parts[1].trim();
                    String date = parts[2].trim();
                    filterItems.add(new FilterItem(nameColumn, filters, date));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filterItems;
    }
}