package tn.luceor.demo99.JWT;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tn.luceor.demo99.repositories.IUserRepository;

import java.util.ArrayList;
import java.util.Objects;

@Slf4j
@Service
public class CustomerUsersDetailsServices implements UserDetailsService {
    @Autowired
    IUserRepository userRepository ;
    private tn.luceor.demo99.entities.User user ;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Inside loadUserByUsername {}",username);
        user = userRepository.findByEmailID(username);
        if(!Objects.isNull(user))
            return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),new ArrayList<>());
        else
            throw new UsernameNotFoundException("User not Found. ");
    }
    public tn.luceor.demo99.entities.User getUserDetails() {
        return user;
    }




}

