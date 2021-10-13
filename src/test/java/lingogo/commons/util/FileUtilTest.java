package lingogo.commons.util;

import static lingogo.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class FileUtilTest {

    @Test
    public void isValidPath() {
        // valid path
        assertTrue(FileUtil.isValidPath("valid/file/path"));

        // invalid path
        assertFalse(FileUtil.isValidPath("a\0"));

        // null path -> throws NullPointerException
        assertThrows(NullPointerException.class, () -> FileUtil.isValidPath(null));

        // invalid CSV file name
        assertFalse(FileUtil.isValidCSVFileName("abc"));
        assertFalse(FileUtil.isValidCSVFileName("a/v/d/.csv"));
        assertFalse(FileUtil.isValidCSVFileName("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz.csv"));
        assertFalse(FileUtil.isValidCSVFileName("file name.csv"));
    }

}
