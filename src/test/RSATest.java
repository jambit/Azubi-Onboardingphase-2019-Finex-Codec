import static org.junit.jupiter.api.Assertions.assertEquals;

import com.jambit.RSACodec;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RSATest {
  RSACodec rsaCodec = new RSACodec();

  @Test
  @DisplayName("RSA Basic test")
  void RSABasicTest() {
    String testMsg = CaesarCodecTest.generateRandomText(1000);
    String[] keys =
        rsaCodec.asymmetricKeyGenerator(
            "62596183837793660881747092379034939976984370076262238741250390581643860136423",
            "57,280,268,856,223,100,619,507,809,781,996,738,021,850,347,661,097,823,339,843,797,463,812,950,192,909");

    System.out.println("\nPUBLIC KEY\n" + keys[0] + "\nPRIVATE KEY\n" + keys[1]);
    String encryptedMsg = rsaCodec.RSAEncrypt(testMsg, keys[0]);
    String decryptedMsg = rsaCodec.RSAEncrypt(testMsg, keys[0]);
    System.out.println("Encrypt:\n" + encryptedMsg);
    System.out.println("Decrypt:\n" + rsaCodec.RSADecrypt(decryptedMsg, keys[1]));
    assertEquals(encryptedMsg, decryptedMsg);
  }
}
