package com.github.kelly.web.ui;

import java.util.HashMap;
import java.util.Map;

public class ModelImpl implements Model{

    // static
    private static final Map<String, Object> modelMap = new HashMap<>();


    @Override
    public void addAttribute(String attributeName, Object attributeValue) {
        System.out.println("attributeName = " + attributeName + "/  attributeValue = " + attributeValue);
        modelMap.put(attributeName, attributeValue);
        System.out.println(" ModelImpl 의 modelMap = " + modelMap);
    }

    @Override
    public Map<String, Object> getMap() {
        System.out.println("ModelImpl - getMap 의 modelMap = " + modelMap);
        return modelMap;
    }

}
