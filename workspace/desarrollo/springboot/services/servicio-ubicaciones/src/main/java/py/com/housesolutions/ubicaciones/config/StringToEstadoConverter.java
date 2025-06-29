/*
package py.com.housesolutions.ubicaciones.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import py.com.housesolutions.ubicaciones.model.Estado;

@Component
public class StringToEstadoConverter implements Converter<String, Estado> {
    @Override
    public Estado convert(String source) {
        try {
            return Estado.valueOf(source.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado inválido: " + source);
        }
    }
}
*/