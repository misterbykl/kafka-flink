package service;

/**
 * misterbaykal
 * <p>
 * 05/02/2017 15:04
 */
public class Service {

    private String processorType = null;

    /**
     * Instantiates a new Service.
     *
     * @param processorType the processor type
     *                      <p>
     *                      5/2/2017 16:04
     */
    public Service(String processorType) {
        this.processorType = processorType;
    }

    /**
     * Manage.
     *
     * @param value the value
     *              <p>
     *              5/2/2017 16:04
     */
    public void manageKafka(String value) {
        switch (this.processorType) {
            case "flink":
                break;
            default:
                break;
        }
    }
}
