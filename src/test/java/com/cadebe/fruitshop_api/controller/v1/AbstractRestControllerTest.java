package com.cadebe.fruitshop_api.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;

abstract class AbstractRestControllerTest {

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}