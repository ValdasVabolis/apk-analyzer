import java.util.HashSet;
import java.util.Set;

public class UnusedPermissionsDetector {
    private final String decodedApkPath;

    public UnusedPermissionsDetector(String decodedApkPath) {
        this.decodedApkPath = decodedApkPath;
    }

    public Set<String> detectUnusedPermissions() {
        Set<String> manifestPermissions = extractManifestPermissions();
        Set<String> usedPermissions = extractUsedPermissions();
        manifestPermissions.removeAll(usedPermissions);
        return manifestPermissions;
    }

    private Set<String> extractManifestPermissions() {
        Set<String> manifestPermissions = new HashSet<>();
        // populate manifestPermissions
        return manifestPermissions;
    }

    private Set<String> extractUsedPermissions() {
        Set<String> usedPermissions = new HashSet<>();
        // populate usedPermissions
        return usedPermissions;

    }
}
