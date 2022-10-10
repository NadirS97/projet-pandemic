package modele.enums;

public enum NomVillesNoir {

    Alger(2946000,6500),
    Bagdad(6204000,10400),
    Calcutta(14374000,1900),
    Chennai(8965000,14600),
    Delhi(22242000,1500),
    Istanbul(13576000,9700),
    Karachi(2071000,25800),
    Le_Caire(14718000,8900),
    Moscou(15512000,3500),
    Mumbai(1690000,30900),
    Riyad(5037000,3400),
    Teheran(7419000,9500);

    private int population;
    private int kmCarre;

    NomVillesNoir(int population, int kmCarre) {
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
