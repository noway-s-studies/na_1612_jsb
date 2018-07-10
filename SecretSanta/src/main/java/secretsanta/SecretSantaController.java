package secretsanta;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SecretSantaController {

    private String message = "Kezdetek!";

    @RequestMapping(
            value = "/",
            method = RequestMethod.GET
    )
    public String welcome(Map<String, Object> model) {
        model.put("message", this.message);
        return "createGroup";
    }

}
