package com.github.kelly.web.ui;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {

    private String viewName;
    private final Map<String, Object> modelMap = new HashMap<>();


    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public ModelAndView addObject(String attributeName, Object attributeValue) {
        modelMap.put(attributeName, attributeValue);
        return this;
    }

    public String getViewName() {
        return viewName;
    }

    public Map<String, Object> getModelMap() {
        return modelMap;
    }
}
