package secretsanta;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import secretsanta.domain.entity.GroupData;
import secretsanta.domain.entity.GroupState;
import secretsanta.domain.repository.GroupDataRepository;

@Controller
public class SecretSantaController {

    private GroupDataRepository groupDataRepository;
    private static final String MODEL_KEY_ERRORMESSAGE = "errorMessage";
    private static final String MODEL_KEY_INFOMESSAGE = "infoMessage";


    /**
     * A Spring amikor létrehozza ezt a controllert akkor az Autowired helyére
     * beilleszti az általa menedzselt GroupDataRepo példényt
     *
     * @param groupDataRepository
     */
    @Autowired
    public SecretSantaController(GroupDataRepository groupDataRepository) {
        this.groupDataRepository = groupDataRepository;
    }

    @RequestMapping(
            value = "/",
            method = RequestMethod.GET
    )
    public String welcome(Map<String, Object> model) {
        return "createGroup";
    }

    @RequestMapping(
            value = "/groups",
            method = RequestMethod.GET
    )
    public String redirectToHome(){
        return "redirect:/";
    }

    @RequestMapping(
            value = "/groups",
            method = RequestMethod.POST
    )
    public String postCreateGroup(@ModelAttribute GroupData groupData, Map<String, Object> model){
        GroupData groupByName = groupDataRepository.findByName(groupData.getName());
        if (groupByName != null){
            setErrorMassage(model, "Group with the same name exists");
            return "createGroup";
        }
        groupData.setId(null);
        groupData.setState(GroupState.CREATED);
        try {
            groupDataRepository.save(groupData);
        } catch (Exception ex){
            Logger.getLogger(SecretSantaController.class.getName()).log(Level.SEVERE, null, ex);
            setErrorMassage(model, "Internal error, please check the logs");
            return  "createGroup";
        }
        return "redirect:/groups/"+groupData.getId().toString();
    }

    private void setErrorMassage(Map<String,Object> model, String errorMessage) {
        model.put(MODEL_KEY_ERRORMESSAGE, errorMessage);
    }

    private void setInfoMassage(Map<String,Object> model, String infoMessage) {
        model.put(MODEL_KEY_INFOMESSAGE, infoMessage);
    }
}
