package com.cotroller;

import com.service.FileHandlerService;
import com.service.FileHandlerServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

@Controller
public class imageController {

    @RequestMapping(value = "/image/{imageName}.{extension}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void returnImage(HttpServletResponse response,
                            @PathVariable("imageName") String imageName,
                            @PathVariable("extension") String extension) {

        try(OutputStream out = response.getOutputStream()) {
            FileHandlerService fileHandlerService = FileHandlerServiceImpl.instance();
            BufferedImage bufImg = fileHandlerService.getBufferedImage(imageName, extension);
            ImageIO.write(bufImg, extension, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
