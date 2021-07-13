import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.HttpsURLConnection;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;

public class SitePage {

    private String siteLink = null;
    private int levelOfSitePage;
    private HashSet<String> childrenLinks;

    SitePage(String siteLink, int levelOfSitePage){
        this.siteLink = siteLink;
        this.levelOfSitePage = levelOfSitePage;
        childrenLinks = new HashSet<>();
        try {
            Thread.sleep(150);
            Document doc = Jsoup.connect(this.siteLink).get();
            //File input = new File(sourceFile);//
            //Document doc = Jsoup.parse(input, "UTF-8", siteLink);
            int parentSiteStartIndex = siteLink.indexOf("://") + 3;
            String parentSite = this.siteLink.substring(parentSiteStartIndex);
            Elements elements = doc.select("a");
            for (Element element : elements) {
                String child = element.attr("abs:href");
                if(child.isBlank()
                        || child.equals(siteLink)
                        || !child.contains(parentSite)
                        || child.contains("#")){
                    continue;
                }

                if(child.substring(child.length() - 1).equals("/")) {
                    childrenLinks.add(child);
                }

            }
        }
        catch(Exception ex){
            System.out.println("Ошибка на сайте: " + siteLink);
            ex.printStackTrace();
        }
    }

    public HashSet<String> getChildrenLinks() {
        return childrenLinks;
    }

    public int getLevelOfSitePage() {
        return levelOfSitePage;
    }

    public String getSiteLink() {
        StringBuilder builder = new StringBuilder();
        if(levelOfSitePage > 0){
            builder.append("\n");
        }
        for(int i = 0; i < levelOfSitePage; i++){
            builder.append("\t");
        }
        builder.append(siteLink);
        return builder.toString();
    }

    public void printChildren(){
        if(childrenLinks == null){
            System.out.println("Нет ссылок на сайты");
        }
        else{
            childrenLinks.stream().forEach(System.out::println);
        }
    }

}
