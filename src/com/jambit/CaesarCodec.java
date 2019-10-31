package com.jambit;

import java.util.Random;

/**
 * Encrypts messages with a key
 */
public class CaesarCodec {
    private String encryptionKey;
    public static final String charSet =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZÄÜÖabcdefghijklmnopqrstuvwxyzäüöß0123456789,.!?\"§$%&/()=+-*\\_#~<>| ";

    public String getEncryptionKey() {
        return encryptionKey + "";
    }

    /**
     * encrypts a String with a random key
     *
     * @param msg String to encrypt
     * @return returns an encrypted String
     */
    public String encrypt(String msg) {
        encryptionKey = generateKey();
        return encrypt(msg, encryptionKey);
    }

    /**
     * encrypts a String with a encryption Key
     *
     * @param msg           String to encrypt
     * @param encryptionKey Key you want to use
     * @return returns a encrypted String
     */
    public String encrypt(String msg, String encryptionKey) {
        StringBuilder encryptedString = new StringBuilder();
        KeyDetector keyDetector = new KeyDetector();

        this.encryptionKey = encryptionKey;
        String[] keys = splitKey(encryptionKey);
        keyDetector.detectKeys(keys);

        String[] splitLine = splitLine(msg);
        for (int i = 0; i < splitLine.length; i++) {
            encryptedString.append(encryptionCaesarCipher(splitLine[i], keyDetector.caesarCodecKey, keyDetector.newCharSet));
            if (i + 1 != splitLine.length) {
                encryptedString.append("\n");
            }
        }

        return encryptedString.toString();
    }

    public String decrypt(String msg, String encryptionKey) {
        KeyDetector keyDetector = new KeyDetector();

        this.encryptionKey = encryptionKey;
        String[] keys = splitKey(encryptionKey);
        keyDetector.detectKeys(keys);

        return encryptionCaesarCipher(msg, (keyDetector.caesarCodecKey * -1), keyDetector.newCharSet);
    }

    /**
     * encrypts the message with Caesar cipher
     *
     * @param msg     String to encrypt
     * @param key     key to use for the encrypt
     * @param charSet charter set yo want to use for the encryption
     * @return returns encrypted message
     */
    private String encryptionCaesarCipher(String msg, int key, String charSet) {
        StringBuilder encryptedMessage = new StringBuilder();

        for (int i = 0; i < msg.length(); i++) {
            int loc = charSet.indexOf(msg.charAt(i)) + key;

            if (loc > charSet.length() - 1) {
                loc -= charSet.length();
            } else if (loc < 0) {
                loc += charSet.length();
            }

            encryptedMessage.append(charSet.charAt(loc));
        }

        return encryptedMessage.toString();
    }

    /**
     * Randomizes the character set
     *
     * @param seed seed to use for the randomization
     * @return updated char set
     */
    private String randomCharSet(int seed) {
        Random generator = new Random(seed);
        String newCharSet = charSet;

        for (int i = 1; i < generator.nextInt(1000); i++) {
            for (int j = 0; j < newCharSet.length(); j++) {
                generator = new Random(seed + (i * 3 * (j + 1)));
                int randNum = generator.nextInt(newCharSet.length() - 1);
                newCharSet = swapIndex(newCharSet, j, randNum);
            }
        }

        return newCharSet;
    }

    /**
     * Swap 2 indexes in a string
     *
     * @param str string to swap string
     * @param i1  index1
     * @param i2  index2
     * @return string after swap
     */
    private String swapIndex(String str, int i1, int i2) {
        StringBuilder sb = new StringBuilder(str);
        sb.setCharAt(i1, str.charAt(i2));
        sb.setCharAt(i2, str.charAt(i1));
        return sb.toString();
    }

    /**
     * Generates a Key
     *
     * @return returns the key as String
     */
    private String generateKey() {
        Random generator = new Random();
        return generator.nextInt() + ":" + generator.nextInt(charSet.length());
    }

    /**
     * Spits a key into multiple parts
     *
     * @param key key you want to split
     * @return a String array with all keys
     */
    private String[] splitKey(String key) {
        if (key.contains(":")) {
            return key.split("[:]");
        } else {
            String[] i = {key};
            return i;
        }
    }

    private String[] splitLine(String msg) {
        if (msg.contains("\n")) {
            return msg.split("\r\n");
        } else {
            String[] splittedMessage = {msg};
            return splittedMessage;
        }
    }

    private class KeyDetector {
        public String newCharSet = charSet;
        public int caesarCodecKey;

        public void detectKeys(String[] keys){
            if (keys.length == 2) {
                newCharSet = randomCharSet(Integer.parseInt(keys[0]));
                caesarCodecKey = Integer.parseInt(keys[1]);
            } else {
                caesarCodecKey = Integer.parseInt(keys[0]);
            }
        }
    }
}
