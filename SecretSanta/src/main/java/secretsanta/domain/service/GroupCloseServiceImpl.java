package secretsanta.domain.service;

import secretsanta.domain.entity.GroupData;
import secretsanta.domain.entity.GroupState;
import secretsanta.domain.entity.Member;
import secretsanta.domain.repository.GroupDataRepository;
import secretsanta.domain.repository.MemberRepository;
import java.util.List;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

/**
 * Spring Been
 */
@Service
/**
 * Minden public függvénye egy darab adatbázis tranzakció lesz
 */
@Transactional
public class GroupCloseServiceImpl implements GroupCloseService {

    private GroupDataRepository groupDataRepo;
    private MemberRepository memberRepo;
    private JavaMailSender mailSender;

    @Autowired
    public GroupCloseServiceImpl(
            GroupDataRepository groupDataRepo,
            MemberRepository memberRepo,
            JavaMailSender mailSender) {
        this.groupDataRepo = groupDataRepo;
        this.memberRepo = memberRepo;
        this.mailSender = mailSender;
    }

    @Override
    public void closeGroup(GroupData group, List<Member> members) {
        group.setState(GroupState.LOT_FINISHED);
        group.setMembers(members);
        groupDataRepo.save(group);
        for (Member m : members) {
            sendEmail(m);
        }
    }

    @Override
    public void sendNotification(Long id, String email) {
        Member member = memberRepo.findByGroupIdAndEmail(id, email);
        if (member == null || member.getPicked() == null) {
            return;
        }
        sendEmail(member);
    }

    private void sendEmail(Member m) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mm) throws Exception {
                mm.setRecipient(Message.RecipientType.TO,
                        new InternetAddress(m.getEmail()));
                mm.setFrom(new InternetAddress("secretsanta@noway.hu"));
                mm.setSubject("SecretSanta game notification");
                mm.setText(constructEmail(m));
            }
        };
        try {
            this.mailSender.send(preparator);
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private String constructEmail(Member member) {
        StringBuilder sb = new StringBuilder();
        sb.append("Dear ");
        sb.append(member.getName());
        sb.append("\n\n");
        sb.append("You picked ");
        sb.append(member.getPicked().getName());
        sb.append(" in ");
        sb.append(member.getGroup().getName());
        sb.append(" group's SecretSanta game");
        return sb.toString();
    }
}
