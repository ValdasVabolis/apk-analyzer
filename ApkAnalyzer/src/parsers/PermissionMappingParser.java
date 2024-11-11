package parsers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;

public class PermissionMappingParser {

    public static Map<String, List<String>> parse(int version) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, List<String>> permissionMapping = null;

        InputStream inputStream = PermissionMappingParser.class.getClassLoader()
                .getResourceAsStream("resources/api_permission_mappings/permissions_" + version + ".json");
        if (inputStream != null) {
            permissionMapping = objectMapper.readValue(inputStream, new TypeReference<Map<String, List<String>>>() {
            });
        }

        return permissionMapping;
    }

}
