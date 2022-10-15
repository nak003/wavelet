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
                return parameters[1] + " is now added to search engine";
            }
            if (url.getPath().contains("search")){
                ArrayList <String> resultList = new ArrayList<String>();
                for (int i = 0; i < list.size(); i++){
                    if (list.get(i).contains(parameters[1])){
                        resultList.add(list.get(i));
                    }
                }

                if (resultList.size()!=0){
                    String result;
                    result = returnArrayList(resultList);
                    return result; 
                }
                
                else {
                    return "No words in search engine with " + parameters[1];
                }
            }
        }
        return "404 Not Found!";
    }

    public String returnArrayList(ArrayList<String> list){
        String result = "";
        for (int i=0; i<list.size();i++){
            result = result + list.get(i) + " ";
        }
        return result; 
    }
}


class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new SearchHandler());
    }
}
