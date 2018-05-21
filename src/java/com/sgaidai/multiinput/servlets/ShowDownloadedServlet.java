package com.sgaidai.multiinput.servlets;

import com.sgaidai.multiinput.imagemodification.FileExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(description = "Upload Files From Server", urlPatterns = { "/showDownloadedServlet" })
public class ShowDownloadedServlet extends HttpServlet {
    
   private static final long serialVersionUID = 1L;
   
	public static int BUFFER_SIZE = 1024 * 100;
	public static final String UPLOAD_DIR = "uploadedFiles";
         List <String> files;
        
        /***** This Method Is Called By The Servlet Container To Process A 'POST' Request *****/
	public void doGet(HttpServletRequest request, HttpServletResponse response)  {
            try {
                handleRequest(request, response);
            } catch (ServletException ex) {
                Logger.getLogger(ShowDownloadedServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ShowDownloadedServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
	}

	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String params = request.getParameter("date") + File.separator+ request.getParameter("generated") ,                       
                        applicationPath = getServletContext().getRealPath(""),
                        downloadPath =  UPLOAD_DIR + File.separator + params + File.separator ,
                        filesPath = applicationPath + UPLOAD_DIR + File.separator + params + File.separator;
                System.out.println( " Path : " + filesPath);
                
		File dir = new File(filesPath);
                files = new ArrayList();
             
             

                if( dir.exists()&& dir.isDirectory() ){
                    for(String file: dir.list(new FileExtensionFilter("jpg","jpeg","bmp","gif","png"))){
                        System.out.println(file);
                        files.add(file);
                    }
                }
                request.setAttribute("filesPath", downloadPath);
                             
                request.setAttribute("files", files);
                request.setAttribute("generated", request.getParameter("generated"));
                request.setAttribute("date", request.getParameter("date"));
           
		
                request.getRequestDispatcher("results.jsp").forward(request, response);
	}
}