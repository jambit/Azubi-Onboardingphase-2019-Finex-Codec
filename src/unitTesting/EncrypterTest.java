package unitTesting;

import com.jambit.Encrypter;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EncrypterTest {
    Encrypter encrypter = new Encrypter();

    @BeforeAll
    public static void initAll() {

    }

    @Test
    @DisplayName("Basic Encryption")
    void basicEncryption() {
        String input = "ABC";
        assertEquals("FGH", encrypter.encrypt(input, 5));
    }

    @Test
    @DisplayName("Frequent (Basic) Encryption")
    void frequentBasicEncryption() {
        String input = "ABC";
        for (int i = 0; i < 10; i++) {
            assertEquals("PQR", encrypter.encrypt(input, 15));
        }
    }

    @Test
    @DisplayName("Random Encryption")
    void randomEncryption() {
        String input = "ABC";
        String out = encrypter.encrypt(input);
        assertEquals(out, encrypter.encrypt(input, encrypter.getEncryptionKey()));
    }
}