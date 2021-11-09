package com.github.kelly.web.ui;

import java.util.Map;

public interface Model {

    void addAttribute(String attributeName, Object attributeValue);

    Map<String, Object> getMap();
}
