package com.example.project.Model;

import java.util.Objects;

public class Warehouse {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Warehouse warehouse = (Warehouse) o;
        return Objects.equals(id, warehouse.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    private Integer id;

    public Integer getId() {
        return this.id;
    }

    public Warehouse(Integer id) {
        this.id = id;
    }


}
