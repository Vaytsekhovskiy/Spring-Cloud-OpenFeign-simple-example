# Мини-приложение, которое последовательно вызывает все методы предоставленного API для создания новой записи в базе и установки статуса "increased".
## feign folder
Взаимодействие с апи происходит через OpenFeign (см. папку feign). Для конкретного взаимодействия потребовалось создать FeignConfig и настроить параметры взаимодействия с редиректами.
Само взаимодействие с апи происходит через класс NotificationServiceFeignClient. Для взаимодействия со сторонним апи, согласно документации OpenFeign, требуется повторить сигнатуру запроса, что и было сделано в данном классе.
## dto folder
Здесь хранятся все data transfer object, которые нужны для взаимодействия с апи
- RolesResponse нужен для парсинга json`а с ролями, при получении этого json через /api/get-roles. Сам парсинг происходит в TaskService.getRoles()
- SetStatusDto нужен для упаковки в json формат при получении этого json через /api/set-status.
- UserDto используется при получении парсинга json при получении его в мини приложение (taskController) и для /api/sign-in соответсвенно
## TaskController
Эндпоинт (/api/register), на который приходят запросы по регистрации пользователя, внутри вшита логика по вызову всех методов представленного апи согласно заданию
## TaskService
Главная логика приложения, есть главный метод registerUser(UserDto userDto), в котором вызываются все методы апи через openfeign. Также каждый апи метод был логически разбит на один метод с соответсвующими названиями
для удобства тестирования. Также такая идея облегчит дальшейшее масштабирование проекта
## Тестирование
Были написаны интеграционные тесты, находятся в папке src/test. Тесты разделены на два класса
* TaskApplicationTests - класс, в котором идёт взаимодействие с апи непосредственно через TaskController. Для этого мокаются все необходимые mvc компоненты для корректной работы при помощи @AutoConfigureMockMvc.
  Было рассмотренно два случая. Удачный - был введён корректный json со всеми полями. Неудачный - намерено опущено поле name, для получения ошибки
* TaskComponentsTests - рассматриваются все компоненты взаимодействия по отдельности, там уже ничего не мокалось, просто проверялась работостпособность обращений к апи через TaskService. Были рассмотрены как положительные, так и отрицательные сценарии
Для тестов использовал email вида et-vajtsehovskij-№теста@example.ru , чтобы снизить вероятность пересечения по данным с другими участниками. Специально ввёл статическую переменную mailIndex, чтобы можно было проводить тесты
с разными данными.
## Как запустить?
> Внимание! Потребуется 22 версия Java

Необходимо собрать проект через Maven. Для этого, находясь в папке проекта, выполните команду:
./mvnw clean package
После необходимо запустить появившийся jar файл (папка target):
java -jar task-0.0.1-SNAPSHOT.jar
Веб приложение запущено. Приложение запускается на порту 8080 на локальном хосте. Через следующий url:
http://localhost:8080/api/register
Можно отправить post запрос. Для корректного ответа тело запроса должно быть в следующем виде:
{
  "last_name": "Новый",
  "first_name": "Пользователь",
  "email": "new@example.ru",
  "role": "string"
}
