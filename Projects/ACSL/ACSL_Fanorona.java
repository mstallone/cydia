import java.util.*;
import java.io.*;

class ACSLFanorona {
    
    private final ArrayList<String> Paths = new ArrayList<String>(Arrays.asList(
         new String[] {"1-2","2-1","2-3","3-2","3-4","4-3","4-5","5-4","6-7","7-6","7-8","8-7","8-9","9-8","9-10","10-9","11-12","12-11","12-13","13-12","13-14","14-13","14-15","15-14","16-17","17-16","17-18","18-17","18-19","19-18","19-20","20-19","21-22","22-21","22-23","23-22","23-24","24-23","24-25","25-24","1-6","6-1","6-11","11-6","11-16","16-11","16-21","21-16","2-7","7-2","7-12","12-7","12-17","17-12","17-22","22-17","3-8","8-3","8-13","13-8","13-18","18-13","18-23","23-18","4-9","9-4","9-14","14-9","14-19","19-14","19-24","24-19","5-10","10-5","10-15","15-10","15-20","20-15","20-25","25-10","1-7","7-1","7-13","13-7","13-19","19-13","19-25","25-19","5-9","9-5","9-13","13-9","13-17","17-13","17-21","21-17"}));
    private final ArrayList<String> Lines = new ArrayList<String>(Arrays.asList(new String[]{"d1-1-2-3-4-5","d1-6-7-8-9-10","d1-11-12-13-14-15","d1-16-17-18-19-20","d1-21-22-23-24-25","d5-1-6-11-16-21","d5-2-7-12-17-22","d5-3-8-13-18-23","d5-4-9-14-19-24","d5-5-10-15-20-25","d6-1-7-13-19-25","d4-5-9-13-17-21"}));
    
    public static void main(String[] args) throws Exception{
        for(int i = 0; i < 5; i++) new ACSLFanorona();
    }
    
    public ACSLFanorona() throws Exception{
        System.out.println("Input:");
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String[] raw = bf.readLine().split(", ");
        ArrayList<String> whites = new ArrayList<String>();
        ArrayList<String> blacks = new ArrayList<String>();
        for(int i = 1; i <= Integer.parseInt(raw[0]); i++) whites.add(raw[i]);
        for(int i = Integer.parseInt(raw[0]) + 2; i < raw.length; i++) blacks.add(raw[i]);
        ArrayList<String> possibleMoves = new ArrayList<String>();
        ArrayList<String> ActionLine = null;
        for(String white : whites) for(String move : Paths) if(move.split("-")[0].equals(white)) if(!whites.contains(move.split("-")[1]) && !blacks.contains(move.split("-")[1])) possibleMoves.add(move);
        for(int pm = 0; pm < possibleMoves.size(); pm++) {
            String s = possibleMoves.get(pm);
            int direction = (Integer.parseInt(s.split("-")[0]) - Integer.parseInt(s.split("-")[1]));
            for(String line : Lines) if(line.split("-")[0].contains(Integer.toString(Math.abs(direction)))) for (String part : line.split("-")) if(part.equals(s.split("-")[0])) ActionLine = new ArrayList<String>(Arrays.asList(line.split("-")));
            boolean hasBlack = false;
            for(int i = 0; i < ActionLine.size(); i++) if (blacks.contains(ActionLine.get(i))) hasBlack = true;
            if(!hasBlack) {
                possibleMoves.remove(pm--);
                continue;
            }
            boolean validBlackBehind = false;
            if(blacks.contains(Integer.toString(Integer.parseInt(s.split("-")[0]) + direction)) && ActionLine.contains(Integer.toString(Integer.parseInt(s.split("-")[0]) + direction))) validBlackBehind = true;
            boolean validBlackInfront = false;
            if(blacks.contains(Integer.toString(Integer.parseInt(s.split("-")[1]) - direction)) && ActionLine.contains(Integer.toString(Integer.parseInt(s.split("-")[1]) - direction))) validBlackInfront = true;
            
            if (!validBlackBehind && !validBlackInfront) possibleMoves.remove(pm--);
        }

        if(possibleMoves.size() == 0) System.out.println("Output: NONE");
        else{
            if(possibleMoves.size() > 1) System.out.println("More than one input! Liar!");
            else{
                ArrayList<String> blacksInLine = new ArrayList<String>();
                ArrayList<String> lineForMove = new ArrayList<String>();
                int direction = (Integer.parseInt(possibleMoves.get(0).split("-")[0]) - Integer.parseInt(possibleMoves.get(0).split("-")[1]));
                for(String line : Lines) if(line.split("-")[0].contains(Integer.toString(Math.abs(direction)))) for (String part : line.split("-")) if(part.equals(possibleMoves.get(0).split("-")[0])) lineForMove = new ArrayList<String>(Arrays.asList(line.split("-")));
                for (String linePart : lineForMove) if (blacks.contains(linePart)) blacksInLine.add(linePart);
                String[] blackContinuosForLine = lineForMove.toArray(new String[lineForMove.size()]);
                System.out.println(blacksInLine);
                for(String move : possibleMoves) System.out.println(move);
            }
        }
    }
}