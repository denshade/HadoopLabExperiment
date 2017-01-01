package thelaboflieven;

/**
 * Created by Lieven on 26-12-2016.
 */
public class Labanalysiscode
{
    public int pk;
    public String analysecode;
    public int analysiscodeversion;
    public String representation;
    public String referenceValue;
    public String unit;

    public String toCsv(String sep)
    {
        return pk + sep + analysecode + sep + analysiscodeversion + sep + representation + sep + referenceValue + sep + unit;
    }
}
