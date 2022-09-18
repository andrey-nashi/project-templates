package server.main.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//---------------------------------------------------------------
//---- This controller is an example of an api that can be used
//---- without the necessity for authentification. Anyon can access it.
//---- MAP: /free - returns a string
//---------------------------------------------------------------

@RestController
@CrossOrigin
public class GlobalController {
    @GetMapping("/free")
    public String debug2() {
        return "This API can be accessible by everyone.";
    }
}