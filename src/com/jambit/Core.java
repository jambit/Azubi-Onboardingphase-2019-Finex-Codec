package com.jambit;

public class Core {
  public static void main(String[] args) {
    rsa("hey");
    // caesar();
  }

  public static void caesar() {
    UserInterface ui = new UserInterface();
    CaesarCodec caesarCodec = new CaesarCodec();
    try {
      ui.opensTitleScreen();
    } catch (Exception e) {
      e.printStackTrace();
    }
    String key = ui.getEncryptionKey();
    String message = ui.getMessage();
    String encryptedMessage = "";
    if (!message.equals("") && !key.equals("")) {
      encryptedMessage = caesarCodec.encrypt(message, key);
    } else if ((!message.equals("") && key.equals(""))) {
      encryptedMessage = caesarCodec.encrypt(message);
    }
    System.out.println("Message: " + encryptedMessage);
    System.out.println("Key: " + caesarCodec.getEncryptionKey());
    try {
      ui.saveFileChooser(encryptedMessage);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void rsa(String msg) {
    RSACodec rsaCodec = new RSACodec();
    System.out.println("---RSA---");

    String[] x =
        rsaCodec.asymmetricKeyGenerator(
            "62596183837793660881747092379034939976984370076262238741250390581643860136423",
            "57,280,268,856,223,100,619,507,809,781,996,738,021,850,347,661,097,823,339,843,797,463,812,950,192,909");

    System.out.println("\nPUBLIC KEY\n" + x[0] + "\nPRIVATE KEY\n" + x[1]);
    String encrypt = rsaCodec.RCAEncrypt(msg, x[0]);
    System.out.println("Encrypt:\n" + encrypt);
    System.out.println("Decrypt:\n" + rsaCodec.RSADecrypt(encrypt, x[1]));
  }
}
