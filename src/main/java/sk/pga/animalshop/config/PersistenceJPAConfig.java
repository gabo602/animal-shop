package sk.pga.animalshop.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class PersistenceJPAConfig {
	
	@Bean
	@Primary
	@ConfigurationProperties("jdbc.datasource")
	public DataSource dataSource() {
		return new DriverManagerDataSource();
	}
	
	private JpaVendorAdapter vendorAdapter() {
		return new HibernateJpaVendorAdapter();
	}
	
	@Bean
	@ConfigurationProperties("jdbc.jpa")
	public JpaProperties jpaProperties() {
		return new JpaProperties();
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws Exception {
		final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan(new String[] { "sk.pga.animalshop.model" });
		em.setJpaVendorAdapter(vendorAdapter());
		em.setJpaPropertyMap(jpaProperties().getProperties());
		return em;
	}
	
	@Bean
	@Autowired
	@Primary
	public JpaTransactionManager transactionManager() throws Exception {
		return new JpaTransactionManager(entityManagerFactory().getObject());
	}
	
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
	
}
