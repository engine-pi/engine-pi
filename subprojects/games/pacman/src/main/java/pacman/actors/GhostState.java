package pacman.actors;

public enum GhostState
{
    STAND("right"), UP("up"), LEFT("left"), RIGHT("right"), DOWN("down");

    private final String fileName;

    GhostState(String fileName)
    {
        this.fileName = fileName;
    }

    public String getImagePath(int no)
    {
        return "images/ghosts/Blinky_" + fileName + "-" + no + ".png";
    }
}
