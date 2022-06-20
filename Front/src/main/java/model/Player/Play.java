package model.Player;


public class Play {
//    String path;
//    volatile boolean pause;
//    volatile boolean stop;
//    volatile int speed;
//
//    public Play() {
//        this.pause = false;
//        this.stop = false;
//        this.speed = 1;
//    }
//
//    public void setPath(String path) { this.path = path; }
//    public void play() { new Thread(()->play_1()).start(); }
//    private void play_1() {
//        try {
//            Socket socket = new Socket(Config.HOST,Config.PORT);
//            TelnetIO io = new TelnetIO(socket.getInputStream(),socket.getOutputStream());
//            Scanner csv = new Scanner(new File(this.path));
//            csv.nextLine();
//            boolean valid = true;
//            while (!this.stop) {
//                while (!this.pause && (valid=csv.hasNext())){
//                    String line = csv.nextLine();
//                    System.out.println(line);
//                    io.write(line);
//                    Thread.sleep(100/speed);
//                }
//                if(!valid) this.stop();
//            }
//            io.close();
//            csv.close();
//            socket.close();
//        } catch (IOException | InterruptedException e) { throw new RuntimeException(e); }
//
//    }
//    public void setSpeed(int speed){
//        this.speed = speed;
//    }
//    public void pause(){
//        this.pause = !this.pause;
//    }
//    public void stop(){
//        this.pause();
//        this.stop = true;
//    }
}
