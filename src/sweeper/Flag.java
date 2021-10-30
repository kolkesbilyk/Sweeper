package sweeper;

class Flag {
    private Matrix flagMap;
    private int countOfCloseBoxes;
    void start(){
        flagMap = new Matrix(Box.CLOSED);
        countOfCloseBoxes = Ranges.getSize().x * Ranges.getSize().y;
    }
    Box get(Coord coord){
        return flagMap.get(coord);
    }

    void setOpenedToBox(Coord coord) {
        flagMap.set(coord, Box.OPENED);
        countOfCloseBoxes--;
    }

    void setFlagToBox(Coord coord) {
        flagMap.set(coord, Box.FLAGED);
    }
    void setClosedToBox(Coord coord) {
        flagMap.set(coord, Box.CLOSED);
    }

    public void toggleFlagToBox(Coord coord) {
        switch (flagMap.get(coord)){
            case FLAGED: setClosedToBox(coord); break;
            case CLOSED: setFlagToBox(coord); break;
        }
    }

    int getCountOfClosedBoxes() {
        return countOfCloseBoxes;
    }
    public void setBombedToBox(Coord coord){
        flagMap.set(coord, Box.BOMBED);
    }

    public void setOpenedToClosedBonBox(Coord coord) {
        if (flagMap.get(coord) == Box.BOMBED){
            flagMap.set(coord, Box.OPENED);
        }
    }

    public void setNoBombToFlagedSaveBox(Coord coord) {
        if (flagMap.get(coord) == Box.FLAGED){
            flagMap.set(coord, Box.BOMBED);
        }
    }

    int getCountOfFlagedBoxesAround(Coord coord) {
        int count = 0;
        for (Coord around: Ranges.getCoordAround(coord)){
            if (flagMap.get(around) == Box.FLAGED) count++;
        }
        return count;
    }
}
