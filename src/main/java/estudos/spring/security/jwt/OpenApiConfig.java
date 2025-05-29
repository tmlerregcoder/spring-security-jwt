package estudos.spring.security.jwt;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI springSecurityJwtOpenAPI() {
        return new OpenAPI()
            .info(new Info().title("Teste de JWT - Rest API")
                .description("API exemplo de uso de Springboot REST API com Spring security e JWT")
                .version("1.0")
                .termsOfService("Termo de uso: Open Source")
                .license(new License().name("").url("")) // Se não tiver licença ou URL, pode deixar vazio ou remover
                .contact(new Contact().name("Thiago Marques Simões da Conceição")
                                     .email("lerreg22@gmail.com")))
            .externalDocs(new ExternalDocumentation() // Opcional, se tiver documentação externa
                .description("Repositório GitHub")
                .url("")); // Coloque a URL do seu repositório, se desejar
    }
}