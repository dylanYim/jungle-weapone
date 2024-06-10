package jungle.dylan.api.config.filter.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jungle.dylan.api.dto.common.ErrorResponse;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class SecurityErrorResponse extends ErrorResponse {
    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public SecurityErrorResponse(String code, String message, List<ValidationError> errors) {
        super(code, message, errors);
    }

    public String convertToJson() throws JsonProcessingException {
        return objectMapper.writeValueAsString(this);
    }

    public static SecurityErrorResponse of(String code, String message) {
        return new SecurityErrorResponse(code, message, null);
    }
}
