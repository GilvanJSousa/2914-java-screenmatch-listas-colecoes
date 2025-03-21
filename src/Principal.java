import javax.swing.*;
import java.util.Collections;
import java.util.List;

public class Principal {

    public static void main(String[] args) {
        try {
            // Inicialização e boas-vindas
            JOptionPane.showMessageDialog(null, "Bem-vindo ao sistema de compras do cartão de crédito!");

            // Obter o limite do cartão
            String limiteInput = JOptionPane.showInputDialog(null, "Digite o limite do cartão:");
            double limite;

            try {
                limite = Double.parseDouble(limiteInput);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Entrada inválida para o limite. Por favor, digite um número.");
                return; // Encerra o programa se a entrada for inválida
            }

            CartaoDeCredito cartao = new CartaoDeCredito(limite);

            // Loop para realizar compras
            int sair = 1;
            while (sair != 0) {
                // Obter descrição da compra
                String descricao = JOptionPane.showInputDialog(null, "Digite a descrição da compra:");
                if (descricao == null) { //Tratamento para o usuario cancelar a operacao
                    sair = 0;
                    break;
                }

                // Obter valor da compra
                String valorInput = JOptionPane.showInputDialog(null, "Digite o valor da compra:");
                double valor;

                try {
                    valor = Double.parseDouble(valorInput);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Entrada inválida para o valor da compra. Por favor, digite um número.");
                    continue; // Volta para o início do loop
                }

                // Realizar a compra
                Compra compra = new Compra(descricao, valor);
                boolean compraRealizada = cartao.lancaCompra(compra);

                if (compraRealizada) {
                    JOptionPane.showMessageDialog(null, "Compra realizada!");
                    String sairInput = JOptionPane.showInputDialog(null, "Digite 0 para sair ou 1 para continuar:");
                    try {
                        sair = Integer.parseInt(sairInput);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Entrada inválida. Saindo do programa.");
                        sair = 0;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Saldo insuficiente!");
                    sair = 0;
                }
            }

            // Exibir compras realizadas
            JOptionPane.showMessageDialog(null, "***********************\nCOMPRAS REALIZADAS:");
            List<Compra> compras = cartao.getCompras();
            Collections.sort(compras);

            StringBuilder comprasRealizadas = new StringBuilder();
            for (Compra c : compras) {
                comprasRealizadas.append(c.getDescricao()).append(" - ").append(c.getValor()).append("\n");
            }
            JOptionPane.showMessageDialog(null, comprasRealizadas.toString() + "\n***********************");

            // Exibir saldo final
            JOptionPane.showMessageDialog(null, "Saldo do cartão: " + cartao.getSaldo());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro inesperado: " + e.getMessage());
            e.printStackTrace(); // Para depuração, imprime o rastreamento da pilha no console
        }
    }
}