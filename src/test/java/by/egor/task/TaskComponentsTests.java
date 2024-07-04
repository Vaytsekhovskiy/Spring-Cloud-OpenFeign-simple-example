package by.egor.task;

import by.egor.task.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static by.egor.task.TaskApplicationTests.mailIndex;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class TaskComponentsTests {
    @Autowired
    private TaskService taskService;
    private static final String testEmail = "et-vajtsehovskij-" + mailIndex++ + "@example.ru";

    @Test
    void get_roles_should_return_roles_list_that_has_five_roles() {
        List<String> roles = taskService.getRoles();
        assertThat(roles).isNotNull().hasSize(5);
    }
    private UserDto buildCorrectUser(){
        return UserDto.builder()
                .lastName("lastName")
                .firstName("name")
                .email(testEmail)
                .role("role")
                .build();
    }
    @Test
    void sign_up_should_return_confirmation_line() {

        String resultString = taskService.signUp(buildCorrectUser());
        assertThat(resultString)
                .containsSubsequence("Данные внесены");
    }
    @Test
    void sign_up_should_return_lack_of_data_error() {
        UserDto userDto = UserDto.builder()
                .lastName("lastName")
                .role("role")
                .build();
        assertThatThrownBy(() -> taskService.signUp(userDto))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }
    @Test
    void get_code_should_return_code() {
        String code = taskService.getCode(testEmail);
        assertThat(code).isNotBlank().doesNotContain("\"");
    }
    @Test
    void set_status_should_return_confirmation_line() {
        UserDto userDto = buildCorrectUser();
        taskService.signUp(userDto);
        String email = userDto.email();
        String code = taskService.getCode(email);
        String encodedToken = taskService.encodeToken(email, code);
        String info = taskService.setStatus(encodedToken);
        assertThat(info)
                .isNotBlank()
                .satisfiesAnyOf(
                        s -> assertThat(s).containsSubsequence("Статус increased зафиксирован. Задание выполнено"),
                        s -> assertThat(s).containsSubsequence("Статус increased зафиксирован. Задание выполнено")
                );
    }
}