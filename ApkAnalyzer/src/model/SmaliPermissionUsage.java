package model;

public class SmaliPermissionUsage {
    private final String className;
    private final String methodName;
    private final int lineNumber;
    private final String permissionType;

    public SmaliPermissionUsage(String className, String methodName, int lineNumber, String permissionType) {
        this.className = className;
        this.methodName = methodName;
        this.lineNumber = lineNumber;
        this.permissionType = permissionType;
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
        return "Class: " + className + ", Method: " + methodName + ", Line: " + lineNumber + ", Permission: " + permissionType;
    }
}