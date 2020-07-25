package cn.apelx.elasticsearch.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * LocalDateTime Jackson 序列化
 *
 * @author lx
 * @since 2020/7/23 8:48
 */
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
    /**
     * Method that can be called to ask implementation to serialize
     * values of type this serializer handles.
     *
     * @param localDateTime Value to serialize; can <b>not</b> be null.
     * @param jsonGenerator Generator used to output resulting Json content
     * @param serializers   Provider that can be used to get serializers for
     */
    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        jsonGenerator.writeString(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
    }
}
