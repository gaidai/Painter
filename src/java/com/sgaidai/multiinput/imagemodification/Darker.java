/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sgaidai.multiinput.imagemodification;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author S.Gaidai
 */
public class Darker {

public String effect ="";    
public static int h,w;
public static BufferedImage img ;
public static int[] pixels;
public static int THREADS_COUNT = 4; 
public String uploadPath = "";

    public Darker(String effect, String uploadPath) {
        this.effect = effect;
        this.uploadPath = uploadPath;
    }

    public File loadImage(InputStream  input , String fileName , String type) throws IOException, InterruptedException{
        
        img = ImageIO.read(input);        
        h = img.getHeight();
        w = img.getWidth();
//        System.out.println("Picture : "+ h + " x " + w);  
        pixels = new int[w*h] ;
//        long startTime = System.nanoTime();        
        // get part of array
        int rowInterval = h / THREADS_COUNT;
        List <Thread> threads= new ArrayList();
        
        for(int i = 0; i <THREADS_COUNT; i++){    
            //  part of array for every thread
            Thread th = new Thread( new Block(rowInterval*i, rowInterval*(i+1), this.effect ), "Thread "+i);
            threads.add(th);
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
       
//        long endTime = System.nanoTime();
//        long duration = (endTime - startTime);
//        System.out.println(duration/1000000);
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, w, h, pixels, 0, w);        
        File imageFile = new File(uploadPath +File.separator+ fileName);
        ImageIO.write(image, type, imageFile); 
        return imageFile;
    }
    
 
    
}
