import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class SearchHandler implements URLHandler {
    // The one bit of state on the server: string can be added and searched by
    // various requests.
    ArrayList <String> list = new ArrayList<String>();

    public String handleRequest(URI url) {
        String[] parameters = url.getQuery().split("=");
        if (parameters[0].equals("s")) {
            if (url.getPath().contains("/add")) {
                list.add(parameters[1]);
                return parameters[1] + "is now added to search engine";
            }
            if (url.getPath().contains("search")){
                ArrayList <String> result = new ArrayList<String>();
                for (int i = 0; i < list.size(); i++){
                    if (list.get(i).contains(parameters[1])){
                        result.add(list.get(i));
                    }
                }

                if (result.size()!=0){
                    /* 
                    int notprinted = result.size(); 
                    int x=0;
                    for (int j =0; j< result.size(); j++ ){
                        x += 1;
                        while (notprinted!=0){
                            notprinted -=1; 
                            return result.get(x);
                        }
                    }
                   */
                }

                else {
                    return "No words in search engine with " + parameters[1];
                }
            }
        }
        return "404 Not Found!";
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
