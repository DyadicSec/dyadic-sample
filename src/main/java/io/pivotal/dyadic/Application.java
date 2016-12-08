package io.pivotal.dyadic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.crypto.Cipher;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    Cipher decryptionCipher(KeyPair keyPair) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "DYADIC");
        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
        return cipher;
    }

    @Bean
    Cipher encryptionCipher(KeyPair keyPair) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "DYADIC");
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
        return cipher;
    }

    @Bean
    KeyPair keyPair() throws GeneralSecurityException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "DYADIC");
        keyPairGenerator.initialize(2_048);
        return keyPairGenerator.generateKeyPair();
    }

    @Bean
    Signature signingSignature(KeyPair keyPair) throws GeneralSecurityException {
        Signature signature = Signature.getInstance("SHA1WithRSA");
        signature.initSign(keyPair.getPrivate());
        return signature;
    }

    @Bean
    Signature verificationSignature(KeyPair keyPair) throws GeneralSecurityException {
        Signature signature = Signature.getInstance("SHA1WithRSA");
        signature.initVerify(keyPair.getPublic());
        return signature;
    }

}
