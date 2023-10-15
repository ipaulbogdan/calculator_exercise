package digital.metro.pricing.calculator.it;

import java.nio.file.Path;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.shaded.org.awaitility.Awaitility;
import org.testcontainers.utility.MountableFile;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
abstract class IntegrationTestBase {

    public static final String CALCULATOR = "calculator";
    public static final String IT_PASSWORD = "it-password";
    public static final String IT_USER = "it-user";

    @LocalServerPort
    private int port;

    private static PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:16.0")
                    .withDatabaseName(CALCULATOR)
                    .withPassword(IT_PASSWORD)
                    .withUsername(IT_USER)
                    .withCopyFileToContainer(MountableFile.forHostPath(Path.of("dev/create-tables.sql")),
                            "/docker-entrypoint-initdb.d/")
                    .withCopyFileToContainer(MountableFile.forHostPath(Path.of("dev/data-provisioning.sql")),
                            "/docker-entrypoint-initdb.d/");

    @DynamicPropertySource
    static void bootUp(final DynamicPropertyRegistry registry) {
        postgreSQLContainer.start();


        //TO FIX HikariPool initialization
        Awaitility.await()
                .pollDelay(Duration.ofSeconds(2))
                .until(() -> true);

        registry.add("spring.datasource.url", () -> postgreSQLContainer.getJdbcUrl());
        registry.add("spring.datasource.username", () -> postgreSQLContainer.getUsername());
        registry.add("spring.datasource.password", () -> postgreSQLContainer.getPassword());
    }

    String getServerAddress(String url) {
        return UriComponentsBuilder.fromHttpUrl("http://localhost:" + port)
                .path(url)
                .build().toString();
    }

}
