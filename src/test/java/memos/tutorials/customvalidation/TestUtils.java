package memos.tutorials.customvalidation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public final class TestUtils {

    private TestUtils() {
    }

    public static MappingJackson2HttpMessageConverter getMessageConverter() {
        MappingJackson2HttpMessageConverter mappingConverter = new MappingJackson2HttpMessageConverter();
        mappingConverter.setObjectMapper(getObjectMapper());
        return mappingConverter;
    }

    public static ObjectMapper getObjectMapper() {
        return new Jackson2ObjectMapperBuilder().createXmlMapper(false)
                                                .build();
    }

    public static MockMvc buildMockMvc(Object controller) {
        return MockMvcBuilders.standaloneSetup(controller)
                              .setMessageConverters(getMessageConverter())
                              .build();
    }
}
