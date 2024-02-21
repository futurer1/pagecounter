package com.ecm.pagecounter.domains.filesystem.scanners;

import java.util.Set;

public interface Scanner {

    Set<String> scan(String path);
}
