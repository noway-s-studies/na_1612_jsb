package santabackend.backend;

import santabackend.backend.domain.entity.Child;
import santabackend.backend.domain.repository.ChildRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ApiController {
    private ChildRepository childRepo;
    @Autowired
    public ApiController(ChildRepository childRepo) {
        this.childRepo = childRepo;
    }
    @RequestMapping(
            value = "/children",
            method = RequestMethod.GET
    )
    public List<Child> getAllChildren() {
        return childRepo.findAll();
    }
    @RequestMapping(
            value = "/children/{id}",
            method = RequestMethod.GET
    )
    public Child getChild(@PathVariable("id") Integer id) {
        Child child = childRepo.findOne(id);
        if (child == null) {
            throw new EntityNotFoundException(Child.class, id);
        }
        return child;
    }
    @RequestMapping(
            value = "/cities/{city}/children",
            method = RequestMethod.GET
    )
    public List<Child> getChildByCity(@PathVariable("city") String city) {
        return childRepo.findByCity(city);
    }
    @RequestMapping(
            value = "/children/{id}",
            method = RequestMethod.PUT
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateChild(@PathVariable("id") Integer id, @RequestBody Child child) {
        Child childFromDb = childRepo.findOne(id);
        if (childFromDb == null) {
            throw new EntityNotFoundException(Child.class, id);
        }
        if (!childFromDb.getId().equals(child.getId())) {
            throw new BadRequestException("Request and data ID mismatch");
        }
        childRepo.save(child);
    }
}
