import static org.junit.jupiter.api.Assertions.assertEquals;

import com.jambit.CaesarCodec;
import java.util.Random;
import org.junit.jupiter.api.*;

class CaesarCodecTest {
  private CaesarCodec caesarCodec = new CaesarCodec();
  private Random rand = new Random();

  String generateRandomText(int length) {
    StringBuilder randomTextBuilder = new StringBuilder();
    for (int i = 0; i < length; i++) {
      randomTextBuilder.append(
          CaesarCodec.charSet.charAt(rand.nextInt(CaesarCodec.charSet.length())));
    }
    return randomTextBuilder.toString();
  }

  @BeforeAll
  public static void initAll() {}

  @BeforeEach
  void init() {
    caesarCodec = new CaesarCodec();
    rand = new Random();
  }

  @Test
  @DisplayName("Basic Encryption")
  void basicEncryption() {
    String input = "ABC";
    assertEquals("FGH", caesarCodec.encrypt(input, "5"));
  }

  @Test
  @DisplayName("Frequent (Basic) Encryption")
  void frequentBasicEncryption() {
    String input = "ABC";
    for (int i = 0; i < 10; i++) {
      assertEquals("PQR", caesarCodec.encrypt(input, "15"));
    }
  }

  @Test
  @DisplayName("Random Encryption")
  void randomEncryption() {
    String input = generateRandomText(rand.nextInt(50));
    assertEquals(
        input, caesarCodec.decrypt(caesarCodec.encrypt(input), caesarCodec.getEncryptionKey()));
  }

  @Test
  @DisplayName("Encryption Length Test")
  void lengthTest() {
    String input = generateRandomText(1_483_647);
    String encrypted = caesarCodec.encrypt(input);
    assertEquals(input, caesarCodec.decrypt(encrypted, caesarCodec.getEncryptionKey()));
  }
}
