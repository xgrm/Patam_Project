import controller.Controller;
import model.BackendModel;
import model.db.DBQueries;


public class Main {
    static volatile boolean stop = false;
    public static void main(String[] args) {

//        BackendModel model = new BackendModel("dbDetails.txt");
//        Controller cn = new Controller(model);
        DBQueries db = new DBQueries("dbDetails.txt");
        //db.createFlightDataTable();
//        db.addFlight2("saarAirCRAFT","YES",-1F);
        db.updateFlight2(1,"NO",100F);

//        new Thread(()->{
//            Thread.currentThread().setName("Front Thread");
//            try {
//                String id = "0";
//                Thread.sleep(15*1000);
//                Socket back = new Socket("127.0.0.1",5500);
//                SocketIO io = new SocketIO(back.getInputStream(),back.getOutputStream());
//                io.write("Front~ ");
//                if(io.readLine().equals("ok"))
//                    io.write("activeAgents~ ");
//                while (id.equals("0")){
//                    id = io.readLine();
//                    io.write("activeAgents~ ");
//                    System.out.println("the id is: "+id);
//                }
//                System.out.println(Thread.currentThread().getName()+" Start sending commands!");
//                for (int i = 0; i < 50; i++) {
//                    io.write("setCommand~"+id+" Aileron 1");
//                    io.write("setCommand~"+id+" Elevator 1");
//                    io.write("setCommand~"+id+" Rudder 1");
//                    io.write("setCommand~"+id+" Throttle 1");
//                    Thread.sleep(100);
//                    io.write("setCommand~"+id+" Aileron -1");
//                    io.write("setCommand~"+id+" Elevator -1");
//                    io.write("setCommand~"+id+" Rudder -1");
//                    io.write("setCommand~"+id+" Throttle -1");
//                    Thread.sleep(100);
//                }
//                io.close();
//                back.close();
//                System.out.println(Thread.currentThread().getName()+" close!");
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }).start();
//
//        new Thread(()->{
//            try {
//                Thread.sleep((long) ((1.5)*60*1000));
//                cn.close();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }).start();

//        new Thread(()->{
//            Thread.currentThread().setName("Front Thread");
//            try {
//                Thread.sleep(6);
//
//                Socket backEnd = new Socket("127.0.0.1",5500);
//                SocketIO io = new SocketIO(backEnd.getInputStream(),backEnd.getOutputStream());
//                io.write("front~ ");
//                if(io.readLine().equals("ok"))
//                    io.write("activeAgents~ ");
//                System.out.println("afterOk");
//                String id ;
//                while ((id=io.readLine()).equals("0")){
//                    io.write("activeAgents~ ");
//
//                }
//                    for (int i = 0; i < 50; i++) {
//                        io.write("setCommand~" + id + " Aileron 1");
//                        io.write("setCommand~" + id + " Elevator 1");
//                        io.write("setCommand~" + id + " Rudder 1");
//                        io.write("setCommand~" + id + " Throttle 1");
//                        Thread.sleep(100);
//                        io.write("setCommand~" + id + " Aileron -1");
//                        io.write("setCommand~" + id + " Elevator -1");
//                        io.write("setCommand~" + id + " Rudder -1");
//                        io.write("setCommand~" + id + " Throttle -1");
//                        Thread.sleep(100);
//                    }
//                io.close();
//                backEnd.close();
//                System.out.println("closed "+Thread.currentThread().getName());
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }).start();
//
//        new Thread(()->{
//            Thread.currentThread().setName("Agent Thread");
//            try {
//                Thread.sleep(12);
//                Socket backEnd = new Socket("127.0.0.1",5500);
//                SocketIO io = new SocketIO(backEnd.getInputStream(),backEnd.getOutputStream());
//                io.write("agent~TLV-TLV");
//                if(io.readLine().equals("ok")){
//                    new Thread(()->{
//                        Thread.currentThread().setName("Agent in Thread");
//                        try {
//                            PrintWriter printWriter = new PrintWriter(new File("c.txt"));
//                            while (io.hasNext()){
//                                printWriter.println(io.readLine());
//                                printWriter.flush();
//                            }
//                            printWriter.close();
//                        } catch (FileNotFoundException e) {
//                            throw new RuntimeException(e);
//                        }
//                        System.out.println("closed "+Thread.currentThread().getName());
//                    }).start();
//                    for (int i = 0; i < 15; i++) {
//                        io.write("exp~from Agent");
//                        Thread.sleep(100);
//                    }
//                    System.out.println(Thread.currentThread().getName()+" is going to sleep for 25 sec");
//                    Thread.sleep(25*1000);
//                    System.out.println(Thread.currentThread().getName()+" is UP");
//                    backEnd.close();
//                    io.close();
//                    System.out.println("closed "+Thread.currentThread().getName());
//                }
//
//
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }).start();
//
//        new Thread(()->{
//            try {
//                Thread.currentThread().setName("main t");
//                Thread.sleep(1*60*1000);
//                cn.close();
//                System.out.println("closed "+Thread.currentThread().getName());
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }).start();

//        new Thread(()->{
//            try {
//                Thread.sleep(1000*20);
//                cn.close();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }).start();
//        Server s = new Server();
//        s.start(5500,(Socket socket)->{
//            new Thread(()->{
//                try {
//                    SocketIO io = new SocketIO(socket.getInputStream(),socket.getOutputStream());
//                    while (io.hasNext()){
//                        System.out.println(io.readLine());
//                    }
//                    io.close();
//                    socket.close();
//                } catch (IOException e) {throw new RuntimeException(e);}
//            }).start();
//        });
//        new Thread(()->{
//            try {
//                Socket server = new Socket("127.0.0.1",5500);
//                SocketIO io = new SocketIO(server.getInputStream(),server.getOutputStream());
//                Thread.sleep(10*1000);
//                for (int i = 0; i < 5; i++) {
//                    io.write("hi");
//                    Thread.sleep(100);
//                }
//                io.close();
//                server.close();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }).start();
//        try {
//            Thread.sleep(5*1000);
//            s.stop();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }


//        try {
//            Scanner scanner = new Scanner(new File("test.csv"));
//            String line;
//            cn.testCommands("addFlight~"+scanner.nextLine());
//            Thread.sleep(100);
//            int id = cn.getId();
//            System.out.println(id);
//            while (scanner.hasNext()){
//                cn.testCommands("addRow~"+id+" "+scanner.nextLine());
//            }
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }
}}