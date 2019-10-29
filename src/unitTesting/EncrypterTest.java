package unitTesting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.jambit.Encrypter;
import org.junit.jupiter.api.*;

class EncrypterTest {
  Encrypter encrypter = new Encrypter();

  @BeforeAll
  public static void initAll() {}

  @BeforeEach
   void init() {
    encrypter = new Encrypter();
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
    String input = "ABC";
    String out = encrypter.encrypt(input);
    String key = encrypter.getEncryptionKey();
    encrypter = new Encrypter();
    assertEquals(out, encrypter.encrypt(input, key));
  }
}
