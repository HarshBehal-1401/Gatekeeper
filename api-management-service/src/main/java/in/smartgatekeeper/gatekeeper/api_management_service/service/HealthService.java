package in.smartgatekeeper.gatekeeper.api_management_service.service;

import in.smartgatekeeper.gatekeeper.api_management_service.dto.responsedto.HealthResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

@Service
@AllArgsConstructor
public class HealthService {
    private JdbcTemplate jdbcTemplate;

    public String gethealth() {
        String databasestaus="DOWN";
        try{
            Integer result=jdbcTemplate.queryForObject("Select 1",Integer.class);
            if(Integer.valueOf(result).equals(1)){
                databasestaus="UP";
            }

        }
        catch(Exception e){
            System.out.println("MySql Health Check Failed"+e.getMessage());
        }
        return databasestaus;
    }
}
