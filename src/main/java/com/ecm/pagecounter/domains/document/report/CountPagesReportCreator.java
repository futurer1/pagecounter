package com.ecm.pagecounter.domains.document.report;

import com.ecm.pagecounter.domains.document.parsers.CountPagesParser;
import com.ecm.pagecounter.domains.document.report.resultunit.DocumentProperties;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountPagesReportCreator implements ReportCreator {

    public final static Logger log = LoggerFactory.getLogger(CountPagesReportCreator.class);

    private final Set<String> fileNamesWithPath;
    private final HashMap<String, CountPagesParser> parsers;

    public CountPagesReportCreator(Set<String> fileNamesWithPath, HashMap<String, CountPagesParser> parsers) {
        if (fileNamesWithPath != null) {
            this.fileNamesWithPath = fileNamesWithPath;
        } else {
            this.fileNamesWithPath = new HashSet<>();
        }
        this.parsers = parsers;
    }

    @Override
    public DocumentProperties[] make() {

        var el = fileNamesWithPath.size();
        DocumentProperties[] docsInfo = new DocumentProperties[el];

        int i = 0;
        for (String filename: fileNamesWithPath) {

            int pages = 0;
            String fileType = "";

            CountPagesParser findParser = null;
            for (String fileExt: parsers.keySet()) {
                if (filename.toLowerCase().endsWith(fileExt)) {
                    findParser = parsers.get(fileExt);
                    fileType = fileExt;
                    break;
                }
            }

            if (findParser != null) {
                pages = findParser.parse(filename);
            } else {
                log.warn("Parser not found for file " + filename);
            }
            docsInfo[i] = (new DocumentProperties(filename, fileType)).setCountPages(pages);

            i++;
        }

        return docsInfo;
    }


}
