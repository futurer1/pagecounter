package com.ecm.pagecounter.domains.document.report.resultunit;

/**
 * Value object with parsed properties
 * Contains summary information about processed document
 * It`s a base element for make report by important business parameters
 */
public class DocumentProperties {
    private final String filePath;

    private final String fileType;

    private int countPages;

    public DocumentProperties(String filePath, String fileType) {
        this.filePath = filePath;
        this.fileType = fileType;
    }

    public String getFileType() {
        return fileType;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getCountPages() {
        return countPages;
    }

    public DocumentProperties setCountPages(int countPages) {
        this.countPages = countPages;
        return this;
    }
}
