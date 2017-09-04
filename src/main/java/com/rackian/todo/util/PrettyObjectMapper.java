package com.rackian.todo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class PrettyObjectMapper extends ObjectMapper {

    public PrettyObjectMapper() {
        enable(SerializationFeature.INDENT_OUTPUT);
    }

}
