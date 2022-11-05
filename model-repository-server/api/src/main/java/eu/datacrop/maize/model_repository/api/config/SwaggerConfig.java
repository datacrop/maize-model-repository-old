package eu.datacrop.maize.model_repository.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**********************************************************************************************************************
 * This class contains configurations for the Swagger auto-generated documentation.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.3.0
 *********************************************************************************************************************/
@Configuration
public class SwaggerConfig {

    /*****************************************************************************************************************
     * Name of a contact person or team regarding the API.
     ****************************************************************************************************************/
    @Value("${swaggerui.contact.name:DataCROP Development Team}")
    private String contactName;

    /*****************************************************************************************************************
     * Website of a contact person or team regarding the API.
     ****************************************************************************************************************/
    @Value("${swaggerui.contact.url:http://datacrop.eu/}")
    private String contactUrl;

    /*****************************************************************************************************************
     * E-mail address of a contact person or team regarding the API.
     ****************************************************************************************************************/
    @Value("${swaggerui.contact.email:datacrop@googlegroups.com}")
    private String contactEmail;

    /*****************************************************************************************************************
     * This method configures the header of the automatically generated Swagger interface.
     *
     * @return OpenAPI An object representing the API information.
     ****************************************************************************************************************/
    @Bean
    public OpenAPI apiInfo() {

        Contact contact = new Contact();
        contact.setName(contactName);
        contact.setUrl(contactUrl);
        contact.setEmail(contactEmail);

        return new OpenAPI()
                .info(new Info().title("Model Repository")
                        .description("DataCROP Model Repository RESTful API")
                        .version("1.0.0")
                        .contact(contact)
                        .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}
