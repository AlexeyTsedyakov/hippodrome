import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class MainTest {
    @Disabled(value = "manual launch")
    @Test
    @Timeout(value = 22)
    void main_checkTimeout() throws Exception {
        Main.main(new String[0]);
    }
}
