package secretsanta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import secretsanta.domain.entity.GroupData;
import secretsanta.domain.entity.GroupState;
import secretsanta.domain.entity.Member;
import secretsanta.domain.repository.GroupDataRepository;
import secretsanta.domain.repository.MemberRepository;
import secretsanta.domain.service.GroupCloseService;

@Controller
public class SecretSantaController {

    private GroupDataRepository groupDataRepo;
    private MemberRepository memberRepo;
    private GroupCloseService groupCloseService;
    private static final String MODEL_KEY_ERRORMESSAGE = "errorMessage";
    private static final String MODEL_KEY_INFOMESSAGE = "infoMessage";
    private static final String MODEL_KEY_GROUP = "group";

    /**
     * A Spring amikor létrehozza ezt a controllert akkor az Autowired helyére
     * beilleszti az általa menedzselt GroupDataRepo példényt
     */
    @Autowired
    public SecretSantaController(
            GroupDataRepository groupDataRepo,
            MemberRepository memberRepo,
            GroupCloseService groupCloseService
    ) {
        this.groupDataRepo = groupDataRepo;
        this.memberRepo = memberRepo;
        this.groupCloseService = groupCloseService;
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
    public String postCreateGroup(@ModelAttribute GroupData groupData,
                                  Map<String, Object> model){
        GroupData groupByName = groupDataRepo.findByName(groupData.getName());
        if (groupByName != null){
            setErrorMessage(model, "Group with the same name exists");
            return "createGroup";
        }
        groupData.setId(null);
        groupData.setState(GroupState.CREATED);
        try {
            groupDataRepo.save(groupData);
        } catch (Exception ex){
            Logger.getLogger(SecretSantaController.class.getName()).log(Level.SEVERE,
                    null,
                    ex);
            setErrorMessage(model, "Internal error, please check the logs");
            return  "createGroup";
        }
        return "redirect:/groups/"+groupData.getId().toString();
    }

    @RequestMapping(
            value = "/groups/{id}",
            method = RequestMethod.GET
    )
    public String showGroup(@PathVariable("id") Long id, Map<String, Object> model){
        GroupData groupData = groupDataRepo.findOne(id);
        if (groupData == null) {
            setErrorMessage(model, "There is no group whit this ID");
            return "createGroup";
        }
        model.put(MODEL_KEY_GROUP, groupData);
        return "showGroup";
    }

    private void setErrorMessage(Map<String,Object> model, String errorMessage) {
        model.put(MODEL_KEY_ERRORMESSAGE, errorMessage);
    }

    private void setInfoMessage(Map<String,Object> model, String infoMessage) {
        model.put(MODEL_KEY_INFOMESSAGE, infoMessage);
    }

    @RequestMapping(
            value = "/groups/{id}/members",
            method = RequestMethod.POST
    )
    public String addMemberToGroup(@ModelAttribute Member member,
                                   @PathVariable("id") Long id,
                                   Map<String, Object> model) {
        GroupData group = groupDataRepo.findOne(id);
        if (group == null) {
            setErrorMessage(model, "There is no group with this ID");
            return "createGroup";
        }
        model.put(MODEL_KEY_GROUP, group);

        if (group.getState() != GroupState.CREATED) {
            setErrorMessage(model, "Group is closed, you can't add more members");
            return "showGroup";
        }
        member.setId(null);
        member.setGroup(group);
        try {
            memberRepo.save(member);
        } catch (Exception ex) {
            setErrorMessage(model, "Invalid member data");
            return "showGroup";
        }
        return "redirect:/groups/" + id.toString();
    }

    @RequestMapping(
            value = "/groups/{id}/finish",
            method = RequestMethod.POST
    )
    public String closeGroup(@PathVariable("id") Long id, Map<String, Object> model){
        GroupData group = groupDataRepo.findOne(id);
        if (group == null) {
            setErrorMessage(model, "There is no group with this ID");
            return "createGroup";
        }
        model.put(MODEL_KEY_GROUP, group);
        if (group.getState() != GroupState.CREATED) {
            setErrorMessage(model, "Group is already closed");
            return "showGroup";
        }
        if (group.getMembers().size() < 2) {
            setErrorMessage(model, "Group has too few members");
            return "showGroup";
        }
        List<Member> members = pickMembers(group.getMembers());
//        System.out.println("------------");
//        for (Member m : members) {
//            System.out.println(m.getName() + " picked " + m.getPicked().getName());
//        }
//        System.out.println("------------");
        groupCloseService.closeGroup(group, members);
        setInfoMessage(model, "Group is closed, notification email are sent");
        return "showGroup";
    }

    @RequestMapping(
            value = "/groups/{id}/check",
            method = RequestMethod.POST
    )
    public String queryPick(@PathVariable("id") Long id,
                            @ModelAttribute Member member,
                            Map<String, Object> model) {
        GroupData group = groupDataRepo.findOne(id);
        if (group == null) {
            return redirectToHome();
        }
        if (group.getState() != GroupState.LOT_FINISHED) {
            return redirectToHome();
        }
        groupCloseService.sendNotification(id, member.getEmail());
        setInfoMessage(model, "Notification sent");
        model.put(MODEL_KEY_GROUP, group);
        return "showGroup";
    }

    private List<Member> pickMembers(List<Member> members) {
        while (true) {
            List<Member> alreadyPickedMembers = new ArrayList<>();
            try {
                for (Member member : members) {
                    List<Member> chooseFrom = members.stream().filter((Member m) -> {
                        return !m.equals(member) && !alreadyPickedMembers.contains(m);
                    }).collect(Collectors.toList());
                    if (chooseFrom.size() == 0) {
                        throw new InvalidPickException();
                    }
                    Random random = new Random();
                    Member chosen = chooseFrom.get(random.nextInt(chooseFrom.size()));
                    alreadyPickedMembers.add(chosen);
                    member.setPicked(chosen);
                }
                return members;
            } catch (InvalidPickException ex) {
                continue;
            }
        }
    }
}
