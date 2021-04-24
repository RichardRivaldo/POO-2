import java.util.Arrays;
import java.util.Scanner;

public class Main {
    Engimon initializeStarterEngimon(vector<Skill> fireSkills, vector<Skill> waterSkills, 
    vector<Skill> electricSkills, vector<Skill> groundSkills, vector<Skill> iceSkills){
        string nama;
        int pilihan;

        Scanner sc = new Scanner();

        System.out.println("Pilih Engimon kamu (1 - 5): ");
        System.out.println("1. Firemon");
        System.out.println("2. Watermon");
        System.out.println("3. Electromon");
        System.out.println("4. Groundmon");
        System.out.println("5. Icemon");

        while (true)
        {
            pilihan = sc.nextInt();
            if (pilihan == 1)
            {
                System.out.println("Masukkan nama Firemon mu : ");
                nama = sc.nextLine();
                Engimon starterEngimon = new Engimon(nama, "Firemon", new ArrayList<String>(Arrays.asList("FIRE")));
                starterEngimon.AddSkill(fireSkills[0]);
                return starterEngimon;
            }
            else if (pilihan == 2)
            {
                System.out.println("Masukkan nama Watermon mu : ");
                mama = sc.nextLine();
                Engimon starterEngimon = new Engimon(nama, "Watermon", new ArrayList<String>(Arrays.asList("WATER")));
                starterEngimon.AddSkill(waterSkills[0]);
                return starterEngimon;
            }
            else if (pilihan == 3)
            {
                System.out.println("Masukkan nama Electricmon mu : ");
                nama = sc.nextLine();
                Engimon starterEngimon = new Engimon(nama, "Electromon", new ArrayList<String>(Arrays.asList("ELECTRIC")));
                starterEngimon.AddSkill(electricSkills[0]);
                return starterEngimon;
            }
            else if (pilihan == 4)
            {
                System.out.println("Masukkan nama Groundmon mu : ");
                nama = sc.nextLine();
                Engimon starterEngimon = new Engimon(nama, "Groundmon", new ArrayList<String>(Arrays.asList("GROUND")));
                starterEngimon.AddSkill(groundSkills[0]);
                return starterEngimon;
            }
            else if (pilihan == 5)
            {
                System.out.println("Masukkan nama Icemon mu : ");
                nama = sc.nextLine();
                Engimon starterEngimon = new Engimon(nama, "Icemon", new ArrayList<String>(Arrays.asList("ICE")));
                starterEngimon.AddSkill(iceSkills[0]);
                return starterEngimon;
            }
            else
            {
                System.out.println("Masukan tidak valid, coba lagi");
            }
        }
    }
    public static void main(String[] args) {
        
    }
}