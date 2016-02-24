/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buttso.demo.easytaggenerator;

import com.beust.jcommander.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author sbutton
 */
//@Parameters(separators = "=")
public class PanelOptions {
    
    @Parameter
    private List<String> parameters = new ArrayList<>();
    
    @Parameter(names="-debug", hidden = true)
    boolean debug = false;

    @Parameter(names = "-help", help = true)
    boolean help = false;

    @Parameter(names = {"-s", "-stats"}, required = false, description = "List of stats to create in panel", variableArity = true)
    List<String> stats = Arrays.asList("A25", "AC", "Shot on T", "Shot Miss T", "PC", "Goal");

    @Parameter(names = {"-n", "--name"}, required = false)
    String name = "";

    @Parameter(names = {"-h", "--home"}, required = false)
    String home = "Home";

    @Parameter(names = {"-o", "--opponent"}, required = false)
    String opponent = "Opp";

//    @Parameter(names = {"-hcat", "--homeCategory"}, required = false)
//    String homeCategory = home;
//
//    @Parameter(names = {"-ocat", "--opponentCategory"}, required = false)
//    String opponentCategory = opponent;

//    @Parameter(names = {"-hlp", "--homeLabelPrefix"}, required = false)
//    String homeLabelPrefix = home.toString();
//
//    @Parameter(names = {"-olp", "--opponentLabelPrefix"}, required = false)
//    String opponentLabelPrefix = opponent.toString();

    @Parameter(names = {"-hcol", "--homeColor"}, required = false)
    private String homeColor = "";

    @Parameter(names = {"-ocol", "--opponentColor"}, required = false)
    private String opponentColor = "";

    final String defaultHomeColor = "0080FF";
    final String defaultOpponentColor = "FF3300";
    
    public String getHomeColor() {
        if (!homeColor.equals("")) {
            return homeColor;
        }
        String color = Colors.teamColor(home);
        return color.equals("")? defaultHomeColor: color;
    }
    
    public String getOpponentColor() {
        if (!opponentColor.equals("")) {
            return opponentColor;
        }
        String color = Colors.teamColor(opponent);
        return color.equals("")? defaultOpponentColor: color;
    }
    

    
    
    
}
