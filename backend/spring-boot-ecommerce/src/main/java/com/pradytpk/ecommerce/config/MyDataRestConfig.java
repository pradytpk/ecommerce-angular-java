package com.pradytpk.ecommerce.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import com.pradytpk.ecommerce.entity.Product;
import com.pradytpk.ecommerce.entity.ProductCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer{
	
	private EntityManager entityManager;
	
	public MyDataRestConfig(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		// TODO Auto-generated method stub
		RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);
	        
		HttpMethod[] theUnsupportedActions = {
				HttpMethod.PUT,HttpMethod.POST, HttpMethod.DELETE
		};
		// disable HTTP methods for Product:PUT,POST and DELETE
		config.getExposureConfiguration()
				.forDomainType(Product.class)
				.withItemExposure((metdata,httpMethods)->httpMethods.disable(theUnsupportedActions))
				.withCollectionExposure((metdata,httpMethods)->httpMethods.disable(theUnsupportedActions));
		
		// disable HTTP methods for ProductCategory:PUT,POST and DELETE
		config.getExposureConfiguration()
				.forDomainType(ProductCategory.class)
				.withItemExposure((metdata,httpMethods)->httpMethods.disable(theUnsupportedActions))
				.withCollectionExposure((metdata,httpMethods)->httpMethods.disable(theUnsupportedActions));
	
		config.setDefaultMediaType(MediaType.APPLICATION_JSON);
        config.useHalAsDefaultJsonMediaType(false);
        
        // call an internal helper method
        exposeIds(config);	
		
	}
	
	private void exposeIds(RepositoryRestConfiguration config) {
		Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
		List<Class> entityClasses = new ArrayList<>();
		
		for(EntityType tempEntityType:entities) {
			entityClasses.add(tempEntityType.getJavaType());
			Class[] domainTypes= entityClasses.toArray(new Class[0]);
			config.exposeIdsFor(domainTypes);
		}
		
	}

}
