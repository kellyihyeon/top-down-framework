package com.github.kelly.web.ui;

import java.util.HashMap;
import java.util.Map;

public class ModelImpl implements Model{

    private final Map<String, Object> modelMap = new HashMap<>();


    @Override
    public void addAttribute(String attributeName, Object attributeValue) {
        modelMap.put(attributeName, attributeValue);
    }

    @Override
    public Map<String, Object> getMap() {
        return modelMap;
    }

}
