import java.util.*;

public class Map {
    private int xmax;
    private int ymax;
    private int maxEngimonLiar;
    private int setCapital; /* X >= setCapital (Huruf Besar), X < setCapital (Huruf Kecil)*/
    private int dimensiWater;
    private Position playerPosition;
    private Position activeEngimonPosition;
    ArrayList<ArrayList<String>> peta = new ArrayList<ArrayList<String>>() ;
    ArrayList<Pair<Position, Engimon>> engimonLiar = new ArrayList<Pair<Position, Engimon>>();
    public Map(){
        this.xmax = 10;
        this.ymax = 10;
        this.setCapital = 5;
        this.dimensiWater = 5;
        this.maxEngimonLiar = 5;
        this.engimonLiar = new ArrayList<Pair<Position, Engimon>>();
        for (int i = 0; i < xmax; i++)
        {
            peta.add(new ArrayList<String>());
            for (int j = 0; j < ymax; j++)
            {
                if (i >= xmax - dimensiWater || j < ymax - dimensiWater)
                {
                    peta.get(i).add("-");
                }
                else
                {
                    peta.get(i).add("0");
                }
            }
        }
    }
    public void printMap()
    {
        for (int i = 0; i < xmax; i++)
        {
            for (int j = 0; j < ymax; j++)
            {
                System.out.print(peta.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

    public ArrayList<Pair<Position, Engimon>> getengimonLiar()
    {
        return this.engimonLiar;
    }
    
    private enum EngimonLiarName
    {
        Omnimon,
        Skull,
        Greymon,
        Piedmon,
        War_Greymon,
        MagnaAngemon,
        Garurumon,
        Devimon,
        Apocalypmon,
        Etemon,
        Agumon;

        /**
         * Pick a random value of the EngimonLiarName enum.
         * @return a random EngimonLiarName.
         */
        public static EngimonLiarName getRandomName() {
            Random random = new Random();
            return values()[random.nextInt(values().length)];
        }
    }

    public enum EngimonLiarSpesies
    {
        Firemon,
        Watermon,
        Electromon,
        Groundmon,
        Icemon;

        /**
         * Pick a random value of the EngimonLiarSpecses enum.
         * @return a random EngimonLiarSpesies.
         */
        public static EngimonLiarSpesies getRandomSpesies() {
            Random random = new Random();
            return values()[random.nextInt(values().length)];
        }
    }
    
    private enum GroundElement
    {
        F,
        G,
        E,
        L,
        N;

        /**
         * Pick a random value of the GroundElement enum.
         * @return a random GroundElement.
         */
        public static GroundElement getRandomGroundElement() {
            Random random = new Random();
            return values()[random.nextInt(values().length)];
        }
    }
    
    private enum WaterElement
    {
        W,
        I,
        S,
        N;

        /**
         * Pick a random value of the WaterElement enum.
         * @return a random WaterElement.
         */
        public static WaterElement getRandomWaterElement() {
            Random random = new Random();
            return values()[random.nextInt(values().length)];
        }
    }

    public void addEngimonLiar()
    {
        Random rand = new Random();
        if (engimonLiar.size() < maxEngimonLiar)
        {
            int coorX = 0;
            int coorY = 0;
            boolean foundx = false;
            boolean foundy = false;
            while (!foundx)
            {
                coorX = rand.nextInt(xmax-1);
                if (coorX != getplayerPositionX() && coorX != getactiveEngimonPositionX())
                {
                    foundx = true;
                }
            }
            while (!foundy)
            {
                coorY = rand.nextInt(ymax - 1);
                if (coorY != getplayerPositionY() && coorY != getactiveEngimonPositionY())
                {
                    foundy = true;
                }
            }
            Position engimonLiarpos = new Position(coorX, coorY);
            //coorX dan coorY ketemu

            //random nama
            String nama = EngimonLiarName.getRandomName().toString();
            //nama ketemu
            
            //random species
            String spesies = EngimonLiarSpesies.getRandomSpesies().toString();
            //spesies ketemu

            //random element (susah nih wkwk)
            ArrayList<String> elemennya = new ArrayList<String>();
            if (coorX <= xmax - dimensiWater || coorY >= ymax - dimensiWater)
            { //grassland
                String elemen = GroundElement.getRandomGroundElement().toString();
                if (elemen.equals("Fire/Electric"))
                {
                    elemennya.add("Fire");
                    elemennya.add("Electric");
                }
                else if (elemen.equals("Water/Ground"))
                {
                    elemennya.add("Water");
                    elemennya.add("Ground");
                }
                else
                {
                    elemennya.add(elemen);
                    elemennya.add(elemen);
                }
            }
            else
            {
                String elemen = WaterElement.getRandomWaterElement().toString();
                if (elemen.equals("Water/Ice"))
                {
                    elemennya.add("Water");
                    elemennya.add("Ice");
                }
                else if (elemen.equals("Water/Ground"))
                {
                    elemennya.add("Water");
                    elemennya.add("Ground");
                }
                else
                {
                    elemennya.add(elemen);
                    elemennya.add(elemen);
                }
            }
            Engimon engimonbaru = new Engimon(nama, spesies, elemennya);
            Pair<Position, Engimon> engimonnya = new Pair<Position,Engimon>(engimonLiarpos,engimonbaru);
            //engimonnya.first = engimonLiarpos;
            //engimonnya.second = engimonbaru;
            engimonLiar.add(engimonnya);
            setAlphabet(engimonbaru, coorX, coorY);
        }
    }

    public enum Direction
    {
        N,
        E,
        S,
        W;

        /**
         * Pick a random value of the WaterElement enum.
         * @return a random WaterElement.
         */
        public static Direction getRandomDirection() {
            Random random = new Random();
            return values()[random.nextInt(values().length)];
        }
    }

    public void moveAllEngimonLiar()
    {
        //cleaning dulu
        for (int i = 0; i < xmax; i++)
        {
            for (int j = 0; j < ymax; j++)
            {
                if (!peta.get(i).get(j).equals("P") && !peta.get(i).get(j).equals("X"))
                {
                    if (i >= xmax - dimensiWater || j < ymax - dimensiWater)
                    {
                        peta.get(i).set(j, "-");
                    }
                    else
                    {
                        peta.get(i).set(j, "0");
                    }
                }
            }
        }
        for (int a = 0; a < engimonLiar.size(); a++)
        {
            String arah = Direction.getRandomDirection().toString();
            if (arah.equals("North") && engimonLiar.get(a).first.getYCoordinate() - 1 >= 0 && engimonLiar.get(a).first.getYCoordinate() - 1 != playerPosition.getYCoordinate() && engimonLiar.get(a).first.getYCoordinate() - 1 != activeEngimonPosition.getYCoordinate())
            {
                if (engimonLiar.get(a).second.getElement().get(0) == "Water" && engimonLiar.get(a).second.getElement().get(0) == "Ground")
                {
                    engimonLiar.get(a).first.setYCoordinate(engimonLiar.get(a).first.getYCoordinate() - 1);
                }
                else if (engimonLiar.get(a).second.getElement().get(0) == "Water" || engimonLiar.get(a).second.getElement().get(0) == "Ice")
                {
                    if (engimonLiar.get(a).first.getXCoordinate() >= xmax - dimensiWater && engimonLiar.get(a).first.getYCoordinate() < ymax - dimensiWater)
                    {
                        engimonLiar.get(a).first.setYCoordinate(engimonLiar.get(a).first.getYCoordinate() - 1);
                    }
                }
                else
                {
                    if (engimonLiar.get(a).first.getYCoordinate() - 1 >= ymax - dimensiWater)
                    {
                        engimonLiar.get(a).first.setYCoordinate(engimonLiar.get(a).first.getYCoordinate() - 1);
                    }
                }
            }
            else if (arah.equals("South") && engimonLiar.get(a).first.getYCoordinate() + 1 < ymax && engimonLiar.get(a).first.getYCoordinate() + 1 != playerPosition.getYCoordinate() && engimonLiar.get(a).first.getYCoordinate() + 1 != activeEngimonPosition.getYCoordinate())
            {
                if (engimonLiar.get(a).second.getElement().get(0) == "Water" || engimonLiar.get(a).second.getElement().get(0) == "Ice")
                {
                    if (engimonLiar.get(a).first.getXCoordinate() >= xmax - dimensiWater && engimonLiar.get(a).first.getYCoordinate() < ymax - dimensiWater && engimonLiar.get(a).first.getYCoordinate() + 1 < ymax - dimensiWater)
                    {
                        engimonLiar.get(a).first.setYCoordinate(engimonLiar.get(a).first.getYCoordinate() + 1);
                    }
                }
                else
                {
                    engimonLiar.get(a).first.setYCoordinate(engimonLiar.get(a).first.getYCoordinate() + 1);
                }
            }
            else if (arah.equals("West") && engimonLiar.get(a).first.getXCoordinate() - 1 >= 0 && engimonLiar.get(a).first.getXCoordinate() - 1 != playerPosition.getXCoordinate() && engimonLiar.get(a).first.getXCoordinate() - 1 != activeEngimonPosition.getXCoordinate())
            {
                if (engimonLiar.get(a).second.getElement().get(0) == "Water" || engimonLiar.get(a).second.getElement().get(0) == "Ice")
                {
                    if (engimonLiar.get(a).first.getXCoordinate() >= xmax - dimensiWater && engimonLiar.get(a).first.getYCoordinate() < ymax - dimensiWater && engimonLiar.get(a).first.getXCoordinate() - 1 >= xmax - dimensiWater)
                    {
                        engimonLiar.get(a).first.setXCoordinate(engimonLiar.get(a).first.getXCoordinate() - 1);
                    }
                }
                else
                {
                    engimonLiar.get(a).first.setXCoordinate(engimonLiar.get(a).first.getXCoordinate() - 1);
                }
            }
            else if (arah.equals("East") && engimonLiar.get(a).first.getXCoordinate() + 1 < xmax && engimonLiar.get(a).first.getXCoordinate() + 1 != playerPosition.getXCoordinate() && engimonLiar.get(a).first.getXCoordinate() + 1 != activeEngimonPosition.getXCoordinate())
            {
                if (engimonLiar.get(a).second.getElement().get(0) == "Water" && engimonLiar.get(a).second.getElement().get(0) == "Ground")
                {
                    engimonLiar.get(a).first.setXCoordinate(engimonLiar.get(a).first.getXCoordinate() + 1);
                }
                else if (engimonLiar.get(a).second.getElement().get(0) == "Water" || engimonLiar.get(a).second.getElement().get(0) == "Ice")
                {
                    if (engimonLiar.get(a).first.getXCoordinate() >= xmax - dimensiWater && engimonLiar.get(a).first.getYCoordinate() < ymax - dimensiWater)
                    {
                        engimonLiar.get(a).first.setXCoordinate(engimonLiar.get(a).first.getXCoordinate() + 1);
                    }
                }
                else
                {
                    if (engimonLiar.get(a).first.getXCoordinate() + 1 < xmax - dimensiWater)
                    {
                        engimonLiar.get(a).first.setXCoordinate(engimonLiar.get(a).first.getXCoordinate() - 1);
                    }
                }
            }
            setAlphabet(engimonLiar.get(a).second, engimonLiar.get(a).first.getYCoordinate(), engimonLiar.get(a).first.getXCoordinate());
        }
    }

    public void setAlphabet(Engimon engimonbaru, int coorY, int coorX)
    {
        if (engimonbaru.getElement().get(0) == "Fire" && engimonbaru.getElement().get(1) == "Fire")
        {
            peta.get(coorY).set(coorX,"f");
        }
        else if (engimonbaru.getElement().get(0) == "Water" && engimonbaru.getElement().get(1) == "Water")
        {
            peta.get(coorY).set(coorX,"w");
        }
        else if (engimonbaru.getElement().get(0) == "Ice" && engimonbaru.getElement().get(1) == "Ice")
        {
            peta.get(coorY).set(coorX,"i");
        }
        else if (engimonbaru.getElement().get(0) == "Ground" && engimonbaru.getElement().get(1) == "Ground")
        {
            peta.get(coorY).set(coorX,"g");
        }
        else if (engimonbaru.getElement().get(0) == "Electric" && engimonbaru.getElement().get(1) == "Electric")
        {
            peta.get(coorY).set(coorX,"e");
        }
        else if (engimonbaru.getElement().get(0) == "Fire" && engimonbaru.getElement().get(1) == "Electric")
        {
            peta.get(coorY).set(coorX,"l");
        }
        else if (engimonbaru.getElement().get(0) == "Water" && engimonbaru.getElement().get(1) == "Ice")
        {
            peta.get(coorY).set(coorX,"s");
        }
        else if (engimonbaru.getElement().get(0) == "Water" && engimonbaru.getElement().get(1) == "Ground")
        {
            peta.get(coorY).set(coorX,"n");
        }
    }

    public void removeEngimonLiar(Engimon engimon)
    {
        int index = 0;
        int xnya;
        int ynya;
        for (int i = 0; i < engimonLiar.size(); i++)
        {
            if (engimonLiar.get(i).second == engimon)
            {
                index = i;
            }
        }
        xnya = engimonLiar.get(index).first.getXCoordinate();
        ynya = engimonLiar.get(index).first.getYCoordinate();
        if (xnya <= xmax - dimensiWater || ynya >= ymax - dimensiWater)
        {
            peta.get(xnya).set(ynya,"-");
        }
        else
        {
            peta.get(xnya).set(ynya,"0");
        }
        engimonLiar.remove(index);
    }

    //getter
    public int getmaxEngimonLiar() { return this.maxEngimonLiar; }
    public int getxmax() { return this.xmax; }
    public int getymax() { return this.ymax; }
    public Position getplayerPosition() { return this.playerPosition; }
    public Position getactiveEngimonPosition() { return this.activeEngimonPosition; }
    public int getplayerPositionX() { return this.playerPosition.getXCoordinate(); }
    public int getplayerPositionY() { return this.playerPosition.getYCoordinate(); }
    public int getactiveEngimonPositionX() { return this.activeEngimonPosition.getXCoordinate(); }
    public int getactiveEngimonPositionY() { return this.activeEngimonPosition.getYCoordinate(); }
    
    //setter
    public void setplayerPosition(int _x, int _y)
    {
        playerPosition.setXCoordinate(_x);
        playerPosition.setYCoordinate(_y);
        for (int i = 0; i < xmax; i++)
        {
            for (int j = 0; j < ymax; j++)
            {
                if (peta.get(i).get(j).equals("P"))
                {
                    if (i >= xmax - dimensiWater || j < ymax - dimensiWater)
                    {
                        peta.get(i).set(j, "-");
                    }
                    else
                    {
                        peta.get(i).set(j, "0");
                    }
                }
            }
        }
        peta.get(_y).set(_x, "P");
    }

    public void setactiveEngimonPosition(int _x, int _y)
    {
        activeEngimonPosition.setXCoordinate(_x);
        activeEngimonPosition.setYCoordinate(_y);
        for (int i = 0; i < xmax; i++)
        {
            for (int j = 0; j < ymax; j++)
            {
                if (peta.get(i).get(j).equals("X"))
                {
                    if (i >= xmax - dimensiWater || j < ymax - dimensiWater)
                    {
                        peta.get(i).set(j, "-");
                    }
                    else
                    {
                        peta.get(i).set(j, "0");
                    }
                }
            }
        }
        peta.get(_y).set(_x, "X");
    }
}
