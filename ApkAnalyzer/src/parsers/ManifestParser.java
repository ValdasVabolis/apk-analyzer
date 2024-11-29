package parsers;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.util.Set;
import model.BasicManifestInfo;

public class ManifestParser {
    public static BasicManifestInfo parse(String directory) throws Exception {
        BasicManifestInfo info = new BasicManifestInfo();
        File manifestFile = new File(directory, "AndroidManifest.xml");
        if (!manifestFile.exists()) {
            System.out.println("AndroidManifest.xml not found at: " + directory);
            return info;
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new FileInputStream(manifestFile));

        document.getDocumentElement().normalize();
        info.setUsedPermissions(getPermissions(document));
        info.setTargetSdkVersion(getSdk(document, "android:targetSdkVersion"));
        info.setMinSdkVersion(getSdk(document, "android:minSdkVersion"));
        info.setMaxSdkVersion(getSdk(document, "android:maxSdkVersion"));

        return info;

    }

    private static Set<String> getPermissions(Document xml) {
        NodeList permissionNodes = xml.getElementsByTagName("uses-permission");
        Set<String> permissions = new HashSet<>();

        for (int i = 0; i < permissionNodes.getLength(); i++) {
            Element element = (Element) permissionNodes.item(i);
            String permissionName = element.getAttribute("android:name");
            if (permissionName != null && !permissionName.isEmpty()) {
                permissions.add(permissionName);
            }
        }

        return permissions;
    }

    private static int getSdk(Document xml, String attributeName) {
        int version = 0;
        NodeList sdkNodes = xml.getElementsByTagName("uses-sdk");
        if (sdkNodes.getLength() > 0) {
            Element sdkElement = (Element) sdkNodes.item(0);
            String value = sdkElement.getAttribute(attributeName);
            if (!value.isEmpty()) {
                version = Integer.parseInt(value);
            }
        }
        return version;
    }
}