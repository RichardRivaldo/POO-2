import java.util.*;

public class Map {
    private int xmax;
    private int ymax;
    private int maxEngimonLiar;
    private int setCapital; /* X >= setCapital (Huruf Besar), X < setCapital (Huruf Kecil)*/
    private int dimensiWater;
    private Position playerPosition;
    private Position activeEngimonPosition;
    Vector<Vector<String>> peta = new Vector<Vector<String>>() ;
    Vector<Pair<Position, Engimon>> engimonLiar = new Vector<Pair<Position, Engimon>>();
    public Map(){
        this.xmax = 10;
        this.ymax = 10;
        this.setCapital = 5;
        this.dimensiWater = 5;
        this.maxEngimonLiar = 5;
        this.engimonLiar = new Vector<Pair<Position, Engimon>>();
        for (int i = 0; i < xmax; i++)
        {
            peta.add(new Vector<String>());
            for (int j = 0; j < ymax; j++)
            {
                if (i >= xmax - dimensiWater || j < ymax - dimensiWater)
                {
                    peta.elementAt(i).add("-");
                }
                else
                {
                    peta.elementAt(i).add("0");
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
                System.out.print(peta.elementAt(i).get(j) + " ");
            }
            System.out.println();
        }
    }

    public Vector<Pair<Position, Engimon>> getengimonLiar()
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
            Vector<String> elemennya = new Vector<String>();
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
            Pair<Position, Engimon> engimonnya = new Pair<Position,Engimon>();
            engimonnya.first = engimonLiarpos;
            engimonnya.second = engimonbaru;
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
                if (!peta.elementAt(i).get(j).equals("P") && !peta.elementAt(i).get(j).equals("X"))
                {
                    if (i >= xmax - dimensiWater || j < ymax - dimensiWater)
                    {
                        peta.elementAt(i).set(j, "-");
                    }
                    else
                    {
                        peta.elementAt(i).set(j, "0");
                    }
                }
            }
        }
        for (int a = 0; a < engimonLiar.size(); a++)
        {
            String arah = Direction.getRandomDirection().toString();
            if (arah.equals("North") && engimonLiar.elementAt(a).first.getYCoordinate() - 1 >= 0 && engimonLiar.elementAt(a).first.getYCoordinate() - 1 != playerPosition.getYCoordinate() && engimonLiar.elementAt(a).first.getYCoordinate() - 1 != activeEngimonPosition.getYCoordinate())
            {
                if (engimonLiar.elementAt(a).second.getElement().elementAt(0) == "Water" && engimonLiar.elementAt(a).second.getElement().elementAt(0) == "Ground")
                {
                    engimonLiar.elementAt(a).first.setYCoordinate(engimonLiar.elementAt(a).first.getYCoordinate() - 1);
                }
                else if (engimonLiar.elementAt(a).second.getElement().elementAt(0) == "Water" || engimonLiar.elementAt(a).second.getElement().elementAt(0) == "Ice")
                {
                    if (engimonLiar.elementAt(a).first.getXCoordinate() >= xmax - dimensiWater && engimonLiar.elementAt(a).first.getYCoordinate() < ymax - dimensiWater)
                    {
                        engimonLiar.elementAt(a).first.setYCoordinate(engimonLiar.elementAt(a).first.getYCoordinate() - 1);
                    }
                }
                else
                {
                    if (engimonLiar.elementAt(a).first.getYCoordinate() - 1 >= ymax - dimensiWater)
                    {
                        engimonLiar.elementAt(a).first.setYCoordinate(engimonLiar.elementAt(a).first.getYCoordinate() - 1);
                    }
                }
            }
            else if (arah.equals("South") && engimonLiar.elementAt(a).first.getYCoordinate() + 1 < ymax && engimonLiar.elementAt(a).first.getYCoordinate() + 1 != playerPosition.getYCoordinate() && engimonLiar.elementAt(a).first.getYCoordinate() + 1 != activeEngimonPosition.getYCoordinate())
            {
                if (engimonLiar.elementAt(a).second.getElement().elementAt(0) == "Water" || engimonLiar.elementAt(a).second.getElement().elementAt(0) == "Ice")
                {
                    if (engimonLiar.elementAt(a).first.getXCoordinate() >= xmax - dimensiWater && engimonLiar.elementAt(a).first.getYCoordinate() < ymax - dimensiWater && engimonLiar.elementAt(a).first.getYCoordinate() + 1 < ymax - dimensiWater)
                    {
                        engimonLiar.elementAt(a).first.setYCoordinate(engimonLiar.elementAt(a).first.getYCoordinate() + 1);
                    }
                }
                else
                {
                    engimonLiar.elementAt(a).first.setYCoordinate(engimonLiar.elementAt(a).first.getYCoordinate() + 1);
                }
            }
            else if (arah.equals("West") && engimonLiar.elementAt(a).first.getXCoordinate() - 1 >= 0 && engimonLiar.elementAt(a).first.getXCoordinate() - 1 != playerPosition.getXCoordinate() && engimonLiar.elementAt(a).first.getXCoordinate() - 1 != activeEngimonPosition.getXCoordinate())
            {
                if (engimonLiar.elementAt(a).second.getElement().elementAt(0) == "Water" || engimonLiar.elementAt(a).second.getElement().elementAt(0) == "Ice")
                {
                    if (engimonLiar.elementAt(a).first.getXCoordinate() >= xmax - dimensiWater && engimonLiar.elementAt(a).first.getYCoordinate() < ymax - dimensiWater && engimonLiar.elementAt(a).first.getXCoordinate() - 1 >= xmax - dimensiWater)
                    {
                        engimonLiar.elementAt(a).first.setXCoordinate(engimonLiar.elementAt(a).first.getXCoordinate() - 1);
                    }
                }
                else
                {
                    engimonLiar.elementAt(a).first.setXCoordinate(engimonLiar.elementAt(a).first.getXCoordinate() - 1);
                }
            }
            else if (arah.equals("East") && engimonLiar.elementAt(a).first.getXCoordinate() + 1 < xmax && engimonLiar.elementAt(a).first.getXCoordinate() + 1 != playerPosition.getXCoordinate() && engimonLiar.elementAt(a).first.getXCoordinate() + 1 != activeEngimonPosition.getXCoordinate())
            {
                if (engimonLiar.elementAt(a).second.getElement().elementAt(0) == "Water" && engimonLiar.elementAt(a).second.getElement().elementAt(0) == "Ground")
                {
                    engimonLiar.elementAt(a).first.setXCoordinate(engimonLiar.elementAt(a).first.getXCoordinate() + 1);
                }
                else if (engimonLiar.elementAt(a).second.getElement().elementAt(0) == "Water" || engimonLiar.elementAt(a).second.getElement().elementAt(0) == "Ice")
                {
                    if (engimonLiar.elementAt(a).first.getXCoordinate() >= xmax - dimensiWater && engimonLiar.elementAt(a).first.getYCoordinate() < ymax - dimensiWater)
                    {
                        engimonLiar.elementAt(a).first.setXCoordinate(engimonLiar.elementAt(a).first.getXCoordinate() + 1);
                    }
                }
                else
                {
                    if (engimonLiar.elementAt(a).first.getXCoordinate() + 1 < xmax - dimensiWater)
                    {
                        engimonLiar.elementAt(a).first.setXCoordinate(engimonLiar.elementAt(a).first.getXCoordinate() - 1);
                    }
                }
            }
            setAlphabet(engimonLiar.elementAt(a).second, engimonLiar.elementAt(a).first.getYCoordinate(), engimonLiar.elementAt(a).first.getXCoordinate());
        }
    }

    public void setAlphabet(Engimon engimonbaru, int coorY, int coorX)
    {
        if (engimonbaru.getElement().elementAt(0) == "Fire" && engimonbaru.getElement().elementAt(1) == "Fire")
        {
            peta.elementAt(coorY).set(coorX,"f");
        }
        else if (engimonbaru.getElement().elementAt(0) == "Water" && engimonbaru.getElement().elementAt(1) == "Water")
        {
            peta.elementAt(coorY).set(coorX,"w");
        }
        else if (engimonbaru.getElement().elementAt(0) == "Ice" && engimonbaru.getElement().elementAt(1) == "Ice")
        {
            peta.elementAt(coorY).set(coorX,"i");
        }
        else if (engimonbaru.getElement().elementAt(0) == "Ground" && engimonbaru.getElement().elementAt(1) == "Ground")
        {
            peta.elementAt(coorY).set(coorX,"g");
        }
        else if (engimonbaru.getElement().elementAt(0) == "Electric" && engimonbaru.getElement().elementAt(1) == "Electric")
        {
            peta.elementAt(coorY).set(coorX,"e");
        }
        else if (engimonbaru.getElement().elementAt(0) == "Fire" && engimonbaru.getElement().elementAt(1) == "Electric")
        {
            peta.elementAt(coorY).set(coorX,"l");
        }
        else if (engimonbaru.getElement().elementAt(0) == "Water" && engimonbaru.getElement().elementAt(1) == "Ice")
        {
            peta.elementAt(coorY).set(coorX,"s");
        }
        else if (engimonbaru.getElement().elementAt(0) == "Water" && engimonbaru.getElement().elementAt(1) == "Ground")
        {
            peta.elementAt(coorY).set(coorX,"n");
        }
    }

    public void removeEngimonLiar(Engimon engimon)
    {
        int index = 0;
        int xnya;
        int ynya;
        for (int i = 0; i < engimonLiar.size(); i++)
        {
            if (engimonLiar.elementAt(i).second == engimon)
            {
                index = i;
            }
        }
        xnya = engimonLiar.elementAt(index).first.getXCoordinate();
        ynya = engimonLiar.elementAt(index).first.getYCoordinate();
        if (xnya <= xmax - dimensiWater || ynya >= ymax - dimensiWater)
        {
            peta.elementAt(xnya).set(ynya,"-");
        }
        else
        {
            peta.elementAt(xnya).set(ynya,"0");
        }
        engimonLiar.remove(engimon);
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
                if (peta.elementAt(i).elementAt(j).equals("P"))
                {
                    if (i >= xmax - dimensiWater || j < ymax - dimensiWater)
                    {
                        peta.elementAt(i).set(j, "-");
                    }
                    else
                    {
                        peta.elementAt(i).set(j, "0");
                    }
                }
            }
        }
        peta.elementAt(_y).set(_x, "P");
    }

    public void setactiveEngimonPosition(int _x, int _y)
    {
        activeEngimonPosition.setXCoordinate(_x);
        activeEngimonPosition.setYCoordinate(_y);
        for (int i = 0; i < xmax; i++)
        {
            for (int j = 0; j < ymax; j++)
            {
                if (peta.elementAt(i).elementAt(j).equals("X"))
                {
                    if (i >= xmax - dimensiWater || j < ymax - dimensiWater)
                    {
                        peta.elementAt(i).set(j, "-");
                    }
                    else
                    {
                        peta.elementAt(i).set(j, "0");
                    }
                }
            }
        }
        peta.elementAt(_y).set(_x, "X");
    }
}
