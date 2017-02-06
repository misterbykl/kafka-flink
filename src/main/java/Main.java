import config.MainConfig;
import config.flink.FlinkConfig;
import config.kafka.ConsumerConfig;
import config.service.ServiceConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
import util.ApplicationUtil;
import util.ExceptionUtil;

/**
 * misterbaykal
 * <p>
 * 05/02/2017 15:20
 */
@PropertySource(value = ApplicationUtil.APPLICATION_PROPERTIES)
public class Main {

    public static String INPUT = null;
    public static String PROCESSOR = null;
    public static String OUTPUT = null;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     *             <p>
     *             misterbaykal
     *             <p>
     *             05/02/2017 15:33
     */
    public static void main(String[] args) {
        ApplicationUtil.setSystemProperties();
        String input = args[0];
        String processor = args[1];
        String output = args[2];
        System.out.println("kafka-flink is starting...");
        AnnotationConfigApplicationContext applicationContext = null;
        try {
            applicationContext = new AnnotationConfigApplicationContext();
            applicationContext.register(MainConfig.class);
            applicationContext.register(ServiceConfig.class);
            if (input.equalsIgnoreCase("kafka")) {
                applicationContext.register(ConsumerConfig.class);
                Main.INPUT = input;
            }
            if (processor.equalsIgnoreCase("flink")) {
                applicationContext.register(FlinkConfig.class);
                Main.PROCESSOR = processor;
            }
            if (output.equalsIgnoreCase("text")) {
                Main.OUTPUT = output;
            }
            applicationContext.refresh();
            applicationContext.registerShutdownHook();
            System.out.println("kafka-flink has started");

        } catch (Exception e) {
            System.out.println("Exception while starting app");
            ExceptionUtil.getStackTraceString(e, "main");
        }
    }
}
