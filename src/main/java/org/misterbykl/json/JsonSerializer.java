package org.misterbykl.json;

import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * misterbaykal
 * <p>
 * 05/02/2017 14:24
 */
@SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
public class JsonSerializer<T> implements Serializer<T> {

    private Gson gson = new Gson();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, T data) {
        return gson.toJson(data).getBytes(Charset.forName("UTF-8"));
    }

    @Override
    public void close() {
    }
}
