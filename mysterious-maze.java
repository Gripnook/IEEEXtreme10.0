import java.io.*;
import java.util.*;

public class Solution {
    static class Room {
        public int x, y;
        public Room(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    static int N;
    static List<Room> openings = new ArrayList<>();
    static int[][] maze;
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        N = in.nextInt();
        while (in.hasNextInt()) {
            int x = in.nextInt();
            if (x == -1)
                break;
            openings.add(new Room(x-1, in.nextInt()-1));
        }
        maze = new int[N][N];
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                maze[i][j] = -1;
            }
        }
        solve();
    }
    
    static void solve() {
        int count = -1;
        for (int i = 0; i < openings.size(); ++i) {
            Room r = openings.get(i);
            
            maze[r.x][r.y] = 0;
            
            if (checkForBottom(r)) {
                maze[r.x][r.y] = 1;
                if (propagate(r)) {
                    count = i+1;
                    break;
                }
            }
            
            if (checkForTop(r)) {
                maze[r.x][r.y] = 2;
                if (propagate(r)) {
                    count = i+1;
                    break;
                }
            }
        }
        System.out.println(count);
    }
    
    static boolean propagate(Room r) {
        Queue<Room> que = new LinkedList<>();
        que.add(r);
        while (!que.isEmpty()) {
            Room rr = que.remove();
            int same = maze[rr.x][rr.y];
            int opposite = (same%2)+1;
            if (rr.x > 0) {
                int xx = maze[rr.x-1][rr.y];
                if (xx==0) {
                    maze[rr.x-1][rr.y] = same;
                    que.add(new Room(rr.x-1, rr.y));
                }
                if (xx==opposite) {
                    return true;
                }
            }
            if (rr.x < N-1) {
                int xx = maze[rr.x+1][rr.y];
                if (xx==0) {
                    maze[rr.x+1][rr.y] = same;
                    que.add(new Room(rr.x+1, rr.y));
                }
                if (xx==opposite) {
                    return true;
                }
            }
            if (rr.y > 0) {
                int xx = maze[rr.x][rr.y-1];
                if (xx==0) {
                    maze[rr.x][rr.y-1] = same;
                    que.add(new Room(rr.x, rr.y-1));
                }
                if (xx==opposite) {
                    return true;
                }
            }
            if (rr.y < N-1) {
                int xx = maze[rr.x][rr.y+1];
                if (xx==0) {
                    maze[rr.x][rr.y+1] = same;
                    que.add(new Room(rr.x, rr.y+1));
                }
                if (xx==opposite) {
                    return true;
                }
            }
            if ((rr.x == 0 && same == 1) || (rr.x == N-1 && same == 2))
                return true;
        }
        return false;
    }
    
    static boolean checkForTop(Room r) {
        if (r.x == 0)
            return true;
        if (r.y > 0) {
            if (maze[r.x][r.y-1] == 2)
                return true;
        }
        if (r.y < N-1) {
            if (maze[r.x][r.y+1] == 2)
                return true;
        }
        if (r.x > 0) {
            if (maze[r.x-1][r.y] == 2)
                return true;
        }
        if (r.x < N-1) {
            if (maze[r.x+1][r.y] == 2)
                return true;
        }
        return false;
    }
    
    static boolean checkForBottom(Room r) {
        if (r.x == N-1)
            return true;
        if (r.y > 0) {
            if (maze[r.x][r.y-1] == 1)
                return true;
        }
        if (r.y < N-1) {
            if (maze[r.x][r.y+1] == 1)
                return true;
        }
        if (r.x > 0) {
            if (maze[r.x-1][r.y] == 1)
                return true;
        }
        if (r.x < N-1) {
            if (maze[r.x+1][r.y] == 1)
                return true;
        }
        return false;
    }
}
