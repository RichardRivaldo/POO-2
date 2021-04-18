import java.util.*;

interface Breeding {
    public Engimon breed(Engimon thisEngimon, Engimon anotherEngimon);
}

public class Breed implements Breeding {
    public Engimon breed(Engimon thisEngimon, Engimon anotherEngimon) {
        Scanner scanner = new Scanner(System.in);

        Engimon engimonAnak = new Engimon();
        if (thisEngimon.getElement() != anotherEngimon.getElement()) {
            return engimonAnak;
        } else if (thisEngimon.getLevel() < 1 || anotherEngimon.getLevel() < 1) {
            return engimonAnak;
        } else {
            Engimon engimon1tmp = thisEngimon;
            Engimon engimon2tmp = anotherEngimon;

            System.out.print("Masukkan nama Engimonmu : ");
            String nama = scanner.nextLine();

            Engimon tmp = new Engimon(nama, thisEngimon.getSpecies(), thisEngimon.getElement());

            ArrayList<String> parents = new ArrayList<String>();
            parents.add(thisEngimon.getName());
            parents.add(anotherEngimon.getName());

            engimonAnak.setParents(parents);

            // for (int i = 0; i < thisEngimon.getSkill().size(); i++) {

            // }
        }

        return engimonAnak;
    }
}
