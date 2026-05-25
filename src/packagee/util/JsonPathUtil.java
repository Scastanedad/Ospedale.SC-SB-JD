package packagee.util;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class JsonPathUtil {

    private JsonPathUtil() {}

    public static Path resolve(String relativePath) {
        Path cwdPath = Paths.get(relativePath);
        Path parentDir = cwdPath.getParent();
        if (parentDir != null && Files.isDirectory(parentDir.toAbsolutePath())) {
            return cwdPath.toAbsolutePath();
        }

        try {
            URI codeUri = JsonPathUtil.class
                    .getProtectionDomain().getCodeSource().getLocation().toURI();
            Path codePath = Paths.get(codeUri);
            Path baseDir = Files.isDirectory(codePath) ? codePath : codePath.getParent();
            return baseDir.resolve(relativePath).toAbsolutePath();
        } catch (Exception e) {
            return Paths.get(relativePath).toAbsolutePath();
        }
    }
}
