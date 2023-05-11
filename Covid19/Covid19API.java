import java.time.LocalDate;
import java.util.ArrayList;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Covid19API {
	@Autowired
    private CovidDB covidDB;

    @PostMapping("/members")
    public void addMember(@RequestBody Member member) {
        covidDB.addMember(member);
    }
    
    @PostMapping("/members/{id}/vaccines")
    public void addVaccine(
            @PathVariable String id,
            @RequestParam int vaccineNum,
            @RequestParam LocalDate vaccineDate,
            @RequestParam String manufacturer
    ) {
        covidDB.addvaccine(id, vaccineNum, vaccineDate, manufacturer);
    }

    @PostMapping("/members/{id}/recovery")
    public void addRecovery(
            @PathVariable String id,
            @RequestParam LocalDate positiveTestDate,
            @RequestParam LocalDate recoveryDate
    ) {
        covidDB.addRecovery(id, positiveTestDate, recoveryDate);
    }

    @GetMapping("/members/{id}")
    public Member getMember(@PathVariable String id) {
        return covidDB.getMember(id);
    }

    @GetMapping("/members")
    public ArrayList<Member> getAllMembers() {
        return covidDB.getAllMembers();
    }
    
    @GetMapping("/active-cases")
    public ArrayList<Integer> getActiveCasesPerDayLastMonth() {
        return covidDB.getActiveCasesPerDayLastMonth();
    }

    @GetMapping("/unvaccinated-count")
    public long getUnvaccinatedMemberCount() {
        return covidDB.getUnvaccinatedMemberCount();
    }

    @PreDestroy
    public void cleanup() {
        covidDB.finish();
    }


}





