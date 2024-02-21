package com.ecm.pagecounter.domains.filesystem.scanners;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Scans the entire tree of subfolders for findFileTypes or all file types.
 */
public class TreePathsScanner implements Scanner {

    private String[] findFileTypes;

    public final static Logger log = LoggerFactory.getLogger(TreePathsScanner.class);

    @Override
    public Set<String> scan(String path) {

        Set<String> folders = null;
        try {
            folders = getTreeFolders(path);
            return getFilesFromFolders(folders);
        } catch (Exception e) {
            log.error("Error during scan folder " + path + ": " + e.getMessage());
        }

        return folders;
    }

    private Set<String> getFilesFromFolders(Set<String> folders) {
        FilesScanner fs = new FilesScanner().setFindFileTypes(findFileTypes);
        Set<String> findFiles = null;

        Set<String> warnings = new HashSet<>();

        for (String folder: folders) {
            try {
                Set<String> tmpFiles = fs.scan(folder);
                if (!tmpFiles.isEmpty()) {
                    if (findFiles != null) {
                        findFiles.addAll(tmpFiles);
                    } else {
                        findFiles = tmpFiles;
                    }
                }
            } catch (Exception e) {
                warnings.add(e.getMessage());
            }
        }

        if (!warnings.isEmpty()) {
            log.warn("There some problems during scan files");
            log.warn(warnings.toString());
        }

        return findFiles;
    }

    private static Set<String> getTreeFolders(String path) {
        FoldersScanner foldersScanner = new FoldersScanner();

        Set<String> folders = foldersScanner.scan(path);

        Stack<String> stackFolders = new Stack<>();
        for (String folder: folders) {
            stackFolders.push(folder);
        }

        while (!stackFolders.empty()) {

            String curFolder = stackFolders.pop();

            Set<String> tmpFolders = foldersScanner.scan(curFolder);
            for (String tmpFolder: tmpFolders) {
                stackFolders.push(tmpFolder);

                folders.add(tmpFolder);
            }
        }
        folders.add(path);
        return folders;
    }

    public TreePathsScanner setFindFileTypes(String[] findFileTypes) {
        this.findFileTypes = findFileTypes;
        return this;
    }
}
