/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sgaidai.multiinput.imagemodification;

/**
 *
 * @author user
 */
public abstract class Effects {
    
     
    public static int dif = 25;
    public static double factor = 0.5;
    public static double BLACK_WHITE_SENSETIVITY = 0.35;
    
    
    public static int[] brightner(int ... args) {
        for(int i = 0;i< 3;i++){
            args[i] = (int) ((Integer) args[i] * factor);
            if(args[i]>255){
                args[i] = 255;
            }if(args[i] <0){
                args[i] = 0;
            }
        }    
        return args;
    }
    public static int[] lighterDif(int ... args) {
        for(int i = 0;i< 3;i++){
            args[i] = args[i] + dif;
            if(args[i]>255){
                args[i] = 255;
            }if(args[i] <0){
                args[i] = 0;
            }
        }    
        return args;
    }
    public static int[] negativ(int ... args) {
        for(int i = 0;i< 3;i++){
            args[i] = 255 - args[i]; 
        }    
        return args;
    }
    public static int[] monochromer(int ... args) {
        int sum = 0;
        for(int arg: args){
            sum = sum +  arg ; 
        }
        int[] result = new int[3];
        for(int i = 0;i< 3;i++){
            result[i]= sum / 3 ;
                    
        }
        return result;
    }
    public static int[] blacwAndWhite(int ... args) {
        int cof = (int) (255*BLACK_WHITE_SENSETIVITY);
        int sum = 0;
        for(int arg: args){
            sum = sum +  arg ; 
        }
        
        int result =  sum / 3  ;
        if (result > cof){
            for(int i = 0;i< 3;i++){            
                args[i]= 255 ;
                    
            }
        }else{
            for(int i = 0;i< 3;i++){            
                args[i]= 0 ;                    
            }            
        }   
        return args;       

    }

    
}
