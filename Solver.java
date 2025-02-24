/*
    Esta es su clase principal. El unico metodo que debe implementar es
    public String[] solve(Maze maze)
    pero es libre de crear otros metodos y clases en este u otro archivo que desee.
*/
public class Solver{


    String bestpath;
    int bestpath_length;

    public Solver(){
        //Sientase libre de implementar el contructor de la forma que usted lo desee
    }

    public String solve(Maze maze){
        //Implemente su metodo aqui. Sientase libre de implementar métodos adicionales

        bestpath = null; 
        bestpath_length = 100000; //empezamos en cienmil porque es nuestro límite superior

        int height = maze.getHeight();
        int width = maze.getWidth();
        int depth = maze.getDepth();

        // vamos a crear una matriz tridimensional booleana para marcar cada parámetro de la cueva (x, y, z) nodo = camara
        boolean [][][] visited = new boolean[width][height][depth];

        Node start = maze.getStartingSpace();

        // Inicializamos el Depth first search dfs

        dfs(maze, start, "", 0, visited);
        
        if (bestpath != null) {
            return bestpath; //retornamos la mejor ruta
        } else {
            return "[]"; //porque todavía no encuentra salida 
        }
        
    }

    //metodo privado encapsulado porque así evitamos que sea modificada afuera de la clase 
    private void dfs(Maze maze, Node current, String path, int steps, boolean[][][] visited) {
    
    //Si hay peligro en este nodo abortamos de este
    if(current.danger){
        return;
    }
    
    // Si el camino actual es igual o mayor que el mejor encontrado no continuar mejor
    if(steps >= bestpath_length){
        return; 
    }

    //Si llegamos a la salida actualizamos cual es la mejor ruta
    if (current.isExit) {
        bestpath_length = steps;


    // un "tostring" para evitar comas al final de la cadena ej: E,S,U,
    String formatted;
    if (path.endsWith(",")) {
    formatted = path.substring(0, path.length() - 1);
    } else {
    formatted = path;
    }

    bestpath = "[" + formatted + "]";
    return;
    }

    // Marcar el nodo actual como ya visitado 
    visited[current.xIndex][current.yIndex][current.zIndex] = true; 

    /* exploramos direcciones en orden dado (E - S - U - N - W - D) */
    // ESTE - SUR - UP - NORTE - WEST - DOWN


//*************************************************************************************/


    // 1. ESTE "E"
    if(current.east){
        Node next = maze.moveEast(current);
        if(!visited[next.xIndex][next.yIndex][next.zIndex] && !next.danger){
            dfs(maze, next, path + "E,",steps+1, visited); //verificación e iterador 

        }
    }

    // 2. SUR "S"
    if(current.south){
        Node next = maze.moveSouth(current);
        if(!visited[next.xIndex][next.yIndex][next.zIndex] && !next.danger){
            dfs(maze, next, path + "S,",steps+1,visited);

        }
    }    

    // 3. ARRIBA "U"
    if (current.up) {
        Node next = maze.moveUp(current);
        if (!visited[next.xIndex][next.yIndex][next.zIndex] && !next.danger) {
            dfs(maze, next, path + "U,", steps + 1, visited);
        }
    }
    // 4. Norte "N"
    if (current.north) {
        Node next = maze.moveNorth(current);
        if (!visited[next.xIndex][next.yIndex][next.zIndex] && !next.danger) {
            dfs(maze, next, path + "N,", steps + 1, visited);
        }
    }

    // 5. Oeste "W" OJO Se usa "W" en lugar de "O"
    if (current.west) {
        Node next = maze.moveWest(current);
        if (!visited[next.xIndex][next.yIndex][next.zIndex] && !next.danger) {
            dfs(maze, next, path + "W,", steps + 1, visited);
        }
    }

    // 6. Abajo "D"
    if (current.down) {
        Node next = maze.moveDown(current);
        if (!visited[next.xIndex][next.yIndex][next.zIndex] && !next.danger) {
            dfs(maze, next, path + "D,", steps + 1, visited);
        }
    }

    // backtraquing que desmarca el nodo actual para permitir que otras ramas lo visiten.
    visited[current.xIndex][current.yIndex][current.zIndex] = false;





    }


}




