package io.ottomoto.webapp.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.PropertiesConfigurationLayout;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.convert.ListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class PropertiesFile extends FileObject {
    protected static final String DEFAULT_FILE_NAME = "configuration";
    protected static final String DEFAULT_PROJECT_FOLDER = "src/main/resources/properties";
    protected static final String PROPERTIES_EXTENSION = ".properties";
    protected static final String DEFAULT_ENVIRONMENT = "default";
    protected static Charset encoding;
    protected Path defaultDirectory = Paths.get(System.getProperty("user.dir")).resolve("src/main/resources/properties");
    protected String valueSeparator = ",";

    public PropertiesFile(String propsName, Path propertiesFolder) {
        this.filePath = Paths.get(System.getProperty("user.dir")).resolve("src/main/resources/properties").resolve("configuration".concat(".properties"));
        this.currentFile = this.filePath.toFile();
        this.setPropertiesFile(propsName, propertiesFolder);

        try {
            FileReader fileReader = new FileReader(this.currentFile);
            Throwable var4 = null;

            try {
                encoding = Charset.forName(fileReader.getEncoding());
            } catch (Throwable var14) {
                var4 = var14;
                throw var14;
            } finally {
                if (fileReader != null) {
                    if (var4 != null) {
                        try {
                            fileReader.close();
                        } catch (Throwable var13) {
                            var4.addSuppressed(var13);
                        }
                    } else {
                        fileReader.close();
                    }
                }

            }
        } catch (IOException var16) {
            //Todo Log.LOGGER.info("Invalid Charset. Setting up the default charset: UTF-8");
            encoding = StandardCharsets.UTF_8;
        }

    }

    public PropertiesFile(File propsFile) throws IOException {
        this.filePath = Paths.get(System.getProperty("user.dir")).resolve("src/main/resources/properties").resolve("configuration".concat(".properties"));
        this.currentFile = this.filePath.toFile();
        this.validateFile(propsFile);
        FileReader fileReader = new FileReader(this.currentFile);
        Throwable var3 = null;

        try {
            encoding = Charset.forName(fileReader.getEncoding());
        } catch (Throwable var12) {
            var3 = var12;
            throw var12;
        } finally {
            if (fileReader != null) {
                if (var3 != null) {
                    try {
                        fileReader.close();
                    } catch (Throwable var11) {
                        var3.addSuppressed(var11);
                    }
                } else {
                    fileReader.close();
                }
            }

        }

    }

    public PropertiesFile(Path propsFilePath) throws IOException {
        this.filePath = Paths.get(System.getProperty("user.dir")).resolve("src/main/resources/properties").resolve("configuration".concat(".properties"));
        this.currentFile = this.filePath.toFile();
        this.validatePath(propsFilePath);
        FileReader fileReader = new FileReader(this.currentFile);
        Throwable var3 = null;

        try {
            encoding = Charset.forName(fileReader.getEncoding());
        } catch (Throwable var12) {
            var3 = var12;
            throw var12;
        } finally {
            if (fileReader != null) {
                if (var3 != null) {
                    try {
                        fileReader.close();
                    } catch (Throwable var11) {
                        var3.addSuppressed(var11);
                    }
                } else {
                    fileReader.close();
                }
            }

        }

    }

    private PropertiesFile(File propsFile, Charset encoding) throws IOException {
        this.filePath = Paths.get(System.getProperty("user.dir")).resolve("src/main/resources/properties").resolve("configuration".concat(".properties"));
        this.currentFile = this.filePath.toFile();
        this.validateFile(propsFile);
        PropertiesFile.encoding = encoding;
    }

    private PropertiesFile() {
        this.filePath = Paths.get(System.getProperty("user.dir")).resolve("src/main/resources/properties").resolve("configuration".concat(".properties"));
        this.currentFile = this.filePath.toFile();
    }

    public Charset getEncoding() {
        return encoding;
    }

    public void setEncoding(Charset encoding) {
        System.setProperty("file.encoding", encoding.name());
        PropertiesFile.encoding = encoding;
    }

    public String getValueSeparator() {
        return this.valueSeparator;
    }

    public void setValueSeparator(String valueSeparator) {
        this.valueSeparator = valueSeparator;
    }

    public Path getDefaultDirectory() {
        return this.defaultDirectory;
    }

    public void setDefaultDirectory(Path defaultDirectory) {
        this.defaultDirectory = defaultDirectory;
    }

    public void setPropertiesFile(String fileName) {
        String validfileName = (String)Optional.ofNullable(fileName).filter(StringUtils::isNotBlank).orElse("configuration");
        this.filePath = this.defaultDirectory.resolve(validfileName);
        this.currentFile = this.filePath.toFile();
    }

    private void setPropertiesFile(String propertiesName, Path propertiesFolder) {
        Optional.ofNullable(propertiesFolder).filter((path) -> {
            return StringUtils.isNotBlank(path.toString());
        }).ifPresent((path) -> {
            String validFileName = (String)Optional.ofNullable(propertiesName).filter((fileName) -> {
                return !fileName.equals("default") && StringUtils.isNotBlank(fileName);
            }).orElse("configuration");
            this.filePath = path.resolve(validFileName.concat(".properties"));
            this.currentFile = this.filePath.toFile();
        });
    }

    public static PropertiesFile create(Path fileDirectory, String fileName, Charset encoding) throws IOException {
        Path filePath = getFullFilePath(fileDirectory, fileName);
        return new PropertiesFile(Files.createFile(filePath).toFile(), encoding);
    }

    public static PropertiesFile forceCreate(Path fileDirectory, String fileName, Charset encoding) throws IOException {
        Path filePath = getFullFilePath(fileDirectory, fileName);
        Files.delete(filePath);
        return new PropertiesFile(Files.createFile(filePath).toFile(), encoding);
    }

    public static PropertiesFile create(Path fileDirectory, String fileName) throws IOException {
        return create(fileDirectory, fileName, encoding);
    }

    public static PropertiesFile forceCreate(Path fileDirectory, String fileName) throws IOException {
        return create(fileDirectory, fileName, encoding);
    }

    private PropertiesConfiguration initProperties() {
        PropertiesConfiguration configuration = new PropertiesConfiguration();
        ListDelimiterHandler delimiter = new DefaultListDelimiterHandler(this.valueSeparator.charAt(0));
        configuration.setListDelimiterHandler(delimiter);
        return configuration;
    }

    public String getFieldValue(String key) {
        return this.getInputConfiguration().getString(key);
    }

    public String getFieldValue(String key, String defaultValue) {
        return this.getInputConfiguration().getString(key, defaultValue);
    }

    private PropertiesConfiguration getInputConfiguration() {
        PropertiesConfiguration configuration = this.initProperties();
        PropertiesConfigurationLayout layout = new PropertiesConfigurationLayout();

        try {
            FileInputStream fileInputStream = new FileInputStream(this.currentFile);
            Throwable var4 = null;

            try {
                Reader fileReader = new InputStreamReader(fileInputStream, encoding);
                layout.load(configuration, fileReader);
            } catch (Throwable var14) {
                var4 = var14;
                throw var14;
            } finally {
                if (fileInputStream != null) {
                    if (var4 != null) {
                        try {
                            fileInputStream.close();
                        } catch (Throwable var13) {
                            var4.addSuppressed(var13);
                        }
                    } else {
                        fileInputStream.close();
                    }
                }

            }
        } catch (IOException | ConfigurationException var16) {
            //Todo Log.LOGGER.error("An error ocurred while reading the properties file. Error: \n".concat(var16.getMessage()));
        }

        return configuration;
    }

    public String[] getFieldValuesAsArray(String key) {
        return this.getInputConfiguration().getStringArray(key);
    }

    public List<String> getFieldValuesAsList(String key) {
        return this.getInputConfiguration().getList(String.class, key);
    }

    public String getRandomFieldValue(String key) {
        List<String> properties = this.getInputConfiguration().getList(String.class, key);
        int index = (new Random()).nextInt(properties.size());
        return (String)properties.get(index);
    }

    public void updateFieldValue(String key, String newValue) {
        try {
            PropertiesConfiguration configuration = this.initProperties();
            PropertiesConfigurationLayout layout = this.getOutputConfiguration(configuration);
            configuration.setProperty(key, newValue);
            FileWriter fileWriter = new FileWriter(this.currentFile, false);
            layout.save(configuration, fileWriter);
        } catch (IOException | ConfigurationException var6) {
            //Todo Log.LOGGER.error("An error ocurred while writting the properties file. Error: \n".concat(var6.getMessage()));
        }

    }

    public boolean isFieldPresent(String key) {
        return StringUtils.isNotBlank(this.getInputConfiguration().getString(key));
    }

    public void addField(String newKey, String newValue) {
        try {
            PropertiesConfiguration configuration = this.initProperties();
            PropertiesConfigurationLayout layout = this.getOutputConfiguration(configuration);
            configuration.addProperty(newKey, newValue);
            FileWriter fileWriter = new FileWriter(this.currentFile, false);
            layout.save(configuration, fileWriter);
        } catch (IOException | ConfigurationException var6) {
            //Todo Log.LOGGER.error("An error ocurred while writting the properties file. Error: \n".concat(var6.getMessage()));
        }

    }

    public void addFieldValues(String key, String... newValues) {
        try {
            String[] actualValues = this.getFieldValuesAsArray(key);
            PropertiesConfiguration configuration = this.initProperties();
            PropertiesConfigurationLayout layout = this.getOutputConfiguration(configuration);
            configuration.setProperty(key, ArrayUtils.addAll(actualValues, newValues));
            FileWriter fileWriter = new FileWriter(this.currentFile, false);
            layout.save(configuration, fileWriter);
        } catch (IOException | ConfigurationException var7) {
            //Todo Log.LOGGER.error("An error ocurred while writting the properties file. Error: \n".concat(var7.getMessage()));
        }

    }

    private PropertiesConfigurationLayout getOutputConfiguration(PropertiesConfiguration configuration) throws IOException, ConfigurationException {
        PropertiesConfigurationLayout layout = new PropertiesConfigurationLayout();
        FileReader fileReader = new FileReader(this.currentFile);
        Throwable var4 = null;

        try {
            layout.load(configuration, fileReader);
        } catch (Throwable var13) {
            var4 = var13;
            throw var13;
        } finally {
            if (fileReader != null) {
                if (var4 != null) {
                    try {
                        fileReader.close();
                    } catch (Throwable var12) {
                        var4.addSuppressed(var12);
                    }
                } else {
                    fileReader.close();
                }
            }

        }

        return layout;
    }
}
