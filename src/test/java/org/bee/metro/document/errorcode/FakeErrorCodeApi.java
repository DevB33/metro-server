package org.bee.metro.document.errorcode;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FakeErrorCodeApi {

    @GetMapping("/test/error-code")
    public void testErrorCodes() {
    }
}
