package com.jambit;

public class Core {
    public static void main(String[] args) throws Exception {
        UserInterface ui = new UserInterface();
        Encrypter encrypter = new Encrypter();
        ui.startMenu();
        String key = ui.getEncryptionKey();
        String message = ui.getMessage();
        String encryptedMessage = "";
        if(!message.equals("") && !key.equals("")){
            encryptedMessage = encrypter.encrypt(message, key);
        }
        else if(!message.equals("") && key.equals("")){
            encryptedMessage = encrypter.encrypt(message);
        }
        System.out.println("Message: " + encryptedMessage);
        System.out.println("Key: " + encrypter.getEncryptionKey());
        ui.writeToFile(encryptedMessage);

    }
}
