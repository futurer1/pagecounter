package com.ecm.pagecounter.view;

import com.ecm.pagecounter.domains.document.report.resultunit.DocumentProperties;
import com.ecm.pagecounter.domains.document.report.ReportCreator;

import java.util.Arrays;

public class ReportOutputConsole {
    public static void countFilesAndPagesInDocuments(
            ReportCreator reportCreator
    ) {
        DocumentProperties[] docsInfo = reportCreator.make();

        int totalPages = Arrays.stream(docsInfo)
                .mapToInt(DocumentProperties::getCountPages)
                .sum();

        System.out.println(System.lineSeparator() + "Documents: " + docsInfo.length);
        System.out.println("Pages: " + totalPages);
    }
}
