package ru.cft.docs.keeper.service.authorization.core.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.cft.docs.keeper.service.authorization.configuration.AuthorizationServiceProperties;
import ru.cft.docs.keeper.service.authorization.core.context.ExecutionContext;
import ru.cft.docs.keeper.service.authorization.core.exception.ErrorCode;
import ru.cft.docs.keeper.service.authorization.core.exception.ServiceException;
import ru.cft.docs.keeper.service.authorization.core.model.UserAuthentication;
import ru.cft.docs.keeper.service.authorization.core.model.UserAuthenticationRequest;
import ru.cft.docs.keeper.service.authorization.core.repository.TokenRepository;
import ru.cft.docs.keeper.service.authorization.core.repository.UserRepository;
import ru.cft.docs.keeper.service.authorization.core.repository.entity.TokenEntity;
import ru.cft.docs.keeper.service.authorization.core.repository.entity.UserEntity;
import ru.cft.docs.keeper.service.authorization.core.service.UserAuthenticationService;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

import static ru.cft.docs.keeper.service.authorization.core.validation.AuthenticationValidator.*;

@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    static ApplicationContext context;

    @Autowired
    public void StaticContextAccessor(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    private static final String ALGORITHM_PBKDF2 = "PBKDF2WithHmacSHA1";

    private static final ThreadLocal<SecureRandom> SECURE_RANDOM = new ThreadLocal<>();

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    private final Duration userTokenLifetime;

    public UserAuthenticationServiceImpl(
            UserRepository userRepository,
            TokenRepository tokenRepository,
            AuthorizationServiceProperties authorizationServiceProperties
    ) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.userTokenLifetime = authorizationServiceProperties.getUserTokenLifetime();
    }


    @Override
    public UserAuthentication authentication(UserAuthenticationRequest userAuthenticationRequest) {
        checkEmail("email", userAuthenticationRequest.getLogin());
        checkPassword("password", userAuthenticationRequest.getPassword());
        checkDeviceId("deviceId", userAuthenticationRequest.getDeviceId());

        UserEntity user = userRepository.findByLogin(userAuthenticationRequest.getLogin());
        if (user == null) {
            throw new ServiceException(ErrorCode.INCORRECT_PARAMETER_VALUE, "Username or password is incorrect");
        }

        String hashPassword = hashPassword(userAuthenticationRequest.getPassword(), user.getSalt());
        if (hashPassword.equals(user.getHashPassword())) {
            return authorization(user.getId(), userAuthenticationRequest.getDeviceId());
        }

        throw new ServiceException(ErrorCode.INCORRECT_PARAMETER_VALUE, "Username or password is incorrect");
    }

    private String hashPassword(String password, String salt) {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), Base64.getDecoder().decode(salt), 65536, 512);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM_PBKDF2);
            return Base64.getEncoder().encodeToString(factory.generateSecret(spec).getEncoded());
        } catch (Exception e) {

            throw new ServiceException(ErrorCode.INTERNAL_SERVER_ERROR, "Password hashing error", e);
        }
    }

    private UserAuthentication authorization(Long userId, String deviceId) {
        TokenEntity token = createToken(userId, deviceId);
        return UserAuthentication.builder()
                .token(token.getToken())
                .expirationDate(token.getExpirationDate())
                .build();
    }

    private TokenEntity createToken(Long userId, String deviceId) {
        Date expirationDate = Date.from(Instant.now().plus(userTokenLifetime));
        return tokenRepository.save(TokenEntity.builder()
                .token(generateToken())
                .deviceId(deviceId)
                .expirationDate(expirationDate)
                .userId(userId)
                .build());
    }

    private String generateToken() {
        Base64.Encoder base64Encoder = Base64.getUrlEncoder();
        byte[] randomBytes = new byte[24];
        SECURE_RANDOM.set(new SecureRandom());
        SECURE_RANDOM.get().nextBytes(randomBytes);
        SECURE_RANDOM.remove();
        return base64Encoder.encodeToString(randomBytes);
    }

    public static Long verifyToken(String token) {
        if (!StringUtils.hasText(token)) {
            throw new ServiceException(ErrorCode.INTERNAL_SERVER_ERROR, "Token not found");
        }
        TokenEntity tokenEntity = (context.getBean(TokenRepository.class)).findByToken(token);
        if (tokenEntity == null) {
            throw new ServiceException(ErrorCode.TOKEN_INVALID, "Token is invalid");
        }
        if (tokenEntity.getExpirationDate().getTime() < Date.from(Instant.now()).getTime()) {
            throw new ServiceException(ErrorCode.SESSION_EXPIRED, "Token is expired");
        }
        return tokenEntity.getUserId();
    }

    public long getUserId() {
        Long token = ExecutionContext.get().getUserId();
        if (token == null) {
            throw new ServiceException(ErrorCode.TOKEN_INVALID, "Incorrect token");
        }
        return token;
    }
}