package main.java.jukebox.controller;

import main.java.jukebox.models.Jukebox;
import main.java.jukebox.models.Setting;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@RestController
public class JukeboxController {
    /**
     * Controller for GET /jukes request to retrieve all jukeboxes.
     *
     * @return listOfJukeboxes  the list of all jukeboxes.
     */
    @GetMapping(value = "/jukes")
    public List<Jukebox> getAllJukeboxes() {
        RestTemplate restTemplate = new RestTemplate();
        Jukebox[] jukeboxes = restTemplate.getForObject("http://my-json-server.typicode.com/touchtunes/tech-assignment/jukes", Jukebox[].class);
        List<Jukebox> listOfJukeboxes = Arrays.asList(jukeboxes);
        return listOfJukeboxes;
    }

    /**
     * Controller for GET /settings request to retrieve all settings.
     *
     * @return listOfSettings  the list of all settings.
     */
    @GetMapping(value = "/settings")
    public List<LinkedHashMap<String,ArrayList>> getAllSettings() {
        RestTemplate restTemplate = new RestTemplate();
        HashMap<String, ArrayList<LinkedHashMap<String,ArrayList>>> settings = restTemplate.getForObject("http://my-json-server.typicode.com/touchtunes/tech-assignment/settings", HashMap.class);
        List<LinkedHashMap<String,ArrayList>> listOfSettings = new ArrayList(settings.get("settings"));
        return listOfSettings;
    }

    /**
     * Controller for GET /jukeboxes request to retrieve all jukeboxes that support the required settings.
     * The full request should look like GET /jukeboxes?settingId={settingId}.
     *
     * @param settingId  the setting id to look up for the required settings. This parameter is mandatory.
     * @param model      the model name of the jukebox for filtering. Optional.
     * @param offset     the page offset for starting index. Optional.
     * @param limit      the page limit for max number of result. Optional.
     * @return paginatedJukeboxes  the paginated list of all jukeboxes that support the settings of settingId.
     * @throws RuntimeException If settingId is not provided.
     */
    @GetMapping(path= "/jukeboxes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Jukebox> getJukeboxesSupportingSettingId (
            @RequestParam(required = true) String settingId,
            @RequestParam(required = false) String model,
            @RequestParam(required = false, defaultValue = "0") Integer offset,
            @RequestParam(required = false, defaultValue = "1000") Integer limit) {
        if (settingId == null) {
            throw new RuntimeException("Invalid Setting ID.");
        }
        List<Jukebox> paginatedJukeboxes = paginateResult(getJukeboxesSupportingSettings(settingId, model), offset, limit);
        return paginatedJukeboxes;
    }

    /**
     * Handles the display of valid jukeboxes given the pagination configuration.
     *
     * @param jukeboxes  all valid jukeboxes.
     * @param offset     the page offset for starting index.
     * @param limit      the page limit for max number of result.
     * @return paginatedJukeboxes  the paginated list of all valid jukeboxes.
     */
    private List<Jukebox> paginateResult(List<Jukebox> jukeboxes, Integer offset, Integer limit) {
        jukeboxes.sort((A,B) -> A.getId().compareTo(B.getId()));

        List<Jukebox> paginatedJukeboxes = new ArrayList<>();

        for (Integer currSize = 0, i=0; i<jukeboxes.size(); i++) {
            if (i < offset) continue;
            if (currSize < limit) {
                paginatedJukeboxes.add(jukeboxes.get(i));
                currSize++;
            }
        }

        return paginatedJukeboxes;
    }

    /**
     * Filter the jukeboxes that have the same model (if provided) and support all required settings.
     *
     * @param settingId  the setting id to look up for the required settings.
     * @param model      the model name of the jukebox for filtering.
     * @return supportedJukeboxes  the list of all jukeboxes that support the settings of settingId.
     * @throws RuntimeException If no jukebox satisfies all the required settings.
     */
    private List<Jukebox> getJukeboxesSupportingSettings(String settingId, String model) {
        List<Jukebox> supportedJukeboxes = new ArrayList<>();

        List<Jukebox> jukeboxes = getAllJukeboxes();

        Setting setting = getSettingBySettingId(settingId);
        List<String> requiredSettings = setting.getRequires();

        for (Jukebox jukebox : jukeboxes) {
            List<String> components = new ArrayList<>();

            if (model == null || jukebox.getModel().equals(model)) {
                for (Properties componentProp : jukebox.getComponents()) {
                    components.add(componentProp.get("name").toString());
                }

                if (components.containsAll(requiredSettings)) {
                    supportedJukeboxes.add(jukebox);
                }
            }
        }

        if (supportedJukeboxes.isEmpty()) {
            throw new RuntimeException("No jukebox satisfies the required settings.");
        }
        return supportedJukeboxes;
    }

    /**
     * Find the setting that matches the id of the specified settingId.
     *
     * @param settingId  the setting id to look up for.
     * @return foundSetting  the setting with settingId.
     * @throws RuntimeException If settingId is not found.
     */
    private Setting getSettingBySettingId(String settingId) {
        List<LinkedHashMap<String,ArrayList>> allSettings = getAllSettings();
        for (LinkedHashMap<String,ArrayList> setting : allSettings) {
            if (String.valueOf(setting.get("id")).equals(settingId)) {
                Setting foundSetting = new Setting(settingId, setting.get("requires"));
                return foundSetting;
            }
        }

        throw new RuntimeException("Setting ID " + settingId + " is not found");
    }
}