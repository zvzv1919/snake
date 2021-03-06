import javax.swing.*;
import java.util.Random;

public class Map {

    private char[][] map;
    private Snake snake;
    private GamePanel gamePanel;
    private Node apple;

    //'*' represents snake; '!' represents apple; '|' represents walls
    //x direction: |-->; y direction: 丁
    //!!! Must set Snake.map in each constructor.
    //!!! Must initialize the snake in each constructor.
    //Map(Snake, GamePanel): Generates an empty map that contains only the snake.
    //Map(GamePanel): Generates an empty map with a default snake.
    //blank(int[][]): set every entry of the passed array to ' '.
    //printMap: print the current Map on the screen(visualize).<-- For early exps, now obsolete
    //updateMap: update the map to cover changes in 'snake'. Also repaints the panel.(visualize)
    //generateApple: generates an apple(Node) at a random place in map that doesn't coincide the snake.


    public Map(Snake snake1, GamePanel gamePanel) {
        snake = snake1;
        this.gamePanel = gamePanel;
        Node node = snake.getHead();
        if(node == null){
            new ErrorHandler(Error.NULL_HEAD);
        }
        map = new char[gamePanel.getWidth() / 10][gamePanel.getHeight() / 10];
        blank(map);
        do{
            map[node.getPos_x()][node.getPos_y()] = '*';
            node = node.next;
        }while(node != null);
        snake.setMap(this);
    }
    public Map(GamePanel gamePanel){
        this(new Snake(), gamePanel);
    }
    private void blank(char[][] map){
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                map[i][j] = ' ';
            }
        }
    }
    public void printMap(){
        if(map == null){
            new ErrorHandler(Error.NULL_MAP);
        }
        int i = 0;
        int j = 0;
        do{
            i = 0;
            System.out.printf("\n");
            do{
                System.out.printf("%c", map[i][j]);
                i++;
            }while(i < map.length);

            j++;
        } while (j < map[0].length);
        System.out.printf("\n");
    }
    public void updateMap(){
        blank(map);
        Node node = snake.getHead();
        do{
            map[node.getPos_x()][node.getPos_y()] = '*';
            node = node.next;
        }while(node != null);
        map[apple.pos_x][apple.pos_y] = '!';

        //panel.printMap(map);
        gamePanel.repaint();
    }
    public void generateApple(){
        Random r = new Random();
        int x;
        int y;
        do{
            x = r.nextInt(getLength());
            y = r.nextInt(getHeight());
            apple = new Node(x, y, null, null);
        }while(coincidence());
    }
    public boolean coincidence(){
        Node node = snake.getHead();
        do{
            if(node.coincide(apple)){
                return true;
            }
            node = node.next;
        }while (node != null);
        return false;
    }
    public boolean eats(){
        Node node = snake.nextNode();
        if(node.coincide(apple)){
            return true;
        }
        else {
            return false;
        }
    }


    public Snake getSnake() {
        return snake;
    }
    public char[][] getMap() {
        return map;
    }
    public int getHeight() {
        return map[0].length;
    }
    public int getLength() {
        return map.length;
    }
}

