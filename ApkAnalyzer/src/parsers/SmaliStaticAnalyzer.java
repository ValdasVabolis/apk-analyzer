package parsers;

import model.SmaliPermissionUsage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SmaliStaticAnalyzer {
    private final List<SmaliPermissionUsage> results;
    private final Map<String, List<String>> permissionMappings;

    public SmaliStaticAnalyzer(Map<String, List<String>> permissionMappings) {
        this.results = new ArrayList<>();
        this.permissionMappings = permissionMappings;
    }
    public void addEntry(String[] parts, String className, int lineNumber) {
        String methodCall = parts[parts.length - 1].trim();
        if (permissionMappings.containsKey(methodCall)) {
            String permissionType = String.valueOf(permissionMappings.get(methodCall));
            String methodName = extractMethodName(methodCall);
            results.add(new SmaliPermissionUsage(className, methodName, lineNumber, permissionType, methodCall));
        }
    }

    public static String extractClassName(File smaliFile) {
        String filePath = smaliFile.getAbsolutePath();
        return filePath.substring(filePath.lastIndexOf(File.separator) + 1).replace(".smali", "");
    }

    private String extractMethodName(String methodCall) {
        if (methodCall.contains("->")) {
            return methodCall.substring(methodCall.lastIndexOf("->") + 2);
        }
        return methodCall;
    }

    public List<SmaliPermissionUsage> getResults() {
        return results;
    }
}
