package com.jambit;

import java.nio.charset.Charset;
import java.util.Random;

public class Encrypter {
    private int encryptionKey = 20;
    private String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZÄÜÖabcdefghijklmnopqrstuvwxyzäüöß0123456789,.!?\"§$%&/()=+-*\\_#~<>| ";

    public String encrypt(String msg) {
        encryptionKey = (int) (Math.random() * charSet.length() - 1) + 1;
        return encryptionAlgorithm(msg, encryptionKey);
    }

    public String encrypt(String msg, int encryptionKey) {
        this.encryptionKey = encryptionKey;
        return encryptionAlgorithm(msg, encryptionKey);
    }

    private String encryptionAlgorithm(String msg, int key) {
        String encryptedMessage = "";

        for (int i = 0; i < msg.length(); i++) {
            int loc = charSet.indexOf(msg.charAt(i)) + key;

            if (loc > charSet.length() - 1) {
                loc -= charSet.length();
            }

            encryptedMessage += charSet.charAt(loc);
        }

        return encryptedMessage;
    }

    public int getEncryptionKey() {
        return encryptionKey;
    }

}
