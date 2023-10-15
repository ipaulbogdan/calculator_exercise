package digital.metro.pricing.calculator.it;

import static org.assertj.core.api.Assertions.assertThat;

import digital.metro.pricing.calculator.dto.Basket;
import digital.metro.pricing.calculator.dto.BasketCalculationResult;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public class BasketControllerIntegrationTest extends IntegrationTestBase {

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    void givenBlankCustomerName_shouldInvalidateRequest() {
        var response = restTemplate.exchange(this.getServerAddress("/calculate-basket"),
                HttpMethod.POST,
                new HttpEntity<>(new Basket()), BasketCalculationResult.class);

        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.BAD_REQUEST);
    }

}
