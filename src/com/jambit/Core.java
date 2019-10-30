package com.jambit;

public class Core {
    public static void main(String[] args) throws Exception {
        UserInterface ui = new UserInterface();
        caesarCodec caesarCodec = new caesarCodec();
        ui.ascii();
        String key = ui.getEncryptionKey();
        String message = ui.getMessage();
        String encryptedMessage = "";
        if(!message.equals("") && !key.equals("")){
            encryptedMessage = caesarCodec.encrypt(message, key);
        }
        else if((!message.equals("") && key.equals(""))){
            encryptedMessage = caesarCodec.encrypt(message);
        }
        System.out.println("Message: " + encryptedMessage);
        System.out.println("Key: " + caesarCodec.getEncryptionKey());
        ui.fileWriterBrowser(encryptedMessage);

    }
}
