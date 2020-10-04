package sk.pga.animalshop.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeDeserializer extends StdDeserializer<LocalDateTime> {
	
	private static final long serialVersionUID = -406711313651033262L;
	
	public LocalDateTimeDeserializer() {
		super(LocalDateTime.class);
	}
	
	@Override
	public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
		return LocalDateTime.parse(parser.readValueAs(String.class), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}
}
