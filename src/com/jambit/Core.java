package com.jambit;

public class Core {
  public static void main(String[] args) throws Exception {
    UserInterface ui = new UserInterface();
    try {
      ui.opensTitleScreen();
    } catch (Exception e) {
      e.printStackTrace();
    }
    ui.chooseEncrypterMethod();
    if (ui.getEncryptionChoice().equals("cc")) {
      caesar();
    } else if (ui.getEncryptionChoice().equals("rsa")) {
      rsa();
    } else {
      System.err.println("Something went very wrong");
    }
  }

  public static void caesar() throws Exception {
    UserInterface ui = new UserInterface();
    CaesarCodec caesarCodec = new CaesarCodec();
    ui.printMenu();
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

  public static void rsa() throws Exception {
    UserInterface ui = new UserInterface();
    ui.printMenu();
    String msg = ui.getMessage();
    RSACodec rsaCodec = new RSACodec();

    String[] keys =
        rsaCodec.asymmetricKeyGenerator(
            "6885504246554828384087415603658257350653491352908727084269274251128970496841095964191423343840423242746065818644381755098684469437571625221589028925993636730046888255089860191931798582016349272609671289066901447099451098774985396436475765766177521615939399385661004611754934299311075295193348934800505200056222289689331317067060190952751756913773442310588063962666650528959706302794161",
            "19745236499374259930968882535850342572257911149085309168291881845736849482122123779678477621104734541694069251255785575063768946752170374968278582845616124547377482141819168482097633946433483839470904967628781865227212016070255906984588382146789975382231272507186642321837275662168635585390204263424626353684400347531972543427372344584456682154214929967449569641570962334451667025601867");

    System.out.println("\nPUBLIC KEY\n" + keys[0] + "\nPRIVATE KEY\n" + keys[1]);
    String encrypt = rsaCodec.RSAEncrypt(msg, keys[0]);
    System.out.println("Encryption finished!");
    System.out.println("Decrypt:\n" + rsaCodec.RSADecrypt(encrypt, keys[1]));
    try {
      ui.saveFileChooser(encrypt);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
