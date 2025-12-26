package blockly_robot.robot.gui;

public class Color extends java.awt.Color
{
    /**
     * {@inheritDoc}
     */
    public Color(int r, int g, int b)
    {
        super(r, g, b);
    }

    public Color(Color c)
    {
        super(c.getRed(), c.getGreen(), c.getBlue());
    }

    private static int toInt(String hex, int start)
    {
        return Integer.valueOf(hex.substring(start, start + 2), 16);
    }

    public static Color fromHex(String hex)
    {
        if (hex.charAt(0) == '#')
        {
            hex = hex.substring(1);
        }
        return new Color(toInt(hex, 0), toInt(hex, 2), toInt(hex, 4));
    }

    public Color(String hexCode)
    {
        this(Color.fromHex(hexCode));
    }
}
