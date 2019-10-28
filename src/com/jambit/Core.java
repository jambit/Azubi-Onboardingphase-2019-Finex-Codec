package com.jambit;

public class Core {
    public static void main(String[] args) {
        Encrypter encrypter = new Encrypter();
        System.out.println(encrypter.encrypt("Hallo123" ,10));
        System.out.println(encrypter.getEncryptionKey());
        encrypter.randomCharSet(202);
        System.out.println(encrypter.encrypt("Hallo123", 10));
    }
}
