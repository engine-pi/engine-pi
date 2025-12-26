package pacman.scenes;

import pi.actor.Image;

public enum Brick
{
    // corner
    CORNER_BL("R 0.5, 0.5, 0.5, 0.5"), CORNER_BR("R 0, 0.5, 0.5, 0.5"),
    CORNER_TL("R 0.5, 0, 0.5, 0.5"), CORNER_TR("R 0, 0, 0.5, 0.5"),
    // double
    DOUBLE_B("R 0, 0, 1, 0.5"), DOUBLE_L("R 0, 0, 0.5, 1"),
    DOUBLE_R("R 0.5, 0, 0.5, 1"), DOUBLE_T("R 0, 0.5, 1, 0.5"),
    // double corner
    DOUBLE_CORNER_BL("R0,0,0.5,1 & R0.5,0,0.5,0.5"),
    DOUBLE_CORNER_BR("R0,0,0.5,0.5 & R0.5,0,0.5,1"),
    DOUBLE_CORNER_TL("R0,0,0.5,0.5 & R0,0.5,1,0.5"),
    DOUBLE_CORNER_TR("R0.5,0,0.5,0.5 & R0,0.5,1,0.5"), FILL("R0,0,1,1"),
    GATE(""),
    // outer corner
    OUTER_CORNER_BL("R 0.5, 0.5, 0.5, 0.5"),
    OUTER_CORNER_BR("R 0, 0.5, 0.5, 0.5"),
    OUTER_CORNER_TL("R 0.5, 0, 0.5, 0.5"), OUTER_CORNER_TR("R 0, 0, 0.5, 0.5"),
    // inner corner
    INNER_CORNER_BL("R0.5,0,0.5,0.5 & R0,0.5,1,0.5"),
    INNER_CORNER_BL_BORDER_T("R0.5,0,0.5,0.5 & R0,0.5,1,0.5"),
    INNER_CORNER_BL_BORDER_R("R0.5,0,0.5,0.5 & R0,0.5,1,0.5"),
    INNER_CORNER_BR("R0,0,0.5,0.5 & R0,0.5,1,0.5"),
    INNER_CORNER_BR_BORDER_T("R0,0,0.5,0.5 & R0,0.5,1,0.5"),
    INNER_CORNER_BR_BORDER_L("R0,0,0.5,0.5 & R0,0.5,1,0.5"),
    INNER_CORNER_TL("R0,0,1,0.5 & R0.5,0.5,0.5,0.5"),
    INNER_CORNER_TL_BORDER_R("R0,0,1,0.5 & R0.5,0.5,0.5,0.5"),
    INNER_CORNER_TR("R0,0,1,0.5 & R0,0.5,0.5,0.5"),
    INNER_CORNER_TR_BORDER_L("R0,0,1,0.5 & R0,0.5,0.5,0.5"),
    // single
    SINGLE_B("R 0, 0.5, 1, 0.5"), SINGLE_L("R 0.5, 0, 0.5, 1"),
    SINGLE_R("R 0, 0, 0.5, 1"), SINGLE_T("R 0, 0, 1, 0.5");

    private final String fixture;

    Brick(String fixture)
    {
        this.fixture = fixture;
    }

    public Image getImage()
    {
        Image image = new Image("images/maze/" + name() + ".png", 8);
        image.setFixtures(fixture);
        return image;
    }
}
