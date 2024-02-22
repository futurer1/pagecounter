package com.ecm.pagecounter;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import com.ecm.pagecounter.domains.filesystem.validators.UserInputPathValidator;
import com.ecm.pagecounter.domains.document.parsers.ParserWorker;
import com.ecm.pagecounter.domains.document.report.CountPagesReportCreator;
import com.ecm.pagecounter.domains.filesystem.scanners.TreePathsScanner;
import com.ecm.pagecounter.view.ReportOutputConsole;

public class Main {
    public static void main(String[] args) {

        System.out.println("Enter root directory path.");
        
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();

        if (!UserInputPathValidator.isValid(userInput, true)) {
            return;
        }

        Path path = Paths.get(userInput);
        String input = path.toString();


        // Getting supported file types and parsers for them,
        // based on content inside parsers library
        var allParsers = new ParserWorker().getAllCountPageParsers();

        // Scanning all files by input parameter extensions
        // array in directory tree include inner folders
        var treePathsScanner = new TreePathsScanner().setFindFileTypes(
                allParsers.keySet().toArray(String[]::new)
            );
        var findedFiles = treePathsScanner.scan(input);

        // Make report and echo to console
        ReportOutputConsole.countFilesAndPagesInDocuments(
                new CountPagesReportCreator(findedFiles, allParsers)
        );
    }
}
