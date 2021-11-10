package ro.micsa.scores.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hostname")
@Slf4j
public class HostnameResource {

    @Value("${HOSTNAME:hostname}")
    private String hostname;

    @GetMapping
    @ResponseBody
    public String hostname() {
        log.debug("Get hostname");

        return hostname;
    }
}
