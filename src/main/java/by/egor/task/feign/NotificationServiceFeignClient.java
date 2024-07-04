package by.egor.task.feign;

import by.egor.task.dto.SetStatusDto;
import by.egor.task.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "registration", url = "http://193.19.100.32:7000/", configuration = FeignConfig.class)
public interface NotificationServiceFeignClient {
    @GetMapping("/api/get-roles")
    String getRoles();
    @PostMapping("/api/sign-up")
    String signUp(@RequestBody UserDto userDto);
    @GetMapping("/api/get-code")
    String getCode(@RequestParam String email);
    @PostMapping("/api/set-status")
    String setStatus(@RequestBody SetStatusDto setStatusDto);
}
