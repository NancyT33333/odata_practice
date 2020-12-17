package ru.teamidea.odatapractice.products.controllers;

import org.apache.olingo.odata2.api.ODataDebugCallback;

public final class ScenarioDebugCallback implements ODataDebugCallback {    
    
    @Override
    public boolean isDebugEnabled() { 
      return true; 
    }   
   
  }
