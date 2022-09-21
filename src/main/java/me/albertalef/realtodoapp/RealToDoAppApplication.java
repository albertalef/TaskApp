package me.albertalef.realtodoapp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info =
@Info(
		title = "Task API",
		version = "1.0",
		description = "An API created for a job from UCSAL"
)
)
public class RealToDoAppApplication {

	public static void main( String[] args ) {
		SpringApplication.run(RealToDoAppApplication.class, args);
	}

}
