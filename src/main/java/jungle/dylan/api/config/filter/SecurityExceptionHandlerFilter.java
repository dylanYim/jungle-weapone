package jungle.dylan.api.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jungle.dylan.api.config.filter.response.SecurityErrorResponse;
import jungle.dylan.api.error.errorcode.ErrorCode;
import jungle.dylan.api.error.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class SecurityExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ApiException ex) {
            setErrorResponse(response, ex.getErrorCode());
        }
    }

    public void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {

        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType("application/json; charset=UTF-8");

        response.getWriter()
                .write(
                        SecurityErrorResponse
                                .of(errorCode.name(), errorCode.getMessage())
                                .convertToJson()
                );
    }
}
