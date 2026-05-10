package com.bsn.api.adapters.output.storage;

import com.bsn.api.core.port.output.ImageStoragePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FileSystemStorageAdapter implements ImageStoragePort {

    private static final Logger log = LoggerFactory.getLogger(FileSystemStorageAdapter.class);


    @Override
    public byte[] resolveFromUrl(String imageUrl) {
        if (imageUrl == null) {
            return null;
        }

        try {
            Path path = new File(imageUrl).toPath();
            return Files.readAllBytes(path);
        } catch(Exception e) {
            log.error("An error while reading the image has occurred", e);
            return null;
        }
    }

}
