package com.jambit;

import java.util.Random;

/**
 * Encrypts messages with a key
 */
public class Encrypter {
    private int encryptionKey;
    static String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZÄÜÖabcdefghijklmnopqrstuvwxyzäüöß0123456789,.!?\"§$%&/()=+-*\\_#~<>| ";


    public int getEncryptionKey() {
        return encryptionKey;
    }

    /**
     * encrypts a String with a random key
     * @param msg String to encrypt
     * @return returns a encrypted String
     */
    public String encrypt(String msg) {
        encryptionKey = (int) (Math.random() * charSet.length() - 1) + 1;
        return encryptionCaesarCipher(msg, encryptionKey);
    }

    /**
     * encrypts a String with a encryption Key
     * @param msg String to encrypt
     * @param encryptionKey Key you want to use
     * @return returns a encrypted String
     */
    public String encrypt(String msg, int encryptionKey) {
        this.encryptionKey = encryptionKey;
        return encryptionCaesarCipher(msg, encryptionKey);
    }

    /**
     * encrypts the message with Caesar cipher
     * @param msg String to encrypt
     * @param key key to use for the encrypt
     * @return returns encrypted message
     */
    private String encryptionCaesarCipher(String msg, int key) {
        StringBuilder encryptedMessage = new StringBuilder();

        for (int i = 0; i < msg.length(); i++) {
            int loc = charSet.indexOf(msg.charAt(i)) + key;

            if (loc > charSet.length() - 1) {
                loc -= charSet.length();
            }

            encryptedMessage.append(charSet.charAt(loc));
        }

        return encryptedMessage.toString();
    }

    /**
     * Randomizes the character set
     * @param seed seed to use for the randomization
     */
    public void randomCharSet(int seed) {
        Random generator = new Random(seed);
        for (int i = 1; i < generator.nextInt(1000); i++) {
            for (int j = 0; j < charSet.length(); j++) {
                generator = new Random(seed + (i * 3 * (j + 1)));
                int randNum = generator.nextInt(charSet.length() - 1);
                charSet = swapIndex(charSet, j, randNum);
            }
        }
        System.out.println(charSet);
    }

    /**
     * Swap 2 indexes in a string
     * @param str string to swap string
     * @param i1 index1
     * @param i2 index2
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
     * @return returns the key as String
     */
    private String generateKey(){
        Random generator = new Random();
        String key = generator.nextInt() + ":" + generator.nextInt(charSet.length());
        return key;
    }

    /**
     * Spits a key into multiple parts
     * @param key key you want to split
     * @return a String array with all keys
     */
    private String[] splitKey(String key){
        String[] keys = key.split("[:]");
        if(keys.length < 2) {
            return null;
        }else{
            return keys;
        }
    }
}
