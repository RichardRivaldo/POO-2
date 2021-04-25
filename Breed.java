import java.util.*;

interface Breeding {
    public Engimon breed(Engimon thisEngimon, Engimon anotherEngimon);
}

public class Breed implements Breeding {
    public static boolean containsSkill(ArrayList<Skill> listSkill, String skill) {
        for (Skill elmt : listSkill) {
            if (elmt.getSkillName() == skill) {
                return true;
            }
        }
        return false;
    }

    public Engimon breed(Engimon thisEngimon, Engimon anotherEngimon) {
        Scanner scanner = new Scanner(System.in);

        Engimon engimonAnak = new Engimon();
        if (thisEngimon.getElement() != anotherEngimon.getElement()) {
            return engimonAnak;
        } else if (thisEngimon.getLevel() < 3 || anotherEngimon.getLevel() < 3) {
            return engimonAnak;
        } else {
            Engimon engimon1tmp = thisEngimon;
            Engimon engimon2tmp = anotherEngimon;

            System.out.print("Masukkan nama Engimonmu : ");
            String nama = scanner.nextLine();

            Engimon tmp = new Engimon(nama, thisEngimon.getSpecies(), thisEngimon.getElement());

            thisEngimon.setLevel(thisEngimon.getLevel() - 3);
            anotherEngimon.setLevel(anotherEngimon.getLevel() - 3);

            ArrayList<String> parents = new ArrayList<String>();
            parents.add(thisEngimon.getName());
            parents.add(anotherEngimon.getName());

            engimonAnak = tmp;

            engimonAnak.setParents(parents);

            for (int i = 0; i < thisEngimon.getSkill().size(); i++) {
                Skill highest1 = engimon1tmp.getHighestMastery();
                Skill highest2 = engimon2tmp.getHighestMastery();

                if (highest1.getSkillMastery() > highest2.getSkillMastery()) {
                    if (containsSkill(engimon2tmp.getSkill(), highest1.getSkillName())) {
                        engimonAnak.addSkill(highest1);
                        engimon1tmp.removeSkill(highest1.getSkillName());
                        engimon2tmp.removeSkill(highest2.getSkillName());
                    } else {
                        engimonAnak.addSkill(highest1);
                        engimon2tmp.removeSkill(highest1.getSkillName());
                    }
                } else if (highest1.getSkillMastery() < highest2.getSkillMastery()) {
                    if (containsSkill(engimon1tmp.getSkill(), highest2.getSkillName())) {
                        engimonAnak.addSkill(highest2);
                        engimon1tmp.removeSkill(highest1.getSkillName());
                        engimon2tmp.removeSkill(highest2.getSkillName());
                    } else {
                        engimonAnak.addSkill(highest2);
                        engimon2tmp.removeSkill(highest2.getSkillName());
                    }
                } else {
                    if (highest1.getSkillName() == highest2.getSkillName()) {
                        Skill tmpSkill = highest1;
                        if (tmpSkill.getSkillMastery() < 3) {
                            tmpSkill.masteryLevelUp(highest1.getSkillPower());
                        }
                        engimonAnak.addSkill(tmpSkill);
                        engimon1tmp.removeSkill(highest1.getSkillName());
                        engimon2tmp.removeSkill(highest2.getSkillName());
                    } else {
                        engimonAnak.addSkill(highest1);
                        engimon1tmp.removeSkill(highest2.getSkillName());
                    }
                }
            }
        }

        return engimonAnak;
    }
}
