import model.SmaliPermissionUsage;

import java.util.Set;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java Main <path_to_decoded_apk>");
            return;
        }
        String decodedApkPath = args[0];
        try {
            UnusedPermissionsDetector detector = new UnusedPermissionsDetector(decodedApkPath);
            Set<String> unusedPermissions = detector.detectUnusedPermissions();

            System.out.println("Unused Permissions: ");
            unusedPermissions.forEach(System.out::println);
            System.out.println("Static analysis: ");
            detector.getStaticAnalysisResults().forEach(r -> System.out.println(r.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
