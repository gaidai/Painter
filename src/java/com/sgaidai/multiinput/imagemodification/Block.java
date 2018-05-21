/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sgaidai.multiinput.imagemodification;

import static com.sgaidai.multiinput.imagemodification.Darker.img;
import static com.sgaidai.multiinput.imagemodification.Darker.pixels;
import static com.sgaidai.multiinput.imagemodification.Darker.w;
import static com.sgaidai.multiinput.imagemodification.Effects.blacwAndWhite;
import static com.sgaidai.multiinput.imagemodification.Effects.brightner;
import static com.sgaidai.multiinput.imagemodification.Effects.lighterDif;
import static com.sgaidai.multiinput.imagemodification.Effects.monochromer;
import static com.sgaidai.multiinput.imagemodification.Effects.negativ;
import java.util.List;



public class Block implements Runnable{
    
    private int startRow, endRow;
    private List all;
    private String effect="";

    public Block(int startRow , int endRow ,String effect) {
        this.startRow = startRow;
        this.endRow = endRow;    
        this.effect = effect;
    }  
    
    @Override
    public void run() {
        for(int y = startRow; y < endRow ;y++){            
            for (int x = 0; x < w;  x++) {             
                
                int rgb = img.getRGB(x, y);
                int red =(rgb >> 16) & 0x000000FF;        
                int green =  (rgb >>8 ) & 0x000000FF;
                int blue = (rgb) & 0x000000FF;
                int [] colors = null ;
                // use effect
                switch(effect){
                    
                    case "blacwAndWhite":
                        colors =  blacwAndWhite(red, green, blue); 
                        break;
                    case "brightner":
                        colors =  brightner(red, green, blue); 
                        break; 
                    case "lighterDif":
                        colors =  lighterDif(red, green, blue); 
                        break;
                    case "negativ":
                        colors =  negativ(red, green, blue); 
                        break;
                    case "monochromer":
                        colors =  monochromer(red, green, blue); 
                        break;
                    default:colors =  negativ(red, green, blue); 
                    
                }
                    
                int pix = (colors[0] << 16 | colors[1] << 8 | colors[2]);
                pixels[w*y + x ] = pix;
            }
            
        }
        System.out.println( Thread.currentThread().getName());  
    }
    
    
}
