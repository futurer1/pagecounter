
## Java Console Application for counting pages in files documents

Project structure:
- domains/document/parsers/countpages
Parsers for .pdf and .docx file types. If you need to add new countpage parser, 
you can add one file in this directory. All parsers in the target folder are recognizing 
and applying automatically: just add new file.

- java/com/ecm/pagecounter/domains/document/parsers/ParserWorker.java
This worker is able to work with different types of parsers.
Package 'parsers' now include parsers with type CountPageParsers in folder 'countpages'.
You can add another types of parsers in new folders 'domains/document/parsers/here'.

- domains/document/report
This folder contains CountPagesReportCreator for making reports with parameters: quantity 
of documents and quantity pages in this documents. You cat use ReportCreator interface for
adding new types of report creators.

- domains/document/report/resultunit/DocumentProperties.java
It`s raw middleware object for accumulate results of parsers work.
If you want to parse addition parameters of documents, you must add them to this structure unit.

- domains/filesystem/scanners
Scanners for work with file system. Scans all internal files and folders.

- domains/filesystem/validators/UserInputPathValidator.java
Validator for user data. It check correctness of path to goal folder in file system.

- view/ReportOutputConsole.java
Now there are one type of output for report - to user console.
You can add another output views, e.t. for printer, network or other.


Build with gradle wrapper:
```commandline
./gradlew clean build
```

Run application:
```commandline
java -jar ecmPageCounter-0.1.jar
```

Example console output:
```commandline
Enter root directory path.
../Documents

Documents: 127
Pages: 3238
```