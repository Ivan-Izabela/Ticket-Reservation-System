package ft.services.rest;

import ft.domain.Cursa;
import ft.persistance.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/ft/curse")
public class CursaController {
    private static final String template = "Hello, %s!";


    @Autowired
    private CursaRepository cursaRepository;

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return String.format(template, name);
    }

    @RequestMapping( method= RequestMethod.GET)
    public Cursa[] getAll(){

        Cursa[] curse= new Cursa[StreamSupport.stream(cursaRepository.findAll().spliterator(),false).collect(Collectors.toList()).size()];
        StreamSupport.stream(cursaRepository.findAll().spliterator(),false).collect(Collectors.toList()).toArray(curse);

        return curse;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable String id){

        Cursa cursa=cursaRepository.findByID(Integer.parseInt(id));
        if (cursa==null)
            return new ResponseEntity<String>("Cursa not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Cursa>(cursa, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Cursa create(@RequestBody Cursa cursa){
        cursaRepository.save(cursa);
        return cursa;

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Cursa update(@RequestBody Cursa cursa) {
        System.out.println("Updating cursa ...");
        cursaRepository.update(cursa.getId(),cursa);
        return cursa;

    }

    @RequestMapping("/{cursa}/destinatie")
    public String destinatie(@PathVariable String cursa){
        Cursa result=cursaRepository.findByDestinatie(cursa).get(0);
        System.out.println("Result ..."+result);

        return result.getDestinatie();
    }

    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Integer id){
        System.out.println("Deleting cursa ... "+id);
        try {
            cursaRepository.delete(id);
            return new ResponseEntity<Cursa>(HttpStatus.OK);
        }catch (RepositoryException ex){
            System.out.println("Ctrl Delete user exception");
            return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }



    @ExceptionHandler(RepositoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String cursaError(RepositoryException e) {
        return e.getMessage();
    }
}
