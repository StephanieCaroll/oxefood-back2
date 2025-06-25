package br.com.ifpe.oxefood.api.produto;

import br.com.ifpe.oxefood.modelo.produto.Produto;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoRequest {

    @NotNull(message = "A categoria é obrigatória.")
    private Long idCategoria;

    @NotBlank(message = "O código é obrigatório.")
    @Size(max = 20, message = "O código deve ter no máximo {max} caracteres.")
    private String codigo;

    @NotBlank(message = "O título é obrigatório.")
    @Size(max = 100, message = "O título deve ter no máximo {max} caracteres.")
    private String titulo;

    @Size(max = 500, message = "A descrição deve ter no máximo {max} caracteres.")
    private String descricao;

    @NotNull(message = "O valor unitário é obrigatório.")
    // @DecimalMin(value = "20.00", message = "O valor unitário deve ser no mínimo R$ 20.00.") // Alterado
    @DecimalMax(value = "100.00", message = "O valor unitário deve ser no máximo R$ 100.00.") // Adicionado
    private Double valorUnitario;

    @NotNull(message = "O tempo de entrega mínimo é obrigatório.")
    @Min(value = 0, message = "O tempo de entrega mínimo não pode ser negativo.")
    private Integer tempoEntregaMinimo;

    @NotNull(message = "O tempo de entrega máximo é obrigatório.")
    @Min(value = 0, message = "O tempo de entrega máximo não pode ser negativo.")
    private Integer tempoEntregaMaximo;

    public Produto build() {
        return Produto.builder()
                .codigo(codigo)
                .titulo(titulo)
                .descricao(descricao)
                .valorUnitario(valorUnitario)
                .tempoEntregaMinimo(tempoEntregaMinimo)
                .tempoEntregaMaximo(tempoEntregaMaximo)
                .build();
    }
}