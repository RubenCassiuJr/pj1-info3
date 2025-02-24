public class Solverprobado {

    // Variables globales donde metemos la mejor ruta y su longitud.
    private String bestPath;
    private int bestPathLength;

    public Solverprobado() {
        // Constructor (no requiere inicialización especial)
    }

    public String solve(Maze maze) {
        // reiniciar variables para cda test
        bestPath = null;
        bestPathLength = Integer.MAX_VALUE;

        int width = maze.getWidth();
        int height = maze.getHeight();
        int depth = maze.getDepth();
        boolean[][][] visited = new boolean[width][height][depth];
        Node start = maze.getStartingSpace();

        // Iniciar el DFS desde el nodo de entrada con camino vacío y 0 pasos.
        dfs(maze, start, "", 0, visited);
        
        return bestPath != null ? bestPath : "[]";
    }

    /**
     * Realiza una búsqueda en profundidad (DFS) de forma recursiva para encontrar la ruta
     * más corta desde el nodo actual hasta la salida, evitando nodos peligrosos y ciclos.
     *
     * @param maze    El objeto Maze que contiene el mapa.
     * @param current El nodo actual.
     * @param path    La secuencia de direcciones recorridas hasta el momento (con comas).
     * @param steps   Número de pasos dados hasta el momento.
     * @param visited Matriz tridimensional que marca los nodos ya visitados en el camino actual.
     */
    private void dfs(Maze maze, Node current, String path, int steps, boolean[][][] visited) {
        // Si el nodo es peligroso, abortamos esta rama.
        if (current.danger) {
            return;
        }
        
        // Poda: si el camino actual ya es igual o mayor que el mejor encontrado, no continuar.
        if (steps >= bestPathLength) {
            return;
        }
        
        // Si llegamos a la salida, actualizamos la mejor ruta.
        if (current.isExit) {
            bestPathLength = steps;
            String formatted = path.endsWith(",") ? path.substring(0, path.length() - 1) : path;
            bestPath = "[" + formatted + "]";
            return;
        }

        // Marcar el nodo actual como visitado.
        visited[current.xIndex][current.yIndex][current.zIndex] = true;

        // Exploramos las direcciones en el siguiente orden: E, S, U, N, W, D.

        // 1. Este (E)
        if (current.east) {
            Node next = maze.moveEast(current);
            if (!visited[next.xIndex][next.yIndex][next.zIndex] && !next.danger) {
                dfs(maze, next, path + "E,", steps + 1, visited);
            }
        }
        // 2. Sur (S)
        if (current.south) {
            Node next = maze.moveSouth(current);
            if (!visited[next.xIndex][next.yIndex][next.zIndex] && !next.danger) {
                dfs(maze, next, path + "S,", steps + 1, visited);
            }
        }
        // 3. Arriba (U)
        if (current.up) {
            Node next = maze.moveUp(current);
            if (!visited[next.xIndex][next.yIndex][next.zIndex] && !next.danger) {
                dfs(maze, next, path + "U,", steps + 1, visited);
            }
        }
        // 4. Norte (N)
        if (current.north) {
            Node next = maze.moveNorth(current);
            if (!visited[next.xIndex][next.yIndex][next.zIndex] && !next.danger) {
                dfs(maze, next, path + "N,", steps + 1, visited);
            }
        }
        // 5. Oeste (W) -> Se usa "W" en lugar de "O"
        if (current.west) {
            Node next = maze.moveWest(current);
            if (!visited[next.xIndex][next.yIndex][next.zIndex] && !next.danger) {
                dfs(maze, next, path + "W,", steps + 1, visited);
            }
        }
        // 6. Abajo (D)
        if (current.down) {
            Node next = maze.moveDown(current);
            if (!visited[next.xIndex][next.yIndex][next.zIndex] && !next.danger) {
                dfs(maze, next, path + "D,", steps + 1, visited);
            }
        }

        // Backtracking: desmarcar el nodo actual para permitir que otras ramas lo visiten.
        visited[current.xIndex][current.yIndex][current.zIndex] = false;
    }
}
