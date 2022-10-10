package modele.enums;

public enum NomVillesJaune {
    Bogota(8702000,21000),
    Buenos_Aires(13639000,5200),
    Johannesburg(3888000,2400),
    Khartoum(4897000,4500),
    Kinshasa(9046000,15500),
    Lagos(1547000,12700),
    Lima(9121000,14000),
    Los_Angeles(14900000,2400),
    Mexico(19463000,9500),
    Miami(5582000,1700),
    Santiago(6015000,6500),
    Sao_Paulo(20186000,6400);

    private int population;
    private int kmCarre;

    NomVillesJaune(int population, int kmCarre) {
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
