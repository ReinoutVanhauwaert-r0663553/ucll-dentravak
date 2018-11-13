package be.ucll.da.Implementation;

import be.ucll.da.Model.Sandwich;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.management.Attribute;
import javax.persistence.AttributeConverter;
import java.io.IOException;

public class SandwichConverter implements AttributeConverter<Sandwich,String>{
    private final static ObjectMapper om = new ObjectMapper();
    @Override
    public String convertToDatabaseColumn(Sandwich sandwhichtobeconvertedtojson) {
        try{
            return om.writeValueAsString(sandwhichtobeconvertedtojson);
        }catch (JsonProcessingException jpe){
            throw new RuntimeException(jpe.getMessage());
        }
    }

    @Override
    public Sandwich convertToEntityAttribute(String sandwhichjson) {
        try{
            return om.readValue(sandwhichjson, Sandwich.class);
        }catch (IOException ioe){
            throw new RuntimeException(ioe.getMessage());
        }
    }
}
