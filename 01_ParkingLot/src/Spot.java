class Spot {
    int id;
    SpotType spotType;
    boolean occupied;

    public Spot(int id, SpotType spotType,boolean occupied) {
        this.id = id;
        this.spotType = spotType;
        this.occupied = occupied;
    }
}
