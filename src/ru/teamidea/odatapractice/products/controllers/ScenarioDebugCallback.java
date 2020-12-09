package ru.teamidea.odatapractice.products.controllers;

import org.apache.olingo.odata2.api.ODataDebugCallback;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.exception.ODataApplicationException;
import org.apache.olingo.odata2.api.processor.ODataErrorContext;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ScenarioDebugCallback implements ODataDebugCallback {
    
    private static final Logger LOG = LoggerFactory.getLogger(ScenarioDebugCallback.class);
    
    @Override
    public boolean isDebugEnabled() { 
      return true; 
    }
    
   
  }
