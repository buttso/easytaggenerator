/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buttso.demo.easytaggenerator;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author sbutton
 */
public class Colors {
    
    static String[] AHC =   {"AHC",  "0080FF"};
    static String[] AUHC =  {"AUHC", "FFFFFF"};    
    static String[] BHC =   {"BHC",  "0000FF"};
    static String[] FHC =   {"FHC",  "162C56"};
    static String[] GRRYHC = {"GRRYHC","800000"};
    static String[] NEHC =  {"NEHC", "FF0000"};
    static String[] PADHC=  {"PADHC","000000"};
    static String[] SHC =   {"SHC",  "00C4C4"};
    static String[] WHC =   {"WHC",  "008000"};
    static String[] SPOCHC =   {"SPOCHC",  "FFFFFF"};
    
    static HashMap<String, String> COLOR_MAP = new HashMap<>();
    static {
        COLOR_MAP.put(AHC[0],AHC[1]);
        COLOR_MAP.put(AUHC[0], AUHC[1]);        
        COLOR_MAP.put(BHC[0], BHC[1]);
        COLOR_MAP.put(FHC[0], FHC[1]);
        COLOR_MAP.put(GRRYHC[0], GRRYHC[1]);
        COLOR_MAP.put(NEHC[0], NEHC[1]);
        COLOR_MAP.put(PADHC[0], PADHC[1]);
        COLOR_MAP.put(SHC[0], SHC[1]);
        COLOR_MAP.put(WHC[0], WHC[1]);
        COLOR_MAP.put(SPOCHC[0], SPOCHC[1]);
    }
    
    public static String teamColor(String team) {
        System.out.println("TEAM: " + team);
        return COLOR_MAP.containsKey(team)? COLOR_MAP.get(team) : "";
    }
    
    
}
