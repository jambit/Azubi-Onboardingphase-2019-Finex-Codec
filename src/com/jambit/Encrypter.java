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

    public void randomCharSet(int seed) {
        Random generator = new Random(seed);
        for (int i = 1; i < generator.nextInt(1000); i++) {
            for (int j = 0; j < charSet.length(); j++) {
                generator = new Random(seed + (i * (j + 1)));
                int randNum = generator.nextInt(charSet.length() - 1);
                charSet = swapIndex(charSet, j, randNum);
            }
        }
        System.out.println(charSet);
    }

    private String swapIndex(String str, int i1, int i2) {
        StringBuilder sb = new StringBuilder(str);
        sb.setCharAt(i1, str.charAt(i2));
        sb.setCharAt(i2, str.charAt(i1));
        return sb.toString();
    }

}
