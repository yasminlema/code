package proyectoDam.PlanetaDigital.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Mapea la ruta URL /imagenes/** a la carpeta física /subidas/imagenes/
        registry.addResourceHandler("/imagenes/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/subidas/imagenes/");
        
        // Mapea la ruta URL /pdf/** a la carpeta física /subidas/pdf/
        registry.addResourceHandler("/pdf/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/subidas/pdf/");
    }
}
