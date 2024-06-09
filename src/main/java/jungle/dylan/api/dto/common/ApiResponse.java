package jungle.dylan.api.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@Builder
public class ApiResponse<T> {

    private HttpStatus status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    private String message;
}
