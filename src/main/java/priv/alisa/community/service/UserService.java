package priv.alisa.community.service;

import org.springframework.stereotype.Service;
import priv.alisa.community.model.User;

@Service
public interface UserService {
    void createOrUpdate(User user);
}
