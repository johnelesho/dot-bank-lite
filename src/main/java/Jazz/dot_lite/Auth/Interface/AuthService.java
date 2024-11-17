package Jazz.dot_lite.Auth.Interface;

import Jazz.dot_lite.Auth.Dto.LoginDto;
import com.nimbusds.jose.JOSEException;

public interface AuthService {
    String login(LoginDto loginDto) throws JOSEException;
}
