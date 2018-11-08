package me.andreaiacono.utils;

import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {

    public static String readTextFile(String filename) throws Exception {
        return new String(Files.readAllBytes(Paths.get(filename)));
    }
}
