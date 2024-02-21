package com.ecm.pagecounter.domains.document.parsers;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * This worker is able to work with different types of parsers.
 * Package 'parsers' now include parsers with type CountPageParsers in folder 'countpages'.
 * All parsers in the target folder are recognizing and applying automatically: just add new file.
 * You can add another types of parsers in new folders 'domains/document/parsers/here'.
 */
public class ParserWorker {

    /**
     * Package name for group of parser type.
     * You can add new group like this:
     * "com.ecm.pagecounter.domains.document.parsers.hereNewTypeParsers"
     */
    private final String PACKAGE_PARSER_COUNT_PAGES = "com.ecm.pagecounter.domains.document.parsers.countpages";

    /**
     * Field 'FILE_EXTENSION' inside parser is uses for set association within file extension and type of parser.
     */
    private final String FILE_EXTENSION_FIELD_NAME = "FILE_EXTENSION";

    private Set<Class<?>> findAllClassesUsingReflectionsLibrary(String packageName) {
        Scanners sc = Scanners.SubTypes.filterResultsBy(s -> true);
        Reflections reflections = new org.reflections.Reflections(packageName, sc);

        return new HashSet<>(reflections.getSubTypesOf(Object.class));
    }

    private <T> T getFieldValueFromClass(Class<?> clazz, String fieldName) {
        try {
            Object clazzObj = clazz.getDeclaredConstructor().newInstance();
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            T value = (T) field.get(clazzObj);
            return value;
        } catch (Exception e) {
            throw new RuntimeException("Error getting field value from class", e);
        }
    }


    public HashMap<String, CountPagesParser> getAllCountPageParsers() {
        HashMap<String, CountPagesParser> filesExtensionsParsers = new HashMap<>();

        Set<Class<?>> findClazz = findAllClassesUsingReflectionsLibrary(PACKAGE_PARSER_COUNT_PAGES);

        for (Class<?> clazz : findClazz) {
            try {

                String value = getFieldValueFromClass(clazz, FILE_EXTENSION_FIELD_NAME);
                if (value != null) {
                    Class<?> c = Class.forName(clazz.getName());
                    Object clazzObj = c.getConstructor().newInstance();
                    filesExtensionsParsers.put(value, (CountPagesParser) clazzObj);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return filesExtensionsParsers;
    }
}
