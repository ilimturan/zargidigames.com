package helpers;

/**
 * Created by ilimturan on 07/03/15.
 */
public class FileHelper {

    public static boolean checkfileType(String t) {
        if (t.equalsIgnoreCase("image/jpeg")) {
            return true;
        } else if (t.equalsIgnoreCase("image/png")) {
            return true;
        }
        if (t.equalsIgnoreCase("image/gif")) {
            return true;
        }
        return false;
    }

    public static String clearAndGenerateFileName(String fileName) {

        String newFileName = fileName.toLowerCase().replaceAll("[^a-zA-Z0-9\\._]+", "_");

        Long currentTimeStamp = System.currentTimeMillis() / 1000L;

        return currentTimeStamp + "_" + newFileName;
    }

    public static String clearAndGenerateRemovedFileName(String fileName) {

        return "r_" + fileName;
    }
}
