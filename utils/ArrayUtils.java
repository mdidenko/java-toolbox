package utils;

import java.io.File;
import java.util.Arrays;

public class ArrayUtils {

    public static boolean isMatrix(final String[][] array) {
        final int firstColumnLength = array[0].length;
        for (int rowIndex = 1; rowIndex < array.length; rowIndex++) {
            if (array[rowIndex].length != firstColumnLength) {
                return false;
            }
        }
        return true;
    }

    public static int[] getMaxColumnsDataLength(final String[][] matrix) {
        if (!isMatrix(matrix)) {
            throw new UnsupportedOperationException("Method to get max columns length can use only for matrix.");
        }
        final int[] maxColumnsLength = new int[matrix[0].length];
        Arrays.fill(maxColumnsLength, 0);
        for (String[] rows : matrix) {
            for (int columnIndex = 0; columnIndex < rows.length; columnIndex++) {
                final int currentLengthForColumn;
                if (rows[columnIndex] == null) {
                    currentLengthForColumn = 4;
                } else {
                    currentLengthForColumn = rows[columnIndex].length();
                }
                if (currentLengthForColumn > maxColumnsLength[columnIndex]) {
                    maxColumnsLength[columnIndex] = currentLengthForColumn;
                }
            }
        }
        return maxColumnsLength;
    }

    public static int getElementsSum(final int[] array) {
        int sum = 0;
        for (int element : array) {
            sum = sum + element;
        }
        return sum;
    }

    public static String convertToTableString(final String[][] array) {
        final File tmpFile = FileUtils.createTempFile("tmpConvertToTableString", ".txt");
        FileUtils.writeArrayAsTable(tmpFile, array, false);
        return FileUtils.getFileContent(tmpFile.getPath());
    }
}
