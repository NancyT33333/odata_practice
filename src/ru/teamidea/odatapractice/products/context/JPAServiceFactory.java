package ru.teamidea.odatapractice.products.context;

import javax.persistence.EntityManagerFactory;

import org.apache.olingo.odata2.api.ODataCallback;
import org.apache.olingo.odata2.api.ODataDebugCallback;
import org.apache.olingo.odata2.core.exception.ODataRuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAServiceFactory;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;



import ru.teamidea.odatapractice.products.controllers.MyOwnErrorCallback;
//import ru.teamidea.odatapractice.products.controllers.ProductsProcessingExtension;
import ru.teamidea.odatapractice.products.controllers.ScenarioDebugCallback;
import ru.teamidea.odatapractice.products.util.SpringContextUtil;

@Configuration
public class JPAServiceFactory extends ODataJPAServiceFactory {

    private static final String PERSISTENT_UNIT = "ODataSpring";
    private static final String EMF = "entityManagerFactory";

    private static final Logger LOG = LoggerFactory.getLogger(JPAServiceFactory.class);

    @Override
    public ODataJPAContext initializeODataJPAContext() throws ODataJPARuntimeException {

        ODataJPAContext oDataJPACtx = getODataJPAContext();

        EntityManagerFactory emf;
        try {
            emf = (EntityManagerFactory) SpringContextUtil.getBean(EMF);
            LOG.debug("EMF in JPAservicefactory " + emf);
            oDataJPACtx.setEntityManagerFactory(emf);
            oDataJPACtx.setPersistenceUnitName(PERSISTENT_UNIT);
           
//            oDataJPACtx.setJPAEdmExtension(new ProductsProcessingExtension());
        //    oDataJPACtx.setJPAEdmMappingModel("EdmJPAMapping.xml");
            oDataJPACtx.setDefaultNaming(true);
            this.setDetailErrors(true);
            return oDataJPACtx;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new ODataRuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends ODataCallback> T getCallback(final Class<T> callbackInterface) {
        return (T) (callbackInterface.isAssignableFrom(MyOwnErrorCallback.class) ? new MyOwnErrorCallback()
                : callbackInterface.isAssignableFrom(ODataDebugCallback.class) ? new ScenarioDebugCallback()
                        : super.getCallback(callbackInterface));
    }

}
