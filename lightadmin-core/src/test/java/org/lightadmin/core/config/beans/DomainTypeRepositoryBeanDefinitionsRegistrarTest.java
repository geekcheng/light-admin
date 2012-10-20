package org.lightadmin.core.config.beans;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.lightadmin.core.annotation.Administration;
import org.lightadmin.core.persistence.repository.DynamicJpaRepository;
import org.lightadmin.core.persistence.repository.support.DynamicJpaRepositoryFactoryBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

import static org.junit.Assert.assertEquals;
import static org.lightadmin.core.test.util.BeanDefinitionUtils.constructorArgValue;
import static org.lightadmin.core.test.util.BeanDefinitionUtils.propertyValue;

public class DomainTypeRepositoryBeanDefinitionsRegistrarTest {

	private static final String REPOSITORY_BEAN_NAME = "org.lightadmin.core.persistence.repository.domainEntityRepository";

	private DomainTypeRepositoryBeanDefinitionRegistrar subject;

	@Before
	public void setup() {
		subject = new DomainTypeRepositoryBeanDefinitionRegistrar( DomainEntityConfiguration.class );
	}

	@Test
	public void domainTypeRepositoryBeanDefinitionRegistered() throws Exception {
		final Capture<BeanDefinition> beanDefinitionCapture = new Capture<BeanDefinition>();

		final BeanDefinitionRegistry beanDefinitionRegistry = EasyMock.createMock( BeanDefinitionRegistry.class );

		beanDefinitionRegistry.registerBeanDefinition( EasyMock.eq( REPOSITORY_BEAN_NAME ), EasyMock.<BeanDefinition>capture( beanDefinitionCapture ) );
		EasyMock.expectLastCall();

		EasyMock.replay( beanDefinitionRegistry );

		subject.registerBeanDefinitions( beanDefinitionRegistry );

		EasyMock.verify( beanDefinitionRegistry );

		final BeanDefinition beanDefinition = beanDefinitionCapture.getValue();

		assertEquals( DynamicJpaRepositoryFactoryBean.class.getName(), beanDefinition.getBeanClassName() );

		assertEquals( DomainEntity.class, constructorArgValue( beanDefinition, 0 ) );

		assertEquals( DynamicJpaRepository.class, propertyValue( beanDefinition, "repositoryInterface" ) );
	}

	private static class DomainEntity {
	}

	@Administration( DomainEntity.class )
	private static class DomainEntityConfiguration {
	}
}