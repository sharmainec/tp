package lingogo.commons.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import lingogo.commons.exceptions.CsvColumnHeaderException;
import lingogo.commons.exceptions.CsvNumColumnsException;

/**
 * Writes and reads files
 */
public class FileUtil {

    private static final String CHARSET = "UTF-8";
    private static final String UTF8_BOM = "\uFEFF"; // the UTF-8 byte order mark (BOM)

    public static boolean isFileExists(Path file) {
        return Files.exists(file) && Files.isRegularFile(file);
    }

    /**
     * Returns true if {@code path} can be converted into a {@code Path} via {@link Paths#get(String)},
     * otherwise returns false.
     * @param path A string representing the file path. Cannot be null.
     */
    public static boolean isValidPath(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException ipe) {
            return false;
        }
        return true;
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories.
     * @throws IOException if the file or directory cannot be created.
     */
    public static void createIfMissing(Path file) throws IOException {
        if (!isFileExists(file)) {
            createFile(file);
        }
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories.
     */
    public static void createFile(Path file) throws IOException {
        if (Files.exists(file)) {
            return;
        }

        createParentDirsOfFile(file);

        Files.createFile(file);
    }

    /**
     * Creates parent directories of file if it has a parent directory
     */
    public static void createParentDirsOfFile(Path file) throws IOException {
        Path parentDir = file.getParent();

        if (parentDir != null) {
            Files.createDirectories(parentDir);
        }
    }

    /**
     * Assumes file exists
     */
    public static String readFromFile(Path file) throws IOException {
        return new String(Files.readAllBytes(file), CHARSET);
    }

    /**
     * Writes given string to a file.
     * Will create the file if it does not exist yet.
     */
    public static void writeToFile(Path file, String content) throws IOException {
        Files.write(file, content.getBytes(CHARSET));
    }

    /**
     * Returns true if {@code fileName} is a valid CSV fileName,
     * otherwise returns false.
     * @param fileName A string representing the file name. Cannot be null.
     */
    public static boolean isValidCsvFileName(String fileName) {
        Pattern specialChars = Pattern.compile("[\"$^&+:=\\\\?@#|/'<>%!*{}`]");
        Pattern reservedNames = Pattern.compile("CON|PRN|AUX|NUL|COM[0-9]|LPT[0-9]");
        return fileName.endsWith(".csv")
                && !fileName.replace(".csv", "").replace(".", "").isBlank()
                && !specialChars.matcher(fileName).find()
                && !reservedNames.matcher(fileName.replace(".csv", "")).matches()
                && fileName.length() < 32;
    }

    /**
     * Returns a List of String[] representing the CSV file content at the specified
     * filepath. Each element of the List contains a row of data in the CSV file.
     *
     * @param filepath A string representing the file path. Cannot be null.
     * @param expectedHeaders The expected headers in the CSV file.
     * @throws IOException If an error occurs during the read.
     * @throws CsvValidationException When the CSV file is not valid.
     * @throws CsvColumnHeaderException When the headers of the CSV file do not match expectedHeaders.
     * @throws CsvNumColumnsException When the number of columns in the CSV file does not match the
     * length of expectedHeaders.
     */
    public static List<String[]> importCsvFileContent(String filepath, String[] expectedHeaders)
            throws IOException, CsvValidationException, CsvColumnHeaderException, CsvNumColumnsException {
        // Try-with-resources statement ensures the CSVReader is closed after the execution of the try block
        try (CSVReader reader = new CSVReaderBuilder(
            new InputStreamReader(new FileInputStream(filepath), StandardCharsets.UTF_8)).build()) {

            String[] headers = reader.readNext();

            // Handle empty CSV file
            if (headers == null) {
                throw new CsvColumnHeaderException();
            }

            // Handle UTF-8 byte order mark
            if (headers.length > 0 && headers[0].startsWith(UTF8_BOM)) {
                headers[0] = headers[0].substring(1);
            }

            // Validate headers
            if (!Arrays.toString(headers).equals(Arrays.toString(expectedHeaders))) {
                throw new CsvColumnHeaderException();
            }

            ArrayList<String[]> output = new ArrayList<>();
            String[] line;
            int expectedNumberOfColumns = expectedHeaders.length;

            while ((line = reader.readNext()) != null) {
                if (line.length != expectedNumberOfColumns || Arrays.asList(line).stream().anyMatch(e -> e.isBlank())) {
                    throw new CsvNumColumnsException();
                }
                output.add(line);
            }

            return output;
        }
    }

    /**
     * Exports the given headers and content into a CSV file at the specified filepath.
     *
     * @param filepath The filepath where the CSV file will be created at.
     * @param headers The headers to be written into the CSV file.
     * @param content The content to be written into the CSV file. Each element of the List contains a row of data.
     * @throws IOException When an IO error occurs when writing to the specified file.
     */
    public static void exportToCsvFile(String filepath, String[] headers, List<String[]> content)
            throws IOException {
        try (FileOutputStream w = new FileOutputStream(filepath);
             CSVWriter writer = new CSVWriter(new OutputStreamWriter(w, StandardCharsets.UTF_8))) {

            // Add the byte order mark (BOM) for UTF-8 encoding. This is necessary for certain software
            // (e.g. Excel) to read the CSV file correctly
            w.write(0xef);
            w.write(0xbb);
            w.write(0xbf);

            writer.writeNext(headers);
            writer.writeAll(content);
        }
    }

}
