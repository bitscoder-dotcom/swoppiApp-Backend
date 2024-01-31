package com.bitscoder.swoppiapp.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component("secretKeyGenerator")
public class SecretKeyGenerator {

    @Value("${jwtSecretKey.filepath}")
    private String configFilePath;

    @PostConstruct
    public void generateAndSaveSecretKey() throws NoSuchAlgorithmException, IOException {
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSha512");
        java.security.Key key = keyGen.generateKey();
        String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());

        // Read the existing content of the jwtSecretKey.file
        String content = Files.exists(Paths.get(configFilePath)) ? new String(Files.readAllBytes(Paths.get(configFilePath))) : "";

        // Check if the secret key already exists in the jwtSecretKey.file
        if (content.contains("swoppiApp.jwtSecretKey")) {
            // If it exists, replace it with the new secret key
            content = content.replaceAll("(?<=swoppiApp.jwtSecretKey=).*", encodedKey);
        } else {
            // If it doesn't exist, add the secret key to the jwtSecretKey.file
            content += "\nswoppiApp.jwtSecretKey=" + encodedKey;
        }

        // Write the new content back to the jwtSecretKey.file
        Files.write(Paths.get(configFilePath), content.getBytes());
    }
}
