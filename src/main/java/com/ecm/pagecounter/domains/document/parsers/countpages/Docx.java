package com.ecm.pagecounter.domains.document.parsers.countpages;

import com.ecm.pagecounter.domains.document.parsers.CountPagesParser;
import com.ecm.pagecounter.domains.document.parsers.ParserWorker;
import org.apache.poi.ooxml.POIXMLProperties;
import org.apache.poi.openxml4j.opc.OPCPackage;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Parser for document type .docx
 */
public class Docx implements CountPagesParser {

    public final static Logger log = LoggerFactory.getLogger(Docx.class);

    /**
     * Field 'FILE_EXTENSION' inside parser is uses for set association within file extension and type of parser.
     * @see ParserWorker
     */
    public final String FILE_EXTENSION = "docx";

    @Override
    public Integer parse(String fileNameWithPath) {

        try {
            OPCPackage pkg = OPCPackage.open(new File(fileNameWithPath));
            POIXMLProperties props = new POIXMLProperties(pkg);
            return props.getExtendedProperties().getPages();
        } catch (Exception e) {
            log.error("Error parse file " + fileNameWithPath + " - " + e.getMessage());
        }
        return 0;
    }
}
