package secretsanta.domain.service;

import secretsanta.domain.entity.GroupData;
import secretsanta.domain.entity.Member;
import java.util.List;

public interface GroupCloseService {
    void closeGroup(GroupData group, List<Member> members);
    void sendNotification(Long id, String email);
}