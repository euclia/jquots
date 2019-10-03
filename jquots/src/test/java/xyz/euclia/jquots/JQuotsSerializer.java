package xyz.euclia.jquots;




import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import xyz.euclia.jquots.serialize.Serializer;


//import xyz.euclia.jquots.serialize.Serializer;

/**
 * @author Pantelis panka
 */
class JQuotsSerializer implements Serializer {

    private final ObjectMapper objectMapper;

    JQuotsSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
//        this.objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        this.objectMapper.setAnnotationIntrospector(new JaxbAnnotationIntrospector());
    }

    @Override
    public void write(Object entity, OutputStream out) {
        try {
            objectMapper.writeValue(out, entity);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void write(Object entity, Writer writer) {
        try {
            objectMapper.writeValue(writer, entity);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String write(Object entity) {
        try {
            return objectMapper.writeValueAsString(entity);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public <T> T parse(String content, Class<T> valueType) {
        try {
            return objectMapper.readValue(content, valueType);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public <T> T parse(InputStream src, Class<T> valueType) {
        try {
            return objectMapper.readValue(src, valueType);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
