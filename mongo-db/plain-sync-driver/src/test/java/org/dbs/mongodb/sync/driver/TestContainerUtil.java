package org.dbs.mongodb.sync.driver;

import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.MountableFile;

import java.io.IOException;

// TODO make it JUnit extension
public class TestContainerUtil {

    public static void importData(String db, String collection, String file, MongoDBContainer container) throws IOException, InterruptedException {
        container.copyFileToContainer(MountableFile.forClasspathResource(file), file);
        container.execInContainer("sh", "-c", importCommand(db, collection, file));
    }

    private static String importCommand(String db, String collection, String file) {
        return String.format(
                "bin/mongoimport --db %s --collection %s --file %s --jsonArray",
                db, collection, file
        );
    }

}
