package thelaboflieven;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Lieven on 26-12-2016.
 */
public class LabOrderGenerator {

    public static List<Labanalysiscode> codes = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        if (args.length != 1)
        {
            System.err.println("Invalid number of arguments: " + args.length);
            System.err.println("Usage: <LabOrderGenerator> nrOrders ");
            System.exit(1);
        }
        String nrOfOrdersString = args[0];
        long numberOfOrders = Long.parseLong(nrOfOrdersString);
        LabOrderGenerator gen = new LabOrderGenerator();
        gen.storeGeneratedOrders(numberOfOrders);
    }

    public void storeGeneratedOrders(long numberOrders) throws IOException {
        boolean printheaders = false;
        generateCodes();
        FileWriter writer = new FileWriter(new File("labordercodes.csv"));
        String sep = ",";
        if (printheaders)
            writer.write("pk" + sep + "analysecode" + sep + "analysiscodeversion" + sep + "representation" + sep + "referenceValue" + sep + "unit" + "\n");
        for (Labanalysiscode code : codes)
        {
            writer.write(code.toCsv(sep) + "\n");
        }
        writer.close();
        FileWriter laborderwriter = new FileWriter(new File("laborder.csv"));
        FileWriter labspecimenWriter = new FileWriter(new File("labspecimen.csv"));
        FileWriter labresultWriter = new FileWriter(new File("labresult.csv"));

        if (printheaders){
            laborderwriter.write("insertts" + sep  + "ordernr" + sep + "patientnr" + sep + "visitNr" + "\n");
            labspecimenWriter.write("specimennr" + sep + "ordernr" + "\n");
            labresultWriter.write("result" + sep + "deleted" + sep +  "code.pk" + sep + "inserttimestamp" + sep + "specimennr" + sep + "executiontimestamp"+ "\n");
        }

        for (int i = 0; i < numberOrders; i++)
        {
            LabOrder order = generateOrder();
            laborderwriter.write(order.toCsv(sep) + "\n");
            for (Labspecimen specimen : order.specimens)
            {
                labspecimenWriter.write(specimen.toCsv(sep) + "\n");

                for (LabResult result : specimen.labanalysises)
                {
                    labresultWriter.write(result.toCsv(sep) + "\n");
                }
            }
        }
        laborderwriter.close();
        labspecimenWriter.close();
        labresultWriter.close();
    }


    /**
     * LabOrder :- 1 Mrows data. Each laborder is connected to two labspecimens.
     * LabSpecimen :- 2MRows data.
     * LabResult :- For most labspecimens we generate random between [1-4] labresults.
     * LabResultCode :- For each labresult there is a labcode. 1 of random 100.
     *
     * @return
     */

    public LabOrder generateOrder() {
        LabOrder order = new LabOrder();
        Random r = new Random();
        Date date = getDate(r);
        order.insertts = date; //randbetween 1388534400 <-> 1420070400
        order.ordernr = "O" + r.nextInt();
        order.patientnr = "P" + r.nextInt(10000); //1 mil / 100 = 10 000
        order.visitNr = "V" + r.nextInt(); //unique;
        order.specimens.add(generateSpecimen(order));
        order.specimens.add(generateSpecimen(order));
        return order;
    }

    private Date getDate(Random r) {
        long num = r.nextInt(1420070400 - 1388534400);
        return new Date((1388534400 + num)*1000);
    }

    public Labspecimen generateSpecimen(LabOrder order) {
        Labspecimen specimen = new Labspecimen();
        Random r = new Random();
        specimen.ordernr = order.ordernr;
        specimen.specimennr = "S" + r.nextInt();
        for (int i = 0; i < r.nextInt(4); i++)
        {
            specimen.labanalysises.add(generateResult(specimen));
        }
        return specimen;
    }

    public LabResult generateResult(Labspecimen specimen) {
        LabResult result = new LabResult();
        Random r = new Random();
        result.inserttimestamp = getDate(r);
        result.executiontimestamp = getDate(r);
        result.deleted = r.nextInt() % 2 == 0;
        result.result = "R" + r.nextLong();
        result.code = codes.get(r.nextInt(100));
        result.specimennr = specimen.specimennr;
        return result;
    }

    public static void generateCodes()
    {
        Random r = new Random();

        for (int i = 0; i < 100; i++)
        {
            Labanalysiscode code = new Labanalysiscode();
            code.pk = i;
            code.analysecode = "C" + i;
            code.analysiscodeversion = 1;
            code.referenceValue = "REF" + r.nextInt();
            code.representation = "REPRE" + i;
            code.unit = "mg/"+i;
            codes.add(code);
        }

    }

}
