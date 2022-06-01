package io.ottomoto.webapp.actions;

import io.ottomoto.webapp.utils.PropertiesFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public abstract class Action {
    protected static PropertiesFile properties;
    protected static Path propertiesFolder = Paths.get(System.getProperty("user.dir"), "src/main/resources/properties");


    public static PropertiesFile getPropertiesFiles() {
        return (PropertiesFile) Optional.ofNullable(properties).orElseGet(() -> {
            String environment = (String)Optional.ofNullable(System.getProperty("Environment")).orElse("default");
            return new PropertiesFile(environment, propertiesFolder);
        });
    }
}
