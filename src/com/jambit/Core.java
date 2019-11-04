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
            "6,885,504,246,554,828,384,087,415,603,658,257,350,653,491,352,908,727,084,269,274,251,128,970,496,841,095,964,191,423,343,840,423,242,746,065,818,644,381,755,098,684,469,437,571,625,221,589,028,925,993,636,730,046,888,255,089,860,191,931,798,582,016,349,272,609,671,289,066,901,447,099,451,098,774,985,396,436,475,765,766,177,521,615,939,399,385,661,004,611,754,934,299,311,075,295,193,348,934,800,505,200,056,222,289,689,331,317,067,060,190,952,751,756,913,773,442,310,588,063,962,666,650,528,959,706,302,794,161",
            "19,745,236,499,374,259,930,968,882,535,850,342,572,257,911,149,085,309,168,291,881,845,736,849,482,122,123,779,678,477,621,104,734,541,694,069,251,255,785,575,063,768,946,752,170,374,968,278,582,845,616,124,547,377,482,141,819,168,482,097,633,946,433,483,839,470,904,967,628,781,865,227,212,016,070,255,906,984,588,382,146,789,975,382,231,272,507,186,642,321,837,275,662,168,635,585,390,204,263,424,626,353,684,400,347,531,972,543,427,372,344,584,456,682,154,214,929,967,449,569,641,570,962,334,451,667,025,601,867");

    System.out.println("\nPUBLIC KEY\n" + keys[0] + "\nPRIVATE KEY\n" + keys[1]);
    String encrypt = rsaCodec.RSAEncrypt(msg, keys[0]);
    System.out.println("Encrypt:\n" + encrypt);
    try {
      ui.saveFileChooser(encrypt);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
