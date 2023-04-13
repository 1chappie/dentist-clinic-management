package validators;

import java.io.File;

public class PathValidator {

    private static boolean checkExtension(String path, String extension) {
        return path.endsWith(extension);
    }

    private static boolean checkExistsLocal(String path) {
        File file = new File(path);
        return file.isFile();
    }

    public static boolean checkBIN(String... path) {
        for (String p : path)
            if (!checkExtension(p, ".bin") || !checkExistsLocal(p))
                return false;
        return true;
    }

    public static boolean checkCSV(String... path) {
        for (String p : path)
            if (!checkExtension(p, ".csv") || !checkExistsLocal(p))
                return false;
        return true;
    }

    public static boolean checkXML(String... path) {
        for (String p : path)
            if (!checkExtension(p, ".xml") || !checkExistsLocal(p))
                return false;
        return true;
    }

    public static boolean checkJSON(String... path) {
        for (String p : path)
            if (!checkExtension(p, ".json") || !checkExistsLocal(p))
                return false;
        return true;
    }

    public static boolean checkURL(String... path) {
        for (String p : path)
            if (!checkExtension(p, ".db") || !checkExistsLocal(p))
                return false;
        return true;
    }
}
