package ru.teamidea.odatapractice.products.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.AnnotationAttribute;
import org.apache.olingo.odata2.api.edm.provider.AnnotationElement;
import org.apache.olingo.odata2.api.edm.provider.Association;
import org.apache.olingo.odata2.api.edm.provider.AssociationEnd;
import org.apache.olingo.odata2.api.edm.provider.ComplexType;
import org.apache.olingo.odata2.api.edm.provider.EntityContainer;
import org.apache.olingo.odata2.api.edm.provider.Property;
import org.apache.olingo.odata2.api.edm.provider.Schema;
import org.apache.olingo.odata2.api.edm.provider.SimpleProperty;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmExtension;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmSchemaView;
import org.springframework.util.ResourceUtils;

public class ProductsProcessingExtension implements JPAEdmExtension{
    @Override
    public void extendJPAEdmSchema( JPAEdmSchemaView view) {
      Schema edmSchema = view.getEdmSchema();
//      ComplexType compType = getComplexType();
//      List<ComplexType> list= new ArrayList<ComplexType>();
//      list.add(compType);
//      edmSchema.setComplexTypes(list);

    }

    private ComplexType getComplexType() {
      ComplexType complexType = new ComplexType();

      List<Property> properties = new ArrayList<Property>();
      SimpleProperty property = new SimpleProperty();

      property.setName("FullName");
      property.setType(EdmSimpleTypeKind.String);
      properties.add(property);
     
      complexType.setName("FullName");
      complexType.setProperties(properties);

      return complexType;

    }

    @Override
    public void extendWithOperation(JPAEdmSchemaView view) {
      
    }


    @Override
    public InputStream getJPAEdmMappingModelStream() {
        try {
            File file = ResourceUtils.getFile("classpath:EdmJPAMapping.xml");
            InputStream in = new FileInputStream(file);
            return in;
        } catch (FileNotFoundException e) {
            return null;
        }
    }
    

    
    
}
