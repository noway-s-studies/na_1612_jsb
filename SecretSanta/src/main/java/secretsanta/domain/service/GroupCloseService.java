package hu.netacademia.secretsanta.domain.service;

import hu.netacademia.secretsanta.domain.entity.GroupData;
import hu.netacademia.secretsanta.domain.entity.Member;
import java.util.List;

/**
 *
 * @author java
 */
public interface GroupCloseService {
    
    void closeGroup(GroupData group, List<Member> members);
    
    void sendNotification(Long id, String email);
    
}
