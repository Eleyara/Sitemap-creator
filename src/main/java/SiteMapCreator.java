import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

public class SiteMapCreator extends RecursiveTask<String> {
    private SitePage sitePage;

    public SiteMapCreator(SitePage sitePage){
        this.sitePage = sitePage;
    }

    @Override
    protected String compute() {
        StringBuilder builder = new StringBuilder();
        builder.append(sitePage.getSiteLink());
        ArrayList<SiteMapCreator> tasks = new ArrayList<>();

        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
        for(String childLink : sitePage.getChildrenLinks()){
            SiteMapCreator task = new SiteMapCreator(new SitePage(childLink, sitePage.getLevelOfSitePage() + 1));
            task.fork();
            tasks.add(task);
        }

        for(SiteMapCreator task : tasks){
            builder.append(task.join());
        }

        return builder.toString();
    }
}
