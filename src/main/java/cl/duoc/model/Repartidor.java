package cl.duoc.model;

/**
 *Tarea concurrente que representa a un repartidor dentro del sistema SpeedFast.
 *
 * Implementa la interfaz Runnable para actuar como un hilo. Haciendo que los repartidores
 * actúen de forma concurrente al retirar pedidos desde la zonaDeCarga, actualiza sus fases de
 * reparto (PENDIENTE, EN_REPARTO, ENTREGADO) y simula los tiempos de desplazamiento hacia los destinos de entrega mediante
 * pausas controladas.
 * @author Katherine Avila
 */
public class Repartidor implements Runnable {
    private String nombre;
    private ZonaDeCarga zonaDeCarga;

    public Repartidor(String nombre, ZonaDeCarga zonaDeCarga) {

        this.nombre = nombre;
        this.zonaDeCarga = zonaDeCarga;
    }

    /**
     * Ciclo principal de ejecución del hilo Repartidor.
     *
     * Retira pedidos de la zona de carga de manera continua hasta que ya no queden pedidos pendientes (null)
     * para cada pedido retirado: actualiza el estado de PENDIENTE a EN_REPARTO y notifica el retiro del pedido,
     * simula el tiempo de viaje utilizando Thread.sleep.
     * Actualiza el estado ha entregado y confirma la entrega.
     */
    @Override
    public void run() {
        while (true) {
            Pedido pedidoRetirado = zonaDeCarga.retirarPedido();
            if (pedidoRetirado == null) {
                System.out.println(nombre + " finalizando Jornada...");
                break;
            }
            pedidoRetirado.setEstadoPedido(EstadoPedido.EN_REPARTO);
            System.out.println("Pedido #" + pedidoRetirado.getId() + " |retirado por: " + nombre + " |Estado: " + pedidoRetirado.getEstadoPedido());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            pedidoRetirado.setEstadoPedido(EstadoPedido.ENTREGADO);
            System.out.println("Pedido #" + pedidoRetirado.getId() + " |Estado: " + pedidoRetirado.getEstadoPedido() + " |Entregado por: " +  nombre);
        }
    }
}
