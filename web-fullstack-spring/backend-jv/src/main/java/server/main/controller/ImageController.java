package server.main.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import server.main.security.JwtTokenUtil;
import server.main.user.ApiUserService;
import server.main.user.ApiUser;
import server.main.image.Image;
import server.main.image.ImageService;

//---------------------------------------------------------------
//---- This controller provides mapping for the task repository
//---- MAP: /image - returns all images that belongs to a user
//---------------------------------------------------------------

@CrossOrigin
@RestController
public class ImageController {

    //-----------------------------------------------
    @Autowired
    private ImageService imageService;
    @Autowired
    private ApiUserService userService;

    //---- Use JWT token util class for extracting username
    @Autowired
    private JwtTokenUtil jwtUtil;
    //-----------------------------------------------

    /**
     * Provide user access to the internal data. Check if the security token
     * is passed in the header. If the token is passed, extract username from
     * the token and search for tasks corresponding to that particular username.
     * But in general this will not be open to outside anyway.
     * @param headers
     * @return a list of images that belong to a user (or empty array)
     */
    @GetMapping("/image-list")
    public List<Image> getAll(@RequestHeader Map<String, String> headers) {
        if (headers.containsKey("authorization")) {
            String token = headers.get("authorization").replace("Bearer ", "");
            String username = jwtUtil.getUsernameFromToken(token);
            ApiUser user = userService.findByUsername(username);

            if (user != null) {
                Long id = user.getId();
                return imageService.getByUserID(id);
            }
        }
        return new ArrayList<Image>();
    }

    /**
     * Provides an API for uploading a file with an additional check of user
     * credentials. Also updates the database of images.
     * @param headers
     * @param file
     * @return
     */
    @PostMapping("/upload-image")
    public String addImage(@RequestHeader Map<String, String> headers, @RequestParam("file") MultipartFile file) {

        if (headers.containsKey("authorization")) {
            String token = headers.get("authorization").replace("Bearer ", "");
            String username = jwtUtil.getUsernameFromToken(token);
            ApiUser user = userService.findByUsername(username);

            if (user != null) {
                Long id = user.getId();
                imageService.addNewFile(id, file);
                return "OK";
            }
        }
        return "NO";
    }

    @GetMapping(value="/image/{imageID}")
    public String getImage(@RequestHeader Map<String, String> headers, @PathVariable Long imageID) {
        System.out.println("HI " + imageID);

        try {
            if (headers.containsKey("authorization")) {
                String token = headers.get("authorization").replace("Bearer ", "");
                String username = jwtUtil.getUsernameFromToken(token);
                ApiUser user = userService.findByUsername(username);

                if (user != null) {
                    Long userID = user.getId();
                    Image img = imageService.getByImageID(imageID, userID);

                    if (img != null) {
                        System.out.println(img.getName());
                        Path filepath = Paths.get("images", img.getName());
                        InputStream in = Files.newInputStream(filepath);
                        byte[] targetArray = new byte[in.available()];
                        in.read(targetArray);
                       return new String(Base64.getEncoder().encode(targetArray));

                    }
                }
            }

            return "";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}