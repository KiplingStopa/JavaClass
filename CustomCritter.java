import java.awt.Color;

public class CustomCritter extends Critter {
    private static final String SPECIES_NAME = "Spider";
    
    public CustomCritter(){
        super(SPECIES_NAME);
    }

    @Override
    public Color getColor(){
        //This spider is black
        return Color.BLACK;
    }

    @Override
    public Direction getMove(){
        //The spider loves raccoons and open spaces, and seeks them out first
        //if no open spaces exist it stays where it is.
        Direction[] dir = new Direction[4];
        dir[0]=Direction.NORTH;
        dir[1]=Direction.EAST;
        dir[2]=Direction.SOUTH;
        dir[3]=Direction.WEST;
        for(int i = 0; i<dir.length;i++){
            if (info.getNeighbor(dir[i]).equals("R")){
                return dir[i];
            }
            else if (info.getNeighbor(dir[i]).equals(" ")){
                return dir[i];
            }
        }
        return Direction.CENTER;
    }

    
    @Override
    public Attack getAttack(String opponent){
        if (opponent.equals("R")||opponent.equals("Tu")){
            return Attack.POUNCE;
        }
        return Attack.SCRATCH;
    }
    
}
