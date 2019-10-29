package com.jambit;

public class Decrypter {
    private String charSet = Encrypter.charSet;

    public String decrypt(String msg, int encryptionKey) {
        return decryptionCaesarCipher(msg, encryptionKey);
    }

    /**
     * encrypts the message with Caesar cipher
     * @param msg String to encrypt
     * @param key key to use for the encrypt
     * @return returns encrypted message
     */
    private String decryptionCaesarCipher(String msg, int key) {
        StringBuilder decryptedMessage = new StringBuilder();

        for (int i = 0; i < msg.length(); i++) {
            int loc = charSet.indexOf(msg.charAt(i)) - key;

            if (loc < 0) {
                loc += charSet.length();
            }

            decryptedMessage.append(charSet.charAt(loc));
        }

        return decryptedMessage.toString();
    }
}
