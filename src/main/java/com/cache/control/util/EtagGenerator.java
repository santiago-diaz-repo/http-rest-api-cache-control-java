package com.cache.control.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
public class EtagGenerator {

    public String getEtag(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String carAsString = objectMapper.writeValueAsString(object);
        StringBuilder builder = new StringBuilder(37);
        //builder.append("\"0");
        DigestUtils.appendMd5DigestAsHex(carAsString.getBytes(), builder);
        return builder.toString();
    }
}
