package com.ecm.pagecounter.domains.document.parsers.countpages;


import com.ecm.pagecounter.domains.document.parsers.CountPagesParser;
import com.ecm.pagecounter.domains.document.parsers.ParserWorker;
import com.itextpdf.text.pdf.PdfReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Parser for document type .pdf
 */
public class Pdf implements CountPagesParser {

    public final static Logger log = LoggerFactory.getLogger(Pdf.class);

    /**
     * Field 'FILE_EXTENSION' inside parser is uses for set association within file extension and type of parser.
     * @see ParserWorker
     */
    public final String FILE_EXTENSION = "pdf";

    @Override
    public Integer parse(String fileNameWithPath) {
        try {
            PdfReader reader = new PdfReader(fileNameWithPath);
            int countPages = reader.getNumberOfPages();
            reader.close();
            return countPages;
        } catch (Exception e) {
            log.error("Error parse file " + fileNameWithPath + " - " + e.getMessage());
        }
        return 0;
    }
}
