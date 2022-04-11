package cat.albirar.daw.tdd;

import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import cat.albirar.daw.tdd.comptes.impl.ServeiComptesImpl;

@SpringBootApplication
@ComponentScan(basePackageClasses = ServeiComptesImpl.class)
public class TddApplication {
	    
    @Bean public MethodValidationPostProcessor mmethodValidationPostProcessor(@Autowired ValidatorFactory validatorFactory) {
        MethodValidationPostProcessor mp;
        
        mp = new MethodValidationPostProcessor();
        mp.setValidatorFactory(validatorFactory);
        return mp;
    }
    
    @Bean public ValidatorFactory localValidatorFactoryBean() {
        return new LocalValidatorFactoryBean();
    }

	public static void main(String[] args) {
		SpringApplication.run(TddApplication.class, args);
	}

}
