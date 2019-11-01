package com.jambit;

public class Core {
    public static void main(String[] args) throws Exception {
        UserInterface ui = new UserInterface();
        CaesarCodec caesarCodec = new CaesarCodec();
        RSACodec rsaCodec = new RSACodec();
        /**
        ui.opensTitleScreen();
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
        ui.saveFileChooser(encryptedMessage);
**/
        String[] x = rsaCodec.asymmetricKeyGenerator(1217,2963);
        System.out.println("keys \n" + x[0] + "\n" + x[1]);
        rsaCodec.RCAEncrypt(rsaCodec.RCAEncrypt("ABC", x[0]), x[1]);
    }
}
