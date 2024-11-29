package model;

import java.util.HashSet;
import java.util.Set;

public class BasicManifestInfo {

    private Set<String> usedPermissions = new HashSet<>();
    private int targetSdkVersion = 0;
    private int minSdkVersion = 0;
    private int maxSdkVersion = 0;

    public Set<String> getUsedPermissions() {
        return usedPermissions;
    }

    public void setUsedPermissions(Set<String> usedPermissions) {
        this.usedPermissions = usedPermissions;
    }

    public int getTargetSdkVersion() {
        return targetSdkVersion;
    }

    public void setTargetSdkVersion(int targetSdkVersion) {
        this.targetSdkVersion = targetSdkVersion;
    }

    public BasicManifestInfo() {
    }

    public int getMinSdkVersion() {
        return minSdkVersion;
    }

    public void setMinSdkVersion(int minSdkVersion) {
        this.minSdkVersion = minSdkVersion;
    }

    public int getMaxSdkVersion() {
        return maxSdkVersion;
    }

    public void setMaxSdkVersion(int maxSdkVersion) {
        this.maxSdkVersion = maxSdkVersion;
    }

    public BasicManifestInfo(Set<String> usedPermissions, int targetSdkVersion, int minSdkVersion, int maxSdkVersion) {
        this.usedPermissions = usedPermissions;
        this.targetSdkVersion = targetSdkVersion;
        this.minSdkVersion = minSdkVersion;
        this.maxSdkVersion = maxSdkVersion;
    }

}
