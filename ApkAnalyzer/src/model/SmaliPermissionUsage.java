package model;

public class SmaliPermissionUsage {
    private final String className;
    private final String methodName;
    private final int lineNumber;
    private final String permissionType;
    private final String smaliPermission;

    public SmaliPermissionUsage(String className, String methodName, int lineNumber, String permissionType, String smaliPermission) {
        this.className = className;
        this.methodName = methodName;
        this.lineNumber = lineNumber;
        this.permissionType = permissionType;
        this.smaliPermission = smaliPermission;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getPermissionType() {
        return permissionType;
    }

    @Override
    public String toString() {
        return String.format(
            "----------------------------------------%n" +
                    "Class:               %s%n" +
                    "Method:              %s%n" +
                    "Line:                %d%n" +
                    "Permission:          %s%n" +
                    "Permission in Smali: %s%n" +
            "----------------------------------------",
            className,
            methodName,
            lineNumber,
            permissionType,
            smaliPermission
        );
    }
}