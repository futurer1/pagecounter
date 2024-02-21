package com.ecm.pagecounter.domains.filesystem.scanners;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilesScanner implements Scanner {

    private String[] findFileTypes;

    public FilesScanner setFindFileTypes(String[] findFileTypes) {
        this.findFileTypes = findFileTypes;
        return this;
    }

    @Override
    public Set<String> scan(String path) {
        try (Stream<Path> stream = Files.list(Paths.get(path))
        ) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .filter(tmpPath -> {
                        if (findFileTypes != null) {
                            boolean find = false;
                            for (int i = 0; i < findFileTypes.length; i++) {
                                if (tmpPath.getFileName()
                                        .toString().toLowerCase()
                                        .endsWith(findFileTypes[i])
                                ) {
                                    find = true;
                                    break;
                                }
                            }
                            return find;
                        } else {
                            return true;
                        }
                    })
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .map( str -> path + File.separator + str )
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
