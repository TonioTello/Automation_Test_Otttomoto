package io.ottomoto.webapp.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public abstract class FileObject {
    protected static final String NO_NAME_FILE = "NoNameFile";
    public static final String FILE_NOT_FOUND_TEMPLATE = "Invalid %s file. The file doesnt exist. Use %s.create(path,fileName) in order to create a new one.";
    static String fileNotFoundMsg = "Invalid object file. The file doesnt exist. Use FileObject.create(path,fileName) in order to create a new one.";
    public static final String INVALID_PATH_MSG = "Not a valid path.";
    File currentFile;
    Path filePath;

    public FileObject() {
    }

    public static String getFileNotFoundMsg() {
        return fileNotFoundMsg;
    }

    public static void setFileNotFoundMsg(String fileNotFoundMsg) {
        FileObject.fileNotFoundMsg = fileNotFoundMsg;
    }

    public void validateFile(File fileDirPath) throws IOException {
        this.currentFile = (File) Optional.ofNullable(fileDirPath).filter(File::exists).orElseThrow(() -> {
            return new FileNotFoundException(fileNotFoundMsg);
        });
        this.filePath = this.currentFile.toPath();
    }

    public void validateStringPath(String fileDirPath) throws IOException {
        String fileDirectory = (String)Optional.ofNullable(fileDirPath).orElse("");
        this.filePath = (Path)Optional.of(Paths.get(fileDirectory)).orElseThrow(() -> {
            return new InvalidPathException(fileDirectory, "Not a valid path.");
        });
        this.currentFile = (File)Optional.of(this.filePath.toFile()).filter(File::exists).orElseThrow(() -> {
            return new FileNotFoundException(fileNotFoundMsg);
        });
    }

    public void validatePath(Path filePath) throws IOException {
        Path fileDirectory = (Path)Optional.ofNullable(filePath).orElse(Paths.get(System.getProperty("user.dir")));
        this.filePath = (Path)Optional.of(fileDirectory).orElseThrow(() -> {
            return new InvalidPathException(fileDirectory.toString(), "Not a valid path.");
        });
        this.currentFile = (File)Optional.of(this.filePath.toFile()).filter(File::exists).orElseThrow(() -> {
            return new FileNotFoundException(fileNotFoundMsg);
        });
    }

    protected static Path getFullFilePath(Path fileDirectory, String fileName) {
        String finalName = (String)Optional.ofNullable(fileName).orElse("NoNameFile");
        return ((Path)Optional.ofNullable(fileDirectory).orElse(Paths.get(System.getProperty("user.dir")))).resolve(finalName);
    }

    public void delete() throws IOException {
        Files.delete(this.currentFile.toPath());
    }
}
