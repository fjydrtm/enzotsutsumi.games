package application.controllers;
 
import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
 
import application.models.Jogo;
import application.repositories.JogoRepository;
 
@Controller
@RequestMapping("/jogos")
public class JogoController {
    @Autowired
    private JogoRepository jogoRepo;
 
    @RequestMapping("list")
    public String list(Model model) {
        model.addAttribute("jogos", jogoRepo.findAll());
        return "list.jsp"; 
    }
 
    @RequestMapping("insert")
    public String formInsert() {
        return "insert.jsp";
    }
 
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public String saveInsert(@RequestParam("titulo") String titulo) {
        Jogo jogo = new Jogo();
        jogo.setTitulo(titulo);
 
        jogoRepo.save(jogo);
 
        return "redirect:/jogos/list";
    }
 
    @RequestMapping("update/{id}")
    public String formUpdate(Model model, @PathVariable int id) {
        Optional<Jogo> jogo = jogoRepo.findById(id);
        if(!jogo.isPresent())
            return "redirect:/jogos/list";
        model.addAttribute("jogo", jogo.get());
        return "/jogos/update.jsp";
    }
 
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String saveUpdate(@RequestParam("nome") String nome, @RequestParam("id") int id) {
        Optional<Jogo> jogo = jogoRepo.findById(id);
        if(!jogo.isPresent())
            return "redirect:/jogos/list";
        jogo.get().setNome(nome);
 
        jogoRepo.save(jogo.get());
 
        return "redirect:/jogos/list";
    }
 
    @RequestMapping("delete/{id}")
    public String formDelete(Model model, @PathVariable int id) {
        Optional<Jogo> jogo = jogoRepo.findById(id);
        if(!jogo.isPresent())
            return "redirect:/jogos/list";
        model.addAttribute("jogo", jogo.get());
        return "/jogos/delete.jsp";
    }
 
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String confirmDelete(@RequestParam("id") int id) {
        jogoRepo.deleteById(id);
        return "redirect:/jogos/list";
    }
}