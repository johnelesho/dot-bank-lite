package Jazz.dot_lite.Agent;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/agents")
public class AgentController {

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String getAllAgents(){
        return "You Received All Agents List";
    }

    @PostMapping()
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String saveAgents(){
        return "You saved a Agent";
    }

    @PutMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public String updateAgents(){
        return "You updated a Agents";
    }
}
