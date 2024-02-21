package com.ecm.pagecounter.domains.filesystem.validators;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class UserInputPathValidator {

    public static boolean isValid(String userInput, boolean systemOut) {
        String err = "";
        Path path = null;
        try {
            path = Paths.get(userInput);
            try (Stream<Path> stream = Files.list(path)) {

            } catch (Exception e) {
                err = e.getMessage();
                path = null;
            }
        } catch (Exception e) {
            err = e.getMessage();
            path = null;
        }
        if (!err.isEmpty() && systemOut) {
            System.out.println("You enter wrong directory path: " + err);
        }
        return path != null;
    }
}
