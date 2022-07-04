package com.growbot1.growbot;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    ContactRepository contactrepository;
    @Autowired
    private MailService notificationService;

    @Autowired
    private User user;


    @RequestMapping("/")
    public String home(){
        return "index";
    }
    @GetMapping("/About")
    public String About(){
        return "About";
    }
    @GetMapping("/add")
    public String Contactform(Model model){
        model.addAttribute("Contact", new contact());
        return "Contact";
    }
    @PostMapping("/process")
    public String processform(@Valid contact Contact, BindingResult result){

        user.setFirstName("Growbot");
        user.setLastName("Growbot will contact you shortly with more information");
        user.setEmailAddress(Contact.getEmail());
        System.out.println(Contact.getEmail());

        if(result.hasErrors()){
            return"Contact";
        }
        contactrepository.save(Contact);
        try {
            notificationService.sendEmail(user);
        } catch (MailException mailException) {
            System.out.println(mailException);
        }
        return "redirect:/";

    }
}
