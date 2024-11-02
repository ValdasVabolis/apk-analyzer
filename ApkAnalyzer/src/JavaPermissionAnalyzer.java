import model.Permission;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JavaPermissionAnalyzer {
    public static List<Permission> analyzePermissions(String javaCode, List<String> permissions) {
        List<Permission> result = new ArrayList<>();
        String[] lines = javaCode.split("\\r?\\n");

        String className = "";
        String currentMethod = "";
        boolean classFound = false;

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();

            if (line.startsWith("public class") || line.startsWith("class ")) {
                className = line.split("\\s+")[2];
                classFound = true;
            }

            if (line.matches(".*\\s+void\\s+.*\\(.*\\).*\\{")) {
                currentMethod = line.split("\\s+")[1];
            }

            for (String permission : permissions) {
                if (line.contains(permission) && classFound) {
                    result.add(new Permission(permission, className, currentMethod, i + 1));
                }
            }
        }
        return result;
    }

    public static List<Permission> analyzePermissionsFromFile(String filePath, List<String> permissions) throws IOException {
        StringBuilder javaCode = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                javaCode.append(line).append("\n");
            }
        }
        return analyzePermissions(javaCode.toString(), permissions);
    }
}
