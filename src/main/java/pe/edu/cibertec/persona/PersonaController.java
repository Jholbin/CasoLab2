package pe.edu.cibertec.persona;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/Persona")
public class PersonaController {
    
    private PersonaRepository personaRepository;

    PersonaController(PersonaRepository personaRepository){
        this.personaRepository = personaRepository;
    }

    @GetMapping
    public String list(Model model){
        List<Persona> personas = personaRepository.findAll();
        model.addAttribute( "personas", personas);
        return "Persona/listar";

    }
    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("persona", new Persona());
        return "Persona/create";
    }

    @PostMapping
    public String store(Persona persona){
        personaRepository.save(persona);
        return "redirect:/Persona";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {

        Optional<Persona> personaOptional = personaRepository.findById(id);
        if(personaOptional.isEmpty()) {
            return "404";
        }

        model.addAttribute("persona", personaOptional.get());
        return "Persona/detail";
    }

    @GetMapping("/{id}/edit")
    public String showFormEdit(@PathVariable Integer id, Model model) {
        Optional<Persona> personaOptional = personaRepository.findById(id);
        if(personaOptional.isEmpty()) {
            return "404";
        }

        model.addAttribute("persona", personaOptional.get());
        return "Persona/edit";
    }

    @PostMapping("/{id}")
    public String edit(@PathVariable Integer id, Persona dataFormulario) {
        Optional<Persona> personaOptional = personaRepository.findById(id);
        if(personaOptional.isEmpty()) {
            return "404";
        }

        Persona persona = personaOptional.get();
        persona.setNombre(dataFormulario.getNombre());
        persona.setApellido(dataFormulario.getApellido());
        persona.setDni(dataFormulario.getDni());
        persona.setEdad(dataFormulario.getEdad());
        personaRepository.save(persona); // UPDATE 

        return "redirect:/Persona";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {

        personaRepository.deleteById(id);
        return "redirect:/Persona";
    }

}
