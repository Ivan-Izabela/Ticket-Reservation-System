package start;


import ft.domain.Cursa;
import ft.services.rest.ServiceException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import rest.CurseClient;


import java.time.LocalDateTime;


public class StartRestClient {
    private final static CurseClient curseClient=new CurseClient();
    public static void main(String[] args) {
        RestTemplate restTemplate=new RestTemplate();
        Cursa cursaT=new Cursa("test123","2020-06-06",18);
        try{


            show(()-> System.out.println(curseClient.create(cursaT)));

            show(()->{
                Cursa[] res=curseClient.getAll();
                for(Cursa c:res){
                    System.out.println(c.getId()+": "+c.getDestinatie());
                }
            });
            show(()->{
                Cursa cursaU=curseClient.getById("11");
                cursaU.setDestinatie("1234");
                curseClient.update(cursaU);
                Cursa[] res=curseClient.getAll();
                for(Cursa c:res){
                    System.out.println(c.getId()+": "+c.getDestinatie());
                }
            });
            show(()->{
                curseClient.delete(34);
                Cursa[] res=curseClient.getAll();
                for(Cursa c:res){
                    System.out.println(c.getId()+": "+c.getDestinatie());
                }
            });




        }catch(RestClientException ex){
            System.out.println("Exception ... "+ex.getMessage());
        }

    }



    private static void show(Runnable task) {
        try {
            task.run();
        } catch (ServiceException e) {
          //  LOG.error("Service exception", e);
            System.out.println("Service exception"+ e);
        }
    }
}
