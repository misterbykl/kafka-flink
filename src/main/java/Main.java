import config.MainConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * misterbaykal
 * <p>
 * 05/02/2017 15:20
 */
@SuppressWarnings("WeakerAccess")
@SpringBootApplication
public class Main {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     *             <p>
     *             misterbaykal
     *             <p>
     *             31/07/2017 11:21
     */

    public static void main(String[] args) {
        SpringApplication.run(MainConfig.class, args);
    }
}
