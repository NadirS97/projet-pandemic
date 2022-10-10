package modele.enums;

public enum NomVillesRouge {
    Bangkok(7151000,3200),
    Hô_chi_minh_ville(834000,9900),
    Hong_kong(7106000,25900),
    Jakarta(26063000,9400),
    Manille(20767000,14400),
    Osaka(2871000,19000),
    Pékin(17311000,5000),
    Séoul(22547000,10400),
    Shangai(13482000,2200),
    Sydney(3785000,2100),
    Taipei(8338000,7300),
    Tokyo(13189000,6030);


    private int population;
    private int kmCarre;

    NomVillesRouge(int population, int kmCarre) {
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
