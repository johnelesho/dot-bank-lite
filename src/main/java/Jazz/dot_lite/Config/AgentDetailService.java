package Jazz.dot_lite.Config;

import Jazz.dot_lite.Agent.Models.Agent;
import Jazz.dot_lite.Agent.Repository.AgentRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Data
public class AgentDetailService implements UserDetailsService {

    @Autowired
    private AgentRepository agentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Agent agent = agentRepository.findByUsername(username);

        Set<GrantedAuthority> authorities = agent.getRoles().stream()
                .map((roles) -> new SimpleGrantedAuthority("ROLE_" + roles.getName()))
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
                username,
                agent.getPassword(),
                authorities
        );

    }
}
