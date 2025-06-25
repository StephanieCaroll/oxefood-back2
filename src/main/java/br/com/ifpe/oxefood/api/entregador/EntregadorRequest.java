package br.com.ifpe.oxefood.api.entregador;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.ifpe.oxefood.modelo.entregador.Entregador;
import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past; // Para datas
import jakarta.validation.constraints.Pattern; // Para o prefixo do telefone
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntregadorRequest {

    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    private String nome;

    @NotBlank(message = "O CPF é obrigatório.")
    private String cpf;

    @NotBlank(message = "O RG é obrigatório.")
    @Size(max = 20, message = "O RG deve ter no máximo 20 caracteres.")
     @Column(unique = true)
    private String rg;

    @NotNull(message = "A data de nascimento é obrigatória.")
    @Past(message = "A data de nascimento não pode ser futura.")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @NotBlank(message = "O telefone celular é obrigatório.")

    private String foneCelular;

    private String foneFixo;

    @NotNull(message = "A quantidade de entregas realizadas é obrigatória.")
    @Min(value = 0, message = "A quantidade de entregas realizadas não pode ser negativa.")
    private Integer qtdEntregasRealizadas;

    @NotNull(message = "O valor do frete é obrigatório.")
    @DecimalMin(value = "0.0", message = "O valor do frete não pode ser negativo.")
    private Double valorFrete;

    @NotBlank(message = "A rua do endereço é obrigatória.")
    @Size(max = 150, message = "A rua do endereço deve ter no máximo 150 caracteres.")
    private String enderecoRua;

    @Size(max = 100, message = "O complemento do endereço deve ter no máximo 100 caracteres.")
    private String enderecoComplemento;

    @NotBlank(message = "O número do endereço é obrigatório.")
    @Size(max = 20, message = "O número do endereço deve ter no máximo 20 caracteres.")
    private String enderecoNumero;

    @NotBlank(message = "O bairro do endereço é obrigatório.")
    @Size(max = 100, message = "O bairro do endereço deve ter no máximo 100 caracteres.")
    private String enderecoBairro;

    @NotBlank(message = "A cidade do endereço é obrigatória.")
    @Size(max = 100, message = "A cidade do endereço deve ter no máximo 100 caracteres.")
    private String enderecoCidade;

    @NotBlank(message = "O CEP do endereço é obrigatório.")
    // @Pattern(regexp = "\\d{8}", message = "O CEP deve conter 8 dígitos numéricos.")
    private String enderecoCep;

    @NotBlank(message = "A UF do endereço é obrigatória.")
    @Size(min = 2, max = 2, message = "A UF do endereço deve ter 2 caracteres.")
    private String enderecoUf;

    private Boolean ativo;
    
    public Entregador build() {
        return Entregador.builder()
                .nome(nome)
                .cpf(cpf)
                .rg(rg)
                .dataNascimento(dataNascimento)
                .foneCelular(foneCelular)
                .foneFixo(foneFixo)
                .qtdEntregasRealizadas(qtdEntregasRealizadas)
                .valorFrete(valorFrete)
                .enderecoRua(enderecoRua)
                .enderecoComplemento(enderecoComplemento)
                .enderecoNumero(enderecoNumero)
                .enderecoBairro(enderecoBairro)
                .enderecoCidade(enderecoCidade)
                .enderecoCep(enderecoCep)
                .enderecoUf(enderecoUf)
                .ativo(ativo)
                .build();
    }
}