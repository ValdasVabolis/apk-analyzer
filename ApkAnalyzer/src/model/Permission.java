package model;

public class Permission {
    private final String permission;
    private final String className;
    private final String methodName;
    private final int lineNumber;

    public Permission(String permission, String className, String methodName, int lineNumber) {
        this.permission = permission;
        this.className = className;
        this.methodName = methodName;
        this.lineNumber = lineNumber;
    }

    @Override
    public String toString() {
        return "PermissionUsage{" +
                "permission='" + permission + '\'' +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", lineNumber=" + lineNumber +
                '}';
    }
}