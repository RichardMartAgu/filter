package com.svalero.filter.model;

import javafx.beans.property.SimpleStringProperty;

public class FilterItem {
    private final SimpleStringProperty name;
    private final SimpleStringProperty filters;
    private final SimpleStringProperty date;

    public FilterItem(String nameColumn, String filters, String date) {
        this.name = new SimpleStringProperty(nameColumn);
        this.filters = new SimpleStringProperty(filters);
        this.date = new SimpleStringProperty(date);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getFilters() {
        return filters.get();
    }

    public SimpleStringProperty filtersProperty() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters.set(filters);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }
}

