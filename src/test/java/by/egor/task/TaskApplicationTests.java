package by.egor.task;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import by.egor.task.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class TaskApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    static int mailIndex;
    @Test
    @SneakyThrows
    void should_add_completed_message(){
        String json = new ObjectMapper().writeValueAsString(
                UserDto.builder()
                        .lastName("lastName")
                        .firstName("name")
                        .email("et-vajtsehovskij-" + mailIndex++ + "@example.ru")
                        .role("role")
                        .build());
        this.mockMvc.perform(post("/api/register")
                        .contentType("application/json")
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(anyOf(
                        containsString("Статус increased зафиксирован. Задание выполнено"),
                        containsString("Установлен статус increased")
                )));
    }
    @Test
    @SneakyThrows
    void should_return_lack_of_data_error(){
        String json = new ObjectMapper().writeValueAsString(
                UserDto.builder()
                        .lastName("lastName")
                        .email("et-vajtsehovskij-" + mailIndex++ + "@example.ru")
                        .role("role")
                        .build());
        this.mockMvc.perform(post("/api/register")
                        .contentType("application/json")
                        .content(json))
                .andDo(print())
                .andExpect(content().string(
                        containsString("Укажите все необходимые поля для регистрации")
                ));
    }

}
