package thelaboflieven;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Lieven on 26-12-2016.
 */
public class LabOrder {
    public String ordernr;//PK.
    public Date insertts;
    public String patientnr;
    public String visitNr;
    public List<Labspecimen> specimens = new ArrayList<>();
    public String toCsv(String sep)
    {
        return insertts.getTime()/1000 + sep  + ordernr + sep + patientnr + sep + visitNr;
    }
}
