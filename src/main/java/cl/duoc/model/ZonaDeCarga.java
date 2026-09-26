package cl.duoc.model;

import java.util.ArrayList;
import java.util.List;


/**
 * Representa el recurso compartido donde se depositan y retiran los pedidos.
 */
public class ZonaDeCarga {
    private final List<Pedido> pedidosPendientes = new ArrayList<>();

    public List<Pedido> getPedidosPendientes() {
        return pedidosPendientes;
    }

    /**
     * Agrega un pedido a la zona de carga de forma segura.
     * @param p el objeto pedido que ingresa al almacén.
     */
    public synchronized void agregarPedido(Pedido p) {
        pedidosPendientes.add(p);
        System.out.println("...........Nuevo Pedido Pendiente.........." + p);
    }

    /**
     * Retira y devuelve el primer pedido disponible en la zona de carga.
     * Si existen pedidos en la lista, remueve el primero en espera y lo retorna al repartidor. En caso de que la lista
     * se encuentre vacía, muestra un mensaje informativo por consola y retorna null.
     * @return el pedido extraído de la cola, o null.
     */
    public synchronized Pedido retirarPedido() {
        if(!pedidosPendientes.isEmpty()) {
        Pedido pedidoRetirado = pedidosPendientes.remove(0);
        return pedidoRetirado;
        }else {
            System.out.println("[Notificando a repartidor] " + Thread.currentThread().getName() + " No hay pedidos pendientes.");
            return null;
        }

    }

}