import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.jambit.caesarCodec;

import java.util.Random;

class CaesarCodecTest {
  private caesarCodec caesarCodec = new caesarCodec();
  private Random rand = new Random();

  String generateRandomText(int length){
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++) {
      sb.append(com.jambit.caesarCodec.charSet.charAt(rand.nextInt(com.jambit.caesarCodec.charSet.length())));
    }
    return sb.toString();
  }

  @BeforeAll
  public static void initAll() {
  }

  @BeforeEach
  void init() {
    caesarCodec = new caesarCodec();
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
    assertEquals(input , caesarCodec.decrypt(caesarCodec.encrypt(input), caesarCodec.getEncryptionKey()));
  }

  @Test
  @DisplayName("Encryption Length Test")
  void lengthTest() {
    String input = generateRandomText(1_483_647);
    String encrypted = caesarCodec.encrypt(input);
    assertEquals(input, caesarCodec.decrypt(encrypted, caesarCodec.getEncryptionKey()));
  }
}
