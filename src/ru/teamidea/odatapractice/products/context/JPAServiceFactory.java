package ru.teamidea.odatapractice.products.context;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;

import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAServiceFactory;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import ru.teamidea.odatapractice.products.util.SpringContextUtil;


@Configuration
public class JPAServiceFactory extends ODataJPAServiceFactory {

	private static final String PERSISTENT_UNIT = "ODataSpring";
	private static final String EMF = "entityManagerFactory";

	private static final Logger LOG = LoggerFactory.getLogger(JPAServiceFactory.class);

	@Override
	public ODataJPAContext initializeODataJPAContext() throws ODataJPARuntimeException {
		ODataJPAContext oDataJPACtx = getODataJPAContext();
		ODataContext octx = oDataJPACtx.getODataContext();
//        HttpServletRequest request = (HttpServletRequest) octx.getParameter(
//        ODataContext.HTTP_SERVLET_REQUEST_OBJECT);
//        EntityManager em = (EntityManager)request.getAttribute(JerseyConfig.EntityManagerFilter.EM_REQUEST_ATTRIBUTE);
//        
//      oDataJPACtx.setEntityManager(em);
		EntityManagerFactory emf = (EntityManagerFactory) SpringContextUtil.getBean(EMF);
		LOG.debug("EMF in JPAservicefactory " + emf);
		oDataJPACtx.setEntityManagerFactory(emf);
		oDataJPACtx.setPersistenceUnitName(PERSISTENT_UNIT);
//		oDataJPACtx.setJPAEdmMappingModel("EspmEdmMapping.xml");
		
		oDataJPACtx.setDefaultNaming(true);
		this.setDetailErrors(true);
		return oDataJPACtx;
	}

}
