import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import helpers.SdkVersionScanner;
import model.BasicManifestInfo;
import model.SmaliPermissionUsage;
import parsers.ManifestParser;
import parsers.PermissionMappingParser;
import parsers.SmaliStaticAnalyzer;

import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class UnusedPermissionsDetector {
    private final String decodedApkPath;
    private BasicManifestInfo info;
    private final Set<String> usedMethods = new HashSet<>();
    Map<String, List<String>> permissionMappingsResolved = new HashMap<>();

    private SmaliStaticAnalyzer smaliStaticAnalyzer;

    public UnusedPermissionsDetector(String decodedApkPath) {
        this.decodedApkPath = decodedApkPath;
    }

    public Set<String> detectUnusedPermissions() {
        Set<String> manifestPermissions = new HashSet<>();
        try {
            this.info = ManifestParser.parse(decodedApkPath);
            manifestPermissions = info.getUsedPermissions();
            Set<String> usedPermissions = extractUsedPermissions();
            manifestPermissions.removeAll(usedPermissions);

            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return manifestPermissions;

    }

    private Set<String> extractUsedPermissions() {
        Set<String> usedPermissions = new HashSet<>();
        List<Integer> supportedVersions = new SdkVersionScanner()
                .getAllSdkVersions("resources/api_permission_mappings");

        try {
            Map<String, List<String>> permissionMappings = transformJsonKeys(
                    PermissionMappingParser.parse(selectVersion(supportedVersions, info.getMinSdkVersion(),
                            info.getMaxSdkVersion(), info.getTargetSdkVersion())));
            permissionMappingsResolved = permissionMappings;
            smaliStaticAnalyzer = new SmaliStaticAnalyzer(permissionMappingsResolved);
            scanDirectory(new File(decodedApkPath + "/smali"));
            Set<String> commonMethods = new HashSet<>(usedMethods);
            commonMethods.retainAll(permissionMappings.keySet());
            for (String method : commonMethods) {
                usedPermissions.addAll(permissionMappings.get(method));
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return usedPermissions;
    }

    private int selectVersion(List<Integer> availableSdkVersions, int minSdkVersion, int maxSdkVersion,
            int targetSdkVersion) {
        Collections.sort(availableSdkVersions);

        if (targetSdkVersion > 0 && availableSdkVersions.contains(targetSdkVersion)) {
            return targetSdkVersion;
        }

        if (maxSdkVersion > 0) {
            for (int i = availableSdkVersions.size() - 1; i >= 0; i--) {
                int version = availableSdkVersions.get(i);
                if (version <= maxSdkVersion) {
                    return version;
                }
            }
        }

        if (minSdkVersion > 0) {
            for (int i = availableSdkVersions.size() - 1; i >= 0; i--) {
                int version = availableSdkVersions.get(i);
                if (version >= minSdkVersion) {
                    return version;
                }
            }
        }

        return availableSdkVersions.get(availableSdkVersions.size() - 1);
    }

    private Map<String, List<String>> transformJsonKeys(Map<String, List<String>> originalMapping) {
        Map<String, List<String>> transformedMapping = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : originalMapping.entrySet()) {
            String transformedKey = entry.getKey()
                    .replace(";-", ";->")
                    .replace("-(", "(");
            transformedMapping.put(transformedKey, entry.getValue());
        }

        return transformedMapping;
    }

    private void scanDirectory(File directory) throws IOException {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    scanDirectory(file);
                } else if (file.isFile() && file.getName().endsWith(".smali")) {
                    scanSmaliFile(file);
                }
            }
        }
    }

    private void scanSmaliFile(File smaliFile) throws IOException {
        String className = SmaliStaticAnalyzer.extractClassName(smaliFile);
        try (BufferedReader reader = new BufferedReader(new FileReader(smaliFile))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                lineNumber++;
                if (line.startsWith("invoke-")) {
                    String[] parts = line.split(",");
                    if (parts.length > 1) {
                        String method = parts[parts.length - 1].trim();
                        usedMethods.add(method);
                        smaliStaticAnalyzer.addEntry(parts, className, lineNumber);
                    }
                }
            }
        }
    }

    public Set<String> getUsedMethods() {
        return usedMethods;
    }

    public List<SmaliPermissionUsage> getStaticAnalysisResults() {
        return smaliStaticAnalyzer.getResults();
    }
}
