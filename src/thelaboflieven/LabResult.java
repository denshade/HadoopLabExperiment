package thelaboflieven;

import java.util.Date;

/**
 * Created by Lieven on 26-12-2016.
 */
public class LabResult
{
    public String result;
    public boolean deleted;
    public String specimennr;
    public Labanalysiscode code;
    public Date inserttimestamp;
    public Date executiontimestamp;

    public String toCsv(String sep)
    {
        return result + sep + deleted + sep +  code.pk + sep + inserttimestamp.getTime()/1000 + sep + specimennr + sep + executiontimestamp.getTime()/1000;
    }
}
