package by.egor.task;

import by.egor.task.dto.RolesResponse;
import by.egor.task.dto.SetStatusDto;
import by.egor.task.dto.UserDto;
import by.egor.task.feign.NotificationServiceFeignClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
@EnableFeignClients
public class TaskService {
    private final NotificationServiceFeignClient feignClient;
    private final ObjectMapper objectMapper;
    private List<String> roles;
    @SneakyThrows
    public String registrateUser(UserDto userDto){
        String email = userDto.email();
        try {
            signUp(userDto);
        } catch (IllegalArgumentException e) {
            return "Укажите все необходимые поля для регистрации";
        }
        String code = getCode(email);
        String encodedToken = encodeToken(email, code);
        return setStatus(encodedToken);
    }
    @SneakyThrows
    public List<String> getRoles() {
        roles = objectMapper.readValue(feignClient.getRoles(), RolesResponse.class).roles();
        return roles;
    }
    public String signUp(UserDto userDto){
        if (userDto.lastName() == null || userDto.firstName() == null ||
                userDto.email() == null || userDto.role() == null)
            throw new IllegalArgumentException("One or more fields are null");
        return feignClient.signUp(userDto);
    }
    @SneakyThrows
    public String getCode(String email){
        String jsonCode = feignClient.getCode(email);
        return objectMapper.readValue(jsonCode, String.class);
    }
    public String encodeToken(String email, String code) {
        String token = email + ":" + code;
        return Base64.getEncoder().encodeToString(token.getBytes());
    }
    public String setStatus(String encodedToken){
        SetStatusDto setStatusDto = new SetStatusDto(encodedToken, "increased");
        return feignClient.setStatus(setStatusDto);
    }
}
