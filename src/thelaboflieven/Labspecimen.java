package thelaboflieven;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lieven on 26-12-2016.
 */
public class Labspecimen {
    public String specimennr;
    public String ordernr;
    public List<LabResult> labanalysises = new ArrayList<>();

    public String toCsv(String sep)
    {
        return specimennr + sep + ordernr;
    }

}
