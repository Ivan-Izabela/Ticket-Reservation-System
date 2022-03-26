package rest;

import ft.domain.Cursa;
import ft.services.rest.ServiceException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;

public class CurseClient {
    public static final String URL = "http://localhost:8080/ft/curse";

    private RestTemplate restTemplate = new RestTemplate();
    private <T> T execute(Callable<T> callable) {
        try {
            return callable.call();
        } catch (ResourceAccessException | HttpClientErrorException e) { // server down, resource exception
            throw new ServiceException(e);
        } catch (Exception e) {
            throw new ServiceException(e);
        }


    }

    public Cursa[] getAll() {
        return execute(() -> restTemplate.getForObject(URL, Cursa[].class));
    }

    public Cursa getById(String id) {
        return execute(() -> restTemplate.getForObject(String.format("%s/%s", URL, id), Cursa.class));
    }

    public Cursa create(Cursa cursa) {
        return execute(() -> restTemplate.postForObject(URL, cursa, Cursa.class));
    }

    public void update(Cursa cursa) {
        execute(() -> {
            restTemplate.put(String.format("%s/%s", URL, cursa.getId()), cursa);
            return null;
        });
    }

    public void delete(Integer id) {
        execute(() -> {
            restTemplate.delete(String.format("%s/%s", URL, id));
            return null;
        });
    }
}
