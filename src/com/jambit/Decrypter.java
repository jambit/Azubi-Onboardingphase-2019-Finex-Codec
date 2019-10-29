package com.jambit;

public class Decrypter {
    private String charSet = Encrypter.charSet;

    public String decrypt(String msg, int decryptionKey) {
        return decryptionCaesarCipher(msg, decryptionKey);
    }

    /**
     * decrypts the message with Caesar cipher
     * @param msg String to decrypt
     * @param key key to use for the decrypt
     * @return returns decrypted message
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
