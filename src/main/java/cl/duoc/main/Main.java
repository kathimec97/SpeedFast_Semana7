package cl.duoc.main;


import cl.duoc.vista.VentanaPrincipal;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            try{
                VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
                ventanaPrincipal.setVisible(true);
            }catch(Exception e){
                System.out.println("Error al iniciar la ventana principal: "+e.getMessage());
            }

            });
        }
    }
