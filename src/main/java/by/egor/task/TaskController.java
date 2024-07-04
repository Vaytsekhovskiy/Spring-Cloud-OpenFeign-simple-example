package by.egor.task;

import by.egor.task.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    @PostMapping("/api/register")
    @SneakyThrows
    public String registrateUser(@RequestBody UserDto userDto){
        return taskService.registrateUser(userDto);
    }
}
