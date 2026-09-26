package cl.duoc.main;

import cl.duoc.model.EstadoPedido;
import cl.duoc.model.Pedido;
import cl.duoc.model.Repartidor;
import cl.duoc.model.ZonaDeCarga;

///**
// * Clase principal que actúa como punto de entrada de la aplicación SpeedFast.
// *
// * Inicializa el recurso compartido ZonaDeCarga.java
// * registra los pedidos iniciales en estado pendiente y coordina la ejecución
// * de los hilos Repartidor. Además de sincronizar el cierre ordenado mediante join()
// * @author Katherine Avila
// */
//public class Main {
//    public static void main(String[] args) {
////        ZonaDeCarga cargaPedido =  new ZonaDeCarga();
////        //Generación de Pedidos
////        Pedido p01 = new Pedido(1,"Velaris 2010", EstadoPedido.PENDIENTE);
////        Pedido p02 = new Pedido(2,"Luthadel 800", EstadoPedido.PENDIENTE);
////        Pedido p03 = new Pedido(3, "Muy Muy Lejano 100",  EstadoPedido.PENDIENTE);
////        Pedido p04 = new Pedido(4, "Migdar 5010",  EstadoPedido.PENDIENTE);
////        Pedido p05 = new Pedido(5, "Campamento Mestizo 200",  EstadoPedido.PENDIENTE);
////        Pedido p06 = new Pedido(6, "Plataforma 934",  EstadoPedido.PENDIENTE);
////
////        //Pedidos agregados a la zona de despacho
////        cargaPedido.agregarPedido(p01);
////        cargaPedido.agregarPedido(p02);
////        cargaPedido.agregarPedido(p03);
////        cargaPedido.agregarPedido(p04);
////        cargaPedido.agregarPedido(p05);
////        cargaPedido.agregarPedido(p06);
////        System.out.println("................................");
////
////        //Creación de Repartidores
////        Thread repartidor1 = new Thread(new Repartidor("HarryP", cargaPedido),"HarryP");
////        Thread repartidor2 = new Thread(new Repartidor("Tamlin", cargaPedido), "Tamlin");
////        Thread repartidor3 = new Thread(new Repartidor("Fiona", cargaPedido),"Fiona");
////
////        //Inicio de los hilos
////        repartidor1.start();
////        repartidor2.start();
////        repartidor3.start();
////
////
////        try{
////            repartidor1.join();
////            repartidor2.join();
////            repartidor3.join();
////            System.out.println("................Todos los pedidos han sido entregados correctamente.............");
////
////        } catch (InterruptedException e) {
////             Thread.currentThread().interrupt();
////            System.out.println("Proceso interrumpido: " + e.getMessage());
////        }
////
////    }
//    }