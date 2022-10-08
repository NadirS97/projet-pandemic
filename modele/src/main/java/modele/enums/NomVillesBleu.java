package modele.enums;

public enum NomVillesBleu {

    Atlanta(47500,700),
    Chicago(400,41),
    Essen(100,24);
//    Londres,
//    Madrid,
//    Milan,
//    Montréal,
//    Paris,
//    Saint_Pétersbourg,
//    San_Francisco,
//    New_York,
//    Washington;

    private int population;
    private int kmCarre;

    NomVillesBleu(int population,int kmCarre) {
        this.population = population;
        this.kmCarre = kmCarre;
    }

    public int getPopulation() {
        return population;
    }

    public int getKmCarre() {
        return kmCarre;
    }
}
