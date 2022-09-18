package server.main.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.OutputStream;
import java.nio.file.Files;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public ImageService() {}

    public List<Image> getAll() {
        List<Image> output= new ArrayList<>();
        for (Image image : imageRepository.findAll()) {
            output.add(image);
        }
        return output;
    }

    public List<Image> getByUserID(Long userID) {
        List<Image> output = new ArrayList<>();
        for (Image image: imageRepository.findByUserID(userID)) {
            output.add(image);
        }
        return output;
    }

    public Image getByImageID(Long imageID, Long userID) {
        List <Image> output = imageRepository.findByImageID(imageID, userID);

        if (output != null) {
            return output.get(0);
        }

        return null;
    }

    public void addNewFile(Long userID, MultipartFile file) {
        String name = file.getOriginalFilename();
        Path filepath = Paths.get("images", name);

        try {
            imageRepository.save(new Image(userID, name));

            OutputStream os = Files.newOutputStream(filepath);
            os.write(file.getBytes());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }


}