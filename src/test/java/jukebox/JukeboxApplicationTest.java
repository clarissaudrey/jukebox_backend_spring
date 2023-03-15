package test.java.jukebox;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import main.java.jukebox.JukeboxApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(classes= JukeboxApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class JukeboxApplicationTest {
    @Test
    void contextLoads() {}

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    private String baseUrl = "http://localhost:" + port + "/jukeboxes?settingId=";

    @Test
    public void settingIdNotFoundShouldThrowError() throws Exception {
        String url = baseUrl + "515ef38b-0529-418f-a93a-7f2347fc5805random";
        assertThrows(RuntimeException.class, () -> this.restTemplate.getForObject(url,String.class), "Setting ID 515ef38b-0529-418f-a93a-7f2347fc5805random is not found");
    }

    @Test
    public void NoJukeboxSatisfiesRequiredSettingsShouldThrowError() throws Exception {
        String url = baseUrl + "bd9df656-323c-4417-b14b-bd9e9743be23";
        assertThrows(RuntimeException.class, () -> this.restTemplate.getForObject(url,String.class), "No jukebox satisfies the required settings.");
    }

    @Test
    public void validGetRequestWithSettingIdShouldReturnAllValidJukebox() throws Exception {
        String url = "http://localhost:" + port + "/jukeboxes?settingId=515ef38b-0529-418f-a93a-7f2347fc5805";
        String result = """
 [{"id":"5ca94a8a13385f0c82aa9f2e","model":"virtuo","components":[{"name":"money_storage"},{"name":"pcb"},{"name":"touchscreen"}]},{"id":"5ca94a8a1d1bc6d59afb9392","model":"virtuo","components":[{"name":"money_storage"},{"name":"speaker"}]},{"id":"5ca94a8a8b58770bb38055a0","model":"angelina","components":[{"name":"money_storage"},{"name":"pcb"}]},{"id":"5ca94a8aa2330a0762019ac0","model":"angelina","components":[{"name":"money_storage"},{"name":"amplifier"}]},{"id":"5ca94a8acfdeb5e01e5bdbe8","model":"virtuo","components":[{"name":"money_storage"},{"name":"money_pcb"},{"name":"money_storage"},{"name":"camera"},{"name":"money_receiver"}]},{"id":"5ca94a8af0853f96c44fa858","model":"virtuo","components":[{"name":"led_matrix"},{"name":"touchscreen"},{"name":"money_storage"},{"name":"pcb"},{"name":"money_receiver"}]}]""";
        assertThat(this.restTemplate.getForObject(url,String.class)).contains(result);
    }

    @Test
    public void validGetRequestWithPaginationShouldReturnAllValidJukebox() throws Exception {
        String url = "http://localhost:" + port + "/jukeboxes?settingId=515ef38b-0529-418f-a93a-7f2347fc5805&limit=3&offset=4";
        String result = """
 [{"id":"5ca94a8acfdeb5e01e5bdbe8","model":"virtuo","components":[{"name":"money_storage"},{"name":"money_pcb"},{"name":"money_storage"},{"name":"camera"},{"name":"money_receiver"}]},{"id":"5ca94a8af0853f96c44fa858","model":"virtuo","components":[{"name":"led_matrix"},{"name":"touchscreen"},{"name":"money_storage"},{"name":"pcb"},{"name":"money_receiver"}]}]""";
        assertThat(this.restTemplate.getForObject(url,String.class)).contains(result);
    }

    @Test
    public void validGetRequestWithModelFilterShouldReturnAllValidJukebox() throws Exception {
        String url = "http://localhost:" + port + "/jukeboxes?settingId=515ef38b-0529-418f-a93a-7f2347fc5805&model=angelina";
        String result = """
[{"id":"5ca94a8a8b58770bb38055a0","model":"angelina","components":[{"name":"money_storage"},{"name":"pcb"}]},{"id":"5ca94a8aa2330a0762019ac0","model":"angelina","components":[{"name":"money_storage"},{"name":"amplifier"}]}]""";
        assertThat(this.restTemplate.getForObject(url,String.class)).contains(result);
    }
}
