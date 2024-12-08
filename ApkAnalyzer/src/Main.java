import model.SmaliPermissionUsage;

import java.util.Set;

public class Main {
    public static void main(String[] args) {

        ApkDecompiler decompiler = new ApkDecompiler();

        /*if (args.length < 1) {
            System.out.println("Usage: java Main <path_to_decoded_apk>");
            return;
        }*/
        String decodedApkPath = decompiler.Decompile();
        UnusedPermissionsDetector detector = new UnusedPermissionsDetector(decodedApkPath);
        Set<String> unusedPermissions = detector.detectUnusedPermissions();

        System.out.println("Unused Permissions: ");
        unusedPermissions.forEach(System.out::println);
        System.out.println("Static analysis: ");
        detector.getStaticAnalysisResults().forEach(System.out::println);
    }
}
