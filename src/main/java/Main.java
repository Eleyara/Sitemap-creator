
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;

public class Main {

    private static String siteLink = "https://metanit.com/";
    private static String sourceFile = "src/main/data/source.html";
    private static String siteMapFile = "src/main/data/siteMap.txt";

    public static void main(String[] args) {

        SitePage page = new SitePage(siteLink, 0);
        System.out.println(siteLink.substring(siteLink.length() - 1).equals("/") ? siteLink : siteLink.concat("/"));
        String siteMap = new ForkJoinPool().invoke(new SiteMapCreator(page));
        //System.out.println(siteMap);
        writeSiteMapToFile(siteMap, siteMapFile);

    }

    public static void writeSiteMapToFile(String siteMap, String filePath) {
        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(siteMap);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
