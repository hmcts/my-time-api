package uk.gov.hmcts.dts.controllers.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith({SpringExtension.class})
class UserControllerTest extends FunctionalTestBase {

    @Test
    void shouldReturnUserObject() {
        final var response = doGetRequest("/");
        assertThat(response.statusCode()).isEqualTo(OK.value());
    }
}
