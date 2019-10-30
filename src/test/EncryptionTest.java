import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.jambit.Encrypter;

import java.util.Random;

class EncryptionTest {
  Encrypter encrypter = new Encrypter();
  Random rand = new Random();

  String generateRandomText(int length){
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++) {
      sb.append(Encrypter.charSet.charAt(rand.nextInt(Encrypter.charSet.length())));
    }
    return sb.toString();
  }

  @BeforeAll
  public static void initAll() {
  }

  @BeforeEach
  void init() {
    encrypter = new Encrypter();
    rand = new Random();
  }

  @Test
  @DisplayName("Basic Encryption")
  void basicEncryption() {
    String input = "ABC";
    assertEquals("FGH", encrypter.encrypt(input, "5"));
  }

  @Test
  @DisplayName("Frequent (Basic) Encryption")
  void frequentBasicEncryption() {
    String input = "ABC";
    for (int i = 0; i < 10; i++) {
      assertEquals("PQR", encrypter.encrypt(input, "15"));
    }
  }

  @Test
  @DisplayName("Random Encryption")
  void randomEncryption() {
    String input = generateRandomText(rand.nextInt(50));
    assertEquals(input , encrypter.decrypt(encrypter.encrypt(input), encrypter.getEncryptionKey()));
  }

  @Test
  @DisplayName("Encryption Length Test")
  void lengthTest() {
    String input = generateRandomText(1_483_647);
    String encrypted = encrypter.encrypt(input);
    assertEquals(input, encrypter.decrypt(encrypted, encrypter.getEncryptionKey()));
  }
}
