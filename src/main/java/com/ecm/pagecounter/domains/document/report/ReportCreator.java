package com.ecm.pagecounter.domains.document.report;

import com.ecm.pagecounter.domains.document.report.resultunit.DocumentProperties;

public interface ReportCreator {

    DocumentProperties[] make();
}
