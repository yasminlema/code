package proyectoDam.PlanetaDigital.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // mapea la ruta de las imagenes y pdf ya que se encuentran en la raiz del priyecto y sino no los encuentra
        registry.addResourceHandler("/imagenes/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/subidas/imagenes/");
        
        registry.addResourceHandler("/pdf/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/subidas/pdf/");
    }
}
