package Jazz.dot_lite.Agent.Repository;

import Jazz.dot_lite.Agent.Models.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<Agent,Long> {
    Agent findByUsername(String username);
}

