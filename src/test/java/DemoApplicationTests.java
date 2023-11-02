import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.netology.first.FirstApplication;

@Testcontainers
@SpringBootTest(classes = FirstApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoApplicationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Container
    private final GenericContainer<?> devApp = new GenericContainer<>("devapp").withExposedPorts(8080);
    @Container
    private final GenericContainer<?> prodApp = new GenericContainer<>("prodapp").withExposedPorts(8081);

    private Integer getLocalPort(GenericContainer app, Integer originalPort) {
        return app.getMappedPort(originalPort);
    }

    @Test
    void notFound() {
        Integer portDev = getLocalPort(devApp, 8080);
        Integer portProd = getLocalPort(prodApp, 8081);
        ResponseEntity<String> entityDev = restTemplate.getForEntity("http://localhost:" + portDev, String.class);
        ResponseEntity<String> entityProd = restTemplate.getForEntity("http://localhost:" + portProd, String.class);
        System.out.println(entityDev.getBody());
        Assertions.assertEquals(entityDev.getStatusCode(), HttpStatus.NOT_FOUND);
        Assertions.assertEquals(entityProd.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void ok() {
        Integer portDev = getLocalPort(devApp, 8080);
        Integer portProd = getLocalPort(prodApp, 8081);
        ResponseEntity<String> entityDev = restTemplate.getForEntity("http://localhost:" + portDev + "/authorize?user=admin&password=admin", String.class);
        ResponseEntity<String> entityProd = restTemplate.getForEntity("http://localhost:" + portProd + "/authorize?user=admin&password=admin", String.class);
        System.out.println(entityDev.getBody());
        Assertions.assertEquals(entityDev.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(entityProd.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void notValid() {
        Integer portDev = getLocalPort(devApp, 8080);
        Integer portProd = getLocalPort(prodApp, 8081);
        ResponseEntity<String> entityDev = restTemplate.getForEntity("http://localhost:" + portDev + "/authorize?user=user&password=password", String.class);
        ResponseEntity<String> entityProd = restTemplate.getForEntity("http://localhost:" + portProd + "/authorize?user=user&password=password", String.class);
        System.out.println(entityDev.getBody());
        Assertions.assertEquals(entityDev.getStatusCode(), HttpStatus.UNAUTHORIZED);
        Assertions.assertEquals(entityProd.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }
}
