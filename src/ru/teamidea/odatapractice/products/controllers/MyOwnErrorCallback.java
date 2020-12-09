package ru.teamidea.odatapractice.products.controllers;

import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.exception.ODataApplicationException;
import org.apache.olingo.odata2.api.processor.ODataErrorContext;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAErrorCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.olingo.odata2.api.processor.ODataErrorCallback;



public class MyOwnErrorCallback implements ODataErrorCallback{

    private static final Logger LOG = LoggerFactory.getLogger(MyOwnErrorCallback.class);
    
    @Override
    public ODataResponse handleError(final ODataErrorContext context) throws ODataApplicationException {
      context.getException().printStackTrace();
      LOG.error(context.getInnerError());
      LOG.error(context.getException().getMessage());   
      LOG.error(context.getMessage());
      return EntityProvider.writeErrorDocument(context);
    }
    
    
    public boolean isDebugEnabled()
    { 
        LOG.info("debug odata enabled");
       
      return true; 
    }
}
