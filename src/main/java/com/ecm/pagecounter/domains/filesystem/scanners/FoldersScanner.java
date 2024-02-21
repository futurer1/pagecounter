package com.ecm.pagecounter.domains.filesystem.scanners;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FoldersScanner implements Scanner {
    @Override
    public Set<String> scan(String path) {
        try (Stream<Path> stream = Files.list(Paths.get(path))
        ) {
            return stream
                    .filter(Files::isDirectory)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .map( str -> path + File.separator + str )
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
