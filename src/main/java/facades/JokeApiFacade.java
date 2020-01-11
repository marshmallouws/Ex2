/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.Gson;
import dtos.JokeDTO;
import entities.Category;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author Annika
 */
public class JokeApiFacade {


    private static JokeApiFacade instance;
    private static EntityManagerFactory emf;
    private ExecutorService executor
            = Executors.newFixedThreadPool(16);

    public static JokeApiFacade getJokeApiFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            instance = new JokeApiFacade();
            emf = _emf;
        }
        return instance;
    }


    public JokeDTO getJokes(String categories) throws InterruptedException, ExecutionException {
        String[] c = categories.split(",");
        Gson gson = new Gson();

        List<Future<String>> futures = new ArrayList<>();

        for (String s : c) {
            Future<String> fut = executor.submit(new Jokes(s));
            futures.add(fut);
        }

        class ChuckNorrisResponse {
            String[] categories;
            String value;
        }
        
            /*
    TODO: make api for returning data in the right format
    */
    
        /*{ 
   "jokes":[ 
      { "category":"food", "joke":"When Chuck Norris goes to out to eat...."},
      { "category":"fashion","joke":"Chuck Norris does not follow fashion trends..."},
      { "category":"history","joke":"After returning from World War 2 unscathed..." }
   ]
   "Reference" : "api.chucknorris.io"

}
     */

        for (Future<String> f : futures) {
            gson.fromJson(f.get(), ChuckNorrisResponse.class);
        }

        executor.shutdown();

        return null;
    }

    class Jokes implements Callable<String> {

        String category;

        Jokes(String category) {
            this.category = category;
        }

        @Override
        public String call() throws Exception {
            URL url = new URL("https://api.chucknorris.io/jokes/random?category=" + category);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json;charset=UTF-8");
            con.setRequestProperty("User-Agent", ""); // chuck norris api requires user-agent
            Scanner scan = new Scanner(con.getInputStream());
            String jsonStr = null;
            if (scan.hasNext()) {
                jsonStr = scan.nextLine();
            }
            scan.close();
            return jsonStr;
        }

    }

    public static void main(String[] args) throws InterruptedException {
        EntityManagerFactory emf2 = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
        try {
            JokeApiFacade.getJokeApiFacade(emf2).getJokes("food,food,food,food");
        } catch (ExecutionException ex) {
            Logger.getLogger(JokeApiFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
