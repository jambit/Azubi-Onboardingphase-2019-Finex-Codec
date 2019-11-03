package com.jambit;

public class Core {
  public static void main(String[] args) throws Exception {
    UserInterface ui = new UserInterface();
    CaesarCodec caesarCodec = new CaesarCodec();
    RSACodec rsaCodec = new RSACodec();
    /**
     * ui.opensTitleScreen(); String key = ui.getEncryptionKey(); String message = ui.getMessage();
     * String encryptedMessage = ""; if(!message.equals("") && !key.equals("")){ encryptedMessage =
     * caesarCodec.encrypt(message, key); } else if((!message.equals("") && key.equals(""))){
     * encryptedMessage = caesarCodec.encrypt(message); } System.out.println("Message: " +
     * encryptedMessage); System.out.println("Key: " + caesarCodec.getEncryptionKey());
     * ui.saveFileChooser(encryptedMessage);
     */
    String[] x =
        rsaCodec.asymmetricKeyGenerator(
            "62596183837793660881747092379034939976984370076262238741250390581643860136423",
            "57,280,268,856,223,100,619,507,809,781,996,738,021,850,347,661,097,823,339,843,797,463,812,950,192,909");

    System.out.println("keys \n" + x[0] + "\n" + x[1]);
    String encrypt = rsaCodec.RCAEncrypt("hallo das ist ein test ", x[0]);
    System.out.println("Encrypt:\n" + encrypt);
    System.out.println("Decrypt:\n" + rsaCodec.RSADecoder(encrypt, x[1]));
  }
}
