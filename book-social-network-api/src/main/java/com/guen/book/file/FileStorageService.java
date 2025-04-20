package com.guen.book.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileStorageService
{
    @Value("${application.file.upload.photos-output-path}")
    private String fileUploadPath;

    public String saveFile(
            @NonNull MultipartFile sourceFile,
            @NonNull Integer userId)
    {
        final String fileUploadSubPath = "users" + File.separator + userId;
        return uploadFile(sourceFile, fileUploadSubPath);
    }

    private String uploadFile(
            @NonNull MultipartFile sourceFile,
            @NonNull String fileUploadSubPath)
    {
        final String finalUploadPath = fileUploadPath + File.separator + fileUploadSubPath;

        File targetFolder = new File(finalUploadPath);
        if (!targetFolder.exists())
        {
            boolean folderCreated = targetFolder.mkdirs();
            if (!folderCreated)
            {
                log.warn("Erreur lors de la création du dossier cible");
                return null;
            }
        }

        final String fileExtension = getFileExtension(sourceFile.getOriginalFilename());

        // Ex : ./upload/users/1/1256689689145.jpg  (/ est le séparateur, 1 est le userID et 1256689689145 le System.currentTimeMillis())
        String targetFilePath = finalUploadPath + File.separator + System.currentTimeMillis() +  "." + fileExtension;

        Path targetPath = Paths.get(targetFilePath);
        try
        {
            Files.write(targetPath, sourceFile.getBytes());
            log.info("Fichier enregistré dans " + targetFilePath);
            return targetFilePath;
        } catch (IOException e) {
            log.error("Erreur lors de l'enregistrement du fichier", e);
        }

        return null;
    }

    private String getFileExtension(String filename)
    {
        if (filename == null || filename.isEmpty())
        {
            return "";
        }

        int lastDotIndex = filename.lastIndexOf("."); // something.jpg
        if (lastDotIndex == -1)
        {
            return "";
        }

        return filename.substring(lastDotIndex + 1).toLowerCase(); //Ex JPG -> jpg
    }
}
