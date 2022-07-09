package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {

    public static String getFileContent(final String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException ioExc) {
            throw new UncheckedIOException(String.format("Couldn't read content from \"%s\" file.", filePath), ioExc);
        }
    }

    public static File createTempFile(final String prefix, final String suffix) {
        final File tempFile;
        try {
            tempFile = File.createTempFile(prefix, suffix);
            tempFile.deleteOnExit();
        } catch (IOException ioExc) {
            throw new UncheckedIOException("Couldn't create new temporary file.", ioExc);
        }
        return tempFile;
    }

    public static void writeArrayAsTable(final File file, final String[][] array, final boolean append) {
        final int[] maxColumnsDataLength = ArrayUtils.getMaxColumnsDataLength(array);
        final int maxTableWidth = ArrayUtils.getElementsSum(maxColumnsDataLength) + maxColumnsDataLength.length * 5 + 1;
        final String horizontalLine = StringUtils.buildString(maxTableWidth, '-');

        try (final FileWriter fileWriter = new FileWriter(file, append)) {
            for (String[] rows : array) {
                fileWriter.write(horizontalLine);
                fileWriter.write('\n');
                for (int columnIndex = 0; columnIndex < rows.length; columnIndex++) {
                    if (rows[columnIndex] == null) {
                        final String emptyPlace = StringUtils.buildString(maxColumnsDataLength[columnIndex] - 4, ' ');
                        fileWriter.write(String.format("|  (NULL)%s", emptyPlace));
                    } else {
                        final String emptyPlace = StringUtils.buildString(maxColumnsDataLength[columnIndex] - rows[columnIndex].length() + 2, ' ');
                        fileWriter.write(String.format("|  %s%s", rows[columnIndex], emptyPlace));
                    }
                }
                fileWriter.write("|\n");
            }
            fileWriter.write(horizontalLine);
            fileWriter.flush();
        } catch (IOException ioExc) {
            throw new UncheckedIOException(String.format("Couldn't write array as table to \"%s\" file.", file.toPath()), ioExc);
        }
    }
}
