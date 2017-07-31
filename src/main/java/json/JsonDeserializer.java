package json;

import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

/**
 * misterbaykal
 * <p>
 * 05/02/2017 14:24
 */
@SuppressWarnings({"ALL", "unused"})
public class JsonDeserializer<T> implements Deserializer<T> {

    private Gson gson = new Gson();
    private Class<T> deserializedClass;

    /**
     * Instantiates a new Json deserializer.
     *
     * @param deserializedClass the deserialized class
     *                          <p>
     *                          misterbaykal
     *                          <p>
     *                          05/02/2017 15:31
     */
    public JsonDeserializer(Class<T> deserializedClass) {
        this.deserializedClass = deserializedClass;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void configure(Map<String, ?> configs, boolean isKey) {
        if (deserializedClass == null) {
            deserializedClass = (Class<T>) configs.get("serializedClass");
        }
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }
        return gson.fromJson(new String(data), deserializedClass);
    }

    @Override
    public void close() {
    }
}
