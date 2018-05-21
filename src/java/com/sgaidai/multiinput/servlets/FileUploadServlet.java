package com.sgaidai.multiinput.servlets;

import com.sgaidai.multiinput.imagemodification.Darker;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(description = "Upload File To The Server", urlPatterns = { "/fileUploadServlet" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, maxFileSize = 1024 * 1024 * 30, maxRequestSize = 1024 * 1024 * 50)
public class FileUploadServlet extends HttpServlet {
    
        private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static final long serialVersionUID = 1L;
	public static final String UPLOAD_DIR = "uploadedFiles";

        
        /***** This Method Is Called By The Servlet Container To Process A 'POST' Request *****/
	public void doPost(HttpServletRequest request, HttpServletResponse response)  {
            try {
                handleRequest(request, response);
            } catch (IOException ex) {
                Logger.getLogger(FileUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServletException ex) {
                Logger.getLogger(FileUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
	}

	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {
            
               
                              
		
                String applicationPath = getServletContext().getRealPath(""),
                            currentDate = dateFormat.format(new Date()),
                            generatedString = generateRandomString(),
                            generatedPath = File.separator + currentDate + File.separator  + generatedString,
                            
                            uploadPath = applicationPath + File.separator + UPLOAD_DIR + generatedPath ; 

                File fileUploadDirectory = new File(uploadPath);
                if (!fileUploadDirectory.exists()) {
                        fileUploadDirectory.mkdirs();
                }

		String fileName = "";
                String type = "";
//                String effect = "monochromer";
                Part destFileNamePart = request.getPart("effect");
                Scanner s = new Scanner(destFileNamePart.getInputStream());
                String effect = s.nextLine();
                
                if ((effect == null || effect.equals(""))){
                    System.out.println("Effect is not selected: "+ effect);
                }else{              
                    Darker darker = new Darker(effect,uploadPath);
                    List<File> fileList = new ArrayList<File>();
                    File currentImage = null;
                    System.out.println("Effect: "+ effect);

                    for (Part part : request.getParts()) {
                        // file type validation
                        if (part.getContentType() == null){
                            continue;
                        }
                        switch(part.getContentType()){
                            case "image/jpeg": type = "jpg"; break;
                            case "image/png": type = "png"; break;
                            case "image/bmp": type = "bmp"; break;
                            case "image/gif": type = "gif"; break;
                            case "image/jpg": type = "jpg"; break;
                            default: continue; 
                        }
                        fileName = extractFileName(part);

                        try {
                            currentImage = darker.loadImage(part.getInputStream(), fileName, type); 
                               fileList.add(currentImage);
                        } catch (IOException | InterruptedException ioObj) {
                                ioObj.printStackTrace();
                        }
                    }

    //		request.setAttribute("uploadedFiles", fileList);
    //		RequestDispatcher dispatcher = request.getRequestDispatcher("/fileuploadResponse.jsp");
    //		dispatcher.forward(request, response);
                    String greetings = generatedString + currentDate;
                  

                   
                    String json =  ("{\"date\": \"" + currentDate + "\","
                                    +"\"generatedString\": \""+ generatedString  + "\"}");
                    response.setCharacterEncoding("utf8");
                    response.setContentType("application/json"); 
                    PrintWriter out = response.getWriter(); 
                    out.print(json);
                    out.flush();
                }
	}

	/***** Helper Method #1 - This Method Is Used To Read The File Names *****/
	private String extractFileName(Part part) {
		String fileName = "", 
				contentDisposition = part.getHeader("content-disposition");
		String[] items = contentDisposition.split(";");
		for (String item : items) {
			if (item.trim().startsWith("filename")) {
				fileName = item.substring(item.indexOf("=") + 2, item.length() - 1);
			}
		}
		return fileName;
	}
        private String generateRandomString() {
  
            int leftLimit = 97; // letter 'a'
            int rightLimit = 122; // letter 'z'
            int targetStringLength = 20; // letters and digits
            Random random = new Random();
            StringBuilder buffer = new StringBuilder(targetStringLength);
            for (int i = 0; i < targetStringLength/2; i++) {
                int randomLimitedInt = leftLimit + (int) 
                  (random.nextFloat() * (rightLimit - leftLimit + 1));
                buffer.append(random.nextInt(10) + 1);
                buffer.append((char) randomLimitedInt);

            }
            String generatedString = buffer.toString();

            return generatedString;
        }
}