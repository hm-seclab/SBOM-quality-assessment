package org.mariuxdeangelo.masterthesis.database.modelsView;

import java.util.List;

public class DependencyListViewModel {
    private String name;
    private List<String> generator;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getGenerator() {
        return generator;
    }

    public void setGenerator(List<String> generator) {
        this.generator = generator;
    }
}
