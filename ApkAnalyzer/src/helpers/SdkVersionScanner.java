package helpers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SdkVersionScanner {
    public List<Integer> getAllSdkVersions(String resourcePath) {
        List<Integer> sdkVersions = new ArrayList<>();
        try {
            File resourceDirectory = new File(getClass().getClassLoader().getResource(resourcePath).toURI());

            Pattern pattern = Pattern.compile("permissions_(\\d+)\\.json");
            File[] files = resourceDirectory.listFiles();
            if (files != null) {
                for (File file : files) {
                    Matcher matcher = pattern.matcher(file.getName());
                    if (matcher.matches()) {
                        String versionString = matcher.group(1);
                        int versionNumber = Integer.parseInt(versionString);
                        sdkVersions.add(versionNumber);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sdkVersions;
    }
}